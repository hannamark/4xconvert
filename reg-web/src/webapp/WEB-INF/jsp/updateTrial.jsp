<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="update.trial.page.title"/></title>   
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
        addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "updateTrial");
        addCalendar("Cal2", "Select Date", "trialDTO.startDate", "updateTrial");
        addCalendar("Cal3", "Select Date", "trialDTO.completionDate", "updateTrial");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocol"/>
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
    chosenname = oname;
}
function setpersid(persIdentifier, sname,email,phone){
    persid = persIdentifier;
    chosenname = sname;
    contactMail = email;
    contactPhone = phone;
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
function lookup4loadresponsiblepartygenericcontact(){ 
   var orgid = document.getElementById('trialDTO.sponsorIdentifier').value;
   showPopWin('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid, 900, 400, createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
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

function reviewProtocolUpdate(){
    var action = "updateTrialreviewUpdate.action"; 
    document.forms[0].page.value = "save";
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
}
function cancelProtocol (){   
    var action = "updateTrialcancel.action";   
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
        document.getElementById('rpgcid').style.display='none';
        document.getElementById('trialDTO.responsiblePersonName').value = '';
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
    var  url = '/registry/protected/ajaxManageGrantsActionaddGrantForUpdate.action?fundingMechanismCode='+fundingMechanismCode+'&nihInstitutionCode='+nihInstitutionCode+'&serialNumber='+serialNumber+'&nciDivisionProgramCode='+nciDivisionProgramCode;    
    var div = document.getElementById('grantadddiv');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
    callAjax(url, div);
    resetGrantRow();
}
function deleteGrantRow(rowid){ 
    var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrantForUpdate.action?uuid='+rowid;
    var div = document.getElementById('grantadddiv');
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
    
    var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIdeForUpdate.action?uuid='+rowid;
    var div = document.getElementById('indideadddiv');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
    callAjax(url, div);             
}
function addIndIde(indIde,number,grantor,holdertype,programcode,expandedaccess,expandedaccesstype) {
    var studyid = document.getElementById('trialDTO.studyProtocolId').value;
    var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicatorForUpdate.action?indIde='+indIde+'&number='+number+'&grantor='+grantor+'&holdertype='+holdertype+'&programcode='+programcode+'&expandedaccess='+expandedaccess+'&expandedaccesstype='+expandedaccesstype+'&studyid='+studyid;
    var div = document.getElementById('indideadddiv');
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
    callAjax(url, div);
    resetValues();

}

function checkFDADropDown(){
        if (document.getElementById('fdaindid').value == '' | document.getElementById('fdaindid').value == 'false'){            
            input_box=confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
            if (input_box==true){
                document.getElementById('sec801id').value ='';
                document.getElementById('delpostindid').value ='';
                hideRow(document.getElementById('sec801row'));
            } else {
                document.getElementById('fdaindid').value = 'true';
            }
        } else {
            showRow(document.getElementById('sec801row'));
        }
    }

    function checkSection108DropDown(){
        if (document.getElementById('sec801id').value == '' | document.getElementById('sec801id').value == 'false') {   
            input_box=confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
            if (input_box==true){
                hideRow(document.getElementById('delpostindrow'));
                document.getElementById('delpostindid').value ='';
            } else {
                document.getElementById('sec801id').value = 'true';
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
        function loadDiv() {   
        var url = '/registry/protected/ajaxgetAuthOrgsgetRegAuthoritiesList.action?countryid='+document.getElementById('countries').value;
        var div = document.getElementById('loadAuthField');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';         
        var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
        return false;
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
    <h1><fmt:message key="update.trial.page.header"/></h1>
    <c:set var="topic" scope="request" value="update_trial"/> 
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="updateTrial" id="updateTrial" method="POST" validate="false" enctype="multipart/form-data">
        <s:if test="hasActionErrors()">
            <div class="error_msg"><s:actionerror/></div>
        </s:if>
        <s:else>
            <s:actionerror/>           
        </s:else>
   <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
   <s:hidden name="trialDTO.phaseCode" id="trialDTO.phaseCode"/>
   <s:hidden name="trialDTO.officialTitle" id="trialDTO.officialTitle"/>        
    <s:hidden name="trialDTO.trialType" id="trialDTO.trialType"/>
    <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/> 
    <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
    <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
    <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
    <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
    <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
    <s:hidden name="trialDTO.localProtocolIdentifier" id="trialDTO.localProtocolIdentifier"/>
    
        <s:hidden name="page" />
        <s:hidden name="uuidhidden"/>  
    <p>Update trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
    <table class="form">
          <tr>
                <th colspan="2"><fmt:message key="update.trial.trialDetails"/></th>
          </tr>
          <tr> 
            <td colspan="2" class="space">&nbsp;</td>
          </tr>
           <tr><td>
          <table>  
          <tr>     
            <td scope="row" class="label">
                <label for="Identifier">
                    <fmt:message key="view.trial.identifier"/>                
                </label>
          </td>
          <td class="value">
            <s:property value="trialDTO.assignedIdentifier"/> 
          </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="updateTrial_nctNumber"> <fmt:message key="update.trial.nctNumber"/></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>                
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="updateTrial_trialPurpose"><fmt:message key="update.trial.purpose"/><span class="required">*</span></label> 
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
                    <label for="updateTrial_otherPurposeText"> <fmt:message key="update.trial.otherPurposeText"/></label>

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
          </table></td></tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr> 
       
          <tr>
             <th colspan="2"><fmt:message key="update.trial.sponsorResParty"/></th>
          </tr>          
          <tr>
           <td colspan="2" class="space">&nbsp;</td>
          </tr>
           <tr><td>
          <table> 
          <tr>
                <td scope="row" class="label">
                    <label for="updateTrial_resppartysponsor"> <fmt:message key="update.trial.responsibleParty"/><span class="required">*</span></label>
                </td>
                <td>
                <s:radio name="trialDTO.responsiblePartyType" id="trialDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
                </td>
         </tr>
         <s:if test="trialDTO.responsiblePartyType == 'sponsor'">
          <tr id="rpcid" >
              <td scope="row" class="label">
                          <label for="updateTrial_resPartyContactFullName"> <fmt:message key="update.trial.responsiblePartyContact"/></label> 
              </td>                                        
              <td class="value">
              <div id="loadResponsibleContactField">
                   <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
              </div>                                                                                             
              </td>
           </tr>
           <tr id="rpgcid">
                    <td scope="row" class="label">
                        <label for="updateTrial_resPartyGenericContact"><fmt:message key="update.trial.responsiblePartyGenericContact"/></label> 
                    </td>
                    <td class="value">
                        <div id="loadResponsiblePartyGenericContactField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                        </div>      
                    </td>
          </tr>   
          </s:if>
          <s:else>
          <tr id="rpcid" style="display:none">
                     <td scope="row" class="label">
                                 <label for="updateTrial_resPartyContactFullName"> <fmt:message key="update.trial.responsiblePartyContact"/></label> 
                     </td>                                        
                     <td class="value">
                               <div id="loadResponsibleContactField">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialresponsibleContact.jsp" %>
                               </div>                                                                                             
                     </td>
            </tr>
            <tr id="rpgcid"  style="display:none">
                    <td scope="row" class="label">
                        <label for="updateTrial_resPartyGenericContact"><fmt:message key="update.trial.responsiblePartyGenericContact"/></label> 
                    </td>
                    <td class="value">
                        <div id="loadResponsiblePartyGenericContactField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                        </div>      
                    </td>
           </tr>   
          </s:else>                                                
         <tr>
          <td colspan="2">
           <p><b><I>Please provide professional contact information only.</I></b></p>
          </td>
         </tr>
         <tr>
                <td scope="row" class="label">
                   <label for="updateTrial_contactEmail"> <fmt:message key="update.trial.responsiblePartyEmail"/><span class="required">*</span></label> 
                </td>
                <td class="value">
                    <s:textfield id="trialDTO.contactEmail" name="trialDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px"/>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.contactEmail</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                 <label for="updateTrial_contactPhone"> <fmt:message key="update.trial.responsiblePartyPhone"/><span class="required">*</span></label>
                </td>
                <td class="value">
                    <s:textfield name="trialDTO.contactPhone"  id="trialDTO.contactPhone" maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.contactPhone</s:param>
                       </s:fielderror>                            
                     </span>
                </td>           
          </tr>             
          <tr>
           <td colspan="2">
             <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
           </td>
          </tr>
          </table>
          </td></tr>
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
          <tr><td>
          <table>    
          <tr>  
                <td scope="row" class="label">
                    <label for="updateTrial_summary4FundingCategory">Summary 4 Funding Sponsor Type:</label> 
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
                        <label for="updateTrial_selectedSummary4Sponsor_name_part_0__value"> Summary 4 Funding Sponsor: </label> 
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
           </table>
           </td></tr> 
          <tr>  <td colspan="2" class="space">&nbsp;</td></tr>
          <tr><td>
          <table class="data2">
          <tr>
                <th colspan="2"><fmt:message key="update.trial.grantInfo"/></th> 
          </tr>
           <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="2">
               <fmt:message key="update.trial.grantInstructionalText"/>
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
                            <th><fmt:message key="update.trial.fundingMechanism"/></th>
                            <th><fmt:message key="update.trial.instituteCode"/></th>
                            <th><fmt:message key="update.trial.serialNumber"/></th>
                            <th><fmt:message key="update.trial.divProgram"/></th>
                            <th></th>
                    </tr>
                       
                       <tr>
                            <s:set name="fundingMechanismValues" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getFundingMechanisms()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="fundingMechanismCode" 
                                     list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     id="fundingMechanismCode"
                                     value="fundingMechanismCode" 
                                     cssStyle="width:150px" />
                            </td>
                            <s:set name="nihInstituteCodes" value="@gov.nih.nci.registry.util.RegistryServiceLocator@getLookUpTableService().getNihInstitutes()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="nihInstitutionCode" 
                                     list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"
                                     id="nihInstitutionCode"
                                     value="nihInstitutionCode" 
                                     cssStyle="width:150px"  />
                                     <span class="formErrorMsg" >
                                        <s:fielderror>
                                        <s:param>nihInstitutionCode</s:param>
                                       </s:fielderror>                            
                                     </span>
                            </td>
                            <td>
                                <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                <span class="formErrorMsg"> 
                                    <s:fielderror>
                                    <s:param>serialNumber</s:param>
                                    </s:fielderror>                            
                                </span>
                            </td>
                            <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="nciDivisionProgramCode" cssStyle="width:150px" />
                                <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>nciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
                            </td>
                            <td> <input type="button" id="grantbtnid" value="Add Grant" onclick="addGrant();" /></td>
                            
                      </tr>
                      </tbody>
                    </table>
                </td>
          </tr>
          <tr>
                <td colspan="2" class="space">  
                <div id="grantdiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialViewGrant.jsp" %>
                </div>
                </td>
                
          </tr>
         <tr>
                <td colspan="2" class="space">  
                <div id="grantadddiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/addGrantForUpdate.jsp" %>
                </div>
                </td>
                
          </tr>
          </table></td></tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr><td>
          <table class="form">
          <tr>
                <th colspan="2"><fmt:message key="update.trial.statusDates"/></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          
          <tr>
                <td scope="row" class="label">
                    <label for="updateTrial_statusCode"> <fmt:message key="update.trial.currentTrialStatus"/><span class="required">*</span></label>
                </td>
                    <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
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
                <label for="updateTrial_reason"> <fmt:message key="update.trial.trialStatusReason"/></label>                
            </td>
            <td>
                <s:textarea name="trialDTO.reason"  cols="50" rows="2" />
                <span class="info">Required for Administratively Complete ,Withdrawn and Temporarily Closed statuses only</span>
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.reason</s:param>
                   </s:fielderror>                            
                 </span>                 
            </td> 
        </tr>
        <tr>
            <td scope="row" class="label"><label for="updateTrial_statusDate"><fmt:message
                key="update.trial.currentTrialStatusDate" /><span class="required">*</span></label></td>
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
            <td scope="row" class="label"><label for="updateTrial_startDate"><fmt:message
                key="update.trial.trialStartDate" /><span class="required">*</span></label></td>
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
            <td scope="row" class="label"><label for="updateTrial_completionDate">
            <fmt:message key="update.trial.primaryCompletionDate" /><span class="required">*</span></label></td>
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
        </td></tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
        <tr><td>  
        <table class="data2">
        <tr>
              <th colspan="2">FDA IND/IDE Information for applicable trials</th>
        </tr>     
        <s:hidden name="indIdeUpdateDtosLen" id="indIdeUpdateDtosLen"/>
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
         </tr>
          <tr>
            <td colspan="2">
               <fmt:message key="update.trial.indInstructionalText"/>
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
                                    
                        <%@ include file="/WEB-INF/jsp/nodecorate/updateIdeIndIndicator.jsp" %>
                
            </div>
            </td>
        </tr>
         <tr>    
            <td colspan="2" class="space">
            <div id="indideadddiv">         
                                    
                        <%@ include file="/WEB-INF/jsp/nodecorate/addForUpdateIdeIndIndicator.jsp" %>
                
            </div>
            </td>
        </tr>
        </table>
        </td></tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
        <tr><td>  
        <table class="data2">
        <tr>
              <th colspan="2">Regulatory Information</th>
          </tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
         <!--  Trial Oversight Authority Country -->
        <tr>
        <td scope="row" class="label">
        <fmt:message key="regulatory.oversight.country.name"/><span class="required">*</span></td>
          <td class="value"><s:select id="countries" headerValue="-Select-" headerKey=""
                      name="lst"                   
                      list="countryList"  
                      listKey="id" listValue="name"                    
                      onchange="loadDiv();"  
                      />
                    <span class="formErrorMsg"> 
                       <s:fielderror>
                       <s:param>lst</s:param>
                      </s:fielderror>                            
                    </span>
         </td>
       </tr>
       <!--  Trial Oversignt Authority Organization Name -->
     <tr>
         <td scope="row" class="label">
           <fmt:message key="regulatory.oversight.auth.name"/><span class="required">*</span></td>
                     <td class="value">
                         <div id="loadAuthField">
                         <%@ include file="/WEB-INF/jsp/nodecorate/authorityname.jsp" %>
                         </div>
                                                         
                     </td>
                    
     </tr>   
     <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
         <fmt:message key="regulatory.FDA.regulated.interv.ind"/><span class="required">*</span> </td>
         <td class="value"><s:select  id="fdaindid" name="regulatoryAuthority.fdaRegulatedInterventionIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkFDADropDown();" value="regulatoryAuthority.fdaRegulatedInterventionIndicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>regulatoryAuthority.fdaRegulatedInterventionIndicator</s:param></s:fielderror></span>
         </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label"><fmt:message key="regulatory.section801.ind"/><span class="required">*</span></td>
         <td class="value"><s:select id="sec801id" name="regulatoryAuthority.section801Indicator" list="#{'':'', 'false':'No', 'true':'Yes'}" onchange="checkSection108DropDown();" value="regulatoryAuthority.section801Indicator"/>
         <span class="formErrorMsg"><s:fielderror><s:param>regulatoryAuthority.section801Indicator</s:param></s:fielderror></span>
         </td>
     </tr>
     
     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label"><fmt:message key="regulatory.delayed.posting.ind"/><span class="required">*</span></td>
         <td class="value"><s:select id="delpostindid" name="regulatoryAuthority.delayedPostingIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" value="regulatoryAuthority.delayedPostingIndicator" />
         <span class="formErrorMsg"><s:fielderror><s:param>regulatoryAuthority.delayedPostingIndicator</s:param></s:fielderror></span>
         </td>       
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
         <td class="value"><s:select id="datamonid" name="regulatoryAuthority.dataMonitoringIndicator" list="#{'':'', 'false':'No', 'true':'Yes'}" value="regulatoryAuthority.dataMonitoringIndicator" />      
         </td>       
     </tr>
     </table>
      </td></tr>
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
        <tr><td>
       <s:if test="participatingSites.size > 0" >
       <table class="data2">
         <tr>
              <th colspan="2">Participating Sites</th>
         </tr>
         <tr> <td>
         <table class="form">
         <tbody> 
                    <tr>
                        <th>Site</th>
                        <th>Recruitment Status</th>
                        <th>Date</th>
                        <th>Program Code</th>
                        
                    </tr>
                                                                                                         
                    <s:iterator id="participatingSites" value="participatingSites" status="psstats">
                    <tr>
                        <td>
                            <s:textarea  name="participatingSites[%{#psstats.index}].name" value="%{name}" readonly="true" cssStyle="width:400px;border: 1px solid #FFFFFF" rows="2"/>
                        </td>
                            
                         <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                        <td class="value"><s:select headerKey="" headerValue="--Select--"
                            name="participatingSites[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                            list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                       
                      </td> 
                       <td>
                            <s:textfield  name="participatingSites[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}"/>
                           
                        </td>
                       <td>
                           <s:textfield  name="participatingSites[%{#psstats.index}].programCode" value="%{programCode}"/>
                           <s:hidden  name="participatingSites[%{#psstats.index}].id" value="%{id}"/> 
                        </td>
                    </tr>
                    </s:iterator >
                      
                </tbody>   
         </table> 
       
          <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>participatingsite.recStatus</s:param>
                              </s:fielderror>                            
                        </span>
                         <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>participatingsite.recStatusDate</s:param>
                              </s:fielderror>                            
                        </span>
         </td></tr></table> 
         </s:if>
            <tr>
              <td colspan="2" class="space">&nbsp;</td>
           </tr>
        <tr><td>  
        <s:if test="collaborators.size > 0" > 
        <table class="data2">
         <tr>
             <th colspan="2" >Collaborators</th>
          </tr>
         <tr><td>
           <table class="form">
           <tbody> 
                    <tr>
                        <th>Collaborator</th>
                        <th>Functional Role</th>
                        
                    </tr>
                                                                                  
                    <s:iterator value="collaborators" id="collaborators" status="stat" >
                    <tr>
                        <td class="tdBoxed">
                          <s:hidden  name="collaborators[%{#stat.index}].id" value="%{id}" />
                          <s:textarea  name="collaborators[%{#stat.index}].name" value="%{name}" readonly="true" cssStyle="width:400px;border: 1px solid #FFFFFF" rows="2"/>
                         </td>
                            <s:set name="functionalCodeValues"
                                value="@gov.nih.nci.pa.enums.StudySiteFunctionalCode@getCollaboratorDisplayNames()"/>
                         <td class="value" colspan="2"><s:select headerKey="" headerValue="--Select--" 
                             name="collaborators[%{#stat.index}].functionalRole" list="#functionalCodeValues" value="%{functionalRole}"/>
                             
                             
                             </td>
                     </tr>
                    </s:iterator>
                     
                </tbody>   
           
        </table>
        
        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>collaborator.functionalCode</s:param>
                              </s:fielderror>                            
                        </span>
        </td></tr></table>
       </s:if>
        <table class="data2">
         <tr>
             <th colspan="2" >IRB Approval</th>
          </tr>
           <tr>
              <td colspan="2" class="space">&nbsp;</td>
           </tr>
            <tr>
              <td scope="row" class="label">
              <label for="updateTrial_irbApproval">
                     <fmt:message key="update.trial.irbApproval"/>
              </label>
             </td>
             <td class="value">
                 <s:file name="irbApproval" id="irbApproval" cssStyle="width:270px"/>
             </td>         
         </tr>         
      </table>
      <p>
        Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.  
           
        </p>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                        <li>
                        <li>            
                            <s:a href="#" cssClass="btn" onclick="reviewProtocolUpdate()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                            <s:a href="#" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                        </li>
                </ul>   
            </del>
        </div>
   
</table>
</s:form>
 </div> 
</body>
</html>
