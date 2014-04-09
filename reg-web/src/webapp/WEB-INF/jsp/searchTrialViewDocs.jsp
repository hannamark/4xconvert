<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="data table table-striped table-bordered sortable" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
    name="${requestScope.protocolDocument}" requestURI="searchTrialviewDoc.action" export="false">
	<display:column titleKey="search.trial.view.documentTypeCode" property="typeCode.code"   sortable="true" headerClass="sortable"/>
	<display:column escapeXml="true" titleKey="search.trial.view.documentFileName" property="fileName.value"  href="searchTrialviewDoc.action"
            paramId="identifier"  paramProperty="identifier.extension"
            sortable="true" headerClass="sortable"/>"
</display:table>
