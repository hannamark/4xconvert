<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpprocedureName.action?type=radiationType" var="lookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=radiationTotalDoseUom" var="totalDoselookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=radiationDurationUom" var="durationlookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addRadiation.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeRadiation.action";
        document.forms[0].submit();
    }
    
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Radiation Type');
	}
    
    function lookupTotalDoseUom(){
        showPopWin('${totalDoselookupUrl}', 900, 400, '', 'Total Dose Uom');
	}
	
	function lookupDurationUom(){
        showPopWin('${durationlookupUrl}', 900, 400, '', 'Duration UOM');
	}

</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "radiationDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="radiation_adding"/> 
        <fmt:message key="radiation.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="radiation_update"/> 
        <fmt:message key="radiation.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
  <s:if test="%{currentAction == 'create'}">
        <fmt:message key="radiation.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="radiation.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">
 
 	<tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.name"/><span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="radiation.type" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.type</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.date"/><span class="required">*</span></label></td>
        <td>
        	<s:textfield id="radiationDate" name="radiation.radDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
             <s:fielderror cssClass="formErrorMsg"><s:param>radiation.radDate</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.totalDose"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="radiation.totalDose.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.totalDose.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.totalDoseUOM"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.totalDose.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupTotalDoseUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.totalDose.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.duration"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="radiation.duration.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.duration.value</s:param></s:fielderror>
        </td>      
    </tr>
    
     <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.durationUOM"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.duration.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDurationUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.duration.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.machineType"/><span class="required">*</span></label></td>
        <td class="value">
            <s:set name="radiationProcedureTypeCodeValues" value="@gov.nih.nci.pa.enums.RadiationProcedureTypeCode@getDisplayNames()" />
            <s:select id ="radiationCode" name="radiation.machineType" headerKey="" headerValue="--Select--"
                      list="#radiationProcedureTypeCodeValues" value="radiation.machineType.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.machineType</s:param></s:fielderror>
        </td>      
    </tr>

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
