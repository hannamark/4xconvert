<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="search.trial.page.title"/></title>
    <s:head/>
</head>
<SCRIPT LANGUAGE="JavaScript">
    window.onload=displayOrg;
    function resetValues(){
        document.searchTrial.reset();
        document.getElementById("officialTitle").value="";
        document.getElementById("phaseCode").value="";
        document.getElementById("typeCodeValues").value="";
        document.getElementById("identifierType").value="";
        document.getElementById("identifier").value="";
        document.getElementById("organizationType").value="";
        document.getElementById("typeCodeValues").value="";
        document.getElementById("organizationId").value="";
        document.getElementById("participatingSiteId").value="";
        document.getElementById("phaseAdditionalQualifierCode").value="";
    }
    function handleAction(){
         var input = "criteria.myTrialsOnly";
         document.forms[0].elements[input].value="false";
         document.forms[0].action="searchTrialquery.action";
         document.forms[0].submit();
    }
    function sendXml(pId){
         document.forms[0].action="searchTrialsendXml.action?studyProtocolId="+pId;
         document.forms[0].submit();
    }

    function handleMyAction(){
        var input = "criteria.myTrialsOnly";
        document.forms[0].elements[input].value="true";
        document.forms[0].action="searchTrialquery.action";
        document.forms[0].submit();
    }
    function getMyPartialTrial() {
        var input = "criteria.myTrialsOnly";
        document.forms[0].elements[input].value="true";
        document.forms[0].action="searchTrialgetMyPartiallySavedTrial.action";
        document.forms[0].submit();
    }
    function viewProtocol(pId) {
        document.forms[0].action="searchTrialview.action?studyProtocolId="+pId;
        document.forms[0].submit();
    }
    function displayOrg(){
        var input="criteria.organizationType";
        var inputElement = document.forms[0].elements[input];
            if (inputElement.options[inputElement.selectedIndex].value == "Participating Site") {
                document.getElementById("Lead").style.display = "none";
                document.getElementById("Site").style.display = "";
                document.getElementById("organizationId").value="";
            } else {
                document.getElementById("Lead").style.display = "";
                document.getElementById("Site").style.display = "none";
                document.getElementById("participatingSiteId").value="";
            }
    }
    function viewPartialProtocol(pId,user) {
        document.forms[0].action="searchTrialpartiallySubmittedView.action?studyProtocolId="+pId;
        document.forms[0].submit();
    }
   function deletePartialProtocol() {
       return confirm("Do you want to delete?");
   }
