<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialStatus.title" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/coppa.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
    addCalendar("Cal1", "Select Date", "statusDate", "studyOverallStatus");
    addCalendar("Cal2", "Select Date", "startDate", "studyOverallStatus");
    addCalendar("Cal3", "Select Date", "completionDate", "studyOverallStatus");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");

    function statusChange() {
        newStatus=document.studyOverallStatus.currentTrialStatus.value;
        if((newStatus=="Administratively Complete")
           || (newStatus=="Withdrawn")
           || (newStatus=="Temporarily Closed to Accrual")
           || (newStatus=="Temporarily Closed to Accrual and Intervention")) {
          document.studyOverallStatus.statusReason.disabled=false;
          document.studyOverallStatus.statusReason.readonly=false;
        } else {
          document.studyOverallStatus.statusReason.disabled=true;
          document.studyOverallStatus.statusReason.readonly=true;
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
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="trialStatus.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<s:form name="studyOverallStatus">
    <s:if test="hasActionErrors()">
    <div class="error_msg"><s:actionerror/></div>
    </s:if>
<h2><fmt:message key="trialStatus.title" /></h2>
    <table class="form">
        
        <tr>
            <td class="label"><s:label for="currentTrialStatus"><fmt:message
                key="trialStatus.current.trial.status" /></s:label></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td class="value"><s:select headerValue="--Select--" onchange="statusChange()"  onfocus="statusChange()"
                name="currentTrialStatus"
                list="#currentTrialStatusValues" /></td>
            <td>
            	<ul class="btnrow">			
					<li style="padding-left:0"><a href="#" class="btn" onclick="openPI('studyOverallStatushistory.action', 'popup')"><span class="btn_img"><span class="history">History</span></span></a></li>
				</ul>
            </td>
        </tr>
        <tr>
            <td class="label"><s:label for="statusDate"><fmt:message
                key="trialStatus.current.trial.status.date" /></s:label></td>
            <td class="value"><s:textfield name="statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
            </td>
        </tr>
        <tr>
            <td class="label"><s:label name="statusReasonLabel" for="statusReason">
                <fmt:message key="trialStatus.current.trial.status.reason"/>
            </s:label></td>
            <td class="value"><s:textarea name="statusReason" rows="3"
                    cssStyle="width:280px;float:left" /></td>
        </tr>
        <tr> 
            <td/>
            <td class="info" colspan="2">Administratively Complete, Withdrawn, and Temporarily Closed statuses only</td>
        </tr>
        <tr><td>&nbsp</td></tr>
        <tr>
            <td class="label"><s:label for="startDate"><fmt:message
                key="trialStatus.trial.start.date" /></s:label></td>
            <td class="value"><s:textfield name="startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="startDateType" list="dateTypeList" /></td>
        </tr>
        <tr>
            <td class="label"><s:label for="completionDate">
            <fmt:message key="trialStatus.primary.completion.date" /></s:label></td>
            <td class="value"><s:textfield name="completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="completionDateType" list="dateTypeList" /></td>
        </tr>
    </table>
<div class="actionsrow">
    <del class="btnwrapper">
        <ul class="btnrow">
            <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>
            <li><a href="regulatoryInfoquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            <li><a href="trialFundingquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
        </ul>   
    </del>
</div>
</s:form>
</div>
</body>
</html>