<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <link href="${pageContext.request.contextPath}/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
        <link href="${pageContext.request.contextPath}/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <!-- /po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
            addCalendar("Cal1", "Select Date", "trialDTO.siteStatusDate", "submitProprietaryTrial");
            addCalendar("Cal2", "Select Date", "trialDTO.dateOpenedforAccrual", "submitProprietaryTrial");
            addCalendar("Cal3", "Select Date", "trialDTO.dateClosedforAccrual", "submitProprietaryTrial");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        </script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
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
            }
            
            function lookup4loadleadorg() {
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

            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
            }
            
            function loadSiteOrgDiv() {
                $("trialDTO.siteOrganizationIdentifier").value = orgid;
                $('trialDTO.siteOrganizationName').value = chosenname;
            }
            
            function loadLeadPersDiv() {
                $("trialDTO.sitePiIdentifier").value = persid;
                $('trialDTO.sitePiName').value = chosenname;
            }
            
            function loadSummary4SponsorDiv() {
                $("trialDTO.summaryFourOrgName").value = chosenname;
                $('trialDTO.summaryFourOrgIdentifier').value = orgid;
            }

            function reviewProtocol () {
                submitFirstForm("review", "submitProprietaryTrialreview.action");
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol() {
                submitFirstForm("cancle", "submitProprietaryTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
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
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber
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
            
            function resetGrantRow() {
                $('fundingMechanismCode').value = '';
                $('nihInstitutionCode').value = '';
                $('serialNumber').value = '';
                $('nciDivisionProgramCode').value = '';
            }
            
            function deleteIndIde(rowid) {
                var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIde.action';
                var params = { uuid: rowid };
                var div = $('indidediv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function addIndIde(indIde, number, grantor, holderType, programCode, expandedAccess, expandedAccessType) {
                var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicator.action';
                var params = {
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
            
            function partialSave() {
                submitFirstForm(null, "submitProprietaryTrialpartialSave.action");
                showPopWin('${partialSave}', 600, 200, '', 'Saving Draft');
            }
            
            function toggledisplay (it, box) {
                var vis = (box.checked) ? "block" : "none";
                $(it).style.display = vis;
            }
            
            function toggledisplay2 (it) {
                var vis = $(it).style.display;
                $(it).style.display = (vis == "block") ? "none" : "block";
            }
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <h1><fmt:message key="submit.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="submittrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="submitProprietaryTrial" method="POST" enctype="multipart/form-data">
                <s:token/>
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
                <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                <s:hidden name="page" />
                <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
                <table class="form">
                    <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="submitTrial_selectedLeadOrg_name_part_0__value" labelKey="submit.trial.leadOrganization" required="true">
                        <div id="loadOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_participationWebDTO_localProtocolIdentifier" labelKey="submit.trial.leadOrgidentifier" required="true">
                        <s:textfield name="trialDTO.leadOrgTrialIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_selectedLeadOrg_name_part_0__value" labelKey="submit.proprietary.trial.siteOrganization" required="true">
                        <div id="loadSiteOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSiteOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_participationWebDTO_localProtocolIdentifier" labelKey="submit.proprietary.trial.siteidentifier" required="true">
                        <s:textfield name="trialDTO.localSiteIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                        <span class="formErrorMsg">
                            <s:fielderror>
                              <s:param>trialDTO.localSiteIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_participationWebDTO_nctNumber" labelKey="submit.trial.nctNumber" tooltip="tooltip.nct_number">
                        <s:textfield name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                        <span class="info">(Mandatory if Exists)</span>
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.nctIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:titleRow titleKey="submit.trial.trialDetails"/>
                    <reg-web:valueRow labelFor="submitTrial_protocolWebDTO_trialTitle" labelKey="submit.trial.title" required="true" tooltip="tooltip.title">
                        <s:textarea name="trialDTO.officialTitle"  cols="75" rows="4" />
                        <span class="info">Max 4000 characters</span>
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.officialTitle</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialType" labelKey="submit.trial.type" required="true">
                        <input type="radio" name="trialDTO.trialType" value="Interventional" checked="checked"> Interventional
                        <input type="radio" name="trialDTO.trialType" value="Observational" disabled="disabled"> Observational
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.trialType</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
                    <!-- include po person jsp -->
                    <reg-web:valueRow labelFor="submitTrial_poLeadPiFullName" labelKey="submit.proprietary.trial.siteInvestigator" required="true" tooltip="tooltip.pi">
                        <div id="loadPersField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSitePrincipalInvestigator.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <!--  summary4 information -->
                    <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="submitTrial_summary4FundingCategory" labelKey="update.proprietary.trial.summary4FundingCategory" tooltip="tooltip.summary_4_funding_sponsor_type">
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                        <s:select headerKey="" headerValue="--Select--"
                                  name="trialDTO.summaryFourFundingCategoryCode"
                                  list="#summaryFourFundingCategoryCodeValues"
                                  cssStyle="width:206px" disabled="true" />
                         <span class="formErrorMsg">
                               <s:fielderror>
                                   <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                               </s:fielderror>
                         </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_selectedSummary4Sponsor_name_part_0__value" labelKey="update.proprietary.trial.summary4Sponsor" 
                                      required="true" tooltip="tooltip.summary_4_funding_source">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="siteProgramCode" labelKey="submit.proprietary.trial.sitePrgCode" tooltip="tooltip.summary_4_program_code">
                        <s:textfield name="trialDTO.siteProgramCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.programCodeText</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <table class="form">
                        <reg-web:titleRow titleKey="submit.proprietary.trial.statusDate"/>
                        <reg-web:spaceRow/>
                        <reg-web:valueRow labelFor="submitTrial_overallStatusWebDTO_statusCode" labelKey="submit.trial.siteRecruitmentStatus" required="true">
                            <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                            <s:select headerKey="" headerValue="--Select--" name="trialDTO.siteStatusCode" list="#recruitmentStatusValues" value="trialDTO.siteStatusCode" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusCode</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="submitTrial_overallStatusWebDTO_statusDate" labelKey="submit.trial.siteRecruitmentStatusDate" required="true">
                            <s:textfield name="trialDTO.siteStatusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusDate</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="submitTrial_protocolWebDTO_startDate" labelKey="submit.proprietary.trial.dateOpenedforAccrual">
                            <s:textfield name="trialDTO.dateOpenedforAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal2')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>trialDTO.dateOpenedforAccrual</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="submitTrial_protocolWebDTO_dateClosedforAccrual" labelKey="submit.proprietary.trial.dateClosedforAccrual">
                            <s:textfield name="trialDTO.dateClosedforAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal3')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="info"><fmt:message key="error.proprietary.submit.dateOpenReq" /></span>
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>trialDTO.dateClosedforAccrual</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                    </table>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
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
                                <s:a href="#" cssClass="btn" onclick="partialSave()"><span class="btn_img"><span class="save">Save as Draft</span></span></s:a>
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
