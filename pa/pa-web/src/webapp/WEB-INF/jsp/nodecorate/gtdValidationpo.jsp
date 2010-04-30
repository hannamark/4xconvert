<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
<script language="JavaScript">

function lookup4loadresponsibleparty1(){
   try{     
        var orgid = document.getElementById('trialValidationquery_gtdDTO_sponsorIdentifier').value;
    } catch(err) {
        orgid = document.getElementById('generalTrialDesignquery_gtdDTO_sponsorIdentifier').value;  
    }
    showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
}
function lookup4loadleadorg(){
	showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Organization');
}
function lookup4loadleadpers(){
	showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
	showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadSummary4Sponsor(){
	showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
}   
function loadLeadOrgDiv() { 
    var url = 'ajaxTrialValidationdisplayLeadOrganization.action?orgId='+orgid;
    var div = document.getElementById('loadOrgField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
    callAjax(url, div); 
}
function loadLeadPersDiv() {    
    var url = 'ajaxTrialValidationdisplayLeadPrincipalInvestigator.action?persId='+persid;
    var div = document.getElementById('loadPersField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
    callAjax(url, div);    
}
function loadSponsorDiv() {     
    var url = 'ajaxTrialValidationdisplaySelectedSponsor.action?orgId='+orgid;
    document.getElementById('gtdDTO.responsiblePersonIdentifier').value = '';
    document.getElementById('gtdDTO.responsibleGenericContactName').value = '';//unset the responsible personname
    document.getElementById("gtdDTO.contactEmail").value = '';//unset the responsible personname
    document.getElementById("gtdDTO.contactPhone").value = ''; //unset the responsible personname   
    document.getElementById('gtdDTO.responsiblePersonName').value = ''; // unset the responsible personname
        
    var div = document.getElementById('loadSponsorField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Sponsor...</div>';
    callAjax(url, div);
}
function callAjax(url, div){
    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
    return false;
}
function createOrgContactDiv() {
    var url = 'ajaxTrialValidationcreateOrganizationContacts.action?persId='+persid+'&orgId='+orgid;
    document.getElementById('gtdDTO.responsibleGenericContactName').value = '';
    document.getElementById('gtdDTO.responsiblePersonIdentifier').value = persid;
    var div = document.getElementById('loadResponsibleContactField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding responsible contact...</div>';
    callAjax(url, div);
}
function createOrgGenericContactDiv() {
    document.getElementById('gtdDTO.responsiblePersonIdentifier').value = persid;
    document.getElementById('gtdDTO.responsibleGenericContactName').value = selectedName;
    document.getElementById("gtdDTO.contactEmail").value = contactMail;
    document.getElementById("gtdDTO.contactPhone").value = contactPhone;    
    document.getElementById('gtdDTO.responsiblePersonName').value = ''; // unset the responsible personname
}
function lookup4loadresponsiblepartygenericcontact(){
    var orgid = document.getElementById('sponsorIdentifier').value;
    showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Responsible Party', createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
}
function manageRespPartyLookUp(){
    if(document.getElementById('gtdDTO.responsiblePartyTypepi').checked==true) {
        document.getElementById('rpcid').style.display='none';
        document.getElementById('rpgcid').style.display='none';
        document.getElementById('gtdDTO.responsiblePersonName').value = '';
        document.getElementById('gtdDTO.responsibleGenericContactName').value = '';
        document.getElementById('gtdDTO.responsiblePersonIdentifier').value  = '';
    }
    if(document.getElementById('gtdDTO.responsiblePartyTypesponsor').checked==true) {             
                document.getElementById('rpcid').style.display='';
                document.getElementById('rpgcid').style.display='';
    }

}
function toggledisplayDivs(val) {
      var vis = val.value;
        if (vis == 'false') { 
            document.getElementById('sponsorRespDiv').style.display = 'none'; 
        } else { 
            document.getElementById('sponsorRespDiv').style.display = ''; 
        }
}


</script> 
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    <s:hidden name="gtdDTO.responsiblePersonIdentifier" id="gtdDTO.responsiblePersonIdentifier"/>
    <s:hidden name="gtdDTO.proprietarytrialindicator" id="gtdDTO.proprietarytrialindicator"/>
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    Lead Organization
                 <span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/leadOrg.jsp" %>
            </div>      
        </td>
    </tr>
    
    <c:if test="${(sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false')}">
     <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    Principal Investigator
                 <span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadPersField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displayLeadPrincipalInvestigator.jsp" %>
            </div>      
        </td>
    </tr>
       <tr> <td colspan="2" class="space">  
           <s:if test="%{gtdDTO.ctGovXmlRequired == true}">
            <div id="sponsorRespDiv" style="display:''">
                 <%@ include file="/WEB-INF/jsp/nodecorate/sponsorRespPartyInfo.jsp" %>
            </div>
            </s:if>
            <s:else>
              <div id="sponsorRespDiv" style="display:none">
                 <%@ include file="/WEB-INF/jsp/nodecorate/sponsorRespPartyInfo.jsp" %>
            </div>
            </s:else>
           </td> 
         </tr>  
   </c:if>