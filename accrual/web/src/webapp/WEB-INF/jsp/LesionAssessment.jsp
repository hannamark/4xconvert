<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">

    function handleAddAction() {
        document.forms[0].action = "createLesionAssessment.action";
        document.forms[0].submit();
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="lesionAssessment_detail"/> 
     </s:if>
     <fmt:message key="lesionAssessment.mainPage"/>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    <fmt:message key="lesionAssessment.mainPage"/>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <accrual:sucessMessage /> 
<s:form name="detailForm">
    <display:table class="data" id="row" name="displayTagList" sort="list" pagesize="10" requestURI="executeLesionAssessment.action">    
            <display:column titleKey="lesionAssessment.num" property="lesionNum.extension" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lesionAssessment.site" property="lesionSite.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lesionAssessment.medt" property="measurableEvaluableDiseaseType.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lesionAssessment.lmmethod" property="lesionMeasurementMethod.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="lesionAssessment.clinicalAssessmentDate" sortable="true" headerClass="sortable">
            	<s:property value="%{#attr.row.clinicalAssessmentDate}"/>
            </display:column>
            <display:column title="Edit" class="action">
    		<s:url id="url" action="updateLesionAssessment"><s:param name="selectedRowIdentifier" value="%{#attr.row.id.extension}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
    	</display:column>
		<display:column title="Delete"	headerClass="centered" class="action">
				<s:url id="url" action="deleteLesionAssessment"><s:param name="selectedRowIdentifier" value="%{#attr.row.id.extension}" /></s:url>
    			<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_delete.gif"	alt="Delete" width="16" height="16" /></s:a>
				</display:column>  
    </display:table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="add">Add</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
