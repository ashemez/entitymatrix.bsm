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
  <title>Service Manager</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="resource/css/boatstrap/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyLato.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/fontsFamilyMontserrat.css" rel="stylesheet" type="text/css">
  <link href="resource/css/fonts/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link href="resource/css/vodafonebsm.css" rel="stylesheet" type="text/css">
  <link href="resource/css/d3tree/d3-context-menu.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/d3tree/editor.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-toggle.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/boatstrap/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/multi-select.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/chosen.css" rel="stylesheet" type="text/css" />
  
  <!-- Query Building Css -->
  <link href="resource/css/query_builder/bootstrap-datepicker3.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-slider.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/selectize.bootstrap3.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/awesome-bootstrap-checkbox.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/query_builder/query-builder.default.min.css" rel="stylesheet" type="text/css" />
  
  <link href="resource/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css" />
  <link href="resource/css/jquery-cron.css" rel="stylesheet" type="text/css" />
  
  <script src="resource/js/jquery/jquery.min.js"></script> 
  <script src="resource/js/boatstrap/bootstrap.min.js"></script>
  <script src="resource/js/d3tree/d3.v3.min.js"></script>
  <script src="resource/js/jquery/jquery.dataTables.min.js"></script>
  <script src="resource/js/jquery/jquery.datetimepicker.full.js"></script>
  <script src="resource/js/jquery/jquery-cron.js"></script>
  
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
  <script src="resource/js/bsm.js"></script>
  <script src="resource/js/vodafonebsm.js"></script>
  <script src="resource/js/kpioverall.js"></script>
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
<script src="resource/js/jquery/jquery.multi-select.js"></script>
<script src="resource/js/jquery/chosen.jquery.js"></script>
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
					<a id="mainmenu1a" href="#" data-toggle="collapse" data-target="#submenu-1">
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
					<div class="panel-heading">Dashboards > KPI Overall</div>
				</div>
				<!-- pie chart by service status -->
				<div id="statpiechartcontainer" class="modal-body" style="width:100%;height:300px;">
					<table class="table" style="width:100%;height:100%;">
						<tr>
							<td style="width:50%;height:50%;"><canvas id="statpiechart"></canvas></td>
							<td style="width:50%;height:50%;"><canvas id="lastmonthavailpiechart"></canvas></td>
						</tr>
						<tr>
							<td colspan="2" style="width:33%;height:50%;"><canvas id="currentmonthavailpiechart"></canvas></td>
						</tr>
					</table>
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
            <input type="text" class="form-control" id="dsformname" placeholder="Enter name">
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
            <input type="text" class="form-control" id="kpiformname" placeholder="Enter kpi name">
          </div>
          <div class="form-group">
    		<label for="kpiformdatasource"><span class="fa fa-list"></span>DataSource</label>
    		<select class="form-control" id="kpiformdatasource">
      			<option>1</option>
      			<option>2</option>
      			<option>3</option>
    		</select>
  		  </div>
          <div class="form-group">
            <label for="kpiformquery"><span class="fa fa-file-text"></span> KPI Query </label>
            <textarea type="text" class="form-control" id="kpiformquery" placeholder="Enter KPI Query"></textarea>
          </div>
          <div class="form-group">
            <label for="kpiforminterval"><span class="fa fa-file-text"></span> Interval </label>
            <select class="form-control" id="kpiforminterval">
				<option>Second</option>
				<option>Minute</option>
				<option>Hour</option>
				<option>Day</option>
				<option>Week</option>
			</select>
          </div>
          <div class="form-group">
            <label for="kpiformtimeunit"><span class="fa fa-database"></span> Time Unit </label>
            <input type="text" class="form-control" id="kpiformtimeunit" placeholder="Enter Time Unit">
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