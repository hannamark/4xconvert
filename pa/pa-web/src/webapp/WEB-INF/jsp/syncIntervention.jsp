<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.collections.CollectionUtils"%>
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:set name="action" value="action" />
<title><fmt:message
		key="manageTerms.syncIntervention.page.title" /></title>
<s:head />
</head>
<body>
	<h1>
		<fmt:message key="manageTerms.syncIntervention.page.title" />
	</h1>
	<c:set var="topic" scope="request" value="mangeterms" />
	<pa:failureMessage />
	<table class="form">
		<thead>
			<tr>
				<th>Attribute</th>
				<th>Value in CTRP</th>
				<th>Value in NCIt</th>
			</tr>
		</thead>
		<tr>
			<td scope="row" class="label">NCIt Identifier:<span
				class="required">*</span></td>
			<td width="150px"><s:property
					value="currentIntervention.ntTermIdentifier" /></td>
			<td width="150px"><s:property
					value="intervention.ntTermIdentifier" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">CDR Identifier:</td>
            <td><s:property value="currentIntervention.identifier" /></td>
			<td><s:property value="intervention.identifier" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">Preferred Name:<span
				class="required">*</span></td>
            <td><s:if test="%{currentIntervention.name != intervention.name}"><font color="red"><strong></s:if><s:property value="currentIntervention.name" /><s:if test="%{currentIntervention.name != intervention.name}"></strong></font></s:if></td>
			<td><s:property value="intervention.name" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">Synonyms:</td>
            <td><s:if test="%{currentIntervention.alterNames.size() != intervention.alterNames.size()}"><font color="red" ><strong></s:if>
            <s:iterator value="currentIntervention.alterNames" status="alt">
                <s:property /><s:if test="!#alt.last"><br></s:if>
            </s:iterator>
            <s:if test="%{currentIntervention.alterNames.size() != intervention.alterNames.size()}"></strong></font></s:if></td>
			<td><s:iterator value="intervention.alterNames" status="alt">
                <s:property /><s:if test="!#alt.last"><br></s:if>
            </s:iterator>
		</tr>
		<tr>
			<td scope="row" class="label">Cancer.gov Type:</td>
            <td><s:property value="currentIntervention.type" /></td>
			<td><s:property value="intervention.type" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">ClinicalTrials.gov Type:</td>
            <td><s:property value="currentIntervention.ctGovType" /></td>
			<td><s:property value="intervention.ctGovType" /></td>
		</tr>
	</table>
	<div class="actionsrow">
		<del class="btnwrapper">
			<ul class="btnrow">
				<li><s:a href="manageTermssyncIntervention.action" cssClass="btn">
						<span class="btn_img"><span class="save">Sync Term</span></span>
					</s:a> 
					<s:a href="manageTerms.action" cssClass="btn">
						<span class="btn_img"><span class="cancel">Cancel</span></span>
					</s:a></li>
			</ul>
		</del>
	</div>
</body>
</html>