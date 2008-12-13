<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>

<script language="JavaScript">

function lookup4loadresponsibleparty(){
   try{		
		var orgid = document.getElementById('trialValidationquery_gtdDTO_sponsorIdentifier').value;
	} catch(err) {
		orgid = document.getElementById('generalTrialDesignquery_gtdDTO_sponsorIdentifier').value;	
	}
	showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 1050, 400, createOrgContactDiv, 'Select Responsible contact ddd');
}
function lookup4loadleadorg(){
       showPopWin('${lookupOrgUrl}', 1050, 400, loadLeadOrgDiv, 'Select Organization');
}
function lookup4loadleadpers(){
    showPopWin('${lookupPersUrl}', 1050, 400, loadLeadPersDiv, 'Select Principal Investigator');
}
function lookup4sponsor(){
    showPopWin('${lookupOrgUrl}', 1050, 400, loadSponsorDiv, 'Select Sponsor');
} 
function lookup4loadSummary4Sponsor(){
    showPopWin('${lookupOrgUrl}', 1050, 400, loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
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
    var div = document.getElementById('loadResponsibleContactField');   
    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding responsible contact...</div>';
    callAjax(url, div);
}

</script> 
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    
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
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    Principal Investigator
                 </dfn><span class="required">*</span>
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
        	<s:radio name="gtdDTO.responsiblePartyType" list="#{'pi':'PI', 'sponsor':'Sponsor'}" onclick="manageRespPartyLookUp();"/>
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
                <s:textfield name="gtdDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px" />
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
                <s:textfield name="gtdDTO.contactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>contactPhone</s:param>
                   </s:fielderror>                            
                 </span>
            </td>           
        </tr>             
