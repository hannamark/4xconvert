<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="amend.trial.page.title"/></title>   
    <s:head/>
</head>
<!-- po integration -->
<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<!-- /po integration -->
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "submitTrial");
        addCalendar("Cal2", "Select Date", "trialDTO.startDate", "submitTrial");
        addCalendar("Cal3", "Select Date", "trialDTO.completionDate", "submitTrial");
        addCalendar("Cal4", "Select Date", "trialDTO.amendmentDate", "submitTrial");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<!-- c:url value="/protected/ajaxorganizationContactsavePI.action" var="lookupOrgUrl"/-->

<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="reviewProtocol"/>
<SCRIPT LANGUAGE="JavaScript">
var orgid;
var chosenname;
var persid;
var respartOrgid;
function setorgid(orgIdentifier, oname){
	orgid = orgIdentifier;
	chosenname = oname;
}
function setpersid(persIdentifier, sname){
	persid = persIdentifier;
	chosenname = sname;
}
//
function lookup4loadleadorg(){
    showPopWin('${lookupOrgUrl}', 900, 400, loadLeadOrgDiv, 'Select Lead Organization');
}
function lookup4loadleadpers(){
    showPopWin('${lookupPersUrl}', 900, 400, loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
    showPopWin('${lookupOrgUrl}', 900, 400, loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadresponsibleparty(){	
	showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 900, 400, createOrgContactDiv, 'Select Responsible Party Contact');
}
function lookup4loadSummary4Sponsor(){
    showPopWin('${lookupOrgUrl}', 900, 400, loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
}
//
function loadLeadOrgDiv() {	
	document.getElementById("trialDTO.leadOrganizationIdentifier").value = orgid;
    document.getElementById('trialDTO.leadOrganizationName').value = chosenname;
}
function loadLeadPersDiv() {
    document.getElementById("trialDTO.piIdentifier").value = persid;
    document.getElementById('trialDTO.piName').value = chosenname;

}
function loadSponsorDiv() {
	document.getElementById("trialDTO.sponsorIdentifier").value = orgid;
    document.getElementById('trialDTO.sponsorName').value = chosenname;
	document.getElementById('lookupbtn4RP').disabled = "";
	respartOrgid = orgid;
}
function createOrgContactDiv() {
	document.getElementById("trialDTO.responsiblePersonIdentifier").value = persid;
    document.getElementById('trialDTO.responsiblePersonName').value = chosenname;
    document.getElementById('lookupbtn4RP').disabled = "";
}
function loadSummary4SponsorDiv() {
	document.getElementById("trialDTO.summaryFourOrgName").value = chosenname;
    document.getElementById('trialDTO.summaryFourOrgIdentifier').value = orgid;
}
//
function reviewProtocol (){	
	var action = "amendTrialreview.action";	
    document.forms[0].page.value = "save";
    document.forms[0].action=action;
    document.forms[0].submit();
	showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
}
function cancelProtocol (){   
    var action = "amendTrialcancel.action";   
    document.forms[0].page.value = "Submit";
    document.forms[0].action=action;
    document.forms[0].submit();
    //showPopWin('${cancelProtocol}', 600, 200, '', 'cancel Trial');
}
function callAjax(url, div){
    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
    return false;
}
function manageRespPartyLookUp(){
	if(document.getElementById('trialDTO.responsiblePartyTypepi').checked==true) {
		document.getElementById('rpcid').style.display='none';
        document.getElementById('trialDTO.responsiblePersonName').value = '';
	}
	if(document.getElementById('trialDTO.responsiblePartyTypesponsor').checked==true) {				
				document.getElementById('rpcid').style.display='';
	}
}
function addGrant(){
	var serialNumber = document.getElementById('serialNumber').value;
	var b = serialNumber.length;
    if (isNaN(serialNumber)){
        alert("Serial Number must be numeric");
        return false;
    }
	if (b < 5 || b > 6){
		alert("Serial Number must be 5 or 6 digits");
		return false;
	}
	var fundingMechanismCode = document.getElementById('fundingMechanismCode').value;
	var nihInstitutionCode = document.getElementById('nihInstitutionCode').value;
	var nciDivisionProgramCode = document.getElementById('nciDivisionProgramCode').value;
	var  url = '/registry/protected/ajaxAmendTrialActionaddGrant.action?fundingMechanismCode='+fundingMechanismCode+'&nihInstitutionCode='+nihInstitutionCode+'&serialNumber='+serialNumber+'&nciDivisionProgramCode='+nciDivisionProgramCode;	
   	var div = document.getElementById('grantdiv');   
   	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
   	callAjax(url, div);
	resetGrantRow();
}
function deleteGrantRow(rowid){	
	var  url = '/registry/protected/ajaxAmendTrialActiondeleteGrant.action?uuid='+rowid;
   	var div = document.getElementById('grantdiv');
   	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
   	callAjax(url, div);				
}
function enableGrantAddButton(){	
	var fundingMechanismCode = document.getElementById('fundingMechanismCode').value;	
	var nihInstitutionCode = document.getElementById('nihInstitutionCode').value;
	var serialNumber;
	serialNumber = document.getElementById('serialNumber').value;
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
function deleteIndIde(rowid){
	
	var  url = '/registry/protected/ajaxAmendTrialActiondeleteIndIde.action?uuid='+rowid;
	var div = document.getElementById('indidediv');
	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
	callAjax(url, div);				
}
function addIndIde(indIde,number,grantor,holdertype,programcode,expandedaccess,expandedaccesstype) {
	var  url = '/registry/protected/ajaxAmendTrialActionaddIdeIndIndicator.action?indIde='+indIde+'&number='+number+'&grantor='+grantor+'&holdertype='+holdertype+'&programcode='+programcode+'&expandedaccess='+expandedaccess+'&expandedaccesstype='+expandedaccesstype;
	var div = document.getElementById('indidediv');
	div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
	callAjax(url, div);
	resetValues();

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
    <h1><fmt:message key="amend.trial.page.header"/></h1>
    <c:set var="topic" scope="request" value="submit_trial"/> 
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="submitTrial" method="POST" enctype="multipart/form-data">
        <s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
        </s:if>
        <s:else>
            <s:actionerror/>
        </s:else>
    <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
    <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/> 
    <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
    <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
    <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
    <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>

        <input type="hidden" name="page" />
        <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
        <table class="form">
        <tr>
                <th colspan="2"><fmt:message key="trial.amendDetails"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>     
            <td scope="row" class="label">
                <label for="Identifier">
                    <fmt:message key="view.trial.amendmentNumber"/>                
                </label>
          </td>
          <td>
            <s:textfield name="trialDTO.localAmendmentNumber"  maxlength="200" size="100"  cssStyle="width:200px"  />
              <span class="formErrorMsg"> 
              <s:fielderror>
              <s:param>trialDTO.localAmendmentNumber</s:param>
              </s:fielderror>                            
             </span> 
          </td>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>     
            <td scope="row" class="label">
                <label for="Date">
                    <fmt:message key="view.trial.amendmentDate"/>
                    <span class="required">*</span>                
                </label>
          </td>
          <td class="value">
          <s:textfield name="trialDTO.amendmentDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal4')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                            <s:param>trialDTO.amendmentDate</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
          </tr> 
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>     
    		<td scope="row" class="label">
    			<label for="Identifier">
    				<fmt:message key="view.trial.identifier"/>                
            	</label>
          </td>
          <td class="value">
          	<c:out value="${requestScope.trialDTO.assignedIdentifier}"/> 
          </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_localProtocolIdentifier"> <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.localProtocolIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.localProtocolIdentifier</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_nctNumber"> <fmt:message key="submit.trial.nctNumber"/></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>                
          </tr>
          <tr>
                <td scope="row" class="label">
                     <label for="submitTrial_protocolWebDTO_trialTitle"> <fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textarea name="trialDTO.officialTitle"  cols="75" rows="4" />
                    <span class="info">Max 4000 characters</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.trialTitle</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_trialPhase"> <fmt:message key="submit.trial.phase"/><span class="required">*</span></label> 
                </td>
              
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.phaseCode" list="#phaseCodeValues" cssStyle="width:206px" value="trialDTO.phaseCode"/>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.trialPhase</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_otherPhaseText"> <fmt:message key="submit.trial.otherPhaseText"/></label>
                </td>
                <td>
                    <s:textarea name="trialDTO.phaseOtherText"  cols="50" rows="2" />
                    <span class="info">Required if Phase equals &#39;Other&#39;</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.otherPhaseText</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/><span class="required">*</span></label> 
                </td>
                <td>
				    <input type="radio" name="trialDTO.trialType" value="Interventional" checked="checked"> Interventional
				    <input type="radio" name="trialDTO.trialType" value="Observational" disabled> Observational
				     <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialType</s:param>
                       </s:fielderror>                            
                     </span>				
				</td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_trialPurpose"><fmt:message key="submit.trial.purpose"/><span class="required">*</span></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.primaryPurposeCode" list="#typeCodeValues"  cssStyle="width:206px" value="trialDTO.primaryPurposeCode"/>
                     <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.trialPurpose</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_protocolWebDTO_otherPurposeText"> <fmt:message key="submit.trial.otherPurposeText"/></label>

                </td>
                <td>
                    <s:textarea name="trialDTO.primaryPurposeOtherText"  cols="50" rows="2" />
                    <span class="info">Required if Purpose equals &#39;Other&#39;</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.otherPurposeText</s:param>
                       </s:fielderror>                            
                     </span>
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
						<label for="submitTrial_selectedLeadOrg_name_part_0__value"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadOrgField">
						<%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
						</div>		
					</td>
		  </tr>
          <!-- include po person jsp -->
          <tr>
					<td scope="row" class="label">
						<label for="submitTrial_poLeadPiFullName"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadPersField">
						<%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
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
						<label for="submitTrial_selectedSponsor_name_part_0__value"> Sponsor:<span class="required">*</span></label> 
					</td>
					<td class="value">
						<div id="loadSponsorField">
						<%@ include file="/WEB-INF/jsp/nodecorate/trialSponsor.jsp" %>
						</div>		
					</td>
		</tr>   
		<tr>
				<td scope="row" class="label">
				    <label for="submitTrial_resppartysponsor"> <fmt:message key="submit.trial.responsibleParty"/><span class="required">*</span></label>
				</td>
				<td>
				<s:radio name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
				</td>
		</tr>
        <c:choose>
         <c:when test="${trialDTO.responsiblePartyType == 'sponsor'}">
             <tr id="rpcid" >
              <td scope="row" class="label">
                          <label for="submitTrial_resPartyContactFullName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
              </td>                                        
              <td class="value">
              <div id="loadResponsibleContactField">
                   <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
              </div>                                                                                             
              </td>
             </tr>
         </c:when>
         <c:otherwise>
            <tr id="rpcid" style="display:none">
                     <td scope="row" class="label">
                                 <label for="submitTrial_resPartyContactFullName"> <fmt:message key="submit.trial.responsiblePartyContact"/></label> 
                     </td>                                        
                     <td class="value">
                               <div id="loadResponsibleContactField">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
                               </div>                                                                                             
                     </td>
            </tr>
         </c:otherwise>
        </c:choose>                                          
				

          <tr>
                <td scope="row" class="label">
                   <label for="submitTrial_contactEmail"> <fmt:message key="submit.trial.responsiblePartyEmail"/><span class="required">*</span></label> 
                </td>
                <td class="value">
                    <s:textfield name="trialDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px"/>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>contactEmail</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
                </tr>
                <tr>
                <td scope="row" class="label">
                 <label for="submitTrial_contactPhone"> <fmt:message key="submit.trial.responsiblePartyPhone"/><span class="required">*</span></label>
                </td>
                <td class="value">
                    <s:textfield name="trialDTO.contactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
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
                <th colspan="2">Summary 4 Information (for trials at NCI-designated cancer centers)</th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
          <tr>	
          		<td scope="row" class="label">
					<label for="submitTrial_summary4FundingCategory">Summary 4 Funding Sponsor Type:</label> 
				</td>
			         <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <td class="value">
                     <s:select headerKey="" headerValue="--Select--" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
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
						<label for="submitTrial_selectedSummary4Sponsor_name_part_0__value"> Summary 4 Funding Sponsor: </label> 
				</td>
				<td class="value">
						<div id="loadSummary4FundingSponsorField">
							<%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
						</div>		
				</td>
			</tr>  
                         			                    
          
          <tr>  <td colspan="2" class="space">&nbsp;</td></tr>
          <table class="data2">
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.grantInfo"/></th> 
          </tr>
          
         
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
               <fmt:message key="submit.trial.grantInstructionalText"/>
            </td>
          </tr>
          <tr>
              <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td colspan="3">
					<table class="form">
					<tbody>	
					   <tr>
							<th><fmt:message key="submit.trial.fundingMechanism"/></th>
							<th><fmt:message key="submit.trial.instituteCode"/></th>
							<th><fmt:message key="submit.trial.serialNumber"/></th>
							<th><fmt:message key="submit.trial.divProgram"/></th>
							<th></th>
				 	</tr>
					   
					   <tr>
							<s:set name="fundingMechanismValues" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getFundingMechanisms()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" 
							         name="trialFundingDTO.fundingMechanismCode" 
							         list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     id="fundingMechanismCode"
                                     value="trialFundingDTO.fundingMechanismCode" 
                                     cssStyle="width:150px" onblur="enableGrantAddButton();"/>
							</td>
							<s:set name="nihInstituteCodes" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getNihInstitutes()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" 
							         name="trialFundingDTO.nihInstitutionCode" 
							         list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"
                                     id="nihInstitutionCode"
							         value="trialFundingDTO.nihInstitutionCode" 
							         cssStyle="width:150px" onblur="enableGrantAddButton();" />
						             <span class="formErrorMsg" >
				                        <s:fielderror>
				                        <s:param>trialFundingDTO.nihInstitutionCode</s:param>
				                       </s:fielderror>                            
				                     </span>
							</td>
							<td>
                                <s:textfield name="trialFundingDTO.serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px" onblur="enableGrantAddButton();" />
	                            <span class="formErrorMsg"> 
	                                <s:fielderror>
	                                <s:param>trialFundingDTO.serialNumber</s:param>
	                                </s:fielderror>                            
	                            </span>
                            </td>
							<s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
							<td>                                             
							    <s:select headerKey="" headerValue="--Select--" name="trialFundingDTO.nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="trialFundingDTO.nciDivisionProgramCode" cssStyle="width:150px" onblur="enableGrantAddButton();"/>
		                        <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>trialFundingDTO.nciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
							</td>
							<td> <input type="button" id="grantbtnid" value="Add Grant.." onclick="addGrant();" disabled="disabled"/></td>
							<td> &nbsp;</td><td> &nbsp;</td><td> &nbsp;</td>
					  </tr>
					  </tbody>
					</table>
                </td>
          </tr>
          </table>
         
          <tr>
                <td colspan="2" class="space">       <div id="grantdiv">   
                <%@ include file="/WEB-INF/jsp/nodecorate/addGrant.jsp" %>
                </div>
                </td>
                
          </tr>
          

	
       
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <table class="form">
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.statusDates"/></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_overallStatusWebDTO_statusCode"> <fmt:message key="submit.trial.currentTrialStatus"/><span class="required">*</span></label>
                </td>
                    <s:set name="statusCodeValues" value="@gov.nih.nci.registry.enums.TrialStatusCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.statusCode" list="#statusCodeValues"  value="trialDTO.statusCode" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.statusCode</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
          </tr>
        <tr>
            <td scope="row" class="label">
                <label for="submitTrial_overallStatusWebDTO_reason"> <fmt:message key="submit.trial.trialStatusReason"/></label>                
            </td>
            <td>
                <s:textarea name="trialDTO.reason"  cols="50" rows="2" />
                <span class="info">Required for Administratively Complete and Temporarily Closed statuses only</span>
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.reason</s:param>
                   </s:fielderror>                            
                 </span>                 
            </td> 
        </tr>
        <tr>
            <td scope="row" class="label"><label for="submitTrial_overallStatusWebDTO_statusDate"><fmt:message
                key="submit.trial.currentTrialStatusDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="trialDTO.statusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                            <s:param>trialDTO.statusDate</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
        </tr>
        <s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />
        <tr>
            <td scope="row" class="label"><label for="submitTrial_protocolWebDTO_startDate"><fmt:message
                key="submit.trial.trialStartDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="trialDTO.startDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="trialDTO.startDateType" list="#dateTypeList" />
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>trialDTO.startDate</s:param>
                   </s:fielderror>                            
                </span>
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>trialDTO.startDateType</s:param>
                   </s:fielderror>                            
                </span>
            </td>

        </tr>
        <tr>
            <td scope="row" class="label"><label for="submitTrial_protocolWebDTO_completionDate">
            <fmt:message key="submit.trial.primaryCompletionDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="trialDTO.completionDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> 
                <s:radio name="trialDTO.completionDateType" list="#dateTypeList" />
                <span class="formErrorMsg"> 
                   <s:fielderror>
                   <s:param>trialDTO.completionDate</s:param>
                   </s:fielderror>                            
                </span>
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>trialDTO.completionDateType</s:param>
                   </s:fielderror>                            
                </span>
            </td>

        </tr>
       </table> 
        
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
          
        <table class="data2">
        <tr>
              <th colspan="2">FDA IND/IDE Information for applicable trials</th>
        </tr>     
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
               <fmt:message key="submit.trial.indInstructionalText"/>
            </td>
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
        </table>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
  		<c:if test="${requestScope.protocolDocument != null}">
		<div class="box">		
			<h3>Exiting Trial Related Documents</h3>  
			<jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>
		</div>
		</c:if>
        <div class="box">       
            <h3>Amendment Related Documents</h3>  
            <table class="form">  
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
              <label for="submitTrial_protocolDoc">
                     <fmt:message key="amend.trial.protocolDocument"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
               <s:file name="protocolDoc" id="protocolDoc" value="true" cssStyle="width:270px"/>
               <c:if test="${requestScope.amendProtocolDoc != null}">
               <label for="currentDocument">
                    <fmt:message key="label.currentDocument"/>
                    <c:out value="${requestScope.amendProtocolDoc}"></c:out>
               </label>
               </c:if>  
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.protocolDocFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="amend.trial.changeMemo"/>
              <span class="required">*</span>
              </label>
              
             </td>
             <td class="value">
                 <s:file name="changeMemoDoc" id="changeMemoDoc" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.changeMemoDocFileName</s:param>
                   </s:fielderror>                            
                 </span>                 
               </td>         
         </tr> 
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="amend.trial.protocolHighlight"/></label>
             </td>
             <td class="value">
                 <s:file name="protocolHighlight" id="protocolHighlight" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.otherDocumentFileName</s:param>
                   </s:fielderror>                            
                 </span>                 
               </td>         
         </tr> 
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_irbApproval">
                     <fmt:message key="submit.trial.irbApproval"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
                 <s:file name="irbApproval" id="irbApproval" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.irbApprovalFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>         
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_participatingSites"><fmt:message key="submit.trial.participatingSites"/></label>
             </td>
             <td class="value">
                 <s:file name="participatingSites" id="participatingSites" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.participatingSitesFileName</s:param>
                   </s:fielderror>                            
                 </span>                 
               </td>         
         </tr>         
         
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_informedConsentDocument"><fmt:message key="submit.trial.informedConsent"/></label>
             </td>
             <td class="value">
                 <s:file name="informedConsentDocument" id="informedConsentDocument" cssStyle="width:270px"/>
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.informedConsentDocumentFileName</s:param>
                   </s:fielderror>                            
                 </span>             
               </td>         
         </tr>         
        </table>

        </div>
        </table>
        <p align="center" class="info">
           Please verify ALL the trial information you provided on this screen before clicking the &#34;Submit Trial&#34; button below.  
           <br>Once you submit the trial you will not be able to modify the information.
        </p>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                        <li>
                        <li>            
                            <s:a href="#" cssClass="btn" onclick="reviewProtocol()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="save">Cancel Trial</span></span></s:a>
                        </li>
                </ul>   
            </del>
        </div>
      <input type="hidden" name="uuidhidden"/>  
   </s:form>

 </div> 
</body>
</html>
