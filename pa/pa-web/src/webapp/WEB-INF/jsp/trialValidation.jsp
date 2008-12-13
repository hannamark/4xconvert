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
 <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
 
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
	function loadSummary4SponsorDiv() {
	    var url = 'ajaxTrialValidationdisplaySummary4FundingSponsor.action?orgId='+orgid;
	    var div = document.getElementById('loadSummary4FundingSponsorField');   
	    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Summary 4 Sponsor...</div>';
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
	function showAlert() {
	var input="gtdDTO.phaseCode";
  	var inputElement = document.forms[0].elements[input];
	
   		if (inputElement.options[inputElement.selectedIndex].value == "Other")
		{
			alert("Please select a different Trial Phase");
		}
	}

	function manageRespPartyLookUp(){
	
	if(document.getElementById('trialValidationquery_gtdDTO_responsiblePartyTypepi').checked==true) {							
			document.getElementById('rpcid').style.display='none';
	}
	if(document.getElementById('trialValidationquery_gtdDTO_responsiblePartyTypesponsor').checked==true) {	
			document.getElementById('rpcid').style.display='';
	}
}
</script>
    
</head>
<body onload="manageRespPartyLookUp();">
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
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.LocalProtocolIdentifier</s:param>
             </s:fielderror>                            
          </span>
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
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.OfficialTitle</s:param>
             </s:fielderror>                            
          </span>
       </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
             <fmt:message key="studyProtocol.studyPhase"/></dfn><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
            <s:select headerKey="" headerValue="" name="gtdDTO.phaseCode" list="#phaseCodeValues" 
                value="gtdDTO.phaseCode" cssStyle="width:120px" onchange="showAlert()"/>
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
            <s:textarea name="gtdDTO.phaseOtherText" rows="2" cssStyle="width:300px" readonly="true"/>
        </td>
    </tr>    
    <tr>
        <td  scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.primary.purpose"/></dfn><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="gtdDTO.primaryPurposeCode" cssStyle="width:150px" />
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
	<%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>    
    <tr>
        <th colspan="2"> Summary 4 Information 5</th>
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
                    <s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Submitted'}">
                    <li><a href="#" class="btn" onclick="handleActionAccept();"><span class="btn_img"><span class="save">Accept</span></span></a></li>
                    <li><a href="#" class="btn" onclick="handleActionReject();"><span class="btn_img"><span class="save">Reject</span></span></a></li>
                    </s:if>
                </ul>   
            </del>

        </div>          

    </s:form>
   </div>

 </body>
 </html>