<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Update <fmt:message key="submit.proprietary.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <script type="text/javascript" src="${scriptPath}/js/subModalcommon.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/subModal.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <script type="text/javascript">
            var bla = <s:property value="trialDTO.participatingSitesList.size"/>;
            jQuery(function() {
                for (var i = 0; i < <s:property value="trialDTO.participatingSitesList.size"/>; i++) {
                    addCalendar("Cal1-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].recruitmentStatusDate", "updateProprietaryTrial");
                    addCalendar("Cal2-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].dateOpenedforAccrual", "updateProprietaryTrial");
                    addCalendar("Cal3-" + i, "Select Date", "trialDTO.participatingSitesList[" + i + "].dateClosedforAccrual", "updateProprietaryTrial");
                }
                setWidth(90, 1, 15, 1);
                setFormat("mm/dd/yyyy");
            });
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
            
            function lookup4loadSummary4Sponsor() {
                showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
            }

            function loadLeadOrgDiv() {
                $("trialDTO.leadOrganizationIdentifier").value = orgid;
                $('trialDTO.leadOrganizationName').value = chosenname;
            }
            
            function loadSummary4SponsorDiv() {
                $("trialDTO.summaryFourOrgName").value = chosenname;
                $('trialDTO.summaryFourOrgIdentifier').value = orgid;
            }

            function reviewProtocol () {
                showPopWin('${reviewProtocol}', 600, 200, '', 'Review Register Trial');
                submitFirstForm("review", "updateProprietaryTrialreview.action");
            }
            
            function cancelProtocol() {
                submitFirstForm("cancle", "updateProprietaryTrialcancel.action");
            }

            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
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
        <c:set var="topic" scope="request" value="updatetrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="updateProprietaryTrial" method="POST" enctype="multipart/form-data">
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
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                <c:if test="${not empty trialDTO.summaryFourFundingCategoryCode}">
                    <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
                </c:if>
                <s:hidden name="page" />
                <table class="form">
                    <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelKey="view.trial.identifier">
                        <s:property value="trialDTO.assignedIdentifier"/>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_selectedLeadOrg_name_part_0__value" labelKey="view.trial.leadOrganization" required="true">
                        <div id="loadOrgField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_participationWebDTO_localProtocolIdentifier" labelKey="submit.trial.leadOrgidentifier" required="true">
                        <s:textfield name="trialDTO.leadOrgTrialIdentifier" maxlength="200" size="100" cssStyle="width:200px"  />
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_participationWebDTO_nctNumber" labelKey="submit.trial.nctNumber">
                        <s:textfield name="trialDTO.nctIdentifier" maxlength="200" size="100" cssStyle="width:200px" />
                        <span class="info">(Mandatory if Exists)</span>
                        <span class="formErrorMsg">
                            <s:fielderror>
                                <s:param>trialDTO.nctIdentifier</s:param>
                            </s:fielderror>
                        </span>
                    </reg-web:valueRow>
                    <reg-web:titleRow titleKey="submit.trial.trialDetails"/>
                    <reg-web:valueRow labelFor="submitTrial_protocolWebDTO_trialTitle" labelKey="submit.trial.title" required="true">
                        <s:textarea name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="charcounter"/>
                        
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
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <!--  summary4 information -->
                    <reg-web:titleRow titleKey="update.proprietary.trial.summary4Info"/>
                    <reg-web:spaceRow/>
                    <reg-web:valueRow labelFor="submitTrial_summary4FundingCategory" labelKey="update.proprietary.trial.summary4FundingCategory">
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
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
                    </reg-web:valueRow>
                    <reg-web:valueRow labelFor="submitTrial_selectedSummary4Sponsor_name_part_0__value" labelKey="update.proprietary.trial.summary4Sponsor">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                        </div>
                    </reg-web:valueRow>
                    <reg-web:spaceRow/>
                    <reg-web:spaceRow/>
                    <table class="data2">
                         <tr>
                            <th colspan="2">Participating Sites</th>
                         </tr>
                         <tr> 
                            <td>
                                <table class="form">
                                    <tbody>
                                        <tr>
                                            <th>Organization/Investigator</th>
                                            <th>Local Trial<br/> Identifier<span class="required">*</span></th>
                                            <th>Program Code</th>
                                            <th>Current Site<br/> Recruitment Status<span class="required">*</span></th>
                                            <th>Current Site<br/> Recruitment <br/>Status Date<span class="required">*</span><br/>(mm/dd/yyyy) </th>
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
                                            <s:set name="recruitmentStatusValues" value="@gov.nih.nci.pa.enums.RecruitmentStatusCode@getDisplayNames()"  />
                                            <td class="value">
                                                <s:select headerKey="" headerValue="--Select--"
                                                          name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatus" value="%{recruitmentStatus}"
                                                          list="#recruitmentStatusValues" cssStyle="text-align:left;"/>
                                            </td>
                                            <td nowrap="nowrap">
                                                <s:textfield name="trialDTO.participatingSitesList[%{#psstats.index}].recruitmentStatusDate" value="%{recruitmentStatusDate}" size="12"/>
                                                <a href="javascript:showCal('Cal1-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                            <td nowrap="nowrap">
                                                <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateOpenedforAccrual" value="%{dateOpenedforAccrual}" size="12"/>
                                                <a href="javascript:showCal('Cal2-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                            <td nowrap="nowrap">
                                                <s:textfield  name="trialDTO.participatingSitesList[%{#psstats.index}].dateClosedforAccrual" value="%{dateClosedforAccrual}" size="12"/>
                                                <a href="javascript:showCal('Cal3-<s:property value="%{#psstats.index}"/>')"> 
                                                    <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
                                                </a>
                                            </td>
                                        </tr>
                                        </s:iterator >
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </table>
                </table>
                
                <div id="existingDocsDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateProprietaryTrialDocumentsSection.jsp" %>
                </div>
                
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
