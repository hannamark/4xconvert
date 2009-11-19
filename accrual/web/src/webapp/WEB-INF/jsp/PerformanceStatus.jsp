<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleSaveAction() {
        document.forms[0].action = "savePerformanceStatus.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executePerformanceStatus.action";
        document.forms[0].submit();
    }

    function handleNextAction() {
        document.getElementsByName("nextTarget")[0].value = "Treatment";
        document.forms[0].action = "nextPerformanceStatus.action";
        document.forms[0].submit();
    }
</script>
<title>
     Performance Status
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Performance Status
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="nextTarget"/>
<label><fmt:message key="perfStatus.label.onlyOne"/></label>
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="perfStatus.label.ecog"/><span class="required">*</span></label></td>
<td><s:select id="performance.ecogStatus" name="performance.ecogStatus" headerKey="" headerValue="--Select--"
               list="performance.ecogStatuses" listKey="code" listValue="code" value="performance.ecogStatus.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>performance.ecogStatus</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="perfStatus.label.karn"/><span class="required">*</span></label></td>
<td><s:select id="performance.karnofskyStatus" name="performance.karnofskyStatus" headerKey="" headerValue="--Select--"
               list="performance.karnofskyStatuses" listKey="code" listValue="code" value="performance.karnofskyStatus.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>performance.karnofskyStatus</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="perfStatus.label.lans"/><span class="required">*</span></label></td>
<td><s:select id="performance.lanskyStatus" name="performance.lanskyStatus" headerKey="" headerValue="--Select--"
               list="performance.lanskyStatuses" listKey="code" listValue="code" value="performance.lanskyStatus.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>performance.lanskyStatus</s:param></s:fielderror></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleNextAction()"><span class="btn_img"><span class="next">Next</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
