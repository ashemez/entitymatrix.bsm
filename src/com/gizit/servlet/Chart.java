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

import com.gizit.bsm.dao.ChartDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

@WebServlet(urlPatterns = {"/Chart"})
public class Chart extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private ChartDAO chartDAO = new ChartDAO();
	
	LogManager LOG = new LogManager(Chart.class);
	
	private String getAvailabilityMetric(int sid, String queryType) {
		return chartDAO.GetAvailabilityMetric(sid, 0, 0, queryType);
    }
	private String getAvailabilityMetricList(int[] sidlist, String queryType) {
		return chartDAO.GetAvailabilityMetricList(sidlist, 0, 0, queryType);
    }
	private String getChart(int sid, String queryType) {
		//return chartDAO.GetWeeklyChart(sid, weekCount);
		return chartDAO.GetChart(sid, 0, 0, queryType);
    }
	private String lastDayAvailability(int sid) {
		return chartDAO.LastDayAvailability(sid);
	}
	private String lastDayAvailabilityList(int[] sidlist) {
		return chartDAO.LastDayAvailabilityList(sidlist);
	}
	private String getStatusPieChart() {
		return chartDAO.GetStatusPieChart();
	}
	private String getLastSevenDayChartAllServices() {
		return chartDAO.GetLastSevenDayChartAllServices();
	}
	private String getLastMonthAvailChartAllServicesPieChart(String queryType) {
		return chartDAO.GetLastMonthAvailChartAllServicesPieChart(queryType);
	}
	private String getTopTenChart() {
		return chartDAO.GetTopTenChart();
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
        String queryType = request.getParameter("queryType");
        String sid = request.getParameter("sid");
        String weekCount = request.getParameter("weekCount");
        String sids = request.getParameter("sidlist");

        try (PrintWriter out = response.getWriter()) {

        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		chartDAO.setUsername(sessionManager.currentUser.username.get());
        		
        		if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
        			LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
            		out.print(LOG.resultBean.Serialize());
        		} else {
        			switch(process)
                    {
	                    case "getAvailabilityMetric":
	                    	out.print(getAvailabilityMetric(Integer.parseInt(sid), queryType));
	                    	break;
	                    case "getAvailabilityMetricList":
	                    	String[] sidStrList2 = sids.split(",");
	                    	LOG.Debug(sids);
                            int[] sidlist2 = new int[sidStrList2.length];
                        	for(int i1 = 0; i1< sidStrList2.length; i1++)
                        	{
                        		sidlist2[i1] = Integer.parseInt(sidStrList2[i1]);
                        	}
	                    	out.print(getAvailabilityMetricList(sidlist2, queryType));
	                    	break;
                        case "getChart":
                        	//out.print(getWeeklyChart(Integer.parseInt(sid), Integer.parseInt(weekCount)));
                        	out.print(getChart(Integer.parseInt(sid), queryType));
                        	break;
                        case "lastDayAvailability":
                        	out.print(lastDayAvailability(Integer.parseInt(sid)));
                        	break;
                        case "lastDayAvailabilityList":
                        	String[] sidStrList = sids.split(",");
                            int[] sidlist = new int[sidStrList.length];
                        	for(int i1 = 0; i1< sidStrList.length; i1++)
                        	{
                        		sidlist[i1] = Integer.parseInt(sidStrList[i1]);
                        	}
                        	out.print(lastDayAvailabilityList(sidlist));
                        	break;
                        case "getStatusPieChart":
                        	out.print(getStatusPieChart());
                        	break;
                        case "getLastSevenDayChartAllServices":
                        	out.print(getLastSevenDayChartAllServices());
                        	break;
                        case "getLastMonthAvailChartAllServicesPieChart":
                        	out.print(getLastMonthAvailChartAllServicesPieChart(queryType));
                        	break;
                        case "getTopTenChart":
                        	out.print(getTopTenChart());
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
