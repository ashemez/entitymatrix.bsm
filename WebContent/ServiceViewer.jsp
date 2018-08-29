<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.gizit.bsm.beans.UserBean"%>
<%@page import="com.gizit.managers.SessionManager"%>
<% 
	//UserBean currentUser = (UserBean) session.getAttribute("currentSessionUser");
	SessionManager sessionManager = new SessionManager();
	sessionManager.CheckSession(session, response);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<link rel="icon" type="image/png" href="resource/img/favicon_bsview.ico">
  <title>Business Service View</title>
  <meta http-equiv="Content-Type"  content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="resource/css/boatstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyLato.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyMontserrat.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/multi-select.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/vodafonebsm.css" rel="stylesheet" type="text/css">
  <link href="resource/css/d3tree/d3-context-menu.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/d3tree/editor.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-toggle.css" rel="stylesheet" type="text/css" />
  
  <!-- Query Building Css -->
  <link href="resource/css/query_builder/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-slider.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/selectize.bootstrap3.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/awesome-bootstrap-checkbox.css" rel="stylesheet" type="text/css" />
  <!-- <link href="resource/css/query_builder/query-builder.default.min.css" rel="stylesheet" type="text/css" /> -->
  <link href="resource/css/query_builder/query-builder.dark.min.css" rel="stylesheet" type="text/css" />

  <script src="resource/js/jquery/jquery.min.js"></script> 
  <script src="resource/js/boatstrap/bootstrap.min.js"></script>
  <script src="resource/js/d3tree/d3.v3.min.js"></script>
  <script src="resource/js/jquery/jquery.dataTables.min.js"></script>
  <%
	out.print("<script>");
	if (request.getParameter("msbval") == null) {
		out.print("var minisideval=0;");
		out.print("console.log(minisideval);");
	} else {
	 	out.print("var minisideval = " + request.getParameter("msbval") + ";");
	  	out.print("console.log(minisideval);");
	}
	if (request.getParameter("lgval") == null) {
		out.print("var listgridvalue=1;");
		out.print("console.log(listgridvalue);");
	} else {
	 	out.print("var listgridvalue = " + request.getParameter("lgval") + ";");
	  	out.print("console.log(listgridvalue);");
	}
	if (request.getParameter("page") == null) {
		out.print("var srvBoxPageNumber = 1;");
    } else {
    	out.print("var srvBoxPageNumber = " + request.getParameter("page") + ";");
    }
	if (request.getParameter("spattern") == null) {
		out.print("var spattern = '';");
    } else {
    	out.print("var spattern = '" + request.getParameter("spattern") + "';");
    }
	
	if (request.getParameter("statusFilter") == null) {
		out.print("var statusFilter = [0,3,5];");
    } else {
    	out.print("var statusFilter = [" + request.getParameter("statusFilter") + "];");
    }
	out.print("</script>");
  %>
  <script src="resource/js/jquery/jquery.multi-select.js"></script>
  <script src="resource/js/boatstrap/bootstrap-toggle.js"></script>
  <script src="resource/js/bsm.js"></script>
  <script src="resource/js/vodafonebsm.js"></script>
  <script src="resource/js/service.js"></script>
  <script src="resource/js/d3tree/underscore-min.js"></script>
  <script src="resource/js/d3tree/d3-context-menu.js"></script>
  <script src="resource/js/d3tree/dndTree.js"></script>
  <script src="resource/js/d3tree/d3.tip.v0.6.3.js"></script>
  <script src="resource/js/boatstrap/bootstrapValidator.js"></script>
  <script src="resource/js/boatstrap/bootstrap-multiselect.js"></script>
  <script src="resource/js/boatstrap/bootstrap-toggle.js"></script>
  <script src="resource/js/Chart.js"></script>
  <script src="resource/js/jquery/jquery.twbsPagination.js"></script>

 <!-- Query Building js -->
 <script src="resource/js/query_builder/moment.min.js"></script> 
 <script src="resource/js/query_builder/bootstrap-datepicker.min.js"></script> 
 <script src="resource/js/query_builder/bootstrap-slider.min.js"></script> 
 <script src="resource/js/query_builder/selectize.min.js"></script> 
 <script src="resource/js/query_builder/bootstrap-select.min.js"></script>  
 <script src="resource/js/query_builder/sql-parser.js"></script>
<script src="resource/js/query_builder/query-builder.standalone.min.js"></script>
 
<script src="resource/js/outputrule.js"></script>
<script src="resource/js/kpidsqueryrule.js"></script>
<script src="resource/js/propagate_child2parent.js"></script>

