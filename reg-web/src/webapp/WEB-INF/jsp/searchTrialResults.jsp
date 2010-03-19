<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:set name="records" value="records" scope="request"/>
<c:choose>
<c:when test="${requestScope.partialSubmission != null}">
<h2 id="search_results">Saved Draft Search Results</h2>
<display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results" 
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
              name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="false">   
    <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row">
            <a href="#" onclick="viewPartialProtocol('${row.studyProtocolId}','${row.userLastCreated}');">${row.studyProtocolId}</a>
      </display:column>
    <display:column titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.trialCategory" property="trialCategory"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.action" >
        <a href="#" onclick="completeDraft('${row.studyProtocolId}','${row.isProprietaryTrial}');">Complete</a>
        </display:column>
    <display:column titleKey="search.trial.action">
        <a href="#" onclick="deletePartialProtocol('${row.studyProtocolId}','${row.userLastCreated}');">Delete</a> 
    </display:column>
</display:table>
</c:when>
<c:otherwise>
<h2 id="search_results">Submitted Clinical Trials Search Results</h2>
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



