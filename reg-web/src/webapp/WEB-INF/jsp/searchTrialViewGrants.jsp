<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    	name="${requestScope.trialFundingList}" requestURI="searchTrialview.action" export="false">    
	<display:column titleKey="search.trial.view.fundingMechanism" property="fundingMechanismCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.nihInstitutionCode" property="nihInstitutionCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.serialNumber" property="serialNumber.value"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.divProgram" property="nciDivisionProgramCode.code"   sortable="true" headerClass="sortable"/>
</display:table>