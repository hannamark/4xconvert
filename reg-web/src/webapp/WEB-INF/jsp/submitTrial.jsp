<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>   
    <s:head/>
</head>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "statusDate", "submitTrial");
        addCalendar("Cal2", "Select Date", "startDate", "submitTrial");
        addCalendar("Cal3", "Select Date", "completionDate", "submitTrial");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>

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
    <s:form name="submitTrial" method="POST" enctype="multipart/form-data"><s:actionerror/>
        <input type="hidden" name="page" />
        <p>Add Trial into NCI Clinical Trials Portal - Powered by (caCTUS) by submitting this form. Please note: asterisks (<span class="required">*</span>) indicate required fields. </p>
        <table class="form"> 
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.trialDetails"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
  
          <tr>
                <td scope="row" class="label">
                    <label for="leadOrgIdentifier"> <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="participationWebDTO.localProtocolIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>participationWebDTO.localProtocolIdentifier</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
          <tr>
                <td scope="row" class="label">
                     <label for="trialTitle"> <fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textarea name="protocolWebDTO.trialTitle"  cols="75" rows="4"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>protocolWebDTO.trialTitle</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialPhase"> <fmt:message key="submit.trial.phase"/><span class="required">*</span></label> 
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="protocolWebDTO.trialPhase" list="#phaseCodeValues"  value="trialPhase" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>protocolWebDTO.trialPhase</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="trialType" list="#typeCodeValues"  value="trialType" cssStyle="width:206px" />
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
							<s:set name="fundingMechanismValues" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getFundingMechanisms()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" 
							         name="trialFundingWebDTO.fundingMechanismCode" 
							         list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     value="fundingMechanism" 
                                     cssStyle="width:106px" />
							</td>
							<s:set name="nihInstituteCodes" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getNihInstitutes()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" 
							         name="trialFundingWebDTO.nihInstitutionCode" 
							         list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"   
							         value="instituteCode" 
							         cssStyle="width:106px" />
						             <span class="formErrorMsg"> 
				                        <s:fielderror>
				                        <s:param>trialFundingWebDTO.nihInstitutionCode</s:param>
				                       </s:fielderror>                            
				                     </span>
							</td>
							<td>
                                <s:textfield name="trialFundingWebDTO.serialNumber"  maxlength="200" size="100"  cssStyle="width:100px" />
	                            <span class="formErrorMsg"> 
	                                <s:fielderror>
	                                <s:param>trialFundingWebDTO.serialNumber</s:param>
	                                </s:fielderror>                            
	                            </span>
                            </td>
							<s:set name="programCodes" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="All" name="trialFundingWebDTO.nciDivisionProgramCode" list="#programCodes"  value="trialPhase" cssStyle="width:106px" />
		                        <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>trialFundingWebDTO.nciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
							</td>
					  </tr>
					</table>
                </td>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><h2><fmt:message key="submit.trial.statusDates"/></h2></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="currentTrialStatus"> <fmt:message key="submit.trial.currentTrialStatus"/><span class="required">*</span></label>
                </td>
                    <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="overallStatusWebDTO.statusCode" list="#statusCodeValues"  value="trialStatus" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>overallStatusWebDTO.statusCode</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
          </tr>
        <tr>
            <td scope="row" class="label"><label for="statusDate"><fmt:message
                key="submit.trial.currentTrialStatusDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="overallStatusWebDTO.statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                            <s:param>overallStatusWebDTO.statusDate</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
        </tr>
        <s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />
        <tr>
            <td scope="row" class="label"><label for="startDate"><fmt:message
                key="submit.trial.studyStartDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="protocolWebDTO.startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="protocolWebDTO.startDateType" list="#dateTypeList" />
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>protocolWebDTO.startDate</s:param>
                   </s:fielderror>                            
                </span>
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>protocolWebDTO.startDateType</s:param>
                   </s:fielderror>                            
                </span>
            </td>

        </tr>
        <tr>
            <td scope="row" class="label"><label for="completionDate">
            <fmt:message key="submit.trial.primaryCompletionDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="protocolWebDTO.completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="protocolWebDTO.completionDateType" list="#dateTypeList" />
                <span class="formErrorMsg"> 
                   <s:fielderror>
                   <s:param>protocolWebDTO.completionDate</s:param>
                   </s:fielderror>                            
                </span>
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>protocolWebDTO.completionDateType</s:param>
                   </s:fielderror>                            
                </span>
            </td>

        </tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
        <tr>
              <th colspan="2"><h2><fmt:message key="submit.trial.documents"/></h2></th>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
               <fmt:message key="submit.trial.docInstructionalText"/>
            </td>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
      
        <tr>
              <td scope="row" class="label">
              <label for="protocolDocument">
                     <fmt:message key="submit.trial.protocolDocument"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
                 <s:file name="upload" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDocumentWebDTO.uploadFileName</s:param>
                   </s:fielderror>                            
                 </span>
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
