<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialStatus.title" /></title>
<s:head />
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
        } else {
          document.studyOverallStatus.statusReason.disabled=true;
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
<body onload="setFocusToFirstControl();statusChange();">
<h1><fmt:message key="trialStatus.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<s:form name="studyOverallStatus">
    <s:actionerror />
<h2><fmt:message key="trialStatus.title" /></h2>
    <table class="form">
        
        <tr>
            <td scope="row" class="label"><s:label for="currentTrialStatus"><fmt:message
                key="trialStatus.current.trial.status" /></s:label></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td class="value"><s:select headerKey="" headerValue="" onchange="statusChange()"
                name="currentTrialStatus"
                list="#currentTrialStatusValues" /></td>
            <td>
            	<ul class="btnrow">			
					<li style="padding-left:0"><a href="#" class="btn" onclick="openPI('studyOverallStatushistory.action', 'popup')"><span class="btn_img"><span class="history">History</span></span></a></li>
				</ul>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="statusDate"><fmt:message
                key="trialStatus.current.trial.status.date" /></s:label></td>
            <td class="value"><s:textfield name="statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label name="statusReasonLabel" for="statusReason">
                <fmt:message key="trialStatus.current.trial.status.reason"/>
            </s:label></td>
            <s:if test="(currentTrialStatus=='Administratively Complete')||(currentTrialStatus=='Withdrawn')
                        ||(currentTrialStatus=='Temporarily Closed to Accrual')
                        ||(currentTrialStatus=='Temporarily Closed to Accrual and Intervention')">
                <td scope="row" class="label"><s:textfield name="statusReason" maxlength="200" size="200" 
                    cssStyle="width:280px;float:left" disabled="false"/></td>
            </s:if>
            <s:else>
                <td scope="row" class="label"><s:textfield name="statusReason" maxlength="200" size="200" 
                    cssStyle="width:280px;float:left" disabled="true"/></td>
            </s:else>
        </tr>        
        <tr>
            <td scope="row" class="label"><s:label for="startDate"><fmt:message
                key="trialStatus.trial.start.date" /></s:label></td>
            <td class="value"><s:textfield name="startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="startDateType" list="dateTypeList" /></td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="completionDate">
            <fmt:message key="trialStatus.primary.completion.date" /></s:label></td>
            <td class="value"><s:textfield name="completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="completionDateType" list="dateTypeList" /></td>
        </tr>
        <!--<td colspan="2">
          <s:submit value="Save"  action='studyOverallStatusUpdate' cssClass="button" />
          <s:submit value="Next" action='nciSpecificInformation' cssClass="button" />
        </td> -->
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