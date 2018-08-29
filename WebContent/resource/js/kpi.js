var urlParams = {};
$(function() {
    pairs = document.URL.split('?')
           .pop()
           .split('&');

    for (var i = 0, p; i < pairs.length; i++) {
           p = pairs[i].split('=');
           urlParams[ p[0] ] =  p[1];
    }
});

$(document).ready(function() {
    if(urlParams["p"] === 'ds' || urlParams["p"] === 'ds#'){
        $("#mainmenu3 #mainmenu3a").trigger("click");
        $("#submenu-3 #kpi_li1 #data_source").trigger("click");
    	$("#submenu-3 #kpi_li1 #data_source").trigger("focus");
    	
    } else if(urlParams["p"] === 'kpi' || urlParams["p"] === 'kpi#') {
	    $("#mainmenu3 #mainmenu3a").trigger("click");
	    $("#submenu-3 #kpi_li2 #kpi").trigger("click");
		$("#submenu-3 #kpi_li2 #kpi").trigger("focus");
		
    }
    
	//Redirect jsp pages
	$("#submenu-1 #li_bs" ).on('click', 'a', function (){
    	window.location.href ="/index.jsp?msbval="+minisideval;
    });
    $("#submenu-1 #li_kpiover").on('click', 'a', function (){
    	window.location.href ="/kpioverall.jsp?msbval="+minisideval;
    });    
	$("#submenu-2 #rpt1_li" ).on('click', 'a', function (){
    	window.location.href ="/ReportViewer.jsp?p=reportlist&msbval="+minisideval;
    });
    $("#submenu-2 #rpt2_li").on('click', 'a', function (){
    	window.location.href ="/ReportViewer.jsp?p=reportjobs&msbval="+minisideval;
    });	
    $("#admin_menu #li_usr" ).on('click', 'a', function (){
    	window.location.href ="/admin.jsp?p=users&msbval="+minisideval;
    });
    $("#admin_menu #li_role" ).on('click', 'a', function (){
    	window.location.href ="/admin.jsp?p=roles&msbval="+minisideval;
    });
    $("#admin_menu #li_grp" ).on('click', 'a', function (){
    	window.location.href ="/admin.jsp?p=groups&msbval="+minisideval;
    });
    
    /*$('#kpi_form_def_open_close').on('click', function () {
    	if ($('#kpi_form').is(":visible")){
    		$('#kpi_form').hide();
    		$('#kpi_form_def_open_close').removeClass('fa-minus').addClass('fa-plus');
    	}
    	else{
    		$('#kpi_form').show();
    		$('#kpi_form_def_open_close').removeClass('fa-plus').addClass('fa-minus');
    	}
    	
    });*/
    $('#kpi_form_def_open_close').on('click', function () {
    	if ($('#kpi_form').is(":visible")){
    		$('#kpi_form').hide();
    		$('#kpi_form_def_open_close').removeClass('fa-minus').addClass('fa-plus');
    	}
    	else{
			$("#kpi_srv_list option").remove();
			$("#loader").attr('class', 'loader');
			$.ajax({
				cache: false,
				url:'/Report?p=getAllServiceList',
				success:function(data) {
					$("#loader").attr('class', '');
					
					// service list data
					var jData = JSON.parse(data);
					$.each(jData, function(key,value) {
						  var option = $("<option value='" + value.service_instance_id + "' >" + value.service_instance_name + "</option>");
						  option.appendTo($("#kpi_srv_list"));
					});
					console.log("Option is added");
					$("#kpi_srv_list").val("").trigger("chosen:updated");
					
					// server list search
					$('#kpi_srv_list').val(jData.service_instance_id).trigger("chosen:updated"); // Select the value before .chosen();
					var $chosen = $('#kpi_srv_list').chosen({
						max_selected_options: 1,
					});
					$chosen.change(function () {
					  var $this = $(this);
					  var chosen = $this.data('chosen');
					  var search = chosen.search_container.find('input[type="text"]');
					  
					  if($('#kpi_srv_list').val().length > 0){
						//$('#saverptbtn').attr('data-sid', $('#srv_list').val());
						console.log(JSON.parse($('#kpi_srv_list').val()));
					  }
					  console.log("Chosen Active Field:"+chosen.active_field);
					  if (chosen.active_field) {
						search.focus();
					  }
					});

				}
			});
    		$('#kpi_form').show();
    		$('#kpi_form_def_open_close').removeClass('fa-plus').addClass('fa-minus');
    	}
    	
    });
    $('#datasource_form_def_open_close').on('click', function () {
    	if ($('#ds_form').is(":visible")){
    		$('#ds_form').hide();
    		$('#datasource_form_def_open_close').removeClass('fa-minus').addClass('fa-plus');
    	}
    	else{
    		$('#ds_form').show();
    		$('#datasource_form_def_open_close').removeClass('fa-plus').addClass('fa-minus');
    	}
    	
    });
    
    $('#saveModal').on('hidden.bs.modal', function () {
    	console.log("KPI Hidden model!!!!!!!!!!!");
    	if($("#saveModal").attr("data-form_name") == "adddatasource"){
            var attr = $("#datasource_form #existingdatasource tbody tr").attr('data-sid');
            if (typeof attr !== typeof undefined && attr !== false) {
            	$("#datasource_form #existingdatasource tbody tr[data-sid]").remove();
            }
			$("#loader").attr('class', 'loader');
			$.ajax({
				cache: false,
				url:'/DataSource?p=isEditDSAuthorized',
				success:function(edata) {
					$$.ajax({
						cache: false,
						url:'/DataSource?p=getDSList',
						success:function(data) {
							$("#loader").attr('class', 'loader collapse');
							showdataSourceList(data, edata);
						}
					});
				}
			});
			$('#ds_form').hide();
    		$('#datasource_form_def_open_close').removeClass('fa-minus').addClass('fa-plus');
    	}
    	if($("#saveModal").attr("data-form_name") == "editdatasource"){
    		 var attr = $("#datasource_form #existingdatasource tbody tr").attr('data-sid');
	            if (typeof attr !== typeof undefined && attr !== false) {
	            	$("#datasource_form #existingdatasource tbody tr[data-sid]").remove();
	            }
				$("#loader").attr('class', 'loader');
				$.ajax({
					cache: false,
					url:'/DataSource?p=isEditDSAuthorized',
					success:function(edata) {
						$.ajax({
							cache: false,
							url:'/DataSource?p=getDSList',
							success:function(data) {
								$("#loader").attr('class', 'loader collapse');
								showdataSourceList(data, edata);
							}
						});
					}
				});
				$("#editDSModal").modal("hide");
    	}
    	if($("#saveModal").attr("data-form_name") == "addkpi"){
    		var attr = $("#keypi_form #existingkpi tbody tr").attr('data-kpiid');
            if (typeof attr !== typeof undefined && attr !== false) {
            	$("#keypi_form #existingkpi tbody tr[data-kpiid]").remove();
            }
			$("#loader").attr('class', 'loader');
			$.ajax({
				cache: false,
				url:'/Kpi?p=isEditKPIAuthorized',
				success:function(edata) {
					$.ajax({
						cache: false,
						url:'/Kpi?p=getKPIList',
						success:function(data) {
							$("#loader").attr('class', 'loader collapse');
							showKpiList(data, edata);
						}
					});
				}
			});
			$('#kpi_form').hide();
    		$('#kpi_form_def_open_close').removeClass('fa-minus').addClass('fa-plus');
    	}
    	if($("#saveModal").attr("data-form_name") == "editkpi"){
            var attr = $("#keypi_form #existingkpi tbody tr").attr('data-kpiid');
            if (typeof attr !== typeof undefined && attr !== false) {
            	$("#keypi_form #existingkpi tbody tr[data-kpiid]").remove();
            }
			$("#loader").attr('class', 'loader');
			$.ajax({
				cache: false,
				url:'/Kpi?p=isEditKPIAuthorized',
				success:function(edata) {
					$.ajax({
						cache: false,
						url:'/Kpi?p=getKPIList',
						success:function(data) {
							$("#loader").attr('class', 'loader collapse');
							showKpiList(data, edata);
						}
					});
				}
			});
			$("#editKPIModal").modal("hide");
    	}
    });

});
