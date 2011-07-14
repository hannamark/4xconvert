<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_adhoc"/> 
<c:url value="/ctro/popupDis.action" var="lookupUrl" />
<head>
    <title><fmt:message key="adHoc.header" /></title>
    <s:head />
    <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
    <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
    <script type="text/javascript">
        function handleAction(){
            document.forms[0].action="resultsAdHocReport.action";
            document.forms[0].submit();
        }
        
        function resetValues(){
            document.getElementById("officialTitle").value="";
            document.getElementById("leadOrganizationId").value="";
            document.getElementById("identifierType").value="";
            document.getElementById("identifier").value="";
            document.getElementById("principalInvestigatorId").value="";
            document.getElementById("phaseCode").value="";
            document.getElementById("primaryPurpose").value="";
            document.getElementById("studyStatusCode").value="";
            document.getElementById("documentWorkflowStatusCode").value="";
            document.getElementById("studyMilestone").value="";
            document.getElementById("submissionType").value="";
            document.getElementById("trialCategory").value="";
            document.getElementById("criteria_diseaseCondition").value="";
            document.getElementById("diseaseName").value="";
            document.getElementById("summ4Sponsor").value="";
            document.getElementById("interventionType").value="";
            document.getElementById("summ4FundingSourceTypeCode").value="";
        }
        
        function lookup() {
            showPopup('${lookupUrl}', '', 'Disease');
        }
        
        function loadDiv(intid, disName) {
            var url = '/viewer/ctro/ajaxptpDiseasedisplay.action';
            var params = {diseaseName: disName, 'criteria.diseaseConditionId': intid};
            var div = document.getElementById('loadDetails');   
            div.innerHTML = '<div align="left"><img  src="<c:url value="/images/loading.gif"/>"/>&nbsp;Loading...</div>';
            var aj = callAjaxPost(div, url, params);
        }
        
        function generateTSR(Id) {
            var url = "/viewer/ctro/ajax/resultsAdHocReportviewTSR.action?studyProtocolId="+Id;
            document.sForm.target = "TSR";
            document.sForm.action = url;
            document.sForm.submit();
        }
    </script>
</head>
<body>
<!-- main content begins-->

    <h1><fmt:message key="adHoc.header"/></h1>
    <s:form name="sForm">
        <table class="form">
            <s:if test="hasActionErrors()"><tr><td colspan="2"><div class="error_msg"><s:actionerror /></div></td></tr></s:if> 
            
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
                    <s:select name="criteria.leadOrganizationId" id="leadOrganizationId" list="leadOrgList" listKey="id"
                        listValue="name" headerKey="" headerValue="All" value="criteria.leadOrganizationId" />
                </td>
            </tr>
            <tr>    
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="studyProtocol.trialType"/></label>

                </td>
                <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" id="primaryPurpose" headerValue="All" name="criteria.primaryPurposeCode" list="#primaryPurposeCodeValues"
                    value="criteria.primaryPurposeCode" cssStyle="width:206px" />
                </td>

                <td scope="row" class="label">
                    <label for="summ4Sponsor"> <fmt:message key="studyProtocol.summ4Sponsor"/></label>
                </td>
                <td>
                   <s:select name="criteria.summ4FundingSourceId" id="summ4Sponsor" list="summ4FunsingSponsorsList" listKey="id"
                         listValue="name" headerKey="" headerValue="All" value="criteria.summ4FundingSourceId" />
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
                    <label for="diseaseConditionId"> <fmt:message key="studyProtocol.diseaseCondition"/></label>
                </td>
                <td>
                    <s:form name="diseaseForm">
                    <div id="loadDetails">
                        <%@ include file="/WEB-INF/jsp/nodecorate/selectedDiseaseDetails.jsp"%>
                    </div>
                    </s:form>
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
             <tr>
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
                
                <td scope="row" class="label">
                    <label for="submissionType"> <fmt:message key="studyProtocol.submissionTypeSearch"/></label>
                </td>
                <s:set name="submissionTypeValues" value="@gov.nih.nci.pa.enums.SubmissionTypeCode@getDisplayNames()" />
                <td>
                   <s:select headerKey="" headerValue="All" id="submissionType" name="criteria.submissionType" list="#submissionTypeValues"  value="criteria.submissionType" cssStyle="width:206px" />
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
                    <label for="trialCategory"> <fmt:message key="studyProtocol.trialCategorySearch"/></label>
                </td>
                <td>
                   <s:select headerKey="" headerValue="Both" id="trialCategory" name="criteria.trialCategory" list="#{'p':'Abbreviated','n':'Complete'}"  value="criteria.trialCategory" cssStyle="width:206px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="interventionType"> <fmt:message key="studyProtocol.interventionType"/></label>
                </td>
                <s:set name="interventionTypeValues" value="@gov.nih.nci.pa.enums.ActivitySubcategoryCode@getDisplayNames()" />
                <td>
                   <s:select headerKey="" headerValue="All" id="interventionType" name="criteria.interventionType" list="#interventionTypeValues"  value="criteria.interventionType" cssStyle="width:206px" />
                </td>
                <s:set name="summ4FundingSourceTypeCodes" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <td scope="row" class="label">
                    <label for="summ4FundingSourceTypeCode"> <fmt:message key="studyProtocol.summ4FundingSourceTypeCode"/></label>
                </td>
                <td>
                   <s:select headerKey="" headerValue="All" id="summ4FundingSourceTypeCode" name="criteria.summ4FundingSourceTypeCode" list="#summ4FundingSourceTypeCodes"  value="criteria.summ4FundingSourceTypeCode" cssStyle="width:206px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="studyStatus"> <fmt:message key="studyProtocol.studyStatus"/></label>
                </td>
               <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />

                <td>
                   <s:select headerKey="" id="studyStatusCode" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  value="criteria.studyStatusCode" cssStyle="width:206px" />
                </td>
                <td scope="row" class="label">
                    <label for="milestoneCode"> <fmt:message key="studyProtocol.milestone"/></label>
                </td>
                <s:set name="milestoneCodes" value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
                <td>
                   <s:select headerKey="" headerValue="All" id="studyMilestone" name="criteria.studyMilestone" list="#milestoneCodes"  value="criteria.studyMilestone" cssStyle="width:206px" />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Run Report</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                        </li>
                </ul>
            </del>

        </div>
        <s:if test="%{resultList != null}">
            <table width="100%">
                <tr>
                    <td colspan="2">
                        <h2><fmt:message key="adHoc.header"/></h2>
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
                            <display:column escapeXml="true" titleKey="studyProtocol.leadOrganization" maxLength= "200" property="leadOrganizationName" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.leadOrgID" maxLength= "200" property="leadOrganizationId" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.principalInvestigator" maxLength= "200" property="piFullName" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.trialType" maxLength= "200" property="primaryPurpose" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.diseaseCondition" maxLength= "200" property="diseaseNames" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.interventionType" maxLength= "200" property="interventionTypes" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="true" titleKey="studyProtocol.milestone" property="studyMilsetone.code" sortable="true" headerClass="sortable"/>
                            <display:column escapeXml="false" titleKey="studyProtocol.milestoneDate" property="studyMilestoneDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>   
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
        </s:if>
    </s:form>
</body>
