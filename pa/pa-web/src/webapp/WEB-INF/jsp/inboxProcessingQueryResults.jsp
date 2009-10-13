<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm"><s:actionerror/>    
<c:set var="topic" scope="request" value="inbox_process"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="inboxProcessingquery.action" export="false">
    <display:column class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
        href="studyProtocolview.action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.milestone" property="studyMilsetone.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.milestoneDate" property="studyMilestoneDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyOnholdReasons" property="onHoldReasons"  headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyOnholdDates" property="offHoldDates" headerClass="sortable"/>
    <display:column titleKey="inboxTrial.updatedDate" property="updatedDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="inboxTrial.updateReason" property="updatedComments"  headerClass="sortable"/>
    <c:if test="${(sessionScope.role == 'Abstractor') || (sessionScope.role == 'SuAbstractor')}">
        <display:column class="title" 
            titleKey="studyProtocol.action" 
            href="studyProtocolview.action" property="action"
            paramId="studyProtocolId" paramProperty="studyProtocolId"
            sortable="true" headerClass="sortable"/>
    </c:if>    
     <display:column class="title" 
            titleKey="inboxTrial.remove" 
            href="inboxProcessingremove.action" property="remove"
            paramId="studyInboxId" paramProperty="studyInboxId"
            sortable="true" headerClass="sortable"/>
</display:table>
</s:form>
