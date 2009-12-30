<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=radiationTotalDoseUom" var="totalDoselookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=radiationDurationUom" var="durationlookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=radiationDoseUom" var="doseUomlookupUrl" />
<c:url value="/outcomes/lookUpdoseFrequency.action?type=radiationFrequency" var="doseFrequencylookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">

	function handleSaveAction() {
	if (confirm("Select OK to complete the Save. Once saved can not be Deleted!")) {
	        document.forms[0].action = "addRadiation.action";
	        document.forms[0].submit();
        }
    }

    function handleCancelAction() {
        document.forms[0].action = "executeRadiation.action";
        document.forms[0].submit();
    }
	
	function lookupDoseFrequency(){
        showPopWin('${doseFrequencylookupUrl}', 900, 400, '', 'DoseFrequency');
	}    
    
    function lookupDoseUom(){
        showPopWin('${doseUomlookupUrl}', 900, 400, '', 'Dose UOM');
	}
	
    function lookupTotalDoseUom(){
        showPopWin('${totalDoselookupUrl}', 900, 400, '', 'Total Dose Uom');
	}
	
	function lookupDurationUom(){
        showPopWin('${durationlookupUrl}', 900, 400, '', 'Duration UOM');
	}
	
	function handleEditAction(){
	if (confirm("Select OK to complete the Save. Once saved can not be Deleted!")) {
		    document.forms[0].action="editRadiation.action";
		    document.forms[0].submit();
	    }
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
        <c:set var="topic" scope="request" value="add_radiation"/> 
        <fmt:message key="radiation.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="update_radiation"/> 
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
<s:hidden name = "currentAction"/>
<s:hidden name = "selectedRowIdentifier"/>
<s:hidden name = "radiation.id"/>
<s:hidden name = "radiation.doseFreqId" />
<table class="form"> 
 	<tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.name"/>:<span class="required">*</span></label></td>
        <td>
        	<s:set name="radiationProcedureTypeCodeValues" value="@gov.nih.nci.pa.enums.RadiationProcedureTypeCode@getDisplayNames()" />
            <s:select id ="radiation.type" name="radiation.type" headerKey="" headerValue="--Select--"
                      list="#radiationProcedureTypeCodeValues" value="radiation.type.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.type</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.date"/>:<span class="required">*</span></label></td>
        <td>
        	<s:textfield id="radiationDate" name="radiation.radDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
             <s:fielderror cssClass="formErrorMsg"><s:param>radiation.radDate</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.dose"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="radiation.dose.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.dose.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.doseUOM"/>:<span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.dose.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDoseUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.dose.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.frequency"/>:<span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.doseFreq" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDoseFrequency();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.doseFreq</s:param></s:fielderror>
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
        <td scope="row" class="label"><label><fmt:message key="radiation.totalDoseUOM"/>:<span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.totalDose.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupTotalDoseUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.totalDose.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.duration"/>:</label></td>
        <td class="value">
            <s:textfield name="radiation.duration.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.duration.value</s:param></s:fielderror>
        </td>      
    </tr>
    
     <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.durationUOM"/>:</label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="radiation.duration.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDurationUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.duration.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="radiation.machineType"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:set name="radiationMachineTypeCodeValues" value="@gov.nih.nci.pa.enums.RadiationMachineTypeCode@getDisplayNames()" />
            <s:select id ="radiationCode" name="radiation.machineType" headerKey="" headerValue="--Select--"
                      list="#radiationMachineTypeCodeValues" value="radiation.machineType.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>radiation.machineType</s:param></s:fielderror>
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
         <s:elseif test="%{currentAction == 'update'}">
         	<s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
         </s:elseif>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
