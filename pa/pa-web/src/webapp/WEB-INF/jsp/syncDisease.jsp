<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
					value="currentDisease.ntTermIdentifier" /></td>
			<td width="150px"><s:property value="disease.ntTermIdentifier" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">CDR Identifier:<span
				class="required">*</span></td>
			<td><s:property value="currentDisease.code" /></td>
			<td><s:property value="disease.code" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">Preferred Name:<span
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
			<td scope="row" class="label">Menu Display Name:<span
				class="required">*</span></td>
			<td><s:property value="currentDisease.menuDisplayName" /></td>
			<td><s:property value="disease.menuDisplayName" /></td>
		</tr>
		<tr>
			<td scope="row" class="label">Synonyms:</td>
			<td><s:if
					test="%{currentDisease.alterNameList.size() != disease.alterNameList.size()}">
					<font color="red"><strong>
				</s:if> <s:iterator value="currentDisease.alterNameList" status="alt">
					<s:property />
					<s:if test="!#alt.last">
						<br>
					</s:if>
				</s:iterator> <s:if
					test="%{currentDisease.alterNameList.size() != disease.alterNameList.size()}">
					</strong>
					</font>
				</s:if></td>
			<td><s:iterator value="disease.alterNameList" status="alt">
					<s:property />
					<s:if test="!#alt.last">
						<br>
					</s:if>
				</s:iterator></td>
		</tr>
		<tr>
			<td scope="row" class="label">Parent Terms:</td>
			<td><s:if
					test="%{currentDisease.parentTermList.size() != disease.parentTermList.size()}">
					<font color="red"><strong>
				</s:if> <s:iterator value="currentDisease.parentTermList" status="parent">
					<s:property />
					<s:if test="!#parent.last">
						<br>
					</s:if>
				</s:iterator> <s:if
					test="%{currentDisease.parentTermList.size() != disease.parentTermList.size()}">
					</strong>
					</font>
				</s:if></td>
			<td><s:iterator value="disease.parentTermList"
					status="parent">
					<s:property />
					<s:if test="!#parent.last">
						<br>
					</s:if>
				</s:iterator></td>
		</tr>
		<tr>
			<td scope="row" class="label">Child Terms:</td>
			<td><s:if
					test="%{currentDisease.childTermList.size() != disease.childTermList.size()}">
					<font color="red"><strong>
				</s:if> <s:iterator value="currentDisease.childTermList" status="child">
					<s:property />
					<s:if test="!#child.last">
						<br>
					</s:if>
				</s:iterator> <s:if
					test="%{currentDisease.childTermList.size() != disease.childTermList.size()}">
					</strong>
					</font>
				</s:if></td>
			<td><s:iterator value="disease.childTermList" status="child">
					<s:property />
					<s:if test="!#child.last">
						<br>
					</s:if>
				</s:iterator></td>
		</tr>
	</table>
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