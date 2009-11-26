<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUplesionLocationAnatomicSite.action?type=lesionSite" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addLesionAssessment.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeLesionAssessment.action";
        document.forms[0].submit();
    }
    
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Lesion Site');
	}
	
	function handleEditAction(){
	    document.forms[0].action="editLesionAssessment.action";
	    document.forms[0].submit();
	}

</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "clinicalAssessmentDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="lesionAssessment_adding"/> 
        <fmt:message key="lesionAssessment.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="lesionAssessment_update"/> 
        <fmt:message key="lesionAssessment.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
  <s:if test="%{currentAction == 'create'}">
        <fmt:message key="lesionAssessment.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="lesionAssessment.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
	<s:hidden name = "currentAction"/>
	<s:hidden name = "selectedRowIdentifier"/>
    <s:hidden name = "lesionAssessment.Id" />
     <s:hidden name = "lesionAssessment.oldTreatmentPlanId" />
<table class="form">
	<tr>
        <td scope="row" class="label"><label><fmt:message key="outcomes.summary.treatmentPlan"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:select  name="lesionAssessment.treatmentPlanId" headerKey="" headerValue="--Select--"
                      list="treatmentPlans"  listKey="id" listValue="name"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.treatmentPlanId</s:param></s:fielderror>
        </td>      
    </tr>
 
 	<tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.num"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="lesionAssessment.lesionNum" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.lesionNum</s:param></s:fielderror>
        </td>      
    </tr>
    
 	<tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.site"/>:<span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="lesionAssessment.lesionSite" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.lesionSite</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.medt"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:set name="measurableEvalDisTypeCodeValues" value="@gov.nih.nci.pa.enums.MeasurableEvaluableDiseaseTypeCode@getDisplayNames()" />
            <s:select name="lesionAssessment.measurableEvaluableDiseaseType" headerKey="" headerValue="--Select--"
                      list="#measurableEvalDisTypeCodeValues" value="lesionAssessment.measurableEvaluableDiseaseType.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.measurableEvaluableDiseaseType</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.lmmethod"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:set name="lesionMeasMethodCodeValues" value="@gov.nih.nci.pa.enums.LesionMeasurementMethodCode@getDisplayNames()" />
            <s:select name="lesionAssessment.lesionMeasurementMethod" headerKey="" headerValue="--Select--"
                      list="#lesionMeasMethodCodeValues" value="lesionAssessment.lesionMeasurementMethod.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.lesionMeasurementMethod</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
    	<td scope="row" class="label"><label><fmt:message key="lesionAssessment.contrastAgentIndicator"/>:<span class="required">*</span></label></td>
		<td><s:select  name="lesionAssessment.contrastAgentIndicator" headerKey="" headerValue="--Select--"
                      list="lesionAssessment.contrastAgentInds" listKey="code" listValue="code" value="lesionAssessment.contrastAgentIndicator.code"/>
                    <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.contrastAgentIndicator</s:param></s:fielderror>
         </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.imageSeriesIdentifier"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="lesionAssessment.imageSeriesIdentifier" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.imageSeriesIdentifier</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.imageIdentifier"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="lesionAssessment.imageIdentifier" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.imageIdentifier</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.lesionLongestDiameter"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="lesionAssessment.lesionLongestDiameter.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.lesionLongestDiameter.value</s:param></s:fielderror>
        </td>      
    </tr>
                    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="lesionAssessment.clinicalAssessmentDate"/>:<span class="required">*</span></label></td>
        <td>
        	<s:textfield id="clinicalAssessmentDate" name="lesionAssessment.clinicalAssessmentDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
             <s:fielderror cssClass="formErrorMsg"><s:param>lesionAssessment.clinicalAssessmentDate</s:param></s:fielderror>
        </td>
    </tr>

</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>
        <s:if test="%{currentAction == 'create'}">
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
        </s:if>
         <s:if test="%{currentAction == 'update'}">
         	<s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
         </s:if>    
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
