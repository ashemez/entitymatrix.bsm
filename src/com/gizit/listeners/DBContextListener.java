package com.gizit.listeners;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.gizit.managers.SMDataSource;
import com.zaxxer.hikari.HikariDataSource;

public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	System.out.println("Starting up the SM server");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	System.out.println("Stopping the SM server");
    	try {
			InitialContext initialContext = new InitialContext();
			Context context = (Context) initialContext.lookup("java:comp/env");
			//HikariDataSource ds = (HikariDataSource) context.lookup("connpool");
			//DataSource ds = (DataSource) context.lookup("connpool");
	    	
			if(!SMDataSource.getInstance().getBds().isClosed()) {
				try {
					System.out.println("SMDB connection pool being closed!");
					SMDataSource.getInstance().getBds().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        } catch (NamingException e) {
        	//LOG.Error(e);
        }
    }
}