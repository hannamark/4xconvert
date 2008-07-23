<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<display:table class="its" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="/queryProtocol.action" export="false">    
    <display:column titleKey="studyProtocol.nciIdentifier" property="nciIdentifier" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.officialTitle" property="officialTitle"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatus" property="studyStatusCode.name"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatusDate" property="studyStatusDate"    sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.name"   sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate"    sortable="true" headerClass="sortable"/>

</display:table>