<%
	if (request.getParameter("sid") == null) {
        // herror
    } else {
    	String sid = request.getParameter("sid");
    	
    	out.print("<script>");
    	out.print("var _ssid = " + sid + ";");
    	out.print("$( document ).ready(function() {");
		out.print("    getServiceTreeData(_ssid);");
		out.print("});");
        out.print("</script>");
    }
%>
<!--   currentUser.getFirstName() + " " + currentUser.getLastName()  %> -->
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="#">
                <img id="logo" src="resource/img/bsview_logo.png" alt="LOGO">
            </a>
			<!-- <p class="navbar-text">Service Manager</p> -->
        </div>
        <!-- Top Menu Items -->
		<div class="top-nav">
			<ul class="nav navbar-left">
				<li><a href="#" class="btn" id="sidebarCollapse"><span class="fa fa-bars"></span></a></li>
			</ul>
			<ul class="nav navbar-right">
				<li id="tooltip"><a href="#" data-placement="bottom" data-toggle="tooltip" href="#" data-original-title="Stats"><i class="fa fa-bar-chart-o"></i>
					</a>
				</li>
				<li class="dropdown">
				  <a href="#" class="dropdown-toggle" data-toggle="dropdown"> <%
				  if(sessionManager.currentUser != null)
				  	out.print(sessionManager.currentUser.firstName.get() + " " + sessionManager.currentUser.lastName.get());
				  %> <b class="fa fa-fw fa-user"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#" id="changePassword"><i class="fa fa-fw fa-cog"></i> Change Password</a></li>
						<li><a href="LogoutServlet"><i class="fa fa-fw fa-power-off"></i> Logout</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
		<div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar">
			<ul class="nav navbar-nav side-nav" id="sidebar-nav">
				<li id="mainmenu1">
					<a href="#" data-toggle="collapse" data-target="#submenu-1">
						<i id="image1" class="fa fa-fw fa-dashboard"></i> 
						<span id="text1">Dashboards</span> 
						<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
					</a>
					<ul id="submenu-1" class="collapse">
						<li id="li_bs"><a id="dash1" href="javascript:;"><i class="fa fa-circle-o"></i> Business Services</a></li>
						<li id="li_kpiover"><a id="dash2" href="javascript:;"><i class="fa fa-circle-o"></i> KPI Overall</a></li>
					</ul>
				</li>
				<li id="mainmenu2">
                    <a href="#" data-toggle="collapse" data-target="#submenu-2">
						<i id="image2" class="fa fa-fw fa-bar-chart"></i>  
						<span id="text2">Reporting</span> 
						<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
					</a>
                    <ul id="submenu-2" class="collapse">
                        <li id="rpt1_li"><a id="rpt1" href="javascript:;"><i class="fa fa-circle-o"></i> Reports</a></li>
                        <li id="rpt2_li"><a id="rpt2" href="javascript:;"><i class="fa fa-circle-o"></i> Report Jobs</a></li>
                    </ul>
                </li>
				<li id="mainmenu3">
                    <a href="#" data-toggle="collapse" data-target="#submenu-3">
						<i id="image3" class="fa fa-fw fa-calendar"></i>  
						<span id="text3">KPI Management</span> 
						<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
					</a>
                    <ul id="submenu-3" class="collapse">
                        <li id="li_ds"><a id="data_source" href="javascript:;"><i class="fa fa-circle-o"></i> Data Sources</a></li>
                        <li id="li_kpi"><a id="kpi" href="javascript:;"><i class="fa fa-circle-o"></i> KPIs </a></li>
                    </ul>
                </li>
                 <%
                	if(sessionManager.auth.permission.AdministrationAuthorized()){ 
                 %>
	                <li id="adminmenu">
	                    <a href="#" data-toggle="collapse" data-target="#admin_menu">
							<i id="image4" class="fa fa-fw fa-wrench"></i>
							<span id="text4">Administrator</span> 
							<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
						</a>
	                    <ul id="admin_menu" class="collapse">
	                        <li id="li_usr"><a href="javascript:;" id="user_source" ><i class="fa fa-circle-o"></i> Users</a></li>
	                        <li id="li_role"><a href="javascript:;" id="role_source"><i class="fa fa-circle-o"></i> Roles</a></li>
	                        <li id="li_grp"><a href="javascript:;" id="group_source"><i class="fa fa-circle-o"></i> Groups</a></li>
	                    </ul>
	                </li>
	             <%
                	}
                 %>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

	<!-- Page content -->
    <div id="page-content-wrapper">
        <div class="page-content">
            <div class="container-fluid col-md-12">
				<div id="contentheader" class="panel panel-default">
					<div class="panel-heading">Dashboards > Business Services</div>
				</div>
				<div id="contentpage">
					<div class="btn-group row">
						<button type="button" class="btn btn-success"><span class="fa fa-arrow-circle-left"></span><span>Back</span></button>
    				</div>
    				<!-- Switch Autozoom --> 
    				<label class="switch" id="switchautozoom">
  						<input type="checkbox" checked>
  						<span class="slider round"></span>
					</label>

					<div id="divtree" class="row" style="border:1px solid black;margin-top:20px;height:400px;">
        				<div id="tree-container"></div>
        				<!-- Alarm / KPI List -->
						<div id="alarm-kpi-list" class="collapse">
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#alarmlist_table" aria-controls="alarmlist_table" role="tab" data-toggle="tab">Alarm List</a></li>
							<li role="presentation"><a href="#kpilist_table" aria-controls="kpilist_table" role="tab" data-toggle="tab">KPI List</a></li>
						</ul>
						
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane fade in active" id="alarmlist_table">
								<table id="alarmtable" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">          
										<thead>
										<tr>
											<th>ServerSerial</th>
											<th>Manager</th>
											<th>Node</th>
											<th>Summary</th>
											<th>Severity</th>
											<th>FirstOccurrence</th>
											<th>LastOccurrence</th>
											<th>Service Name</th>
										</tr>
										</thead>
									</table>
							</div>
							<div role="tabpanel" class="tab-pane fade" id="kpilist_table">
									<table id="kpitable" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">          
										<thead>
										<tr>
											<th>KPI Name</th>
											<th>Service Name</th>
											<th>Measured Value</th>
											<th>Status</th>
											<th>Last Ran</th>
										</tr>
										</thead>
									</table>
							</div>
						</div>
						</div> <!-- Alarm/KPI List End -->       				
        			</div>
        			
        			<div id="loader" class="loader collapse" style="margin:0 auto;"></div>	
				</div>			
            </div>
        	<div class="collapse" id="rightsidebar">
			</div>
        </div>
    </div>
