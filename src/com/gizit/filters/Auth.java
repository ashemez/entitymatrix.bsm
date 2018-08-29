package com.gizit.filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gizit.bsm.beans.UserBean;
import com.gizit.bsm.dao.UsersDAO;
import com.gizit.managers.LDAPManager;
import com.gizit.managers.ResourceManager;
import com.sun.jndi.ldap.LdapCtxFactory;

public class Auth implements Filter {

	boolean authenticationGranted = false;
	
	String ldapHostname;
	String ldapUsername;
	String ldapPassword;
	
	String loginUrlPath;
	
	LDAPManager ldapManager;
	ResourceManager RM;
	
	public void init(FilterConfig filterConfiguration) throws ServletException {
		RM = new ResourceManager();
		ldapManager = new LDAPManager();
		
		loginUrlPath = filterConfiguration.getInitParameter("login-url-path");

		System.out.println("Authorize filter created having with login url '" + loginUrlPath + 
				"', ldap server '" + ldapHostname +  "', with username '" + ldapUsername + 
				"' and password '" + ldapPassword + "'.");
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
        FilterChain servletFilterChain) throws IOException, ServletException {
	
		UsersDAO usersDAO = new UsersDAO();
		
		System.out.println("Performing authorization... Auth mode: " + RM.GetServerProperty("authentication"));

		boolean authenticationGranted = false;
		String redirectUri = null;
		
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		
		// perform authentication only if user is not previously authorized
		String authorizedUsername = (String) session.getAttribute("authorizedUsername");
		String authorizedFirstname = (String) session.getAttribute("authorizedFirstname");
		String authorizedLastname = (String) session.getAttribute("authorizedLastname");
		if (authorizedUsername != null) {
			System.out.println("User '" + authorizedUsername + "' was previously authorized!");
			UserBean user = new UserBean();
			user.username.set(authorizedUsername);
			user.firstName.set(authorizedFirstname);
			user.lastName.set(authorizedLastname);
			/*UserBean user = new UserBean(); 
			user.username.set(request.getParameter("username"));
			user.password.set(request.getParameter("password"));
			UsersDAO usersDAO = new UsersDAO();
			user = usersDAO.login(user);*/
			session.setAttribute("currentSessionUser",user);
			authenticationGranted = true;
		} else {
			if (request.getServletPath().equals(loginUrlPath)) {
				System.out.println("auth request.getMethod() " + request.getMethod());

				if (request.getMethod().equals("GET")) {
					// login URI is always allowed
					authenticationGranted = true;
				} else if (request.getMethod().equals("POST")) {
					String username = request.getParameter("j_username");
					String password = request.getParameter("j_password");
					
					if(username.equals("gizit")) {
						System.out.println("logging in as gizit...");
						UserBean user = new UserBean();
						user.username.set(username);
						user.password.set(password);
						user = usersDAO.login(user);

						if (user.valid.get()) { 
							authenticationGranted = true;
							session.setAttribute("authorizedUsername", username);
							session.setAttribute("authorizedFirstname", user.firstName.get());
							session.setAttribute("authorizedLastname", user.lastName.get());
						    session.setAttribute("currentSessionUser",user);
						} else {
							redirectUri = String.format("%s%s", request.getContextPath(), loginUrlPath);
							session.setAttribute("_login_authentication_error", "Authentication error, username or password invalid!");
						}
					} else {
						System.out.println("auth request.getServletPath() " + request.getServletPath() + " loginUrlPath: " + loginUrlPath);
						// loginUrlPath would be defined as "/login"
						System.out.println("auth request.getMethod() " + request.getMethod() + "  username: " + username);

						if(RM.GetServerProperty("authentication").equals("smdb")) {
							
							UserBean user = new UserBean();
							user.username.set(username);
							user.password.set(password);
							user = usersDAO.login(user);
							
							if (user.valid.get()) { 
								authenticationGranted = true;
								session.setAttribute("authorizedUsername", username);
								session.setAttribute("authorizedFirstname", user.firstName.get());
								session.setAttribute("authorizedLastname", user.lastName.get());
							    session.setAttribute("currentSessionUser",user);
							} else {
								redirectUri = String.format("%s%s", request.getContextPath(), loginUrlPath);
								session.setAttribute("_login_authentication_error", "Authentication error, username or password invalid!");
							}
							
						} else {
							// authorize user with credentials provided from login page
							if (ldapManager.LDAPAuthentication(username, password)) {
								session.setAttribute("authorizedUsername", username);
								session.setAttribute("authorizedFirstname", ldapManager.firstName.get());
								session.setAttribute("authorizedLastname", ldapManager.lastName.get());
								authenticationGranted = true;
	
								// create userbean for session
								UserBean user = new UserBean();
								user.username.set(username);
								user.firstName.set(ldapManager.firstName.get());
								user.lastName.set(ldapManager.lastName.get());
								session.setAttribute("currentSessionUser",user);
	
								System.out.println("User '" + session.getAttribute("authorizedUsername") + "' is now authorized!");
							} else {
								redirectUri = String.format("%s%s", request.getContextPath(), loginUrlPath);
								session.setAttribute("_login_authentication_error", "Authentication error, username or password invalid!");
							}
						}
					}

				}
			} else {
				// redirect to login URI if configured
				if (loginUrlPath != null) {
					redirectUri = String.format("%s%s", request.getContextPath(), loginUrlPath);
					// keep original URL in session to redirect after valid authentication
					session.setAttribute("_login_original_request_url", request.getRequestURL());
				} else {
					// authorize user with credentials provided from initial parameters
					/* if (ldapManager.LDAPAuthentication(ldapUsername, ldapPassword)) {
						authorizedUsername = ldapUsername;
						session.setAttribute("authorizedUsername", ldapUsername);
						authenticationGranted = true;

						System.out.println("User '" + authorizedUsername + "' is now authorized!");
					} */
				}
			}
		}
		
		if(!request.getMethod().equals("GET") && session.getAttribute("authorizedUsername") != null && !usersDAO.UserExists(session.getAttribute("authorizedUsername").toString())) {
			session.setAttribute("authorizedUsername", null);
			session.setAttribute("authorizedFirstname", null);
			session.setAttribute("authorizedLastname", null);
		    session.setAttribute("currentSessionUser", null);

			authenticationGranted = false;
			redirectUri = "/login";
			session.setAttribute("_login_authentication_error", "Authentication error, user is not authorized yet!");
			//response.sendRedirect("/login");
		}
		
		if(authenticationGranted) {
			if(session.getAttribute("_login_original_request_url") == null) {
				session.setAttribute("_login_original_request_url", "/index.jsp");
			} else if(session.getAttribute("_login_original_request_url").toString().contains("login.jsp")) {
				session.setAttribute("_login_original_request_url", "/index.jsp");
			}

			System.out.println("2* " + session.getAttribute("_login_original_request_url") + " " + loginUrlPath);
			servletFilterChain.doFilter(servletRequest, servletResponse);
		} else {
			if (redirectUri == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				response.sendRedirect(redirectUri);
			}
		}
	}
	
