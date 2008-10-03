<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="trialStatus.title" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/prototype.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/scriptaculous.js"/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript">
            var cal = new CalendarPopup();
            var cal1 = new CalendarPopup();
            var cal2 = new CalendarPopup();
     </script>
     
</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
    input_box=confirm("Click OK to save changes or Cancel to Abort.");
    if (input_box==true){
     document.studyOverallStatus.action="studyOverallStatusupdate.action";
     document.studyOverallStatus.submit();     
    }
}


</SCRIPT>

<body onload="setFocusToFirstControl();">
<h1><fmt:message key="trialStatus.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
<div class="box">
<pa:sucessMessage/>
<s:form name="studyOverallStatus">
    <s:actionerror />
<h2><fmt:message key="trialStatus.title" /></h2>
    <table class="form">
        
        <tr>
            <td scope="row" class="label"><label for="currentTrialStatus"><fmt:message
                key="trialStatus.current.trial.status" /></label></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td class="value"><s:select headerKey="" headerValue=""
                name="currentTrialStatus"
                list="#currentTrialStatusValues" /></td>
            <td>
            	<ul class="btnrow">			
					<li style="padding-left:0"><a href="#" class="btn" onclick="openPI('studyOverallStatushistory.action', 'popup')"><span class="btn_img"><span class="history">History</span></span></a></li>
				</ul>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="statusDate"><fmt:message
                key="trialStatus.current.trial.status.date" /></label></td>
            <td class="value"><s:textfield name="statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal.select(document.forms[0].statusDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a></td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="startDate"><fmt:message
                key="trialStatus.trial.start.date" /></label></td>
            <td class="value"><s:textfield name="startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal1.select(document.forms[0].startDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a><s:radio name="startDateType" list="dateTypeList" /></td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="completionDate">
            <fmt:message key="trialStatus.primary.completion.date" /></label></td>
            <td class="value"><s:textfield name="completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/> <a href="javascript:;"
                onclick="cal.select(document.forms[0].completionDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a><s:radio name="completionDateType" list="dateTypeList" /></td>
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