<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:set name="records" value="records" scope="request"/>
<s:if test="records != null">
    <s:if test="records.size > 0">
        <c:set var="topic" scope="request" value="searchresults"/>
    </s:if>
    <c:choose>
        <c:when test="${requestScope.partialSubmission != null}">
            <h2 id="search_results">Saved Draft Search Results</h2>
            <display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="true">
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsSavedDraftSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsSavedDraftSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row" media="html">
                    <a href="#" onclick="viewPartialProtocol('${row.studyProtocolId}');"><c:out value="${row.studyProtocolId}"/></a>
                </display:column>
                <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.studyProtocolId}"/>
                 </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.action" media="html">
                    <s:if test="%{#attr.row.proprietaryTrial}">
                        <s:url id="completeUrl" action="submitProprietaryTrialcomplete"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:if>
                    <s:else>
                        <s:url id="completeUrl" action="submitTrialcompletePartialSubmission"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:else>
                    <s:a href="%{completeUrl}">Complete</s:a>
                </display:column>
                <display:column titleKey="search.trial.action" media="html">
                    <s:url id="deleteUrl" action="submitTrialdeletePartialSubmission">
                        <s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" />
                        <s:param name="usercreated" value="%{#attr.row.userLastCreated}" />
                    </s:url>
                    <s:a href="%{deleteUrl}" onclick="return deletePartialProtocol();">Delete</s:a>
                </display:column>
            </display:table>
        </c:when>
        <c:otherwise>
            <h2 id="search_results">Submitted Clinical Trials Search Results</h2>
            <display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialquery.action" export="true">
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsTrialSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsTrialSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="NCI Trial Identifier" sortable="true" headerScope="col" scope="row" media="html">
                    <a href="#" onclick="viewProtocol('${row.studyProtocolId}');"><c:out value="${row.nciIdentifier}"/></a>
                </display:column>
                <display:column class="title" title="NCI Trial Identifier" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength="200" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.studyStatusCode" property="studyStatusCode.code" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.piFullName" property="piFullName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.recordVerificationDate" property="recordVerificationDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.nctNumber" property="nctNumber" sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.submitter" property="lastCreated.userLastDisplayName" sortable="true" headerClass="sortable"/>
                
                <display:column titleKey="search.trial.update" sortable="false" headerClass="sortable" media="html">
                    <s:if test="%{#attr.row.proprietaryTrial}">
                        <s:url id="url" action="updateProprietaryTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
                    </s:if>
                    <s:else>
                        <s:url id="url" action="updateTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
                    </s:else>
                 </display:column>
                 <display:column titleKey="search.trial.amend" href="amendTrialview.action" property="amend" paramId="studyProtocolId" paramProperty="studyProtocolId"
                                 sortable="false" headerClass="sortable" media="html"/>
                 <display:column titleKey="search.trial.statusChange" sortable="false" headerClass="sortable" media="html">
                    <s:if test="%{#attr.row.proprietaryTrial}">
                        <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <a href="#" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');"><s:property value="%{#attr.row.statusChangeLinkText}" /></a>
                    </s:if>
                    <s:else>
                        <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <a href="#" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');"><s:property value="%{#attr.row.statusChangeLinkText}" /></a>
                    </s:else>
                 </display:column>
                 <display:column titleKey="search.trial.addMySite" sortable="false" headerClass="sortable" media="html">
                     <s:if test="%{#attr.row.currentUserCanAddSite}">
                         <a href="javascript:void(0);" onclick="addMySite('${row.studyProtocolId}');" onkeypress="addMySite('${row.studyProtocolId}');">Add</a>
                     </s:if>
                 </display:column>
                 <display:column titleKey="search.trial.updateMySite" sortable="false" headerClass="sortable" media="html">
                     <s:if test="%{#attr.row.currentUserCanEditSite}">
                         <a href="javascript:void(0);" onclick="editMySite('${row.studyProtocolId}');" onkeypress="editMySite('${row.studyProtocolId}');">Update</a>
                     </s:if>
                 </display:column>
                 <display:column class="title" title="Action" sortable="false" headerScope="col" scope="row" media="html">
                     <s:if test="%{#attr.row.showSendXml.booleanValue() == true}">
                         <a href="#" onclick="sendXml('${row.studyProtocolId}');">Send XML/TSR</a>
                     </s:if>
                 </display:column>
            </display:table>
        </c:otherwise>
    </c:choose>
</s:if>