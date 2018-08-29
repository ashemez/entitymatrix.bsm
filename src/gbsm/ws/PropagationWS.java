/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gbsm.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gizit.managers.ConnectionManager;
import com.google.gson.Gson;
import com.sybase.jdbc3.jdbc.Convert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebService(serviceName = "PropagationWS")
public class PropagationWS {
	
	ConnectionManager connectionManager;
	public PropagationWS() {
		connectionManager = new ConnectionManager();
	}

	String err = "";
    Connection conn;
    private void OpenConn() throws IOException
    {
    	conn = connectionManager.GetSMDBConnection();
    }
    
    private void CloseConn()
    {
    	connectionManager.CloseConnection(conn);
    }
    
    private String GetParentList(int sid)
    {
    	err = "";

    	String parentList = "";
        try {
			OpenConn();
	        String q = "select parent_instance_id from smdbadmin.service_instance_relations";
	        q += " where service_instance_id=?";
	        PreparedStatement st = conn.prepareStatement(q);
	        st.setInt(1, sid);
	        ResultSet rs = st.executeQuery();

	        parentList = "{\"parentList\":[";
	        int itemcnt = 0;
	        while(rs.next()){
	        	if(itemcnt > 0)
	        		parentList += ",";
	        	parentList += "{\"sid\":" + rs.getString(1) + "}";
	        	itemcnt++;
	        }
	        parentList += "]}";
	        
	        rs.close();
	        st.close();
		} catch (IOException e1) {
			err = e1.getMessage();
		} catch (SQLException e) {
			err = e.getMessage();
		} finally {
			CloseConn();
		}

        return parentList;
    }

    @WebMethod(operationName = "getParentList")
    public String getParentList(@WebParam(name = "sid") int sid) {
    	String parentList = GetParentList(sid);
    	if(err != "")
    		return err;
    	return parentList;
    }

    private String GetParentChildrenAndGroups(int sid)
    {
    	err = "";

    	String children = "";
        try {
			OpenConn();
	        String q = "select i.service_instance_id, i.service_instance_name, g.group_name, g.node_group_id, g.bad_weight, i.current_status from smdbadmin.service_instances i";
	        q = q + " left join smdbadmin.service_instance_relations r on(r.service_instance_id=i.service_instance_id)";
	        q = q + " left join smdbadmin.node_group g on(g.node_group_id=r.node_group_id)";
	        q = q + " where r.parent_instance_id=?";
	        q = q + " order by g.node_group_id asc";
	        PreparedStatement st = conn.prepareStatement(q);
	        st.setInt(1, sid);
	        ResultSet rs = st.executeQuery();

	        children = "[";
	        int itemcnt = 0;
	        while(rs.next()){
	        	if(itemcnt > 0)
	        		children += ",";
	        	children += "[";
	        	children += rs.getString(1) + ",";
	        	children += "\"" + rs.getString(2) + "\",";
	        	children += "\"" + rs.getString(3) + "\",";
	        	children += rs.getString(4) + ",";
	        	children += rs.getString(5) + ",";
	        	children += rs.getString(6);
	        	children += "]";
	        	itemcnt++;
	        }
	        children += "]";
	        
	        rs.close();
	        st.close();
			CloseConn();
		} catch (IOException e1) {
			err = e1.getMessage();
		} catch (SQLException e) {
			err = e.getMessage();
		}

        return children;
    }

    @WebMethod(operationName = "getParentChildrenAndGroups")
    public String getParentChildrenAndGroups(@WebParam(name = "sid") int sid) {
    	String parentList = GetParentChildrenAndGroups(sid);
    	if(err != "")
    		return err;
    	return parentList;
    }
    
    private String GetOutputRule(int sid)
    {
    	err = "";

    	String cond = "";
        try {
			OpenConn();
	        String q = "select condition from smdbadmin.output_rules where parent_instance_id=?";
	        PreparedStatement st = conn.prepareStatement(q);
	        st.setInt(1, sid);
	        ResultSet rs = st.executeQuery();

	        while(rs.next()){
	        	cond = "{";
	        	cond += "\"condition\":\"" + rs.getString(1) +"\"";
	        	cond += "}";
	        }
	        
	        rs.close();
	        st.close();
			CloseConn();
		} catch (IOException e1) {
			err = e1.getMessage();
		} catch (SQLException e) {
			err = e.getMessage();
		}

        return cond;
    }

    @WebMethod(operationName = "getOutputRule")
    public String getOutputRule(@WebParam(name = "sid") int sid) {
    	String cond = GetOutputRule(sid);
    	if(err != "")
    		return err;
    	return cond;
    }
    
    private void UpdateNodeStatus(int sid, int status)
    {
    	err = "";
        try {
			OpenConn();
	        String q = "update smdbadmin.service_instances set current_status = ?, ";
	        q += " status_timestamp=EXTRACT(EPOCH FROM (select current_timestamp))  where service_instance_id = ?";
	        PreparedStatement st = conn.prepareStatement(q);
	        st.setInt(1, status);
	        st.setInt(2, sid);
			st.executeUpdate();
			st.close();
			
			CloseConn();
		} catch (IOException e1) {
			err = e1.getMessage();
		} catch (SQLException e) {
			err = e.getMessage();
		}
    }
    
    @WebMethod(operationName = "updateNodeStatus")
    public String updateNodeStatus(@WebParam(name="sid") int sid, @WebParam(name="status") int status) {
    	UpdateNodeStatus(sid, status);
    	if(err != "")
    		return err;
    	return "success";
    }

}
