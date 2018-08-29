package com.gizit.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizit.bsm.beans.NodeGroupBean;
import com.gizit.bsm.beans.NodeGroupBean.Member;
import com.gizit.bsm.dao.NodeGroupDAO;
import com.gizit.bsm.dao.OutputRulesDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/NodeGroupsWS.wsdl")

@WebServlet(urlPatterns = {"/NodeGroups"})
public class NodeGroups extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(NodeGroups.class);
	
	private NodeGroupDAO nodeGroup = new NodeGroupDAO();
	private OutputRulesDAO or = new OutputRulesDAO();
	
	private String createNodeGroup(String groupName, double badweight, int[] members, int parentSid) {
		NodeGroupBean ngBean = NodeGroupBean.CreateBean(groupName, badweight, members, parentSid);
		return nodeGroup.CreateNodeGroup(ngBean);
    }
	private String updateNodeGroup(int groupId, String groupName, double badweight, int[] members, int parentSid) {
		NodeGroupBean ngBean = NodeGroupBean.CreateBean(groupName, badweight, members,parentSid);
		return nodeGroup.UpdateNodeGroup(ngBean);
    }
	private String getNodeGroup(int groupId) {
		return nodeGroup.GetNodeGroup(groupId);
    }
	private String getNodeGroupsOfCurrentFunctionalGroup(int parentSid) {
		return nodeGroup.GetNodeGroupsOfCurrentFunctionalGroup(parentSid);
    }
	private String deleteNodeGroup(int groupId) {
		return nodeGroup.DeleteNodeGroup(groupId);
    }
	private String getNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(int parentSid) {
		return or.GetNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(parentSid);
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

        String process = request.getParameter("p");
        String groupName = request.getParameter("groupName");
        String badweight = request.getParameter("badweight");
        String parentSid = request.getParameter("parentSid");
        String groupId = request.getParameter("groupId");
        
        String[] memberList = (request.getParameter("members") != null) ? request.getParameter("members").split(",") : null;
        int [] members = null;
        if(memberList != null) {
        	members = new int[memberList.length];
        	for(int i1 = 0; i1< memberList.length; i1++)
        	{
        		members[i1] = Integer.parseInt(memberList[i1]);
        	}
        }

        try (PrintWriter out = response.getWriter()) {
            
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		nodeGroup.setUsername(sessionManager.currentUser.username.get());
        		or.setUsername(sessionManager.currentUser.username.get());
        		switch(process)
                {
                    case "createNodeGroup":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(createNodeGroup(groupName, Double.parseDouble(badweight), members, Integer.parseInt(parentSid)));
                		}
                    	break;
                    case "getNodeGroupsOfCurrentFunctionalGroup":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getNodeGroupsOfCurrentFunctionalGroup(Integer.parseInt(parentSid)));
                		}
                    	break;
                    case "getNodeGroup":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getNodeGroup(Integer.parseInt(groupId)));
                		}
                    	break;
                    case "updateNodeGroup":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(updateNodeGroup(Integer.parseInt(groupId), groupName, Double.parseDouble(badweight), members,Integer.parseInt(parentSid)));
                		}
                    	break;
                    case "deleteNodeGroup":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(deleteNodeGroup(Integer.parseInt(groupId)));
                		}
                    	break;
                    case "getNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(Integer.parseInt(parentSid)));
                		}
                    	break;
                    default:
                    	LOG.Warn(RM.GetErrorString("WARN_INVALID_ENDPOINT") + " " + requestedEndPoint);
                    	out.print(LOG.resultBean.Serialize());
                        break;
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
