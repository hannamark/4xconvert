<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">
    function handleEditAction() {
        document.forms[0].action = "saveStaging.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "cancelStaging.action";
        document.forms[0].submit();
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="staging_detail"/> 
     </s:if>
     Staging
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Staging
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">

    <!-- Staging Method -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.method.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:select id="stagingMethods" headerValue="--Select--" headerKey="" list="stagingMethods" listKey="id" listValue="name" name="stagingWebDto.method"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.method</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- T Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.t.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="tValue" name="stagingWebDto.tt" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.tt</s:param></s:fielderror></span>
        </td>
    </tr>
    
    <!-- N Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.n.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="nValue" name="stagingWebDto.nn" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.nn</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- M Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.m.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="mValue" name="stagingWebDto.mm" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.mm</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- Stage -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.stage.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="stage" name="stagingWebDto.stage" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.stage</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- Staging System -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.system.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:select id="stagingSystems" headerValue="--Select--" headerKey="" list="stagingSystems" listKey="id" listValue="name" name="stagingWebDto.system"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.system</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- Tumor Marker -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.tumor.marker.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:select id="tumorMarkers" headerValue="--Select--" headerKey="" list="tumorMarkers" listKey="id" listValue="name" name="stagingWebDto.tumorMarker"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.tumorMarker</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- Tumor Marker Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.tumor.marker.value.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="tumorMarkerValue" name="stagingWebDto.tumorMarkerValue" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.tumorMarkerValue</s:param></s:fielderror></span>
        </td>      
    </tr>
    
    <!-- Tumor Marker Value UOM -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.tumor.marker.value.uom.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:select id="tumorMarkerValueUoms" headerValue="--Select--" headerKey="" list="tumorMarkerValueUoms" listKey="id" listValue="name" name="stagingWebDto.tmvUom"/>
            <span class="formErrorMsg"><s:fielderror><s:param>stagingWebDto.tmvUom</s:param></s:fielderror></span>
        </td>      
    </tr>

</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
