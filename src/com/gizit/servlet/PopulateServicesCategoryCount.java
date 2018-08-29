package com.gizit.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizit.bsm.dao.ServiceFetchData;
import com.gizit.managers.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class PopulateServicesCategoryCount
 */
@WebServlet("/PopulateServicesCategoryCount")
public class PopulateServicesCategoryCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopulateServicesCategoryCount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SessionManager sessionManager = new SessionManager();
		SessionManager.LoginState loginState = sessionManager.CheckServletSession(request.getSession(), response);
		
		String spattern = request.getParameter("spattern");
		if(spattern == null)
			spattern = "";
		
		switch(loginState)
    	{
    	case loggedin:
    		List<Integer> categoryCountList = new ArrayList<>();
    	    categoryCountList = ServiceFetchData.getServicesCategoryCount(sessionManager.auth.permission.ServicePermissions, spattern.trim(), sessionManager.auth.permission.AdministrationAuthorized());
    	    String json = new Gson().toJson(categoryCountList);

    	    response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(json);
    		break;
    	case notloggedin:
    		response.getWriter().print("Error: You have to login!");
    		break;
    	}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
