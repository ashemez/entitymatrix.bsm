//When Page Load Service Open 

$(document).ready(function() {
	$("#contentheader .panel-heading").text('Dashboards > Service Tree');
	
	//Redirect jsp pages
    $("#submenu-1 #li_bs").on('click', 'a', function (){
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
    $("#submenu-3 #li_ds" ).on('click', 'a', function (){
    	window.location.href ="/kpi.jsp?p=ds&msbval="+minisideval;
    });
    $("#submenu-3 #li_kpi" ).on('click', 'a', function (){
    	window.location.href ="/kpi.jsp?p=kpi&msbval="+minisideval;
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

});

//serviceviewer kpi table
var kpiTable;
function GetKPIList(sid){
	
	$.fn.dataTable.ext.errMode = 'none';
	if ( $.fn.dataTable.isDataTable('#kpitable')){
		kpiTable.destroy();
		
		kpiTable = $('#kpitable').DataTable( {
			"processing": false,
			"serverSide": true,
	        //ajax: 'resource/alarmlist.json',
			"ajax": {
				"url":  '/Kpi?p=getSrvKPIListForTable&sid=' + sid,
				"cache": false
			},
		    "cache": false,
	        scrollY:        200,
	        scrollCollapse: true,
			"bPaginate": false,
			lengthChange: false,
		    "bFilter": true,
		    "bInfo": false,
		    "bAutoWidth": false,
		    "createdRow": function( row, data, dataIndex ) {
		    	$(row).css('backgroundColor', getKPIStatusColor(data[3]));
		    },
		    "fnServerData": function ( sUrl, aoData, fnCallback, oSettings ) {
		        oSettings.jqXHR = $.ajax( {
		           "url":  '/Kpi?p=getSrvKPIListForTable&sid=' + sid,
		           "data": aoData,
		           "success": function (json) {
		              if ( json.sError ) {
		                 oSettings.oApi._fnLog( oSettings, 0, json.sError );
		              }

		              console.log(json);
		              $(oSettings.oInstance).trigger('xhr', [oSettings, json]);
		              fnCallback( json );
		              
		              outerUpdateStats();
		              
		           },
		           "dataType": "json",
		           "cache": false,
		           "type": oSettings.sServerMethod,
		           "error": function (xhr, error, thrown) {
		              if ( error == "parsererror" ) {
		                 oSettings.oApi._fnLog( oSettings, 0, "DataTables warning: JSON data from "+
		                    "server could not be parsed. This is caused by a JSON formatting error." );
		              }
		           }
		        } );
		     }
	    } );
	} else {
		kpiTable = $('#kpitable').DataTable( {
			"processing": false,
			"serverSide": true,
	        //ajax: 'resource/alarmlist.json',
			"ajax": {
				"url":  '/Kpi?p=getSrvKPIListForTable&sid=' + sid,
				"cache": false
			},
		    "cache": false,
	        scrollY:        200,
	        scrollCollapse: true,
			"bPaginate": false,
			lengthChange: false,
		    "bFilter": true,
		    "bInfo": false,
		    "bAutoWidth": false,
		    "createdRow": function( row, data, dataIndex ) {       
		    	$(row).css('backgroundColor', getKPIStatusColor(data[3]));
		    },
		    "fnServerData": function ( sUrl, aoData, fnCallback, oSettings ) {
		        oSettings.jqXHR = $.ajax( {
		           "url":  '/Kpi?p=getSrvKPIListForTable&sid=' + sid,
		           "data": aoData,
		           "success": function (json) {
		              if ( json.sError ) {
		                 oSettings.oApi._fnLog( oSettings, 0, json.sError );
		              }

		              console.log(json);
		              $(oSettings.oInstance).trigger('xhr', [oSettings, json]);
		              fnCallback( json );

		              outerUpdateStats();
		           },
		           "dataType": "json",
		           "cache": false,
		           "type": oSettings.sServerMethod,
		           "error": function (xhr, error, thrown) {
		              if ( error == "parsererror" ) {
		                 oSettings.oApi._fnLog( oSettings, 0, "DataTables warning: JSON data from "+
		                    "server could not be parsed. This is caused by a JSON formatting error." );
		              }
		           }
		        } );
		     }
	    } );
	}
	
	//setInterval( function () {
	//	kpiTable.ajax.reload(); // user paging is not reset on reload
	//}, 300000 );
}

