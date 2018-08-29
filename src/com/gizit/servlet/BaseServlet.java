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

import com.gizit.managers.LogManager;
import com.gizit.managers.ResourceManager;
import com.gizit.managers.SessionManager;


//@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/gbsm/ws/ChartWS.wsdl")

//@WebServlet(urlPatterns = {"/BaseServlet"})
public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public BaseServlet() {
		
	}

	ResourceManager RM = new ResourceManager();
	SessionManager sessionManager = new SessionManager();
	SessionManager.LoginState loginState;

}
