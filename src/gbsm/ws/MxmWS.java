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

import com.gizit.bsm.tree.TreeStructure;
import com.gizit.bsm.tree.TreeStructure.Node;
import com.gizit.managers.ConnectionManager;
import com.google.gson.Gson;
import com.sybase.jdbc3.jdbc.Convert;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebService(serviceName = "MxmWS")
public class MxmWS {

	ConnectionManager connectionManager;
	public MxmWS() {
		connectionManager = new ConnectionManager();
	}
	
	String err = "";
    Connection conn;
    Connection mxmConn;
    private void OpenConn() throws IOException
    {
    	conn = connectionManager.GetSMDBConnection();
    }
    
    private void CloseConn()
    {
    	connectionManager.CloseConnection(conn);
    }
    
    private void OpenMxmConn() throws IOException
    {
        try
        {
			Properties prop = new Properties();
			InputStream inputStream = MxmWS.class.getClassLoader().getResourceAsStream("/mxmdb.properties");
			prop.load(inputStream);
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String password = prop.getProperty("password");
			Class.forName(driver);
			mxmConn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MxmWS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MxmWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void CloseMxmConn()
    {
        try {
        	mxmConn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MxmWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void InitSrvImport()
    {
    	//String q = "select cinum, ciname from ci where classstructureid=105175 and cinum='OI-2FB40C6AE5FE435A8CBF36B70B6A8CAB'";
    	String q = "select cinum, ciname from ci where classstructureid=105175 and status='OPERATING' and cinum like 'OI-%'";
		try {
			Statement st = mxmConn.createStatement();
			
			ResultSet ccr = st.executeQuery(q);
	        
			int i = 0;
			while(ccr.next()){
				String srvName = ccr.getString(2);
				int parentsid = GetServiceID(srvName);
				if(parentsid == 0)
				{
				   String tmpSrv = srvName.replaceAll("'", "''");
				   String ciq = "insert into smdbadmin.service_instances(service_instance_name, service_instance_displayname, current_status, citype, propagate, status_timestamp)";
				   ciq = ciq + " values('" + tmpSrv + "', '" + tmpSrv + "', 0, 'CI.BUSINESSMAINSERVICE', 0, 0)";
			       Statement st111 = conn.createStatement();
			       st111.executeUpdate(ciq);
			       parentsid = GetServiceID(srvName);
			       st111.close();
			    }

				//System.out.println(i + " ***** CURRENT MAIN SERVICE: " + parentsid);
				if(!usedSidList.containsKey(parentsid)) {
					//System.out.println(i + " ***** PROCESSING MAIN SERVICE: " + parentsid);
					ImportSrv(parentsid, ccr.getString(1), "105175");
				} else {
					//System.out.println(i + " ***** ALREADY PROCESSED MAIN SERVICE: " + parentsid);
				}
				
				i++;
			}
	    	
	    	ccr.close();
	    	st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			err = e.getMessage();
		}
        
    }
    
    @WebMethod(operationName = "ImportMxmSrv")
    public String ImportMxmSrv() {
    	
		try {
			//OpenMxmConn();
			OpenConn();
			
	    	//InitSrvImport();
	    	//System.out.println("Done dune");
	    	
	    	//CloseMxmConn();
	    	
	    	// scan tree
	    	TScanAllServices();
	    	System.out.println("Done dune 2");
	    	CloseConn();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			err = e.getMessage();
		}
		
    	return err;
    }
    
    TreeStructure ts;
    private void TScanAllServices() {
    	try {
	    	String q = "SELECT service_instance_id from smdbadmin.service_instances where citype='CI.BUSINESSMAINSERVICE'";
			PreparedStatement st = conn.prepareStatement(q);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				TInitServiceTree(rs.getInt(1));
				TTSBuild(ts.rootNode);
			}
	        
	        st.close();
	        rs.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void TInitServiceTree(int sid) {
    	ts = new TreeStructure();
    	try {
			ts.AddRoot(sid, GetServiceName(sid), GetServiceStatus(sid), GetServiceType(sid));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void TTSBuild(Node parentNode) {
		try {
	        if(!parentNode.canHaveChild)
	        	DisablePropagation(parentNode.sid);

			String q = "SELECT service_instance_id from smdbadmin.service_instance_relations where parent_instance_id=?";
			PreparedStatement stc = conn.prepareStatement(q);
			stc.setInt(1, parentNode.sid);
	        ResultSet rs = stc.executeQuery();
	        
	        while(rs.next()) {
	        	int childSid = rs.getInt(1);

	        	Node cnode = ts.AddChild(parentNode, childSid, GetServiceName(childSid), GetServiceStatus(childSid), GetServiceType(childSid));
	        	if(cnode != null) {
	        		TTSBuild(cnode);
	        	}
	        }

	        rs.close();
	        stc.close();
		} catch (SQLException e) {
			err = e.getMessage();
		}
    }
    
    private void DisablePropagation(int sid)
    {
    	String q = "update smdbadmin.service_instances set propagate=2 where service_instance_id=?";
		try {
			PreparedStatement st = conn.prepareStatement(q);
			st.setInt(1, sid);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private String GetServiceName(int serviceID) throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT service_instance_name from smdbadmin.service_instances where service_instance_id=?");
        st.setInt(1, serviceID);
        ResultSet rs1 = st.executeQuery();
        rs1.next();
        String name = rs1.getString(1);
        rs1.close();
        st.close();
        
        return name;
    }
    
    private String GetServiceType(int serviceID) throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT citype from smdbadmin.service_instances where service_instance_id=?");
        st.setInt(1, serviceID);
        ResultSet rs1 = st.executeQuery();
        rs1.next();
        String name = rs1.getString(1);
        rs1.close();
        st.close();
        
        return name;
    }
    
    private int GetServiceStatus(int serviceID) throws SQLException
    {
        PreparedStatement st = conn.prepareStatement("SELECT current_status from smdbadmin.service_instances where service_instance_id=?");
        st.setInt(1, serviceID);
        ResultSet rs1 = st.executeQuery();
        rs1.next();
        int status = rs1.getInt(1);
        rs1.close();
        st.close();
        
        return status;
    }
    
    private int GetServiceID(String _ciname){
    	   int _sid = 0;
    	   _ciname = _ciname.replaceAll("'", "''");
    	   String q = "select service_instance_id from smdbadmin.service_instances where service_instance_name='" + _ciname + "'";
    	   try {
    		   Statement st = conn.createStatement();
    		   ResultSet ccr = st.executeQuery(q);

			if(ccr.next())
	    	      _sid = ccr.getInt(1);
			
			ccr.close();
			st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
    	   return _sid; 
    }
    
    HashMap<Integer, Integer> usedSidList = new HashMap<Integer, Integer>();
    private void ImportSrv(int parentSID, String cinum, String citype)
    {
    	try {
    		usedSidList.put(parentSID, 1);
		    // get sourcetype, parenttype and relationtype from relation type mapping table
		    // and insert into service_instances and service_instance_relations
		    String psq = "select sourcetype, targettype, relationtype from VFCITBSMMAPPING where parenttype=" + citype;
	        Statement st = mxmConn.createStatement();
	        ResultSet psr = st.executeQuery(psq);
		        
	         while(psr.next()){
	        	 
	            // select children from cirelation according to ci-type mapping
	            boolean queryThis = true; 

	            String rq = "select cir.relationnum, cir.sourceci sourcecinum, cir.targetci targetcinum, scl.classstructureid sourceclass, tcl.classstructureid targetclass, sci.ciname sourceciname, tci.ciname targetciname, tcl.classificationid targetcitype, scl.classificationid sourcecitype ";
	            rq = rq + " from cirelation cir, ci sci, ci tci, classstructure scl, classstructure tcl";
	            rq = rq + " where cir.sourceci=sci.cinum and cir.targetci=tci.cinum and sci.classstructureid=scl.classstructureid and tci.classstructureid=tcl.classstructureid";
	            rq = rq + " and cir.relationnum='" + psr.getString(3) + "'";
	            if(citype.equals(psr.getString(1))){ 
	               rq = rq + " and cir.sourceci='" + cinum + "' and scl.classstructureid=" + citype + " and tcl.classstructureid=" + psr.getString(2);
	            } else if(citype.equals(psr.getString(2))){
	               rq = rq + " and cir.targetci='" + cinum + "' and scl.classstructureid=" + psr.getString(1) + " and tcl.classstructureid=" + citype; 
	            } else {
	               queryThis = false;
	            }
	            
	            if(cinum.equals("OI-0677C5A4A995448A802C404003A791A0"))
	            	System.out.println(rq);
	            if(queryThis){
			        Statement st1 = mxmConn.createStatement();
			        ResultSet cir = st1.executeQuery(rq);

	                  // insert into service_instances if does not exist
	                  // insert into service_instance_relations if does not exist
	                  while(cir.next()){
	                     String parentName = cir.getString(6);
	                     String childName = cir.getString(7);
	                     String childCITYPE = cir.getString(8);
	                     String childClassStruct = cir.getString(5);
	                     String childCINUM = cir.getString(3);
	                     
	                     if(citype.equals(psr.getString(2))){
	                        parentName = cir.getString(7);
	                        childName = cir.getString(6);
	                        childCITYPE = cir.getString(9); 
	                        childClassStruct = cir.getString(4);
	                        childCINUM = cir.getString(2); 
	                     }

	                     
	                     if(parentName.equals("MAXIMO APP")) {
	                    	 System.out.println(parentName + " ** " + childName + ", childCITYPE " + childCITYPE);
	                     }

	                     if(citype.equals("105017")) {
	                    	 //System.out.println("105017 ***** parentName: " + parentName + ", childName:" + childName);
	                     }

	                     int childSID = GetServiceID(childName);

	                     if(childSID > 0){
	                        String srq = "select count(*) cnt from smdbadmin.service_instance_relations where parent_instance_id=" + parentSID + " and service_instance_id=" + childSID;
	                        Statement st2 = conn.createStatement();
					        ResultSet srrr = st2.executeQuery(srq);
					        srrr.next();
	                        if(srrr.getInt(1) == 0){
	                           String sriq = "insert into smdbadmin.service_instance_relations(service_instance_id, parent_instance_id, node_group_id)";
	                           sriq = sriq + " values(" + childSID + "," + parentSID + ",0)";
	                           Statement st3 = conn.createStatement();
	                           st3.executeUpdate(sriq);
	                           
	                           st3.close();
	                        }
	                        
	                        //System.out.println("**** CHILD INSERTED, PARENT RELATION: " + childSID + " ** " + parentSID + " **** childName:" + childName);
	                        //System.out.println(rq);
	                        srrr.close();
	                        st2.close();
	                     } else {
	                    	String tmpChild = childName.replaceAll("'", "''");
	                        String ciq = "insert into smdbadmin.service_instances(service_instance_name, service_instance_displayname, current_status, citype, propagate, status_timestamp)";
	                        ciq = ciq + " values('" + tmpChild + "', '" + tmpChild + "', 0, '" + childCITYPE + "', 0, 0)";
	                        Statement st4 = conn.createStatement();
	                        st4.executeUpdate(ciq);
	                        childSID = GetServiceID(childName);
	                        st4.close();
	 
	                        String sriq = "insert into smdbadmin.service_instance_relations(service_instance_id, parent_instance_id, node_group_id)";
	                        sriq = sriq + " values(" + childSID + "," + parentSID + ",0)";
	                        Statement st5 = conn.createStatement();
	                        st5.executeUpdate(sriq);
	                        st5.close();
	                        
	                        //System.out.println("**** CHILD INSERTED, PARENT RELATION: " + childSID + " ** " + parentSID + " **** childName:" + tmpChild + ", parentName: " + parentName + " *** " + rq);
	                     }

	                     if(!usedSidList.containsKey(childSID))
	                    	 ImportSrv(childSID, childCINUM, childClassStruct);

	                  }
	                  
	                  cir.close();
	                  st1.close();
	            }

	         }
	         
	         psr.close();
	         st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


}
