<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set scope="request" var="disableDefaultJQuery" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="resultsdashboard.title" /></title>
<s:head />
<link href="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.css"
	rel="stylesheet" media="all" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${scriptPath}/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css">
<style type="text/css">
div.exportlinks {
	text-align: right;
}

li[role="treeitem"] table td:first-child {
	width: 100%;
}

li[role="treeitem"] table td:nth-child(2) {
	min-width: 10px;
	padding-left: 5px;
	vertical-align: middle;
}

i.fa-sitemap {
	cursor: default;
}

#wl_table_container {
	max-width: 930px;
	overflow-x: scroll;
}

a.nciid,td.checkedOut span {
	white-space: nowrap;
}

th.filter td,th.submissionType td {
	border: 0;
	padding: 2px;
	vertical-align: middle;
}

i.fa-filter {
	cursor: pointer;
}

#date-range-filter table td {
	padding: 5px;
}

#date-range-filter input {
	min-width: 150px;
	margin-right: 5px;
}

#submission-type-filter input {
	margin-left: 20px;
}

#submission-type-filter label {
	font-weight: normal;
}

#submission-type-filter label:after {
	content: "\A";
	white-space: pre;
}

i.fa-pencil-square-o {
	margin-left: 5px;
	cursor: pointer;
}

#abstraction-date-override input {
	min-width: 200px;
	margin-right: 5px;
}

#abstraction-date-override textarea {
	width: 100%;
	min-height: 50px;
}

span[data-overridden='true'] {
	text-decoration: underline;
}

#count_panels_container {
	margin-top: 20px;
	padding-bottom: 20px;
}

div.trial_count_panel {
	width: 45%;
}

h3.ui-accordion-header {
    font-weight: bold;
}

table.dataTable thead th {
    padding: 0px 0px 0px 0px;
    font-size: 90%;
}

table.dataTable tbody td {
    padding: 3px 3px;
}

#Total td {
    font-weight: bold;
}

#Total td a {
    text-decoration: none;
    color: black;
    cursor: auto;
}

a.count {
    color: #386EBF;
    cursor: pointer;
}

td.middle {
    vertical-align:middle;
}

label.pcd {
    width: 50px;
}

table.results {
    width:100%;
    padding: 5px;
}

.flash {
 display:none; 
 color:#00CC00;
}

</style>

<script type="text/javascript"
	src="${scriptPath}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript"
	src="${scriptPath}/js/jquery-ui-1.11.2.custom/jquery-ui.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="${scriptPath}/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/showhide.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/control.tabs.js"/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
	
<script type="text/javascript"
    src="${scriptPath}/js/Chart.min.js"></script>

<c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl" />
<c:url value="/protected/popupDisdisplayDiseaseWidget.action"
	var="diseaseWidgetURL" />

<script type="text/javascript" language="javascript">
jQuery(function() {
	
	var reportDateUpdateError = function(study, attr){
		alert("Update of "+attr+ " failed for study "+ study+"." )
	}
	
	jQuery("#pcdFrom").datepicker();
	jQuery("#pcdTo").datepicker();
	initChart();
	if ('${pcdFrom}' != '') {
      jQuery("#pcdFrom").val(jQuery.datepicker.formatDate('mm/dd/yy', new Date('${pcdFrom}')));
	}
	if ('${pcdTo}' != '') {
	  jQuery("#pcdTo").val(jQuery.datepicker.formatDate('mm/dd/yy', new Date('${pcdTo}')));
	}
	
	jQuery(".datePicker").datepicker({
		onClose : function (date, inp){
			var id = inp.id;
			var attr = id.split('_')[0];
			var studyId = id.split('_')[1];
			
			var ajaxReq = new Ajax.Request('resultsDashboardajaxChangeDate.action', {
            method: 'post',
            parameters: 'studyId='+studyId+'&dateAttr='+attr+'&dateValue='+date,
            onSuccess: function(response) {
                if (response.responseText != 'success'){
                   	    reportDateUpdateError(studyId, attr);
                    } else {
                   	    jQuery('#'+id+'_flash').delay(100).fadeIn('normal', function() {
                        jQuery(this).delay(2500).fadeOut();
                 	});
                }
            },
            onFailure: function(response) {
                	 reportDateUpdateError(studyId, attr);
            },
            onException: function(requesterObj, exceptionObj) {
                 ajaxReq.options.onFailure(null);
            },
            on0: function(response) {
                  ajaxReq.options.onFailure(response);
            }
          });			
		}
	});
  });
  

