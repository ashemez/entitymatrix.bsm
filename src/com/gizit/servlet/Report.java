package com.gizit.servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizit.bsm.dao.ReportDAO;
import com.gizit.bsm.helpers.Helper;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

@WebServlet(urlPatterns = {"/Report"})
public class Report extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private ReportDAO reportDAO = new ReportDAO();
	
	LogManager LOG = new LogManager(Report.class);
	
	private String getMonthlyReport(int sid, int startingDate, int endingDate, boolean getOutageDetails, String queryType) {
		return reportDAO.GetCalculatedOutageReportOfSingleService(sid, startingDate, endingDate, getOutageDetails, queryType);
    }
	private String getCalculatedOutageReportOfChildren(int sid, int startingDate, int endingDate, boolean getOutageDetails, String queryType) {
		return reportDAO.GetCalculatedOutageReportOfChildren(sid, startingDate, endingDate, getOutageDetails, queryType);
	}
	private String getReportOfServiceTree(int sid, int startingDate, int endingDate, boolean getOutageDetails, String queryType) {
		return reportDAO.GetReportOfServiceTree(sid, startingDate, endingDate, getOutageDetails, queryType);
	}
	private String createNewReport(int sid, int startingDate, int endingDate, boolean getOutageDetails, String queryType, String name, String username) {
		return reportDAO.CreateNewReport(sid, startingDate, endingDate, queryType, name, username);
	}
	private String getCreatedReportList(String username, List<Integer> groups, boolean isAdmin) {
		return reportDAO.GetCreatedReportList(username, groups, isAdmin);
	}
	private String getCreatedReportOutput(int report_id) {
		return reportDAO.GetCreatedReportOutput(report_id);
	}
	private String getMainServiceList() {
		return reportDAO.GetMainServiceList();
	}
	private String getMainServiceCount() {
		return reportDAO.GetMainServiceCount();
	}
	private String getAllServiceList(HashMap<Integer, String> serviceList, boolean isAdmin) {
		return reportDAO.GetAllServiceList(serviceList, isAdmin);
	}
	private String deleteReport(int report_id) {
		return reportDAO.DeleteReport(report_id);
	}
	private String updateReport(int sid, int startingDate, int endingDate, boolean getOutageDetails, String queryType, String name) {
		return reportDAO.UpdateReport(sid, startingDate, endingDate, queryType, name);
	}
	private String getReport(int report_id) {
		return reportDAO.GetReport(report_id);
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

        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		reportDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.ViewReportAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			boolean getdetails = false;
        			switch(process)
                    {
                        case "getCalculatedOutageReportOfSingleService":
                        	if(queryType == null)
                        		queryType = ReportDAO.QueryType.LAST_MONTH;
                        	
                        	if(startingDate == null)
                        		startingDate = Integer.toString(Helper.LastMonthStartingDate().intValue());
                        	if(endingDate == null) {
                        		int endOfMonth = (86400 * Helper.DayCountOfMonth(Integer.parseInt(startingDate)));
                        		endingDate = Integer.toString((Integer.parseInt(startingDate) + endOfMonth));
                        	}
                        	
                        	if(getOutageDetails == null)
                        		getOutageDetails = "0";
                        	
                        	if(getOutageDetails.equals("1"))
                        		getdetails = true;
                        	
                        	out.print(getMonthlyReport(Integer.parseInt(sid), Integer.parseInt(startingDate), Integer.parseInt(endingDate), getdetails, queryType));
                        	break;
                        case "getCalculatedOutageReportOfChildren":
                        	
                        	if(queryType == null)
                        		queryType = ReportDAO.QueryType.LAST_MONTH;
                        	
                        	if(startingDate == null)
                        		startingDate = Integer.toString(Helper.LastMonthStartingDate().intValue());
                        	if(endingDate == null) {
                        		int endOfMonth = (86400 * Helper.DayCountOfMonth(Integer.parseInt(startingDate)));
                        		endingDate = Integer.parseInt(startingDate) + Integer.toString(endOfMonth);
                        	}
                        	
                        	if(getOutageDetails == null)
                        		getOutageDetails = "0";
                        	
                        	if(getOutageDetails.equals("1"))
                        		getdetails = true;
                        	out.print(getCalculatedOutageReportOfChildren(Integer.parseInt(sid), Integer.parseInt(startingDate), Integer.parseInt(endingDate), getdetails, queryType));
                        	break;
                        case "getReportOfServiceTree":
                        	if(queryType == null)
                        		queryType = ReportDAO.QueryType.LAST_MONTH;
                        	
                        	if(startingDate == null)
                        		startingDate = Integer.toString(Helper.LastMonthStartingDate().intValue());
                        	if(endingDate == null) {
                        		int endOfMonth = (86400 * Helper.DayCountOfMonth(Integer.parseInt(startingDate)));
                        		endingDate = Integer.parseInt(startingDate) + Integer.toString(endOfMonth);
                        	}
                        	
                        	if(getOutageDetails == null)
                        		getOutageDetails = "0";
                        	
                        	if(getOutageDetails.equals("1"))
                        		getdetails = true;
                        	out.print(getReportOfServiceTree(Integer.parseInt(sid), Integer.parseInt(startingDate), Integer.parseInt(endingDate), getdetails, queryType));
                        	break;
                        case "createNewReport":
                        	if(!sessionManager.auth.permission.EditReportAuthorized()) {
                        		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                        		out.print(LOG.resultBean.Serialize());
                        	} else {
                        		if(queryType == null)
                            		queryType = ReportDAO.QueryType.LAST_MONTH;
                            	
                            	if(startingDate == null)
                            		startingDate = Integer.toString(Helper.LastMonthStartingDate().intValue());
                            	if(endingDate == null) {
                            		int endOfMonth = (86400 * Helper.DayCountOfMonth(Integer.parseInt(startingDate)));
                            		endingDate = Integer.parseInt(startingDate) + Integer.toString(endOfMonth);
                            	}
                            	
                            	if(getOutageDetails == null)
                            		getOutageDetails = "0";
                            	
                            	if(getOutageDetails.equals("1"))
                            		getdetails = true;
                            	
                            	name = name.trim();
                            	System.out.println("userid: " + sessionManager.currentUser.userid.get());
                            	out.print(createNewReport(Integer.parseInt(sid), Integer.parseInt(startingDate), Integer.parseInt(endingDate), getdetails, queryType, name, sessionManager.currentUser.username.get()));
                        	}
                        	break;
                        case "getCreatedReportOut":
                        	out.print(getCreatedReportOutput(Integer.parseInt(report_id)));
                        	break;
                        case "getCreatedReportList":
                        	System.out.println(sessionManager.auth.permission.AdministrationAuthorized());
                        	out.print(getCreatedReportList(sessionManager.currentUser.username.get(), sessionManager.auth.permission.Groups, sessionManager.auth.permission.AdministrationAuthorized()));
                        	break;
                        case "getMainServiceList":
                        	out.print(getMainServiceList());
                        	break;
                        case "getMainServiceCount":
                        	out.print(getMainServiceCount());
                        	break;
                        case "getAllServiceList":
                        	out.print(getAllServiceList(sessionManager.auth.permission.ServicePermissions, sessionManager.auth.permission.AdministrationAuthorized()));
                        	break;
                        case "getReport":
                        	out.print(getReport(Integer.parseInt(report_id)));
                        	break;
                        case "deleteReport":
                        	if(!sessionManager.auth.permission.EditReportAuthorized()) {
                        		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                        		out.print(LOG.resultBean.Serialize());
                        	} else {
                        		out.print(deleteReport(Integer.parseInt(report_id)));
                        	}
                        	break;
                        case "updateReport":
                        	if(!sessionManager.auth.permission.EditReportAuthorized()) {
                        		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                        		out.print(LOG.resultBean.Serialize());
                        	} else {
                        		out.print(updateReport(Integer.parseInt(sid), Integer.parseInt(startingDate), Integer.parseInt(endingDate), getdetails, queryType, name));
                        	}
                        	break;
                        case "isEditReportAuthorized":
                        	out.print(sessionManager.auth.permission.EditReportAuthorized());
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
