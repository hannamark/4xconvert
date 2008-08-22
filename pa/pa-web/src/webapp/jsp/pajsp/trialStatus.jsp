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
<div id="contentwide">
<h1><fmt:message key="trialStatus.title" /></h1>

<!--Help Content--> <a href="#" class="helpbutton"
    onclick="Help.popHelp('login');">Help</a>
<div id="box"><s:form>
    <s:actionerror />
    <table width="480">
        <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp" />
        <tr>
            <th colspan="2"><fmt:message key="trialStatus.title" /></th>
        </tr>
        <tr>
            <td align="right"><label for="currentTrialStatus"> <fmt:message
                key="trialStatus.current.trial.status" /></label></td>
            <s:set name="currentTrialStatusValues"
                value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
            <td align="left"><s:select headerKey="" headerValue="All"
                name="trialStatusData.currentTrialStatus"
                list="#currentTrialStatusValues"
                value="trialStatusData.currentTrialStatus" /></td>
            <td><s:submit value="Status History"
                action='overallStatusHistory' cssClass="button" /></td>
        </tr>
        <tr>
            <td align="right"><label for="currentTrialStatusDate"> <fmt:message
                key="trialStatus.current.trial.status.date" /></label></td>
            <td align="left"><s:textfield name="currentTrialStatusDate"
                maxlength="10" size="10" value="" /> <a href="javascript:;"
                onclick="cal.select(document.forms[0].currentTrialStatusDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a></td>
        </tr>
        <tr>
            <td align="right"><label for="trialStartDate"> <fmt:message
                key="trialStatus.trial.start.date" /></label></td>
            <td align="left"><s:textfield name="trialStartDate"
                maxlength="10" size="10" value="" /> <a href="javascript:;"
                onclick="cal1.select(document.forms[0].trialStartDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a></td>
            <td nowrap="true"><s:radio name="checkStartDate" list="trialStartDate" /></td>
        </tr>
        <tr>
            <td align="right"><label for="trialStatusCompletionDate">
            <fmt:message key="trialStatus.primary.completion.date" /></label></td>
            <td align="left"><s:textfield name="trialStatusCompletionDate"
                maxlength="10" size="10" value="" /> <a href="javascript:;"
                onclick="cal.select(document.forms[0].trialStatusCompletionDate,'calendarbutton','MM/dd/yyyy'); return false;"
                name="calendarbutton" id="calendarbutton"> <img
                src="images/ico_calendar.gif" alt="select date" class="calendaricon" />
            </a></td>
            <td nowrap="true"><s:radio name="checkPrimaryCompletionDate"
                list="primaryCompletionDate" /></td>
        </tr>
        <td colspan="2">
          <s:submit value="Save"  action='updateOverallStatus' cssClass="button" />
          <s:submit value="Next" action='nciSpecificInformation' cssClass="button" />
        </td>
    </table>
</s:form></div>
</div>
</body>
</html>