</SCRIPT>
<body>
<!-- main content begins-->
    <a href="#search_results" id="navskip2">Skip Search Filters and go to Search Results</a>
    <h1><fmt:message key="search.trial.page.header"/></h1>
    <c:set var="topic"  scope="request" value="searchtrials"/>
    <s:if test="records.size > 0">
        <div class="filter_checkbox"><input type="checkbox" name="checkbox"  id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Hide Search Fields</label></div>
    </s:if>
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="searchTrial">
    <s:hidden name="criteria.myTrialsOnly" id="criteria.myTrialsOnly"/>
        <table class="form">
            <tr>
                <td scope="row" class="label">
                <label for="searchTrial_criteria_officialTitle"> <fmt:message key="search.trial.title"/></label>
                </td>
                <td colspan="4">
                <s:textfield id ="officialTitle" name="criteria.officialTitle" maxlength="400" size="300"  cssStyle="width:98%;max-width:680px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_phaseCode"> <fmt:message key="search.trial.phase"/></label>
                </td>
                    <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>
                    <s:select id="phaseCode" headerKey="" headerValue="--Select--" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" cssStyle="width:206px" />
                </td>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_primaryPurposeCode"> <fmt:message key="search.trial.purpose"/></label>
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>
                    <s:select id="typeCodeValues" headerKey="" headerValue="--Select--" name="criteria.primaryPurposeCode" list="#typeCodeValues"  value="criteria.primaryPurposeCode" cssStyle="width:206px" />
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_phaseAdditionalQualifierCode">
                    <fmt:message key="search.trial.phaseAdditionalQualifier"/></label>
                </td>
                  <s:set name="phaseAdditionlQualiefierCodeValues" value="@gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode@getDisplayNames()" />
                <td>
                  <s:select id="phaseAdditionalQualifierCode" headerKey="" headerValue="--Select--" name="criteria.phaseAdditionalQualifierCode" list="#phaseAdditionlQualiefierCodeValues"
                    value="criteria.phaseAdditionalQualifierCode" cssStyle="width:206px" />
                </td>
                <td> </td>
                </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_identifierType"> <fmt:message key="search.trial.identifierType"/></label>
                </td>
                <td>
                    <s:select
                        id = "identifierType"
                        headerKey=""
                        headerValue="--Select--"
                        name="criteria.identifierType"
                        list="#{'NCI':'NCI','NCT':'NCT (Exact Match)','Lead Organization':'Lead Organization','Other Identifier':'Other Identifier'}"
                        value="criteria.identifierType"
                        cssStyle="width:206px"
                        />
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>criteria.identifierType</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_identifier"> <fmt:message key="search.trial.identifier"/></label>
                    <br><span class="info">(Examples: NCI-2008-00015; ECOG-1234)</span>
                </td>
                <td>
                    <s:textfield id="identifier" name="criteria.identifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>criteria.identifier</s:param>
                       </s:fielderror>
                    </span>
                </td>
            </tr>
            <tr>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_organizationType"> <fmt:message key="search.trial.organizationType"/></label>
                </td>
                <td>
                    <s:select id="organizationType" headerKey="" headerValue="--Select--" name="criteria.organizationType"  list="#{'Lead Organization':'Lead Organization','Participating Site':'Participating Site'}" value="criteria.organizationType" cssStyle="width:206px" onchange="displayOrg()"/>
                </td>
                <td scope="row" class="label">
                    <label for="searchTrial_criteria_organizationId"> <fmt:message key="search.trial.organization"/></label>
                </td>
                <td id="Lead">
                    <s:set name="protocolOrgs" value="@gov.nih.nci.pa.util.PaRegistry@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol('Lead Organization')" />
                    <s:select id="organizationId" name="criteria.organizationId" list="#protocolOrgs"  listKey="id" listValue="name" headerKey="" headerValue="--Select--" value="criteria.organizationId" cssStyle="width:206px"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>criteria.organizationId</s:param>
                       </s:fielderror>
                    </span>
                </td>
                <td id="Site" style="display:none">
                    <s:set name="participatingSites" value="@gov.nih.nci.pa.util.PaRegistry@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol('Participating Site')" />
                    <s:select id="participatingSiteId" name="criteria.participatingSiteId" list="#participatingSites"  listKey="id" listValue="name" headerKey="" headerValue="--Select--"  value="criteria.participatingSiteId" cssStyle="width:206px"/>
                    <span class="formErrorMsg">
                        <s:fielderror>
                        <s:param>criteria.organizationId</s:param>
                       </s:fielderror>
                    </span>
                </td>
            </tr>
            <s:set name="principalInvs" value="@gov.nih.nci.pa.util.PaRegistry@getPAPersonService().getAllPrincipalInvestigators()" />
             <tr>
                <td  scope="row" class="label">
                    <label for="principalInvestigator"> <fmt:message key="search.trial.principalInvestigator"/></label>
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
                    <label for="trialCategory"> <fmt:message key="search.trial.trialCategorySearch"/></label>
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
                        <s:a href="#" cssClass="btn" onclick="handleMyAction()" id="searchMyTrialsBtn"><span class="btn_img"><span class="search">Search My Trials</span></span></s:a>
                        <s:a href="#" cssClass="btn" onclick="handleAction()" id="searchAllTrialsBtn"><span class="btn_img"><span class="search">Search All Trials</span></span></s:a>
                        <s:a href="#" cssClass="btn" onclick="resetValues();return false" id="resetSearchBtn"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                        <s:a href="#" cssClass="btn" onclick="getMyPartialTrial()" id="searchSavedDraftsBtn"><span class="btn_img"><span class="search">Search Saved Drafts</span></span></s:a>
                    </li>
                </ul>
            </del>
        </div>
        <p align="center" class="info">
            Search My Trials: Search the trials I have submitted.
            <br>
            Search All Trials: Search all trials I have submitted as well as those registered by others.
            <br>
            Search Saved Drafts: Search my saved drafts.
        </p>
    </s:form>
    </div>
    <div class="line"></div>
    <jsp:include page="/WEB-INF/jsp/searchTrialResults.jsp"/>
    </body>
</html>
