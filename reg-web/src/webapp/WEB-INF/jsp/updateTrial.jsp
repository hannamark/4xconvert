<%@ page import="gov.nih.nci.registry.util.Constants" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="update.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
        <link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/showhide.js'/>"></script>
        <!-- /po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "updateTrial");
            addCalendar("Cal2", "Select Date", "trialDTO.startDate", "updateTrial");
            addCalendar("Cal3", "Select Date", "trialDTO.completionDate", "updateTrial");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        </script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocol"/>
        <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            var orgid;
            var chosenname;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;
            
            function setorgid(orgIdentifier, oname) {
                orgid = orgIdentifier;
                chosenname = oname.replace(/&apos;/g,"'");
            }
            
            function setpersid(persIdentifier, sname, email, phone) {
                persid = persIdentifier;
                chosenname = sname.replace(/&apos;/g,"'");
                contactMail = email;
                contactPhone = phone;
            }

            function lookup4loadleadorg() {
                showPopup('${lookupOrgUrl}', loadLeadOrgDiv, 'Select Lead Organization');
            }
            
            function lookup4loadleadpers() {
                showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
            }
            
            function lookup4sponsor() {
                showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
            }
            
            function lookup4loadresponsibleparty() {
                showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible Party Contact');
            }
            
            function lookup4loadresponsiblepartygenericcontact() {
               var orgid = $('trialDTO.sponsorIdentifier').value;
               showPop
               up('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid, createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
            }
            
            function lookup4loadSummary4Sponsor() {
                showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
            }
            
            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
            }
            
            function loadLeadPersDiv() {
                $("trialDTO.piIdentifier").value = persid;
                $('trialDTO.piName').value = chosenname;
            }
            
            function loadSponsorDiv() {
                $("trialDTO.sponsorIdentifier").value = orgid;
                $('trialDTO.sponsorName').value = chosenname;
                $('lookupbtn4RP').disabled = "";
                respartOrgid = orgid;
            }
            
            function createOrgContactDiv() {
                $("trialDTO.responsiblePersonIdentifier").value = persid;
                $('trialDTO.responsiblePersonName').value = chosenname;
                $('lookupbtn4RP').disabled = "";
                $('trialDTO.responsibleGenericContactName').value = '';
            }
            
            function createOrgGenericContactDiv() {
                $('trialDTO.responsiblePersonIdentifier').value = persid;
                $('trialDTO.responsibleGenericContactName').value = chosenname;
                $("trialDTO.contactEmail").value = contactMail;
                $("trialDTO.contactPhone").value = contactPhone;
                $('lookupbtn4RP').disabled = "";
                $('trialDTO.responsiblePersonName').value = '';
            }
            
            function loadSummary4SponsorDiv() {
                $("trialDTO.summaryFourOrgName").value = chosenname;
                $('trialDTO.summaryFourOrgIdentifier').value = orgid;
            }
            
            function reviewProtocolUpdate() {
                submitFirstForm("save", "updateTrialreviewUpdate.action");
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol(){
                submitFirstForm("Submit", "updateTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function manageRespPartyLookUp() {
                if ($('trialDTO.responsiblePartyTypepi').checked == true) {
                    $('rpcid').style.display='none';
                    $('rpgcid').style.display='none';
                    $('trialDTO.responsiblePersonName').value = '';
                }
                if ($('trialDTO.responsiblePartyTypesponsor').checked == true) {
                            $('rpcid').style.display='';
                            $('rpgcid').style.display='';
                }
            }
            
            function trim(val) {
                var ret = val.replace(/^\s+/, '');
                ret = ret.replace(/\s+$/, '');
                return ret;
            }
            
            function addGrant() {
                var fundingMechanismCode = $('fundingMechanismCode').value;
                var nihInstitutionCode = $('nihInstitutionCode').value;
                var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
                var serialNumber = $('serialNumber').value;
                serialNumber = trim(serialNumber);
                var isValidGrant;
                var isSerialEmpty  =false;
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
                if (isSerialEmpty == false && isNaN(serialNumber)) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Serial Number must be numeric";
                } else if (isSerialEmpty == false && serialNumber != null) {
                           var numericExpression = /^[0-9]+$/;
                           if (!numericExpression.test(serialNumber)) {
                               isValidGrant = false;
                               alertMessage=alertMessage+ "\n Serial Number must be numeric";
                           }
                }
                if (isSerialEmpty == false && (serialNumber.length < 5 || serialNumber.length > 6)) {
                    isValidGrant = false;
                    alertMessage=alertMessage+ "\n Serial Number must be 5 or 6 digits";
                }
                if (isValidGrant == false) {
                    alert(alertMessage);
                    return false;
                }
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrantForUpdate.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber
                };
                var div = $('grantadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetGrantRow();
            }
            
            function deleteGrantRow(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrantForUpdate.action';
                var params = { uuid: rowid };
                var div = $('grantadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function resetGrantRow() {
                $('fundingMechanismCode').value = '';
                $('nihInstitutionCode').value = '';
                $('serialNumber').value = '';
                $('nciDivisionProgramCode').value = '';
            }
            
            function deleteIndIde(rowid) {
                var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIdeForUpdate.action';
                var params = { uuid: rowid };
                var div = $('indideadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function addIndIde(indIde, number, grantor, holderType, programCode, expandedAccess, expandedAccessType, exemptIndicator) {
                var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicatorForUpdate.action';
                var params = {
                        exemptIndicator: exemptIndicator,
                        expandedAccess: expandedAccess,
                        expandedAccessType: expandedAccessType,
                        grantor: grantor,
                        holderType: holderType,
                        indIde: indIde,
                        number: number,
                        programCode: programCode,
                        studyid: $('trialDTO.studyProtocolId').value
                    };
                var div = $('indideadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetValues();
            }
            
            function checkFDADropDown(){
                if ($('trialDTO.fdaRegulatoryInformationIndicator').value == '' | $('trialDTO.fdaRegulatoryInformationIndicator').value == 'No'){
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
        
            function checkSection108DropDown(){
                if ($('trialDTO.section801Indicator').value == '' | $('trialDTO.section801Indicator').value == 'No') {
                    input_box = confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
                    if (input_box == true) {
                        hideRow($('delpostindrow'));
                        $('trialDTO.delayedPostingIndicator').value ='';
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
            
            function loadRegAuthoritiesDiv() {
                var url = '/registry/protected/ajaxgetOAuthOrgsgetTrialOversightAuthorityOrganizationNameList.action';
                var params = { countryid: $('countries').value };
                var div = $('loadAuthField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
                return false;
            }
            
            function addOtherIdentifier() {
                var orgValue = trim($("otherIdentifierOrg").value);
                if (orgValue != null && orgValue != '') {
                    var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value="";
                } else {
                    alert("Please enter a valid Other identifier to add");
                }
            }
            
            function deleteOtherIdentifierRow(rowid) {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActiondeleteOtherIdentifierUpdate.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            document.observe("dom:loaded", function() {
                                               displayTrialStatusDefinition('updateTrial_trialDTO_statusCode');
                                           });
        </script>
    </head>
    <body>
    
        <!-- main content begins-->
        <h1><fmt:message key="update.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="updatetrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="updateTrial" id="updateTrial" method="POST" validate="false" enctype="multipart/form-data">
                <s:if test="hasActionErrors()">
                    <div class="error_msg"><s:actionerror/></div>
                </s:if>
                <s:else>
                    <s:actionerror/>
                </s:else>
                <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
                <s:hidden name="trialDTO.phaseCode" id="trialDTO.phaseCode"/>
                <s:hidden name="trialDTO.officialTitle" id="trialDTO.officialTitle"/>
                <s:hidden name="trialDTO.trialType" id="trialDTO.trialType"/>
                <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
                <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
                <s:hidden name="trialDTO.summaryFourOrgIdentifier" id="trialDTO.summaryFourOrgIdentifier"/>
                <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.leadOrgTrialIdentifier" id="trialDTO.leadOrgTrialIdentifier"/>
                <s:hidden name="trialDTO.phaseAdditonalQualifier" id="trialDTO.phaseAdditonalQualifier" />
                <s:hidden name="trialDTO.xmlRequired" id="trialDTO.xmlRequired" />
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                    <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                </c:if>
                <s:hidden name="page" />
                <s:hidden name="uuidhidden"/>
                <p>Update trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
                <table class="form">
                    <tr>
                        <th colspan="2"><fmt:message key="update.trial.trialDetails"/></th>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table>
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
                                <%@ include file="/WEB-INF/jsp/nodecorate/showOtherIds.jsp" %>
                                <tr>
                                    <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_nctNumber"> <fmt:message key="update.trial.nctNumber"/></label>
                                    </td>
                                    <td>
                                        <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                                    </td>
                                </tr>
                                <c:if test="${trialDTO.ctepIdentifier != null }">
                                    <tr>
                                        <td scope="row" class="label">
                                            <label for="updateTrial_CTEPNumber"> <fmt:message key="submit.trial.ctepIdentifier"/></label>
                                        </td>
                                        <td>
                                            <s:textfield name="trialDTO.ctepIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${trialDTO.dcpIdentifier!= null}">
                                    <tr>
                                        <td scope="row" class="label">
                                            <label for="updateTrial_CTEPNumber"> <fmt:message key="submit.trial.dcpIdentifier"/></label>
                                        </td>
                                        <td>
                                            <s:textfield name="trialDTO.dcpIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                                        </td>
                                    </tr>
                                </c:if>
                                <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr> 
                        <td colspan="2" class="space">
                            <s:if test="%{trialDTO.xmlRequired == true}">
                                <div id="sponsorDiv" style="display:''">
                                     <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialResponsibleParty.jsp" %>
                                </div>
                            </s:if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <!--  summary4 information -->
                    <%@ include file="/WEB-INF/jsp/nodecorate/summaryFourInfo.jsp" %>
                    <tr>  
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table class="data2">
                                <tr>
                                    <th colspan="2"><fmt:message key="update.trial.grantInfo"/></th>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                       <fmt:message key="update.trial.grantInstructionalText"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="3">
                                        <table class="form">
                                            <tbody>
                                                <tr>
                                                    <th><fmt:message key="update.trial.fundingMechanism"/></th>
                                                    <th><fmt:message key="update.trial.instituteCode"/></th>
                                                    <th><fmt:message key="update.trial.serialNumber"/></th>
                                                    <th><fmt:message key="update.trial.divProgram"/></th>
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
                                                             value="fundingMechanismCode"
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
                                                             value="nihInstitutionCode"
                                                             cssStyle="width:150px"  />
                                                        <span class="formErrorMsg" >
                                                            <s:fielderror>
                                                                <s:param>nihInstitutionCode</s:param>
                                                           </s:fielderror>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <s:textfield name="serialNumber" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                                        <span class="formErrorMsg">
                                                            <s:fielderror>
                                                                <s:param>serialNumber</s:param>
                                                            </s:fielderror>
                                                        </span>
                                                    </td>
                                                    <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                                    <td>
                                                        <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="nciDivisionProgramCode" cssStyle="width:150px" />
                                                        <span class="formErrorMsg">
                                                            <s:fielderror>
                                                                <s:param>nciDivisionProgramCode</s:param>
                                                            </s:fielderror>
                                                        </span>
                                                    </td>
                                                    <td>
                                                        <input type="button" id="grantbtnid" value="Add Grant" onclick="addGrant();" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">
                                        <div id="grantdiv">
                                            <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialViewGrant.jsp" %>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">
                                        <div id="grantadddiv">
                                            <%@ include file="/WEB-INF/jsp/nodecorate/addGrantForUpdate.jsp" %>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table class="form">
                                <tr>
                                    <th colspan="2"><fmt:message key="update.trial.statusDates"/></th>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_statusCode"><fmt:message key="update.trial.currentTrialStatus"/><span class="required">*</span></label>
                                    </td>
                                    <s:set name="statusCodeValues" value="@gov.nih.nci.pa.enums.StudyStatusCode@getDisplayNamesForAmend()" />
                                    <td>
                                        <s:select headerKey="" headerValue="--Select--" name="trialDTO.statusCode" list="#statusCodeValues"
                                                  value="trialDTO.statusCode" cssStyle="width:206px" onchange="displayTrialStatusDefinition('updateTrial_trialDTO_statusCode');" />
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.statusCode</s:param>
                                            </s:fielderror>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td class="info"><%@ include file="/WEB-INF/jsp/nodecorate/trialStatusDefinitions.jsp" %></td>
                                </tr>
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_reason"> <fmt:message key="update.trial.trialStatusReason"/></label>
                                    </td>
                                    <td>
                                        <s:textarea name="trialDTO.reason"  cols="50" rows="2" />
                                        <span class="info">Required for Administratively Complete ,Withdrawn and Temporarily Closed statuses only</span>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.reason</s:param>
                                            </s:fielderror>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_statusDate"><fmt:message key="update.trial.currentTrialStatusDate" /><span class="required">*</span></label>
                                    </td>
                                    <td class="value">
                                        <s:textfield name="trialDTO.statusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                        <a href="javascript:showCal('Cal1')">
                                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                        </a> (mm/dd/yyyy)
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.statusDate</s:param>
                                            </s:fielderror>
                                        </span>
                                    </td>
                                </tr>
                                <s:set name="dateTypeList" value="@gov.nih.nci.pa.enums.ActualAnticipatedTypeCode@getDisplayNames()" />
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_startDate"><fmt:message key="update.trial.trialStartDate" /><span class="required">*</span></label>
                                    </td>
                                    <td class="value">
                                        <s:textfield name="trialDTO.startDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                        <a href="javascript:showCal('Cal2')">
                                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                        </a> (mm/dd/yyyy)
                                        <s:radio name="trialDTO.startDateType" list="#dateTypeList" />
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.startDate</s:param>
                                            </s:fielderror>
                                        </span>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.startDateType</s:param>
                                            </s:fielderror>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td scope="row" class="label">
                                        <label for="updateTrial_completionDate"><fmt:message key="update.trial.primaryCompletionDate" /><span class="required">*</span></label>
                                    </td>
                                    <td class="value">
                                        <s:textfield name="trialDTO.completionDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                                        <a href="javascript:showCal('Cal3')">
                                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                        </a> (mm/dd/yyyy)
                                        <s:radio name="trialDTO.completionDateType" list="#dateTypeList" />
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.completionDate</s:param>
                                            </s:fielderror>
                                        </span>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>trialDTO.completionDateType</s:param>
                                            </s:fielderror>
                                        </span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <table class="data2">
                                <tr>
                                    <th colspan="2">FDA IND/IDE Information for applicable trials</th>
                                </tr>
                                <s:hidden name="indIdeUpdateDtosLen" id="indIdeUpdateDtosLen"/>
                                <tr>
                                    <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                       <fmt:message key="update.trial.indInstructionalText"/>
                                    </td>
                                </tr>
                                <tr>
                                  <td colspan="2" class="space">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">
                                        <%@ include file="/WEB-INF/jsp/nodecorate/indide.jsp" %>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="space">
                                        <div id="indidediv">
                                            <%@ include file="/WEB-INF/jsp/nodecorate/updateIdeIndIndicator.jsp" %>
                                        </div>
                                    </td>
                                </tr>
                                 <tr>
                                    <td colspan="2" class="space">
                                        <div id="indideadddiv">
                                            <%@ include file="/WEB-INF/jsp/nodecorate/addForUpdateIdeIndIndicator.jsp" %>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:if test="%{trialDTO.xmlRequired == true}">
                                <div id="regDiv" style="display:''">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInforamtion.jsp" %>
                                </div>
                            </s:if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:if test="participatingSitesList.size > 0" >
                                <table class="data2">
                                    <tr>
                                        <th colspan="2">Participating Sites</th>
                                    </tr>
                                    <tr> 
                                        <td>
                                            <table class="form">
                                                <tbody>
                                                    <tr>
                                                        <th>Site</th>
                                                        <th>Recruitment Status</th>
                                                        <th>Date</th>
                                                        <th>Program Code</th>
                                                    </tr>
                                                    <s:iterator id="participatingSitesList" value="participatingSitesList" status="psstats">
                                                    <tr>
                                                        <td>
                                                            <s:textarea  name="participatingSitesList[%{#psstats.index}].name" value="%{name}" readonly="true" cssStyle="width:400px;border: 1px solid #FFFFFF" rows="2"/>
                                                        </td>
                                                        <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                                        <td class="value">
                                                            <s:select headerKey="" headerValue="--Select--"
                                                                name="participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                                                list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                                                            <span class="formErrorMsg">
                                                                <s:fielderror>
                                                                    <s:param>participatingsite.recStatus<s:property value="%{#psstats.index}"/></s:param>
                                                                </s:fielderror>
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <s:textfield  name="participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}"/>
                                                            <span class="formErrorMsg">
                                                                <s:fielderror>
                                                                    <s:param>participatingsite.recStatusDate<s:property value="%{#psstats.index}"/></s:param>
                                                                </s:fielderror>
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <s:textfield  name="participatingSitesList[%{#psstats.index}].programCode" value="%{programCode}"/>
                                                            <s:hidden  name="participatingSitesList[%{#psstats.index}].id" value="%{id}"/>
                                                        </td>
                                                    </tr>
                                                    </s:iterator >
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </s:if>
                        </td>
                    </tr>        
                    <tr>
                        <td colspan="2" class="space">&nbsp;</td>
                    </tr>
                    <tr>
                        <td>
                            <s:if test="collaborators.size > 0" >
                                <table class="data2">
                                    <tr>
                                        <th colspan="2" >Collaborators</th>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table class="form">
                                                <tbody>
                                                    <tr>
                                                        <th>Collaborator</th>
                                                        <th>Functional Role</th>
                                                    </tr>
                                                    <s:iterator value="collaborators" id="collaborators" status="stat" >
                                                    <tr>
                                                        <td class="tdBoxed">
                                                            <s:hidden  name="collaborators[%{#stat.index}].id" value="%{id}" />
                                                            <s:textarea  name="collaborators[%{#stat.index}].name" value="%{name}" readonly="true" cssStyle="width:400px;border: 1px solid #FFFFFF" rows="2"/>
                                                        </td>
                                                        <s:set name="functionalCodeValues" value="@gov.nih.nci.pa.enums.StudySiteFunctionalCode@getCollaboratorDisplayNames()"/>
                                                        <td class="value" colspan="2">
                                                            <s:select headerKey="" headerValue="--Select--"
                                                                      name="collaborators[%{#stat.index}].functionalRole" list="#functionalCodeValues" value="%{functionalRole}"/>
                                                            <span class="formErrorMsg">
                                                                <s:fielderror>
                                                                    <s:param>collaborator.functionalCode<s:property value="%{#stat.index}"/></s:param>
                                                                </s:fielderror>
                                                            </span>
                                                        </td>
                                                    </tr>
                                                    </s:iterator>
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </s:if>
                        </td>
                    </tr>  
                </table>      
                <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                <p>
                    Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                </p>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="#" cssClass="btn" onclick="reviewProtocolUpdate()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                                <s:a href="#" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
