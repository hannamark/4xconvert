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
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="amendProtocol"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="submitProtocol"/>
        <script type="text/javascript" language="javascript">
        //<![CDATA[
            function submitTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action="updateProprietaryTrialupdate.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');
                }
            }
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action="updateProprietaryTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol (){
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=750,height=600,left=100,top=25";
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
                    <div id="contentprint">
                        <table class="form">
                            <reg-web:titleRow titleKey="submit.proprietary.trial.trialIdentification"/>
                            <reg-web:valueRow labelKey="view.trial.leadOrganization" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrganizationName}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier" noLabelTag="true">
                                <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                            </reg-web:valueRow>
                            <c:if test="${fn:trim(trialDTO.nctIdentifier) != ''}">
                                <reg-web:valueRow labelKey="view.trial.nctNumber" noLabelTag="true">
                                    <c:out value="${trialDTO.nctIdentifier}"/>
                                </reg-web:valueRow>
                            </c:if>
                            <reg-web:titleRow titleKey="view.trial.trialDetails"/>
                            <reg-web:valueRow labelKey="view.trial.title" noLabelTag="true">
                                <c:out value="${trialDTO.officialTitle}"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.phase" noLabelTag="true">
                                <c:out value="${trialDTO.phaseCode}"/>
                            </reg-web:valueRow>
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
                                <reg-web:valueRow labelKey="view.trial.secondaryPurpose">
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
                            <c:if test="${fn:length(trialDTO.summaryFourOrgIdentifiers) > 0}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                                <reg-web:valueRow labelKey="view.trial.SubmissionCategory" noLabelTag="true">
                                    <c:out value="${trialDTO.summaryFourFundingCategoryCode}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.FundingSponsor" noLabelTag="true">
                                    <c:forEach items="${trialDTO.summaryFourOrgIdentifiers}" var="summaryFourOrgIdentifiers">
                                        <c:out value="${summaryFourOrgIdentifiers.orgName}"/><br/>
                                    </c:forEach>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="update.proprietary.trial.consortiaTrialCategoryCode" noLabelTag="true">
                                    <c:out value="${empty trialDTO.consortiaTrialCategoryCode?'No':'Yes - '}"/>
                                    <c:out value="${trialDTO.consortiaTrialCategoryCode}"/>
                                </reg-web:valueRow>
                            </c:if>
                        </table>
                        <c:if test="${trialDTO.participatingSitesList != null && fn:length(trialDTO.participatingSitesList) > 0}">
                            <div class="box">
                                <h3>Participating sites </h3>
                                <display:table class="data" sort="list"  uid="row"  name="${trialDTO.participatingSitesList}" >
                                    <display:column title="Organization Name" property="name"  headerClass="sortable"/>
                                    <display:column title="Site Principal Investigator" property="investigator"  headerClass="sortable"/>
                                    <display:column title="Local Trial<br/> Identifier" property="siteLocalTrialIdentifier"  headerClass="sortable"/>
                                    <display:column title="Program Code" property="programCode"  headerClass="sortable"/>
                                    <display:column title="Current Site<br/> Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                    <display:column title="Current Site<br/> Recruitment Status Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                                    <display:column title="Date Opened <br/>for Accrual" property="dateOpenedforAccrual"  headerClass="sortable"/>
                                    <display:column title="Date Closed <br/>for Accrual" property="dateClosedforAccrual"  headerClass="sortable"/>
                                </display:table>
                            </div>
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
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <li><a href="javascript:void(0)" class="btn" onclick="editTrial();"><span class="btn_img"> <span class="edit">Edit</span></span></a></li>
                                <li><a href="javascript:void(0)" class="btn" onclick="submitTrial();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
                                <li><a href="javascript:void(0)" class="btn" onclick="printProtocol();"><span class="btn_img"><span class="print">Print</span></span></a></li>
                            </ul>
                        </del>
                    </div>
                </s:form>
            </div>
        </div>
    </body>
</html>
