<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialStatus.title" /></title>
<s:head />
<script type="text/javascript" src="scripts/js/calendarpopup.js" />  
<script type="text/javascript" src="scripts/js/prototype.js"></script>
<script type="text/javascript" src="scripts/js/scriptaculous.js"></script>

<script type="text/javascript">
            var cal = new CalendarPopup();
            var cal1 = new CalendarPopup();
            var cal2 = new CalendarPopup();
     </script>
</head>


<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
<h1><fmt:message key="trialStatus.title" /></h1>

<!--Help Content-->
<!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
<div class="summarybox">
						
			<div class="summarytitle">
				<span class="value"><strong> <c:out value="${sessionScope.trialSummary.nciIdentifier }"/></strong>:
				  <c:out value="${sessionScope.trialSummary.officialTitle }"/>
				 </span>
			</div>
							
			<div class="float33_first">
				<div class="row">
					<span class="label"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>:</span> 
					<span class="value"></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.leadOrganization"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.leadOrganizationName }"/></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.principalInvestigator"/>:</span> 
					<span class="value"> <c:out value="${sessionScope.trialSummary.piFullName }"/></span>
				</div>
				<div class="row">
					<span class="label">Trial Submitter:</span> 
					<span class="value"></span>
				</div>
			</div>
							
			<div class="float33">
				<div class="row">
					<span class="label"> <fmt:message key="studyProtocol.studyStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/></span>
				</div>
				<div class="row">
					<span class="label"><fmt:message key="studyProtocol.documentWorkflowStatus"/>:</span> 
					<span class="value"><c:out value="${sessionScope.trialSummary.documentWorkflowStatusCode.code }"/></span>
				</div>
			</div>
				
			<div class="clear"></div>
							
   </div>
<div class="box"><s:form>
    <s:actionerror />
<h2><fmt:message key="trialStatus.title" /></h2>
	<table class="form">
        <%--  <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> --%>
        
        <tr>
            <td scope="row" class="label"><label for="currentTrialStatus"><fmt:message
                key="trialStatus.current.trial.status" /></label></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td class="value"><s:select headerKey="" headerValue="All"
                name="trialStatusData.currentTrialStatus"
                list="#currentTrialStatusValues"
                value="trialStatusData.currentTrialStatus" /></td>
            <td><s:submit value="Status History"
                action='studyOverallStatusHistory' cssClass="button" /></td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="currentTrialStatusDate"><fmt:message
                key="trialStatus.current.trial.status.date" /></label></td>
            <td class="value"><s:textfield name="currentTrialStatusDate"
                maxlength="10" size="10" value="" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal.select(document.forms[0].currentTrialStatusDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a></td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="trialStartDate"><fmt:message
                key="trialStatus.trial.start.date" /></label></td>
            <td class="value"><s:textfield name="trialStartDate"
                maxlength="10" size="10" value="" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal1.select(document.forms[0].trialStartDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a><s:radio name="checkStartDate" list="trialStartDate" /></td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="trialStatusCompletionDate">
            <fmt:message key="trialStatus.primary.completion.date" /></label></td>
            <td class="value"><s:textfield name="trialStatusCompletionDate"
                maxlength="10" size="10" value="" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal.select(document.forms[0].trialStatusCompletionDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a><s:radio name="checkPrimaryCompletionDate"
                list="primaryCompletionDate" /></td>
        </tr>
        <!-- <td colspan="2">
          <s:submit value="Save"  action='studyOverallStatusUpdate' cssClass="button" />
          <s:submit value="Next" action='nciSpecificInformation' cssClass="button" />
        </td> -->
    </table>
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><a href="studyOverallStatusUpdate.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="save">Save</span></span></a></li>
			<li><a href="regulatoryInfo.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
			<li><a href="nciSpecificInformation.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
		</ul>	
	</del>
</div>
</s:form>
</div>
</body>
</html>