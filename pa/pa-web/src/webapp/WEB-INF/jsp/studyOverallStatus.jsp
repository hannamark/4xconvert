<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialStatus.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<c:url value="/protected/studyOverallStatushistorypopup.action" var="lookupUrl" />

<script type="text/javascript">
    addCalendar("Cal1", "Select Date", "statusDate", "studyOverallStatus");
    addCalendar("Cal2", "Select Date", "startDate", "studyOverallStatus");
    addCalendar("Cal3", "Select Date", "completionDate", "studyOverallStatus");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");

    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();         
    }
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Status History');
    }   
    function statusChange() {
        var newStatus=document.getElementById('currentTrialStatus').value;
        if((newStatus=="Administratively Complete")
           || (newStatus=="Withdrawn")
           || (newStatus=="Temporarily Closed to Accrual")
           || (newStatus=="Temporarily Closed to Accrual and Intervention")) {
          document.getElementById('statusReason').disabled=false;
          document.getElementById('statusReason').readonly=false;
        } else {
          document.getElementById('statusReason').disabled=true;
          document.getElementById('statusReason').readonly=true;
        }
    }
    function handleAction() {
        input_box=confirm("Click OK to save changes or Cancel to Abort.");
        if (input_box==true){
          document.studyOverallStatus.action="studyOverallStatusupdate.action";
          document.studyOverallStatus.submit();
        }
    }    
</script>
     
</head>
<body>
<h1><fmt:message key="trialStatus.title" /></h1>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="review_status"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstract_status"/>
</c:if>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<s:form name="studyOverallStatus">
    <s:if test="hasActionErrors()">
    <div class="error_msg"><s:actionerror/></div>
    </s:if>
    <h2><fmt:message key="trialStatus.title" /></h2>
    <table class="form"><tr><td width="0">
    <table>
        <tr>
            <td class="label"><s:label for="currentTrialStatus"><fmt:message
                key="trialStatus.current.trial.status" /></s:label><span class="required">*</span></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td class="value"><s:select onchange="statusChange()" onfocus="statusChange()"
                id="currentTrialStatus"
                list="#currentTrialStatusValues" /></td>
            <td>
            	<ul class="btnrow">			
					<li style="padding-left:0"><a href="#" class="btn" onclick="lookup()"><span class="btn_img"><span class="history">History</span></span></a></li>
				</ul>
            </td>
        </tr>
        <tr>
            <td class="label"><s:label for="statusDate"><fmt:message
                key="trialStatus.current.trial.status.date" /></s:label><span class="required">*</span></td>
            <td colspan="2" class="value"><s:textfield name="statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
            </td>
        </tr>
        <tr> 
            <td/>
            <td class="info" colspan="2">Administratively Complete, Withdrawn, and Temporarily Closed statuses only</td>
        </tr>
        <tr>
            <td class="label"><s:label name="statusReasonLabel" for="statusReason">
                <fmt:message key="trialStatus.current.trial.status.reason"/></s:label></td>
            <td colspan="2" class="value"><s:textarea id="statusReason" rows="3"
                cssStyle="width:280px;float:left" /></td>
        </tr>        
        <tr><td>&nbsp</td></tr>
        <tr>
            <td class="label"><s:label for="startDate"><fmt:message
                key="trialStatus.trial.start.date" /></s:label><span class="required">*</span></td>
            <td  colspan="2" class="value"><s:textfield name="startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="startDateType" list="dateTypeList" /></td>
        </tr>
        <tr>
            <td class="label"><s:label for="completionDate">
                <fmt:message key="trialStatus.primary.completion.date" /></s:label><span class="required">*</span></td>
            <td  colspan="2" class="value"><s:textfield name="completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="completionDateType" list="dateTypeList" /></td>
        </tr>
    </table>
    </td></tr></table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
             <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
             					|| (sessionScope.role == 'SuAbstractor')}">
             <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
             </c:if>
			<!-- 
			<c:choose>
                <c:when test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
				<li><a href="trialDocumentquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
				<li><a href="trialFundingquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
			</c:when>
			<c:otherwise>
            	<li><a href="trialIndidequery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            	<li><a href="trialFundingquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
			</c:otherwise>
			</c:choose>
			 -->
        </ul>   
    </del>
</div>
</s:form>
</div>
</body>
</html>