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
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
 <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
 <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
 <!-- /po integration -->    
 <script type="text/javascript"> 
    var orgid;
    var persid;
    var respartOrgid;
    var contactMail;
    var contactPhone;
    var selectedName;    
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();         
    }
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
    function setpersid(persIdentifier,name,email,phone){
        persid = persIdentifier;
        selectedName = name;
        contactMail = email;
        contactPhone = phone;
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
    function lookup4loadresponsibleparty(){
    	var orgid = document.getElementById('sponsorIdentifier').value;
    	showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
    }
    function lookup4loadresponsiblepartygenericcontact(){
    	var orgid = document.getElementById('sponsorIdentifier').value;
    	showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid,  createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
    }


    function deleteOtherIdentifierRow(rowid){ 
        var  url = 'ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action?uuid='+rowid;
        var div = document.getElementById('otherIdentifierdiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
        callAjax(url, div);             
    }

    function addOtherIdentifier() {
        var orgValue=document.getElementById("otherIdentifierOrg").value;
        if (orgValue != null && orgValue != '') {
            var  url = 'ajaxManageOtherIdentifiersActionaddOtherIdentifier.action?otherIdentifier='+orgValue;    
            var div = document.getElementById('otherIdentifierdiv');   
            div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
            callAjax(url, div);
            document.getElementById("otherIdentifierOrg").value="";
        } else {
            alert("Please enter a valid Other identifier.");
        }
    }

</script>
    
</head>
<body>
<c:set var="topic" scope="request" value="validate_trial"/>
    <h1><fmt:message key="trialValidation.page.title" /></h1>
    <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
    <div class="box" >
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form >
    <s:actionerror/> 
     <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
     <s:hidden name="gtdDTO.studyProtocolId" id="gtdDTO.studyProtocolId"/>
     <s:hidden name="gtdDTO.ctGovXmlRequired" id="gtdDTO.ctGovXmlRequired"/>
     <h2>Trial Details</h2>
    <table class="form">
          <tr>
            <td scope="row" class="label">
                <label for="nciAccessionNumber">                
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${sessionScope.trialSummary.nciIdentifier }"/> 
            </td>
       </tr>
        <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/><span class="required">*</span>
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.localProtocolIdentifier" cssStyle="width:206px" maxlength="50"/> 
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.LocalProtocolIdentifier</s:param>
             </s:fielderror>                            
          </span>

        </td>
    </tr>
     <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                    NCT Number
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.nctIdentifier" cssStyle="width:206px" maxlength="50"/> 
        </td>
    </tr>
    <tr>
            <td scope="row" class="label">
                <label for="leadOrg"> 
                    <fmt:message key="studyProtocol.proprietaryTrial"/>
                </label>
            </td>
            <td class="value">
                <s:property value="gtdDTO.proprietarytrialindicator"/> 
            </td>
    </tr> 
    <tr>
       <td scope="row" class="label">
          <label for="officialTitle">
                   <fmt:message key="studyProtocol.officialTitle"/>
              <span class="required">*</span>
          </label>
       </td>
       <td class="value">
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.OfficialTitle</s:param>
             </s:fielderror>                            
          </span>
             
       </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase">
            <label for="officialTitle">
             <fmt:message key="studyProtocol.studyPhase"/><span class="required">*</span></label> </td>
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
    <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
    <tr>
        <td   scope="row" class="label"><label><fmt:message key="isdesign.details.phase.comment"/></label></td>
        <td>
            <s:textarea name="gtdDTO.phaseOtherText" rows="2" cssStyle="width:300px" />
             <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.phaseOtherText</s:param>
             </s:fielderror>                            
            </span>
        </td>
    </tr>
    </c:if>    
    <tr>
        <td  scope="row" class="label"><label>
            <fmt:message key="isdesign.details.primary.purpose"/><span class="required">*</span></label></td>
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
    <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
    <tr>
        <td   scope="row" class="label"><label>
            <fmt:message key="isdesign.details.primary.purpose.other"/></label></td>
        <td>
            <s:textarea name="gtdDTO.primaryPurposeOtherText" cssStyle="width:300px" rows="2"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeOtherText</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    </c:if>

    <tr>
        <th colspan="2">Other Identifiers</th>
    </tr>
    <tr>
        <td scope="row" class="label">
            <label for="submitTrial_protocolWebDTO_otherIdentifiers">Other Identifier</label>
        </td>
        <td>
            <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" />&nbsp; 
            <input type="button" id="otherIdbtnid" value="Add Other Identifier" onclick="addOtherIdentifier();" />
        </td>
    </tr>
    <tr>
        <td colspan="2" class="space">
            <div id="otherIdentifierdiv">
                <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp"%>
            </div>
        </td>
    </tr>
    
    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>    
    <tr>
        <th colspan="2"> Summary 4 Information </th>
    </tr>

     <tr>
          <td scope="row" class="label">
               <label for="summary4TypeCode"><fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></label>
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
            <label for="summary4TypeCode">Summary 4 Funding Source:</label>
        </td>
        <td class="value">
            <div id="loadSummary4FundingSponsorField">
           <%@ include file="/WEB-INF/jsp/nodecorate/displaySummary4FundingSponsor.jsp" %>
            </div>      
        </td>
        
    </tr>
    <tr>
        <td scope="row" class="label">
        <label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
        </td>
        <td class="value">
            <s:textfield name="gtdDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>gtdDTO.programCodeText</s:param>
                </s:fielderror>                            
             </span>
        </td>
    </tr>
    <s:if test="gtdDTO.submissionNumber > 1">
    <tr>
        <th colspan="2"> Amendment Information </th>
    </tr>

     <tr>
          <td scope="row" class="label">
               <label for="amendmentReasonCode">
                <fmt:message key="studyProtocol.amendmentReasonCodeValues"/>
                <span class="required">*</span>
               </label>
          </td>
          <s:set name="amendmentReasonCodeValues" value="@gov.nih.nci.pa.enums.AmendmentReasonCode@getDisplayNames()" />
          <td class="value">
            <s:select headerKey="" headerValue="" 
                name="gtdDTO.amendmentReasonCode" 
                list="#amendmentReasonCodeValues"  
                value="gtdDTO.amendmentReasonCode" 
                cssStyle="width:206px" />
                <span class="formErrorMsg"> 
                    <s:fielderror>
                        <s:param>gtdDTO.amendmentReasonCode</s:param>
                    </s:fielderror>                            
                </span>
          </td>                                 
     </tr>    
</s:if>    
    </table>  
         <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                <c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                					|| (sessionScope.role == 'SuAbstractor')}">
                    <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Submitted' || sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Amendment Submitted' }">
                        <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                        <li><a href="#" class="btn" onclick="handleActionAccept();"><span class="btn_img"><span class="save">Accept</span></span></a></li>
                        <li><a href="#" class="btn" onclick="handleActionReject();"><span class="btn_img"><span class="save">Reject</span></span></a></li>
                    </c:if>
                  </c:if>  
                </ul>   
            </del>

        </div>          

    </s:form>
   </div>

 </body>
 </html>