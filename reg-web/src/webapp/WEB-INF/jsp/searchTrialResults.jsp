<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results" 
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
              name="${param.listName}" requestURI="searchTrialquery.action" export="false">	
	<display:column class="title" title="NCI Trial Identifier" sortable="true" headerScope="col" scope="row">
            <a href="#" onclick="viewProtocol('${row.studyProtocolId}','${row.userLastCreated}');">${row.nciIdentifier}</a>
      </display:column>
	<display:column titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.studyStatusCode" property="studyStatusCode.code"   sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
	<display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.piFullName" property="piFullName"    sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode"   sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="search.trial.action" 
        href="amendTrialview.action" property="action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
       
</display:table>


