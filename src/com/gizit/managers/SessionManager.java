package com.gizit.managers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.gizit.bsm.beans.UserBean;
import gbsm.gizit.security.Authorization;

public class SessionManager { 

	LogManager LOG = new LogManager(SessionManager.class);
	public enum LoginState {
		loggedin,
		notloggedin
	}

	public Authorization auth;
	public UserBean currentUser = null;
	public void CheckSession(HttpSession session, HttpServletResponse response) {
		try {
			if(session.getAttribute("currentSessionUser") == null) {
				response.sendRedirect("/login");
			}
			else {
				currentUser = (UserBean) session.getAttribute("currentSessionUser");
				auth = new Authorization(currentUser.username.get());
			}
		}
		catch (IOException e) {
			LOG.Error(e);
		}
   }

	public LoginState CheckServletSession(HttpSession session, HttpServletResponse response) {
		if(session.getAttribute("currentSessionUser") == null) {
			return LoginState.notloggedin;
		}
		currentUser = (UserBean) session.getAttribute("currentSessionUser");
		auth = new Authorization(currentUser.username.get());
		return LoginState.loggedin;
	}

}