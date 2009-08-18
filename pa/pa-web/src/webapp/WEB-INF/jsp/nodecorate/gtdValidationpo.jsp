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
	showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 900, 400, createOrgContactDiv, 'Select Responsible contact');
}
function lookup4loadleadorg(){
       showPopWin('${lookupOrgUrl}', 900, 400, loadLeadOrgDiv, 'Select Organization');
}
function lookup4loadleadpers(){
    showPopWin('${lookupPersUrl}', 900, 400, loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
    showPopWin('${lookupOrgUrl}', 900, 400, loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadSummary4Sponsor(){
    showPopWin('${lookupOrgUrl}', 900, 400, loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
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
    showPopWin('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid, 900, 400, createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
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

</script> 
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    <s:hidden name="gtdDTO.responsiblePersonIdentifier" id="gtdDTO.responsiblePersonIdentifier"/>
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
    
    <tr>
         <th colspan="2">Sponsor/Responsible Party</th>
    </tr>          
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
        	<s:radio id="gtdDTO.responsiblePartyType"  name="gtdDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
        </td>
        </tr> 
         <c:choose>
         <c:when test="${gtdDTO.responsiblePartyType == 'sponsor'}">
            

             <tr id="rpcid" >
                <td scope="row" class="label">
                    <label for="responsiblepartycontact"> Responsible Party Personal Contact :</label> 
                </td>               
                <td class="value">
                    <div id="loadResponsibleContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
                    </div>                                    
                </td>
            </tr>
            <tr id="rpgcid">
                <td scope="row" class="label">
                    <label for="resPartyGenericContact">Responsible Party Generic Contact:</label> 
                </td>
                <td class="value">
                    <div id="loadResponsiblePartyGenericContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                    </div>      
                </td>
            </tr>   
        </c:when>
        <c:otherwise>
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
            <tr id="rpgcid" style="display:none">
                <td scope="row" class="label">
                    <label for="resPartyGenericContact">Responsible Party Generic Contact:</label> 
                </td>
                <td class="value">
                    <div id="loadResponsiblePartyGenericContactField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialResPartyGenericContact.jsp" %>
                    </div>      
                </td>
            </tr>
        </c:otherwise>
        </c:choose>
        <tr>
            <td scope="row" class="label">
               Email Address:<span class="required">*</span>
            </td>
            <td class="value">
                <s:textfield name="gtdDTO.contactEmail"  id="gtdDTO.contactEmail" maxlength="199" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.contactEmail</s:param>
                   </s:fielderror>                            
                 </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
            <td class="value">
                <s:textfield name="gtdDTO.contactPhone" id="gtdDTO.contactPhone" maxlength="199" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>gtdDTO.contactPhone</s:param>
                   </s:fielderror>                            
                 </span>
            </td>           
        </tr>             
