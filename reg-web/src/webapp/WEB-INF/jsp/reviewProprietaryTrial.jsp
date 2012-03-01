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
        <script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
        <script type="text/javascript" language="javascript">
        //<![CDATA[
            function submitTrial() {
                document.forms[0].action="submitProprietaryTrialcreate.action";
                document.forms[0].submit();
                showPopWin('${amendProtocol}', 600, 200, '', 'Submit Register Trial');
            }
            
            function editTrial() {
                    document.forms[0].action = "submitProprietaryTrialedit.action";
                    document.forms[0].submit();
            }
            
            function printProtocol (){
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
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
        //]]>
        </script>
    </head>
    <body>
        <div id="contentwide">
            <h1><fmt:message key="review.trial.view.page.title" /></h1>
            <c:set var="topic" scope="request" value="edittrial"/>
            <div class="box">
                <s:form>
                    <s:token/> 
                    <s:actionerror/>
                    <s:hidden name="trialDTO.assignedIdentifier" id="trialDTO.assignedIdentifier"/>
                    <s:hidden name="pageFrom" id="pageFrom"/>
                    <c:if test="${requestScope.protocolId != null}">
                        <div class="confirm_msg">
                            <strong>The trial has been successfully submitted and assigned the NCI Identifier ${requestScope.protocolId}</strong>
                        </div>
                    </c:if>
                    <div id="contentprint">
                        <table class="form">
                            <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                            <reg-web:valueRow labelKey="view.trial.leadOrganization">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="submit.proprietary.trial.siteOrganization">
                                <c:out value="${trialDTO.siteOrganizationName}"/>
                            </reg-web:valueRow>  
                            <reg-web:valueRow labelKey="submit.proprietary.trial.siteidentifier">
                                <c:out value="${trialDTO.localSiteIdentifier}"/>
                            </reg-web:valueRow>  
                            <c:if test="${fn:trim(trialDTO.nctIdentifier) != ''}">
                                <reg-web:valueRow labelKey="view.trial.nctNumber">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRow>  
                            </c:if>
                            <reg-web:titleRow titleKey="view.trial.trialDetails"/>
                            <reg-web:valueRow labelKey="view.trial.title">
                                <c:out value="${trialDTO.officialTitle}"/>
                            </reg-web:valueRow>  
                            <reg-web:valueRow labelKey="view.trial.phase">
                                <c:out value="${trialDTO.phaseCode}"/>
                            </reg-web:valueRow>   
                            <reg-web:valueRow labelKey="view.trial.type">
                                <c:out value="${trialDTO.trialType}"/>
                            </reg-web:valueRow>    
                            <reg-web:valueRow labelKey="view.trial.primaryPurpose">
                                <c:out value="${trialDTO.primaryPurposeCode}"/>
                            </reg-web:valueRow>     
                            <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                                <reg-web:valueRow labelKey="view.trial.otherPurposeText">
                                    <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <reg-web:valueRow labelKey="view.trial.principalInvestigator">
                                <c:out value="${trialDTO.sitePiName}"/>
                            </reg-web:valueRow>
                            <reg-web:spaceRow/>
                            <c:if test="${fn:trim(trialDTO.summaryFourOrgName) != ''}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                <reg-web:valueRow labelKey="view.trial.SubmissionCategory">
                                    <c:out value="${trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.FundingSponsor">
                                    <c:out value="${trialDTO.summaryFourOrgName}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${fn:trim(trialDTO.siteProgramCodeText) != ''}">
                                <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">
                                    <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                </c:if>
                                <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode">
                                    <c:out value="${trialDTO.siteProgramCodeText}"/>
                                </reg-web:valueRow>
                                <reg-web:spaceRow/>
                            </c:if>
                            <reg-web:titleRow titleKey="view.trial.statusDates"/>
                            <reg-web:valueRow labelKey="view.trial.siteRecruitmentStatus">
                                <c:out value="${trialDTO.siteStatusCode}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.siteRecruitmentStatusDate">
                                <c:out value="${trialDTO.siteStatusDate}"/>
                            </reg-web:valueRow>
                            <c:if test="${fn:trim(trialDTO.dateOpenedforAccrual) != ''}">
                                <reg-web:valueRow labelKey="submit.proprietary.trial.dateOpenedforAccrual">
                                    <c:out value="${trialDTO.dateOpenedforAccrual}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <c:if test="${fn:trim(trialDTO.dateClosedforAccrual) != ''}">
                                <reg-web:valueRow labelKey="submit.proprietary.trial.dateClosedforAccrual">
                                    <c:out value="${trialDTO.dateClosedforAccrual}"/>
                                </reg-web:valueRow>
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
                        <c:if test="${fn:length(trialDTO.docDtos) >0}">
                            <div class="box">
                                <display:table class="data" decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" size="false" id="row"
                                               name="${trialDTO.docDtos}" requestURI="searchTrialviewDoc.action" export="false">
                                    <display:column titleKey="search.trial.view.documentTypeCode" property="proprietaryTypeCode"   sortable="true" headerClass="sortable"/>
                                    <display:column titleKey="search.trial.view.documentFileName" property="fileName"   sortable="true" headerClass="sortable"/>"
                                </display:table>
                            </div>
                        </c:if>
                    </div>
                    <c:if test="${requestScope.protocolId == null}">
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <ul class="btnrow">
                                    <li><a href="#" class="btn" onclick="editTrial();"><span class="btn_img"> <span class="edit">Edit</span></span></a></li>
                                    <li><a href="#" class="btn" onclick="submitTrial();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
                                    <li><a href="#" class="btn" onclick="printProtocol();"><span class="btn_img"><span class="print">Print</span></span></a></li>
                                </ul>
                            </del>
                        </div>
                    </c:if>
                </s:form>
            </div>
        </div>
    </body>
</html>
