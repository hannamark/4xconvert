<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="gov.nih.nci.pa.util.ActionUtils"%>
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="pagePrefix" value="disclaimer." />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:set name="action" value="action" />
<title><fmt:message key="manageTerms.syncDisease.page.title" /></title>
<s:head />
</head>
<body>
	<h1>
		<fmt:message key="manageTerms.syncDisease.page.title" />
	</h1>
	<c:set var="topic" scope="request" value="syncdisease" />
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
			<td scope="row" width="20%"><label>NCIt Identifier:</label><span
				class="required">*</span></td>
			<td width="150px"><s:property
					value="currentDisease.ntTermIdentifier" /></td>
			<td width="150px"><s:property value="disease.ntTermIdentifier" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>CDR Identifier:</label></td>
			<td><s:property value="currentDisease.code" /></td>
			<td><s:property value="disease.code" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Preferred Name:</label><span
				class="required">*</span></td>
			<td><s:if
					test="%{currentDisease.preferredName != disease.preferredName}">
					<font color="red"><strong>
				</s:if>
				<s:property value="currentDisease.preferredName" />
				<s:if
					test="%{currentDisease.preferredName != disease.preferredName}">
					</strong>
					</font>
				</s:if></td>
			<td><s:property value="disease.preferredName" /></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Display Name:</label></td>
			<td><s:property value="currentDisease.menuDisplayName" /></td>
			<td><s:property value="disease.menuDisplayName" /></td>
		</tr>
		 <% List<String> newAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.alterNameList");
		    List<String> currentAltnames = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.alterNameList"); 
		    List<String> newParentTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.parentTermList");
            List<String> currentParentTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.parentTermList");
            List<String> newChildTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("disease.childTermList");
            List<String> currentChildTerms = (List<String>) ActionContext.getContext().getValueStack().findValue("currentDisease.childTermList");
            %>
		<tr>
			<td scope="row"  width="20%"><label>Synonyms:</label></td>
			<td><%= ActionUtils.generateListDiffStringForManageTerms(currentAltnames, newAltnames ) %></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(newAltnames, currentAltnames ) %></td>
		</tr>
		<tr>
		   
			<td scope="row"  width="20%"><label>Parent Terms:</label></td>
			<td> <%= ActionUtils.generateListDiffStringForManageTerms(currentParentTerms, newParentTerms ) %></td>
			<td><%= ActionUtils.generateListDiffStringForManageTerms(newParentTerms, currentParentTerms ) %></td>
		</tr>
		<tr>
			<td scope="row"  width="20%"><label>Child Terms:</label></td>
			<td> <%= ActionUtils.generateListDiffStringForManageTerms(currentChildTerms, newChildTerms ) %></td>
            <td><%= ActionUtils.generateListDiffStringForManageTerms(newChildTerms, currentChildTerms ) %></td>
		</tr>
	</table>
	<div align="center"><span class="info">Note: 'CDR Identifier', 'Display Name', 'Parent Terms' and 'Child Terms' attributes are NOT synchronized from NCIt, their existing CTRP values shown above will be retained.</span></div>
	<div class="actionsrow">
		<del class="btnwrapper">
			<ul class="btnrow">
				<li><s:a href="manageTermssyncDisease.action" cssClass="btn">
						<span class="btn_img"><span class="save">Sync Term</span></span>
					</s:a> <s:a href="manageTerms.action" cssClass="btn">
						<span class="btn_img"><span class="cancel">Cancel</span></span>
					</s:a></li>
			</ul>
		</del>
	</div>
</body>
</html>