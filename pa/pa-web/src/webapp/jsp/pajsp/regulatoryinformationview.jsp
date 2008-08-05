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
<!--Help Content--> 
<a href="#" class="helpbutton"onclick="Help.popHelp('query_protocol')">Help</a>
<table cellspacing="2">
	<tr><td align=right>NCI accession number:</td><td align=left>NCI-2008-0002</td></tr>
	<tr><td align=right>Trial Title:</td><td align=left>A Phase I Trial of Taxol in refractory leukemia	in children</td></tr>
	<tr><td align=right>Abstraction Status:</td><td align=left>Accepted</td></tr>
	<tr><td align=right>Trial Submitter:</td><td align=left>Joe Smith</td></tr>
	<tr><td align=right>Principal Investigator:</td><td align=left>Mike Sholch</td></tr>
</table>
<table cellspacing="8">	
	<tr>
		<th colspan="2">Regulatory Information</th>
	</tr>
	<!--  IND Trial Indicator -->
	<tr>
		<td scope="row" class="label">IND Trial Indicator :</td>
		<td><s:textfield name="regulatoryDTO.indTrialIndicator" disabled="true"/></td>
	</tr>
	<!--  IDE Trial Indicator-->
	<tr>
		<td scope="row" class="label">IDE Trial Indicator :</td>
		<td><s:textfield name="regulatoryDTO.ideTrialIndicator" disabled="true"/></td>
	</tr>
	<!--  Trial Oversignt Authority Country -->	
	<tr>
		<td scope="row" class="label">Trial	Oversight Authority Country :</td>
		<td><s:textfield name="regulatoryDTO.trialOversgtAuthCountry" disabled="true"/></td>
                    
	</tr>
	<!--  Trial Oversignt Authority Organization Name -->
	<tr>
		<td scope="row" class="label">Trial	Oversight Authority Organization Name :</td>
		<td><s:textfield name="regulatoryDTO.trialOversgtAuthOrgName" disabled="true"/></td>
	</tr>
	<!--   FDA Regulated Intervention Indicator-->
	<tr>
		<td scope="row" class="label">FDA Regulated Intervention Indicator :</td>
		<td><s:textfield name="regulatoryDTO.fdaRegulatedInterventionIndicator" disabled="true"/></td>
	</tr>
	<!--   Section 801 Indicator-->
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Section	801 Indicator :</td>
		<td><s:textfield name="regulatoryDTO.section801Indicator" disabled="true"/></td>
	</tr>
	<!--   Delayed Posting Indicator-->
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Delayed	Posting Indicator :</td>
		<td><s:textfield name="regulatoryDTO.delayedPostingIndicator" disabled="true"/></td>	
	</tr>
	<!--   Data Monitoring Committee Appointed Indicator -->
	<tr>
		<td scope="row" class="label"><label for="localtrialIdentifier">Data Monitoring Committee Appointed Indicator :</td>
		<td><s:textfield name="regulatoryDTO.dataMonitoringIndicator" disabled="true"/></td>	
	</tr>

	<tr>
		<td colspan="2" class="pad10">
		<div class="line"></div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="centered"><input type="submit" value="Edit" class="button"></td>
	</tr>
</table>
</div>
</s:form>
</body>
</html>