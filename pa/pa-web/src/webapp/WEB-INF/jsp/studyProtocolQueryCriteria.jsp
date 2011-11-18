<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="studyProtocol.search.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript">
            function handleAction() {
                 document.forms[0].action="studyProtocolquery.action";
                 document.forms[0].submit();
            }
            function generateReport(pid) {
                showPopup('/pa/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId=' + pid, null, 'View Trial Summary Report');
            }
            function resetValues() {
                $("officialTitle").value="";
                $("leadOrganizationId").value="";
                $("identifierType").value="";
                $("identifier").value="";
                $("principalInvestigatorId").value="";
                $("phaseCode").value="";
                $("primaryPurpose").value="";
                $("studyStatusCode").value="";
                $("documentWorkflowStatusCode").value="";
                $("studyMilestone").value="";
                $("searchOnHold").checked=false;
                $("submissionType").value="";
                $("studyLockedBy").checked=false;
                $("trialCategory").value="";
            }

            document.onkeypress = runEnterScript;
            function runEnterScript(e) {
                var KeyID = (window.event) ? event.keyCode : e.keyCode;
                if (KeyID == 13) {
                    handleAction();
                    return false;
                }
            }
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
        <h1><fmt:message key="studyProtocol.search.header"/></h1>
        <c:set var="topic" scope="request" value="searchtrial"/>
        <s:if test="records.size > 0">
            <div class="filter_checkbox"><input type="checkbox" name="checkbox" id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Hide Search Fields</label></div>
        </s:if>
        <div class="box" id="filters">
            <s:form>
                <pa:failureMessage/>
                <table class="form">
                    <s:set name="protocolOrgs" value="@gov.nih.nci.pa.util.PaRegistry@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol('Lead Organization')" />
                    <tr>
                        <td  scope="row" class="label">
                            <label for="officialTitle"> <fmt:message key="studyProtocol.officialTitle"/></label>
                        </td>
                        <td>
                            <s:textfield id="officialTitle" name="criteria.officialTitle" maxlength="200" size="100" cssStyle="width:200px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="leadOrganization"> <fmt:message key="studyProtocol.leadOrganization"/></label>
                        </td>
                        <td>
                            <s:select name="criteria.leadOrganizationIds" id="leadOrganizationId" list="#protocolOrgs" listKey="id"
                                listValue="name" headerKey="" headerValue="All" value="criteria.leadOrganizationIds" />
                        </td>
                    </tr>
                    <s:set name="identifierSearchTypes" value="@gov.nih.nci.pa.enums.IdentifierType@getDisplayNames()" />
                    <tr>
                        <td scope="row" class="label">
                            <label for="identfierType"><fmt:message key="studyProtocol.identifierType"/></label>
                        </td>
                        <td>
                            <s:select id="identifierType" headerKey="" headerValue="--Select--" name="criteria.identifierType"
                                list="#identifierSearchTypes" value="criteria.identifierType"  cssStyle="width:206px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>criteria.identifierType</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                        <td scope="row" class="label">
                            <label for="identifier"><fmt:message key="studyProtocol.identifier"/></label>
                            <br><span class="info">(e.g: NCI-2008-00015; ECOG-1234, etc)</span>
                        </td>
                        <td>
                            <s:textfield id="identifier" name="identifier" maxlength="200" size="100"  cssStyle="width:200px" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>identifier</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <s:set name="principalInvs" value="@gov.nih.nci.pa.util.PaRegistry@getPAPersonService().getAllPrincipalInvestigators()" />
                        <td  scope="row" class="label">
                            <label for="principalInvestigator"> <fmt:message key="studyProtocol.principalInvestigator"/></label>
                        </td>
                        <td align=left>
                            <s:select
                                name="criteria.principalInvestigatorId"
                                id="principalInvestigatorId"
                                list="#principalInvs"
                                listKey="id"
                                listValue="fullName"
                                headerKey=""
                                headerValue="All"
                                value="criteria.principalInvestigatorId" />
                        </td>
                        <td  scope="row" class="label">
                            <label for="trialType"> <fmt:message key="studyProtocol.trialType"/></label>
                        </td>
                        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                        <td>
                            <s:select headerKey="" id="primaryPurpose" headerValue="All" name="criteria.primaryPurposeCode" list="#primaryPurposeCodeValues"
                                      value="criteria.primaryPurposeCode" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="studyPhase"> <fmt:message key="studyProtocol.studyPhase"/></label>
                        </td>
                        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                        <td>
                            <s:select headerKey="" id="phaseCode" headerValue="All" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="studyStatus"> <fmt:message key="studyProtocol.studyStatus"/></label>
                        </td>
                        <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                        <td>
                           <s:select headerKey="" id="studyStatusCode" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  value="criteria.studyStatusCode" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="documentWorkflowStatus"> <fmt:message key="studyProtocol.documentWorkflowStatus"/></label>
                        </td>
                        <s:set name="documentWorkflowStatusCodeValues" value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
                        <td>
                           <s:select headerKey="" headerValue="All" id="documentWorkflowStatusCode" name="criteria.documentWorkflowStatusCode" list="#documentWorkflowStatusCodeValues"  value="criteria.documentWorkflowStatusCode" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="milestoneCode"> <fmt:message key="studyProtocol.milestone"/></label>
                        </td>
                        <s:set name="milestoneCodes" value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
                        <td>
                           <s:select headerKey="" headerValue="All" id="studyMilestone" name="criteria.studyMilestone" list="#milestoneCodes"  value="criteria.studyMilestone" cssStyle="width:206px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="searchOnHold"> <fmt:message key="studyProtocol.searchOnHold"/></label>
                        </td>
                        <td>
                            <s:checkbox id="searchOnHold" name="criteria.searchOnHold" />
                            <input type="hidden" id="searchOnHold" value="false">
                        </td>
                        <td scope="row" class="label">
                            <label for="studyLockedBy"> <fmt:message key="studyProtocol.searchOnCheckout"/></label>
                        </td>
                        <td>
                            <s:checkbox id="studyLockedBy" name="criteria.studyLockedBy" />
                            <input type="hidden" id="studyLockedBy" value="false">
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="submissionType"> <fmt:message key="studyProtocol.submissionTypeSearch"/></label>
                        </td>
                        <s:set name="submissionTypeValues" value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
                        <td>
                           <s:select headerKey="" headerValue="All" id="submissionType" name="criteria.submissionType" list="#submissionTypeValues"  value="criteria.submissionType" cssStyle="width:206px" />
                        </td>
                        <td scope="row" class="label">
                            <label for="trialCategory"> <fmt:message key="studyProtocol.trialCategorySearch"/></label>
                        </td>
                        <td>
                           <s:select headerKey="" headerValue="Both" id="trialCategory" name="criteria.trialCategory" list="#{'p':'Abbreviated','n':'Complete'}"  value="criteria.trialCategory" cssStyle="width:206px" />
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
        <div class="line"></div>
        <h2>Search Results</h2>
        <jsp:include page="/WEB-INF/jsp/studyProtocolQueryResults.jsp"/>
    </body>
</html>
