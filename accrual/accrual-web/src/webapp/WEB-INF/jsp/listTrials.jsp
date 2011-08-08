<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<h1><fmt:message key="accrual.list.trials.page.header"/></h1>
    <accrual:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <display:table class="data" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.accweb.decorator.SearchTrialResultDecorator"
      sort="list" pagesize="10" id="row" name="displayTagList" requestURI="viewTrials.action" export="false">
       <s:if test="%{#attr.row.industrial.value}">
           <display:column titleKey="accrual.list.trials.protocolNumber" property="assignedIdentifier" sortable="true" href="industrialPatients.action" paramId="studyProtocolId"
             paramProperty="studyProtocolIdentifier.extension" headerClass="sortable" headerScope="col"/>
       </s:if>
       <s:else>
           <display:column titleKey="accrual.list.trials.protocolNumber" property="assignedIdentifier" sortable="true" href="patients.action" paramId="studyProtocolId"
             paramProperty="studyProtocolIdentifier.extension" headerClass="sortable" headerScope="col"/>
       </s:else>
       <display:column escapeXml="true" titleKey="accrual.list.trials.protocolTitle" property="officialTitle" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus" property="studyStatusCode" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table>
