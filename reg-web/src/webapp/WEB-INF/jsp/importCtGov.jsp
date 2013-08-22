<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="importctgov.title" /></title>
<s:head />
<script type="text/javascript" language="javascript">
	function handleAction(action) {
		$('importCtGovForm').action = "searchTrial" + action + ".action";
		$('importCtGovForm').submit();
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction();
			return false;
		}
	}
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="importctgov.title" />
	</h1>
	<c:set var="topic" scope="request" value="importctgov" />
	<div class="box" id="filters">
		<s:form id="importCtGovForm">
			<s:token name="struts.token.importctgov" />
			<s:hidden name="searchPerformed"></s:hidden>
			<s:if test="hasActionErrors()">
				<div class="error_msg">
					<s:actionerror />
				</div>
			</s:if>
			<reg-web:failureMessage />
			<reg-web:sucessMessage />
			<c:if test="${searchPerformed}">
				<p align="justify" class="info">Sorry, no match was found in
					CTRP system using the NCT identifier specified. However, a match
					has been found in ClinicalTrials.gov. Please review the following
					trial details and click 'Import Trial From ClinicalTrials.gov'
					button if you wish to proceed and register this trial in CTRP
					system. Otherwise, click 'Cancel' to stop.</p>

				<div id="searchResults">
					<s:if test="study==null">
						<div align="center">No studies found.</div>
					</s:if>
					<s:if test="study!=null">
						<s:hidden id="nctIdToImport" name="nctIdToImport"
							value="%{study.nctId}" />
						<h2>Studies on ClinicalTrials.gov</h2>
						<s:set name="studies" value="study" scope="request" />
						<display:table class="data" sort="list" pagesize="10" uid="row"
							name="studies" export="false"
							requestURI="importCtGovquery.action">
							<display:setProperty name="basic.msg.empty_list"
								value="No studies found." />
							<display:column escapeXml="true" title="NCT ID" property="nctId" />
							<display:column escapeXml="true" title="Status  "
								property="status" />
							<display:column escapeXml="false" title="Study">
								<c:out value="${row.title}" />
								<br />
								<br />
								<b>Condition(s)</b>: <c:out value="${row.conditions}" />
								<br />
								<b>Intervention(s)</b>: <c:out value="${row.interventions}" />
							</display:column>
						</display:table>
						<div class="actionsrow">
							<del class="btnwrapper">
								<ul class="btnrow">
									<li><s:a href="javascript:void(0)" cssClass="btn"
											onclick="handleAction('importTrial')">
											<span class="btn_img"><span class="search">Import
													Trial From ClinicalTrials.gov</span></span>
										</s:a> <c:url value="/protected/searchTrial.action" var="cancelUrl" />
										<s:a action="searchTrial.action" cssClass="btn">
											<span class="btn_img"><span class="cancel">Cancel</span></span>
										</s:a></li>
								</ul>
							</del>
						</div>
					</s:if>
				</div>
			</c:if>
		</s:form>
	</div>
</body>
</html>
