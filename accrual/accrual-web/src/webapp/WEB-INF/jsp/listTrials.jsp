<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<h1><fmt:message key="accrual.list.trials.page.header"/></h1>
    <accrual:sucessMessage />
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <display:table class="data" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.accweb.decorator.SearchTrialResultDecorator"
      sort="list" pagesize="10" id="row" name="displayTagList" requestURI="viewTrials.action" export="false">
       <display:column titleKey="accrual.list.trials.protocolNumber" sortable="true" headerClass="sortable" headerScope="col">
           <s:if test="%{#attr.row.industrial.value}">
                <s:url id="url" action="industrialPatients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:if>
           <s:else>
                <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:else>
           <s:a href="%{url}"><s:property value="%{#attr.row.assignedIdentifier.value}" /></s:a>
       </display:column>
       <display:column escapeXml="true" titleKey="accrual.list.trials.protocolTitle" property="officialTitle" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus" property="studyStatusCode" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table>
