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
		if (document.getElementById('fdaindid').value == 'No'){
			document.getElementById('sec801id').value ='No';
			document.getElementById('indideid').value ='No';
			document.getElementById('delpostindid').value ='No';
			hideRow(document.getElementById('sec801row'));
		} else {
			showRow(document.getElementById('sec801row'));
		}
			
		if (document.getElementById('sec801id').value == 'No') {	
			hideRow(document.getElementById('indiderow'));
			document.getElementById('indideid').value ='No';
			document.getElementById('delpostindid').value ='No';
		} else {
			showRow(document.getElementById('indiderow'));
		}			
		if (document.getElementById('indideid').value == 'No') {
			document.getElementById('delpostindid').value ='No';
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
</script>
</head>
<body>
<!-- main content begins-->
<!-- <div id="contentwide"> -->
<h1><fmt:message key="regulatory.title" /></h1>
<div class="summarybox">
						
			<div class="summarytitle">
				<span class="value"><strong> <c:out value="${sessionScope.trialSummary.nciIdentifier }"/></strong>:
				  <c:out value="${sessionScope.trialSummary.officialTitle }"/>
				 </span>
			</div>
							
			<div class="float33_first">
				<div class="row">
					<span class="label"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>:</span> 
					<span class="value"></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.leadOrganization"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.leadOrganizationName }"/></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.principalInvestigator"/>:</span> 
					<span class="value"> <c:out value="${sessionScope.trialSummary.piFullName }"/></span>
				</div>
				<div class="row">
					<span class="label">Trial Submitter:</span> 
					<span class="value"></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"> <fmt:message key="studyProtocol.studyStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.documentWorkflowStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/></span>
				</div>
			</div>
				
			<div class="clear"></div>
							
   </div>
<div id="box">

<s:form action="saveRegAuthority" id="saveRegAuthority" theme="simple">
<s:actionerror/>
<h2><fmt:message key="regulatory.title" /></h2>
<!--Help Content--> 
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a> -->
<table class="form">
<%-- <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> --%>

	<!-- <tr>
		<th colspan="2">Regulatory Information</th>
	</tr> -->
	<!--  Trial Oversight Authority Country -->
	<tr>
		<td scope="row" class="label">Trial	Oversight Authority Country :</td>
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
		<td scope="row" class="label"><fmt:message key="regulatory.oversight.auth.name"/></td>
		<td class="value">
			<s:url id="d_url" action="getAuthOrgsAjax" />
			<s:div id="details" href="%{d_url}" theme="ajax" listenTopics="show_detail" formId="saveRegAuthority"></s:div>
		</td>
	</tr>
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row"  class="label"><fmt:message key="regulatory.FDA.regulated.interv.ind"/> </td>
		<td class="value"><s:select  id="fdaindid" name="regulatoryDTO.fdaRegulatedInterventionIndicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	<!--   Section 801 Indicator-->
	<tr id="sec801row">
		<td scope="row" class="label"><label><fmt:message key="regulatory.section801.ind"/></td>
		<td class="value"><s:select id="sec801id" name="regulatoryDTO.section801Indicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkAll();"/></td>
	</tr>
	<!--  IND/IDE Indicator-->
	<tr id="indiderow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.ideind.indicator"/></td>
		<td class="value"><s:select id="indideid" name="regulatoryDTO.indideTrialIndicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkAll();"/></td>		
	</tr>	
	<!--   Delayed Posting Indicator-->
	<tr id="delpostindrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.delayed.posting.ind"/></td>
		<td class="value"><s:select id="delpostindid" name="regulatoryDTO.delayedPostingIndicator" list="#{'No':'No', 'Yes':'Yes'}" onchange="checkAll();"/></td>		
	</tr>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr id="datamonrow">
		<td scope="row" class="label"><label><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
		<td class="value"><s:select id="datamonid" name="regulatoryDTO.dataMonitoringIndicator" list="#{'No':'No', 'Yes':'Yes'}"/></td>		
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
			<li><a href="saveRegAuthority.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="save">Save</span></span></a></li>
			<li><a href="nciSpecificInformation.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
			<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
		</ul>	
	</del>
</div>
</s:form>
</div>
<!-- </div> -->
</body>
</html>
