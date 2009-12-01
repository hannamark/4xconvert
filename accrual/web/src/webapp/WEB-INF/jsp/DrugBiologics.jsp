<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">

    function handleAddAction() {
        document.forms[0].action = "createDrugBiologics.action";
        document.forms[0].submit();
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="drugBiologics_detail"/> 
     </s:if>
     Drug/Biologics
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
   Drug/Biologics
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
  <accrual:sucessMessage /> 
<s:form name="detailForm">
    <display:table class="data" name="displayTagList" sort="list" pagesize="10">    
            <display:column titleKey="drugBiologic.name" property="drugName.value" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.dose" property="dose.value" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.doseUOM" property="dose.unit" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.roa" property="doseRoute.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.frequency" property="doseFreq.code" sortable="true" headerClass="sortable"/>
            <display:column titleKey="drugBiologic.doseMT" property="doseModType.code" sortable="true" headerClass="sortable"/>
            <display:column title="Edit" class="action">
    		<s:url id="url" action="updateDrugBiologics"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
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
