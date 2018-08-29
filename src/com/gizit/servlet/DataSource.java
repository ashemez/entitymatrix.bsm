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

import com.gizit.bsm.dao.DataSourceDAO;
import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/OutputRulesWS.wsdl")

@WebServlet(urlPatterns = {"/DataSource"})
public class DataSource extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private DataSourceDAO dsDAO = new DataSourceDAO();
	
	LogManager LOG = new LogManager(DataSource.class);
	
	private String saveDS(String name, String dstype, String host, String port, String dbname, String username, String pw) {
		return dsDAO.SaveDS(name, dstype, host, port, dbname, username, pw);
    }
	private String getDS(int dsid) {
		return dsDAO.GetDS(dsid);
    }
	private String getDSList() {
		return dsDAO.GetDSList();
    }
	private String deleteDS(int dsid) {
		return dsDAO.DeleteDS(dsid);
    }
	private String getDSKPIQueryColumn(int dsid, String query) {
		return dsDAO.GetDSKPIQueryColumn(dsid,query);
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
        String dstype = request.getParameter("dstype");
        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String dbname = request.getParameter("dbname");
        String username = request.getParameter("username");
        String pw = request.getParameter("pw");
        String query = request.getParameter("query");
        String dsid = request.getParameter("dsid");

        try (PrintWriter out = response.getWriter()) {
            
        	String requestedEndPoint = "Requested endpoint: " + process;
        	switch(loginState)
        	{
        	case loggedin:
        		dsDAO.setUsername(sessionManager.currentUser.username.get());
	            switch(process)
	            {
	                case "saveDS":
	                	if(!sessionManager.auth.permission.EditDatasourceAuthorized()) {
	                		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(saveDS(name.replaceAll("%20", " ").trim(),
                					dstype.replaceAll("%20", ""),
                					host.replaceAll("%20", "").trim(),
                					port.replaceAll("%20", " ").trim(),
                					dbname.replaceAll("%20", " ").trim(),
                					username.replaceAll("%20", " ").trim(),
                					pw.replaceAll("%20", " ")));
                		}
	                	break;
	                case "getDS":
	                	if(!sessionManager.auth.permission.ViewDatasourceAuthorized()) {
	                		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getDS(Integer.parseInt(dsid)));
                		}
	                	break;
	                case "getDSList":
	                	if(!sessionManager.auth.permission.ViewDatasourceAuthorized()) {
	                		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getDSList());
                		}
	                	break;
	                case "deleteDS":
	                	if(!sessionManager.auth.permission.EditDatasourceAuthorized()) {
	                		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(deleteDS(Integer.parseInt(dsid)));
                		}
	                	break;
	                case "getDSKPIQueryColumn":
	                	if(!sessionManager.auth.permission.ViewDatasourceAuthorized()) {
	                		LOG.Warn(RM.GetErrorString("WARN_NOT_AUTHORIZED") + " " + requestedEndPoint);
                    		out.print(LOG.resultBean.Serialize());
                		} else {
                			out.print(getDSKPIQueryColumn(Integer.parseInt(dsid), query.replaceAll("%20", " ")));
                		}
	                	break;
	                case "isEditDSAuthorized":
	                	out.print(sessionManager.auth.permission.EditDatasourceAuthorized());
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
