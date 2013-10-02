<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="submit.trial.page.title"/></title>
        <s:head/>

        <!-- po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <!-- /po integration -->
        <script type="text/javascript" language="javascript">
                addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "submitTrial");
                addCalendar("Cal2", "Select Date", "trialDTO.startDate", "submitTrial");
                addCalendar("Cal3", "Select Date", "trialDTO.primaryCompletionDate", "submitTrial");
                addCalendar("Cal4", "Select Date", "trialDTO.completionDate", "submitTrial");
                setWidth(90, 1, 15, 1);
                setFormat("mm/dd/yyyy");
        </script>
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
        <h1><fmt:message key="submit.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="submittrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="submitTrial" method="POST" enctype="multipart/form-data">
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
                <p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks(<span class="required">*</span>).</p>
                <table class="form">
                    <tr>
                        <td scope="row" class="label">
                            <reg-web:displayTooltip tooltip="tooltip.ct_gov_xml">
                                XML required for <a href="http://www.ClinicalTrials.gov" target="_new">ClinicalTrials.gov</a>?
                            </reg-web:displayTooltip>
                        </td>
                        <td>
                            <s:radio name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="hidePrimaryCompletionDate(), toggledisplayDivs(this);"/>
                        </td>
                    </tr>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialIdentifiers.jsp" %>
                    <reg-web:titleRow titleKey="submit.trial.otherIdentifiers"/>
                    <reg-web:valueRow labelFor="otherIdentifierOrg" labelKey="submit.trial.otherIdentifier" tooltip="tooltip.other_identifier">
                        <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value=""/>&nbsp; 
                        <input type="button" id="otherIdbtnid" value="Add Other Identifier" onclick="addOtherIdentifier();" />
                    </reg-web:valueRow>
                    <tr>
                        <td colspan="2" class="space">
                            <div id="otherIdentifierdiv">
                                <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp" %>
                            </div>
                        </td>
                    </tr>
                    <reg-web:titleRow titleKey="submit.trial.trialDetails"/>
                    <reg-web:valueRow labelFor="trialDTO.officialTitle" labelKey="submit.trial.title" required="true" tooltip="tooltip.title">
                        <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="charcounter"/>
                       
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.officialTitle</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
                    <reg-web:spaceRow/>
                    <reg-web:titleRow titleKey="submit.trial.leadOrgInvestigator"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="trialDTO.leadOrganizationName" labelKey="submit.trial.leadOrganization" required="true" tooltip="tooltip.lead_organization">
                        <div id="loadOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <!-- include po person jsp -->
                    <reg-web:valueRow labelFor="trialDTO.piName" labelKey="submit.trial.principalInvestigator" required="true" tooltip="tooltip.pi">
                        <div id="loadPersField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <tr> 
                        <td colspan="2" class="space">
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
                        </td>
                    </tr>
                    <reg-web:spaceRow/>
                    <!--  summary4 information -->
                    <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="trialDTO.summaryFourFundingCategoryCode" labelKey="update.trial.summary4FundingCategory" tooltip="tooltip.summary_4_funding_sponsor_type">
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                        <s:select headerKey="" headerValue="--Select--" 
                                    id="trialDTO.summaryFourFundingCategoryCode"
                                    name="trialDTO.summaryFourFundingCategoryCode"
                                    list="#summaryFourFundingCategoryCodeValues"
                                    cssStyle="width:206px" disabled="true"/>
                         <span class="formErrorMsg">
                             <s:fielderror>
                                 <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                             </s:fielderror>
                         </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow id="trialDTO.summaryFourOrgName" labelKey="update.proprietary.trial.summary4Sponsor" 
                                      required="true" tooltip="tooltip.summary_4_funding_source">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.programCodeText" labelKey="studyProtocol.summaryFourPrgCode" tooltip="tooltip.summary_4_program_code">
                        <s:textfield id="trialDTO.programCodeText" name="trialDTO.programCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.programCodeText</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <table class="data2">
                        <reg-web:titleRow titleKey="submit.trial.grantInfo"/>
                        <reg-web:spaceRow/>
                        <tr>
                            <td colspan="2">
                               <fmt:message key="submit.trial.grantInstructionalText"/>
                            </td>
                        </tr>
                        <tr>
                          <td>
                            Is this trial funded by an NCI grant?<span class="required">*</span>
                            <s:radio name="trialDTO.nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" />
                          </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <table class="form">
                                    <tbody>
                                        <tr>
                                            <th>
                                                <reg-web:displayTooltip tooltip="tooltip.funding_mechanism">
                                                    <label for="fundingMechanismCode"><fmt:message key="submit.trial.fundingMechanism"/></label>
                                                </reg-web:displayTooltip>
                                            </th>
                                            <th>
                                                <reg-web:displayTooltip tooltip="tooltip.institution_code">
                                                    <label for="nihInstitutionCode"><fmt:message key="submit.trial.instituteCode"/></label>
                                                </reg-web:displayTooltip>
                                            </th>
                                            <th>
                                                <reg-web:displayTooltip tooltip="tooltip.serial_number">
                                                   <label for="serialNumber"><fmt:message key="submit.trial.serialNumber"/></label>
                                                </reg-web:displayTooltip>
                                            </th>
                                            <th>
                                                <reg-web:displayTooltip tooltip="tooltip.nci_division_program_code">
                                                   <label for="nciDivisionProgramCode"> <fmt:message key="submit.trial.divProgram"/></label>
                                                </reg-web:displayTooltip>
                                            </th>
                                            <th style="display:none">
                                                   <label for="fundingPercent"><fmt:message key="submit.trial.fundingPercent"/></label>
                                            </th>
                                            <th></th>
                                        </tr>
                                        <tr>
                                            <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--"
                                                     name="fundingMechanismCode"
                                                     list="#fundingMechanismValues"
                                                     listKey="fundingMechanismCode"
                                                     listValue="fundingMechanismCode"
                                                     id="fundingMechanismCode"
                                                     cssStyle="width:150px" />
                                            </td>
                                            <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--"
                                                     name="nihInstitutionCode"
                                                     list="#nihInstituteCodes"
                                                     listKey="nihInstituteCode"
                                                     listValue="nihInstituteCode"
                                                     id="nihInstitutionCode"
                                                     cssStyle="width:150px"  />
                                            </td>
                                            <td>
                                                <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                            </td>
                                            <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                            <td>
                                                <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  cssStyle="width:150px" />
                                            </td>
                                            <td style="display:none">
                                                <s:textfield name="fundingPercent" id="fundingPercent" maxlength="5" size="5"  cssStyle="width:50px" />%
                                            </td>
                                            <td><input type="button" id="grantbtnid" value="Add Grant" onclick="addGrant();" /></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <tr>
                        <td colspan="2" class="space">
                            <div id="grantdiv">
                                <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                            </div>
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>trialDTO.nciGrant</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <reg-web:spaceRow/>
                    <table class="form">
                        <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>
                    </table>
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
        </div>
    </body>
</html>

