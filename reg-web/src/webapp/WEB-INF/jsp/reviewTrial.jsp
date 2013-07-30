<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="review.trial.page.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
        <script type="text/javascript" language="javascript">
        //<![CDATA[
            function submitTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action = "amendTrialamend.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Amend Trial');
                } else {
                    document.forms[0].action = "submitTrialcreate.action";
                    document.forms[0].submit();
                    showPopWin('${submitProtocol}', 600, 200, '', 'Submit Register Trial');
                }
            }
            
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action = "amendTrialedit.action";
                    document.forms[0].submit();
                } else {
                    document.forms[0].action = "submitTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol () {
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=750,height=600,left=100,top=25";
                var sWinHTML = document.getElementById('contentprint').innerHTML;
                var winprint = window.open("", "", sOption);
                    winprint.document.open();
                    winprint.document.write('<html><body>');
                    winprint.document.write(sWinHTML);
                    winprint.document.write('</body></html>');
                    winprint.document.close();
                    winprint.focus();
            }
        //]]>
        </script>
    </head>
    <body>
        <div id="contentwide">
            <h1><fmt:message key="review.trial.view.page.title" /></h1>
            <c:set var="topic" scope="request" value="edittrial"/>
            <div class="box">
                <s:form id="reviewTrialForm">
                    <s:token/>
                    <s:actionerror/>
                    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                    <s:hidden name="pageFrom" id="pageFrom"/>
                    <c:if test="${requestScope.protocolId != null && requestScope.partialSubmission != null && requestScope.partialSubmission == 'submit'}">
                        <div class="confirm_msg">
                            <strong>The trial draft has been successfully saved and assigned the Identifier ${requestScope.protocolId}</strong>
                        </div>
                    </c:if>
                    <div id="contentprint">
                        <table class="form">
                            <reg-web:titleRow titleKey="view.trial.trialIDs"/>
                            
                            <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                                <reg-web:valueRow labelKey="view.trial.nciAccessionNumber" strong="true" noLabelTag="true">
                                    <c:out value="${trialDTO.assignedIdentifier}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${requestScope.protocolId != null}" >
                                <reg-web:valueRow labelKey="view.trial.assignedIdentifier" strong="true" noLabelTag="true">
                                    <c:out value="${requestScope.protocolId}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRow>
                            <c:if test="${trialDTO.nctIdentifier != null}">
                                <reg-web:valueRow labelKey="view.trial.nctNumber" noLabelTag="true">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                                <c:if test="${trialDTO.ctepIdentifier != null}">
                                    <reg-web:valueRow labelKey="view.trial.ctepIdentifier" noLabelTag="true">
                                        <c:out value="${trialDTO.ctepIdentifier}"/>
                                    </reg-web:valueRow>
                                </c:if>
                                <c:if test="${trialDTO.dcpIdentifier != null}">
                                    <reg-web:valueRow labelKey="view.trial.dcpIdentifier" noLabelTag="true">
                                        <c:out value="${trialDTO.dcpIdentifier}"/>
                                    </reg-web:valueRow>
                                </c:if>
                                <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIds.jsp" %>
                                <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                                    <reg-web:titleRow titleKey="trial.amendDetails"/>
                                    <reg-web:valueRow labelKey="view.trial.amendmentNumber" noLabelTag="true">
                                        <c:out value="${trialDTO.localAmendmentNumber}"/>
                                    </reg-web:valueRow>
                                    <reg-web:valueRow labelKey="view.trial.amendmentDate" noLabelTag="true">
                                        <c:out value="${trialDTO.amendmentDate}"/>
                                    </reg-web:valueRow>
                                </c:if>
                            </c:if>
                            <reg-web:titleRow titleKey="view.trial.trialDetails"/>
                            <reg-web:valueRow labelKey="view.trial.title" noLabelTag="true">
                                <c:out value="${trialDTO.officialTitle}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.phase" noLabelTag="true">
                                <c:out value="${trialDTO.phaseCode}"/>
                            </reg-web:valueRow>
                            <c:if test="${trialDTO.phaseAdditionalQualifier!= '' && trialDTO.phaseCode=='NA'}">
                                <reg-web:valueRow labelKey="view.trial.otherPhaseText" noLabelTag="true">
                                    <c:out value="${trialDTO.phaseAdditionalQualifier=='Pilot'?'Yes':'No'}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <reg-web:valueRow labelKey="view.trial.type" noLabelTag="true">
                                <c:out value="${trialDTO.trialType}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.primaryPurpose" noLabelTag="true">
                                <c:out value="${trialDTO.primaryPurposeCode}"/>
                            </reg-web:valueRow>
                            <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                                <reg-web:valueRow labelKey="view.trial.otherPurposeText" noLabelTag="true">
                                    <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${trialDTO.trialType != 'NonInterventional'}">
	                            <reg-web:valueRow labelKey="view.trial.secondaryPurpose" noLabelTag="true">
	                                <c:out value="${trialDTO.secondaryPurposeAsReadableString}"/>
	                            </reg-web:valueRow>   
	                            <c:if test="${trialDTO.secondaryPurposeAsReadableString == 'Other'}">
	                                <reg-web:valueRow labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
	                                    <c:out value="${trialDTO.secondaryPurposeOtherText}"/>
	                                </reg-web:valueRow>
                                </c:if> 
                            </c:if>
                             
                            <c:if test="${trialDTO.trialType == 'NonInterventional'}">
                                <reg-web:valueRow labelKey="submit.trial.studySubtypeCode" noLabelTag="true">
                                    <c:out value="${trialDTO.studySubtypeCode}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="submit.trial.studyModelCode" noLabelTag="true">
                                    <c:out value="${trialDTO.studyModelCode}"/>
                                </reg-web:valueRow>
                                <c:if test="${trialDTO.studyModelCode == 'Other'}">
                                    <reg-web:valueRow labelKey="submit.trial.studyModelOtherText" noLabelTag="true">
                                        <c:out value="${trialDTO.studyModelOtherText}"/>
                                    </reg-web:valueRow>                                
                                </c:if>                                
                                <reg-web:valueRow labelKey="submit.trial.timePerspectiveCode" noLabelTag="true">
                                    <c:out value="${trialDTO.timePerspectiveCode}"/>
                                </reg-web:valueRow>             
                                <c:if test="${trialDTO.timePerspectiveCode == 'Other'}">
                                    <reg-web:valueRow labelKey="submit.trial.timePerspectiveOtherText" noLabelTag="true">
                                        <c:out value="${trialDTO.timePerspectiveOtherText}"/>
                                    </reg-web:valueRow>                                
                                </c:if>
                            </c:if>                                                                                
                            <reg-web:spaceRow/>
                            <reg-web:titleRow titleKey="view.trial.leadOrgInvestigator"/>
                            <reg-web:valueRow labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRow>
                            <c:if test="${trialDTO.propritaryTrialIndicator == 'Yes'}">
                                <reg-web:valueRow labelKey="submit.proprietary.trial.siteOrganization" noLabelTag="true">
                                    <c:out value="${trialDTO.siteOrganizationName}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="submit.proprietary.trial.siteidentifier" noLabelTag="true">
                                    <c:out value="${trialDTO.localSiteIdentifier}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                    <c:out value="${trialDTO.sitePiName}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                                <reg-web:valueRow labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                    <c:out value="${trialDTO.piName}"/>
                                </reg-web:valueRow>            
                                <reg-web:spaceRow/>
                                <c:if test="${trialDTO.sponsorName != null && trialDTO.xmlRequired == true}">
                                    <reg-web:titleRow titleKey="view.trial.sponsorResParty"/>
                                    <reg-web:valueRow labelKey="view.trial.sponsor" noLabelTag="true">
                                        <c:out value="${trialDTO.sponsorName}"/>
                                    </reg-web:valueRow>
                                    <reg-web:valueRow labelKey="view.trial.respParty" noLabelTag="true">
                                        <c:out value="${func:capitalize(trialDTO.responsiblePartyType)}"/>
                                    </reg-web:valueRow>
                                    <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
                                        <reg-web:valueRow labelKey="view.trial.respPartyContact" noLabelTag="true">
                                            <c:out value="${trialDTO.responsiblePersonName}"/>
                                        </reg-web:valueRow>
                                    </c:if>
                                    <c:if test="${fn:trim(trialDTO.responsibleGenericContactName) != ''}">
                                        <reg-web:valueRow labelKey="view.trial.respPartyContact" noLabelTag="true">
                                            <c:out value="${trialDTO.responsibleGenericContactName}"/>
                                        </reg-web:valueRow>
                                    </c:if>
                                    <reg-web:valueRow labelKey="view.trial.respPartyEmailAddr" noLabelTag="true">
                                        <c:out value="${trialDTO.contactEmail}"/>
                                    </reg-web:valueRow>
                                    <reg-web:valueRow labelKey="view.trial.respPartyPhone" noLabelTag="true">
                                        <c:out value="${trialDTO.contactPhone}"/>
                                        <c:if test="${not empty trialDTO.contactPhoneExtn}">
                                        Ext. <c:out value="${trialDTO.contactPhoneExtn}"/>
                                        </c:if>
                                    </reg-web:valueRow>
                                    <reg-web:spaceRow/>
                                </c:if>
                            </c:if>
                            <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                <reg-web:valueRow labelKey="view.trial.FundingCategory" noLabelTag="true">
                                    <c:out value="${trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                    <c:forEach items="${trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                        <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                    </c:forEach>
                                </reg-web:valueRow>
                                <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'Yes'}">
	                                <reg-web:valueRow labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
	                                    <c:out value="${empty trialDTO.consortiaTrialCategoryCode?'Yes':'No - '}"/>
	                                    <c:out value="${trialDTO.consortiaTrialCategoryCode}"/>
	                                </reg-web:valueRow>
                                </c:if>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
                                <c:if test="${trialDTO.programCodeText != ''}">
                                    <c:if test="${empty trialDTO.summaryFourOrgIdentifiers}">
                                        <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                    </c:if>
                                    <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
                                        <c:out value="${trialDTO.programCodeText}"/>
                                    </reg-web:valueRow>
                                    <reg-web:spaceRow/>
                                </c:if>
                                <%@ include file="/WEB-INF/jsp/nodecorate/viewStatusSection.jsp" %>
                            </c:if>
                            <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'Yes'}">
                                <c:if test="${fn:trim(trialDTO.siteProgramCodeText) != ''}">
                                    <c:if test="${empty trialDTO.summaryFourOrgIdentifiers}">
                                        <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                    </c:if>
                                    <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
                                        <c:out value="${trialDTO.siteProgramCodeText}"/>
                                    </reg-web:valueRow>
                                    <reg-web:spaceRow/>
                                </c:if>
                                <reg-web:titleRow titleKey="view.trial.statusDates"/>
                                <reg-web:valueRow labelKey="view.trial.siteRecruitmentStatus" noLabelTag="true">
                                    <c:out value="${trialDTO.siteStatusCode}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.siteRecruitmentStatusDate" noLabelTag="true">
                                    <c:out value="${trialDTO.siteStatusDate}"/>
                                </reg-web:valueRow>
                                <c:if test="${fn:trim(trialDTO.dateOpenedforAccrual) != ''}">
                                    <reg-web:valueRow labelKey="submit.proprietary.trial.dateOpenedforAccrual" noLabelTag="true">
                                        <c:out value="${trialDTO.dateOpenedforAccrual}"/>
                                    </reg-web:valueRow>
                                </c:if>
                                <c:if test="${fn:trim(trialDTO.dateClosedforAccrual) != ''}">
                                    <reg-web:valueRow labelKey="submit.proprietary.trial.dateClosedforAccrual" noLabelTag="true">
                                        <c:out value="${trialDTO.dateClosedforAccrual}"/>
                                    </reg-web:valueRow>
                                </c:if>
                            </c:if>
                        </table>
                        <c:if test="${fn:length(trialDTO.indIdeDtos) > 0}">
                            <div class="box">
                                <h3>FDA IND/IDE Information for applicable trials</h3>
                                <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
                            </div>
                        </c:if>
                        <c:if test="${trialDTO.fundingDtos != null}">
                            <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                        </c:if>
                        <c:if test="${fn:trim(trialDTO.propritaryTrialIndicator) == 'No' && trialDTO.xmlRequired == true}">
                            <table class="form">
                                <reg-web:titleRow titleKey="regulatory.title"/>
                                <reg-web:spaceRow/>
                                <!--  Trial Oversight Authority Country -->
                                <reg-web:valueRow labelKey="regulatory.oversight.country.name" noLabelTag="true">
                                    <c:out value="${trialDTO.trialOversgtAuthCountryName }"/>
                                </reg-web:valueRow>
                                <!--  Trial Oversight Authority Organization Name -->
                                <reg-web:valueRow labelKey="regulatory.oversight.auth.name" noLabelTag="true">
                                    <c:out value="${trialDTO.trialOversgtAuthOrgName }"/>
                                </reg-web:valueRow>
                                <!--   FDA Regulated Intervention Indicator-->
                                <reg-web:valueRow labelKey="regulatory.FDA.regulated.interv.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.fdaRegulatoryInformationIndicator}" /> 
                                </reg-web:valueRow>
                                <!--   Section 801 Indicator-->
                                <reg-web:valueRow id="sec801row" labelKey="regulatory.section801.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.section801Indicator}" />
                                </reg-web:valueRow>
                                <!--   Delayed Posting Indicator-->
                                <reg-web:valueRow id="delpostindrow" labelKey="regulatory.delayed.posting.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.delayedPostingIndicator}" />
                                </reg-web:valueRow>
                                <!--   Data Monitoring Committee Appointed Indicator -->
                                <reg-web:valueRow id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind" noLabelTag="true">
                                    <c:out value="${trialDTO.dataMonitoringCommitteeAppointedIndicator}" />
                                </reg-web:valueRow>
                            </table>
                        </c:if>
                        <c:if test="${fn:length(trialDTO.docDtos) >0}">
                            <div class="box">
                                <display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                                name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                    <display:column titleKey="search.trial.view.documentTypeCode" property="typeCode"   sortable="true" headerClass="sortable"/>
                                    <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>"
                                </display:table>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${requestScope.partialSubmission == null}">
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <p align="center" class="info">
                                   Please verify ALL the trial information you provided on this screen before clicking the <b>Submit</b> button below.
                                   <br>Once you submit the trial you will not be able to modify the information.
                                </p>
                                <ul class="btnrow">
                                    <li><a href="javascript:void(0)" class="btn" onclick="editTrial();"><span class="btn_img"> <span class="edit">Edit</span></span></a></li>
                                    <li><a href="javascript:void(0)" class="btn" onclick="submitTrial();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
                                    <li><a href="javascript:void(0)"  class="btn" onclick="printProtocol();"><span class="btn_img"><span class="print">Print</span></span></a></li>
                                </ul>
                            </del>
                        </div>
                    </c:if>
                </s:form>
            </div>
        </div>
    </body>
</html>
