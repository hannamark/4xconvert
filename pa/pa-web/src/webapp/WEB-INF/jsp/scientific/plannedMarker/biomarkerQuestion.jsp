<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="plannedMarker.details.title" />
</title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet"
	type="text/css" media="all" />
<script type="text/javascript"
	src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
</head>
<body>
	<h1>
		<fmt:message key="plannedMarker.question.to.submitter" />
	</h1>
	<s:url id="cancelUrl" namespace="/protected" action="bioMarkers" />
	<div class="box">
		<s:set var="submitUrl" value="'bioMarkerssendQuestionMail'" />
		<table class="form">
			<tr>
				<td colspan="2"><s:form id="plannedMarkerForm"
						action="%{#submitUrl}">
						<s:token />
						<pa:studyUniqueToken />
						<div id="plannedMarkerDetails">
							<table class="form">
								<s:hidden name="plannedMarker.id" />
								<tr>
									<td class="label"><s:label
											for="plannedMarker.nciIdentifier">
											<fmt:message key="plannedMarker.trial" />:</s:label></td>
									<td><s:hidden name="plannedMarker.nciIdentifier"
											id="nciIdentifier" /> <s:property
											value="plannedMarker.nciIdentifier" /></td>
								</tr>
								<tr>
									<td class="label"><s:label for="plannedMarker.name">
											<fmt:message key="plannedMarker.name" />:</s:label></td>
									<td><s:hidden name="plannedMarker.name" id="name" /> <s:property
											value="plannedMarker.name" /></td>
								</tr>
								<tr>
									<td class="label"><s:label
											for="plannedMarker.csmUserEmailId">
											<fmt:message key="plannedMarker.submitter" />:</s:label></td>
									<td><s:hidden name="plannedMarker.csmUserEmailId"
											id="csmUserEmailId" /> <s:property
											value="plannedMarker.csmUserEmailId" /></td>
								</tr>
								<tr>
									<td class="label"><s:label for="plannedMarker.status">
											<fmt:message key="plannedMarker.status" />:</s:label></td>
									<td><s:hidden name="plannedMarker.status" id="status" />
										<s:property value="plannedMarker.status" /></td>
								</tr>
								<tr>
									<td class="label"><s:label for="plannedMarker.question">
											<fmt:message key="plannedMarker.question" />
										</s:label></td>
									<td class="value"><s:textarea
											name="plannedMarker.question" rows="4" cssStyle="width:206px" />
									</td>
								</tr>
							</table>
						</div>
						<div class="actionsrow">
							<del class="btnwrapper">
								<ul class="btnrow">
									<li><s:a cssClass="btn" href="#"
											onclick="document.forms[0].submit();">
											<span class="btn_img">Submit</span>
										</s:a> <s:a href="%{cancelUrl}" cssClass="btn">
											<span class="btn_img"><span class="cancel">Cancel</span>
											</span>
										</s:a></li>
								</ul>
							</del>
						</div>
					</s:form></td>
			</tr>
		</table>
	</div>
</body>
</html>