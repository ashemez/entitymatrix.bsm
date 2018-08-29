package com.gizit.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import com.gizit.bsm.beans.Service;
import com.gizit.bsm.dao.ServiceFetchData;
import com.gizit.managers.SessionManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class PopulateServicesBoxes
 */
@WebServlet("/PopulateServicesBoxes")
public class PopulateServicesBoxes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopulateServicesBoxes() {
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
		
		String id = request.getQueryString();
		int page = 1;
		int [] statusFilter = new int[1];
		if(id != null)
		{
			String[] qstr = id.split("&");

	        for(int i=0; i<qstr.length; i++)
	        {
	            String[] kv = qstr[i].split("=");
	            if(kv[0].equals("page"))
	            {
	            	page =  Integer.parseInt(kv[1]);
	            }
	            else if(kv[0].equals("statusFilter"))
	            {
	            	String[] statusFilterList =  kv[1].split(",");
	            	statusFilter = new int[statusFilterList.length];
	            	for(int i1 = 0; i1< statusFilterList.length; i1++)
	            	{
	            		statusFilter[i1] = Integer.parseInt(statusFilterList[i1]);
	            	}
	            }
	        }
		}
		
		page--;
		
		switch(loginState)
    	{
    	case loggedin:
    		ArrayList<Service> services=new ArrayList<Service>();
    		services=ServiceFetchData.getAllServices(page,statusFilter,sessionManager.auth.permission.ServicePermissions, spattern.trim(), sessionManager.auth.permission.AdministrationAuthorized());
    		Gson gson = new Gson();
    		JsonElement element = gson.toJsonTree(services, new TypeToken<List<Service>>() {}.getType());

    		JsonArray jsonArray = element.getAsJsonArray();
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().print(jsonArray);
    		break;
    	case notloggedin:
    		//response.getWriter().print("Error: You have to login!");
    		response.sendRedirect("/login.jsp");
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
