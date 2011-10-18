<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="view.trial.page.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript">
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            
            function handleBackAction(){
             document.forms[0].action = "searchTrialquery.action";
             document.forms[0].submit();
            }
        </script>
    </head>

    <body onload="setFocusToFirstControl();">
        <div id="contentwide">
            <h1><fmt:message key="search.trial.view.page.title" /></h1>
            <c:set var="topic" scope="request" value="viewresult"/>
            <div class="box">
                <c:if test="${param.trialAction == 'submit'}">
                    <div class="confirm_msg">
                        <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.trialDTO.assignedIdentifier}</strong>
                    </div>
                </c:if>
                <c:if test="${param.trialAction == 'amend'}">
                    <div class="confirm_msg">
                        <strong>The amendment to trial with the NCI Identifier ${requestScope.trialDTO.assignedIdentifier} was successfully submitted.</strong>
                    </div>
                </c:if>
                <c:if test="${param.trialAction == 'update'}">
                    <div class="confirm_msg">
                        <strong>
                            The trial update with the NCI Identifier
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                ${requestScope.trialDTO.assignedIdentifier}
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                ${requestScope.assignedIdentifier}
                            </c:if>
                           was successfully submitted.
                       </strong>
                    </div>
                </c:if>
                <s:form > 
                    <s:actionerror/>
                    <s:hidden name="criteria.identifierType" />
                    <s:hidden name="criteria.identifier" />
                    <s:hidden name="criteria.officialTitle" />
                    <s:hidden name="criteria.organizationId" />
                    <s:hidden name="criteria.participatingSiteId" />
                    <s:hidden name="criteria.phaseCode" />
                    <s:hidden name="criteria.primaryPurposeCode" />
                    <s:hidden name="criteria.organizationType" />
                    <s:hidden name="criteria.myTrialsOnly" />
                    <s:hidden name="criteria.principalInvestigatorId" />
                    <table class="form">
                        <reg-web:titleRow titleKey="view.trial.trialIDs"/>
                        <reg-web:valueRow labelKey="view.trial.identifier" strong="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.assignedIdentifier}"/>
                            </c:if>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.leadOrgTrialIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.leadOrgTrialIdentifier}"/>
                            </c:if>
                        </reg-web:valueRow>
                        <c:if test="${requestScope.trialDTO.nctIdentifier != null}">
                            <reg-web:valueRow labelKey="view.trial.nctNumber">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.nctIdentifier }"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                            || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.nctIdentifier }"/>
                                </c:if>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.dcpIdentifier != null}">
                            <reg-web:valueRow labelKey="submit.trial.dcpIdentifier">
                                <c:out value="${requestScope.trialDTO.dcpIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.ctepIdentifier != null}">
                            <reg-web:valueRow labelKey="submit.trial.ctepIdentifier">
                                <c:out value="${requestScope.trialDTO.ctepIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.amendmentDate.value != null}">
                            <reg-web:titleRow titleKey="trial.amendDetails"/>
                            <reg-web:spaceRow/>
                            <reg-web:valueRow labelKey="submit.trial.amendmentNumber">
                                <c:if test="${requestScope.trialSummary.amendmentNumber.value != null}">
                                    <c:out value="${requestScope.trialSummary.amendmentNumber.value}"/>
                                </c:if>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="submit.trial.amendmentDate">
                                <fmt:formatDate value="${requestScope.trialSummary.amendmentDate.value}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.secondaryIdentifierList != null && fn:length(requestScope.trialDTO.secondaryIdentifierList) > 0}">
                            <reg-web:titleRow titleKey="view.trial.otherIdentifiers"/>
                            <s:set name="records" value="requestScope.trialDTO.secondaryIdentifierList" scope="request"/>
                            <tr>
                                <td colspan="2">
                                    <ul>
                                        <c:forEach var="item" items="${requestScope.trialDTO.secondaryIdentifierList}">
                                            <li><c:out value="${item.extension}"/></li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:if>
                        <reg-web:titleRow titleKey="view.trial.trialDetails"/>
                        <reg-web:valueRow labelKey="view.trial.title">
                            <c:out value="${requestScope.trialSummary.officialTitle.value}"/>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelKey="view.trial.phase">
                            <c:out value="${requestScope.trialSummary.phaseCode.code }"/>
                        </reg-web:valueRow>
                        <c:if test="${requestScope.trialDTO.phaseAdditionalQualifier != null }">
                            <reg-web:valueRow labelKey="view.trial.otherPhaseText">
                                <c:out value="${requestScope.trialDTO.phaseAdditionalQualifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test= "${requestScope.trialDTO.trialType != null} ">
                            <reg-web:valueRow labelKey="view.trial.type">
                                <c:out value="${requestScope.trialDTO.trialType}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <reg-web:valueRow labelKey="view.trial.primaryPurpose">
                            <c:out value="${requestScope.trialSummary.primaryPurposeCode.code}"/>
                        </reg-web:valueRow>
                        <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                            <reg-web:valueRow labelKey="view.trial.otherPurposeText">
                                <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <reg-web:spaceRow/>
                        <tr>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <th colspan="2"><fmt:message key="view.trial.leadOrgInvestigator"/></th>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                        && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <th colspan="2"><fmt:message key="view.trial.leadOrganization"/></th>
                            </c:if>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <label for="Lead Organization">
                                    <fmt:message key="view.trial.leadOrganization"/>
                                </label>
                            </td>
                            <td class="value">
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                            && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                    <c:out value="${requestScope.leadOrganizationName}"/>
                                </c:if>
                                <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                            && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                    <c:out value="${requestScope.trialDTO.leadOrganizationName}"/>
                                </c:if>
                            </td>
                        </tr>
                        <c:if test="${(requestScope.trialSummary.proprietaryTrialIndicator == null
                                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false')}">
                            <reg-web:valueRow labelKey="view.trial.principalInvestigator">
                                <c:out value="${requestScope.trialDTO.piName}"/>
                            </reg-web:valueRow>        
                        </c:if>
                        <reg-web:spaceRow/>
                        <c:if test="${requestScope.sponsor != null}">
                            <reg-web:titleRow titleKey="view.trial.sponsorResParty"/>
                            <reg-web:valueRow labelKey="view.trial.sponsor">
                                <c:out value="${requestScope.sponsor}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.respParty">
                                <c:out value="${requestScope.respParty}"/>
                            </reg-web:valueRow>
                            <c:if test="${requestScope.respPartyContact != null}">
                                <reg-web:valueRow labelKey="view.trial.respPartyContact">
                                    <c:out value="${requestScope.respPartyContact}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <reg-web:valueRow labelKey="view.trial.respPartyEmailAddr">
                                <c:out value="${requestScope.respPartyEmailAddr}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.respPartyPhone">
                                <c:out value="${requestScope.respPartyPhone}"/>
                            </reg-web:valueRow>
                            <reg-web:spaceRow/>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'
                                    && requestScope.summaryFourOrgName != null}">
                            <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            <reg-web:valueRow labelKey="view.trial.FundingCategory">
                                <c:out value="${requestScope.summaryFourFundingCategoryCode}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.FundingSponsor">
                                <c:out value="${requestScope.summaryFourOrgName}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'
                                    && requestScope.trialDTO.summaryFourOrgName != null}">
                            <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            <reg-web:valueRow labelKey="view.trial.FundingCategory">
                                <c:out value="${requestScope.trialDTO.summaryFourFundingCategoryCode}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.FundingSponsor">
                                <c:out value="${requestScope.trialDTO.summaryFourOrgName}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.programCodeText.value != null}">
                            <c:if test="${requestScope.trialDTO.summaryFourOrgName == null}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            </c:if>
                            <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode">
                                <c:out value="${requestScope.trialSummary.programCodeText.value}"/>
                            </reg-web:valueRow>
                            <reg-web:spaceRow/>
                        </c:if>
                        <c:if test="${requestScope.participatingSitesList != null && fn:length(requestScope.participatingSitesList) > 0}">
                            <reg-web:titleRow titleKey="view.trial.participatingSites"/>
                            <tr>
                                <td colspan="2">
                                    <div class="box">
                                        <display:table class="data" sort="list"  uid="row"  name="${requestScope.participatingSitesList}" >
                                            <display:column escapeXml="true" title="Organization Name" property="name"  headerClass="sortable"/>
                                            <display:column escapeXml="true" title="Site Principal Investigator" property="investigator"  headerClass="sortable"/>
                                            <display:column escapeXml="true" title="Local Trial<br/> Identifier" property="siteLocalTrialIdentifier"  headerClass="sortable"/>
                                            <display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable"/>
                                            <display:column title="Current Site<br/> Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                            <display:column title="Current Site<br/> Recruitment Status Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                                            <display:column title="Date Opened <br/>for Accrual" property="dateOpenedforAccrual"  headerClass="sortable"/>
                                            <display:column title="Date Closed <br/>for Accrual" property="dateClosedforAccrual"  headerClass="sortable"/>
                                        </display:table>
                                    </div>
                                </td>
                            </tr>       
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                            <%@ include file="/WEB-INF/jsp/nodecorate/viewStatusSection.jsp" %>
                        </c:if>
                    </table>
                    <c:if test="${requestScope.studyIndIde != null}">
                        <div class="box">
                            <h3>FDA IND/IDE Information for applicable trials</h3>
                            <jsp:include page="/WEB-INF/jsp/searchTrialViewIndIde.jsp"/>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.trialFundingList != null}">
                        <div class="box">
                            <h3>NIH Grant Information (for NIH funded Trials)</h3>
                            <jsp:include page="/WEB-INF/jsp/searchTrialViewGrants.jsp"/>
                        </div>
                    </c:if>
                    <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                || fn:trim(requestScope.trialSummary.proprietaryTrialIndicator.value) == 'false'
                                && requestScope.trialDTO.xmlRequired == true}">
                        <table class="form">
                            <reg-web:titleRow titleKey="regulatory.title"/>
                            <reg-web:spaceRow/>
                            <!--  Trial Oversight Authority Country -->
                            <reg-web:valueRow labelKey="regulatory.oversight.country.name">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthCountryName}"/>
                            </reg-web:valueRow>
                            <!--  Trial Oversight Authority Organization Name -->
                            <reg-web:valueRow labelKey="regulatory.oversight.auth.name">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthOrgName}"/>
                            </reg-web:valueRow>
                            <!--   FDA Regulated Intervention Indicator-->
                            <reg-web:valueRow labelKey="regulatory.FDA.regulated.interv.ind">
                                <c:out value="${requestScope.trialDTO.fdaRegulatoryInformationIndicator}" /> 
                            </reg-web:valueRow>
                            <!--   Section 801 Indicator-->
                            <reg-web:valueRow id="sec801row" labelKey="regulatory.section801.ind">
                                <c:out value="${requestScope.trialDTO.section801Indicator}" />
                            </reg-web:valueRow>
                            <!--   Delayed Posting Indicator-->
                            <reg-web:valueRow id="delpostindrow" labelKey="regulatory.delayed.posting.ind">
                                <c:out value="${requestScope.trialDTO.delayedPostingIndicator}" />
                            </reg-web:valueRow>
                            <!--   Data Monitoring Committee Appointed Indicator -->
                            <reg-web:valueRow id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind">
                                <c:out value="${requestScope.trialDTO.dataMonitoringCommitteeAppointedIndicator}" />
                            </reg-web:valueRow>
                        </table>
                    </c:if>
                    <c:if test="${requestScope.protocolDocument != null}">
                        <div class="box">
                            <h3>Trial Related Documents</h3>
                            <jsp:include page="/WEB-INF/jsp/searchTrialViewDocs.jsp"/>
                        </div>
                    </c:if>
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <li><a href="#" class="btn" onclick="handleBackAction();this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                            </ul>
                        </del>
                    </div>
                </s:form>
            </div>
        </div>
    </body>
</html>
