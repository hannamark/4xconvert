<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>

<script>
	function displaySubmitter(){
	    showPopWin('ajaxdisplaypersoninfoquery.action', 600, 300, '', 'Trial Submitter Information');
	}
	function displayPi(){
	    showPopWin('ajaxdisplaypersoninfoqueryPiInfo.action', 600, 300, '', 'Principal Investigator Information');
	}
	function displayCheckoutUser(){
		// currently not used. will be used with po-2163
	    showPopWin('ajaxdisplaypersoninfocheckoutUser.action', 600, 300, '', 'Checked Out User Information');
	}
</script>

<div class="summarybox">

			<div class="summarytitle">
				<span class="value"><strong> <c:out value="${sessionScope.trialSummary.nciIdentifier }"/></strong>:
				  <c:out value="${sessionScope.trialSummary.officialTitle }"/>
				 </span>
			</div>

			<div class="float33_first">
				<div class="row">
					<span class="label"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>:</span>
					<span class="value"><c:out value="${sessionScope.trialSummary.localStudyProtocolIdentifier }"/></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.leadOrganization"/>:</span>
					<span class="value"><c:out value="${sessionScope.trialSummary.leadOrganizationName }"/></span>
				</div>
				<c:if test="${sessionScope.trialSummary.amendmentNumber != null}">
                    <div class="row">
                        <span class="label"><fmt:message key="studyProtocol.amendmentNumber"/>:</span>
                        <span class="value"><c:out value="${sessionScope.trialSummary.amendmentNumber }"/></span>
                    </div>
                </c:if>
                       <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
                    <div class="row">
                        <span class="label"><fmt:message key="studyProtocol.trialCategory"/></span>
                        <span class="value"><c:out value="${sessionScope.trialSummary.trialCategory }" /></span>
                    </div>
                </c:if>
			</div>

			<div class="float33">
				<c:if test="${sessionScope.trialSummary.piFullName != null }">
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.principalInvestigator"/>:</span>
					<span class="value"><a href="javascript:displayPi();"> <c:out value="${sessionScope.trialSummary.piFullName }"/></a></span>
				</div>
				</c:if>
				<div class="row">
					 <span class="label">Trial Submitter:</span>
					<span class="value"><a href="javascript:displaySubmitter();"><c:out value="${sessionScope.trialSummary.userLastCreatedUsername }"/></a></span>
				</div>
			    <c:if test="${sessionScope.trialSummary.amendmentDate != null}">
                    <div class="row">
                        <span class="label"><fmt:message key="studyProtocol.amendmentDate"/>:</span>
                        <span class="value"><fmt:formatDate value="${sessionScope.trialSummary.amendmentDate }" dateStyle="short"/></span>
                    </div>
                </c:if>
			</div>

			<div class="float33">
			   <c:if test="${sessionScope.trialSummary.studyStatusCode.code != null}">
				<div class="row">
					<span class="label"> <fmt:message key="studyProtocol.studyStatus"/>:</span>
					<span class="value"><c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/></span>
				</div>
				</c:if>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.documentWorkflowStatus"/></span>
					<span class="value"><c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/></span>
				</div>
			    <c:if test="${sessionScope.trialSummary.studyCheckoutBy != null}">
                    <div class="row">
                        <span class="label"><fmt:message key="studyProtocol.checkOutBy"/>:</span>
                        <span class="value"><c:out value="${sessionScope.trialSummary.studyCheckoutByUsername }"/></span>
                    </div>
                </c:if>
			</div>
			<div class="clear"></div>

   </div>