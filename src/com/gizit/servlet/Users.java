package com.gizit.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizit.bsm.beans.BeanList;
import com.gizit.bsm.beans.RoleBean;
import com.gizit.bsm.beans.UserBean;
import com.gizit.bsm.dao.UsersDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;


//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/ChartWS.wsdl")

@WebServlet(urlPatterns = {"/Users"})
public class Users extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(Users.class);

	private UsersDAO usersDAO = new UsersDAO();
		
	private String changePassw(String username, String password) {
		return usersDAO.ChangePassw(username,password);
    }
	private String addUser(String username, String firstname, String lastname, String password) {
		return usersDAO.AddUser(username,firstname,lastname,password);
	}
	private String updateUser(int userid, String username, String firstname, String lastname, String password) {
		return usersDAO.UpdateUser(userid,username,firstname,lastname,password);
	}
	private String deleteUser(int userid) {
		return usersDAO.DeleteUser(userid);
	}
	private String getUser(int userid) {
		return usersDAO.GetUser(userid);
	}
	private String getUserAfterInsert(String username, String firstname, String lastname) {
		return usersDAO.getUserAfterInsert(username, firstname,lastname);
	}
	private String getAllUsers() {
		return usersDAO.GetAllUsers();
	}
	private String getUserEnrolledRoles(int userid) {
		return usersDAO.GetUserEnrolledRoles(userid);
	}
	private String getUserUnEnrolledRoles(int userid) {
		return usersDAO.GetUserUnEnrolledRoles(userid);
	}
	private String getUserEnrolledGroups(int userid) {
		return usersDAO.GetUserEnrolledGroups(userid);
	}
	private String getUserUnEnrolledGroups(int userid) {
		return usersDAO.GetUserUnEnrolledGroups(userid);
	}
	private String updateUserRoles(int userid,String [] roleList) {
		return usersDAO.UpdateUserRoles(userid,roleList);
	}
	private String updateUserGroups(int userid, String [] groupList) {
		return usersDAO.UpdateUserGroups(userid,groupList);
	}

	
	
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        this.loginState = sessionManager.CheckServletSession(request.getSession(), response);
        
        /*String username = "";
        if(sessionManager.currentUser != null)
        	username = sessionManager.currentUser.getUsername();*/
        
        String process = request.getParameter("p");
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("pass");
        
        String grplist = request.getParameter("groupList");
        String [] groupList = null;
        if(grplist != null)
        {
        	String[] groups = grplist.split(",");
        	groupList = new String[groups.length];
        	for(int i1 = 0; i1< groupList.length; i1++)
        	{
        		groupList[i1] = groups[i1];
        	}
        }
    	
        String rolelist = request.getParameter("roleList");
        String [] roleList = null;
        if(rolelist != null)
        {
        	String[] roles =  rolelist.split(",");
        	roleList = new String[roles.length];
        	for(int i1 = 0; i1< roleList.length; i1++)
        	{
        		roleList[i1] = roles[i1];
        	}
        }
        
        try (PrintWriter out = response.getWriter()) {
        	
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		usersDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.AdministrationAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			switch(process)
                    {
                        case "changePassw":
                        	if(sessionManager.currentUser != null)
                            	username = sessionManager.currentUser.username.get();
                        	out.print(changePassw(username,password));
                        	break;
                        case "addUser":
                        	out.print(addUser(username, firstname, lastname,password));
                        	break;
                        case "updateUser":
                        	out.print(updateUser(Integer.parseInt(userid), username, firstname,lastname, password));
                        	break;
                        case "deleteUser":
                        	out.print(deleteUser(Integer.parseInt(userid)));
                        	break;
                        case "getUser":
                        	out.print(getUser(Integer.parseInt(userid)));
                        	break;
                        case "getUserAfterInsert":
                        	out.print(getUserAfterInsert(username,firstname,lastname));
                        	break;                        	                       	
                        case "getAllUsers":
                        	out.print(getAllUsers());
                        	break;
                        case "getUserEnrolledRoles":
                        	out.print(getUserEnrolledRoles(Integer.parseInt(userid)));
                        	break;
                        case "getUserUnEnrolledRoles":
                        	out.print(getUserUnEnrolledRoles(Integer.parseInt(userid)));
                        	break;
                        case "getUserEnrolledGroups":
                        	out.print(getUserEnrolledGroups(Integer.parseInt(userid)));
                        	break;
                        case "getUserUnEnrolledGroups":
                        	out.print(getUserUnEnrolledGroups(Integer.parseInt(userid)));
                        	break;
                        case "updateUserRoles":
                        	out.print(this.updateUserRoles(Integer.parseInt(userid), roleList));
                        	break;
                        case "updateUserGroups":
                        	out.print(this.updateUserGroups(Integer.parseInt(userid), groupList));
                        	break;
                        default:
                        	LOG.Warn(RM.GetErrorString("WARN_INVALID_ENDPOINT") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                            break;
                    }
        		}
        		break;
        	case notloggedin:
        		LOG.Warn(RM.GetErrorString("WARN_LOGIN_NEEDED") + " " + requestedEndPoint);
        		out.print(LOG.resultBean.Serialize());
        		break;
        	}
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
