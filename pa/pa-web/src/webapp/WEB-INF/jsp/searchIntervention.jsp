<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="gov.nih.nci.pa.action.ManageTermsAction"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<s:set name="action" value="action" />
<title><fmt:message key="manageTerms.lookupIntrv.page.title" /></title>
<s:head />
</head>
<body>
	<c:set var="topic" scope="request" value="importintervention" />
	<div id="box">
		<h1>
			<fmt:message key="manageTerms.lookupIntrv.page.title" />
		</h1>
		<pa:failureMessage />

		<s:form name="lookupintrvForm" method="post"
			action="manageTermssearchIntervention.action">
			<s:hidden name="importTerm" />
			<table class="form">
				<tr>
					<td colspan="3"><span class="info"><fmt:message key="manageTerms.lookup.helpmessage" /> </span></td>
				</tr>
				<tr>
					<td scope="row" class="label"><label for="ntTermIdentifier">
							NCIt Identifier</label> <span class="required">*</span></td>
					<td width="150px"><s:textfield id="ntTermIdentifier"
							name="intervention.ntTermIdentifier" maxlength="10" size="10"
							cssStyle="width:150px" /></td>
					<td><s:a href="javascript:void(0)" cssClass="btn"
							onclick="document.forms[0].submit()">
							<span class="btn_img"><span class="search">Lookup</span></span>
						</s:a></td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>