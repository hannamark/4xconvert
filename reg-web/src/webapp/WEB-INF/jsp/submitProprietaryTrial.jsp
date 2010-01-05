<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.proprietary.trial.page.title"/></title>   
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
        addCalendar("Cal1", "Select Date", "trialDTO.siteStatusDate", "submitProprietaryTrial");
        addCalendar("Cal2", "Select Date", "trialDTO.dateOpenedforAccrual", "submitProprietaryTrial");
        addCalendar("Cal3", "Select Date", "trialDTO.dateClosedforAccrual", "submitProprietaryTrial");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
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
}

//
function lookup4loadleadorg(){
	showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Lead Organization');
}
function lookup4loadSiteOrg () {
	showPopup('${lookupOrgUrl}', loadSiteOrgDiv, 'Select Site Organization');
}
function lookup4loadSitePerson(){
	showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Site Principal Investigator');
}
function lookup4loadSummary4Sponsor(){
	showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
}
//
function loadLeadOrgDiv() { 
    document.getElementById("trialDTO.leadOrganizationIdentifier").value = orgid;
    document.getElementById('trialDTO.leadOrganizationName').value = chosenname;
}
function loadSiteOrgDiv() {
	document.getElementById("trialDTO.siteOrganizationIdentifier").value = orgid;
    document.getElementById('trialDTO.siteOrganizationName').value = chosenname;
}
function loadLeadPersDiv() {
    document.getElementById("trialDTO.sitePiIdentifier").value = persid;
    document.getElementById('trialDTO.sitePiName').value = chosenname;
}
function loadSummary4SponsorDiv() {
    document.getElementById("trialDTO.summaryFourOrgName").value = chosenname;
    document.getElementById('trialDTO.summaryFourOrgIdentifier').value = orgid;
}
//
function reviewProtocol (){ 
    var action = "submitProprietaryTrialreview.action"; 
    document.forms[0].page.value = "review";
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
}
function cancelProtocol (){   
    var action = "submitProprietaryTrialcancel.action";   
    document.forms[0].page.value = "cancle";
    document.forms[0].action=action;
    document.forms[0].submit();
}
function callAjax(url, div){
    var aj = new Ajax.Updater(div, url, { asynchronous: true,  method: 'get', evalScripts: false });
    return false;
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
    <h1><fmt:message key="submit.trial.page.header"/></h1>
    <c:set var="topic" scope="request" value="submit_trial"/> 
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="submitProprietaryTrial" method="POST" enctype="multipart/form-data">
    <s:if test="hasActionErrors()">
    <div class="error_msg">
    <s:actionerror/>
    </div>
    </s:if>
        <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
        <s:hidden name="trialDTO.sitePiIdentifier" id="trialDTO.sitePiIdentifier"/> 
        <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
        <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
    
        <s:hidden name="page" />
        <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="submit.proprietary.trial.trialIdentification"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
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
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_localProtocolIdentifier"> <fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.leadOrgTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
           <tr>
                    <td scope="row" class="label">
                        <label for="submitTrial_selectedLeadOrg_name_part_0__value"><fmt:message key="submit.proprietary.trial.siteOrganization"/><span class="required">*</span></label> 
                    </td>
                    <td class="value">
                        <div id="loadSiteOrgField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSiteOrganization.jsp" %>
                        </div>      
                    </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_participationWebDTO_localProtocolIdentifier"> <fmt:message key="submit.proprietary.trial.siteidentifier"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="trialDTO.localSiteIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.localSiteIdentifier</s:param>
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
                    <span class="info">(Mandatory if Exists)</span>
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.nctIdentifier</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
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
          <!-- include po person jsp -->
          <tr>
                    <td scope="row" class="label">
                        <label for="submitTrial_poLeadPiFullName"><fmt:message key="submit.proprietary.trial.siteInvestigator"/><span class="required">*</span></label> 
                    </td>
                    <td class="value">
                        <div id="loadPersField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSitePrincipalInvestigator.jsp" %>
                        </div>      
                    </td>
                    
                </tr>
        <tr><td colspan="2" class="space">&nbsp;</td></tr>
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
             <td scope="row" class="label"><label for="siteProgramCode"><fmt:message key="submit.proprietary.trial.sitePrgCode"/></label></td>
             <td class="value">
                <s:textfield name="trialDTO.siteProgramCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                <span class="formErrorMsg">
                    <s:fielderror>
                            <s:param>trialDTO.programCodeText</s:param>
                    </s:fielderror>                            
                 </span>
               </td>
            </tr>                                             
          
          <tr>  <td colspan="2" class="space">&nbsp;</td></tr>
          
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <table class="form">
          <tr>
                <th colspan="2"><fmt:message key="submit.proprietary.trial.statusDate"/></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="submitTrial_overallStatusWebDTO_statusCode"> <fmt:message key="submit.trial.siteRecruitmentStatus"/><span class="required">*</span></label>
                </td>
                    <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="--Select--" name="trialDTO.siteStatusCode" list="#recruitmentStatusValues"  value="trialDTO.siteStatusCode" cssStyle="width:206px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>trialDTO.siteStatusCode</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
          </tr>
        <tr>
            <td scope="row" class="label"><label for="submitTrial_overallStatusWebDTO_statusDate"><fmt:message
                key="submit.trial.siteRecruitmentStatusDate" /><span class="required">*</span></label></td>
            <td class="value"><s:textfield name="trialDTO.siteStatusDate"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                            <s:param>trialDTO.siteStatusDate</s:param>
                        </s:fielderror>                            
                    </span>
                </td>
        </tr>
        <tr>
            <td scope="row" class="label"><label for="submitTrial_protocolWebDTO_startDate"><fmt:message
                key="submit.proprietary.trial.dateOpenedforAccrual" /></label></td>
            <td class="value"><s:textfield name="trialDTO.dateOpenedforAccrual"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal2')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
                <span class="formErrorMsg"> 
                   <s:fielderror>
                    <s:param>trialDTO.dateOpenedforAccrual</s:param>
                   </s:fielderror>                            
                </span>
            </td>

        </tr>
        <tr>
            <td scope="row" class="label"><label for="submitTrial_protocolWebDTO_completionDate">
            <fmt:message key="submit.proprietary.trial.dateClosedforAccrual" /></label></td>
            <td class="value"><s:textfield name="trialDTO.dateClosedforAccrual"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal3')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <span class="info"><fmt:message key="error.proprietary.submit.dateOpenReq" /></span>  
                <span class="formErrorMsg"> 
                   <s:fielderror>
                   <s:param>trialDTO.dateClosedforAccrual</s:param>
                   </s:fielderror>                            
                </span>
            </td>
        </tr>
       </table> 
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
        </table>
        <table class="form">  
        <tr>
              <th colspan="2"><fmt:message key="submit.trial.documents"/></th>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2">
               <fmt:message key="submit.proprietary.trial.docInstructionalText"/>
            </td>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
        </tr>
        
        <tr>
              <td scope="row" class="label">
              <label for="submitTrial_protocolDoc">
                     <fmt:message key="submit.proprietary.trial.protocolDocument"/>
              </label>
             </td>
             <td class="value">
                 <s:file name="protocolDoc" value="true" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.protocolDocFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="submitTrial_otherDocument"><fmt:message key="submit.trial.otherDocument"/></label>
             </td>
             <td class="value">
                 <s:file name="otherDocument" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDTO.otherDocumentFileName</s:param>
                   </s:fielderror>                            
                 </span>                 
               </td>         
         </tr> 
         </table>
        <p align="center" class="info">
           Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.  
           <br>Once you submit the trial you will not be able to modify the information.
        </p>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                        <li>
                        <li>            
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
