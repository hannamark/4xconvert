<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="studyProtocolquery.action" export="false">
    <display:column class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
        href="studyProtocolview.action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.officialTitle" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatus" property="studyStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatusDate" property="studyStatusDate" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" sortable="true" headerClass="sortable"/>
    <display:column class="title" 
        titleKey="studyProtocol.action" 
        href="generalTrialDesignquery.action" property="action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
    
    <display:column titleKey="studyProtocol.generateXML"  >
        <s:a href="#" onclick="generateReport('${row.studyProtocolId}');">Generate XML</s:a>
    </display:column>

</display:table>
