<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="submit.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->        
       <!--  <script type="text/javascript" language="javascript">
                addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "submitTrial");
                addCalendar("Cal2", "Select Date", "trialDTO.startDate", "submitTrial");
                addCalendar("Cal3", "Select Date", "trialDTO.primaryCompletionDate", "submitTrial");
                addCalendar("Cal4", "Select Date", "trialDTO.completionDate", "submitTrial");
                setWidth(90, 1, 15, 1);
                setFormat("mm/dd/yyyy");
        </script> -->
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="submitProtocol"/>
        <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            var orgid;
            var chosenname;
            var p30GrantSerialNumber;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;
            var countOfOtherIdentifiers = 0;

            jQuery(function() {
                jQuery("#serialNumber").autocomplete({delay: 250,
                      source: function(req, responseFn) {
                        var instCode = jQuery("#nihInstitutionCode").val();
                        if ('CA' != instCode) {
                            return;
                        }
                        var url = registryApp.contextPath + '/ctro/json/ajaxI2EGrantsloadSerialNumbers.action?serialNumberMatchTerm=' + req.term;
                        jQuery.getJSON(url,null,function(data){
                               responseFn(jQuery.map(data.serialNumbers, function (value, key) { 
                                    return {
                                        label: value,
                                        value: key
                                    };
                               }));
                        });
                    }
                });
            });

            function countLeft(field, count, max) {
                // if the length of the string in the input field is greater than the max value, trim it 
                if ($(field).value.length > max)
                	$(field).value = $(field).value.substring(0, max);
                else
                // calculate the remaining characters  
                $(count).value = max - $(field).value.length;
            }
           
            
            function setorgid(orgIdentifier, oname, p30grant) {
                orgid = orgIdentifier;
                chosenname = oname.replace(/&apos;/g,"'");
                p30GrantSerialNumber = p30grant;
            }
            
            function setpersid(persIdentifier, sname, email, phone) {
                persid = persIdentifier;
                chosenname = sname.replace(/&apos;/g,"'");
                contactMail = email;
                contactPhone = phone;
            }
            
            function lookup4loadleadorg() {
                showPopup('${lookupOrgUrl}',loadLeadOrgDiv, 'Select Lead Organization');
            }
            
            function lookup4loadleadpers() {
                showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
            }
            
            function lookup4sponsor() {
                showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
            }
            
            function lookup4loadSummary4Sponsor() {
                showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
            }
            
            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
                deleteP30Grants();
                if( p30GrantSerialNumber){
                    var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
                    var params = {
                        fundingMechanismCode: 'P30',
                        nciDivisionProgramCode: 'OD',
                        nihInstitutionCode: 'CA',
                        serialNumber: p30GrantSerialNumber
                    };
                    var div = $('grantdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    resetGrantRow();
                } 
            }
            
            function loadLeadPersDiv() {
                $("trialDTO.piIdentifier").value = persid;
                $('trialDTO.piName').value = chosenname;
                
                var partyType = $F('trialDTO.responsiblePartyType');
                if (partyType=='pi') {                
                    $('trialDTO.responsiblePersonIdentifier').value=persid;
                    $('trialDTO.responsiblePersonName').value=chosenname;
                }
            }
            
            function loadSponsorDiv() {
                $("trialDTO.sponsorIdentifier").value = orgid;
                $('trialDTO.sponsorName').value = chosenname;                             
                respartOrgid = orgid;
                
                var partyType = $F('trialDTO.responsiblePartyType');
                if (partyType=='si') {                
                     $('trialDTO.responsiblePersonAffiliationOrgId').value=orgid;
                     $('trialDTO.responsiblePersonAffiliationOrgName').value=chosenname;
                }
            }
           
            function loadSummary4SponsorDiv() {
                var url = '/registry/protected/popupaddSummaryFourOrg.action';
                var params = { orgId: orgid, chosenName : chosenname };
                var div = document.getElementById('loadSummary4FundingSponsorField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
            }

            function deleteSummary4SponsorRow(rowid) {
                var  url = '/registry/protected/popupdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function reviewProtocol () {
                submitFirstForm("review", "submitTrialreview.action");
            }
            
            function partialSave() {
                submitFirstForm(null, "submitTrialpartialSave.action");
            }
            
            function cancelProtocol() {
                var retVal = confirm("All data entered will be lost. Do you want to continue ?");
                if( retVal == true ){
                    submitFirstForm("Submit", "submitTrialcancel.action");
                    return true;
                }else{
                    return false;
                } 
            }
            
            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function addGrant() {
                var fundingMechanismCode = $('fundingMechanismCode').value;
                var nihInstitutionCode = $('nihInstitutionCode').value;
                var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
                var serialNumber = $('serialNumber').value;
                serialNumber = trim(serialNumber);
                var fundingPercent =  $('fundingPercent').value;
                var isValidGrant;
                var isSerialEmpty = false;
                var alertMessage = "";
                if (fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
                    isValidGrant = false;
                    alertMessage="Please select a Funding Mechanism";
                }
                if (nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Please select an Institute Code";
                }
                if (serialNumber.length == 0 || serialNumber == null) {
                    isValidGrant = false;
                    isSerialEmpty = true;
                    alertMessage=alertMessage+ "\n Please enter a Serial Number";
                }
                if (nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Please select a NCI Division/Program Code";
                }
                if (isSerialEmpty == false && isNaN(serialNumber)){
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Serial Number must be numeric";
                } else if (isSerialEmpty == false && serialNumber != null) {
                           var numericExpression = /^[0-9]+$/;
                           if (!numericExpression.test(serialNumber)) {
                               isValidGrant = false;
                               alertMessage=alertMessage+ "\n Serial Number must be numeric";
                           }
                }
                if (fundingPercent.length != 0 && fundingPercent != null) {
                    if (isNaN(fundingPercent)){
                        isValidGrant = false;
                        alertMessage=alertMessage+ "\n % of Grant Funding must be numeric";
                    } else if(Number(fundingPercent) > 100.0 || Number(fundingPercent) < 0.0){
                        isValidGrant = false;
                        alertMessage=alertMessage+ "\n % of Grant Funding must be positive and <= 100";
                    }
                }
                if (isValidGrant == false) {
                    alert(alertMessage);
                    return false;
                }
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber,
                    fundingPercent: fundingPercent
                };
                var div = $('grantdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetGrantRow();
            }

            function deleteGrantRow(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrant.action';
                var params = { uuid: rowid };
                var div = $('grantdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function deleteP30Grants(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteP30Grants.action';
                var div = $('grantdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, null);
            }

            function deleteOtherIdentifierRow(rowid) {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function resetGrantRow() {
                $('fundingMechanismCode').value = '';
                $('nihInstitutionCode').value = '';
                $('serialNumber').value = '';
                $('nciDivisionProgramCode').value = '';
                $('fundingPercent').value = '';
            }
            
            function deleteIndIde(rowid) {
                var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIde.action';
                var params = { uuid: rowid };
                var div = $('indidediv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function addIndIde(indIde, number, grantor, holderType, programCode, expandedAccess, expandedAccessType, exemptIndicator) {
                var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicator.action';
                var params = {
                    exemptIndicator: exemptIndicator,
                    expandedAccess: expandedAccess,
                    expandedAccessType: expandedAccessType,
                    grantor: grantor,
                    holderType: holderType,
                    indIde: indIde,
                    number: number,
                    programCode: programCode
                };
                var div = $('indidediv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetValues();
            }
            
            function loadRegAuthoritiesDiv() {
                var url = '/registry/protected/ajaxgetOAuthOrgsgetTrialOversightAuthorityOrganizationNameList.action';
                var params = { countryid: $('countries').value };
                var div = $('loadAuthField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function checkFDADropDown() {
                if ($('trialDTO.fdaRegulatoryInformationIndicator').value == ''| $('trialDTO.fdaRegulatoryInformationIndicator').value == 'No') {
                    input_box = confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        $('trialDTO.section801Indicator').value = '';
                        $('trialDTO.delayedPostingIndicator').value = '';
                        hideRow($('sec801row'));
                        hideRow($('delpostindrow'));
                    } else {
                        $('trialDTO.fdaRegulatoryInformationIndicator').value = 'Yes';
                    }
                } else {
                    showRow($('sec801row'));
                    showRow($('delpostindrow'));
                }
            }
        
            function checkSection108DropDown() {
                if ($('trialDTO.section801Indicator').value == '' | $('trialDTO.section801Indicator').value == 'No') {
                    input_box = confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        hideRow($('delpostindrow'));
                        $('trialDTO.delayedPostingIndicator').value = '';
                    } else {
                        $('trialDTO.section801Indicator').value = 'Yes';
                    }
                } else {
                    showRow($('delpostindrow'));
                }
            }
            
            function hideRow(row) {
                row.style.display = 'none';
            }
            
            function showRow(row) {
                row.style.display = '';
            }
            
            function addOtherIdentifier() {
                var orgValue=trim($("otherIdentifierOrg").value);
                if (orgValue != null && orgValue != '') {
                    var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value = "";
                } else {
                    alert("Please enter a valid Other identifier to add");
                }
            }
        
            document.observe("dom:loaded", function() {
                                               displayTrialStatusDefinition('trialDTO_statusCode');
                                           });
            
                       
            Event.observe(window, "load", setDisplayBasedOnTrialType);
            
        </script>
    </head>
    <body>
    <!-- main content begins-->
    <div class="container">
        <h1 class="heading"><span><fmt:message key="submit.trial.page.header"/></span></h1>
        <p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks (<span class="required">*</span>).</p>
        <c:set var="topic" scope="request" value="submittrial"/>
        <!-- div class="box" id="filters"-->
            <reg-web:failureMessage/>
            <s:form cssClass="form-horizontal" name="submitTrial" method="POST" enctype="multipart/form-data">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="error_msg">
                        <s:actionerror/>
                    </div>
                </s:if>
                <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
                <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
                <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                <s:hidden name="page" />
                <!--  p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks(<span class="required">*</span>).</p-->
                <div class="form-group">
                    <label for="organization-type" class="col-xs-4 control-label left-align">XML Required, Enable "Upload from NCI CTRP" in <a data-placement="top" rel="tooltip" data-original-title="Open in new window" href="http://www.clinicaltrials.gov/" target="_new">ClinicalTrials.gov</a>?</label>                    
                    <div class="col-xs-4">
                        <s:radio cssClass="radio-inline" name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="hidePrimaryCompletionDate(), toggledisplayDivs(this);"/>
                        <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Indicate whether you need an XML file to submit/update your trial to ClinicalTrials.gov." data-placement="top" data-trigger="hover"></i>
                    </div>                    
                </div>
                <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
                <div class="accordion-group">
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialIdentifiers.jsp" %>
                    <div class="accordion">
                        <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.otherIdentifiers"/></a></div>
                            <div id="section2" class="accordion-body in">
                                <div class="container">
                                    <div class="form-group">
                                        <label for="otherIdentifierOrg" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherIdentifier"/></label>
                                        <div class="col-xs-2">
                                            <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" class="form-control"/>
                                        </div>
                                        <div class="col-xs-4">
                                            <button onclick="addOtherIdentifier();" id="otherIdbtnid" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i> Add Other Identifier</button>
                                            <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Enter an additional trial identifier such as a unique identifier from other registries, NIH grant numbers, or protocol numbers assigned by the Review Board."  data-placement="top" data-trigger="hover"></i> 
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-3"></div> 
                                        <div class="col-xs-4" id="otherIdentifierdiv">
                                            <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp" %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="accordion">
                         <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a>
                         </div>
                             <div id="section3" class="accordion-body in">
                                 <div class="container">
                                     <div class="form-group">
                                     <label for="trialDTO.officialTitle" class="col-xs-4 control-label"><fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                                         <div class="col-xs-4">
                                          <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="form-control charcounter"/>
                                          <span class="formErrorMsg">
                                              <s:fielderror>
                                                  <s:param>trialDTO.officialTitle</s:param>
                                              </s:fielderror>
                                          </span>
                                          <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the name of the clinical trial as it appears in the protocol document."  data-placement="top" data-trigger="hover"></i>
                                     </div>
                                 </div>
                                 <%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
                             </div>
                         </div>
                     </div>
                     <div class="accordion">
                        <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.trial.leadOrgInvestigator"/><span class="required">*</span></a>
                        </div>
                        <div id="section4" class="accordion-body in">
                            <div class="container">
                            <div class="form-group">                                
                                <label for="trialDTO.leadOrganizationName" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label>
                                <!-- <div id="loadOrgField"> -->
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                                <!-- </div> -->
                            </div>                
                            <div class="form-group">
                                <label for="trialDTO.piName" class="col-xs-4 control-label"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
                                <div id="loadPersField">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
                                </div>              
                            </div>
                        </div>
                    </div>
                    </div>
                    <s:if test="%{trialDTO.xmlRequired == true}">
                        <div id="sponsorDiv" style="display:''">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                        </div>
                    </s:if>
                    <s:else>
                        <div id="sponsorDiv" style="display:none">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                        </div>
                    </s:else>
                    <div class="accordion">
                        <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a>
                        </div>
                        <div id="section6" class="accordion-body in">
                            <div class="container">
                                <div class="form-group">
                                    <label for="trialDTO.summaryFourFundingCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.trial.summary4FundingCategory"/></label>
                                    <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                                    <div class="col-xs-3">
                                       <s:select headerKey="" headerValue="--Select--" id="trialDTO.summaryFourFundingCategoryCode" 
                                           name="trialDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" 
                                           cssStyle="width:206px" disabled="true"/>
                                       <span class="formErrorMsg">
                                           <s:fielderror>
                                               <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                                           </s:fielderror>
                                       </span>
                                       <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Trial category you selected for trial submission."  data-placement="top" data-trigger="hover"></i>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label id="trialDTO.summaryFourOrgNameTR" for="trialDTO.summaryFourOrgName" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4Sponsor"/><span class="required">*</span></label>
                                    <div id="loadSummary4FundingSponsorField">
                                        <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                                    </div>                                    
                                </div>
                                <div class="form-group">                               
                                    <label for="trialDTO.programCodeText" class="col-xs-4 control-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                                    <div class="col-xs-2">
                                        <s:textfield id="trialDTO.programCodeText" name="trialDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" 
                                            cssClass="form-control"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.programCodeText</s:param>
                                            </s:fielderror>
                                        </span>
                                    </div>
                                    <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the Program Code that includes the study."  data-placement="top" data-trigger="hover"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion">
                        <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section7"><fmt:message key="submit.trial.grantInfo"/><span class="required">*</span></a>
                        </div>
                        <div id="section7" class="accordion-body in">
                            <div class="container">
                            <p><fmt:message key="submit.trial.grantInstructionalText"/></p>              
                                <div class="form-group">
                                    <label for="organization-type" class="col-xs-4 control-label">Is this trial funded by an NCI grant? <span class="required">*</span></label>
                                    <div class="col-xs-4">
                                        <s:radio cssClass="radio-inline" name="trialDTO.nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" />
                                    </div>
                                </div> 
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th><label for="fundingMechanismCode"><fmt:message key="submit.trial.fundingMechanism"/></label><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the NIH funding mechanism unique identifier, a 3-character code used to identify areas of extramural research activity applied to funding mechanisms."  data-placement="top" data-trigger="hover"></i></th>
                                            <th><label for="nihInstitutionCode"><fmt:message key="submit.trial.instituteCode"/></label><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the two-letter code identifying the first major-level subdivision, the organization that supports an NIH grant, contract, or inter-agency agreement. The support may be financial or administrative."  data-placement="top" data-trigger="hover"></i></th>
                                            <th><label for="serialNumber"><fmt:message key="submit.trial.serialNumber"/></label><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the number generally assigned sequentially to a series within an Institute, Center, or Division."  data-placement="top" data-trigger="hover"></i></th>
                                            <th><label for="nciDivisionProgramCode"> <fmt:message key="submit.trial.divProgram"/></label><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the NCI organizational unit that provides funding for the study."  data-placement="top" data-trigger="hover"></i></th>
                                            <th style="display:none">
                                                <label for="fundingPercent"><fmt:message key="submit.trial.fundingPercent"/></label>
                                            </th>
                                            <th>&nbsp;</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--"
                                                     name="fundingMechanismCode"
                                                     list="#fundingMechanismValues"
                                                     listKey="fundingMechanismCode"
                                                     listValue="fundingMechanismCode"
                                                     id="fundingMechanismCode"
                                                     cssStyle="width:150px" cssClass="form-control"/>
                                            </td>
                                            <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--"
                                                     name="nihInstitutionCode"
                                                     list="#nihInstituteCodes"
                                                     listKey="nihInstituteCode"
                                                     listValue="nihInstituteCode"
                                                     id="nihInstitutionCode"
                                                     cssStyle="width:150px" cssClass="form-control"/>
                                            </td>
                                            <td>
                                                <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  
                                                    cssClass="form-control"/>
                                            </td>
                                            <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  
                                                    cssStyle="width:150px" />
                                            </td>
                                            <td style="display:none">
                                                <s:textfield name="fundingPercent" id="fundingPercent" maxlength="5" size="5"  cssStyle="width:50px"  
                                                    cssClass="form-control"/>%
                                            </td>
                                            <td>
                                                <button type="button" id="grantbtnid" class="btn btn-icon btn-default" onclick="addGrant();"><i class="fa-plus"></i>Add Grant</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="form-group" id="grantdiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                                </div>
                                <span class="formErrorMsg">
                                    <s:fielderror>
                                        <s:param>trialDTO.nciGrant</s:param>
                                    </s:fielderror>
                                </span>                                
                            </div>
                        </div>
                    </div>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>
                </div>
                <table class="form">
                    <table class="data2">
                        <reg-web:titleRow titleKey="submit.trial.indInfo"/>
                        <reg-web:spaceRow/>
                        <tr>
                            <td colspan="2">
                               <fmt:message key="submit.trial.indInstructionalText"/>
                            </td>
                        </tr>
                        <reg-web:spaceRow/>
                        <tr>
                            <td colspan="2" class="space">
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
                            <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
                        </div>
                    </s:if>
                    <s:else>
                        <div id="regDiv" style="display:none">
                            <!-- Regulatory page -->
                            <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
                        </div>
                    </s:else>
                    <reg-web:spaceRow/>
                </table>
                <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="partialSave()"><span class="btn_img"><span class="save">Save as Draft</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="reviewProtocol()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
                <s:hidden name="uuidhidden"/>
            </s:form>
        <!-- /div-->
    </div>
    </body>
</html>

