 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%> 

<h3 class="heading mt20"><span><fmt:message key="accrual.list.trials.page.header"/></span></h3>
    <accrual:sucessMessage />
    <s:if test="hasActionErrors()"><div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong><s:actionerror />.</div></s:if>   
    <s:set name="selectedPageSize" value="10" scope="request"/>
    <s:set name="accrualDiseaseValues" value="@gov.nih.nci.pa.util.PaRegistry@getAccrualDiseaseTerminologyService().getValidCodeSystems()" />
    <display:table class="table table-striped sortable" summary="This table contains your trial search results.
    Please use column headers to sort results" decorator="gov.nih.nci.accrual.accweb.decorator.SearchTrialResultDecorator"
      defaultsort="1" defaultorder="descending" 
      sort="list" pagesize="${selectedPageSize}" id="row" name="displayTagList" requestURI="viewTrials.action" export="false">
       <display:column titleKey="accrual.list.trials.protocolNumber" sortable="true" sortProperty="assignedIdentifier.value" headerClass="sortable" headerScope="col">
           <!-- <s:property value="%{#attr.row.assignedIdentifier.value}" /> -->
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
       <display:column titleKey="accrual.list.trials.diseaseCodeSystem" sortable="true" headerClass="sortable" headerScope="col">
           <s:if test="%{#attr.row.canChangeDiseaseCodeSystem.value}">
                <s:select id="disCodeSystem_%{#attr.row.studyProtocolIdentifier.extension}" 
                          name="disCodeSystem_%{#attr.row.studyProtocolIdentifier.extension}"
                          headerKey="" headerValue="--Select--" 
                          list="#accrualDiseaseValues" value="%{#attr.row.diseaseCodeSystem.value}"
                          onchange="saveCodeSystem('%{#attr.row.studyProtocolIdentifier.extension}',this.value);"/>
                <s:div id="termupdlbl_%{#attr.row.studyProtocolIdentifier.extension}" cssClass="text-muted"/>
           </s:if>
           <s:else>
               <s:property value="%{#attr.row.diseaseCodeSystem.value}"/>
           </s:else>
       </display:column>
   </display:table>
