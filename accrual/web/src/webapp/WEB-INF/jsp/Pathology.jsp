<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">
	function handleEditAction() {
		document.forms[0].action = "savePathology.action";
		document.forms[0].submit();
	}

	function handleCancelAction() {
        document.forms[0].action = "cancelPathology.action";
        document.forms[0].submit();
	}
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="pathology_detail"/> 
     </s:if>
     Pathology
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Pathology
</h1>
<div class="box">
    <s:if test="hasActionErrors() && !hasFieldErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form"> 
    <tr><td scope="row" class="label"><label><fmt:message key="pathology.label.grades"/><span class="required">*</span></label></td>
    <td class="value"><s:select required="true" id ="pathologyGrades" name="pathology.grade"
                      headerKey="" headerValue="--Select--"
                      list="pathology.grades" listKey="code" listValue="code" value="pathology.grade.code"/>
                       <s:fielderror cssStyle="color:red"><s:param>pathology.grade</s:param></s:fielderror></td></tr>
    <tr><td scope="row" class="label"><label><fmt:message key="pathology.label.gradeSystems"/><span class="required">*</span></label></td>
    <td class="value"><s:select required="true" id ="pathologyGradeSystems" name="pathology.gradeSystem"
                      headerKey="" headerValue="--Select--"
                      list="pathology.gradeSystems" listKey="code" listValue="code" value="pathology.gradeSystem.code"/>
                       <s:fielderror cssStyle="color:red"><s:param>pathology.gradeSystem</s:param></s:fielderror></td></tr>
    <tr><td scope="row" class="label"><label><fmt:message key="pathology.label.description"/><span class="required">*</span></label></td>
    <td class="value"><s:textarea cols="60" rows="4" name="pathology.description"/>
                       <s:fielderror cssStyle="color:red"><s:param>pathology.description</s:param></s:fielderror></td></tr>
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
