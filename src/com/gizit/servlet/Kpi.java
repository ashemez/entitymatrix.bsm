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

import com.gizit.bsm.dao.KPIDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/OutputRulesWS.wsdl")

@WebServlet("/Kpi")
public class Kpi extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private KPIDAO kpiDAO = new KPIDAO();
	
	LogManager LOG = new LogManager(Kpi.class);
	
	private String saveKPI(String name,int sid,String query,int interval,String timeUnit,String thresholdMarginalRule,String thresholdBadRule,int dsid, String username) {
		return kpiDAO.SaveKPI(name, sid, query, interval, timeUnit, thresholdMarginalRule, thresholdBadRule, dsid, username);
    }
	private String getKPI(int kpiId) {
		return kpiDAO.GetKPI(kpiId);
    }
	private String getKPIList(String username, boolean isAdmin) {
		return kpiDAO.GetKPIList(username, isAdmin);
    }
	private String getSrvKPIListForTable(int sid) {
		return kpiDAO.GetKPIListForTable(sid);
    }
	private String deleteKPI(int kpiId) {
		return kpiDAO.DeleteKPI(kpiId);
    }
	private String updateRunKpi(int kpiId, int isrunning) {
		return kpiDAO.UpdateRunKpi(kpiId,isrunning);
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
        String name = request.getParameter("name");
        String query = request.getParameter("query");
        
        String thresholdMarginalRule = request.getParameter("thresholdMarginalRule");
        String thresholdBadRule = request.getParameter("thresholdBadRule");
        
        String interval = request.getParameter("interval");
        String timeUnit = request.getParameter("timeunit");
        String kpiId = request.getParameter("kpiId");
        String sid = request.getParameter("sid");
        String dsid = request.getParameter("dsid");
        String isrunning = request.getParameter("isrunning");

        try (PrintWriter out = response.getWriter()) {
            
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		kpiDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		switch(process)
                {
                    case "saveKPI":
                    	if(!sessionManager.auth.permission.EditKPIAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			name = name.replaceAll("%20", " ").trim();
                			
                			thresholdMarginalRule = thresholdMarginalRule.replaceAll("greater", ">");
                	    	thresholdMarginalRule = thresholdMarginalRule.replaceAll("less", "<");
                	    	thresholdMarginalRule = thresholdMarginalRule.replaceAll("equal", "=");
                	    	
                	    	thresholdBadRule = thresholdBadRule.replaceAll("greater", ">");
                	    	thresholdBadRule = thresholdBadRule.replaceAll("less", "<");
                	    	thresholdBadRule = thresholdBadRule.replaceAll("equal", "=");
                	    	
                	    	query = query.replaceAll("%20", " ").replaceAll("~", "=");
                	    	
                	    	timeUnit = timeUnit.replaceAll("%20", " ");
                	    	
                			out.print(saveKPI(name,
                					Integer.parseInt(sid),
                					query,
                					Integer.parseInt(interval),
                					timeUnit,
                					thresholdMarginalRule,
                					thresholdBadRule,
                					Integer.parseInt(dsid), sessionManager.currentUser.username.get()));
                		}
                    	break;
                    case "getKPI":
                    	if(!sessionManager.auth.permission.ViewKPIAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getKPI(Integer.parseInt(kpiId)));
                		}
                    	break;
                    case "getKPIList":
                    	if(!sessionManager.auth.permission.ViewKPIAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getKPIList(sessionManager.currentUser.username.get(), sessionManager.auth.permission.AdministrationAuthorized()));
                		}
                    	break;
                    case "deleteKPI":
                    	if(!sessionManager.auth.permission.EditKPIAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(deleteKPI(Integer.parseInt(kpiId)));
                		}
                    	break;
                    case "updateRunKpi":
                    	if(!sessionManager.auth.permission.EditKPIAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(updateRunKpi(Integer.parseInt(kpiId), Integer.parseInt(isrunning)));
                		}
                    	break;
                    case "getSrvKPIListForTable":
                    	out.print(getSrvKPIListForTable(Integer.parseInt(sid)));
                    	break;
                    case "isEditKPIAuthorized":
                    	out.print(sessionManager.auth.permission.EditKPIAuthorized());
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
