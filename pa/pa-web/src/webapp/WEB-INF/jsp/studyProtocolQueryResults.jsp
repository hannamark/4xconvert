<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="records != null">
    <h2>Search Results</h2>
    <s:form name="sForm">
        <s:token/>
        <s:actionerror/>
        <s:set name="records" value="records" scope="request"/>
        <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" id="row"
            name="records" requestURI="studyProtocolquery.action" export="true">                           
            <display:setProperty name="export.xml" value="false"/>
            <display:setProperty name="export.excel.filename" value="resultsSavedDraftSearch.xls"/>
            <display:setProperty name="export.excel.include_header" value="true"/>
            <display:setProperty name="export.csv.filename" value="resultsSavedDraftSearch.csv"/>
            <display:setProperty name="export.csv.include_header" value="true"/>
                
            <display:column escapeXml="true" class="title" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier"
                href="studyProtocolview.action" paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable" media="html"/>
            <display:column class="title" titleKey="studyProtocol.nciIdentifier" sortable="true" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
            </display:column>    
            <display:column escapeXml="true" titleKey="studyProtocol.ctepIdentifier" property="ctepId"
                sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.dcpIdentifier" property="dcpId"
                sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.milestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.studyMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.studyMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.adminMilestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.adminMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.adminMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.scientificMilestone" sortable="true" headerClass="sortable">
                <c:out value="${row.milestones.scientificMilestone.milestone.code}" />
                <fmt:formatDate value="${row.milestones.scientificMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
            </display:column>
            <display:column escapeXml="true" titleKey="studyProtocol.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="false" titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="false" titleKey="studyProtocol.recordVerificationDate" property="recordVerificationDate"  format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdReasons" property="onHoldReasons"  headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.studyOnholdDates" property="offHoldDates" headerClass="sortable"/>
            <display:column escapeXml="true" titleKey="studyProtocol.submissionType" property="submissionTypeCode"  headerClass="sortable"/>
            <display:column titleKey="studyProtocol.adminCheckOutBy"  sortable="true" headerClass="sortable" >
            	<s:if test="%{#attr.row.adminCheckout.checkoutByUsername != null}">
            		<c:out value="${row.adminCheckout.checkoutByUsername}"/>
            	</s:if>
            </display:column>
            <display:column titleKey="studyProtocol.scientificCheckOutBy"  sortable="true" headerClass="sortable" >
                <s:if test="%{#attr.row.scientificCheckout.checkoutByUsername != null}">
                    <c:out value="${row.scientificCheckout.checkoutByUsername}"/>
                </s:if>
            </display:column>
            <c:if test="${sessionScope.isAbstractor || sessionScope.isSuAbstractor}">
                <display:column class="title" titleKey="studyProtocol.action" href="studyProtocolview.action" property="action"
                    paramId="studyProtocolId" paramProperty="studyProtocolId" sortable="true" headerClass="sortable" media="html"/>
            </c:if>
            <c:if test="${sessionScope.isSuAbstractor}">
                <display:column class="title" title="Super User Action" sortable="true" headerClass="sortable" media="html">
                	<s:if test="%{#attr.row.adminCheckout.checkoutBy != null}">
                		<a href="javascript:void(0)" onclick="adminCheckIn('${row.studyProtocolId}')">Check-In (Admin)</a><br/>
                    </s:if>
                    <s:if test="%{#attr.row.scientificCheckout.checkoutBy != null}">
                        <a href="javascript:void(0)" onclick="scientificCheckIn('${row.studyProtocolId}')">Check-In (Scientific)</a><br/>
                    </s:if>
                </display:column>
            </c:if>
            <display:column titleKey="studyProtocol.viewTSR" href="studyProtocolviewTSR.action" property="viewTSR" media="html"/>
        </display:table>
    </s:form>
    <script type="text/javascript">
        function generateTSR(id) {
            var form = document.sForm;
            form.target = "TSR";
            form.action = paApp.contextPath + "/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId=" + id;
            form.submit();
        }
        function adminCheckIn(id) {
            var form = document.sForm;
            form.action = paApp.contextPath + "/protected/studyProtocoladminCheckIn.action?studyProtocolId=" + id;
            form.submit();
        }
        function scientificCheckIn(id) {
            var form = document.sForm;
            form.action = paApp.contextPath + "/protected/studyProtocolscientificCheckIn.action?studyProtocolId=" + id;
            form.submit();
        }
    </script>
</s:if>