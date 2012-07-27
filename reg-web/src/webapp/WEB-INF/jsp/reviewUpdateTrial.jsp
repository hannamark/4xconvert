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
                    document.forms[0].action="updateTrialupdate.action";
                    document.forms[0].submit();
                    showPopWin('${amendProtocol}', 600, 200, '', 'Update Trial');
                }
            }
            
            function editTrial() {
                var assignedId = document.getElementById("trialDTO.assignedIdentifier").value ;
                if (assignedId != '') {
                    document.forms[0].action="updateTrialedit.action";
                    document.forms[0].submit();
                }
            }
            
            function printProtocol () {
                var sOption = "toolbar=no,location=no,directories=no,menubar=yes,";
                sOption += "scrollbars=yes,width=750,height=600,left=100,top=25";
                var sWinHTML = document.getElementById('contentprint').innerHTML;
                var winprint = window.open("", "", sOption);
                winprint.document.open();
                winprint.document.write("<html><body>");
                winprint.document.write(sWinHTML);
                winprint.document.write("</body></html>");
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
                        <reg-web:titleRow titleKey="view.trial.trialIDs"/>
                        <c:if test="${trialDTO.assignedIdentifier !=null && trialDTO.assignedIdentifier!= ''}">
                            <reg-web:valueRow labelKey="view.trial.nciAccessionNumber" strong="true">
                                <c:out value="${trialDTO.assignedIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <reg-web:valueRow labelKey="view.trial.leadOrgTrialIdentifier">
                            <c:out value="${trialDTO.leadOrgTrialIdentifier}"/>
                        </reg-web:valueRow>
                        <c:if test="${trialDTO.nctIdentifier != null}">
                            <reg-web:valueRow labelKey="view.trial.nctNumber">
                                <c:out value="${trialDTO.nctIdentifier}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIds.jsp" %>
                        <reg-web:titleRow titleKey="view.trial.trialDetails"/>
                        <reg-web:valueRow labelKey="view.trial.primaryPurpose">
                            <c:out value="${trialDTO.primaryPurposeCode}"/>
                        </reg-web:valueRow>
                        <c:if test="${trialDTO.primaryPurposeCode == 'Other'}">
                            <reg-web:valueRow labelKey="view.trial.otherPurposeText">
                                <c:out value="${trialDTO.primaryPurposeOtherText}"/>
                            </reg-web:valueRow>
                        </c:if>
                        <reg-web:spaceRow/>
                        <c:if test="${trialDTO.xmlRequired == true}">
                            <c:if test="${trialDTO.sponsorIdentifier != null}">
                                <reg-web:titleRow titleKey="view.trial.sponsorResParty"/>
                                <reg-web:valueRow labelKey="view.trial.respParty">
                                    <c:out value="${func:capitalize(trialDTO.responsiblePartyType)}"/>
                                </reg-web:valueRow>
                                <c:if test="${fn:trim(trialDTO.responsiblePersonName) != ''}">
                                    <reg-web:valueRow labelKey="view.trial.respPartyContact">
                                        <c:out value="${trialDTO.responsiblePersonName}"/>
                                    </reg-web:valueRow>
                                </c:if>
                                <c:if test="${fn:trim(trialDTO.responsibleGenericContactName) != ''}">
                                    <reg-web:valueRow labelKey="view.trial.respPartyContact">
                                        <c:out value="${trialDTO.responsibleGenericContactName}"/>
                                    </reg-web:valueRow>
                                </c:if>
                                <reg-web:valueRow labelKey="view.trial.respPartyEmailAddr">
                                    <c:out value="${trialDTO.contactEmail}"/>
                                </reg-web:valueRow>
                                <reg-web:valueRow labelKey="view.trial.respPartyPhone">
                                    <c:out value="${trialDTO.contactPhone}"/>
                                </reg-web:valueRow>
                                <reg-web:spaceRow/>
                            </c:if>
                        </c:if>
                        <c:if test="${fn:trim(trialDTO.summaryFourOrgName) != ''}">
                            <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            <reg-web:valueRow labelKey="view.trial.FundingCategory">
                                <c:out value="${trialDTO.summaryFourFundingCategoryCode }"/>
                            </reg-web:valueRow>
                            <reg-web:valueRow labelKey="view.trial.FundingSponsor">
                                <c:out value="${trialDTO.summaryFourOrgName }"/>
                            </reg-web:valueRow>
                        </c:if>
                        <c:if test="${fn:trim(trialDTO.programCodeText) != ''}">
                            <c:if test="${fn:trim(trialDTO.summaryFourOrgName) == ''}">
                                <reg-web:titleRow titleKey="view.trial.Summary4Information"/>
                            </c:if>
                            <reg-web:valueRow labelKey="studyProtocol.summaryFourPrgCode">
                                <c:out value="${trialDTO.programCodeText}"/>
                            </reg-web:valueRow>
                            <reg-web:spaceRow/>
                        </c:if>
                        <%@ include file="/WEB-INF/jsp/nodecorate/viewStatusSection.jsp" %>
                    </table>
                    <c:if test="${trialDTO.xmlRequired == true}">
                        <h3>Regulatory Information</h3>
                        <table>
                            <reg-web:valueRow labelKey="regulatory.oversight.country.name">
                                <s:property value="trialDTO.trialOversgtAuthCountryName"/>
                            </reg-web:valueRow>
                            <!--  Trial Oversight Authority Organization Name -->
                            <reg-web:valueRow labelKey="regulatory.oversight.auth.name">
                                <s:property value="trialDTO.trialOversgtAuthOrgName"/>
                            </reg-web:valueRow>
                            <!--   FDA Regulated Intervention Indicator-->
                            <reg-web:valueRow labelKey="regulatory.FDA.regulated.interv.ind">
                                <s:property value="trialDTO.fdaRegulatoryInformationIndicator"/>
                            </reg-web:valueRow>
                            <!--   Section 801 Indicator-->
                            <reg-web:valueRow id="sec801row" labelKey="regulatory.section801.ind">
                                <s:property value="trialDTO.section801Indicator"/>
                            </reg-web:valueRow>
                            <!--   Delayed Posting Indicator-->
                            <reg-web:valueRow id="delpostindrow" labelKey="regulatory.delayed.posting.ind">
                                <s:property value="trialDTO.delayedPostingIndicator"/>
                            </reg-web:valueRow>
                            <!--   Data Monitoring Committee Appointed Indicator -->
                            <reg-web:valueRow id="datamonrow" labelKey="regulatory.data.monitoring.committee.ind">
                                <s:property value="trialDTO.dataMonitoringCommitteeAppointedIndicator"/>
                            </reg-web:valueRow>
                        </table>
                    </c:if>
                    <c:if test="${trialDTO.indIdeUpdateDtos != null && fn:length(trialDTO.indIdeUpdateDtos) > 0}">
                        <div class="box">
                            <h3>Updated FDA IND/IDE </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.indIdeUpdateDtos}">
                                <display:column title="IND/IDE Type" property="indIde"  headerClass="sortable" style="width:75px" />
                                <display:column escapeXml="true" title="Number" property="number"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="NIH Inst Holder Code" property="nihInstHolder"  headerClass="sortable"  style="width:75px"/>
                                <display:column escapeXml="true" title="NCI Div Prog Holder Code" property="nciDivProgHolder" headerClass="sortable"  style="width:75px"/>
                                <display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.indIdeAddDtos != null && fn:length(trialDTO.indIdeAddDtos) > 0}">
                        <div class="box">
                            <h3>Added FDA IND/IDE </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.indIdeAddDtos}">
                                <display:column title="IND/IDE Type" property="indIde"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Number" property="number"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Grantor" property="grantor"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Holder" property="holderType"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access?" property="expandedAccess"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Expanded Access Type" property="expandedAccessType"  headerClass="sortable" style="width:75px" />
                                <display:column title="Exempt?" property="exemptIndicator"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.fundingDtos != null && fn:length(trialDTO.fundingDtos) > 0}">
                        <div class="box">
                            <h3>Updated Grant Information </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.fundingDtos}" >
                                <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>
                                <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.fundingAddDtos != null && fn:length(trialDTO.fundingAddDtos) > 0}">
                        <div class="box">
                            <h3>Added Grant Information </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.fundingAddDtos}" >
                                <display:column title="Funding Mechanism Type" property="fundingMechanismCode"  headerClass="sortable" style="width:75px"/>
                                <display:column title="Institute Code" property="nihInstitutionCode"  headerClass="sortable" style="width:75px"/>
                                <display:column escapeXml="true" title="Serial Number" property="serialNumber"  headerClass="sortable" style="width:75px"/>
                                <display:column title="NIH Division Program Code" property="nciDivisionProgramCode"  headerClass="sortable" style="width:75px"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.collaborators != null && fn:length(trialDTO.collaborators) > 0}">
                        <div class="box">
                            <h3>Colaborators </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.collaborators}" >
                                <display:column escapeXml="true" title="Collaborator" property="name"  headerClass="sortable"/>
                                <display:column escapeXml="true" title="Functional Role" property="functionalRole"  headerClass="sortable"/>
                            </display:table>
                        </div>
                    </c:if>
                    <c:if test="${trialDTO.participatingSites != null && fn:length(trialDTO.participatingSites) > 0}">
                        <div class="box">
                            <h3>Participating sites </h3>
                            <display:table class="data" sort="list"  uid="row"  name="${trialDTO.participatingSites}" >
                                <display:column escapeXml="true" title="Site" property="name"  headerClass="sortable"/>
                                <display:column title="Recruitment Status" property="recruitmentStatus"  headerClass="sortable"/>
                                <display:column title="Date" property="recruitmentStatusDate"  headerClass="sortable"/>
                                <display:column escapeXml="true" title="Program Code" property="programCode"  headerClass="sortable"/>
                            </display:table>
                        </div>
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
