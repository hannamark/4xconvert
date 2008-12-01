<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialValidation.page.title"/></title>   
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

function handleSave(){
	document.forms[0].action="trialValidationupdate.action";
	document.forms[0].submit(); 
}
function handleAccept(){
	document.forms[0].actionName.value="Accept";
	document.forms[0].action="trialValidationupdate.action";
    document.forms[0].submit();  
}

function handleReject(){
   document.forms[0].action="trialValidationupdate.action";
   document.forms[0].actionName.value="Reject";
   document.forms[0].submit(); 
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
    <h1><fmt:message key="trialValidation.title" /></h1>
    <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
    <div class="box" id="filters">
    <pa:sucessMessage/>
    <s:form name="trialValidation" method="POST" ><s:actionerror/>
    <input type="hidden" name="studyProtocolWebDTO.actionName" id="actionName" />
        <p>Please note: asterisks (<span class="required">*</span>) indicate required fields. </p>
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="trialValidation.trialDetails"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>
                <td scope="row" class="label">
                    <label for="leadOrgIdentifier"> <fmt:message key="trialValidation.leadOrgidentifier"/><span class="required">*</span></label>
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
                     <label for="trialTitle"> <fmt:message key="trialValidation.title"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textarea name="studyProtocolWebDTO.trialTitle"  cols="75" rows="4"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>protocolWebDTO.trialTitle</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialPhase"> <fmt:message key="trialValidation.phase"/><span class="required">*</span></label> 
                </td>
              
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="-Select-" name="studyProtocolWebDTO.trialPhase" list="#phaseCodeValues" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>studyProtocolWebDTO.trialPhase</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
          <td  scope="row" class="label">
              <label for="studyProtocolType"> <fmt:message key="trialValidation.trialType"/><span class="required">*</span></label> 
          </td>
          <td class="value"><s:select id="studyProtocolType" name="studyProtocolWebDTO.studyProtocolType" list="#{'Observational':'Observational', 'Interventional':'Interventional'}" cssStyle="width:206px"/>
          </td>
    </tr>
    <tr>
          <td  scope="row" class="label">
              <label for="trialPurpose"><fmt:message key="trialValidation.purpose"/><span class="required">*</span></label> 
          </td>
              <s:set name="purposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
          <td>                                             
              <s:select headerKey="" headerValue="-Select-" name="studyProtocolWebDTO.primaryPurposeCode" list="#purposeCodeValues"  cssStyle="width:206px" />
          </td>
    </tr>
    <!--  leadOrganization/PI -->
    <tr>
    	<th colspan="2"><fmt:message key="trialValidation.leadOrgInvestigator"/></th>
    </tr>
    <tr>
      <td colspan="2" class="space">&nbsp;</td>
    </tr>
    <tr>
		<td scope="row" class="label">
			<label for="org"><fmt:message key="trialValidation.leadOrganization"/><span class="required">*</span></label> 
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
			<label for="org"><fmt:message key="trialValidation.principalInvestigator"/><span class="required">*</span></label> 
		</td>
		<td class="value">
			<div id="loadPersField">
			<%@ include file="/WEB-INF/jsp/nodecorate/displayLeadPrincipalInvestigator.jsp" %>
			</div>		
			</td>
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
				<label for="summaryFourFundingCategoryCode">Summary 4 Funding Category :</label> 
			</td>
		        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
            <td>
                <s:select headerKey="" headerValue="-Select-" name="studyResourcingWebDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" cssStyle="width:206px" />
                <span class="formErrorMsg"> 
                     <s:fielderror>
                     <s:param>studyResourcingWebDTO.summaryFourFundingCategoryCode</s:param>
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
		
    </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleSave()" ><span class="btn_img"><span class="save">Save</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="handleAccept()" ><span class="btn_img"><span class="save">Accept</span></span></s:a> 
                            <s:a href="#" cssClass="btn" onclick="handleReject()" ><span class="btn_img"><span class="save">Reject</span></span></s:a> 
                        </li>
                </ul>   
            </del>
        </div>   
   </s:form>

 </div> 
</body>
</html>
