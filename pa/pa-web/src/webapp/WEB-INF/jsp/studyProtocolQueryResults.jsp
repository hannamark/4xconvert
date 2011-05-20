<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:form name="sForm">
    <s:actionerror/>
    <s:set name="records" value="records" scope="request"/>
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
        name="records" requestURI="studyProtocolquery.action" export="false">
        <display:column escapeXml="true" class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
            href="studyProtocolview.action" paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.milestone" property="studyMilsetone.code" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="false" titleKey="studyProtocol.milestoneDate" property="studyMilestoneDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="false" titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="false" titleKey="studyProtocol.recordVerificationDate" property="recordVerificationDate"  format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdReasons" property="onHoldReasons"  headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdDates" property="offHoldDates" headerClass="sortable"/>
        <display:column escapeXml="true" titleKey="studyProtocol.submissionType" property="submissionTypeCode"  headerClass="sortable"/>
        <display:column titleKey="studyProtocol.adminCheckOutBy"  sortable="true" headerClass="sortable" >
        	<s:if test="%{#attr.row.studyAdminCheckoutByUsername != null}">
        		<c:out value="${row.studyAdminCheckoutByUsername}"/>
        	</s:if>
        </display:column>
        <display:column titleKey="studyProtocol.scientificCheckOutBy"  sortable="true" headerClass="sortable" >
            <s:if test="%{#attr.row.studyScientificCheckoutByUsername != null}">
                <c:out value="${row.studyScientificCheckoutByUsername}"/>
            </s:if>
        </display:column>
        <c:if test="${sessionScope.isAbstractor || sessionScope.isSuAbstractor}">
            <display:column class="title" titleKey="studyProtocol.action" href="studyProtocolview.action" property="action"
                paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable"/>
        </c:if>
        <c:if test="${sessionScope.isSuAbstractor}">
            <display:column class="title" title="Super User Action" sortable="true" headerClass="sortable">
            	<s:if test="%{#attr.row.studyAdminCheckoutBy != null}">
                    <s:url id="url" action="studyProtocoladminCheckIn"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
            		<s:a href="%{url}">Check-In (Admin)</s:a><br/>
                </s:if>
                <s:if test="%{#attr.row.studyScientificCheckoutBy != null}">
                    <s:url id="url" action="studyProtocolscientificCheckIn"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    <s:a href="%{url}">Check-In (Scientific)</s:a><br/>
                </s:if>
            </display:column>
        </c:if>
        <display:column titleKey="studyProtocol.viewTSR" href="studyProtocolviewTSR.action" property="viewTSR" />
    </display:table>
</s:form>
<script type="text/javascript" language="javascript">
    function generateTSR(id) {
        var form = document.sForm;
        form.target = "TSR";
        form.action = "${pageContext.request.contextPath}/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId=" + id;
        form.submit();
    }
</script>