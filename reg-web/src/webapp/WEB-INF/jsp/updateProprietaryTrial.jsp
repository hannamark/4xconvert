<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Update <fmt:message key="submit.proprietary.trial.page.title"/></title>   
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
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
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
function lookup4loadSummary4Sponsor(){
	showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
}
//
function loadLeadOrgDiv() { 
    document.getElementById("trialDTO.leadOrganizationIdentifier").value = orgid;
    document.getElementById('trialDTO.leadOrganizationName').value = chosenname;
}
function loadSummary4SponsorDiv() {
    document.getElementById("trialDTO.summaryFourOrgName").value = chosenname;
    document.getElementById('trialDTO.summaryFourOrgIdentifier').value = orgid;
}
//
function reviewProtocol (){ 
    var action = "updateProprietaryTrialreview.action"; 
    document.forms[0].page.value = "review";
    document.forms[0].action=action;
    document.forms[0].submit();
    showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
}
function cancelProtocol (){   
    var action = "updateProprietaryTrialcancel.action";   
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
    <c:set var="topic" scope="request" value="update_trial"/> 
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="updateProprietaryTrial" method="POST" enctype="multipart/form-data">
    <s:if test="hasActionErrors()">
    <div class="error_msg">
    <s:actionerror/>
    </div>
    </s:if>
        <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
        <s:hidden name="trialDTO.sitePiIdentifier" id="trialDTO.sitePiIdentifier"/> 
        <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
        <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
        <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
    	<s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
    	<s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
        <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
            <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
        </c:if>
        <s:hidden name="page" />
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="submit.proprietary.trial.trialIdentification"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
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
            <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>
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
                    <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                         <s:select headerKey="" headerValue="--Select--" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            cssStyle="width:206px" 
                            disabled="true"/>
                     </c:if>
                    <c:if test="${empty trialDTO.summaryFourFundingCategoryCode}">
                         <s:select headerKey="" headerValue="--Select--" 
                            name="trialDTO.summaryFourFundingCategoryCode" 
                            list="#summaryFourFundingCategoryCodeValues"
                            cssStyle="width:206px" />
                     </c:if>
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
                <td colspan="2" class="space">&nbsp;</td>
          </tr>          
         <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
          <table class="data2">
         <tr>
              <th colspan="2">Participating Sites</th>
         </tr>
         <tr> <td>
          <table class="form">
         <tbody> 
                    <tr>
                        <th>Organization/Investigator</th>
                        <th>Local Trial<br/> Identifier<span class="required">*</span></th>
                        <th>Program Code</th>
                        <th>Current Site<br/> Recruitment Status<span class="required">*</span></th>
                        <th>Current Site<br/> Recruitment <br/>Status Date<span class="required">*</span></th>
                        <th>Date Opened <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                        <th>Date Closed <br/>for Accrual <br/>(mm/dd/yyyy) </th>
                        
                    </tr>
                                                                                                         
                    <s:iterator id="trialDTO.participatingSitesList" value="trialDTO.participatingSitesList" status="psstats">
                    <tr>
                        <td>
                            <s:textarea  name="trialDTO.participatingSitesList[%{#psstats.index}].nameInvestigator" value="%{nameInvestigator}" readonly="true" cssStyle="width:200px;border: 1px solid #FFFFFF" rows="2"/>
                            <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].name" value="%{name}"/>
                            <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].investigator" value="%{investigator}"/>
                        </td>
                        <td>
                            <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].siteLocalTrialIdentifier" value="%{siteLocalTrialIdentifier}"/>
                        </td>                       
                        <td>
                           <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].programCode" value="%{programCode}"/>
                           <s:hidden  name="trialDTO.participatingSitesList[%{#psstats.index}].id" value="%{id}"/> 
                        </td>    
                         <s:set name="recruitmentStatusValues" 
                           value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                        <td class="value"><s:select headerKey="" headerValue="--Select--"
                            name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                            list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                       
                      </td> 
                       <td>
                            <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}" size="12"/>                           
                        </td> 
                       <td>
                            <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateOpenedforAccrual" value="%{dateOpenedforAccrual}" size="12"/>                           
                        </td> 
                       <td>
                            <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateClosedforAccrual" value="%{dateClosedforAccrual}" size="12"/>                           
                        </td>
                    </tr>
                    </s:iterator >                      
                </tbody>   
         </table>
         </td></tr></table> 
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
