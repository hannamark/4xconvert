<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.search.title"/></title>   
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
</SCRIPT>
<body>
<!-- main content begins-->

   <div id="contentwide">
    <h1><fmt:message key="studyProtocol.search.header"/></h1>
    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>
 
    <s:form action="studyProtocolQuery"><s:actionerror/>
        <table cellspacing="2" >    
            <tr>
                <td align=right>
                     <label for="nciIdentifier"> <fmt:message key="studyProtocol.nciIdentifier"/></label>
                </td>
                <td align=left>
                    <s:textfield name="criteria.nciIdentifier" size="15" maxlength="20" />
                </td>
                <td align=right>
                    <b><fmt:message key="studyProtocol.officialTitle"/></b> 
                </td>
                <td align=left>                                             
                    <s:textfield name="criteria.officialTitle"   size="50" maxlength="50"  />
                </td>
             </tr>                                               

            <tr>
            <s:set name="protocolOrgs" value="@gov.nih.nci.pa.util.PaRegistry@getPAOrganizationService().getOrganizationsAssociatedWithStudyProtocol()" />
             <tr>
                <td align=right>
                    <b><fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/></b> 
                </td>

                <td align=left>
                    <s:textfield name="criteria.leadOrganizationTrialIdentifier" size="15" maxlength="10" />
                                                                                         
                </td>                    
                <td align=right>
                    <b><fmt:message key="studyProtocol.leadOrganization"/></b> 
                    
                </td>
                <td align=left>
                    <s:select  
                        name="criteria.leadOrganizationId" 
                        list="#protocolOrgs"  
                        listKey="id" listValue="name" headerKey="" headerValue="All" />

                </td>

            </tr>           

            <tr>
            <s:set name="principalInvs" value="@gov.nih.nci.pa.util.PaRegistry@getPAPersonService().getAllPrincipalInvestigators()" />
             <tr>
                <td align=right>
                    <b><fmt:message key="studyProtocol.principalInvestigator"/></b> 
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
                <td align=right>
                    <b><fmt:message key="studyProtocol.trialType"/></b> 
                    
                </td>
                <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td align=left>
                    <s:select headerKey="" headerValue="All" name="criteria.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                    value="criteria.primaryPurposeCode" />
                </td>

            </tr>           

            <tr>
                <td align=right>
                    <b><fmt:message key="studyProtocol.studyPhase"/></b>                         
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td align=left>
                    <s:select headerKey="" headerValue="All" name="criteria.phaseCode" list="#phaseCodeValues"  value="criteria.phaseCode" />
                </td>      
                
                <td align=right>
                    <b><fmt:message key="studyProtocol.studyStatus"/></b>                      
                </td>
               <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
               
                <td align=left>
                   <s:select headerKey="" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  value="criteria.studyStatusCode" />
                </td>                  
            </tr>                                        
            
            <td align=right>
                    <b><fmt:message key="studyProtocol.documentWorkflowStatus"/></b>    
                </td>
                <s:set name="documentWorkflowStatusCodeValues" value="@gov.nih.nci.pa.enums.DocumentWorkflowStatusCode@getDisplayNames()" />
                <td align=left>
                   <s:select headerKey="" headerValue="All" name="criteria.documentWorkflowStatusCode" list="#documentWorkflowStatusCodeValues"  value="criteria.documentWorkflowStatusCode" />
                </td>                  
    
                <td colspan="2">                        
                    <INPUT TYPE="submit" NAME="submit"  value="Search" class="button"/>          
                    <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                </td> 
            </tr>
        </table>
   <c:if test="${records != null}">
   <jsp:include page="/jsp/pajsp/studyProtocolQueryResults.jsp">
        <jsp:param name="listName" value="records" />        
   </jsp:include>
   </c:if>
        
   </s:form>

 </div>
</body>
</html>
