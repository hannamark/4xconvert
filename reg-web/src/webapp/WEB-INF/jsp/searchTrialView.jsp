<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="view.trial.page.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript">
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn");
            }
            
            function handleBackAction(){
                window.history.back();        
            }
            function viewAction(url){
                    var pid = getUrlVars()["studyProtocolId"];
                    if (pid == undefined) {
                        pid = ${requestScope.trialIdentifier};
                    }
                    document.forms[0].setAttribute("action", url+"?studyProtocolId="+pid);
                    document.forms[0].submit();    
               
            }
             function getUrlVars() {
                var vars = {};
                var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
                    vars[key] = value;
                });
                return vars;
            }
        </script>
    </head>

    <body onload="setFocusToFirstControl();">
        <div id="contentwide">
            <h1><fmt:message key="search.trial.view.page.title" /></h1>            
            <c:set var="topic" scope="request" value="viewresult"/>
            <div class="box">
                <reg-web:sucessMessage/>
                <reg-web:failureMessage/>
                 <del class="btnwrapper">
                  <ul class="btnrow" style="align:left">
                       <li>   
                          <c:if test="${showVerifyButton == 'true'}"> 
                                        <s:url id="viewUrl" namespace="/protected" action="trialDataVerificationAction" method="view"/>
                                        <s:a cssClass="btn" href="javascript:void(0)" onclick="javascript:viewAction('%{viewUrl}');">
                                            <span class="btn_img">Verify Trial Data</span>
                                        </s:a>
                         </c:if>
                         <s:if test="showAddMySite"> 
                              <s:url id="addMySiteUrl" action="addSitepopupview">
                                <s:param name="studyProtocolId"><c:out value= "${requestScope.trialIdentifier}"/></s:param>
                              </s:url>
                              <s:a cssClass="btn" href="javascript:void(0)" onclick="showPopup('%{addMySiteUrl}', '', 'Add Participating Site');" 
                                           onkeypress="showPopup('%{addMySiteUrl}', '', 'Add Participating Site');">
                                 <span class="btn_img">Add My Site</span>
                              </s:a>
                         </s:if>
                       </li>
                   </ul>
                </del>
            
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
                    <s:token/>
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
                        <reg-web:valueRow labelKey="view.trial.identifier" strong="true" noLabelTag="true">
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'}">
                                <c:out value="${requestScope.trialDTO.assignedIdentifier}"/>
                            </c:if>
                            <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator == null
                                        || requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'}">
                                <c:out value="${requestScope.assignedIdentifier}"/>
                            </c:if>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
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
                            <reg-web:valueRow labelKey="view.trial.nctNumber" noLabelTag="true">
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
                            <reg-web:valueRow labelKey="submit.trial.dcpIdentifier" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.dcpIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialDTO.ctepIdentifier != null}">
                            <reg-web:valueRow labelKey="submit.trial.ctepIdentifier" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.ctepIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.amendmentDate.value != null}">
                            <reg-web:titleRow titleKey="trial.amendDetails"/>
                            <reg-web:spaceRow/>
                            <reg-web:valueRow labelKey="view.trial.amendmentNumber" noLabelTag="true">
                                <c:if test="${requestScope.trialSummary.amendmentNumber.value != null}">
                                    <c:out value="${requestScope.trialSummary.amendmentNumber.value}"/>
                                </c:if>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.amendmentDate" noLabelTag="true">
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
                        <reg-web:valueRow labelKey="view.trial.title" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.officialTitle.value}"/>
                            <c:if test="${not empty trialSummary.studyAlternateTitles}">
                               <a href="javascript:void(0)" onclick="displayStudyAlternateTitles('${trialSummary.identifier.extension}')">(*)</a>                                                   
                            </c:if>
                        </reg-web:valueRow>
                        <reg-web:valueRow labelKey="view.trial.phase" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.phaseCode.code }"/>
                        </reg-web:valueRow>
                        <c:if test="${requestScope.trialSummary.phaseCode.code=='NA'}">
                            <reg-web:valueRow labelKey="view.trial.otherPhaseText" noLabelTag="true">                                
                                <c:out value="${requestScope.trialSummary.phaseAdditionalQualifierCode.code=='Pilot'?'Yes':'No'}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${not empty trialSummary.studyProtocolType.value}">
                            <reg-web:valueRow labelKey="view.trial.type" noLabelTag="true">
                                <c:if test="${trialSummary.studyProtocolType.value=='InterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='Interventional'}">
                                    Interventional
                                </c:if>
                                <c:if test="${trialSummary.studyProtocolType.value=='NonInterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='NonInterventional'}">
                                    Non-Interventional
                                </c:if>
                            </reg-web:valueRow>
                        </c:if>
                        <reg-web:valueRow labelKey="view.trial.primaryPurpose" noLabelTag="true">
                            <c:out value="${requestScope.trialSummary.primaryPurposeCode.code}"/>
                        </reg-web:valueRow>
                        <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                            <reg-web:valueRow labelKey="view.trial.otherPurposeText" noLabelTag="true">
                                <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${trialSummary.studyProtocolType.value=='InterventionalStudyProtocol' || trialSummary.studyProtocolType.value=='Interventional'}">
                            <c:set var="otherSecPurpose" scope="request" value="${false}"/>
	                        <reg-web:valueRow labelKey="view.trial.secondaryPurpose">           
	                           <c:forEach items="${requestScope.trialSummary.secondaryPurposes.item}" var="st">
	                               <c:out value="${st.value}"/><br/>
	                               <c:set var="otherSecPurpose" scope="request" value="${otherSecPurpose==false?(st.value=='Other'):true}"/>
	                           </c:forEach>
	                        </reg-web:valueRow>
	                        <c:if test="${otherSecPurpose}">
                                <reg-web:valueRow labelKey="view.trial.secOtherPurposeText" noLabelTag="true">
                                    <c:out value="${requestScope.trialSummary.secondaryPurposeOtherText.value}"/>
                                </reg-web:valueRow>
                            </c:if>                     
                        </c:if>
                        <c:if test="${trialSummary.studyProtocolType.value!='InterventionalStudyProtocol' && trialSummary.studyProtocolType.value!='Interventional'}">
                            <reg-web:valueRow labelKey="submit.trial.studySubtypeCode">                            
                                <c:out value="${trialSummary.studySubtypeCode.code}"/>
                            </reg-web:valueRow>  
                            <reg-web:valueRow labelKey="submit.trial.studyModelCode">                            
                                <c:out value="${trialSummary.studyModelCode.code}"/>
                            </reg-web:valueRow>   
                            <c:if test="${trialSummary.studyModelCode.code == 'Other'}">
                                <reg-web:valueRow labelKey="submit.trial.studyModelOtherText" noLabelTag="true">
                                    <c:out value="${trialSummary.studyModelOtherText.value}"/>
                                </reg-web:valueRow>                                
                            </c:if>
                            <reg-web:valueRow labelKey="submit.trial.timePerspectiveCode">                            
                                <c:out value="${trialSummary.timePerspectiveCode.code}"/>
                            </reg-web:valueRow>   
                            <c:if test="${trialSummary.timePerspectiveCode.code == 'Other'}">
                                <reg-web:valueRow labelKey="submit.trial.timePerspectiveOtherText" noLabelTag="true">
                                    <c:out value="${trialSummary.timePerspectiveOtherText.value}"/>
                                </reg-web:valueRow>                                
                            </c:if>
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
                            <td scope="row" class="label-noinput">
                                    <fmt:message key="view.trial.leadOrganization"/>
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
                            <reg-web:valueRow labelKey="view.trial.principalInvestigator" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.piName}"/>
                            </reg-web:valueRow>        
                        </c:if>
                        <reg-web:spaceRow/>
                        <c:if test="${requestScope.trialDTO.xmlRequired == true && maskFields != true}">
                              <reg-web:titleRow titleKey="view.trial.sponsorResParty"/>
                              <reg-web:valueRow labelKey="view.trial.sponsor" noLabelTag="true">
                                  <c:out value="${requestScope.trialDTO.sponsorName}"/>
                              </reg-web:valueRow>
                              <reg-web:valueRow labelKey="view.trial.respParty" noLabelTag="true">
                                  <c:out value="${func:capitalize(requestScope.trialDTO.responsiblePartyTypeReadable)}"/>
                              </reg-web:valueRow>
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonName) != ''}">
                                  <reg-web:valueRow labelKey="view.trial.respParty.investigator" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonName}"/>
                                  </reg-web:valueRow>
                              </c:if>  
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonTitle) != ''}">
                                  <reg-web:valueRow labelKey="view.trial.respParty.investigatorTitle" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonTitle}"/>
                                  </reg-web:valueRow>
                              </c:if>
                              <c:if test="${fn:trim(requestScope.trialDTO.responsiblePersonAffiliationOrgName) != ''}">
                                  <reg-web:valueRow labelKey="view.trial.respParty.investigatorAff" noLabelTag="true">
                                      <c:out value="${requestScope.trialDTO.responsiblePersonAffiliationOrgName}"/>
                                  </reg-web:valueRow>
                              </c:if>                                  
                              <reg-web:spaceRow/>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'true'
                                    && fn:length(requestScope.summaryFourOrgIdentifiers) > 0}">
                            <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            <reg-web:valueRow labelKey="view.trial.SubmissionCategory" noLabelTag="true">
                                <c:out value="${requestScope.summaryFourFundingCategoryCode}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.FundingSponsor" noLabelTag="true">
                            <c:forEach items="${requestScope.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                            </c:forEach>
                                
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
                                    <c:out value="${empty requestScope.consortiaTrialCategoryCode?'Yes':'No - '}"/>
                                    <c:out value="${requestScope.consortiaTrialCategoryCode}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${requestScope.trialSummary.proprietaryTrialIndicator != null
                                    && requestScope.trialSummary.proprietaryTrialIndicator.value == 'false'
                                    && fn:length(requestScope.trialDTO.summaryFourOrgIdentifiers) > 0}">
                            <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            <reg-web:valueRow labelKey="view.trial.FundingCategory" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.summaryFourFundingCategoryCode}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.FundingSponsor" noLabelTag="true">
                            <c:forEach items="${requestScope.trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                            </c:forEach>
                            </reg-web:valueRow>                            
                        </c:if>
                        <c:if test="${requestScope.trialSummary.programCodeText.value != null}">
                            <c:if test="${empty requestScope.trialDTO.summaryFourOrgIdentifiers}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            </c:if>
                            <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode" noLabelTag="true">
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
                            <reg-web:valueRow labelKey="regulatory.oversight.country.name" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthCountryName}"/>
                            </reg-web:valueRow>
                            <!--  Trial Oversight Authority Organization Name -->
                            <reg-web:valueRow labelKey="regulatory.oversight.auth.name" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.trialOversgtAuthOrgName}"/>
                            </reg-web:valueRow>
                            <!--   FDA Regulated Intervention Indicator-->
                            <reg-web:valueRow labelKey="regulatory.FDA.regulated.interv.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.fdaRegulatoryInformationIndicator}" /> 
                            </reg-web:valueRow>
                            <!--   Section 801 Indicator-->
                            <reg-web:valueRow id="sec801row" labelKey="regulatory.section801.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.section801Indicator}" />
                            </reg-web:valueRow>
                            <!--   Delayed Posting Indicator-->
                            <reg-web:valueRow id="delpostindrow" labelKey="regulatory.delayed.posting.ind" noLabelTag="true">
                                <c:out value="${requestScope.trialDTO.delayedPostingIndicator}" />
                            </reg-web:valueRow>
                            <!--   Data Monitoring Committee Appointed Indicator -->
                            <reg-web:valueRow id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind" noLabelTag="true">
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
                    <c:if test="${pageContext.request.method=='GET' && fn:contains(header['Referer'],'/searchTrialquery.action')}">
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <li><a href="javascript:void(0)" class="btn" onclick="handleBackAction();this.blur();"><span class="btn_img"><span class="back">Back to Search Results</span></span></a></li>
                            </ul>
                        </del>
                    </div>
                    </c:if>
                </s:form>
            </div>
        </div>
    </body>
</html>
