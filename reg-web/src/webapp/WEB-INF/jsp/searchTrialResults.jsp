<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="searchTrialquery.action" export="false">    
	<display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode.code"   sortable="true" headerClass="sortable"/>
	<display:column class="title" titleKey="search.trial.nciIdentifier" property="nciIdentifier" 
    		href="searchTrialview.action"
            paramId="studyProtocolId"  paramProperty="studyProtocolId"
            sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.officialTitle" property="officialTitle"    sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.studyStatusCode" property="studyStatusCode"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="search.trial.piFullName" property="piFullName"    sortable="true" headerClass="sortable"/> 
</display:table>
