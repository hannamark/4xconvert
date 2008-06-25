<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="its" excludedParams="*" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="/queryProtocol.action" export="false">    
    <display:column titleKey="protocol.nciIdentifier" property="nciIdentifier" sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.officialTitle" property="longTitleText"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.studyPhase" property="studyPhaseCode.name"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.studyStatus" property="studyStatusCode.name"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.studyStatusDate" property="studyStatusDate"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.leadOrganization" property="leadOrganizationName"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="protocol.principalInvestigator" property="principalInvestigatorFullName"    sortable="true" headerClass="sortable"/>       
</display:table>
