 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%> 

<h3 class="heading mt20"><span><fmt:message key="accrual.list.trials.page.header"/></span></h3>
    <accrual:sucessMessage />
    <s:if test="hasActionErrors()"><div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong><s:actionerror />.</div></s:if>   
      <s:set name="selectedPageSize" value="10" scope="request"/>
    <display:table class="table table-striped sortable" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.accweb.decorator.SearchTrialResultDecorator"
      sort="list" pagesize="${selectedPageSize}" id="row" name="displayTagList" requestURI="viewTrials.action" export="false">
       <display:column titleKey="accrual.list.trials.protocolNumber" sortable="true" sortProperty="assignedIdentifier.value" headerClass="sortable" headerScope="col">
           <s:if test="%{#attr.row.industrial.value && #attr.row.trialType.value == 'Interventional'}">
                <s:url id="url" action="industrialPatients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:if>
           <s:elseif test="%{!(#attr.row.industrial.value) && #attr.row.trialType.value == 'Interventional'}">
                <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>
           <s:elseif test="%{#attr.row.trialType.value == 'Non-interventional' && #attr.row.accrualSubmissionLevel.value == 'Subject Level'}">
                <s:url id="url" action="patients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>
           <s:elseif test="%{#attr.row.trialType.value == 'Non-interventional'}">
                <s:url id="url" action="industrialPatients"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolIdentifier.extension}" /></s:url>
           </s:elseif>           
           <s:a href="%{url}"><s:property value="%{#attr.row.assignedIdentifier.value}" /></s:a>
       </display:column>
       <display:column escapeXml="true" titleKey="accrual.list.trials.protocolTitle" property="officialTitle" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialStatus" property="studyStatusCode" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="accrual.list.trials.trialType" property="trialType" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table>
