<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:head theme="ajax" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><fmt:message key="regulatory.title" /></title>
<script type="text/javascript">	
	window.onload=checkAll;
	/**/
	function checkAll(){
		if (document.getElementById('fdaindid').value == 'false'){
			document.getElementById('sec801id').value ='false';
			document.getElementById('indideid').value ='false';
			document.getElementById('delpostindid').value ='false';
			hideRow(document.getElementById('sec801row'));
		} else {
			showRow(document.getElementById('sec801row'));
		}
			
		if (document.getElementById('sec801id').value == 'false') {	
			hideRow(document.getElementById('indiderow'));
			document.getElementById('indideid').value ='false';
			document.getElementById('delpostindid').value ='false';
		} else {
			showRow(document.getElementById('indiderow'));
		}			
		if (document.getElementById('indideid').value == 'false') {
			document.getElementById('delpostindid').value ='false';
			hideRow(document.getElementById('delpostindrow'));
		} else {
			showRow(document.getElementById('delpostindrow'));
		}
	}
	/**/
	function hideRow(row){			
		row.style.display = 'none';	
	}
	function showRow(row){
		row.style.display = '';
	}
	/*
	Ajax calls to populate authority
	*/
	function show_details() {
		dojo.event.topic.publish("show_detail");
	}
	function handleAction(){
		document.regulatoryInfoupdate.action="regulatoryInfoupdate.action";
		document.regulatoryInfoupdate.submit();
	}
</script>
</head>
<body>
<!-- main content begins-->
<!-- <div id="contentwide"> -->
<h1><fmt:message key="regulatory.title" /></h1>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>

<s:form action="regulatoryInfoupdate" id="saveRegAuthority" theme="simple">
<s:actionerror/>
<h2><fmt:message key="regulatory.title" /></h2>
<!--Help Content--> 
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a> -->
<table class="form">
<%-- <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>

	<!-- <tr>
		<th colspan="2">Regulatory Information</th>
	</tr> -->
	<!--  Trial Oversight Authority Country -->
	<tr>
		<td scope="row" class="label"><label><fmt:message key="regulatory.oversight.country.name"/></td>
		<td class="value"><s:select id="countries"
					 name="lst"                   
                     list="countryList"  
                     listKey="id" listValue="name"                    
                     onchange="javascript:show_details();return false;"                
                     />	
         </td>      
	</tr>
	<!--  Trial Oversignt Authority Organization Name -->
	<tr>
		<td scope="row" class="label"><label><fmt:message key="regulatory.oversight.auth.name"/></td>
		<td class="value">
			<s:url id="d_url" action="getAuthOrgsAjax" />
			<s:div id="details" href="%{d_url}" theme="ajax" listenTopics="show_detail" formId="saveRegAuthority"></s:div>
		</td>
	</tr>
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row"  class="label"><label><fmt:message key="regulatory.FDA.regulated.interv.ind"/> </td>
		<td class="value"><s:select  id="fdaindid" name="webDTO.fdaRegulatedInterventionIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	<!--   Section 801 Indicator-->
	<tr id="sec801row">
		<td scope="row" class="label"><label><fmt:message key="regulatory.section801.ind"/></td>
		<td class="value"><s:select id="sec801id" name="webDTO.section801Indicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	<!--  IND/IDE Indicator-->
	<tr id="indiderow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.ideind.indicator"/></td>
		<td class="value"><s:select id="indideid" name="webDTO.ideTrialIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>		
	</tr>	
	<!--   Delayed Posting Indicator-->
	<tr id="delpostindrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.delayed.posting.ind"/></td>
		<td class="value"><s:select id="delpostindid" name="webDTO.delayedPostingIndicator" list="#{'false':'No', 'true':'Yes'}" onchange="checkAll();"/></td>		
	</tr>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr id="datamonrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
		<td class="value"><s:select id="datamonid" name="webDTO.dataMonitoringIndicator" list="#{'false':'No', 'true':'Yes'}"/></td>		
	</tr>
	<tr>
		<td colspan="2" class="pad10">
			<div class="line"></div>
		</td>
	</tr>
	<!--  <tr>
		<td colspan="2" class="centered"><input type="submit" value="Save" class="button"></td>
	</tr>-->
	
</table>
	
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">			
			<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
			<li><a href="nciSpecificInformationquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
			<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
		</ul>	
	</del>
</div>
</s:form>
</div>
<!-- </div> -->
</body>
</html>
