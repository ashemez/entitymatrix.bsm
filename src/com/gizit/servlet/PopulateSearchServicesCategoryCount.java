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
@WebServlet("/PopulateSearchServicesCategoryCount")
public class PopulateSearchServicesCategoryCount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PopulateSearchServicesCategoryCount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SessionManager sessionManager = new SessionManager();
		SessionManager.LoginState loginState = sessionManager.CheckServletSession(request.getSession(), response);
		
		 int [] srvidlist = null;
	        if(request.getParameter("srvidlist") != null) {
	        	String[] srvidFilterList = request.getParameter("srvidlist").split(",");
	        	srvidlist = new int[srvidFilterList.length];
	        	for(int i = 0; i< srvidFilterList.length; i++)
	        	{
	        		srvidlist[i] = Integer.parseInt(srvidFilterList[i]);
	        	}
	        }
		
		switch(loginState)
    	{
    	case loggedin:
    		List<Integer> categoryCountList = new ArrayList<>();
    	    categoryCountList = ServiceFetchData.getSearchServicesCategoryCount(srvidlist);
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
