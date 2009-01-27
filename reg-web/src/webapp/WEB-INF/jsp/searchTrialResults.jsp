<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="searchTrialquery.action" export="false">	
	<display:column class="title" title="NCI Trial Identifier" sortable="true">
            <s:a href="#" onclick="viewProtocol('${row.studyProtocolId}','${row.userLastCreated}');">${row.nciIdentifier}</s:a>
      </display:column>
	<display:column titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.studyStatusCode" property="studyStatusCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.piFullName" property="piFullName"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode.code"   sortable="true" headerClass="sortable"/>   
</display:table>