function handleAction(action) {
    document.forms[0].action = "resultsDashboardsearch.action";
    document.forms[0].submit();
}

function resetValues() {
	 document.forms[0].getElements().each(function(el) {
        if (el.type=='text') {
               el.clear();                
        }
        if (el.type=='checkbox' || el.type=='radio') {               
            el.checked = false;
        }
    });
    $$('option').each(function(optEl) {
        optEl.selected = false;
    });
    if ($('error_msg_div')!=null) {
        $('error_msg_div').hide();
    }
}

function initChart() {
	var chartData = [
	                 {
	                     value: ${inProcessCnt},
	                     color:"#4F81BC",
	                     highlight: "#5F91CC",
	                     label: "In Process"
	                 },
	                 {
	                     value: ${completedCnt},
	                     color: "#C0504E",
	                     highlight: "#DA6351",
	                     label: "Completed"
	                 },
	                 {
	                     value: ${notStartedCnt},
	                     color: "#A0B953",
	                     highlight: "#BFC860",
	                     label: "Not Started"
	                 },
                     {
                         value: ${issuesCnt},
                         color: "#7B65A5",
                         highlight: "#8F78B0",
                         label: "Issues"
                     }
	             ]

	var ctx = jQuery("#resultsChart").get(0).getContext("2d");
   var opts = {
		    //Number - Amount of animation steps
		    animationSteps : 60,
		    //String - Animation easing effect
		    animationEasing : "easeOutBounce",
		    //Boolean - Whether we animate the rotation of the Doughnut
		    animateRotate : true,
		    //Boolean - Whether we should show a stroke on each segment
		    segmentShowStroke : false,
		    legendTemplate : ${'"<ul class=\'<%=name.toLowerCase()%>-legend\'><% for (var i=0; i<segments.length; i++){%><li><span style=\'background-color:<%=segments[i].fillColor%>;width:15px; height:15px; float:left;\'></span><%if(segments[i].label){%>&nbsp;&nbsp;<%=segments[i].label%><%}%></li><%}%></ul>"'}
	}
	var statChart = new Chart(ctx).Pie(chartData,opts);
	jQuery("#legend").html(statChart.generateLegend());
}

