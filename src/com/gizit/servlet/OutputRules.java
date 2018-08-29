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

import com.gizit.bsm.dao.OutputRulesDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/OutputRulesWS.wsdl")

@WebServlet(urlPatterns = {"/OutputRules"})
public class OutputRules extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(OutputRules.class);
	private OutputRulesDAO orDAO = new OutputRulesDAO();

	private String GetNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(int parentSid) {
		return orDAO.GetNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(parentSid);
	}
	private String saveOutputRule(int parentSid, String sqlcondition) {
		return orDAO.SaveOutputRule(parentSid, sqlcondition);
    }
	private String getOutputRule(int parentSid) {
		return orDAO.GetOutputRule(parentSid);
    }
	private String deleteOutputRule(int parentSid) {
		return orDAO.DeleteOutputRule(parentSid);
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
        String sqlcondition = request.getParameter("condition");
        String parentSid = request.getParameter("parentSid");
        
        try (PrintWriter out = response.getWriter()) {
        	
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		orDAO.setUsername(sessionManager.currentUser.username.get());
        		switch(process)
                {
                    case "saveOutputRule":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			sqlcondition = sqlcondition.replaceAll("%20", " ");
                			sqlcondition = sqlcondition.replaceAll("\\$", " ");
                	    	sqlcondition = sqlcondition.replaceAll("~", "=");
                			out.print(saveOutputRule(Integer.parseInt(parentSid), sqlcondition));
                		}
                    	break;
                    case "getOutputRule":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getOutputRule(Integer.parseInt(parentSid)));
                		}
                    	break;
                    case "deleteOutputRule":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(deleteOutputRule(Integer.parseInt(parentSid)));
                		}
                    	break;
                    case "GetNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup":
                    	if(!sessionManager.auth.permission.EditSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(GetNodeGroupsAndNonGroupedNodesOfCurrentFunctionalGroup(Integer.parseInt(parentSid)));
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
    
    // reflection
    /*
     *
            Object[] params = new Object[3];
            java.lang.reflect.Method method;
            try {
              method = this.getClass().getMethod(process);
              method.invoke(null, params);
            }
            catch (SecurityException e) {  }
            catch (NoSuchMethodException e) {  }
            catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     */

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
