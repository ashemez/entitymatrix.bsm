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

import com.gizit.bsm.dao.TreeDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/TreeOpsWS.wsdl")

@WebServlet(urlPatterns = {"/TreeOps"})
public class TreeOps extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(TreeOps.class);
	
	private TreeDAO treeDAO = new TreeDAO();
	
	private String addNewNode(int parentid, String nodeName, String citype) {
		return treeDAO.AddNewNode(parentid, nodeName, citype);
    }
	
	private String renameNode(int sid, String nodeName) {
		return treeDAO.RenameNode(sid, nodeName);
	}
	
	private String deleteNode(int sid) {
		return treeDAO.DeleteNode(sid);
	}
	
	private String updateNodeStatus(int sid, int status) {
		return treeDAO.UpdateNodeStatus(sid, status);
	}
    
	private String getNodeParentList(int sid) {
		return treeDAO.GetNodeParentList(sid);
	}
	
	private String getNodeSid(String nodeName) {
		return treeDAO.GetNodeSid(nodeName);
	}
	
	private String updateNodeOutputWeights(int sid, String compmodel, int badbad, int badmarginal, int marginalbad, int marginalmarginal, int parentweight, int parentsid) {
		return treeDAO.UpdateNodeOutputWeights(sid, compmodel, badbad, badmarginal, marginalbad, marginalmarginal, parentweight, parentsid);
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
        String parentid = request.getParameter("parentid");
        String sid = request.getParameter("sid");
        String nodeName = request.getParameter("nodeName");
        String citype = request.getParameter("citype");
        String status = request.getParameter("status");
        String compmodel = request.getParameter("compmodel");
        String badbad = request.getParameter("badbad");
        String badmarginal = request.getParameter("badmarginal");
        String marginalbad = request.getParameter("marginalbad");
        String marginalmarginal = request.getParameter("marginalmarginal");
        String parentweight = request.getParameter("relationweight");
        String parentsid = request.getParameter("parentsid");
        
        try (PrintWriter out = response.getWriter()) {
            
        	String requestedEndPoint = "Requested endpoint: " + process;
        	
        	try {

	        	switch(loginState)
	        	{
	        	case loggedin:
	        		treeDAO.setUsername(sessionManager.currentUser.username.get());
	        		
	        		switch(process)
	                {
	                    case "newnode":
	                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(addNewNode(Integer.parseInt(parentid), nodeName.replaceAll("%20", " ").trim(), citype.replaceAll("%20", " ").trim()));
	                		}
	                    	break;
	                    case "renamenode":
	                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(renameNode(Integer.parseInt(sid), nodeName.replaceAll("%20", " ").trim()));
	                		}
	                    	break;
	                    case "deletenode":
	                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(deleteNode(Integer.parseInt(sid)));
	                		}
	                    	break;
	                    case "setstatus":
	                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(updateNodeStatus(Integer.parseInt(sid), Integer.parseInt(status)));
	                		}
	                    	break;
	                    case "parentlist":
	                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(getNodeParentList(Integer.parseInt(sid)));
	                		}
	                    	break;
	                    case "getNodeSid":
	                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(getNodeSid(nodeName.replaceAll("%20", " ").trim()));
	                		}
	                    	break;
	                    case "updateNodeOutputWeights":
	                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
	                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
	                    		out.print(LOG.resultBean.Serialize());
	                		} else {
	                			out.print(this.updateNodeOutputWeights(Integer.parseInt(sid.trim()),
	                					compmodel.trim(),
	                					Integer.parseInt(badbad.trim()),
	                					Integer.parseInt(badmarginal.trim()),
	                					Integer.parseInt(marginalbad.trim()),
	                					Integer.parseInt(marginalmarginal.trim()),
	                					Integer.parseInt(parentweight.trim()),
	                					Integer.parseInt(parentsid.trim())));
	                		}
	                    	break;
	                    case "isEditServiceAuthorized":
		                	out.print(sessionManager.auth.permission.EditSrvResourceAuthorized());
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

        	} catch(Exception e) {
            	LOG.Warn(RM.GetErrorString("ERR") + ": " + e.getClass().getName() + " " + e.getMessage());
            	out.print(LOG.resultBean.Serialize());
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
