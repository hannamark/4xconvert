<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:set name="records" value="records" scope="request"/>
<c:choose>
<c:when test="${requestScope.partialSubmission != null}">
<display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results" 
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
              name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="false">   
    <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row">
            <a href="#" onclick="viewPartialProtocol('${row.studyProtocolId}','${row.userLastCreated}');">${row.studyProtocolId}</a>
      </display:column>
    <display:column titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.studyStatusCode" property="studyStatusCode.code"   sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.piFullName" property="piFullName"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.complete" 
        href="submitTrialcompletePartialSubmission.action" property="completePartialSubmission"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/> 
    <display:column titleKey="search.trial.delete" 
        href="submitTrialdeletePartialSubmission.action" property="deletePartialSubmission"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
</display:table>
</c:when>
<c:otherwise>
<display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results" 
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
              name="records" requestURI="searchTrialquery.action" export="false">	
	<display:column class="title" title="NCI Trial Identifier" sortable="true" headerScope="col" scope="row">
            <a href="#" onclick="viewProtocol('${row.studyProtocolId}','${row.userLastCreated}');">${row.nciIdentifier}</a>
      </display:column>
	<display:column titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.studyStatusCode" property="studyStatusCode.code"   sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.piFullName" property="piFullName"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode"   sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.recordVerificationDate" property="recordVerificationDate"  format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.update" 
        href="updateTrialview.action" property="update"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
     <display:column titleKey="search.trial.amend" 
        href="amendTrialview.action" property="amend"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>     
</display:table>
</c:otherwise>
 </c:choose>



