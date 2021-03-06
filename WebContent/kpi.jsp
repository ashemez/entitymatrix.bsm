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
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="resource/css/boatstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyLato.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyMontserrat.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/boatstrap/bootstrap-toggle.css" rel="stylesheet" type="text/css" /> 
  <link href="resource/css/multi-select.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/vodafonebsm.css" rel="stylesheet" type="text/css">
  <link href="resource/css/d3tree/d3-context-menu.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/d3tree/editor.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/chosen.css" rel="stylesheet" type="text/css" />
<!--  <link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet"> -->

  <!-- Query Building Css -->
  <link href="resource/css/query_builder/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-slider.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/selectize.bootstrap3.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/awesome-bootstrap-checkbox.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/query-builder.default.min.css" rel="stylesheet" type="text/css" />

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
	if (request.getParameter("page") == null) {
		out.print("var srvBoxPageNumber = 1;");
    } else {
    	out.print("var srvBoxPageNumber = " + request.getParameter("page") + ";");
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
  <script src="resource/js/jquery/chosen.jquery.js"></script>
  <script src="resource/js/bsm.js"></script>
  <script src="resource/js/vodafonebsm.js"></script>
  <script src="resource/js/kpi.js"></script>
  <script src="resource/js/d3tree/underscore-min.js"></script>
  <script src="resource/js/d3tree/d3-context-menu.js"></script>
  <script src="resource/js/d3tree/dndTree.js"></script>
  <script src="resource/js/d3tree/d3.tip.v0.6.3.js"></script>
  <script src="resource/js/boatstrap/bootstrapValidator.js"></script>
  <script src="resource/js/boatstrap/bootstrap-multiselect.js"></script>
  <script src="resource/js/Chart.js"></script>
  <script src="resource/js/jquery/jquery.twbsPagination.js"></script>
<!--    <script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script> -->
  

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
			<!--  <p class="navbar-text">Service Manager</p>-->
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
				<%
                	if(sessionManager.auth.permission.ViewReportAuthorized() || sessionManager.auth.permission.EditReportAuthorized()){ 
                %>
					<li id="mainmenu2">
	                    <a id="mainmenu2a" href="#" data-toggle="collapse" data-target="#submenu-2">
							<i id="image2" class="fa fa-fw fa-bar-chart"></i>  
							<span id="text2">Reporting</span> 
							<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
						</a>
	                    <ul id="submenu-2" class="collapse">
	                        <li id="rpt1_li"><a id="rpt1" href="javascript:;"><i class="fa fa-circle-o"></i> Reports</a></li>
	                        <li id="rpt2_li"><a id="rpt2" href="javascript:;"><i class="fa fa-circle-o"></i> Report Jobs</a></li>
	                    </ul>
	                </li>
                <% 
                	}
                %>
				<li id="mainmenu3">
                    <a id="mainmenu3a" href="#" data-toggle="collapse" data-target="#submenu-3">
						<i id="image3" class="fa fa-fw fa-calendar"></i>  
						<span id="text3">KPI Management</span> 
						<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
					</a>
                    <ul id="submenu-3" class="collapse">
                        <li id="kpi_li1"><a id="data_source" href="javascript:;"><i class="fa fa-circle-o"></i> Data Sources</a></li>
                        <li id="kpi_li2"><a id="kpi" href="javascript:;"><i class="fa fa-circle-o"></i> KPIs </a></li>
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
                <li id="menu4">
                    <a href="#"><i id="image4" class="fa fa-fw fa-paper-plane-o"></i> <span id="text4">MENU 4</span></a>
                </li>
                <li id="menu5">
                    <a href="#"><i id="image5" class="fa fa-fw fa fa-question-circle"></i><span id="text5">MENU 5</span></a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

	<!-- Page content -->
    <div id="page-content-wrapper">
        <div class="page-content">
            <div class="container-fluid col-md-12">
				<div id="contentheader" class="panel panel-default">
					<div class="panel-heading">KPI Management</div>
				</div>
				<div id="contentpage">

        			<div id="loader" class="loader collapse" style="margin:0 auto;"></div>
        			
        			<!-- DataSource Form ---- add datasource and list -->
					<div id="datasource_form" class="row collapse">
						<%
                		if(sessionManager.auth.permission.EditDatasourceAuthorized()){ 
                		%>
						<span id="datasource_form_def_open_close" title="Create New Datasource" class="btn btn-success fa fa-plus"></span>
						<%
                		}
                		%>
						<form class="well form-horizontal" action=" " method="post"  id="ds_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Create DataSource</legend>
								
								<!-- Text input DataSource Name-->		
								<div class="form-group">
									<label class="col-md-4 control-label">Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input  name="ds_name" placeholder="Datasource Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Select DataSource Type -->			
								<div class="form-group"> 
									<label class="col-md-4 control-label">Type</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select name="type" class="form-control selectpicker" >
												<option value=" " >Select Datasource type</option>
												<option>ORACLE</option>
												<option>SQLSERVER</option>
												<option>POSTGRESQL</option>
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text input Datasource Host name-->		
								<div class="form-group">
									<label class="col-md-4 control-label" >Host name</label> 
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="hostname" placeholder="Hostname" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
					
								<!-- Text input Datasource Port-->		
								<div class="form-group">
									<label class="col-md-4 control-label" >Port</label> 
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="port" placeholder="Port Number" class="form-control"  type="text">
										</div>
									</div>
								</div>			
								
								<!-- Text input Database Name-->
									
								<div class="form-group">
								<label class="col-md-4 control-label">Database Name</label>  
									<div class="col-md-4 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-database"></i></span>
								<input name="db_name" placeholder="Database Name" class="form-control" type="text">
									</div>
								</div>
								</div>
								
								<!-- Text input Database User name-->
								
								<div class="form-group">
								<label class="col-md-4 control-label">Username</label>  
									<div class="col-md-4 inputGroupContainer">
									<div class="input-group">
										<span class="input-group-addon"><i class="fa fa-user"></i></span>
								<input name="username" placeholder="Username" class="form-control"  type="text">
									</div>
								</div>
								</div>
								
								
								<!-- Password  area -->
								<div class="form-group">
									<label class="col-md-4 control-label">Password</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-lock"></i></span>
											<input type="password" class="form-control" placeholder="Password" name="password" />
										</div>
									</div>
								</div>
								
								<!-- Success message -->
								<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i> Entered Values Saved !!!</div>
								
								<!-- Button -->
								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning" >Save <span class="glyphicon glyphicon-send"></span></button>
									</div>
								</div>
							
							</fieldset>
						</form>
						<h2>DataSources</h2>  
						<div class="table-responsive">           
						<table id="existingdatasource" class="table">
							<thead>
							<tr class="bg-primary">
								<th>Name</th>
								<th>Type</th>
								<th>Host Name</th>
								<th>Port</th>
								<th>Database Name</th>
								<th>Username</th>
								<th>Action</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
						</div>
					</div>
					
					<!-- KPI Form --- add KPI and lists -->
					<div id="keypi_form" class="row collapse">
						<%
                		if(sessionManager.auth.permission.EditKPIAuthorized()){ 
                		%>
						<span id="kpi_form_def_open_close" title="Create New KPI" class="btn btn-success fa fa-plus"></span>
						<%
                		}
                		%>
						<form class="well form-horizontal" action=" " method="post" id="kpi_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Create KPI</legend>
								
								<!-- Text input DataSource Name-->		
								<div class="form-group">
									<label class="col-md-4 control-label">Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input  name="kpi_name" placeholder="KPI Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Select DataSource List -->			
								<div class="form-group"> 
									<label class="col-md-4 control-label">DataSource List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="ds_list_igroup">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select name="ds_list" id="keypi_ds_list" class="form-control selectpicker">
												<option id="defaultds" value="">Select DataSource</option>
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text area for KPI Query-->		
								<div class="form-group">
									<label class="col-md-4 control-label" >KPI Query</label> 
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<textarea class="form-control" name="kpi_query" placeholder="KPI Query"></textarea>
										</div>
									</div>
									<button id="run_query" class="btn btn-danger" type="button">Run Query!</button>
								</div>
								
								<!-- Text input for Time Unit-->
										
								<div class="form-group">
									<label class="col-md-4 control-label" >Time Unit</label> 
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<select name="timeunit" class="form-control selectpicker" >
												<option value=" " >Please select Time Unit</option>
												<!-- <option>Second</option>
												<option>Minute</option> -->
												<option>Hour</option>
												<option>Day</option>
											</select>
										</div>
									</div>
								</div>			
								
								<!-- Text input for Interval-->	
								<div class="form-group">
								<label class="col-md-4 control-label">Interval</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-database"></i></span>
											<input name="interval" placeholder="Interval" class="form-control" type="text">
										</div>
									</div>
								</div>
								
								<div class="form-group" id="kpi_select_service_list_div">
									<label class="col-md-4 control-label">Service</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group" id="kpi_select_service_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='kpi_srv_list' data-placeholder="Select a service" multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
					
								<!-- Text area for threshold marginal rule-->
								<div class="form-group">
									<label class="col-md-4 control-label">Threshold Marginal Rule</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-pencil"></i></span>
											<textarea id="thmrule" class="form-control" name="thresholdmarginal" placeholder="Threshold Marginal Rule"></textarea>
										</div>
									</div>
								</div>

								<!-- Text area for threshold bad rule-->
								<div class="form-group">
									<label class="col-md-4 control-label">Threshold Bad Rule</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-pencil"></i></span>
											<textarea id="thbrule" class="form-control" name="thresholdbad" placeholder="Threshold Bad Rule"></textarea>
										</div>
									</div>
								</div>

								<!-- Success message -->
								<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i>Entered Values Saved !!!</div>
								
								<!-- Button -->
								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning" >Save <span class="glyphicon glyphicon-send"></span></button>
									</div>
								</div>
							</fieldset>
						</form>
						<h2>KPIs</h2>
						<div class="table-responsive">           
						<table id="existingkpi" class="table">
							<thead>
							<tr class="bg-primary">
								<th>Name</th>
								<th>DataSource</th>
								<th>Interval</th>
								<th>Time Unit</th>
								<th>Action</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
						</div>
					</div>									
				</div>			
            </div>
        	<div class="collapse" id="rightsidebar">
			</div>
        </div>
    </div>
</div>

<!-- Save Modal -->
<div class="modal fade" id="saveModal" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
      	<button type="button" class="close" data-dismiss="modal">x</button>
        <h5 class="modal-title" id="savemodalh5Title">Information Message</h5>
      </div>
      <div class="modal-body">
        <p>All changes are saved!</p> 
      </div>
      <div class="modal-footer">
        <button id="savemodalbtn" type="button" class="btn btn-primary">OK</button>
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

<!-- Data Source Edit DataSource Model -->
<div class="modal fade" id="editDSModal" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>
        <h5 class="modal-title" id="modalh5editTitle"><b>...</b></h5>
      </div>
      <div class="modal-body">
        <form role="form">
          <div class="form-group">
            <label for="dsformname"><span class="fa fa-file-text"></span> Name</label>
            <input type="text" readonly class="form-control" id="dsformname" placeholder="Enter name">
          </div>
          <div class="form-group">
    		<label for="dsformtype"><span class="fa fa-list"></span>Type</label>
    		<select class="form-control" id="dsformtype">
      			<option>ORACLE</option>
      			<option>SQLSERVER</option>
      			<option>POSTGRESQL</option>
    		</select>
  		  </div>
          <div class="form-group">
            <label for="dsformhostname"><span class="fa fa-file-text"></span> Host Name </label>
            <input type="text" class="form-control" id="dsformhostname" placeholder="Enter Host Name">
          </div>
          <div class="form-group">
            <label for="dsformport"><span class="fa fa-file-text"></span> Port </label>
            <input type="text" class="form-control" id="dsformport" placeholder="Enter Port Number">
          </div>
          <div class="form-group">
            <label for="dsformdbname"><span class="fa fa-database"></span> Database Name </label>
            <input type="text" class="form-control" id="dsformdbname" placeholder="Enter Database Name">
          </div>
          <div class="form-group">
            <label for="dsformusername"><span class="fa fa-user"></span> Username </label>
            <input type="text" class="form-control" id="dsformusername" placeholder="Enter Username">
          </div>
          <div class="form-group">
            <label for="dsformpassword"><span class="fa fa-lock"></span> Password </label>
            <input type="password" class="form-control" id="dsformpassword" placeholder="Enter Password">
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-secondary" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
        <button id="dsmodaleditbtn" type="button" class="btn btn-primary">Save</button>
      </div>
    </div>
    
  </div>
</div>

<!-- KPI Edit KPI Model -->
<div class="modal fade" id="editKPIModal" role="dialog">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">x</button>
        <h5 class="modal-title" id="modalh5editKPITitle"><b>...</b></h5>
      </div>
      <div class="modal-body">
        <form role="form">
          <div class="form-group">
            <label for="kpiformname"><span class="fa fa-file-text"></span> KPI Name</label>
            <input type="text" readonly class="form-control" id="kpiformname" placeholder="Enter kpi name">
          </div>
          <div class="form-group" id="kpiformgroupds">
    		<label for="kpiformdatasource"><span class="fa fa-list"></span>DataSource</label>
    		<select class="form-control" id="kpiformdatasource">
    		</select>
  		  </div>
          <div class="form-group">
            <label for="kpiformquery"><span class="fa fa-file-text"></span> KPI Query </label>
            <textarea type="text" class="form-control" id="kpiformquery" placeholder="Enter KPI Query"></textarea>
          </div>
          <div class="form-group">
            <label for="kpiformtimeunit"><span class="fa fa-file-text"></span> Time Unit </label>
            <select class="form-control" id="kpiformtimeunit">
				<!-- <option>Second</option>
				<option>Minute</option> -->
				<option>Hour</option>
				<option>Day</option>
				<option>Week</option>
			</select>
          </div>
          <div class="form-group">
            <label for="kpiforminterval"><span class="fa fa-database"></span> Interval </label>
            <input type="text" class="form-control" id="kpiforminterval" placeholder="Enter Interval">
          </div>
          
          <div class="form-group">
            <label for="edit_kpi_srv_list"><span class="fa fa-list"></span> Service List </label>
            <select class="form-control" id="edit_kpi_srv_list">
            </select>
          </div>
          
          <div class="form-group">
            <label for="kpiformmarginal"><span class="fa fa-user"></span> Threshold Marginal Rule </label>
            <textarea type="text" class="form-control" id="kpiformmarginal" placeholder="Enter Threshold Marginal"></textarea>
          </div>
          <div class="form-group">
            <label for="kpiformbad"><span class="fa fa-lock"></span> Threshold Bad Rule </label>
            <textarea type="text" class="form-control" id="kpiformbad" placeholder="Enter Threshold Bad"></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-secondary" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span> Cancel</button>
        <button id="kpimodaleditbtn" type="button" class="btn btn-primary">Save</button>
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
        				<button id="savePassChngBtn" type="submit" class="btn btn-warning"> Save changes <span class="glyphicon glyphicon-send"></span></button>
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