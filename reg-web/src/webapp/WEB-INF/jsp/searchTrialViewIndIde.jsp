<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    				name="${requestScope.studyIndIde}" requestURI="searchTrialview.action" export="false"> 
	<display:column titleKey="search.trial.view.indldeTypeCode" property="indldeTypeCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.indldeNumber" property="indldeNumber.value"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.grantorCode" property="grantorCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.holderTypeCode" property="holderTypeCode.code"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.nciDivProgHolderCode" property="instProgramCode"   sortable="true" headerClass="sortable"/>
	
	<display:column titleKey="search.trial.view.expandedAccessIndicator" property="expandedAccessIndicator"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.expandedAccessStatusCode" property="expandedAccessStatusCode.code"   sortable="true" headerClass="sortable"/>
</display:table>


