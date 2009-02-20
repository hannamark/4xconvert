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

</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
     document.forms[0].action="studyProtocolquery.action";
     document.forms[0].submit();     
}
function generateReport(pid) {
    showPopWin('/pa/protected/ajaxStudyProtocolviewTSR.action?studyProtocolId='+pid, 1050, 400, '', 'View Trial Summary Report');
}

</SCRIPT>
<body>
<!-- main content begins-->
    <h1><fmt:message key="studyProtocol.search.header"/></h1>
    <div class="filter_checkbox"><input type="checkbox" name="checkbox" checked="true" id="filtercheckbox" onclick="toggledisplay('filters', this)" /><label for="filtercheckbox">Display Search Fields</label></div>
    <div class="box" id="filters">
    <s:form>
    <pa:failureMessage/>
        <table class="form">    
            <tr>
                <td scope="row" class="label">
                     <label for="nciIdentifier"> <fmt:message key="studyProtocol.nciIdentifier"/></label>
                </td>
                <td>
                    <s:textfield name="criteria.nciIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
                <td  scope="row" class="label">
                    <label for="officialTitle"> <fmt:message key="studyProtocol.officialTitle"/></label> 
                </td>
                <td>                                             
                    <s:textfield name="criteria.officialTitle" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
             </tr>                                               
            <s:set name="protocolOrgs" value="@gov.nih.nci.pa.util.PaRegistry@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol()" />

            <tr>
                <td scope="row" class="label">
                    <label for="localProtocolIdentifer"> <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/></label>
                </td>

                <td>
                    <s:textfield name="criteria.leadOrganizationTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                                                                                         
                </td>                    
                <td  scope="row" class="label">
                    <label for="leadOrganization"> <fmt:message key="studyProtocol.leadOrganization"/></label> 
                    
                </td>
                <td>
                     <s:select  
                        name="criteria.leadOrganizationId" 
                        list="#protocolOrgs"  
                        listKey="id" listValue="name" headerKey="" headerValue="All" />
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
                        list="#principalInvs"  
                        listKey="id" 
                        listValue="fullName" 
                        headerKey="" 
                        headerValue="All"
                        />

                </td>                    
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="studyProtocol.trialType"/></label>
                    
                </td>
                <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                    value="criteria.primaryPurposeCode" cssStyle="width:206px" />
                </td>

            </tr>           

            <tr>
                <td scope="row" class="label">
                    <label for="studyPhase"> <fmt:message key="studyProtocol.studyPhase"/></label>                        
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>
                    <s:select headerKey="" headerValue="All" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" cssStyle="width:206px" />
                </td>      
                
                <td scope="row" class="label">
                    <label for="studyStatus"> <fmt:message key="studyProtocol.studyStatus"/></label>                   
                </td>
               <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
               
                <td>
                   <s:select headerKey="" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  value="criteria.studyStatusCode" cssStyle="width:206px" />
                </td>                  
            </tr>                                        
            
            <tr>
            	<td scope="row" class="label">
                    <label for="documentWorkflowStatus"> <fmt:message key="studyProtocol.documentWorkflowStatus"/></label>    
                </td>
                <s:set name="documentWorkflowStatusCodeValues" value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
                <td>
                   <s:select headerKey="" headerValue="All" name="criteria.documentWorkflowStatusCode" list="#documentWorkflowStatusCodeValues"  value="criteria.documentWorkflowStatusCode" cssStyle="width:206px" />
                </td>                  
<!--    
                 <td colspan="2">                        
                    <INPUT TYPE="submit" NAME="submit"  value="Search" class="button"/>          
                    <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                </td>  
-->                
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
   		<jsp:include page="/WEB-INF/jsp/studyProtocolQueryResults.jsp">
        	<jsp:param name="listName" value="records" />        
   		</jsp:include>
   </c:if>
   
</body>
</html>
