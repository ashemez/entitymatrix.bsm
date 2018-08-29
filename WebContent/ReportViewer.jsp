<%@page import="com.gizit.bsm.dao.ReportDAO"%>
<%@page import="com.gizit.bsm.helpers.Helper"%>
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
<script src="resource/js/report.js"></script>

<%
	String process = request.getParameter("p");
	String report_id = request.getParameter("report_id");
	String startingDate = request.getParameter("startingDate");
	String endingDate = request.getParameter("endingDate");
	String queryType = request.getParameter("queryType");
	String getOutageDetails = request.getParameter("getOutageDetails");
	
	String rptBtnClass = "collapse";
	String dateDivClass = "collapse";
	String periodDefault = "NONE";

	out.print("<script>");
	out.print("var getOutageDetails=1;var p = 'getCalculatedOutageReportOfSingleService';");
	if(process != null){
		out.print(" p = '" + process + "';");
	}
	if(startingDate != null){
		out.print("var startingDate = " + startingDate + ";");
	} else {
		out.print("var startingDate=" + Helper.CurrentMonthTimeWindow() + ";");
	}
	if(endingDate != null){
		out.print("var endingDate = " + endingDate + ";");
	} else {
		out.print("var endingDate = 0;");
	}
	if(report_id != null){
		out.print("var report_id = " + report_id + ";");
	} else {
		out.print("var report_id = 0;");
	}
	if(queryType != null){
		periodDefault = queryType;
		out.print(" queryType = '" + queryType + "';");
		if(queryType.equals("DATE_RANGE")){
			dateDivClass = "";
			rptBtnClass = "";
		} else if(queryType.equals("LAST_DAY") || queryType.equals("LAST_WEEK") || queryType.equals("LAST_MONTH")){
			dateDivClass = "collapse";
			rptBtnClass = "";
		} else {
			dateDivClass = "collapse";
			rptBtnClass = "collapse";
		}
	} else {
		out.print(" queryType = '" + ReportDAO.QueryType.LAST_MONTH + "';");
	}
	if(getOutageDetails != null){
		out.print(" getOutageDetails = " + getOutageDetails + ";");
	}

	if (request.getParameter("sid") == null) {
        // herror
		out.print("var _ssid = 0;");
    } else {
    	String sid = request.getParameter("sid");
    	out.print("var _ssid = " + sid + ";");
    	out.print("$( document ).ready(function() {");
		out.print("    showReport('reportcontainertbody', _ssid);");
		out.print("});");
    }
	out.print("</script>");
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
					<div class="panel-heading"><span id="rpt_servicename"></span></div>
				</div>
				<div id="contentpage">
					<!-- Created report list div -->
					<div id="rptlistdiv" class="collapse">
					 	<%
                		if(sessionManager.auth.permission.EditReportAuthorized()){ 
                		%>
						<span id="createRptBtn" title="Create New Report" class="btn btn-success fa fa-plus" style="margin-bottom:10px;"></span>
						<%
                		} 
                		%>
						<!-- New report div -->
		        		<div id="new_rpt_div" class="well collapse">
		        			<!-- Form Name -->
							<legend>Create Report</legend>
		        			<div class="form-group">
								<label class="col-md-2 control-label">Report Name</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="new_rpt_name" placeholder="Enter a report name" type="text">
								</div>
							</div>
	
							<div class="form-group" id="select_service_list_div">
								<label class="col-md-2 control-label">Service List</label>
								<div class="col-md-4 input-group" id="select_service_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='srv_list' data-placeholder="Select a service" multiple='multiple' class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group" id="crt_periodselectlist">
								<label class="col-md-2 control-label">Date Range</label>
								<div class="col-md-4 input-group" id="select_date_range_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='crt_queryType' onchange="onCrtPeriodSelected(this);" class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group collapse" id="new_rep_from">
								<label class="col-md-2 control-label">From</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control" id="crt_rptFromDate" placeholder="Select..." type="text">
								</div>
							</div>
							
							<div class="form-group collapse" id="new_rep_to">
								<label class="col-md-2 control-label">To</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control" id="crt_rptToDate" placeholder="Select..." type="text">
								</div>
							</div>			
							
							<!-- Success message -->
							<div id="new_report_form_message_div" class="alert alert-success"><label class="col-md-2" control-label></label><i class="glyphicon glyphicon-thumbs-up"></i>Report is saved !!!</div>			
							
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="input-group">
									<button type="submit" class="btn btn-warning" id="saverptbtn" onclick="CreateNewReport()">Save <span class="glyphicon glyphicon-send"></span></button>
								</div>
							</div>
		        		</div>
		        		
		        		<div id="new_report_form_message_div" class="collapse"></div>
		        		<h2>Reports</h2>		        		
						<table id="rptlisttbl" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">
		        			<thead>
								<tr class="bg-primary">
									<th>Report Name</th>
									<th>Service Name</th>
									<th>Report Period</th>
									<th>From</th>
									<th>To</th>
									<th>CreatedBy</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="rptlisttbody"></tbody>
	        			</table>
        			</div>
        			
        			<!-- Edited report list div -->
					<div id="edit_rptlistdiv" class="collapse">
						<!-- Edit report div -->
		        		<div id="edit_rpt_div" class="well">
		        			<!-- Form Name -->
							<legend>Edit Report</legend>
		        			<div class="form-group">
								<label class="col-md-2 control-label">Report Name</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" readonly id="edit_rpt_name" type="text">
								</div>
							</div>
	
							<div class="form-group" id="edit_select_service_list_div">
								<label class="col-md-2 control-label">Service List</label>
								<div class="col-md-4 input-group" id="edit_select_service_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='edit_srv_list' data-placeholder="Select a service" multiple='multiple' class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group" id="edit_crt_periodselectlist">
								<label class="col-md-2 control-label">Date Range</label>
								<div class="col-md-4 input-group" id="edit_select_date_range_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='edit_crt_queryType' onchange="onCrtPeriodSelectedEdit(this);" class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group collapse" id="edit_rep_from">
								<label class="col-md-2 control-label">From</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control" id="edit_crt_rptFromDate" placeholder="Select..." type="text">
								</div>
							</div>
							
							<div class="form-group collapse" id="edit_rep_to">
								<label class="col-md-2 control-label">To</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control" id="edit_crt_rptToDate" placeholder="Select..." type="text">
								</div>
							</div>
							
							<!-- Success message -->
							<div id="edit_report_form_message_div" class="alert alert-success"><label class="col-md-2" control-label></label><i class="glyphicon glyphicon-thumbs-up"></i>Report Changes are saved !!!</div>
													
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="input-group">
									<button type="submit" class="btn btn-warning" id="saverpteditbtn">Save <span class="glyphicon glyphicon-send"></span></button>
									<button id="close_edit_report" type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
								</div>
							</div>
		        		</div>		        		
        			</div>

					<!-- Report view div -->
					<div id="rptdiv" class="collapse">
						<div class="btn-group" id="rptdiv_back_button">
							<button type="button" class="btn btn-success"><span class="fa fa-arrow-circle-left"></span><span>Back</span></button>
	    				</div>
						<!-- Period select -->
						<div id="periodSelectDiv">
							<div class="form-group" id="periodselectlistdiv"> 
								<div class="col-md-4 selectContainer">
									<div class="input-group" id="periodselectlist">
										<span class="input-group-addon"><i class="fa fa-list"></i></span>
										<select class="form-control" id="queryType" onchange="onPeriodSelected(this);">
											<option value="NONE">Select period</option>
											<option value="LAST_DAY">Last Day</option>
											<option value="LAST_WEEK">Last Week</option>
											<option value="LAST_MONTH">Last Month</option>
											<option value="DATE_RANGE">Date Range</option>
										</select>
									</div>
								</div>
	
				          		<!-- Select Group List -->			
								<div class="form-group" id="rptbtndiv"> 
									<div class="col-md-4 selectContainer">
										<div class="input-group" id="rptbtngroup">
											<span id="rptBtnDiv" class="<%=rptBtnClass %>">
												<span id="refreshRptBtn" class="btn btn-success fa fa-refresh" onclick="RefreshReport();"></span>
											    <!-- <span id="createRptFromQueryBtn" class="btn btn-success fa fa-plus" onclick="showCreateReportRSB();"></span> -->
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Date select -->
						<div id="dateDiv" class="<%=dateDivClass %>">
							<table class="table">
								<tr>
									<td class="bg bg-info">From: </td>
									<td>
									<div class="col-md-6 input-group" id="rptfromdiv">
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										<input class="form-control btn btn-warning" id="rptFromDate" type="text" value="Select...">
									</div>
									</td>
								</tr>
								<tr>
									<td class="bg bg-info">To: </td>
									<td>
									<div class="col-md-6 input-group" id="rpttodiv">
										<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
										<input class="form-control btn btn-warning" id="rptToDate" type="text" value="Select...">
									</div>
									</td>
								</tr>
							</table>
						</div>
	        			<table id="reportcontainer" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">
		        			<thead>
								<tr class="bg-primary">
									<th>Service Name</th>
									<th>CI Type</th>
									<th>Outage Duration</th>
									<th>Availability Metric</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody id="reportcontainertbody"></tbody>
	        			</table>
	        			<div id="outagedetailsdiv" class="collapse">
		        			<table id="reportdetails" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">
			        			<thead>
									<tr class="bg-danger">
										<th>Outage Start</th>
										<th>Outage End</th>
										<th>Service Name</th>
										<th>Outage Duration</th>
									</tr>
								</thead>
								<tbody id="outagedetails"></tbody>
		        			</table>
	        			</div>
        			</div>
        			
        			<!-- Create Report Job Div -->
        			<div id="rpt_report_job_div">
        				<%
                		if(sessionManager.auth.permission.EditReportAuthorized()){ 
                		%>
	        			<span id="createRptJobBtn" title="Create New Report Job" class="btn btn-success fa fa-plus" style="margin-bottom:10px;"></span>	
	        			<%
                		} 
                		%>
	        			<!-- New report job div -->
		        		<div id="new_rptjobdiv" class="well collapse" data-hide="1">
		        			<!-- Form Name -->
							<legend>Create Report Job</legend>
		        			<div class="form-group">
								<label class="col-md-2 control-label">Report Job Name</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="rpt_job_name" type="text">
								</div>
							</div>
							
		        			<div class="form-group">
								<label class="col-md-2 control-label">Recurrent</label>
								<div class="input-group" id="recurrent">
								</div>
							</div>
	
			        		<div class="form-group" id="rpt_job_schedule_div">
								<label class="col-md-2 control-label">Schedule</label>
								<div class="col-md-6 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<div class="form-control" id="cronselector"></div>
								</div>
							</div>
							<div class="form-group collapse" id="rpt_job_date_div">
								<label class="col-md-2 control-label">Run at</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control btn btn-warning" id="rptjob_runat" type="text" value="Select...">
								</div>
							</div>
	
							<div class="form-group" id="select_report_list_div">
								<label class="col-md-2 control-label">Report List</label>
								<div class="input-group" id="select_report_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='rpt_list' multiple='multiple' class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label">Enable</label>
								<div class="input-group" id="isenableddiv">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label">Send Email</label>
								<div class="input-group" id="sendmaildiv">
								</div>
							</div>
							<div class="form-group collapse" id="subject_div">
								<label class="col-md-2 control-label">Subject</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="subject_div_input" type="text">
								</div>
							</div>
							<div class="form-group collapse" id="addresslist_div">
								<label class="col-md-2 control-label">Address List</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="rpt_job_addresslist" type="text">
								</div>
							</div>
							<div class="form-group collapse" id="message_div">
								<label class="col-md-2 control-label">Message</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="message_div_input" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="input-group">
									<button type="submit" class="btn btn-warning" id="rptjob_submit" >Save <span class="glyphicon glyphicon-send"></span></button>
								</div>
							</div>
		        		</div>
		        		
		        		<div id="form_message_div" class="collapse"></div>
		        		
						<!-- Report job list div -->
	        			<div id="rptjoblistdiv" class="collapse">
	        				<h2>Report Jobs</h2>
							<table id="rptjoblisttbl" class="table table-striped table-bordered table-condensed table-responsive" cellspacing="0" width="100%">
			        			<thead>
									<tr class="bg-primary">
										<th>Report Job Name</th>
										<th>Recurrent</th>
										<th>Send E-Mail</th>
										<th>Enabled</th>
										<th>Created By</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="rptjoblisttbody"></tbody>
		        			</table>
	        			</div>
					</div>
					
					<!-- Edit Report Job Div -->
        			<div id="edit_rpt_report_job_div" class="collapse">	
	        			<!-- Edited report job div -->
		        		<div id="edit_rptjobdiv" class="well">
		        			<!-- Form Name -->
							<legend>Edit Report Job</legend>
		        			<div class="form-group">
								<label class="col-md-2 control-label">Report Job Name</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" readonly id="edit_rpt_job_name" type="text">
								</div>
							</div>
							
		        			<div class="form-group">
								<label class="col-md-2 control-label">Recurrent</label>
								<div class="input-group" id="edit_recurrent">
								</div>
							</div>
	
			        		<div class="form-group" id="edit_rpt_job_schedule_div">
								<label class="col-md-2 control-label">Schedule</label>
								<div class="col-md-6 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<div class="form-control" id="edit_cronselector"></div>
								</div>
							</div>
							<div class="form-group collapse" id="edit_rpt_job_date_div">
								<label class="col-md-2 control-label">Run at</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input class="form-control btn btn-warning" id="edit_rptjob_runat" type="text" value="Select...">
								</div>
							</div>
	
							<div class="form-group" id="edit_select_report_list_div">
								<label class="col-md-2 control-label">Report List</label>
								<div class="input-group" id="edit_select_report_list">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<select id='edit_rpt_list' multiple='multiple' class="form-control">
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label">Enable</label>
								<div class="input-group" id="edit_isenableddiv">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label">Send Email</label>
								<div class="input-group" id="edit_sendmaildiv">
								</div>
							</div>
							<div class="form-group collapse" id="edit_subject_div">
								<label class="col-md-2 control-label">Subject</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="edit_subject_div_input" type="text">
								</div>
							</div>
							<div class="form-group collapse" id="edit_addresslist_div">
								<label class="col-md-2 control-label">Address List</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="edit_rpt_job_addresslist" type="text">
								</div>
							</div>
							<div class="form-group collapse" id="edit_message_div">
								<label class="col-md-2 control-label">Message</label>
								<div class="col-md-4 input-group">
									<span class="input-group-addon"><i class="fa fa-list"></i></span>
									<input class="form-control" id="edit_message_div_input" type="text">
								</div>
							</div>
							
							<!-- Success message -->
							<div id="edit_form_message_div" class="alert alert-success"><label class="col-md-2" control-label></label><i class="glyphicon glyphicon-thumbs-up"></i>Report Job Changes are saved !!!</div>
							
							<div class="form-group">
								<label class="col-md-2 control-label"></label>
								<div class="input-group">
									<button type="submit" class="btn btn-warning" id="edit_rptjob_submit" >Save <span class="glyphicon glyphicon-send"></span></button>
									<button id="close_edit_report_job" type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
								</div>
							</div>
		        		</div>
		        		
					</div>
        			<div id="loader" class="loader collapse" style="margin:0 auto;"></div>
					
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