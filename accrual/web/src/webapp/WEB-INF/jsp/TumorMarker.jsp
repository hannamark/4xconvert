<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUptumorMarker.action?type=tumorMarker" var="tumorMarkerLookupUrl" />
<c:url value="lookUpunitOfMeasurement.action?type=tumorMarkeruom" var="tmvUomLookupUrl"/>
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleSaveAction() {
        document.forms[0].action = "saveTumorMarker.action";
        document.forms[0].submit();
        
        document.getElementsByName("nextTarget")[0].value = "Staging";
        document.forms[0].action = "nextTumorMarker.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeStaging.action";
        document.forms[0].submit();
    }
    
    function lookup(lookupType){
        if (lookupType == 'tumorMarker') {
            showPopWin('${tumorMarkerLookupUrl}', 900, 400, '', 'Tumor Marker');
        } else if (lookupType == 'tmvUom') {
            showPopWin('${tmvUomLookupUrl}', 900, 400, '', 'Tumor Marker Value UOM');
        }
    }        
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="staging_detail"/> 
     </s:if>
     Add Tumor Marker
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Add Tumor Marker
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="nextTarget"/>
<table class="form">

    <!-- Tumor Marker -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="tumor.marker.label"/><span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="tumorMarker.tumorMarker" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <s:hidden name="tumorMarker.id"/>
            <a href="#" class="btn" onclick="lookup('tumorMarker');"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>tumorMarker.tumorMarker</s:param></s:fielderror>
        </td>
    </tr>

    <!-- Tumor Marker Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="tumor.marker.value.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="tumorMarkerValue" name="tumorMarker.tumorMarkerValue" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>tumorMarker.tumorMarkerValue</s:param></s:fielderror>
        </td>
    </tr>
    
    <!-- Tumor Marker Value UOM -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="tumor.marker.value.uom.label"/><span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="tumorMarker.tmvUom" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookup('tmvUom');"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>tumorMarker.tmvUom</s:param></s:fielderror>
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