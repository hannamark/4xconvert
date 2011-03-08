<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="review.trial.page.title"/></title>
    <s:head/>
</head>

<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
<c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
<script language="javascript">
function submitTrial(){
    var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
    if (assignedId != '') {
        document.forms[0].action="amendTrialamend.action";
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
        document.forms[0].action="amendTrialedit.action";
        document.forms[0].submit();
    } else {
        document.forms[0].action = "submitTrialedit.action";
        document.forms[0].submit();
    }


}
function printProtocol (){
    var sOption="toolbar=no,location=no,directories=no,menubar=yes,";
    sOption+="scrollbars=yes,width=750,height=600,left=100,top=25";
var sWinHTML = document.getElementById('contentprint').innerHTML;

var winprint=window.open("","",sOption);
    winprint.document.open();
    winprint.document.write('<html><body>');
    winprint.document.write(sWinHTML);
    winprint.document.write('</body></html>');
    winprint.document.close();
    winprint.focus();
}
</script>
<body>
<div id="contentwide">
    <h1><fmt:message key="review.trial.view.page.title" /></h1>
    <c:set var="topic" scope="request" value="edittrial"/>
<div class="box">
    <s:form id="reviewTrialForm">
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
         <tr>
            <th colspan="2"><fmt:message key="view.trial.trialIDs"/></th>
          </tr>
          <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
          <tr>
                <td scope="row" class="label">
                    <label for="Assigned NCI Identifier">
                        <strong><fmt:message key="view.trial.nciAccessionNumber"/></strong>
                        </label>
                </td>
                <td class="value">
                    <strong><c:out value="${trialDTO.assignedIdentifier}"/></strong>
                </td>
          </tr>
          </c:if>
          <c:if test="${requestScope.protocolId != null}" >
            <tr>
                <td scope="row" class="label">
                    <label for="Assigned Identifier">Record Identifier</label>
                </td>
                <td class="value">
                    <strong><c:out value="${requestScope.protocolId}"/></strong>
                </td>
          </tr>
          </c:if>

          <tr>
                <td scope="row" class="label">
                    <label for="Lead Organization Trial Identifier">
                        <fmt:message key="view.trial.leadOrgTrialIdentifier"/>
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                </td>
          </tr>
          <c:if test="${trialDTO.nctIdentifier != null}">
              <tr>
                    <td scope="row" class="label">
                        <label for="NCT Number">
                            <fmt:message key="view.trial.nctNumber"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.nctIdentifier }"/>
                    </td>
              </tr>
          </c:if>
          <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
              <c:if test="${trialDTO.ctepIdentifier != null}">
              <tr>
                    <td scope="row" class="label">
                        <label for="CTEP Identifier">
                            <fmt:message key="submit.trial.ctepIdentifier"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.ctepIdentifier }"/>
                    </td>
              </tr>
              </c:if>
              <c:if test="${trialDTO.dcpIdentifier != null}">
                <tr>
                    <td scope="row" class="label">
                        <label for="DCP Identifier">
                            <fmt:message key="submit.trial.dcpIdentifier"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.dcpIdentifier }"/>
                    </td>
               </tr>
           </c:if>
           <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIds.jsp" %>
           <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
          <tr>
            <th colspan="2"><fmt:message key="trial.amendDetails"/></th>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Amendment Number">
                        <fmt:message key="view.trial.amendmentNumber"/>
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.localAmendmentNumber}"/>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Amendment Date">
                        <fmt:message key="view.trial.amendmentDate"/>
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.amendmentDate}"/>
                </td>
          </tr>
        </c:if>
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
                    <c:out value="${trialDTO.officialTitle}"/>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Trial Phase">
                        <fmt:message key="view.trial.phase"/>
                        </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.phaseCode}"/>
                </td>
          </tr>
          <c:if test="${trialDTO.phaseAdditionalQualifier!= ''}">
              <tr>
                    <td scope="row" class="label">
                        <label for="Other Phase Text">
                            <fmt:message key="view.trial.otherPhaseText"/>
                            </label>
                    </td>
                    <td class="value">
                        <c:out value="${trialDTO.phaseAdditionalQualifier}"/>
                    </td>
              </tr>
          </c:if>
          <tr>
                <td scope="row" class="label">
                    <label for="Trial Type">
                        <fmt:message key="view.trial.type"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.trialType }"/>
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                    <label for="Primary Purpose">
                        <fmt:message key="view.trial.primaryPurpose"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.primaryPurposeCode}"/>
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
            <th colspan="2"><fmt:message key="view.trial.leadOrgInvestigator"/></th>
          </tr>
          <tr>
            <td scope="row" class="label">
            <label for="Lead Organization">
                <fmt:message key="view.trial.leadOrganization"/>
            </label>
            </td>
             <td class="value">
                <c:out value="${trialDTO.leadOrganizationName }"/>
             </td>
       </tr>
       <c:if test="${trialDTO.propritaryTrialIndicator == 'Yes'}">
          <tr>
            <td scope="row" class="label">
                <label for="Participating Organization Trial Identifier">
                    <fmt:message key="submit.proprietary.trial.siteOrganization"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${trialDTO.siteOrganizationName}"/>
            </td>
          </tr>
          <tr>
            <td scope="row" class="label">
                <label for="Local Trial Identifier">
                    <fmt:message key="submit.proprietary.trial.siteidentifier"/>
                </label>
            </td>
            <td class="value">
                <c:out value="${trialDTO.localSiteIdentifier}"/>
            </td>
          </tr>
          <tr>
            <td scope="row" class="label">
                <label for="Principal Investigator">
                <fmt:message key="view.trial.principalInvestigator"/>
                </label>
              </td>
             <td class="value">
                <c:out value="${trialDTO.sitePiName}"/>
                </td>
       </tr>
          </c:if>
          <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'
                        && trialDTO.xmlRequired == true}">
           <tr>
            <td scope="row" class="label">
                <label for="Principal Investigator">
                <fmt:message key="view.trial.principalInvestigator"/>
                </label>
              </td>
             <td class="value">
                <c:out value="${trialDTO.piName }"/>
                </td>
       </tr>
       <tr>
            <td colspan="2" class="space">&nbsp;</td>
       </tr>
       <c:if test="${trialDTO.sponsorName != null}">
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
                    <c:out value="${trialDTO.sponsorName }"/>
                 </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                <label for="Responsible Party">
                    <fmt:message key="view.trial.respParty"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${trialDTO.responsiblePartyType}"/>
                 </td>
           </tr>
           <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
               <tr>
                    <td scope="row" class="label">
                    <label for="Responsible Party Contact">
                        <fmt:message key="view.trial.respPartyContact"/>
                    </label>
                    </td>
                     <td class="value">
                        <c:out value="${trialDTO.responsiblePersonName}"/>
                     </td>
               </tr>
           </c:if>
           <c:if test="${fn:trim(trialDTO.responsibleGenericContactName) != ''}">
               <tr>
                    <td scope="row" class="label">
                    <label for="Responsible Party Contact">
                        <fmt:message key="view.trial.respPartyContact"/>
                    </label>
                    </td>
                     <td class="value">
                        <c:out value="${trialDTO.responsibleGenericContactName}"/>
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
                    <c:out value="${trialDTO.contactEmail}"/>
                 </td>
           </tr>
                  <tr>
                <td scope="row" class="label">
                <label for="Phone">
                    <fmt:message key="view.trial.respPartyPhone"/>
                </label>
                </td>
                 <td class="value">
                    <c:out value="${trialDTO.contactPhone}"/>
                 </td>
           </tr>
           <tr>
                <td colspan="2" class="space">&nbsp;</td>
           </tr>
       </c:if>
       </c:if>
       <c:if test="${fn:trim(trialDTO.summaryFourOrgName) != ''}">
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
                    <c:out value="${trialDTO.summaryFourFundingCategoryCode }"/>
                </td>
           </tr>
           <tr>
                <td scope="row" class="label">
                    <label for="Summary 4 Funding Sponsor/Source">
                    <fmt:message key="view.trial.FundingSponsor"/>
                    </label>
                </td>
                <td class="value">
                    <c:out value="${trialDTO.summaryFourOrgName }"/>
                </td>
           </tr>
      </c:if>
      <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'No'}">
        <c:if test="${trialDTO.programCodeText != ''}">
             <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">
                <tr>
                    <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
                </tr>
            </c:if>
           <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <c:out value="${trialDTO.programCodeText}"/>
               </td>
            </tr>
             <tr>
              <td colspan="2" class="space">&nbsp;</td>
            </tr>
            </c:if>
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
            <c:out value="${trialDTO.statusCode}"/>
         </td>
      </tr>
      <c:if test="${trialDTO.reason != ''}">
          <tr>
            <td scope="row" class="label">
            <label for="Trial Status Reason">
                <fmt:message key="view.trial.trialStatusReason"/>
            </label>
           </td>
             <td class="value">
                <c:out value="${trialDTO.reason }"/>
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
           <c:out value="${trialDTO.statusDate}"/>
           </td>
      </tr>
      <tr>
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="view.trial.trialStartDate"/>
              </label>
          </td>
          <td class="value">
               <c:out value="${trialDTO.startDate}"/>
               <c:out value="${trialDTO.startDateType }"/>
          </td>
       </tr>
       <tr>
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="view.trial.primaryCompletionDate"/>
            </label>
        </td>
        <td class="value">
             <c:out value="${trialDTO.completionDate}"/>
             <c:out value="${trialDTO.completionDateType}"/>
        </td>
      </tr>
      </c:if>
      <c:if test="${trialDTO.propritaryTrialIndicator != null && trialDTO.propritaryTrialIndicator == 'Yes'}">
          <c:if test="${fn:trim(trialDTO.siteProgramCodeText) != ''}">
             <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">
                <tr>
                    <th colspan="2"><fmt:message key="view.trial.Summary4Information"/></th>
                </tr>
            </c:if>
           <tr>
             <td scope="row" class="label"><label for="summary4ProgramCode"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label></td>
             <td class="value">
                <c:out value="${trialDTO.siteProgramCodeText}"/>
               </td>
            </tr>
             <tr>
              <td colspan="2" class="space">&nbsp;</td>
            </tr>
            </c:if>
      <tr>
          <th colspan="2"><fmt:message key="view.trial.statusDates"/></th>
      </tr>
      <tr>
        <td scope="row" class="label">
        <label for="Site Recruitment Status">
            <fmt:message key="view.trial.siteRecruitmentStatus"/>
        </label>
       </td>
         <td class="value">
            <c:out value="${trialDTO.siteStatusCode}"/>
         </td>
      </tr>
      <tr>
        <td scope="row" class="label">
          <label for="Site Recruitment Status Date">
              <fmt:message key="view.trial.siteRecruitmentStatusDate"/>
          </label>
         </td>
         <td class="value">
           <c:out value="${trialDTO.siteStatusDate}"/>
           </td>
      </tr>
      <c:if test="${fn:trim(trialDTO.dateOpenedforAccrual) != ''}">
      <tr>
          <td scope="row" class="label">
              <label for="Trial Start Date">
                  <fmt:message key="submit.proprietary.trial.dateOpenedforAccrual"/>
              </label>
          </td>
          <td class="value">
               <c:out value="${trialDTO.dateOpenedforAccrual}"/>
          </td>
       </tr>
       </c:if>
       <c:if test="${fn:trim(trialDTO.dateClosedforAccrual) != ''}">
       <tr>
        <td scope="row" class="label">
            <label for="Primary Completion Date">
                <fmt:message key="submit.proprietary.trial.dateClosedforAccrual"/>
            </label>
        </td>
        <td class="value">
             <c:out value="${trialDTO.dateClosedforAccrual}"/>
        </td>
      </tr>
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
          <td class="value"> <c:out value="${trialDTO.trialOversgtAuthCountryName }"/> </td>
       </tr>
       <tr>
         <td scope="row" class="label">
           <fmt:message key="regulatory.oversight.auth.name"/></td>
                <td class="value"> <c:out value="${trialDTO.trialOversgtAuthOrgName }"/> </td>
         </tr>

 <!--   FDA Regulated Intervention Indicator-->
     <tr>
         <td scope="row"  class="label">
         <fmt:message key="regulatory.FDA.regulated.interv.ind"/></td>
         <td class="value"><c:out value="${trialDTO.fdaRegulatoryInformationIndicator}" /> </td>
     </tr>
     <!--   Section 801 Indicator-->
     <tr id="sec801row">
         <td scope="row" class="label"><fmt:message key="regulatory.section801.ind"/></td>
         <td class="value"><c:out value="${trialDTO.section801Indicator}" /> </td>
     </tr>

     <!--   Delayed Posting Indicator-->
     <tr id="delpostindrow">
         <td scope="row" class="label"><fmt:message key="regulatory.delayed.posting.ind"/></td>
         <td class="value"><c:out value="${trialDTO.delayedPostingIndicator}" /></td>
     </tr>
     <!--   Data Monitoring Committee Appointed Indicator -->
     <tr id="datamonrow">
         <td scope="row" class="label"><fmt:message key="regulatory.data.monitoring.committee.ind"/></td>
         <td class="value"><c:out value="${trialDTO.dataMonitoringCommitteeAppointedIndicator}" /></td>
     </tr>
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
                <li><a href="#"
                    class="btn" onclick="editTrial();"><span class="btn_img"> <span class="edit">Edit</span></span></a></li>
               <li><a href="#"
                    class="btn" onclick="submitTrial();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
               <li><a href="#"
                    class="btn" onclick="printProtocol();"><span class="btn_img"><span class="print">Print</span></span></a></li>
            </ul>
        </del>
        </div>
        </c:if>
    </s:form>
   </div>
   </div>
 </body>
 </html>
