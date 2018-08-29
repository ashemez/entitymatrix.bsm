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

import com.gizit.bsm.dao.GroupsDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/ChartWS.wsdl")

@WebServlet(urlPatterns = {"/Groups"})
public class Groups extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private GroupsDAO groupsDAO = new GroupsDAO();
	
	LogManager LOG = new LogManager(Groups.class);
	
	private String getAllGroups() {
		return groupsDAO.GetAllGroups();
    }
	private String getGroup(int group_id) {
		return groupsDAO.GetGroup(group_id);
    }
	private String getEnrolledGroups(String username) {
		return groupsDAO.GetEnrolledGroups(username);
	}
	private String getUnenrolledGroups(String username) {
		return groupsDAO.GetUnenrolledGroups(username);
	}
	private String getGroupEnrolledUsers(int groupid) {
		return groupsDAO.GetGroupEnrolledUsers(groupid);
	}
	private String getGroupUnEnrolledUsers(int groupid) {
		return groupsDAO.GetGroupUnEnrolledUsers(groupid);
	}
	private String getGroupEnrolledRoles(int groupid) {
		return groupsDAO.GetGroupEnrolledRoles(groupid);
	}
	private String getGroupUnEnrolledRoles(int groupid) {
		return groupsDAO.GetGroupUnEnrolledRoles(groupid);
	}
	private String createNewGroup(String group_name, String[] grpRoleList, String[] memberList) {
		return groupsDAO.CreateNewGroup(group_name, grpRoleList, memberList);
	}
	private String updateGroup(int group_id, String group_name, String[] grpRoleList, String[] memberList) {
		return groupsDAO.UpdateGroup(group_id, group_name, grpRoleList, memberList);
	}
	private String deleteGroup(int group_id) {
		return groupsDAO.DeleteGroup(group_id);
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

        String username = "";
        if(sessionManager.currentUser != null)
        	username = sessionManager.currentUser.username.get();
        
        String process = request.getParameter("p");
        String group_id = request.getParameter("group_id");        
        String group_name = request.getParameter("group_name");
        
        String grproles =  request.getParameter("grpRoleList");
        String[] grpRoleList = null;
        if(grproles != null) {
        	if(grproles.trim() != "") {
        		String[] grpRoleStrList = grproles.split(",");
            	grpRoleList = new String[grpRoleStrList.length];
                for(int i1 = 0; i1< grpRoleStrList.length; i1++)
            	{
            		grpRoleList[i1] = grpRoleStrList[i1];
            	}
        	}
        }

        String members =  request.getParameter("memberList");
        String[] memberList = null;
        if(members != null) {
        	String[] memStrList = members.split(",");
        	memberList = new String[memStrList.length];
        	for(int i1 = 0; i1< memStrList.length; i1++)
        	{
        		memberList[i1] = memStrList[i1];
        	}
        }

        try (PrintWriter out = response.getWriter()) {
        	
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		groupsDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.AdministrationAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			switch(process)
                    {
                        case "getAllGroups":
                        	out.print(this.getAllGroups());
                        	break;
                        case "getGroup":
                        	out.print(this.getGroup(Integer.parseInt(group_id)));
                        	break;
                        case "getEnrolledGroups":
                        	out.print(this.getEnrolledGroups(username));
                        	break;
                        case "getUnenrolledGroups":
                        	out.print(this.getUnenrolledGroups(username));
                        	break;
                        case "createNewGroup":
                        	out.print(this.createNewGroup(group_name, grpRoleList, memberList));
                        	break;
                        case "updateGroup":
                        	out.print(this.updateGroup(Integer.parseInt(group_id), group_name, grpRoleList, memberList));
                        	break;
                        case "deleteGroup":
                        	out.print(this.deleteGroup(Integer.parseInt(group_id)));
                        	break;
                        case "getGroupEnrolledUsers":
                        	out.print(this.getGroupEnrolledUsers(Integer.parseInt(group_id)));
                        	break;
                        case "getGroupUnEnrolledUsers":
                        	out.print(this.getGroupUnEnrolledUsers(Integer.parseInt(group_id)));
                        	break;
                        case "getGroupEnrolledRoles":
                        	out.print(this.getGroupEnrolledRoles(Integer.parseInt(group_id)));
                        	break;
                        case "getGroupUnEnrolledRoles":
                        	out.print(this.getGroupUnEnrolledRoles(Integer.parseInt(group_id)));
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