function searchResults(url, studyNCIid){
	var studyIdentifier; 
	if(studyNCIid != ''){
		 displayWaitPanel();
		 var ajaxReq = new Ajax.Request('resultsDashboardajaxGetStudyStudyProtocolIdByNCIId.action?', {
             method: 'post',
             parameters: 'studyNCIId='+studyNCIid.trim(),
             onSuccess: function(response) {
            	 hideWaitPanel();
            	 if(response.responseText != ''){
            		 window.location.href = url+'?studyProtocolId='+response.responseText;
            	 } else {
            		 alert("No trial with NCI ID '"+studyNCIid+"' found in PA!");
            	 }
             },
			 onFailure: function(transport) {
				 hideWaitPanel();   
				 alert("Error loading study details, please try again");
	         },
	         onException: function(requesterObj, exceptionObj) {
	             ajaxReq.options.onFailure(null);
	         }
           });
		 
	} else if (url == 'resultsReportingActionsTakenview.action') {
		window.location.href = url;
	}
}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="resultsdashboard.title" />
	</h1>
	<c:set var="topic" scope="request" value="resultsDashboard"/>
	<div class="box" id="filters">
         <s:form>
             <pa:failureMessage/>
             <table class="form" >
                 <tr >
                     <td  scope="row" class="label middle">
                         <label><fmt:message key="resultsdashboard.section801Indicator"/></label>
                           <label for="section801IndicatorYes">Yes</label>
                           <s:checkbox id="section801IndicatorYes" name="section801IndicatorYes"  onclick="jQuery('#section801IndicatorNo').prop('checked', false);" />
                           <label for="section801IndicatorNo">No</label>
                           <s:checkbox id="section801IndicatorNo" name="section801IndicatorNo" onclick="jQuery('#section801IndicatorYes').prop('checked', false);" />
                     </td>
                     
                    <td  scope="row" class="label middle" >
					<table >
                       <tr >
                     <td  scope="row" class="label middle">
                         <label> <fmt:message key="resultsdashboard.primaryCompletionDate"/></label>
                     </td>
                     <td class="middle">
                         <table>
                            <tr>
		                       <td  scope="row" class="label middle" align="right" >
		                           <label for="pcdFrom" > <fmt:message key="resultsdashboard.pcdFrom"/></label>
		                       </td>
		                       <td  scope="row" class="label middle">
		                           <input type="text" id="pcdFrom" name="pcdFrom" size="15"/>
		                       </td>
		                       <td class="label middle" align="right" > 
		                           <label for="pcdTo"><fmt:message key="resultsdashboard.pcdTo"/></label>
		                       </td>
                               <td  scope="row" class="label middle">
		                           <input type="text" id="pcdTo" name="pcdTo" size="15"/>
		                       </td>
	                       </tr>
	                       <tr>
		                       <td class="label middle" align="right" >
		                          <label for="pcdType"><fmt:message key="resultsdashboard.pcdType"/></label>
		                       </td>
                               <td  scope="row" class="label middle">
		                          <s:select headerKey="" headerValue="Any" id="pcdType" name="pcdType" 
                                  list="#{'Actual':'Actual','Anticipated':'Anticipated'}"  value="pcdType" cssStyle="width:120px" />
		                       </td>		                       
		                    </tr>
                         </table>
                     </td>
                     </tr>
                     </table>
                 </tr>
                 </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                               
                                <s:if test="%{pageFrom == 'associate'}">
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();">
                                <span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                                </s:if>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>        
        <s:if test="results != null">
         <div class="line"></div>
         <h2>Search Results</h2>
         <div id="results" class="box">
             <c:set var="requestURI" value="resultsDashboardsearch.action"
                 scope="request" />
             <display:table class="data" sort="list" pagesize="10"
                 decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator"
                 name="results" requestURI="${requestURI}"
                 export="false" uid="row">
                 <display:column  class="title" titleKey="studyProtocol.nciIdentifier" sortable="true" headerScope="col" scope="row" >
                    <a id="trialview_${row.studyProtocolId}" href="javascript:void(0)" onclick="searchResults('trialViewquery.action', '${row.studyProtocolId}')"><c:out value="${row.nciIdentifier}"/></a>
                 </display:column>
                 <display:column  title="NCT ID" property="nctIdentifier"/>
                 <display:column  title="CTEP/DCP ID" property="ctepOrDcp"/>
                 <display:column  title="Lead Org PO ID" property="leadOrganizationPOId"/>          
                 <display:column  title="Lead Organization" property="leadOrganizationName"/>
                 <display:column  title="Results Designee" property="designeeNamesList"/>                                  
                 <display:column title="PCD Sent to PIO">
                    <input id="pcdSentToPIODate_${row.studyProtocolId}" class="datePicker" size="8" value="<fmt:formatDate value="${row.pcdSentToPIODate}" pattern="MM/dd/yyyy"/>"/>
                    <div id="pcdSentToPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="PCD Confirmed">
                     <input id="pcdConfirmedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.pcdConfirmedDate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="pcdConfirmedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>                      
                 <display:column  title="Designee Notified">
                      <input id="desgneeNotifiedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.desgneeNotifiedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="desgneeNotifiedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Reporting in Process">
                      <input id="reportingInProcessDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.reportingInProcessDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="reportingInProcessDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="3 Month Reminder">
                     <input id="threeMonthReminderDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.threeMonthReminderDate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="threeMonthReminderDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>                             
                 <display:column  title="5 Month Reminder">
                      <input id="fiveMonthReminderDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.fiveMonthReminderDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="fiveMonthReminderDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="7 Month Escalation">
                     <input id="sevenMonthEscalationtoPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.sevenMonthEscalationtoPIODate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="sevenMonthEscalationtoPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Results Sent to PIO">
                     <input id="resultsSentToPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.resultsSentToPIODate}" pattern="MM/dd/yyyy"/>"/>
                     <div id="resultsSentToPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Results Approved by PIO">
                      <input id="resultsApprovedByPIODate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.resultsApprovedByPIODate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="resultsApprovedByPIODate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="CTRO Trial Comparison Review">
                     <c:out value="${row.ctroUserName}"/>                 	
                     <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy hh:mm aaa"/>
                 </display:column>
                 <display:column  title="CCCT Trial Comparison Review">
                     <c:out value="${row.ctroUserName}"/>                 	
                     <fmt:formatDate value="${row.ctroUserCreatedDate}" pattern="MM/dd/yyyy hh:mm aaa"/>
                 </display:column>
                 <display:column  title="Trial Comparison Approval">
                 	<c:out value="${row.ccctUserName}"/>
                    <fmt:formatDate value="${row.ccctUserCreatedDate}" pattern="MM/dd/yyyy hh:mm aaa"/>
                 </display:column>
                 <display:column  title="PRS Release Date">
                      <input id="prsReleaseDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.prsReleaseDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="prsReleaseDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="QA Comments Returned Date">
                      <input id="qaCommentsReturnedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.qaCommentsReturnedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="qaCommentsReturnedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
                 <display:column  title="Trial Results Published Date">
                      <input id="trialPublishedDate_${row.studyProtocolId}" class="datePicker"  size="8" value="<fmt:formatDate value="${row.trialPublishedDate}" pattern="MM/dd/yyyy"/>"/>
                      <div id="trialPublishedDate_${row.studyProtocolId}_flash" class="flash" align="center">Saved!</div>
                 </display:column>
             </display:table>
         </div>
       </s:if>
       <div id="chart" class="box">
          <h2><fmt:message key="resultsdashboard.moreinfo"/></h2>
         <table class="results">
          <tr>
           <td align="center" width="40%" style="border-bottom: 2px solid black;border-left:  2px solid black;" >
             <h4><fmt:message key="resultsdashboard.chartHeader"/></h4><br>
             <canvas id="resultsChart" width="300" height="300"></canvas>
            </td>
           <td width="10%" style="border-bottom: 2px solid black;border-right: 2px solid black; vertical-align: middle">
             <div id="legend"></div>
           </td>
           <td  width="50%" height="100%" style="border-bottom: 2px solid black;">
            <table class="results" height="350px">
              <tr height="10%" style="border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.designee"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="designeeTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="designeeTrialId" name="designeeTrialId" size="20"/>
                 </td>
                 <td >
               <s:a id="designeeTrialIdSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingContactexecute.action', jQuery('#designeeTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.trialCompDocs"/></h4></td>
              </tr>
              <tr  height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="trialCompDocsTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="trialCompDocsTrialId" name="trialCompDocsTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="trialCompDocsTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingDocumentquery.action', jQuery('#trialCompDocsTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.coverSheet"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black;">
                <td style="padding: 10px;">
                 <label for="coverSheetTrialId" > <fmt:message key="resultsdashboard.trialId"/></label><span class="required">*</span> <input type="text" id="coverSheetTrialId" name="coverSheetTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="coverSheetTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingCoverSheetquery.action', jQuery('#coverSheetTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
              <tr height="10%" style="border-top: 2px solid black;border-right: 2px solid black;">
                <td colspan="2"><h4><fmt:message key="resultsdashboard.uploadErrors"/></h4></td>
              </tr>
              <tr height="15%" style="border-right: 2px solid black">
                <td style="padding: 10px;">
                 <label for="uploadErrorsTrialId" ><fmt:message key="resultsdashboard.trialId"/></label> <input type="text" id="uploadErrorsTrialId" name="uploadErrorsTrialId" size="20"/>
                 </td>
                 <td>
                 <s:a id="uploadErrorsTrialSearch" href="javascript:void(0)" cssClass="btn" onclick="searchResults('resultsReportingActionsTakenview.action', jQuery('#uploadErrorsTrialId').val())"><span class="btn_img"><span class="search">Search</span></span></s:a>
                </td>
              </tr>
            </table>
           </td>
         </tr>
         </table>
       </div>
    </body>
</html>
