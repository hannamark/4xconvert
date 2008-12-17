<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialFunding.addedittitle"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

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

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
    <h2><fmt:message key="trialFunding.addedittitle" /></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="cbValue" value="${cbValue}" />
    <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="fundingMechanism">                      
                            <fmt:message key="trialFunding.funding.mechanism"/><span class="required">*</span>
                        </label>
                     </td>
                     <s:set name="fundingMechanism" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                      <td class="value"> 
                        <s:select headerKey="" headerValue="" 
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
                            <fmt:message key="trialFunding.institution.code"/><span class="required">*</span>
                          </label>
                     </td>              
                     <s:set name="nihInstitute" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
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
                            <fmt:message key="trialFunding.serial.number"/><span class="required">*</span>
                     </label>
                     </td>
                     <td class="value">
                        <s:textfield name="trialFundingWebDTO.serialNumber" maxlength="6" cssStyle="width:80px"/>
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
                            <fmt:message key="studyProtocol.monitorCode"/><span class="required">*</span>
                     </label>
                    </td>
                    <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
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
                <tr>
                     <td scope="row" class="label">
                     <label for="fundingTypeCode">
                            <fmt:message key="trialFunding.funding.typecode"/>
                     </label>
                    </td>
                    <td class="value">
                        
                        <s:select  
                           name="trialFundingWebDTO.fundingTypeCode" 
                           list="#{'':'','1':'1', '2':'2', '3':'3', '4':'4', '5':'5', '6':'6', '7':'7', '8':'8', '9':'9'}" 
                           cssStyle="width:40px" />                           
                      </td>         
                </tr>                   
                <tr>
                     <td scope="row" class="label">
                     <label for="grantYear">
                            <fmt:message key="trialFunding.grant.year" />
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="trialFundingWebDTO.suffixgrantYear" maxlength="200"  cssStyle="width:100px"/>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="suffix">
                            <fmt:message key="trialFunding.suffix" />
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="trialFundingWebDTO.suffixOther" maxlength="200" cssStyle="width:100px"/>
                      </td>         
                </tr>                 
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                </ul>   
            </del>
        </div>

                   
    </s:form>
   </div>
 </body>
 </html>