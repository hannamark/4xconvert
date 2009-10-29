<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<h1><fmt:message key="accrual.list.trials.page.header"/></h1>
    <accrual:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <display:table class="data" summary="This table contains your trial search results.
    Please use column headers to sort results"
      sort="list" pagesize="10" uid="row" name="displayTagList" requestURI="viewTrials.action" export="false"> 
   
       <display:column titleKey="accrual.list.trials.protocolNumber" sortable="true" headerClass="sortable"
         headerScope="col">
         <s:a href="#" onclick="window.location='accrualSubmissions.action?studyProtocolId=%{#attr.row.studyProtocolIdentifier.extension}';">
         <s:label value="%{#attr.row.assignedIdentifier}" cssStyle="font-weight:normal; color:blue"/>
         </s:a></display:column>
       <display:column titleKey="accrual.list.trials.protocolTitle" sortable="true" headerClass="sortable"
         headerScope="col"><s:label value="%{#attr.row.officialTitle}"
         cssStyle="font-weight:normal"/></display:column>
       <display:column titleKey="accrual.list.trials.trialStatus" sortable="true" headerClass="sortable"
         headerScope="col"><s:label value="%{#attr.row.studyStatusCode}"
         cssStyle="font-weight:normal"/></display:column>
   </display:table>
