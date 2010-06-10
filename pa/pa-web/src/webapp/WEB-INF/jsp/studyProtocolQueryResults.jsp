<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm"><s:actionerror/>
<s:set name="records" value="records" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
    name="records" requestURI="studyProtocolquery.action" export="false">
    <display:column class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
        href="studyProtocolview.action"
        paramId="studyProtocolId" paramProperty="studyProtocolId"
        sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.milestone" property="studyMilsetone.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.milestoneDate" property="studyMilestoneDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.recordVerificationDate" property="recordVerificationDate"  format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyOnholdReasons" property="onHoldReasons"  headerClass="sortable"/>
    <display:column titleKey="studyProtocol.studyOnholdDates" property="offHoldDates" headerClass="sortable"/>
    <display:column titleKey="studyProtocol.submissionType" property="submissionTypeCode"  headerClass="sortable"/>
    <display:column titleKey="studyProtocol.checkOutBy"  sortable="true" headerClass="sortable">
    	<s:if test="%{#attr.row.studyCheckoutByUsername != null}">
    		<c:out value="${row.studyCheckoutByUsername}"/>
    	</s:if>
    </display:column>
    <c:if test="${(sessionScope.role == 'Abstractor') || (sessionScope.role == 'SuAbstractor')}">
        <display:column class="title"
            titleKey="studyProtocol.action"
            href="studyProtocolview.action" property="action"
            paramId="studyProtocolId" paramProperty="studyProtocolId"
            sortable="true" headerClass="sortable"/>
    </c:if>
    <c:if test="${(sessionScope.role == 'SuAbstractor')}">
        <display:column class="title" title="Super User Action" sortable="true" headerClass="sortable">
        	<s:if test="%{#attr.row.studyCheckoutBy != null}">
                <s:url id="url" action="studyProtocolcheckout"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
        		<s:a href="%{url}">Check-In</s:a>
            </s:if>
        </display:column>
    </c:if>
    <display:column titleKey="studyProtocol.viewTSR"
        href="studyProtocolviewTSR.action" property="viewTSR"       />
</display:table>
</s:form>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
function generateTSR(Id) {
    var url = "/pa/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId="+Id;
    document.sForm.target = "TSR";
    document.sForm.action = url;
    document.sForm.submit();

}
</script>