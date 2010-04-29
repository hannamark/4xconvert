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
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<!-- c:url value="/protected/ajaxorganizationContactsavePI.action" var="lookupOrgUrl"/-->

<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="submitProtocol"/>
<c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
<SCRIPT LANGUAGE="JavaScript">
var orgid;
var chosenname;
var persid;
var respartOrgid;
var contactMail;
var contactPhone;
function setorgid(orgIdentifier, oname){
    orgid = orgIdentifier;
    chosenname = oname.replace(/&apos;/g,"'");
}
function setpersid(persIdentifier, sname,email,phone){
    persid = persIdentifier;
    chosenname = sname.replace(/&apos;/g,"'");
    contactMail = email;
    contactPhone = phone;
}

//
function lookup4loadleadorg(){
    showPopup('${lookupOrgUrl}',loadLeadOrgDiv, 'Select Lead Organization');
}
function lookup4loadleadpers(){
    showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
    //clear info for resp party
    showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadresponsibleparty(){ 
    var orgid = document.getElementById('trialDTO.sponsorIdentifier').value;
    showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible Party Contact');
}
function lookup4loadresponsiblepartygenericcontact(){ 
    var orgid = document.getElementById('trialDTO.sponsorIdentifier').value;
    showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid, createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
}
function lookup4loadSummary4Sponsor(){
    showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
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
    document.getElementById('trialDTO.responsiblePersonIdentifier').value = '';
    document.getElementById('trialDTO.responsibleGenericContactName').value = '';//unset the responsible personname
    document.getElementById("trialDTO.contactEmail").value = '';//unset the responsible personname
    document.getElementById("trialDTO.contactPhone").value = ''; //unset the responsible personname   
    document.getElementById('trialDTO.responsiblePersonName').value = ''; // unset the responsible personname
    respartOrgid = orgid;
}
function createOrgContactDiv() {
    document.getElementById("trialDTO.responsiblePersonIdentifier").value = persid;
    document.getElementById('trialDTO.responsiblePersonName').value = chosenname;
    document.getElementById('lookupbtn4RP').disabled = "";
    document.getElementById('trialDTO.responsibleGenericContactName').value = ''; // unset the responsible generic contact
}
function createOrgGenericContactDiv() {
    document.getElementById('trialDTO.responsiblePersonIdentifier').value = persid;
    document.getElementById('trialDTO.responsibleGenericContactName').value = chosenname;
    document.getElementById("trialDTO.contactEmail").value = contactMail;
    document.getElementById("trialDTO.contactPhone").value = contactPhone;    
    document.getElementById('lookupbtn4RP').disabled = "";
    document.getElementById('trialDTO.responsiblePersonName').value = ''; // unset the responsible personname
}
function loadSummary4SponsorDiv() {
    document.getElementById("trialDTO.summaryFourOrgName").value = chosenname;
    document.getElementById('trialDTO.summaryFourOrgIdentifier').value = orgid;
}
//
function reviewProtocol (){ 
    var action = "submitTrialreview.action"; 
    document.forms[0].page.value = "review";
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
}
function partialSave() {
    var action = "submitTrialpartialSave.action"; 
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${partialSave}', 600, 200, '', 'Partial Saving Trial');
}
function cancelProtocol (){   
    var action = "submitTrialcancel.action";   
    document.forms[0].page.value = "Submit";
    document.forms[0].action=action;
    document.forms[0].submit();
}
function callAjax(url, div){
    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
    return false;
}
function manageRespPartyLookUp(){
    if(document.getElementById('trialDTO.responsiblePartyTypepi').checked==true) {
        document.getElementById('rpcid').style.display='none';
        document.getElementById('rpgcid').style.display='none';
        document.getElementById('trialDTO.responsiblePersonName').value = '';
        document.getElementById('trialDTO.responsibleGenericContactName').value = '';
        document.getElementById('trialDTO.responsiblePersonIdentifier').value  = '';
    }
    if(document.getElementById('trialDTO.responsiblePartyTypesponsor').checked==true) {             
                document.getElementById('rpcid').style.display='';
                document.getElementById('rpgcid').style.display='';
    }
}
function trim(val) {
    var ret = val.replace(/^\s+/, '');
    ret = ret.replace(/\s+$/, '');
      return ret;
}
function addGrant(){
    var fundingMechanismCode = document.getElementById('fundingMechanismCode').value;   
    var nihInstitutionCode = document.getElementById('nihInstitutionCode').value;
    var nciDivisionProgramCode = document.getElementById('nciDivisionProgramCode').value;
    var serialNumber = document.getElementById('serialNumber').value;
    serialNumber=trim(serialNumber);
    var isValidGrant;
    var isSerialEmpty=false;
    var alertMessage="";

    if(fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
        isValidGrant = false;
        alertMessage="Please select a Funding Mechanism";
    }
    if(nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
        isValidGrant = false;
        alertMessage=alertMessage+ "\n Please select an Institute Code";
    }
    if(serialNumber.length == 0 || serialNumber == null) {
        isValidGrant = false;
        isSerialEmpty = true;
        alertMessage=alertMessage+ "\n Please enter a Serial Number";
    }
    if(nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
        isValidGrant = false;
        alertMessage=alertMessage+ "\n Please select a NCI Division/Program Code";
    }
    if (isSerialEmpty == false && isNaN(serialNumber)){
        isValidGrant = false;
        alertMessage=alertMessage+ "\n Serial Number must be numeric";
    } else if (isSerialEmpty == false && serialNumber != null){
        var numericExpression = /^[0-9]+$/;
         if(!numericExpression.test(serialNumber)){
         isValidGrant = false;
         alertMessage=alertMessage+ "\n Serial Number must be numeric";
        } 
    }
    if (isSerialEmpty == false && (serialNumber.length < 5 || serialNumber.length > 6)){
        isValidGrant = false;
        alertMessage=alertMessage+ "\n Serial Number must be 5 or 6 digits";
    }
    if (isValidGrant == false) {
        alert(alertMessage);
        return false;
    }
    var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action?fundingMechanismCode='+fundingMechanismCode+'&nihInstitutionCode='+nihInstitutionCode+'&serialNumber='+serialNumber+'&nciDivisionProgramCode='+nciDivisionProgramCode;    
    var div = document.getElementById('grantdiv');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
    callAjax(url, div);
    resetGrantRow();
}
function deleteGrantRow(rowid){ 
    var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrant.action?uuid='+rowid;
    var div = document.getElementById('grantdiv');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
    callAjax(url, div);             
}

function resetGrantRow(){
    document.getElementById('fundingMechanismCode').value = '';
    document.getElementById('nihInstitutionCode').value = '';
    document.getElementById('serialNumber').value = '';
    document.getElementById('nciDivisionProgramCode').value = '';
}
function deleteIndIde(rowid){
    
    var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIde.action?uuid='+rowid;
    var div = document.getElementById('indidediv');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
    callAjax(url, div);             
}
function addIndIde(indIde,number,grantor,holdertype,programcode,expandedaccess,expandedaccesstype) {
    var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicator.action?indIde='+indIde+'&number='+number+'&grantor='+grantor+'&holdertype='+holdertype+'&programcode='+programcode+'&expandedaccess='+expandedaccess+'&expandedaccesstype='+expandedaccesstype;
    var div = document.getElementById('indidediv');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
    callAjax(url, div);
    resetValues();

}
function loadRegAuthoritiesDiv() {   
    var url = '/registry/protected/ajaxgetOAuthOrgsgetTrialOversightAuthorityOrganizationNameList.action?countryid='+document.getElementById('countries').value;
    var div = document.getElementById('loadAuthField');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';         
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: false
    });
    return false;
}
    function checkFDADropDown(){
        if (document.getElementById('trialDTO.fdaRegulatoryInformationIndicator').value == '' 
            | document.getElementById('trialDTO.fdaRegulatoryInformationIndicator').value == 'No'){            
            input_box=confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
            if (input_box==true){
                document.getElementById('trialDTO.section801Indicator').value ='';
                document.getElementById('trialDTO.delayedPostingIndicator').value ='';
                hideRow(document.getElementById('sec801row'));
                hideRow(document.getElementById('delpostindrow'));
            } else {
                document.getElementById('trialDTO.fdaRegulatoryInformationIndicator').value = 'Yes';
            }
        } else {
            showRow(document.getElementById('sec801row'));
            showRow(document.getElementById('delpostindrow'));
        }
    }
    
    function checkSection108DropDown(){
        if (document.getElementById('trialDTO.section801Indicator').value == '' 
            | document.getElementById('trialDTO.section801Indicator').value == 'No') {   
            input_box=confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
            if (input_box==true){
                hideRow(document.getElementById('delpostindrow'));
                document.getElementById('trialDTO.delayedPostingIndicator').value ='';
            } else {
                document.getElementById('trialDTO.section801Indicator').value = 'Yes';
            }
        } else {
            showRow(document.getElementById('delpostindrow'));
        }    
    }
    function hideRow(row){          
        row.style.display = 'none'; 
    }
    function showRow(row){
        row.style.display = '';
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
function toggledisplayDivs(val) {
  var vis = val.value;
  if (vis == 'false') { 
     document.getElementById('regDiv').style.display = "none"; 
     document.getElementById('sponsorDiv').style.display = "none"; 
  } else { 
     document.getElementById('regDiv').style.display = "block"; 
     document.getElementById('sponsorDiv').style.display = "block"; 
     }
}
</script>   

<body>
<!-- main content begins-->
    <h1><fmt:message key="submit.trial.page.header"/></h1>
    <c:set var="topic" scope="request" value="submit_trial"/> 
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="submitTrial" method="POST" enctype="multipart/form-data"><s:actionerror/>
        <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
        <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/> 
        <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
        <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
        <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
        <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>  
        <s:hidden name="page" />
        <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
        <table class="form"> 
        <tr>
        <td scope="row" class="label">
          <a href="http://ClinicalTrials.gov">ClinicalTrials.gov</a> XML required?  
        </td>
        <td>
          <s:radio name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
       </td>
       </tr>
       <tr><td></td></tr>
       <tr><td></td></tr>
            <%@ include file="/WEB-INF/jsp/nodecorate/trialIdentifiers.jsp" %>      
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
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
                        <s:param>trialDTO.officialTitle</s:param>
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
                        <s:param>trialDTO.phaseCode</s:param>
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
                        <s:param>trialDTO.phaseOtherText</s:param>
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
                    <input type="radio" name="trialDTO.trialType" value="Observational" disabled="disabled"> Observational
                     <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.trialType</s:param>
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
                        <s:param>trialDTO.primaryPurposeCode</s:param>
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
                        <s:param>trialDTO.primaryPurposeOtherText</s:param>
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
        
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr> <td colspan="2" class="space">  
          <s:if test="%{trialDTO.xmlRequired == true}">
           <div id="sponsorDiv" style="display:''">
             <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
            </div>
            </s:if>
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
                           <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
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
           <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <s:textfield name="trialDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg">
                    <s:fielderror>
                            <s:param>trialDTO.programCodeText</s:param>
                    </s:fielderror>                            
                 </span>
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
                            <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="trialFundingDTO.fundingMechanismCode" 
                                     list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     id="fundingMechanismCode"
                                     value="trialFundingDTO.fundingMechanismCode" 
                                     cssStyle="width:150px" />
                            </td>
                            <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="trialFundingDTO.nihInstitutionCode" 
                                     list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"
                                     id="nihInstitutionCode"
                                     value="trialFundingDTO.nihInstitutionCode" 
                                     cssStyle="width:150px"  />
                                     <span class="formErrorMsg" >
                                        <s:fielderror>
                                        <s:param>trialFundingDTO.nihInstitutionCode</s:param>
                                       </s:fielderror>                            
                                     </span>
                            </td>
                            <td>
                                <s:textfield name="trialFundingDTO.serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                <span class="formErrorMsg"> 
                                    <s:fielderror>
                                    <s:param>trialFundingDTO.serialNumber</s:param>
                                    </s:fielderror>                            
                                </span>
                            </td>
                            <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" name="trialFundingDTO.nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="trialFundingDTO.nciDivisionProgramCode" cssStyle="width:150px" />
                                <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>trialFundingDTO.nciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
                            </td>
                            <td> <input type="button" id="grantbtnid" value="Add Grant" onclick="addGrant();" /></td>
                            <td> &nbsp;</td><td> &nbsp;</td><td> &nbsp;</td>
                      </tr>
                      </tbody>
                    </table>
                </td>
          </tr>
          </table>
         
          <tr>
                <td colspan="2" class="space">  
                <div id="grantdiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
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
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
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
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
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
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)  
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
        <s:if test="%{trialDTO.xmlRequired == true}">
        <div id="regDiv" style="display:''">
        <!-- Regulatory page -->
        <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInforamtion.jsp" %>
        </div>
        </s:if>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
        </table>
        <div id="uploadDocDiv">
        <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
        </div>
        <p align="center" class="info">
           Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.  
           <br>Once you submit the trial you will not be able to modify the information.
        </p>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                        <li>            
                            <s:a href="#" cssClass="btn" onclick="partialSave()"><span class="btn_img"><span class="save">Save as Draft</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="reviewProtocol()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>
      <s:hidden name="uuidhidden"/>  
   </s:form>

 </div> 
</body>
</html>

