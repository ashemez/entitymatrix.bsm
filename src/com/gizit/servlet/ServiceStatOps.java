package com.gizit.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizit.bsm.dao.ServiceStatDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/wservice.wsdl")

@WebServlet(urlPatterns = {"/ServiceStatOps"})
public class ServiceStatOps extends BaseServlet {

	private static final long serialVersionUID = -3775604503385925947L;

	LogManager LOG = new LogManager(ServiceStatOps.class);
	
	private ServiceStatDAO serviceStatDAO = new ServiceStatDAO();
	
    private String getServiceStat(int sid) throws IOException {
        return serviceStatDAO.GetServiceStat(sid);
    }
    
    //private String getServiceTreeStat(int sid) throws IOException {
    private String getServiceTreeStat(String sidList) throws IOException {
        //return serviceStatDAO.getServiceTreeStatus(sid);
    	return serviceStatDAO.getServiceTreeStatusNew(sidList);
    }
    
    private String[] getServiceDiagram(int sid) throws IOException {
        return serviceStatDAO.ServiceTreeThroughBeans(sid);
    }
    
    private String getServiceCount(int[] statusFilter, HashMap<Integer, String> srvList, String spattern, boolean isAdmin) {
    	return serviceStatDAO.GetServiceCount(statusFilter, srvList, spattern, isAdmin);
    }
    
    private String TSCAN() {
    	return serviceStatDAO.TScanAllServices();
    }
    
    private String getNodeChildren(int sid) {
    	return serviceStatDAO.GetNodeChildren(sid);
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
        String sid = request.getParameter("sid");
        String sidList = request.getParameter("sidList");
        String spattern = request.getParameter("spattern");
        if(spattern == null)
        	spattern = "";
        
        int [] statusFilter = null;
        if(request.getParameter("statusFilter") != null) {
        	String[] statusFilterList = request.getParameter("statusFilter").split(",");
        	statusFilter = new int[statusFilterList.length];
        	for(int i1 = 0; i1< statusFilterList.length; i1++)
        	{
        		statusFilter[i1] = Integer.parseInt(statusFilterList[i1]);
        	}
        }
        
        try (PrintWriter out = response.getWriter()) {
            
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		serviceStatDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		switch(process)
                {
                    case "getServiceStat":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getServiceStat(Integer.parseInt(sid)));
                		}
                        break;
                    case "getServiceTreeStat":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			//out.print(getServiceTreeStat(Integer.parseInt(sid)));
                			out.print(getServiceTreeStat(sidList));
                		}
                        break;
                    case "getServiceDiagram":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			String[] srvDiagram = getServiceDiagram(Integer.parseInt(sid));
                			out.print("[" + srvDiagram[0] + ", " + srvDiagram[1] + "]");
                		}
                        break;
                    case "getServiceCount":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getServiceCount(statusFilter, sessionManager.auth.permission.ServicePermissions, spattern.trim(), sessionManager.auth.permission.AdministrationAuthorized()));
                		}
                    	break;
                    case "TSCAN":
                    	out.print(TSCAN());
                    	break;
                    case "getNodeChildren":
                    	out.print(getNodeChildren(Integer.parseInt(sid)));
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
