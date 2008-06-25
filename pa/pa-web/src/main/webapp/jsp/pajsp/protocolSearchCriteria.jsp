<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="protocol.search.title"/></title>   
    <s:head/>
</head>
<SCRIPT LANGUAGE="JavaScript">
function resetValues () {
    document.forms.queryProtocol.queryProtocol_criteria_nciIdentifier.value="";
    document.forms.queryProtocol.queryProtocol_criteria_longTitleText.value="";
    document.forms.queryProtocol.queryProtocol_criteria_leadOrganizationProtocolId.value="";
    document.forms.queryProtocol.queryProtocol_criteria_leadOrganizationId.value="";
    document.forms.queryProtocol.queryProtocol_criteria_studyPhaseCode.value="";
    document.forms.queryProtocol.queryProtocol_criteria_studyStatusCode.value="";
    document.forms.queryProtocol.queryProtocol_criteria_abstractionStatusCode.value="";


}
</SCRIPT>
<body>
<!-- main content begins-->

   <div id="contentwide">
    <h1><fmt:message key="protocol.search.header"/></h1>
    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>
 
    <s:form action="queryProtocol"><s:actionerror/>
        <table cellspacing="2" >    
            <tr>
                <td align=right>
                     <label for="nciIdentifier"> <fmt:message key="protocol.nciIdentifier"/></label>
                </td>
                <td align=left>
                    <s:textfield name="criteria.nciIdentifier" size="15" maxlength="20" />
                </td>
                <td align=right>
                    <b><fmt:message key="protocol.officialTitle"/></b> 
                </td>
                <td align=left>                                             
                    <s:textfield name="criteria.longTitleText"   size="50" maxlength="50"  />
                </td>
             </tr>                                               
             <tr>
                <td align=right>
                    <b><fmt:message key="studySite.localProtocolIdentifer"/></b> 
                </td>
                <td align=left>
                    <s:textfield name="criteria.leadOrganizationProtocolId" size="15" maxlength="10" />                                                 
                </td>                    
                <td align=right>
                    <b><fmt:message key="protocol.leadOrganization"/></b> 
                </td>
                <td align=left>
                    <s:textfield name="criteria.leadOrganizationId" size="50" maxlength="10" />                                             
                </td>

            </tr>           
            <tr>
                <td align=right>
                    <b><fmt:message key="protocol.studyPhase"/></b>                         
                </td>
                <s:set name="studyPhaseCodeValues" value="@gov.nih.nci.pa.enums.StudyPhaseCode@getCodedNames()" />
                <td align=left>
                    <s:select headerKey="" headerValue="All" name="criteria.studyPhaseCode" list="#studyPhaseCodeValues"  value="criteria.studyPhaseCode" />
                </td>      
                
                <td align=right>
                    <b><fmt:message key="protocol.studyStatus"/></b>                      
                </td>
               <s:set name="studyStatusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getCodedNames()" />
               
                <td align=left>
                   <s:select headerKey="" headerValue="All" name="criteria.studyStatusCode" list="#studyStatusCodeValues"  value="criteria.studyStatusCode" />
                </td>                  
            </tr>                                        
            <s:set name="abstractionStatusCodeValues" value="@gov.nih.nci.pa.enums.AbstractionStatusCode@getCodedNames()" />

            <tr>
            <td align=right>
                    <b><fmt:message key="protocol.abstractionStatus"/></b>    
                </td>
                <td >
                    <s:select headerKey="" headerValue="All" name="criteria.abstractionStatusCode" list="#abstractionStatusCodeValues"  value="criteria.abstractionStatusCodeValues" />
                </td>    
                <td colspan="2">                        
                    <INPUT TYPE="submit" NAME="submit"  value="Search" class="button"/>          
                    <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                </td> 
            </tr>
        </table>
   </s:form>

   <c:if test="${records != null}">
   <jsp:include page="/jsp/pajsp/protocolSearchResults.jsp">
        <jsp:param name="listName" value="records" />        
   </jsp:include>
   </c:if>
 </div>
</body>
</html>
