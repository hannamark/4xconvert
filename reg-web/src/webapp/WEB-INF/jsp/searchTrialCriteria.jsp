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
                    <s:textfield name="criteria.title"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
                <td  scope="row" class="label">
                    <label for="phase"> <fmt:message key="search.trial.phase"/></label> 
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="criteria.phase" list="#phaseCodeValues"  value="criteria.phase" cssStyle="width:206px" />
                </td>
                <td  scope="row" class="label">
                    <label for="phase"> <fmt:message key="search.trial.type"/></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="criteria.type" list="#typeCodeValues"  value="criteria.type" cssStyle="width:206px" />
                </td>
             </tr>                                               


            <tr>
                <td scope="row" class="label">
                    <label for="identifierType"> <fmt:message key="search.trial.identifierType"/></label>
                </td>
                <s:set name="identifierTypeValues" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.identifierType"  list="#identifierTypeValues" value="criteria.identifierType" cssStyle="width:206px" />
                                                                                         
                </td>
                <td scope="row" class="label">
                    <label for="identifier"> <fmt:message key="search.trial.identifier"/></label>
                </td>

                <td>
                    <s:textfield name="criteria.identifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
                <td scope="row" class="label">
                    <label for="organizationType"> <fmt:message key="search.trial.organizationType"/></label>
                </td>
                <s:set name="organizationTypeValues" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />

                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.organizationType"  list="#organizationTypeValues" value="criteria.organizationType" cssStyle="width:206px" />
                                                                                         
                </td>

            </tr>  
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
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
