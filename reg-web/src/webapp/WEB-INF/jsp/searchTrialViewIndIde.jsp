<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    				name="${requestScope.studyIndIde}" requestURI="searchTrialview.action" export="false">
	<display:column titleKey="search.trial.view.indldeTypeCode" property="indIde"   sortable="true" headerClass="sortable"/>
	<display:column escapeXml="true" titleKey="search.trial.view.indldeNumber" property="number"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.grantorCode" property="grantor"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.holderTypeCode" property="holderType"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.nciDivProgHolderCode" property="programCode"   sortable="true" headerClass="sortable"/>

	<display:column titleKey="search.trial.view.expandedAccessIndicator" property="expandedAccess"   sortable="true" headerClass="sortable"/>
	<display:column titleKey="search.trial.view.expandedAccessStatusCode" property="expandedAccessType"   sortable="true" headerClass="sortable"/>
</display:table>


