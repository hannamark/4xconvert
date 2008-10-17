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
function resetValues () {
    document.forms.queryProtocol.queryProtocol_criteria_nciIdentifier.value="";
    document.forms.queryProtocol.queryProtocol_criteria_officialTitle.value="";
    document.forms.queryProtocol.queryProtocol_criteria_leadOrganizationTrialIdentifier.value="";
    document.forms.queryProtocol.queryProtocol_criteria_leadOrganizationId.value="";
    document.forms.queryProtocol.queryProtocol_criteria_studyPhaseCode.value="";
    document.forms.queryProtocol.queryProtocol_criteria_studyStatusCode.value="";
    document.forms.queryProtocol.queryProtocol_criteria_documentWorkflowStatusCode.value="";
}

function handleAction(){
     document.forms[0].action="searchTrialquery.action";
     document.forms[0].submit();     
}
</SCRIPT>
<body>
<!-- main content begins-->
    <h1><fmt:message key="search.trial.page.header"/></h1>
    <div class="filter_checkbox"><input type="checkbox" name="checkbox" checked="true" id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Display Search Fields</label></div>
    <div class="box" id="filters">
    <s:form><s:actionerror/>
        <table class="form">    
            <tr>
                <td scope="row" class="label">
                     <label for="title"> <fmt:message key="search.trial.title"/></label>
                </td>
                <td>
                    <s:textfield name="criteria.officialTitle"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
                <td  scope="row" class="label">
                    <label for="phase"> <fmt:message key="search.trial.phase"/></label> 
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                   <s:select headerKey="" headerValue="All" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" cssStyle="width:150px" />
                </td>
                <td scope="row" class="label">
                    <label for="type"> <fmt:message key="search.trial.type"/></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="criteria.primaryPurposeCode" list="#typeCodeValues"  value="criteria.primaryPurposeCode" cssStyle="width:150px" />
                </td>
             </tr>                                               
             <tr>
                <td scope="row" class="label">
                    <label for="identifierType"> <fmt:message key="search.trial.identifierType"/></label>
                </td>
                <s:set name="identifierTypeValues" value="@gov.nih.nci.pa.enums.IdentifierTypeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.identifierType"  list="#identifierTypeValues" value="criteria.identifierType" cssStyle="width:150px" />                                                                    
                </td>
                <td scope="row" class="label">
                    <label for="identifier"> <fmt:message key="search.trial.identifier"/></label>
                </td>
                <td>
                	<s:textfield name="criteria.leadOrganizationTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:150px" />
                </td>
                <td scope="row" class="label">
                    <label for="organizationType"> <fmt:message key="search.trial.organizationType"/></label>
                </td>
                	<s:set name="organizationTypeValues" value="@gov.nih.nci.pa.enums.OrganizationTypeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.organizationType"  list="#organizationTypeValues" value="criteria.organizationType" cssStyle="width:150px" />
                </td>
                <td  scope="row" class="label">
                <label for="organization"> <fmt:message key="search.trial.organization"/></label> 
                </td>
                <s:set name="protocolOrgs" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol()" />
                <td>
                 	<s:select name="criteria.leadOrganizationleId" list="#protocolOrgs"  listKey="id" listValue="name" headerKey="" headerValue="All" />
                 </td>
            </tr>  
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">         
                <li>           
                <s:a href="#" cssClass="btn" onclick=""><span class="btn_img"><span class="">My Trials</span></span></s:a>       
                <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="Search">Search Trials</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="resetValues ()"><span class="btn_img"><span class="Reset">Reset</span></span></s:a>  
                </li>
               </ul>   
            </del>
        </div>
   </s:form>
 </div>
 <div class="line"></div>
 
 <c:if test="${records != null}">						
		<h2>Search Results</h2>  
   		<jsp:include page="/WEB-INF/jsp/searchTrialResults.jsp">
        	<jsp:param name="listName" value="records" />        
   		</jsp:include>
   </c:if>
</body>
</html>
