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
		$('importCtGovForm').action = "submitProprietaryTrial" + action
				+ ".action";
		$('importCtGovForm').submit();
	}

	document.onkeypress = runEnterScript;
	function runEnterScript(e) {
		var KeyID = (window.event) ? event.keyCode : e.keyCode;
		if (KeyID == 13) {
			handleAction('searchByNct');
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
	<c:set var="topic" scope="request" value="submittrial" />
	<div class="box" id="filters">
		<s:form id="importCtGovForm">
			<s:token name="struts.token.importctgov" />
			<s:if test="hasActionErrors()">
				<div class="error_msg">
					<s:actionerror />
				</div>
			</s:if>

			<reg-web:failureMessage />
			<reg-web:sucessMessage />

            <p class="info">To register a trial under the
                        Industrial/Other submission category in CTRP, please enter the
                        ClinicalTrials.gov identifier below and click <b>Search
                            Studies</b>. If you do not have the ClinicalTrials.gov identifier or
                        if the trial does not have one yet then please contact CTRO staff
                        at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a>.
            </p>

			<table class="form" style="width: 0%;">				
				<tr>
					<td nowrap="nowrap" scope="row" class="label"><label
						for="nctID"><fmt:message key="studyProtocol.nctNumber" /></label></td>
					<td nowrap="nowrap"><s:textfield id="nctID" name="nctID"
							required="true" maxlength="16" cssStyle="width:294px" /></td>
					<td nowrap="nowrap" scope="row"><del class="btnwrapper">
							<ul class="btnrow">
								<li><s:a href="javascript:void(0)" cssClass="btn"
										onclick="handleAction('searchByNct')">
										<span class="btn_img"><span class="search">Search
												Studies</span></span>
									</s:a></li>
							</ul>
						</del></td>
				</tr>
				<tr>
					<td></td>
					<td colspan="2" class="info"><b>Note:</b> Any trials imported
						using this feature will be registered as Abbreviated in CTRP
						system.</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>
