<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
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
            
            function setorgid(orgIdentifier, oname, p30grant) {
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
                var url = '/registry/protected/popupaddSummaryFourOrg.action';
                var params = { orgId: orgid, chosenName : chosenname };
                var div = document.getElementById('loadSummary4FundingSponsorField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = callAjaxPost(div, url, params);
                return false;
            }

            function deleteSummary4SponsorRow(rowid) {
                var  url = '/registry/protected/popupdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
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
            
            Event.observe(window, "load", setDisplayBasedOnTrialType);
            
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <h1><fmt:message key="submit.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="ctimport"/>        
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
                
                <s:hidden name="trialDTO.siteOrganizationIdentifier" id="trialDTO.siteOrganizationIdentifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                <s:hidden name="page" />
                <p>Register trial with NCI's Clinical Trials Reporting Program.  Required fields are marked by asterisks(<span class="required">*</span>). </p>
                <table class="form">
                    <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="trialDTO.leadOrganizationName" labelKey="submit.trial.leadOrganization" required="true">
                        <div id="loadOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.leadOrgTrialIdentifier" labelKey="submit.trial.leadOrgidentifier" required="true" tooltip="tooltip.trial_id">
                        <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier"  maxlength="30 " size="100"  cssStyle="width:200px" cssClass="charcounter" />
                        <span class="alert-danger">
                            <s:fielderror>
                                <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.siteOrganizationName" labelKey="submit.proprietary.trial.siteOrganization" required="true" tooltip="tooltip.submitting_organization_name">
                        <div id="loadSiteOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSiteOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.localSiteIdentifier" labelKey="submit.proprietary.trial.siteidentifier" required="true" tooltip="tooltip.submitting_organization_local_trial_identifier">
                        <s:textfield id="trialDTO.localSiteIdentifier" name="trialDTO.localSiteIdentifier"  maxlength="200" size="100"  cssStyle="width:200px"  />
                        <span class="alert-danger">
                            <s:fielderror>
                              <s:param>trialDTO.localSiteIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="trialDTO.nctIdentifier" labelKey="submit.trial.nctNumber" tooltip="tooltip.nct_number">
                        <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                        <span class="info">(Mandatory if Exists)</span>
                        <span class="alert-danger">
                            <s:fielderror cssStyle = "white-space:pre-line;">
                                <s:param>trialDTO.nctIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:titleRow titleKey="submit.trial.trialDetails"/>
                    <reg-web:valueRow labelFor="trialDTO.officialTitle" labelKey="submit.trial.title" required="true" tooltip="tooltip.title">
                        <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="charcounter"/>                        
                        <span class="alert-danger">
                            <s:fielderror>
                                <s:param>trialDTO.officialTitle</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <tr>
                        <td class="label-noinput" scope="row" >
                         <fmt:message key="submit.trial.type"/><span class="required">*</span>
                        </td>
                        <td class="value">
                            <input type="radio" name="trialDTO.trialType" value="Interventional"
                                ${trialDTO.trialType!='NonInterventional'?'checked=checked':''}
                                onclick="setDisplayBasedOnTrialType();" 
                                id="trialDTO.trialType.Interventional"> <label for ="trialDTO.trialType.Interventional">Interventional</label>
                            <input type="radio" name="trialDTO.trialType" value="NonInterventional"
                                ${trialDTO.trialType=='NonInterventional'?'checked=checked':''}
                                onclick="setDisplayBasedOnTrialType();" 
                                id="trialDTO.trialType.Noninterventional"><label for="trialDTO.trialType.Noninterventional"> Non-interventional</label>
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.trialType</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
					<tr class="non-interventional">        
					      <td  scope="row" class="label">                    
					         <label for="trialDTO.studySubtypeCode"><fmt:message key="submit.trial.studySubtypeCode"/><span class="required">*</span></label>                    
					      </td>
					      <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudySubtypeCode@getDisplayNames()" />
					      <td>
					          <s:select headerKey="" headerValue="--Select--" id ="trialDTO.studySubtypeCode" name="trialDTO.studySubtypeCode" list="#typeCodeValues"  cssStyle="width:206px" 
					                    value="trialDTO.studySubtypeCode"/>
					           <span class="alert-danger">
					              <s:fielderror>
					              <s:param>trialDTO.studySubtypeCode</s:param>
					             </s:fielderror>
					           </span>
					      </td>
					</tr>                    
                    <%@ include file="/WEB-INF/jsp/nodecorate/primaryPurposeOther.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phase.jsp" %>
                    <!-- include po person jsp -->
                    <reg-web:valueRow labelFor="trialDTO.sitePiName" labelKey="submit.proprietary.trial.siteInvestigator" required="true" tooltip="tooltip.site_pi">
                        <div id="loadPersField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSitePrincipalInvestigator.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <!--  summary4 information -->
                    <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="trialDTO.summaryFourFundingCategoryCode" labelKey="update.proprietary.trial.summary4FundingCategory" tooltip="tooltip.summary_4_funding_sponsor_type">
                        
                        <s:select headerKey="" headerValue="--Select--"
                                  id="trialDTO.summaryFourFundingCategoryCode"
                                  name="trialDTO.summaryFourFundingCategoryCode"
                                  list="#{'National':'National', 'Externally Peer-Reviewed':'Externally Peer-Reviewed','Institutional':'Institutional','Industrial':'Industrial/Other'}"
                                  cssStyle="width:206px" disabled="true" />
                         <span class="alert-danger">
                               <s:fielderror>
                                   <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                               </s:fielderror>
                         </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow id="trialDTO.summaryFourOrgNameTR" labelFor="trialDTO.summaryFourOrgName" labelKey="update.proprietary.trial.summary4Sponsor" 
                                      required="true" tooltip="tooltip.summary_4_funding_source">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    
                    <reg-web:valueRow labelFor="trialDTO.consortiaTrialCategoryCode" labelKey="update.proprietary.trial.consortiaTrialCategoryCode">                                               
                        <s:select headerKey="" headerValue="Yes"
                                  id="trialDTO.consortiaTrialCategoryCode"
                                  name="trialDTO.consortiaTrialCategoryCode"
                                  list="consortiaTrialCategoryValueMap"
                                  cssStyle="width:206px" />
                         <span class="alert-danger">
                               <s:fielderror>
                                   <s:param>trialDTO.consortiaTrialCategoryCode</s:param>
                               </s:fielderror>
                         </span>
                    </reg-web:valueRow>
                                        
                    <reg-web:valueRow labelFor="trialDTO.siteProgramCodeText" labelKey="submit.proprietary.trial.sitePrgCode" tooltip="tooltip.summary_4_site_program_code">
                        <s:textfield id="trialDTO.siteProgramCodeText" name="trialDTO.siteProgramCodeText"  maxlength="100" size="100"  cssStyle="width:200px" />
                        <span class="alert-danger">
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
                        <reg-web:valueRow labelFor="trialDTO.siteStatusCode" labelKey="submit.trial.siteRecruitmentStatus" required="true" tooltip="tooltip.site_recruitment_status">
                            <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()" />
                            <s:select id="trialDTO.siteStatusCode" headerKey="" headerValue="--Select--" name="trialDTO.siteStatusCode" list="#recruitmentStatusValues" value="trialDTO.siteStatusCode" />
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusCode</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="trialDTO.siteStatusDate" labelKey="submit.trial.siteRecruitmentStatusDate" required="true" tooltip="tooltip.site_recruitment_status_date">
                            <s:textfield id="trialDTO.siteStatusDate" name="trialDTO.siteStatusDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal1')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.siteStatusDate</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="trialDTO.dateOpenedforAccrual" labelKey="submit.proprietary.trial.dateOpenedforAccrual" tooltip="tooltip.date_opened_for_accrual">
                            <s:textfield id="trialDTO.dateOpenedforAccrual" name="trialDTO.dateOpenedforAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal2')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="alert-danger">
                                <s:fielderror>
                                    <s:param>trialDTO.dateOpenedforAccrual</s:param>
                                </s:fielderror>
                            </span>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelFor="trialDTO.dateClosedforAccrual" labelKey="submit.proprietary.trial.dateClosedforAccrual" tooltip="tooltip.date_closed_for_accrual">
                            <s:textfield id="trialDTO.dateClosedforAccrual" name="trialDTO.dateClosedforAccrual" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                            <a href="javascript:showCal('Cal3')">
                                <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                            </a> (mm/dd/yyyy)
                            <span class="info"><fmt:message key="error.proprietary.submit.dateOpenReq" /></span>
                            <span class="alert-danger">
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
