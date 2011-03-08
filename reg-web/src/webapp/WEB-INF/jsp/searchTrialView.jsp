<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="view.trial.page.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript">
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn");
    }
    function handleBackAction(){
     document.forms[0].action="searchTrialquery.action";
     document.forms[0].submit();
}
    </SCRIPT>
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
          <strong>The trial update with the NCI Identifier
          <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                         ${requestScope.trialDTO.assignedIdentifier}
          </c:if>
          <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                         ${requestScope.assignedIdentifier}
            </c:if>
           was successfully submitted.</strong>
        </div>
     </c:if>
    <s:form > <s:actionerror/>
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
         <tr>
            <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <strong>
                    <label for="Identifier">
                        <fmt:message key="view.trial.identifier"/>
                    </label></strong>
                </td>
                <td class="value">
                     <strong>
                     <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                         <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                  </c:if>
                  <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                         <c:out value="${requestScope.assignedIdentifier}"/>
                  </c:if>
                     </strong>
                </td>

          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Lead Organization Trial Identifier">
                        <fmt:message key="view.trial.leadOrgTrialIdentifier"/>
                        </label>
                </td>
                <td class="value">
                  <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                         <c:out value="${requestScope.trialDTO.leadOrgTrialIdentifier}"/>
                  </c:if>
                  <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                         <c:out value="${requestScope.leadOrgTrialIdentifier}"/>
                  </c:if>
                </td>
          </tr>
          <c:if test="${requestScope.trialDTO.nctIdentifier != null}">
              <tr>
                    <td scope="row" class="label">
                        <label for="NCT Number">
                            <fmt:message key="view.trial.nctNumber"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                         <c:out value="${requestScope.trialDTO.nctIdentifier }"/>
                  </c:if>
                  <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                    || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                         <c:out value="${requestScope.nctIdentifier }"/>
                  </c:if>
                    </td>
              </tr>
          </c:if>
          <c:if test="${requestScope.trialDTO.dcpIdentifier != null}">
              <tr>
                    <td scope="row" class="label">
                        <label for="DCP Number">
                            <fmt:message key="submit.trial.dcpIdentifier"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${requestScope.trialDTO.dcpIdentifier }"/>
                    </td>
              </tr>
          </c:if>
          <c:if test="${requestScope.trialDTO.ctepIdentifier != null}">
              <tr>
                    <td scope="row" class="label">
                        <label for="CTEP Number">
                            <fmt:message key="submit.trial.ctepIdentifier"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${requestScope.trialDTO.ctepIdentifier }"/>
                    </td>
              </tr>
          </c:if>
        <c:if test="${requestScope.trialSummary.amendmentDate.value != null}">
        <tr>
           <th colspan="2"><fmt:message key="trial.amendDetails"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>
            <td scope="row" class="label">
                <label for="Identifier">
                    <fmt:message key="view.trial.amendmentNumber"/>
                </label>
          </td>
          <td>
              <c:if test="${requestScope.trialSummary.amendmentNumber.value != null}">
                <c:out value="${requestScope.trialSummary.amendmentNumber.value}"/>
              </c:if>
           </td>
          </tr>
          <tr>
               <td scope="row" class="label">
                    <label for="Amendment Date">
                        <fmt:message key="view.trial.amendmentDate"/>
                        </label>
                </td>
                <td class="value">
                    <fmt:formatDate value="${requestScope.trialSummary.amendmentDate.value}"/>
                </td>
          </tr>
        </c:if>
        <c:if test="${requestScope.trialDTO.secondaryIdentifierList != null && fn:length(requestScope.trialDTO.secondaryIdentifierList) > 0}">
            <s:set name="records" value="requestScope.trialDTO.secondaryIdentifierList" scope="request"/>
            <tr>
                <th colspan="2"><fmt:message key="view.trial.otherIdentifiers"/></th>
            </tr>
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
          <tr>
              <th colspan="2"><fmt:message key="view.trial.trialDetails"/></th>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="officialTitle">
                        <fmt:message key="view.trial.title"/>
                    </label>
                </td>
                <td class="value">
                     <c:out value="${requestScope.trialSummary.officialTitle.value }"/>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Trial Phase">
                        <fmt:message key="view.trial.phase"/>
                        </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialSummary.phaseCode.code }"/>
                </td>
          </tr>
          <c:if test="${requestScope.trialDTO.phaseAdditionalQualifier != null }">
              <tr>
                    <td scope="row" class="label">
                        <label for="Other Phase Text">
                            <fmt:message key="view.trial.otherPhaseText"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${requestScope.trialDTO.phaseAdditionalQualifier }"/>
                    </td>
              </tr>
          </c:if>
          <c:if test= "${requestScope.trialDTO.trialType != null} ">
          <tr>
                <td scope="row" class="label">
                    <label for="Trial Type">
                        <fmt:message key="view.trial.type"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialDTO.trialType}"/>
                </td>
          </tr>
          </c:if>
          <tr>
                  <td scope="row" class="label">
                      <label for="Primary Purpose">
                          <fmt:message key="view.trial.primaryPurpose"/>
                      </label>
                  </td>
                  <td class="value">
                      <c:out value="${requestScope.trialSummary.primaryPurposeCode.code }"/>
                  </td>
          </tr>
          <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
              <tr>
                    <td scope="row" class="label">
                        <label for="Other Purpose Text">
                            <fmt:message key="view.trial.otherPurposeText"/>
                        </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                    </td>
              </tr>
          </c:if>

          <tr>
              <td colspan="2" class="space">&nbsp;</td>
          </tr>
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
       <tr>
              <td scope="row" class="label">
                  <label for="Principal Investigator">
                  <fmt:message key="view.trial.principalInvestigator"/>
                  </label>
              </td>
              <td class="value">
                 <c:out value="${requestScope.trialDTO.piName }"/>
                 </td>
       </tr>
       </c:if>
       <tr>
            <td colspan="2" class="space">&nbsp;</td>
       </tr>
       <c:if test="${requestScope.sponsor != null}">
           <tr>
                <th colspan="2"><fmt:message key="view.trial.sponsorResParty"/></th>
              </tr>
              <tr>
                <td scope="row" class="label">
                <label for="Sponsor">
                    <fmt:message key="view.trial.sponsor"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${requestScope.sponsor }"/>
                 </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                <label for="Responsible Party">
                    <fmt:message key="view.trial.respParty"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${requestScope.respParty}"/>
                 </td>
           </tr>
           <c:if test="${requestScope.respPartyContact != null}">
               <tr>
                    <td scope="row" class="label">
                    <label for="Responsible Party Contact">
                        <fmt:message key="view.trial.respPartyContact"/>
                    </label>
                    </td>
                     <td class="value">
                        <c:out value="${requestScope.respPartyContact}"/>
                     </td>
               </tr>
           </c:if>
           <tr>
                <td scope="row" class="label">
                <label for="Email Address">
                    <fmt:message key="view.trial.respPartyEmailAddr"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${requestScope.respPartyEmailAddr}"/>
                 </td>
           </tr>
                  <tr>
                <td scope="row" class="label">
                <label for="Phone">
                    <fmt:message key="view.trial.respPartyPhone"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${requestScope.respPartyPhone}"/>
                 </td>
           </tr>
           <tr>
                 <td colspan="2" class="space">&nbsp;</td>
           </tr>
       </c:if>

       <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'
                && requestScope.summaryFourOrgName != null}">
           <tr>
                <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Category">
                    <fmt:message key="view.trial.FundingCategory"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.summaryFourFundingCategoryCode }"/>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Sponsor/Source">
                    <fmt:message key="view.trial.FundingSponsor"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.summaryFourOrgName }"/>
                </td>
           </tr>
       </c:if>
       <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'
                && requestScope.trialDTO.summaryFourOrgName != null}">
           <tr>
                <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Category">
                    <fmt:message key="view.trial.FundingCategory"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialDTO.summaryFourFundingCategoryCode }"/>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Sponsor/Source">
                    <fmt:message key="view.trial.FundingSponsor"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${requestScope.trialDTO.summaryFourOrgName }"/>
                </td>
           </tr>
       </c:if>

      <c:if test="${requestScope.trialSummary.programCodeText.value != null}">
       <c:if test="${requestScope.trialDTO.summaryFourOrgName == null}">
           <tr>
                <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
           </tr>
        </c:if>
       <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <c:out value="${requestScope.trialSummary.programCodeText.value}"/>
               </td>
        </tr>
        <tr>
              <td colspan="2" class="space">&nbsp;</td>
          </tr>
      </c:if>
      <c:if test="${requestScope.participatingSitesList != null && fn:length(requestScope.participatingSitesList) > 0}">
        <div class="box">
        <tr>
                <th colspan="2">Participating sites</th>
         </tr>

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
      </c:if>
      <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
      <tr>
          <th colspan="2"><fmt:message key="view.trial.statusDates"/></th>
      </tr>
      <tr>
        <td scope="row" class="label">
        <label for="Current Trial Status">
            <fmt:message key="view.trial.currentTrialStatus"/>
        </label>
       </td>
            <td class="value">
               <c:out value="${requestScope.trialDTO.statusCode}"/>
         </td>
      </tr>
      <c:if test="${requestScope.trialDTO.reason != null}">
          <tr>
            <td scope="row" class="label">
            <label for="Trial Status Reason">
                <fmt:message key="view.trial.trialStatusReason"/>
            </label>
           </td>
             <td class="value">
                <c:out value="${requestScope.trialDTO.reason}"/>
             </td>
          </tr>
      </c:if>
      <tr>
          <td scope="row" class="label">
          <label for="Current Trial Status Date">
              <fmt:message key="view.trial.currentTrialStatusDate"/>
          </label>
         </td>
         <td class="value">
         <c:out value="${requestScope.trialDTO.statusDate}"/>
         </td>
      </tr>
      <tr>
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="view.trial.trialStartDate"/>
              </label>
          </td>
          <td class="value">
                 <c:out value="${requestScope.trialDTO.startDate }"/>
               <c:out value="${requestScope.trialDTO.startDateType }"/>
          </td>
       </tr>
       <tr>
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="view.trial.primaryCompletionDate"/>
            </label>
        </td>
        <td class="value">
             <c:out value="${requestScope.trialDTO.completionDate }"/>
             <c:out value="${requestScope.trialDTO.completionDateType }"/>
        </td>
      </tr>
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
        <tr>
              <th colspan="2">Regulatory Information</th>
          </tr>
        <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>
