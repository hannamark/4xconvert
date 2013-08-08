<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="ctgov.import.logs.title" /></title>
<s:head />

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/showhide.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/scripts/js/control.tabs.js"/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

<script type="text/javascript" language="javascript">
	function handleAction(action) {
		$('ctGovImportLogsForm').action = "ctGovImportLog" + action + ".action";
		$('ctGovImportLogsForm').submit();
	}

	function resetValues() {
		$("logsOnOrAfter").value = "";
		$("logsOnOrBefore").value = "";
		$("ctGovImportLogsDiv").hide();
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction();
			return false;
		}
	}

	Event.observe(window, 'load', function() {
		addCalendar("Cal1", "Select Date", "logsOnOrAfter",
				"ctGovImportLogsForm");
		addCalendar("Cal2", "Select Date", "logsOnOrBefore",
				"ctGovImportLogsForm");
		setWidth(90, 1, 15, 1);
		setFormat("mm/dd/yyyy");
	});
</script>
</head>
<body>
	<!-- main content begins-->
	<h1>
		<fmt:message key="ctgov.import.logs.title" />
	</h1>
	<c:set var="topic" scope="request" value="ctgovimportlogs" />
	<div class="box" id="filters">
		<s:form id="ctGovImportLogsForm">
			<s:token name="struts.token.ctgovimportlogs" />
			<s:if test="hasActionErrors()">
				<div class="error_msg">
					<s:actionerror />
				</div>
			</s:if>
			<pa:failureMessage />
			<pa:sucessMessage />

			<table class="form" style="width: 0%;">
				<tr>
					<td scope="row" class="label"><label for="logsOnOrAfter"><fmt:message
								key="logs.startDate" />:</label></td>
					<td nowrap="nowrap"><s:textfield id="logsOnOrAfter"
							name="logsOnOrAfter" maxlength="10" size="10" /> <a
						href="javascript:showCal('Cal1')"> <img
							src="${pageContext.request.contextPath}/images/ico_calendar.gif"
							alt="Select Date" class="calendaricon" /></a>
					<td>&nbsp;&nbsp;</td>
					<td scope="row" class="label"><label for="logsOnOrBefore"><fmt:message
								key="logs.endDate" />:</label></td>

					<td nowrap="nowrap"><s:textfield id="logsOnOrBefore"
							name="logsOnOrBefore" maxlength="10" size="10" /> <a
						href="javascript:showCal('Cal2')"> <img
							src="${pageContext.request.contextPath}/images/ico_calendar.gif"
							alt="Select Date" class="calendaricon" />
					</a></td>
				</tr>
				<tr>
					<td colspan="5" class="info"><b>Note:</b> Leave both fields
						empty to display the entire log. In any case, the number of
						displayed records will be limited to 5,000 most recent ones of the
						time interval requested.</td>
				</tr>

			</table>
			<div class="actionsrow">
				<del class="btnwrapper">
					<ul class="btnrow">
						<li><s:a href="javascript:void(0)" cssClass="btn"
								onclick="handleAction('query')">
								<span class="btn_img"><span class="search">Display
										Log </span></span>
							</s:a> <s:a href="javascript:void(0)" cssClass="btn"
								onclick="resetValues()">
								<span class="btn_img"><span class="cancel">Reset </span></span>
							</s:a></li>
					</ul>
				</del>
			</div>

			<c:if test="${searchPerformed}">
				<div id="ctGovImportLogsDiv" align="center">
					<s:if test="ctGovImportLogs==null || ctGovImportLogs.empty">
						<div align="center" class="info">
							<b>No log entries found.</b>
						</div>
					</s:if>
					<s:else>

						<s:set name="logs" value="ctGovImportLogs" scope="request" />
						<display:table class="data" sort="list" pagesize="100" uid="row"
							defaultorder="descending" defaultsort="6" name="logs"
							export="true" requestURI="ctGovImportLogquery.action">
							<display:setProperty name="basic.msg.empty_list"
								value="No log entries found." />
							<display:setProperty name="export.xml" value="false" />
							<display:setProperty name="export.excel.filename"
								value="CTGovImportLogs.xls" />
							<display:setProperty name="export.excel.include_header"
								value="true" />
							<display:setProperty name="export.csv.filename"
								value="CTGovImportLogs.csv" />
							<display:setProperty name="export.csv.include_header"
								value="true" />

							<display:column escapeXml="true" title="NCI ID" property="nciID"
								sortable="true" />
							<display:column escapeXml="true" title="NCT ID" property="nctID"
								sortable="true" />
							<display:column escapeXml="true" title="Title" property="title"
								sortable="true" />
							<display:column escapeXml="true" title="Action" property="action"
								sortable="true" />
							<display:column escapeXml="true" title="Auto/User"
								property="userCreated" sortable="true" />
							<display:column  title="Date/Time" format="{0,date,MM/dd/yyyy hh:mm aaa}"
								property="dateCreated" sortable="true" />
							<display:column escapeXml="true" title="Import Status"
								property="importStatus" sortable="true" />
						</display:table>
					</s:else>
				</div>
			</c:if>
		</s:form>
	</div>
</body>
</html>