</div>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModel" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
      	<button type="button" class="close" data-dismiss="modal">x</button>
        <h5 class="modal-title" id="modalh5Title"><b>...</b> will be deleted. Are you sure ?</h5>
      </div>
      <div class="modal-body">
        <p>Do you really want to delete this record? This process cannot be undone.</p> 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button id="modaldeletebtn" type="button" class="btn btn-danger">Delete</button>
      </div>
    </div>
  </div>
</div>

<!-- Change Password Model -->
<div class="modal fade" id="changePassModal" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>
        <h5 class="modal-title" id="modalh5changePassTitle"><b>Change Password</b></h5>
      </div>
      <div class="modal-body">
        <form role="form" class="well form-horizontal" action="" method="post"  id="changepass_form">
        	<fieldset>
          		<div class="form-group">
            		<label class="col-md-4 control-label"> Current Password </label>
            		<div class="col-md-6 inputGroupContainer">
            			<div class="input-group">
            				<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
            				<input name="changePassOld" type="password" class="form-control" id="changePassOld" placeholder="Current Password">
          				</div>
          			</div>
          		</div>
          		<div class="form-group">
            		<label class="col-md-4 control-label"> New Password </label>
            		<div class="col-md-6 inputGroupContainer">
            			<div class="input-group">
            				<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
            				<input name="changePassNew" type="password" class="form-control" id="changePassNew" placeholder="New Password">
          				</div>
          			</div>
          		</div>
          		<div class="form-group">
            		<label class="col-md-4 control-label"> Confirm Password </label>
            		<div class="col-md-6 inputGroupContainer">
            			<div class="input-group">
            				<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
            				<input name="changePassNewRep" type="password" class="form-control" id="changePassNewRep" placeholder="Confirm Password">
          				</div>
          			</div>
          		</div>
				
				<!-- Success message -->
				<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i>Password Change Saved !!!</div>
				
				<div class="form-group">
	  				<label class="col-md-4 control-label"></label>
					<div class="col-md-4">
        				<button id="savePassChngBtn" type="submit" class="btn btn-warning"> Save <span class="glyphicon glyphicon-send"></span></button>
      				</div>
      			</div>         		
          	</fieldset>
        </form>
      </div>
      <div class="modal-footer">
	  </div>
    </div>
    
  </div>
</div>


</body>

</html>