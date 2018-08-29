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

import com.gizit.bsm.dao.RolesDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;


//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/ChartWS.wsdl")

@WebServlet(urlPatterns = {"/Roles"})
public class Roles extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(Roles.class);

	private RolesDAO rolesDAO = new RolesDAO();
	
	private String getAllRoles() {
		return rolesDAO.GetAllRoles();
    }
	private String getRole(int role_id) {
		return rolesDAO.GetRole(role_id);
    }
	private String getEnrolledRoles(String username) {
		return rolesDAO.GetEnrolledRoles(username);
	}
	private String getUnenrolledRoles(String username) {
		return rolesDAO.GetUnenrolledRoles(username);
	}
	private String getRoleResourcePermissions(int role_id) {
		return rolesDAO.GetRoleResourcePermissions(role_id);
	}
	private String getRoleServicePermissions(int role_id) {
		return rolesDAO.GetRoleServicePermissions(role_id);
	}
	private String createNewRole(String role_name, String[] srvPermList, String[] resPermList) {
		return rolesDAO.CreateNewRole(role_name, srvPermList, resPermList);
	}
	private String getRoleEnrolledUsers(int role_id) {
		return rolesDAO.GetRoleEnrolledUsers(role_id);
	}
	private String getRoleUnEnrolledUsers(int role_id) {
		return rolesDAO.GetRoleUnEnrolledUsers(role_id);
	}
	private String getRoleUnEnrolledUsersSearch(int role_id, String pattern) {
		return rolesDAO.GetRoleUnEnrolledUsers(role_id, pattern);
	}
	private String getRoleEnrolledGroups(int role_id) {
		return rolesDAO.GetRoleEnrolledGroups(role_id);
	}
	private String getRoleUnEnrolledGroups(int role_id) {
		return rolesDAO.GetRoleUnEnrolledGroups(role_id);
	}
	private String getRoleEnrolledServices(int role_id) {
		return rolesDAO.GetRoleEnrolledServices(role_id);
	}
	private String getRoleUnEnrolledServices(int role_id) {
		return rolesDAO.GetRoleUnEnrolledServices(role_id);
	}
	private String updateRole(int role_id, String role_name, String[] srvPermList, String[] resPermList) {
		return rolesDAO.UpdateRole(role_id, role_name, srvPermList, resPermList);
	}
	private String updateRoleMembers(int role_id, String[] groupList, String[] userList) {
		return rolesDAO.UpdateRoleMembers(role_id, groupList, userList);
	}
	private String getRoleAfterInsert(String role_name) {
		return rolesDAO.GetRoleAfterInsert(role_name);
	}
	private String deleteRole(int role_id) {
		return rolesDAO.DeleteRole(role_id);
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
        String role_name = request.getParameter("role_name");
        String role_id = request.getParameter("role_id");
        String searchPattern = request.getParameter("searchPattern");

        String srvperm = request.getParameter("srvPermList");
        String [] srvPermList = null;
        if(srvperm != null && srvperm != "")
        {
        	String[] srvperms = srvperm.split(",");
        	srvPermList = new String[srvperms.length];
        	for(int i1 = 0; i1< srvPermList.length; i1++)
        	{
        		srvPermList[i1] = srvperms[i1];
        	}
        }
        else {
        	srvPermList = new String[0];
        }
        
        String resperm = request.getParameter("resPermList");
        String [] resPermList = null;
        if(resperm != null && resperm != "") {
        	String[] resperms =  resperm.split(",");
        	resPermList = new String[resperms.length];
        	for(int i1 = 0; i1< resPermList.length; i1++)
        	{
        		resPermList[i1] = resperms[i1];
        	}
        }
        else {
        	resPermList = new String[0];
        }
        
        String grplist = request.getParameter("groupList");
        String [] groupList = null;
        if(grplist != null && grplist != "")
        {
        	String[] groups = grplist.split(",");
        	groupList = new String[groups.length];
        	for(int i1 = 0; i1< groupList.length; i1++)
        	{
        		groupList[i1] = groups[i1];
        	}
        }
        else {
        	groupList = new String[0];
        }
    	
        String usrlist = request.getParameter("userList");
        String [] userList = null;
        if(usrlist != null && usrlist != "")
        {
        	String[] users =  usrlist.split(",");
        	userList = new String[users.length];
        	for(int i1 = 0; i1< userList.length; i1++)
        	{
        		userList[i1] = users[i1];
        	}
        }
        else {
        	userList = new String[0];
        }
        try (PrintWriter out = response.getWriter()) {
        	
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		rolesDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.AdministrationAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			switch(process)
                    {
                        case "getAllRoles":
                        	out.print(getAllRoles());
                        	break;
                        case "getRole":
                        	out.print(getRole(Integer.parseInt(role_id)));
                        	break;
                        case "getEnrolledRoles":
                        	out.print(getEnrolledRoles(username));
                        	break;
                        case "getUnenrolledRoles":
                        	out.print(getUnenrolledRoles(username));
                        	break;
                        case "getRoleResourcePermissions":
                        	out.print(getRoleResourcePermissions(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleServicePermissions":
                        	out.print(getRoleServicePermissions(Integer.parseInt(role_id)));
                        	break;
                        case "createNewRole":
                        	out.print(createNewRole(role_name, srvPermList, resPermList));
                        	break;
                        case "getRoleEnrolledUsers":
                        	out.print(getRoleEnrolledUsers(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleUnEnrolledUsers":
                        	out.print(getRoleUnEnrolledUsers(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleUnEnrolledUsersSearch":
                        	searchPattern = searchPattern.replaceAll("%20", " ").trim();
                        	out.print(getRoleUnEnrolledUsersSearch(Integer.parseInt(role_id), searchPattern));
                        	break;
                        case "getRoleEnrolledGroups":
                        	out.print(getRoleEnrolledGroups(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleUnEnrolledGroups":
                        	out.print(getRoleUnEnrolledGroups(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleEnrolledServices":
                        	out.print(getRoleEnrolledServices(Integer.parseInt(role_id)));
                        	break;
                        case "getRoleUnEnrolledServices":
                        	out.print(getRoleUnEnrolledServices(Integer.parseInt(role_id)));
                        	break;                        	
                        case "updateRole":
                        	out.print(updateRole(Integer.parseInt(role_id), role_name, srvPermList, resPermList));
                        	break;
                        case "updateRoleMembers":
                        	out.print(this.updateRoleMembers(Integer.parseInt(role_id), groupList, userList));
                        	break;
                        case "getRoleAfterInsert":
                        	out.print(this.getRoleAfterInsert(role_name));
                        	break;
                        case "deleteRole":
                        	out.print(this.deleteRole(Integer.parseInt(role_id)));
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
