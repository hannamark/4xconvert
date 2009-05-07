<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm"><s:actionerror/>    
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="${param.listName}" requestURI="studyProtocolquery.action" export="false">
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
    <c:if test="${(sessionScope.role == 'Abstractor')}">
        <display:column class="title" 
            titleKey="studyProtocol.action" 
            href="studyProtocolview.action" property="action"
            paramId="studyProtocolId" paramProperty="studyProtocolId"
            sortable="true" headerClass="sortable"/>
    </c:if>    
    <display:column titleKey="studyProtocol.viewTSR"  
        href="studyProtocolviewTSR.action" property="viewTSR"       />
</display:table>
</s:form>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function generateTSR(Id) {
     showPopWin('/pa/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId='+Id, 900, 400, '', 'View TSR');
    
}
</script>