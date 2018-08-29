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

import com.gizit.bsm.dao.AlarmDAO;
import com.gizit.bsm.dao.ServiceStatDAO;
import com.gizit.managers.LogManager;

@WebServlet(urlPatterns = {"/AlarmOps"})
public class AlarmOps extends BaseServlet {

	private static final long serialVersionUID = -3775604503385925947L;
	
	LogManager LOG = new LogManager(AlarmOps.class);

	private AlarmDAO alarmDAO = new AlarmDAO();
	
    private String getAlarmList(int sid) {
        return alarmDAO.getAlarmList(sid);
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

        try (PrintWriter out = response.getWriter()) {
        	String requestedEndPoint = "Requested endpoint: " + process;

        	switch(loginState)
        	{
        	case loggedin:
        		alarmDAO.setUsername(sessionManager.currentUser.username.get());
        		switch(process)
                {
                    case "getAlarmList":
                    	if(!sessionManager.auth.permission.ViewSrvResourceAuthorized()) {
                    		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getAlarmList(Integer.parseInt(sid)));
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
