<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="studyProtocolquery.action" export="false">
    <display:column class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
        href="studyProtocolview.action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatus" property="studyStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyStatusDate" property="studyStatusDate" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" sortable="true" headerClass="sortable"/>
    <c:if test="${(sessionScope.role == 'Abstractor')}">
        <display:column class="title" 
            titleKey="studyProtocol.action" 
            href="studyProtocolview.action" property="action"
            paramId="studyProtocolId" paramProperty="studyProtocolId"
            sortable="true" headerClass="sortable"/>
    </c:if>    
    <display:column titleKey="studyProtocol.viewTSR"  
        href="studyProtocolviewTSR.action" property="viewTSR"
        paramId="studyProtocolId" paramProperty="studyProtocolId"/>
</display:table>

