<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">
    function handleAddAction() {
        document.getElementsByName("currentAction")[0].value = "create";
        document.forms[0].action = "createParticipantOutcomes.action";
        document.forms[0].submit();
    }

    function handleNextAction() {
        document.getElementsByName("currentAction")[0].value = "next";
        document.getElementsByName("nextTarget")[0].value = "LesionAssessment";
        document.forms[0].action = "nextParticipantOutcomes.action";
        document.forms[0].submit();
    }
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="diseaseEvaluation_detail"/> 
     </s:if>
     Disease Evaluation
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Disease Evaluation
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="currentAction"/>
<s:hidden name="nextTarget"/>
<s:hidden name="targetOutcomeId"/>
<s:set var="displayTagList" value="displayTagList" scope="request"/>
    <display:table class="data" summary="The list of Participant Outcomes.
            Please use column headers to sort results." length="0" pagesize="0"
            sort="list" uid="row" name="displayTagList" requestURI="ParticipantOutcomes.action" export="false">
        <display:column titleKey="partOut.label.evalDate" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.evaluationDate}"/>
        </display:column>
        <display:column titleKey="partOut.label.vitalStatus" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.vitalStatus}"/>
        </display:column>
        <display:column titleKey="partOut.label.respInd" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.responseInd}"/>
        </display:column>
        <display:column titleKey="partOut.label.disStatus" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.diseaseStatus}"/>
        </display:column>
        <display:column titleKey="partOut.label.disStatusDate" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.diseaseStatusDate}"/>
        </display:column>
        <display:column titleKey="partOut.label.method" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.assessmentType}"/>
        </display:column>
        <display:column titleKey="partOutEdit.label.bestResponse" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.bestResponse}"/>
        </display:column>
        <display:column titleKey="partOutEdit.label.bestResponseDate" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.bestResponseDate}"/>
        </display:column>
        <display:column titleKey="partOut.label.recInd" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.recurrenceInd}"/>
        </display:column>
        <display:column titleKey="partOut.label.recIndDate" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.recurrenceDate}"/>
        </display:column>
        <display:column titleKey="partOut.label.progInd" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.progressionInd}"/>
        </display:column>
        <display:column titleKey="partOut.label.progIndDate" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.progressionDate}"/>
        </display:column>
        <display:column titleKey="partOut.label.site" headerClass="left" sortable="true">
            <s:label value="%{#attr.row.progressionSite}"/>
        </display:column>
    </display:table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleAddAction()"><span class="btn_img"><span class="add">Add</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleNextAction()"><span class="btn_img"><span class="next">Next</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
