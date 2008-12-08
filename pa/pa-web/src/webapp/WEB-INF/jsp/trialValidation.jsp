<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
 <title><fmt:message key="trialValidation.page.title"/></title>   
 <s:head/>
 <!-- po integration -->
 <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
 <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
 <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
 <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
 <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
 <!-- /po integration -->    
 <script type="text/javascript"> 
    var orgid;
    var persid;
    function handleAction(){
        document.forms[0].action="trialValidationupdate.action";
        document.forms[0].submit(); 
    }    
    function handleActionAccept(){
        document.forms[0].action="trialValidationaccept.action";
        document.forms[0].submit(); 
    }    
    function handleActionReject(){
        document.forms[0].action="trialValidationreject.action";
        document.forms[0].submit(); 
    }    

    function setorgid(orgIdentifier){
        orgid = orgIdentifier;
    }
    function setpersid(persIdentifier){
        persid = persIdentifier;
    }
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn"); 
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
	function lookup4loadresponsibleparty(){
	    showPopWin('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, 1050, 400, createOrgContactDiv, 'Select Responsible contact');
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
	function loadSummary4SponsorDiv() {
	    var url = 'ajaxTrialValidationdisplaySummary4FundingSponsor.action?orgId='+orgid;
	    var div = document.getElementById('loadSummary4FundingSponsorField');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Summary 4 Sponsor...</div>';
	    callAjax(url, div);
	}
	function createOrgContactDiv() {    
	    var url = 'ajaxTrialValidationcreateOrganizationContacts.action?persId='+persid+'&orgId='+orgid;
	    var div = document.getElementById('loadResponsibleContactField');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding primary contact...</div>';
	    callAjax(url, div);
	   
	}
    function loadDiv(orgid){
    }
    function loadPersDiv(persid, func) {
    }
    function callAjax(url, div){
	    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
	    return false;
	}
</script>
    
</head>
<body onload="setFocusToFirstControl();">
    <h1><fmt:message key="trialValidation.title" /></h1>
    <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
    <div class="box" >
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form >
    <s:actionerror/> 
     <h2>Trial Details</h2>
    <table class="form">
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.localProtocolIdentifier" cssStyle="width:206px" /> 
        </td>
    </tr>
    <tr>
       <td scope="row" class="label">
          <label for="officialTitle">
              <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                   <fmt:message key="studyProtocol.officialTitle"/>
              </dfn><span class="required">*</span>
          </label>
       </td>
       <td class="value">
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:406px" rows="5"/> 
       </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
             <fmt:message key="studyProtocol.studyPhase"/></dfn><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
            <s:select headerKey="" headerValue="" name="gtdDTO.phaseCode" list="#phaseCodeValues" 
                value="gtdDTO.phaseCode" cssStyle="width:120px" />
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
        <td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.phase.comment"/></dfn></label></td>
        <td>
            <s:textarea name="gtdDTO.phaseOtherText" rows="2" cssStyle="width:300px" />
        </td>
    </tr>    
    <tr>
        <td  scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.primary.purpose"/></dfn><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="gtdDTO.primaryPurposeCode" cssStyle="width:150px" onchange="activate()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="primaryPurposeOtherText">
        <td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.primary.purpose.other"/></dfn></label></td>
        <td>
            <s:textarea name="gtdDTO.primaryPurposeOtherText" cssStyle="width:300px" rows="2"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeOtherText</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    Lead Organization
                 </dfn><span class="required">*</span>
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
            <input type="radio" name="gtdDTO.responsiblePartyType" value="pi"  onclick="manageRespPartyLookUp();"> PI 
            <input type="radio" name="gtdDTO.responsiblePartyType" value="sponsor" onclick="manageRespPartyLookUp();"> Sponsor
        </td>
        </tr>               
        <tr id="rpcid" >
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
    
    <tr>
        <th colspan="2"> Summary 4 Information</th>
    </tr>

     <tr>
          <td scope="row" class="label">
               <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></dfn></label>
          </td>
          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
          <td class="value">
            <s:select headerKey="" headerValue="" 
                name="gtdDTO.summaryFourFundingCategoryCode" 
                list="#summaryFourFundingCategoryCodeValues"  
                value="gtdDTO.summaryFourFundingCategoryCode" 
                cssStyle="width:206px" />
          </td>                                 
     </tr>    
    <tr>
        <td scope="row" class="label">
            <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> Summary 4 Funding Source:</dfn></label>
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
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                    <li><a href="#" class="btn" onclick="handleActionAccept();"><span class="btn_img"><span class="save">Accept</span></span></a></li>
                    <li><a href="#" class="btn" onclick="handleActionReject();"><span class="btn_img"><span class="save">Reject</span></span></a></li>
                </ul>   
            </del>

        </div>          

    </s:form>
   </div>

 </body>
 </html>