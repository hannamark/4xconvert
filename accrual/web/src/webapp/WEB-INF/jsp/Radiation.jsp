<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">

    function handleAddAction() {
        document.forms[0].action = "createRadiation.action";
        document.forms[0].submit();
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="radiation"/> 
     </s:if>
     <fmt:message key="radiation.mainPage"/>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    <fmt:message key="radiation.mainPage"/>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <accrual:sucessMessage /> 
<s:form name="detailForm">
    <display:table class="data" id="row" name="displayTagList" sort="list" pagesize="10" requestURI="executeRadiation.action">    
            <display:column titleKey="radiation.name" property="type.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="radiation.date" sortable="true" headerClass="sortable">
            	<s:property value="%{#attr.row.radDate}"/>
            </display:column>
            <display:column titleKey="drugBiologic.dose" property="dose.value" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.doseUOM" property="dose.unit" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.frequency" property="doseFreq.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="radiation.machineType" property="machineType.code" sortable="true" headerClass="sortable"/>
            <display:column title="Edit" class="action">
    		<s:url id="url" action="updateRadiation"><s:param name="selectedRowIdentifier" value="%{#attr.row.id.extension}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
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
