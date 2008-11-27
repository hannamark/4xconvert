<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>   
    <s:head/>
</head>
<!-- po integration -->
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<!-- /po integration -->
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "overallStatusWebDTO.statusDate", "submitTrial");
        addCalendar("Cal2", "Select Date", "protocolWebDTO.startDate", "submitTrial");
        addCalendar("Cal3", "Select Date", "protocolWebDTO.completionDate", "submitTrial");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<!-- c:url value="/protected/ajaxorganizationContactsavePI.action" var="lookupOrgUrl"/-->

<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="submitProtocol"/>
<c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<SCRIPT LANGUAGE="JavaScript">
var orgid;
var persid;
function setorgid(orgIdentifier){
	orgid = orgIdentifier;
}
function setpersid(persIdentifier){
	persid = persIdentifier;
}
//
function lookup4loadleadorg(){
    showPopWin('${lookupOrgUrl}', 1050, 400, loadLeadOrgDiv, 'Select Organization');
}
function lookup4loadleadpers(){
    showPopWin('${lookupPersUrl}', 1050, 400, loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
    showPopWin('${lookupOrgUrl}', 1050, 400, loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadresponsibleparty(){	
	showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 1050, 400, createOrgContactDiv, 'Select Responsible contact');
}
function lookup4loadSummary4Sponsor(){
    showPopWin('${lookupOrgUrl}', 1050, 400, loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
}
//
function loadLeadOrgDiv() {	
	var url = '/registry/protected/ajaxSubmitTrialActiondisplayLeadOrganization.action?orgId='+orgid;
    var div = document.getElementById('loadOrgField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
    callAjax(url, div);    
}
function loadLeadPersDiv() {
	var url = '/registry/protected/ajaxSubmitTrialActiondisplayLeadPrincipalInvestigator.action?persId='+persid;
    var div = document.getElementById('loadPersField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
    callAjax(url, div);    
}
function loadSponsorDiv() {
	var url = '/registry/protected/ajaxSubmitTrialActiondisplaySelectedSponsor.action?orgId='+orgid;
    var div = document.getElementById('loadSponsorField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Sponsor...</div>';
    callAjax(url, div);        			
	document.getElementById('lookupbtn4RP').disabled = "";
}
function createOrgContactDiv() {	
	var url = '/registry/protected/ajaxSubmitTrialActioncreateOrganizationContacts.action?persId='+persid+'&orgId='+orgid;
    var div = document.getElementById('loadResponsibleContactField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding primary contact...</div>';
    callAjax(url, div);
    document.getElementById('lookupbtn4RP').disabled = "";
}
function loadSummary4SponsorDiv() {
	var url = '/registry/protected/ajaxSubmitTrialActiondisplaySummary4FundingSponsor.action?orgId='+orgid;
    var div = document.getElementById('loadSummary4FundingSponsorField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Summary 4 Sponsor...</div>';
    callAjax(url, div);
}
//
function handleAction(){  
    document.forms[0].page.value = "Submit";
    document.forms[0].action="submitTrialcreate.action";
    document.forms[0].submit();  
}
function submitProtocol (){	
	var action = "submitTrialcreate.action";	
    document.forms[0].page.value = "Submit";
    document.forms[0].action=action;
    document.forms[0].submit();
	showPopWin('${submitProtocol}', 600, 200, '', 'Submit protocol');
}
function callAjax(url, div){
    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
    return false;
}
function manageRespPartyLookUp(){		
	for(var i=0; i<2; i++) {			
		if(document.forms[0].respparty[i].checked==true) {
			if(document.forms[0].respparty[i].value == 'sponsor') {				
				document.getElementById('rpcid').style.display='';
			}
			if(document.forms[0].respparty[i].value == 'pi') {					
					document.getElementById('rpcid').style.display='none';
			}
		}		
	}
}
function addGrant(){
	var fundingMechanismCode = document.getElementById('fundingMechanismCode').value;
	var nihInstitutionCode = document.getElementById('nihInstitutionCode').value;
	var serialNumber = document.getElementById('serialNumber').value;
	var nciDivisionProgramCode = document.getElementById('nciDivisionProgramCode').value;
	var  url = '/registry/protected/ajaxSubmitTrialActionaddGrant.action?fundingMechanismCode='+fundingMechanismCode+'&nihInstitutionCode='+nihInstitutionCode+'&serialNumber='+serialNumber+'&nciDivisionProgramCode='+nciDivisionProgramCode;	
   	var div = document.getElementById('grantdiv');   
   	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
   	callAjax(url, div);
	resetGrantRow();
}
function deleteGrantRow(rowid){	
	var  url = '/registry/protected/ajaxSubmitTrialActiondeleteGrant.action?uuid='+rowid;
   	var div = document.getElementById('grantdiv');
   	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
   	callAjax(url, div);				
}
function enableGrantAddButton(){	
	var fundingMechanismCode = document.getElementById('fundingMechanismCode').value;	
	var nihInstitutionCode = document.getElementById('nihInstitutionCode').value;
	var serialNumber = document.getElementById('serialNumber').value;
	var nciDivisionProgramCode = document.getElementById('nciDivisionProgramCode').value;
	//alert("fundingMechanismCode "+fundingMechanismCode+"+"+nihInstitutionCode+"+"+serialNumber+"+"+nciDivisionProgramCode);
	if(fundingMechanismCode != '' && nihInstitutionCode != '' && serialNumber != '' && nciDivisionProgramCode != '') {
		document.getElementById('grantbtnid').disabled = false;
	} else {
		document.getElementById('grantbtnid').disabled = true;
	}
}
function resetGrantRow(){
	document.getElementById('fundingMechanismCode').value = '';
	document.getElementById('nihInstitutionCode').value = '';
	document.getElementById('serialNumber').value = '';
	document.getElementById('nciDivisionProgramCode').value = '';
	document.getElementById('grantbtnid').disabled = true;
}
</SCRIPT>
<script language="javascript">
function toggledisplay (it, box) {
  var vis = (box.checked) ? "block" : "none";
  document.getElementById(it).style.display = vis;
}
function toggledisplay2 (it) {
  var vis = document.getElementById(it).style.display
  if (vis == "block") { document.getElementById(it).style.display = "none"; }
                 else { document.getElementById(it).style.display = "block"; }
}
</script>	

<body>
<!-- main content begins-->
    <h1><fmt:message key="submit.trial.page.header"/></h1>
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="submitTrial" method="POST" enctype="multipart/form-data"><s:actionerror/>
        <input type="hidden" name="page" />
        <p>Add Trial into NCI Clinical Trials Reporting Portal  by submitting this form. Please note: asterisks (<span class="required">*</span>) indicate required fields. </p>
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
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
                    <s:select headerKey="" headerValue="--Select--" name="protocolWebDTO.trialPhase" list="#phaseCodeValues" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>protocolWebDTO.trialPhase</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/><span class="required">*</span></label> 
                </td>
                <td>
				<input type="radio" name="trialType" value="Observational" checked="checked"> Observational 
				<input type="radio" name="trialType" value="Interventional" onclick="setRequiredFieldIndicators();"> Interventional
				</td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialPurpose"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialPurpose" list="#typeCodeValues"  value="trialType" cssStyle="width:206px" />
                </td>
          </tr>          
                    <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr> 
          
        <tr>
              <th colspan="2"><fmt:message key="submit.trial.leadOrgInvestigator"/></th>
        </tr>
                    <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr>
					<td scope="row" class="label">
						<label for="org"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadOrgField">
						<%@ include file="/WEB-INF/jsp/nodecorate/displayLeadOrganization.jsp" %>
						</div>		
					</td>
		  </tr>
          <!-- include po person jsp -->
          <tr>
					<td scope="row" class="label">
						<label for="org"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadPersField">
						<%@ include file="/WEB-INF/jsp/nodecorate/displayLeadPrincipalInvestigator.jsp" %>
						</div>		
					</td>
					
				</tr>
          <tr>          
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
        <tr>
             <th colspan="2"><fmt:message key="submit.trial.sponsorResParty"/></th>
        </tr>          
        <tr><td colspan="2" class="space">&nbsp;</td></tr>
        <tr>
					<td scope="row" class="label">
						<label for="sponsor"> Sponsor:<span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadSponsorField">
						<%@ include file="/WEB-INF/jsp/nodecorate/displaySponsor.jsp" %>
						</div>		
					</td>
		</tr>   
				<tr>
				<td scope="row" class="label">Responsible Party:<span class="required">*</span></td>
				<td>
				<input type="radio" name="respparty" value="pi" checked="checked" onclick="manageRespPartyLookUp();"> PI 
				<input type="radio" name="respparty" value="sponsor" onclick="manageRespPartyLookUp();"> Sponsor
				</td>
				</tr>				
				<tr id="rpcid" style="display:none">
				<td scope="row" class="label">
					<label for="responsiblepartycontact"> Responsible Party Contact :</label> 
				</td>				
				<td class="value">
					<div id="loadResponsibleContactField">
						<%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
					</div>								      
				</td>
				</tr>
          <tr>
                <td scope="row" class="label">
                   Email Address:<span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="contactEmail"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>contactEmail</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
                </tr>
                <tr>
                <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
                <td class="value">
                    <s:textfield name="contactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>contactPhone</s:param>
                       </s:fielderror>                            
                     </span>
                </td>           
          </tr>				
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
		  <!--  summary4 information -->
          <tr>
                <th colspan="2">Summary 4 Information</th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
          <tr>
					<td scope="row" class="label">
						<label for="org">Summary 4 Funding Category :</label> 
					</td>
			          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="--Select--" 
                            name="summary4FundingCategory" 
                            list="#summaryFourFundingCategoryCodeValues"
                            cssStyle="width:206px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>summary4FundingCategory</s:param>
                               </s:fielderror>                            
                             </span>
                      </td>
          </tr>          
          <tr>
               
					<td scope="row" class="label">
						<label for="sponsor"> Summary 4 Funding Sponsor : </label> 
					</td>
					<td class="value">
						<div id="loadSummary4FundingSponsorField">
						<%@ include file="/WEB-INF/jsp/nodecorate/displaySummary4FundingSponsor.jsp" %>
						</div>		
					</td>
		</tr>  
                         			                    
          
          <tr>  <td colspan="2" class="space">&nbsp;</td></tr>
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.grantInfo"/></th> 
          </tr>
          
         
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td colspan="3">
					<table class="data2">	
					   <tr>
							<th>Funding Mechanism</th>
							<th><fmt:message key="submit.trial.instituteCode"/></th>
							<th><fmt:message key="submit.trial.serialNumber"/></th>
							<th><fmt:message key="submit.trial.divProgram"/></th>
							<th>&nbsp;&nbsp;&nbsp;&nbsp;</th>
				 	</tr>
					   
					   <tr>
							<s:set name="fundingMechanismValues" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getFundingMechanisms()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" 
							         name="trialFundingWebDTO.fundingMechanismCode" 
							         list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     id="fundingMechanismCode"
                                     value="trialFundingWebDTO.fundingMechanismCode" 
                                     cssStyle="width:150px" onblur="enableGrantAddButton();"/>
							</td>
							<s:set name="nihInstituteCodes" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getNihInstitutes()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" 
							         name="trialFundingWebDTO.nihInstitutionCode" 
							         list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"
                                     id="nihInstitutionCode"
							         value="trialFundingWebDTO.nihInstitutionCode" 
							         cssStyle="width:150px" onblur="enableGrantAddButton();" />
						             <span class="formErrorMsg" >
				                        <s:fielderror>
				                        <s:param>trialFundingWebDTO.nihInstitutionCode</s:param>
				                       </s:fielderror>                            
				                     </span>
							</td>
							<td>
                                <s:textfield name="trialFundingWebDTO.serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px" onblur="enableGrantAddButton();" />
	                            <span class="formErrorMsg"> 
	                                <s:fielderror>
	                                <s:param>trialFundingWebDTO.serialNumber</s:param>
	                                </s:fielderror>                            
	                            </span>
                            </td>
							<s:set name="programCodes" value="@gov.nih.nci.pa.enums.MonitorCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" name="trialFundingWebDTO.nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="trialFundingWebDTO.nciDivisionProgramCode" cssStyle="width:150px" onblur="enableGrantAddButton();"/>
		                        <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>trialFundingWebDTO.nciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
							</td>
							<td> <input type="button" id="grantbtnid" value="Add Grant.." onclick="addGrant();" disabled="disabled"/></td>
							<td> &nbsp;</td><td> &nbsp;</td><td> &nbsp;</td>
					  </tr>
					</table>
                </td>
          </tr>
         
          <tr>
                <td colspan="2" class="space">       <div id="grantdiv">   
                <%@ include file="/WEB-INF/jsp/nodecorate/addGrant.jsp" %>
                </div>
                </td>
                
          </tr>
          

	
       
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.statusDates"/></th>
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
                    <s:select headerKey="" headerValue="--Select--" name="overallStatusWebDTO.statusCode" list="#statusCodeValues"  value="overallStatusWebDTO.statusCode" cssStyle="width:206px" />
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
              <th colspan="2">IND/IDE Information</th>
        </tr>     
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
			
				<tr><td colspan="2" class="space">
						<%@ include file="/WEB-INF/jsp/nodecorate/indide.jsp" %>
						</td>
		  		</tr>
		<tr>	
			<td colspan="2" class="space">
		   	<div id="indidediv">	     
		  							
						<%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
		  		
		    </div>
		    </td>
        </tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
        <tr>
              <th colspan="2"><fmt:message key="submit.trial.documents"/></th>
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
                 <s:file name="protocolDoc" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDocumentWebDTO.protocolDocFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>
         
         <tr>
              <td scope="row" class="label">
              <label for="irbApproval">
                     <fmt:message key="submit.trial.irbApproval"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
                 <s:file name="irbApproval" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDocumentWebDTO.irbApprovalFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>         
         <tr>
              <td scope="row" class="label">
              <label for="particpating sites">List of Participating Sites</label>
             </td>
             <td class="value">
                 <s:file name="participatingSites" cssStyle="width:270px"/>                 
               </td>         
         </tr>         
         
         <tr>
              <td scope="row" class="label">
              <label for="informedConsent">Informed Consent Document</label>
             </td>
             <td class="value">
                 <s:file name="informedConsentDocument" cssStyle="width:270px"/>            
               </td>         
         </tr>         
         <tr>
              <td scope="row" class="label">
              <label for="otherDocument">Other</label>
             </td>
             <td class="value">
                 <s:file name="otherDocument" cssStyle="width:270px"/>                 
               </td>         
         </tr> 

        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="submitProtocol()"><span class="btn_img"><span class="save">Submit Trial Details</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>  
      <input type="hidden" name="uuidhidden"/>  
   </s:form>

 </div> 
</body>
</html>
