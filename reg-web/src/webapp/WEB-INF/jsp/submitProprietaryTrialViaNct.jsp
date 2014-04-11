<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
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
	<h1 class="heading"><span><fmt:message key="importctgov.title" /></span></h1>
	<c:set var="topic" scope="request" value="submittrial" />
		<s:form id="importCtGovForm" cssClass="form-horizontal" role="form">
			<s:token name="struts.token.importctgov" />
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger">
					<s:actionerror />
				</div>
			</s:if>

			<reg-web:failureMessage />
			<reg-web:sucessMessage />

            <p align="center" class="info">To register a trial under the Industrial/Other submission category in CTRP, please enter the ClinicalTrials.gov identifier below and click <b>Search Studies</b>.
                If you do not have the ClinicalTrials.gov identifier or if the trial does not have one yet then please contact CTRO staff at <a href="mailto:ncictro@mail.nih.gov">ncictro@mail.nih.gov</a>.
            </p>
            <br/>
            <div class="form-group">
              <label for="nctID" class="col-xs-4 control-label"><fmt:message key="studyProtocol.nctNumber" /></label>                    
              <div class="col-xs-3">
              	  <s:textfield id="nctID" name="nctID" required="true" maxlength="16" cssClass="form-control" />
              	  <em><b>Note:</b> Any trials imported using this feature will be registered as Abbreviated in CTRP system.</em>
              </div>
              <div class="col-xs-4">
                  <button type="button" href="javascript:void(0)" class="btn btn-icon btn-default" onclick="handleAction('searchByNct')"><i class="fa-search"></i>Search Studies</button>
               </div>
          </div>
			
		</s:form>
