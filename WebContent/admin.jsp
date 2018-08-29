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
  <script src="resource/js/bsm.js"></script>
  <script src="resource/js/vodafonebsm.js"></script>
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
<script src="resource/js/admin.js"></script>
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
				<%
                	if(sessionManager.auth.permission.ViewReportAuthorized() || sessionManager.auth.permission.EditReportAuthorized()){ 
                %>
					<li id="mainmenu2">
	                    <a href="#" data-toggle="collapse" data-target="#submenu-2">
							<i id="image2" class="fa fa-fw fa-bar-chart"></i>  
							<span id="text2">Reporting</span> 
							<i id="pull" class="fa fa-fw fa-angle-down pull-right"></i>
						</a>
	                    <ul id="submenu-2" class="collapse">
	                        <li id="li_rptlists"><a href="javascript:;"><i class="fa fa-circle-o"></i> Reports</a></li>
	                        <li id="li_rptjobs"><a href="javascript:;"><i class="fa fa-circle-o"></i> Report Jobs</a></li>
	                    </ul>
	                </li>
                <% 
                	}
                %>
                <%
                	if(sessionManager.auth.permission.ViewKPIAuthorized() || sessionManager.auth.permission.EditKPIAuthorized()){ 
                %>
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
                	}
                %>
                <li id="adminmenu">
                    <a id="adminmenua" href="#" data-toggle="collapse" data-target="#admin_menu">
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
					<div class="panel-heading">Dashboards > Business Services Dashboard</div>
				</div>
				<div id="contentpage">
        			<div id="loader" class="loader collapse" style="margin:0 auto;"></div>
        			<!-- User Form ---- [add user/edit user/delete user/add user role and user list] -->
					<div id="users_form" class="row collapse">
						<span id="users_form_def_open_close" title="Create New User" class="btn btn-success fa fa-plus"></span>
						<form class="well form-horizontal" action=" " method="post"  id="usr_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Create User</legend>
								
								<!-- Text input User Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Username</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="user_name" id="user_name" placeholder="User Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Text input First Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Firstname</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="first_name" id= "first_name" placeholder="First Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Text input Last Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Lastname</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="last_name" id="last_name" placeholder="Last Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Password  Area -->
								<div class="form-group">
									<label class="col-md-4 control-label">Password</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-lock"></i></span>
											<input type="password" id="user_password" class="form-control" placeholder="Password" name="user_password" />
										</div>
									</div>
								</div>
								
								<!-- Confirm password -->
								<div class="form-group">
				            		<label class="col-md-4 control-label"> Confirm Password </label>
				            		<div class="col-md-4 inputGroupContainer">
				            			<div class="input-group">
				            				<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
				            				<input name="user_repassword" type="password" class="form-control" id="user_repassword" placeholder="Confirm Password">
				          				</div>
				          			</div>
				          		</div>
				          		
				          		<!-- Add Roles Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Roles</label> 
									<button id="add_roles" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>
				          		
				          		<!-- Select Role List -->			
								<div class="form-group" id="user_form_role_list"> 
									<label class="col-md-4 control-label">Role List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_role_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='role_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Add Group Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Groups</label> 
									<button id="add_groups" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>

				          		<!-- Select Group List -->			
								<div class="form-group" id="user_form_group_list"> 
									<label class="col-md-4 control-label">Group List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_group_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='group_list' multiple='multiple' class="form-control">
											</select>
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
						
						<h2>Users</h2>  
						<div class="table-responsive">           
						<table id="existingusers" class="table">
							<thead>
							<tr class="bg-primary">
								<th>Username</th>
								<th>Firstname</th>
								<th>Lastname</th>
								<th>Edit</th>
								<th>Delete</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
						</div>
					</div>
					<!-- Edit User Form -->
					<div id="edit_users_form" class="collapse">
						<form class="well form-horizontal" action=" " method="post"  id="edit_usr_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Edit User: <span>....</span></legend>
								
								<!-- Text input User Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Username</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="edit_user_name" readonly id="edit_user_name" placeholder="User Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Text input First Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Firstname</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="edit_first_name" id= "edit_first_name" placeholder="First Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Text input Last Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Lastname</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="edit_last_name" id="edit_last_name" placeholder="Last Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Password  Area -->
								<div class="form-group">
									<label class="col-md-4 control-label">Password</label>
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-lock"></i></span>
											<input name="edit_user_password" id="edit_user_password" class="form-control" placeholder="Password" type="password"  />
										</div>
									</div>
								</div>
								
								<!-- Confirm password -->
								<div class="form-group">
				            		<label class="col-md-4 control-label"> Confirm Password </label>
				            		<div class="col-md-4 inputGroupContainer">
				            			<div class="input-group">
				            				<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
				            				<input name="edit_user_repassword" type="password" class="form-control" id="edit_user_repassword" placeholder="Confirm Password">
				          				</div>
				          			</div>
				          		</div>
				          		
				          		<!-- Add Roles Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Roles</label> 
									<button id="edit_add_roles" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>
				          		
				          		<!-- Select Role List -->			
								<div class="form-group" id="edit_user_form_role_list"> 
									<label class="col-md-4 control-label">Role List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="edit_select_role_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='edit_role_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Add group Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Groups</label> 
									<button id="edit_add_groups" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>

				          		<!-- Select Group List -->			
								<div class="form-group" id="edit_user_form_group_list"> 
									<label class="col-md-4 control-label">Group List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="edit_select_group_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id="edit_group_list" multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Success message -->
								<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i> Entered Values Saved !!!</div>
								
								<!-- Button -->
								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning" > Save <span class="glyphicon glyphicon-send"></span></button>
										<button id="close_edit_usr" type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									</div>
								</div>
							
							</fieldset>
						</form>
					</div>
					<!-- Roles Form ---- [roles add/edit/delete and role list and resources/service permissions] -->
					<div id="roles_form" class="row collapse">
						<span id="roles_form_def_open_close" title="Create New Role" class="btn btn-success fa fa-plus"></span>
						<form class="well form-horizontal" action=" " method="post"  id="rls_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Create Role</legend>
								
								<!-- Text input Role Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Role Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="role_name" id="role_name" placeholder="Role Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Add Users Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Users</label> 
									<button id="role_add_users" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>
				          		
				          		<!-- Select User List -->			
								<div class="form-group" id="role_form_user_list"> 
									<label class="col-md-4 control-label">User List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_role_form_user_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='role_form_select_user_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Add Groups Text -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Groups</label> 
									<button id="role_add_groups" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>

				          		<!-- Select Group List -->			
								<div class="form-group" id="role_form_group_list"> 
									<label class="col-md-4 control-label">Group List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_role_form_group_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='role_form_select_group_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text Resource Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Resource Permissions</label> 
									<button id="role_resource_permiss" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>
								
								<div id="role_form_perm_list">
									<div class="form-group" id="form_service_permiss">
										<label class="col-md-4 control-label">Service:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxsv" name="srvradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxse" name="srvradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="form_ds_permiss">
										<label class="col-md-4 control-label">Datasource:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxdsv" name="dsradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxdse" name="dsradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="form_kpi_permiss">
										<label class="col-md-4 control-label">KPI:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxkpiv" name="kpiradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxkpie" name="kpiradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="form_report_permiss">
										<label class="col-md-4 control-label">Report:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxrepv" name="rptradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxrepe" name="rptradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="form_admin_permiss">
										<label class="col-md-4 control-label">Administration:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="inlineCheckboxadmv" name="admradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
								</div>	
								
								<!-- Text Service Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Service Permissions</label> 
									<button id="role_service_permiss" class="btn btn-primary" type="button" style="margin-left:15px;width:200px">Edit</button> 
								</div>
								
								<!-- Set Service Permissions -->			
								<div class="form-group" id="role_form_srvper_list"> 
									<label class="col-md-4 control-label">Service List</label>
									<div class="col-md-4 selectContainer">
										<a href='#' id='role_srv_select-all'>Select All</a>
										<a> / </a>
										<a href='#' id='role_srv_deselect-all'>Deselect All</a>
										<div class="input-group" id="select_role_form_srvperm_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='role_form_select_srvperm_list' multiple='multiple' class="form-control">
											</select>
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
						
						<h2>Roles</h2>  
						<div class="table-responsive">           
						<table id="existingroles" class="table">
							<thead>
							<tr class="bg-primary">
								<th>Role Name</th>
								<th>Edit</th>
								<th>Delete</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
						</div>
					</div>
					<!-- Edit Roles Form ---- [roles edit and resources/service permissions] -->
					<div id="edit_roles_form" class="collapse">
						<form class="well form-horizontal" action=" " method="post"  id="edit_rls_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Edit Role: <span>....</span></legend>
								
								<!-- Text input Role Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Role Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="edit_role_name" readonly id="edit_role_name" placeholder="Role Name" class="form-control"  type="text">
										</div>
									</div>
									<!--  <button id="edit_role_add_users" class="btn btn-primary" type="button">Open Edit Users</button> -->
				          			<!--  <button id="edit_role_add_groups" class="btn btn-primary" type="button">Open Edit Groups</button> -->
								</div>
				          		
				          		<!-- Text User Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Users</label> 
									<button id="edit_role_add_users" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>
				          		
				          		<!-- Select User List -->			
								<div class="form-group" id="edit_role_form_user_list"> 
									<label class="col-md-4 control-label">User List</label>
									<div class="col-md-4 selectContainer">
										<!--  User List Search-->
				          				<!-- <input type="text" id="roleusersearch" onkeyup="Roleusersearch()" placeholder="Search for users...">-->
										<div class="search-container">
											<input type="text" placeholder="Search for users..." name="search">
										    <button id="roleusersearch" type="button"><i class="fa fa-search"></i></button>
										</div>
										<div class="input-group" id="edit_select_role_form_user_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='edit_role_form_select_user_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text Group Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Groups</label> 
									<button id="edit_role_add_groups" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>

				          		<!-- Select Group List -->			
								<div class="form-group" id="edit_role_form_group_list"> 
									<label class="col-md-4 control-label">Group List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="edit_select_role_form_group_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='edit_role_form_select_group_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text Resource Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Resource Permissions</label> 
									<button id="edit_role_resource_permiss" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>
								
								<div id="edit_role_form_perm_list">
									<div class="form-group" id="edit_form_service_permiss">
										<label class="col-md-4 control-label">Service:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxsv" name="edit_srvradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxse" name="edit_srvradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="edit_form_ds_permiss">
										<label class="col-md-4 control-label">Datasource:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxdsv" name="edit_dsradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxdse" name="edit_dsradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="edit_form_kpi_permiss">
										<label class="col-md-4 control-label">KPI:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxkpiv" name="edit_kpiradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxkpie" name="edit_kpiradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="edit_form_report_permiss">
										<label class="col-md-4 control-label">Report:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxrepv" name="edit_rptradio" value="1">View
											    </label>
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxrepe" name="edit_rptradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
									
									<div class="form-group" id="edit_form_admin_permiss">
										<label class="col-md-4 control-label">Administration:</label>
										<div class="col-md-4 inputGroupContainer">
											<div class="input-group">
											    <label class="radio-inline">
											      <input type="radio" id="edit_inlineCheckboxadmv" name="edit_admradio" value="2">Edit
											    </label>
											</div>
										</div>
									</div>
								</div>	
								
								<!-- Text Service Permission -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Service Permissions</label> 
									<button id="edit_role_service_permiss" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>
								
								<!-- Set Service Permissions -->			
								<div class="form-group" id="edit_role_form_srvper_list"> 
									<label class="col-md-4 control-label">Service List</label>
									<div class="col-md-4 selectContainer">
										<a href='#' id='edit_role_srv_select-all'>Select All</a>
										<a> / </a>
										<a href='#' id='edit_role_srv_deselect-all'>Deselect All</a>
										<div class="input-group" id="edit_select_role_form_srvperm_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='edit_role_form_select_srvperm_list' multiple='multiple' class="form-control">
											</select>
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
										<button id="close_edit_rls" type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									</div>
								</div>
							
							</fieldset>
						</form>
					</div>
					<!-- Groups Form ---- [groups add/edit/delete and group list and resources/service permissions] -->
					<div id="groups_form" class="row collapse">
						<span id="groups_form_def_open_close" title="Create New Group" class="btn btn-success fa fa-plus"></span>
						<form class="well form-horizontal" action=" " method="post"  id="grps_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Create Group</legend>
								
								<!-- Text input Group Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Group Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="group_name" id="group_name" placeholder="Group Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
				          		
				          		<!-- Text Add User -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Users</label> 
									<button id="group_add_users" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>
				          		
				          		<!-- Select User List -->			
								<div class="form-group" id="group_form_user_list"> 
									<label class="col-md-4 control-label">User List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_group_form_user_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='group_form_select_user_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text Add Roles -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Roles</label> 
									<button id="group_add_roles" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>

				          		<!-- Select Role List -->			
								<div class="form-group" id="group_form_role_list"> 
									<label class="col-md-4 control-label">Role List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="select_group_form_role_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='group_form_select_role_list' multiple='multiple' class="form-control">
											</select>
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
						
						<h2>Groups</h2>  
						<div class="table-responsive">           
						<table id="existinggroups" class="table">
							<thead>
							<tr class="bg-primary">
								<th>Group Name</th>
								<th>Edit</th>
								<th>Delete</th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
						</div>
					</div>
					<!-- Edit Groups Form -->
					<div id="edit_groups_form" class="collapse">
						<form class="well form-horizontal" action=" " method="post"  id="edit_grp_form">
							<fieldset>
								<!-- Form Name -->
								<legend>Edit Group: <span>....</span></legend>
								
								<!-- Text input Group Name -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Group Name</label>  
									<div class="col-md-4 inputGroupContainer">
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-file-text"></i></span>
											<input name="edit_group_name" readonly id="edit_group_name" placeholder="Group Name" class="form-control"  type="text">
										</div>
									</div>
								</div>
								
								<!-- Text Add Users -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Users</label> 
									<button id="edit_grpadd_users" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>
				          		
				          		<!-- Select User List -->			
								<div class="form-group" id="edit_group_form_user_list"> 
									<label class="col-md-4 control-label">User List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="edit_select_user_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id='edit_grpuser_list' multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Text Add Roles -->		
								<div class="form-group">
									<label class="col-md-4 control-label">Roles</label> 
									<button id="edit_grpadd_roles" class="btn btn-primary" type="button" style="margin-left:15px;width:230px">Edit</button> 
								</div>

				          		<!-- Select Role List -->			
								<div class="form-group" id="edit_group_form_role_list"> 
									<label class="col-md-4 control-label">Role List</label>
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="edit_select_role_list">
											<span class="input-group-addon"><i class="fa fa-list"></i></span>
											<select id="edit_grprole_list" multiple='multiple' class="form-control">
											</select>
										</div>
									</div>
								</div>
								
								<!-- Success message -->
								<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i> Entered Values Saved !!!</div>
								
								<!-- Button -->
								<div class="form-group">
									<label class="col-md-4 control-label"></label>
									<div class="col-md-4">
										<button type="submit" class="btn btn-warning" > Save <span class="glyphicon glyphicon-send"></span></button>
										<button id="close_edit_grp" type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
									</div>
								</div>
							
							</fieldset>
						</form>
					</div>										
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