<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="nciSpecificInformation.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
function resetValues () {
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_accrualReportingMethodCode.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_summaryFourFundingCategoryCode.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_organizationName.value="";
    document.forms.updateNCISpecificInformation.updateNCISpecificInformation_nciSpecificInformationWebDTO_monitorCode.value="";
}
</SCRIPT>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/jsp/pajsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form action="updateNCISpecificInformation"><s:actionerror/>
    <h2><fmt:message key="nciSpecificInformation.title" /></h2>
        <table class="form">
                <tr>
                      <td scope="row" class="label">
                           <label for="accrualReportingMethodCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.accrualReportingMethodCode"/></dfn></label>
                      </td>
                      <td class="value">
                            ${nciSpecificInformationWebDTO.accrualReportingMethodCode}                      </td>                         
                 </tr>           
                 <tr>
                      <td scope="row" class="label">
                           <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></dfn></label>
                      </td>
                      
                      <td class="value">
                         ${nciSpecificInformationWebDTO.summaryFourFundingCategoryCode} 
                      </td>                                 
                 </tr>
                 
                <tr>
                      <td scope="row" class="label">
                           <label for="summary4Name"><dfn title="Context sensitive help text or tooltip here."> <fmt:message key="summaryFourFundingSource.organizationName"/></dfn></label>
                      </td>
                      <td class="value">
                          ${nciSpecificInformationWebDTO.organizationName} 
                      </td>          
                 </tr>  
                 
        </table> 
        <!--
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="updateNCISpecificInformation.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                    <li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                    <li><a href="regulatoryInfo.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
                </ul>   
            </del>
        </div>
        -->
                   
    </s:form>
   </div>
<!--  </div> -->
 </body>
 </html>