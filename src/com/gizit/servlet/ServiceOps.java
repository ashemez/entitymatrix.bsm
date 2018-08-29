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

import com.gizit.bsm.dao.ReportDAO;
import com.gizit.bsm.dao.ServiceImportDAO;
import com.gizit.bsm.helpers.Helper;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

@WebServlet(urlPatterns = {"/ServiceOps"})
public class ServiceOps extends BaseServlet {

	private static final long serialVersionUID = 1L;

	LogManager LOG = new LogManager(ServiceOps.class);
	
	ServiceImportDAO srvImportDAO = new ServiceImportDAO();

	private String ImportServiceThroughMxmRest(String mainServiceName, String url) {		
		//srvImportDAO.ImportServiceThroughMxmRest(mainServiceName, url);
		return "";
	}
	
	// --- individual node operations
	
	private String AddServiceNode(String nodeName, String parentNodeName, String citype) {
		return "";
	}

	private String DeleteServiceNode(String nodeName) {
		// are you sure?
		return "";
	}
	
	private String UploadBulkServiceList() {
		return "";
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
        String report_id = request.getParameter("report_id");
        String name = request.getParameter("name");
        String startingDate = request.getParameter("startingDate");
        String endingDate = request.getParameter("endingDate");
        String getOutageDetails = request.getParameter("getOutageDetails");
        String queryType = request.getParameter("queryType");

        //LOG.Debug("*** startingDate: " + startingDate + ", endingDate: " + endingDate);
        try (PrintWriter out = response.getWriter()) {

        	//out.print(srvImportDAO.GetRestXmlOut(""));
        	
        	/*String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		//reportDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.ViewReportAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			boolean getdetails = false;
        			switch(process)
                    {
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
        	}*/
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
