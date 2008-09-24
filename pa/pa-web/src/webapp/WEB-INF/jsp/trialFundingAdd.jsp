<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialFunding.title"/></title>
    <s:head />
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

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialFunding.subtitle" /></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="cbValue" value="${cbValue}" />
    <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="fundingMechanism"><dfn title="Context sensitive help text or tooltip here.">                      
                            <fmt:message key="trialFunding.funding.mechanism"/>*</dfn>
                        </label>
                     </td>
                     <s:set name="fundingMechanism" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
                           name="trialFundingWebDTO.fundingMechanismCode" 
                           list="#fundingMechanism"  
                           listKey="fundingMechanismCode" 
                           listValue="fundingMechanismCode" 
                           cssStyle="width:206px"/>
                      </td>         
                </tr>
                
                <tr> 
                     <td scope="row" class="label">
                          <label for="institutionCode"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="trialFunding.institution.code"/>*</dfn>
                          </label>
                     </td>              
                     <s:set name="nihInstitute" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
                           name="trialFundingWebDTO.nihInstitutionCode" 
                           list="#nihInstitute"  
                           listKey="nihInstituteCode" 
                           listValue="nihInstituteCode" 
                           cssStyle="width:206px"/>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="serialNumber"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="trialFunding.serial.number"/>*</dfn>
                     </label>
                     </td>
                     <td class="value">
                        <s:textfield name="trialFundingWebDTO.serialNumber" maxlength="200" cssStyle="width:206px"/>
                      </td> 
                </tr>
                <tr>
                     <td scope="row" class="label">
                     <label for="monitorCode"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="studyProtocol.monitorCode"/>*</dfn>
                     </label>
                    </td>
                    <s:set name="monitorCodeValues" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
                           name="trialFundingWebDTO.nciDivisionProgramCode" 
                           list="#monitorCodeValues"  
                           cssStyle="width:206px"/>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="fundingTypeCode"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="trialFunding.funding.typecode"/></dfn>
                     </label>
                    </td>
                    <td class="value">
                        
                        <s:select  
                           name="trialFundingWebDTO.fundingTypeCode" 
                           list="#{'1':'1', '2':'2', '3':'3', '4':'4', '5':'5', '6':'6', '7':'7', '8':'8', '9':'9'}" 
                           cssStyle="width:206px" />                           
                      </td>         
                </tr>                   
                <tr>
                     <td scope="row" class="label">
                     <label for="grantYear"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="trialFunding.grant.year" /></dfn>
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="trialFundingWebDTO.suffixgrantYear" maxlength="200"  cssStyle="width:206px"/>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="suffix"><dfn title="Context sensitive help text or tooltip here.">
                            <fmt:message key="trialFunding.suffix" /></dfn>
                     </label>
                    </td>
                    <td class="value">
                        <s:textfield name="trialFundingWebDTO.suffixOther" maxlength="200" cssStyle="width:206px"/>
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