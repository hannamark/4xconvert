<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><fmt:message key="regulatory.title" /></title>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet"	type="text/css" media="all" />
<s:head />
</head>
<body>
<!-- main content begins-->
<s:form action="regulatoryInfo">
<div id="contentwide">
<h1><fmt:message key="regulatory.title" /></h1>
<!--Help Content-->   <div id="box">
<a href="#" class="helpbutton"onclick="Help.popHelp('query_protocol')">Help</a>
 
<table width="880">	
<jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/>
	<tr>
		<th colspan="2">Regulatory Information</th>
	</tr>
	<!--  Trial Oversignt Authority Country -->	
	<tr>
		<td scope="row" class="label">Trial	Oversight Authority Country :</td>
		<td><s:label name="regulatoryDTO.trialOversgtAuthCountry" /></td>
                    
	</tr>
	<!--  Trial Oversignt Authority Organization Name -->
	<tr>
		<td scope="row" class="label">Trial	Oversight Authority Organization Name :</td>
		<td><s:label name="regulatoryDTO.trialOversgtAuthOrgName" /></td>
	</tr>
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row" class="label">FDA Regulated Intervention Indicator :</td>
		<td><s:label name="regulatoryDTO.fdaRegulatedInterventionIndicator" /></td>
	</tr>
	<!--   Section 801 Indicator-->
	<c:if test='${regulatoryDTO.fdaRegulatedInterventionIndicator eq "Yes"}'>
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Section	801 Indicator :</td>
		<td><s:label name="regulatoryDTO.section801Indicator" /></td>
	</tr>
	</c:if>
	<!-- IDE/IND indicator -->
	<c:if test='${regulatoryDTO.section801Indicator eq "Yes"}'>
		<tr>
			<td scope="row" class="label">IND/IDE Indicator :</td>
			<td><s:label name="regulatoryDTO.indideTrialIndicator" /></td>
		</tr>
	</c:if>
	<!--   Delayed Posting Indicator-->
	<c:if test='${regulatoryDTO.indideTrialIndicator eq "Yes"}'>
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Delayed	Posting Indicator :</td>
		<td><s:label name="regulatoryDTO.delayedPostingIndicator" /></td>	
	</tr>
	</c:if>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Data Monitoring Committee Appointed Indicator :</td>
		<td><s:label name="regulatoryDTO.dataMonitoringIndicator" /></td>	
	</tr>

	<tr>
		<td colspan="2" class="pad10">
		<div class="line"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="centered"><input type="submit" value="Edit" class="button"></td>
	</tr>
</table></div>
</div>
</s:form>
</body>
</html>