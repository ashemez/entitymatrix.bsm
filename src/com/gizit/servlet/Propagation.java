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
import javax.xml.ws.WebServiceRef;

import gbsm.ws.PropagationWS;

//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/PropagationWS.wsdl")

@WebServlet(urlPatterns = {"/Propagation"})
public class Propagation extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PropagationWS propagationWS = new PropagationWS();
	// --
	
	private String getParentList(int sid) {
		return propagationWS.getParentList(sid);
    }
	private String getParentChildrenAndGroups(int sid) {
		return propagationWS.getParentChildrenAndGroups(sid);
	}
	private String getOutputRule(int sid) {
		return propagationWS.getOutputRule(sid);
	}
	private String updateNodeStatus(int sid, int status) {
		return propagationWS.updateNodeStatus(sid, status);
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
        
        String id = request.getQueryString();
        String[] qstr = id.split("&");
        
        
        String process = "";
        int sid = 0;
        int status = 0;
        
        for(int i=0; i<qstr.length; i++)
        {
            String[] kv = qstr[i].split("=");
            
            switch(kv[0])
            {
	            case "p":
	            	process = kv[1];
	            	break;
	            case "sid":
	            	sid =  Integer.parseInt(kv[1]);
	            case "status":
	            	status =  Integer.parseInt(kv[1]);
            }
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            switch(process)
            {
                case "getParentList":
                	out.print(getParentList(sid));
                	break;
                case "getParentChildrenAndGroups":
                	out.print(getParentChildrenAndGroups(sid));
                	break;
                case "getOutputRule":
                	out.print(getOutputRule(sid));
                	break;
                case "updateNodeStatus":
                	out.print(updateNodeStatus(sid, status));
                	break;
                default:
                    out.print("Error: Invalid endpoint request!");
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