<!--  Trial Oversight Authority Country -->
        <tr>
        <td scope="row" class="label">
        <fmt:message key="regulatory.oversight.country.name"/></td>
          <td class="value"> <c:out value="${requestScope.trialDTO.trialOversgtAuthCountryName }"/> </td>
       </tr>
       <tr>
         <td scope="row" class="label">
           <fmt:message key="regulatory.oversight.auth.name"/></td>
                <td class="value"> <c:out value="${requestScope.trialDTO.trialOversgtAuthOrgName }"/> </td>
         </tr>

 <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
         <fmt:message key="regulatory.FDA.regulated.interv.ind"/></td>
         <td class="value"><c:out value="${requestScope.trialDTO.fdaRegulatoryInformationIndicator}" /> </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label"><fmt:message key="regulatory.section801.ind"/></td>
         <td class="value"><c:out value="${requestScope.trialDTO.section801Indicator}" /> </td>
     </tr>

     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label"><fmt:message key="regulatory.delayed.posting.ind"/></td>
         <td class="value"><c:out value="${requestScope.trialDTO.delayedPostingIndicator}" /></td>
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
         <td class="value"><c:out value="${requestScope.trialDTO.dataMonitoringCommitteeAppointedIndicator}" /></td>
     </tr>
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
                <li><a href="#"
                    class="btn" onclick="handleBackAction();this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
            </ul>
        </del>
        </div>
    </s:form>
   </div>
   </div>
 </body>
 </html>
