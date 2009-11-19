<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=drugName" var="drugNamelookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=doseUom" var="doseUomlookupUrl" />
<c:url value="/outcomes/lookUprouteOfAdministration.action?type=doseRoa" var="doseRoalookupUrl" />
<c:url value="/outcomes/lookUpdoseFrequency.action?type=doseFrequency" var="doseFrequencylookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=doseDurationUom" var="durationlookupUrl" />
<c:url value="/outcomes/lookUpunitOfMeasurement.action?type=totalDoseUom" var="totalDoselookupUrl" />

<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addDrugBiologics.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeDrugBiologics.action";
        document.forms[0].submit();
    }
    
    function lookupDrugName() {
        showPopWin('${drugNamelookupUrl}', 900, 400, '', 'Drug Name');
	}
    
    function lookupDoseUom(){
        showPopWin('${doseUomlookupUrl}', 900, 400, '', 'Dose UOM');
	}
    
    function lookupDoseRoa(){
        showPopWin('${doseRoalookupUrl}', 900, 400, '', 'Route Of Administration');
	}
	
	function lookupDoseFrequency(){
        showPopWin('${doseFrequencylookupUrl}', 900, 400, '', 'DoseFrequency');
	}
    
    function lookupDurationUom(){
        showPopWin('${durationlookupUrl}', 900, 400, '', 'Duration UOM');
	}
	
	function lookupTotalDoseUom(){
        showPopWin('${totalDoselookupUrl}', 900, 400, '', 'Total Dose UOM');
	}

</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="drugBiologics_adding"/> 
        <fmt:message key="drugBiologic.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="drugBiologics_update"/> 
        <fmt:message key="drugBiologic.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
   <s:if test="%{currentAction == 'create'}">
        <fmt:message key="drugBiologic.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="drugBiologic.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">
 	<tr>
		        <th colspan="3"><fmt:message key="drugBiologic.subHeading1"/></th>
		    </tr>
     <tr><td></td></tr>
 
 	<tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.name"/><span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="drugBiologic.drugName" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDrugName();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.drugName</s:param></s:fielderror>
        </td>
    </tr>
    
     <tr>
		        <th colspan="3"><fmt:message key="drugBiologic.subHeading2"/></th>
	</tr>
	<tr><td></td></tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.dose"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.dose.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.dose.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.doseUOM"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="drugBiologic.dose.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDoseUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.dose.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.roa"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="drugBiologic.doseRoute" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDoseRoa();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseRoute</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.frequency"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="drugBiologic.doseFreq" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDoseFrequency();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseFreq</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.duration"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.doseDur.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseDur.value</s:param></s:fielderror>
        </td>      
    </tr>
    
     <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.durationUOM"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="drugBiologic.doseDur.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupDurationUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseDur.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.height"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.height.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.height.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.heightUOM"/><span class="required">*</span></label></td>
        <td class="value">
            <s:set name="heightValues" value="@gov.nih.nci.pa.enums.PatientHeightUOM@getDisplayNames()" />
            <s:select id ="heightUom" name="drugBiologic.height.unit" headerKey="" headerValue="--Select--"
                      list="#heightValues"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.height.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.weight"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.weight.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.weight.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.weightUOM"/><span class="required">*</span></label></td>
        <td class="value">
            <s:set name="weightValues" value="@gov.nih.nci.pa.enums.PatientWeightUOM@getDisplayNames()" />
            <s:select id ="weightUom" name="drugBiologic.weight.unit" headerKey="" headerValue="--Select--"
                      list="#weightValues"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.weight.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
     <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.bsa"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.bsa.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.bsa.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.tda"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield name="drugBiologic.doseTotal.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseTotal.value</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.tdUOM"/><span class="required">*</span></label></td>
        <td class="value">
        <s:textfield readonly="true" size="50" name="drugBiologic.doseTotal.unit" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookupTotalDoseUom();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseTotal.unit</s:param></s:fielderror>
        </td>      
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="drugBiologic.doseMT"/><span class="required">*</span></label></td>
        <td class="value">
            <s:set name="doseModificationTypeValues" value="@gov.nih.nci.pa.enums.DoseModificationType@getDisplayNames()" />
            <s:select id ="doseModificationType" name="drugBiologic.doseModType" headerKey="" headerValue="--Select--"
                      list="#doseModificationTypeValues" value="drugBiologic.doseModType.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>drugBiologic.doseModType</s:param></s:fielderror>
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
