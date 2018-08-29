package com.gizit.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gizit.bsm.dao.UsersDAO;
import com.gizit.bsm.beans.UserBean;
import com.gizit.managers.LogManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    
	LogManager LOG = new LogManager(LoginServlet.class);
	UsersDAO usersDAO = new UsersDAO();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String httpReferer = request.getParameter("referrer");
		try { 
			UserBean user = new UserBean(); 
			user.username.set(request.getParameter("j_username"));
			user.password.set(request.getParameter("j_password"));
			user = usersDAO.login(user);
			
			if (user.valid.get()) { 
				HttpSession session=request.getSession(true);
			    session.setAttribute("currentSessionUser",user); 

			    if(httpReferer != null) {
				    if(httpReferer.trim() != "")
				    {
				    	if(!httpReferer.contains("login.jsp") && !httpReferer.contains("Login") && !httpReferer.contains("Logout")) {
				    		response.sendRedirect(httpReferer);
				    	} else {
				    		response.sendRedirect("index.jsp");
				    	}
				    } else {
				    	response.sendRedirect("index.jsp");
				    }
			    } else {
			    	response.sendRedirect("index.jsp");
			    }
			} else {
				response.sendRedirect("invalidLogin.jsp");
			} 
		}
		catch(Exception e) {
			LOG.Error(e);
		}

	} 

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
