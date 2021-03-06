package com.gizit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class login extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		System.out.println("reqmethod: " + request.getMethod());

		// restore original URL after successful authorization
		String originalRequestUrl = null;
		if(session.getAttribute("_login_original_request_url") != null)
			originalRequestUrl = session.getAttribute("_login_original_request_url").toString();
		
		if (originalRequestUrl != null && originalRequestUrl.length() > 0) {
			session.removeAttribute("_login_original_request_url");
			response.sendRedirect(originalRequestUrl);
		}
    }
}