	public static String usersContainer = "cn=users,dc=example,dc=com";
	private void GetLDAPGroups() {
		SearchControls ctls = new SearchControls();
        String[] attrIDs = { "cn" };
        ctls.setReturningAttributes(attrIDs);
        ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		ctls.setReturningAttributes(attrIDs);
		String[] attributes = {"memberOf"};
		ctls.setReturningAttributes(attributes);
		
		try {
			NamingEnumeration<SearchResult> answer = ldapManager.directoryContext.search(usersContainer, "(&(objectclass=user)(sAMAccountName=userName))", ctls);
			
		    while (answer.hasMore()) {
		    	SearchResult result = answer.next();
		    	System.out.println("grpInfo: " + result.getName());
		    }
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void GetLDAPUsers() throws NamingException {
		SearchControls searchCtrls = new SearchControls();
		searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String filter = "(objectClass=person)";
		NamingEnumeration values = ldapManager.directoryContext.search("CN=Users,DC=gizldap,DC=com",filter,searchCtrls);
		while (values.hasMoreElements())
		{
			SearchResult result = (SearchResult) values.next();
			Attributes attribs = result.getAttributes();

			if (null != attribs)
			{
				for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();)
				{
					Attribute atr = (Attribute) ae.next();
					String attributeID = atr.getID();
					
					if(attributeID.equals("cn")) {
						for (Enumeration vals = atr.getAll(); 
							vals.hasMoreElements();) {
							
							System.out.println("LDAP_USER_SEARCH: " + attributeID +": "+ vals.nextElement());
						}
					}
				}
			}
		}
	}

	@Override
	public void destroy() {
		try {
			authenticationGranted = false;
			if(ldapManager.directoryContext != null)
				ldapManager.directoryContext.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Authorize filter destroyed.");
	}
}
