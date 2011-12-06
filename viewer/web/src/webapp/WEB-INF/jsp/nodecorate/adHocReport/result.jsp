<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
 <s:if test="%{resultList != null}">
    <div id="overflow-div">
        <table id="resultTable" width="100%">
            <tr>
                <td colspan="2">
                    <h2><fmt:message key="adHocReport.title"/></h2>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <fmt:message key="report.header.user"/>
                    <viewer:displayUser />
                </td>
            </tr>
            <tr>
                <td><br/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <display:table class="data" sort="list" pagesize="10" id="row" name="sessionScope.displayTagList" 
                                   requestURI="resultsAdHocReport.action" export="true">
                        <display:setProperty name="export.excel" value="true" />
                        <viewer:displayTagProperties/>
                        <display:column escapeXml="false" titleKey="studyProtocol.documentWorkflowStatusDate" property="documentWorkflowStatusDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.nciIdentifier" property="nciIdentifier" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.officialTitle" maxLength= "200" property="officialTitle" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="adHocReport.result.leadOrg" maxLength= "200" property="leadOrganizationName" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.leadOrgID" maxLength= "200" property="leadOrganizationTrialIdentifier" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.principalInvestigator" maxLength= "200" property="piFullName" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.primaryPurpose" maxLength= "200" property="primaryPurpose" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.diseaseCondition" maxLength= "200" property="diseaseNames" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.interventionType" maxLength= "200" property="interventionTypes" sortable="true" headerClass="sortable"/>
                        <display:column escapeXml="true" titleKey="studyProtocol.milestone" sortable="true" headerClass="sortable">
                            <s:if test="%{#attr.row.milestones.adminMilestone.milestone == null && #attr.row.milestones.scientificMilestone.milestone == null}">
                                <c:out value="${row.milestones.studyMilestone.milestone.code}" />
                                <fmt:formatDate value="${row.milestones.studyMilestone.milestoneDate}" pattern="MM/dd/yyyy"/>
                            </s:if>
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
                        <display:column escapeXml="true" titleKey="studyProtocol.submissionType" property="submissionTypeCode.code"  headerClass="sortable"/>  
                        <display:column escapeXml="true" titleKey="studyProtocol.studyPhase" property="phaseCode.code"  headerClass="sortable"/>  
                        <display:column escapeXml="true" titleKey="studyProtocol.summ4FundingSourceTypeCode" property="summ4FundingSrcCategory"  headerClass="sortable"/>  
                        <display:column escapeXml="true" titleKey="studyProtocol.studyStatusResult" property="studyStatusCode.code"  headerClass="sortable"/>  
                        <display:column titleKey="studyProtocol.viewTSR" media="html">
                            <c:if test="${row.viewTSR}">
                                <s:a href='#' onclick='generateTSR(%{#attr.row.studyProtocolId});'> View TSR</s:a>
                            </c:if>
                        </display:column>
                    </display:table>
                </td>
            </tr>
        </table>
    </div>
</s:if>