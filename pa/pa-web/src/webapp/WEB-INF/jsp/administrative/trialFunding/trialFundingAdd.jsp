<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialFunding.addedittitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    setFocusToFirstControl();        
}
function handleAction(){
 var page;
page=document.forms[0].page.value;
if (page == "Edit"){
 document.forms[0].action="trialFundingupdate.action";
 document.forms[0].submit(); 
} else {
 document.forms[0].action="trialFundingcreate.action";
 document.forms[0].submit();   
 } 
} 
function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
	}
</SCRIPT>

<body>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
<c:set var="topic" scope="request" value="reviewfunding"/>
</c:if>
<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  != 'Submitted'}">
<c:set var="topic" scope="request" value="abstractfunding"/>
</c:if>
 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:token/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="trialFunding.addedittitle" /></h2>
    <s:hidden name="page" />
    <s:hidden name="cbValue"/>
    <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="fundingMechanism">                      
                            <fmt:message key="trialFunding.funding.mechanism"/>:<span class="required">*</span>
                        </label>
                     </td>
                     <s:set name="fundingMechanism" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                      <td class="value"> 
                        <s:select id="fundingMechanism" headerKey="" headerValue="" 
                           name="trialFundingWebDTO.fundingMechanismCode" 
                           list="#fundingMechanism"  
                           listKey="fundingMechanismCode" 
                           listValue="fundingMechanismCode" 
                           cssStyle="width:60px"/> 
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialFundingWebDTO.fundingMechanismCode</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr>
                
                <tr> 
                     <td scope="row" class="label">
                          <label for="institutionCode">
                            <fmt:message key="trialFunding.institution.code"/>:<span class="required">*</span>
                          </label>
                     </td>              
                     <s:set name="nihInstitute" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                      <td class="value">
                        <s:select id="institutionCode" headerKey="" headerValue="" 
                           name="trialFundingWebDTO.nihInstitutionCode" 
                           list="#nihInstitute"  
                           listKey="nihInstituteCode" 
                           listValue="nihInstituteCode" 
                           cssStyle="width:50px"/>
                           <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialFundingWebDTO.nihInstitutionCode</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="serialNumber">
                            <fmt:message key="trialFunding.serial.number"/>:<span class="required">*</span>
                     </label>
                     </td>
                     <td class="value">
                        <s:textfield id="serialNumber" name="trialFundingWebDTO.serialNumber" maxlength="6" cssStyle="width:80px"/>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialFundingWebDTO.serialNumber</s:param>
                               </s:fielderror>                            
                             </span>
                      </td> 
                </tr>
                <tr>
                     <td scope="row" class="label">
                     <label for="monitorCode">
                            <fmt:message key="studyProtocol.monitorCode"/>:<span class="required">*</span>
                     </label>
                    </td>
                    <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                      <td class="value">
                        <s:select id="monitorCode" headerKey="" headerValue="" 
                           name="trialFundingWebDTO.nciDivisionProgramCode" 
                           list="#monitorCodeValues"  
                           cssStyle="width:206px"/>
                           <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>trialFundingWebDTO.nciDivisionProgramCode</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>         
                </tr>         
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                </ul>   
            </del>
        </div>

                   
    </s:form>
   </div>
 </body>
 </html>