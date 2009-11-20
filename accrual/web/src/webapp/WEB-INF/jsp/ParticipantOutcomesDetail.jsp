<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpassessmentType.action?type=assessmentType" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleSaveAction() {
        if (confirm("Please confirm all information is correct, select OK to complete the Save, select Cancel to "
                + "correct errors. Once saved the outcome can not be Edited or Deleted!")) {
            document.forms[0].action = "addParticipantOutcomes.action";
            document.forms[0].submit();
        }
    }

    function handleCancelAction() {
        document.forms[0].action = "executeParticipantOutcomes.action";
        document.forms[0].submit();
    }
    
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Assessment Type');
	}
</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "targetOutcome.evaluationDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
        addCalendar("Cal2", "Select Date", "targetOutcome.diseaseStatusDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
        addCalendar("Cal3", "Select Date", "targetOutcome.recurrenceDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
        addCalendar("Cal4", "Select Date", "targetOutcome.progressionDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");        
        addCalendar("Cal5", "Select Date", "targetOutcome.bestResponseDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
	<s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="diseaseEvaluation_adding"/> 
    </s:if>    
     Disease Evaluation
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Add Disease Evaluation
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="targetOutcomeId"/>
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.evalDate"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.evaluationDate" name="targetOutcome.evaluationDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.evaluationDate</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.vitalStatus"/><span class="required">*</span></label></td>
<td><s:select id="targetOutcome.vitalStatus" name="targetOutcome.vitalStatus" headerKey="" headerValue="--Select--"
                    list="targetOutcome.vitalStatuses" listKey="code" listValue="code" value="targetOutcome.vitalStatus.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.vitalStatus</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.respInd"/><span class="required">*</span></label></td>
<td><s:select id="targetOutcome.responseInd" name="targetOutcome.responseInd" headerKey="" headerValue="--Select--"
                    list="targetOutcome.responseInds" listKey="code" listValue="code" value="targetOutcome.responseInd.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.responseInd</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.disStatus"/><span class="required">*</span></label></td>
<td><s:select id="targetOutcome.diseaseStatus" name="targetOutcome.diseaseStatus" headerKey="" headerValue="--Select--"
                    list="targetOutcome.diseaseStatuses" listKey="code" listValue="code" value="targetOutcome.diseaseStatus.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.diseaseStatus</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.disStatusDate"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.diseaseStatusDate" name="targetOutcome.diseaseStatusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.diseaseStatusDate</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.method"/><span class="required">*</span></label></td>
<td><s:textfield readonly="true" size="50" name="targetOutcome.assessmentType" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.assessmentType</s:param></s:fielderror></td></tr>        
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.bestResponse"/><span class="required">*</span></label></td>
<td><s:set name="bestResponseCodeValues" value="@gov.nih.nci.pa.enums.BestResponseCode@getDisplayNames()" />
			<s:select id="targetOutcome.bestResponse" name="targetOutcome.bestResponse" headerKey="" headerValue="--Select--"
                    list="#bestResponseCodeValues" value="targetOutcome.bestResponse.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.bestResponse</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.bestResponseDate"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.bestResponseDate" name="targetOutcome.bestResponseDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal5')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.bestResponseDate</s:param></s:fielderror></td></tr>                              
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.recInd"/><span class="required">*</span></label></td>
<td><s:select id="targetOutcome.recurrenceInd" name="targetOutcome.recurrenceInd" headerKey="" headerValue="--Select--"
                    list="targetOutcome.recurrenceInds" listKey="code" listValue="code" value="targetOutcome.recurrenceInd.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.recurrenceInd</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.recIndDate"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.recurrenceDate" name="targetOutcome.recurrenceDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.recurrenceDate</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.progInd"/><span class="required">*</span></label></td>
<td><s:select id="targetOutcome.progressionInd" name="targetOutcome.progressionInd" headerKey="" headerValue="--Select--"
                    list="targetOutcome.progressionInds" listKey="code" listValue="code" value="targetOutcome.progressionInd.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.progressionInd</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.site"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.progressionSite" name="targetOutcome.progressionSite" maxlength="50" size="50"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.progressionSite</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="partOutEdit.label.progIndDate"/><span class="required">*</span></label></td>
<td><s:textfield id="targetOutcome.progressionDate" name="targetOutcome.progressionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal4')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>targetOutcome.progressionDate</s:param></s:fielderror></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
