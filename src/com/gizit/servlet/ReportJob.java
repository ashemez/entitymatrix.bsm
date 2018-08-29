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

import com.gizit.bsm.dao.ReportJobDAO;
import com.gizit.bsm.helpers.Helper;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

@WebServlet(urlPatterns = {"/ReportJob"})
public class ReportJob extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private ReportJobDAO reportJobDAO = new ReportJobDAO();
	
	LogManager LOG = new LogManager(ReportJob.class);
	
	private String getReportJob(int report_job_id) {
		return reportJobDAO.GetReportJob(report_job_id);
	}
	private String getReportJobList(String username, boolean isAdmin) {
		return reportJobDAO.GetReportJobList(username, isAdmin);
    }
	private String saveReportJob(int report_job_id, String report_job_name, int sendmail, String addresslist, String cron, int recurrent, int isenabled, int[] reportList, String subject, String message, String username) {
		return reportJobDAO.SaveReportJob(report_job_id, report_job_name, sendmail, addresslist, cron, recurrent, isenabled, reportList, subject, message, username);
	}
	private String deleteReportJob(int report_job_id) {
		return reportJobDAO.DeleteReportJob(report_job_id);
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
        if(process == null) process = "";
        String report_job_id = request.getParameter("report_job_id");
        if(report_job_id == null)
        	report_job_id = "0";
        String report_job_name = request.getParameter("report_job_name");
        String sendmail = request.getParameter("sendmail");
        String addresslist = request.getParameter("addresslist");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String cron = request.getParameter("cron");
        String recurrent = request.getParameter("recurrent");
        String isenabled = request.getParameter("isenabled");
        String[] reportList = (request.getParameter("reportList") != null) ? request.getParameter("reportList").split(",") : null;
        int [] reports = null;
        if(reportList != null) {
        	reports = new int[reportList.length];
        	for(int i1 = 0; i1< reportList.length; i1++)
        	{
        		reports[i1] = Integer.parseInt(reportList[i1]);
        	}
        }

        try (PrintWriter out = response.getWriter()) {

        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		reportJobDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.ViewReportAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			switch(process)
                    {
                        case "getReportJobList":
                        	out.print(getReportJobList(sessionManager.currentUser.username.get(), sessionManager.auth.permission.AdministrationAuthorized()));
                        	break;
                        case "saveReportJob":
                        	report_job_name = report_job_name.replaceAll("%20", " ");
                        	cron = cron.replaceAll("%20", " ");
                        	addresslist = addresslist.replaceAll("%20", " ");
                        	subject = subject.replaceAll("%20", " ");
                        	message = message.replaceAll("%20", " ");
                        	out.print(saveReportJob(Integer.parseInt(report_job_id), report_job_name, Integer.parseInt(sendmail), addresslist, cron, Integer.parseInt(recurrent), Integer.parseInt(isenabled), reports, subject, message, sessionManager.currentUser.username.get()));
                        	break;
                        case "getReportJob":
                        	out.print(getReportJob(Integer.parseInt(report_job_id)));
                        	break;
                        case "deleteReportJob":
                        	out.print(deleteReportJob(Integer.parseInt(report_job_id)));
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
