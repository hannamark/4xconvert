<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">


function handleAction(){   
    document.forms[0].page.value = "Submit";
    document.forms[0].action="submitTrialcreate.action";
    document.forms[0].submit();  
}

</SCRIPT>

<body>
<!-- main content begins-->
    <h1><fmt:message key="submit.trial.page.header"/></h1>
    <div class="box" id="filters">
    <s:form><s:actionerror/>
        <input type="hidden" name="page" />
        <table class="form"> 
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.trialDetails"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
  
          <tr>
                <td scope="row" class="label">
                    <label for="identifier"> <fmt:message key="submit.trial.identifier"/></label>
                </td>
                <td>
                    <s:textfield name="leadOrgIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                     <label for="trialTitle"> <fmt:message key="submit.trial.title"/></label>
                </td>
                <td>
                    <s:textarea name="trialTitle"  cols="40" rows="4"  />
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialPhase"> <fmt:message key="submit.trial.phase"/></label> 
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="trialPhase" list="#phaseCodeValues"  value="trialPhase" cssStyle="width:206px" />
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="trialType" list="#typeCodeValues"  value="trialType" cssStyle="width:206px" />
                </td>
          </tr>
          
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.responsibleParty"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="nameOfficialTitle"> <fmt:message key="submit.trial.nameOfficialTitle"/></label>
                </td>
                <td>
                    <s:textfield name="nameOfficialTitle"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="organization"> <fmt:message key="submit.trial.organization"/></label>
                </td>
                <td>
                    <s:textfield name="organization"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="contactInfo"> <fmt:message key="submit.trial.contactInfo"/></label>
                </td>
                <td>
                    <s:textfield name="contactInfo"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.leadOrgInvestigator"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="leadOrganization"> <fmt:message key="submit.trial.leadOrganization"/></label>
                </td>
                <td>
                    <s:textfield name="leadOrganization"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="principalInvestigator"> <fmt:message key="submit.trial.principalInvestigator"/></label>
                </td>
                <td>
                    <s:textfield name="principalInvestigator"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.summary4Info"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="fundingCategory"> <fmt:message key="submit.trial.fundingCategory"/></label>
                </td>
                <td>
                    <s:textfield name="fundingCategory"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="fundingSponsor"> <fmt:message key="submit.trial.fundingSponsor"/></label>
                </td>
                <td>
                    <s:textfield name="fundingSponsor"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.grantInfo"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td colspan="2">
					<table>
					   <tr>
							<td scope="row" class="label">
							    <label for="fundingMechanism"> <fmt:message key="submit.trial.fundingMechanism"/></label>
							</td>
							<td scope="row" class="label">
							    <label for="instituteCode"> <fmt:message key="submit.trial.instituteCode"/></label>
							</td>
							<td scope="row" class="label">
                                <label for="serialNumber"> <fmt:message key="submit.trial.serialNumber"/></label>
                            </td>
							<td scope="row" class="label">
							    <label for="divProgram"> <fmt:message key="submit.trial.divProgram"/></label>
							</td>
					   </tr>
					   <tr>
							<s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" name="fundingMechanism" list="#phaseCodeValues"  value="trialPhase" cssStyle="width:106px" />
							</td>
							<s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" name="instituteCode" list="#phaseCodeValues"  value="trialPhase" cssStyle="width:106px" />
							</td>
							<td>
                                <s:textfield name="serialNumber"  maxlength="200" size="100"  cssStyle="width:100px" />
                            </td>
							<s:set name="programCodes" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" name="divPrgCode" list="#programCodes"  value="trialPhase" cssStyle="width:106px" />
							</td>
					  </tr>
					</table>
                </td>
          </tr>

        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Submit Trial Details</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>  
        
   </s:form>

 </div>
   
</body>
</html>
