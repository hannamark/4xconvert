<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">
    function handleSaveAction() {
        document.forms[0].action = "saveStaging.action";
        document.forms[0].submit();
    }

    function handleAddAction() {
        document.forms[0].action = "executeTumorMarker.action";
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
            <s:select id="stagingMethods" name="staging.method" headerKey="" headerValue="--Select--" 
                      list="staging.methods" listKey="code" listValue="code" value="staging.method.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.method</s:param></s:fielderror>
        </td>      
    </tr>
    
    <!-- T Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.t.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="tValue" name="staging.tt" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.tt</s:param></s:fielderror>
        </td>
    </tr>
    
    <!-- N Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.n.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="nValue" name="staging.nn" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.nn</s:param></s:fielderror>
        </td>      
    </tr>
    
    <!-- M Value -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.m.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="mValue" name="staging.mm" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.mm</s:param></s:fielderror>
        </td>      
    </tr>
    
    <!-- Stage -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.stage.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="stage" name="staging.stage" maxlength="400" size="50" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.stage</s:param></s:fielderror>
        </td>      
    </tr>
    
    <!-- Staging System -->
    <tr>
        <td scope="row" class="label"><label><fmt:message key="staging.system.label"/><span class="required">*</span></label></td>
        <td class="value">
            <s:select id="stagingSystems" name="staging.system" headerKey="" headerValue="--Select--"
                      list="staging.systems" listKey="code" listValue="code" value="staging.system.code"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>staging.system</s:param></s:fielderror>
        </td>      
    </tr>

</table>
</s:form>

<s:if test="displayTagList != null">
    <div class="box">
        <display:table class="data" name="displayTagList" sort="list" pagesize="10">    
            <display:column titleKey="tumor.marker.title" property="tumorMarker.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="tumor.marker.value.title" property="tumorMarkerValue.value" sortable="true" headerClass="sortable"/>
            <display:column titleKey="tumor.marker.value.uom.title" property="tmvUom.unit" sortable="true" headerClass="sortable"/>
        </display:table>
    </div>
</s:if>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="add">Add Tumor Marker</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>