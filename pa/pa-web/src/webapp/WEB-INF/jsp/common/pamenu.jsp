<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<script type="text/javascript">
    jQuery(function() {
        jQuery("#viewTsrMenuOption").bind("click", function(ev) {
            var form = document.qForm;
            form.target = "TSR";
            form.action = paApp.contextPath + "/protected/ajaxAbstractionCompletionviewTSR.action";
            form.submit();
        });
    });
</script>

<s:form name="qForm">
    <li class="stdnav">
        <div><fmt:message key="pamenu.abstraction"/></div>
        <ul>
            <pa:menuLink href="studyProtocolexecute.action" id="trialSearchMenuOption" labelKey="pamenu.abstraction.search" selected="${requestScope.topic == 'searchtrial'}"/>
            <pa:menuLink href="inboxProcessingexecute.action" id="inboxProcessingMenuOption" labelKey="pamenu.abstraction.inbox" selected="${requestScope.topic == 'inboxprocess' || requestScope.topic == 'inboxaccess'}"/>
            <c:if test="${pageContext.request.remoteUser != null}">
                <pa:menuLink href="logout.action" id="logoutMenuOption" labelKey="pamenu.abstraction.logout"/>
            </c:if>
        </ul>
    </li>

    <c:if test="${sessionScope.trialSummary != null && (sessionScope.isAbstractor || sessionScope.isSuAbstractor)}">
        <c:set var="status" value="${ (sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'On-Hold') ? sessionScope.trialSummary.lastOffHollStatusCode.code : sessionScope.trialSummary.documentWorkflowStatusCode.code}"/>
        <c:choose>
            <c:when test="${status == 'Submitted' || status == 'Amendment Submitted'}">
                <c:set var="menuStatus" value="submitted" />
            </c:when>
            <c:when test="${status == 'Rejected'}">
                <c:set var="menuStatus" value="rejected" />
            </c:when>
            <c:otherwise>
                <c:set var="menuStatus" value="accepted" />
            </c:otherwise>
        </c:choose>
        <li class="sub">
            <div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
            <ul>
                <li>
                    <div><fmt:message key="pamenu.overview"/></div>
                    <ul>
                        <pa:menuLink href="studyProtocolview.action?studyProtocolId=${sessionScope.trialSummary.studyProtocolId}" labelKey="pamenu.overview.identification" selected="${requestScope.topic == 'trialdetails'}"/>
                        <pa:menuLink href="trialHistory.action" labelKey="pamenu.overview.history" selected="${requestScope.topic == 'trialhistory'}"/>
                        <pa:menuLink href="milestone.action" labelKey="pamenu.overview.milestone" selected="${requestScope.topic == 'trialmilestones'}"/>
                        <pa:menuLink href="onhold.action" labelKey="pamenu.overview.onhold" selected="${requestScope.topic == 'trialonhold'}"/>
                        <pa:menuLink href="manageAccrualAccess.action" labelKey="pamenu.overview.accrualaccess" selected="${requestScope.topic == 'accrualaccess'}"/>
                        <pa:menuLink href="#" id="viewTsrMenuOption" labelKey="pamenu.overview.viewTsr"/>
                        <pa:menuLink href="assignOwnershipview.action" labelKey="pamenu.overview.assignOwnership"/>
                        <pa:menuLink href="auditTrail.action" labelKey="pamenu.overview.auditTrail" selected="${requestScope.topic == 'auditTrail'}"/>
                    </ul>
                </li>
                <c:if test="${menuStatus == 'submitted'}">
                    <div><fmt:message key="pamenu.validation"/></div>
                    <ul>
                        <pa:menuLink href="trialDocumentquery.action" labelKey="pamenu.admin.documents" selected="${requestScope.topic == 'reviewdocs'}"/>
                        <c:choose>
                            <c:when test="${sessionScope.trialSummary.proprietaryTrial}">
                                <pa:menuLink href="participatingOrganizations.action?trialType=proprietary" labelKey="pamenu.admin.participatingSites" selected="${requestScope.topic == 'abstractsite'}"/>
                            </c:when>
                            <c:otherwise>
                                <pa:menuLink href="studyOverallStatus.action" labelKey="pamenu.admin.status" selected="${requestScope.topic == 'reviewstatus'}"/>
                                <pa:menuLink href="trialFundingquery.action" labelKey="pamenu.admin.funding" selected="${requestScope.topic == 'reviewfunding'}"/>
                                <pa:menuLink href="trialIndidequery.action" labelKey="pamenu.admin.indide" selected="${requestScope.topic == 'reviewind'}"/> 
                                <pa:menuLink href="regulatoryInfoquery.action" labelKey="pamenu.admin.regulatory" selected="${requestScope.topic == 'reviewregulatory'}"/> 
                            </c:otherwise>
                        </c:choose>
                        <pa:menuLink href="trialValidationquery.action?studyProtocolId=${sessionScope.trialSummary.studyProtocolId}" labelKey="pamenu.validation.validation" selected="${requestScope.topic == 'validatetrial'}"/>
                    </ul>
                </c:if>
                <c:if test="${menuStatus == 'accepted'}">
                    <li>
                        <div><fmt:message key="pamenu.admin"/></div>
                        <ul>
                            <pa:menuLink href="generalTrialDesignquery.action" labelKey="pamenu.admin.general" selected="${requestScope.topic == 'abstractgeneral'}"/> 
                            <pa:menuLink href="nciSpecificInformationquery.action" labelKey="pamenu.admin.nciSpecific" selected="${requestScope.topic == 'abstractnci'}"/> 
                            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                <li class="hassubmenu">
                                    <fmt:message key="pamenu.admin.regulatory"/>
                                    <ul id="part_sites">
                                        <pa:menuLink href="regulatoryInfoquery.action" labelKey="pamenu.admin.regulatory" selected="${requestScope.topic == 'abstractregulatory'}"/> 
                                        <pa:menuLink href="irb.action" labelKey="pamenu.admin.safety" selected="${requestScope.topic == 'abstractsafety'}"/>
                                        <pa:menuLink href="trialIndidequery.action" labelKey="pamenu.admin.indide" selected="${requestScope.topic == 'abstractind'}"/> 
                                   </ul>
                                </li>
                                <pa:menuLink href="studyOverallStatus.action" labelKey="pamenu.admin.status" selected="${requestScope.topic == 'abstractstatus'}"/>
                                <pa:menuLink href="trialFundingquery.action" labelKey="pamenu.admin.funding" selected="${requestScope.topic == 'abstractfunding'}"/> 
                            </c:if>
                            <pa:menuLink href="participatingOrganizations.action" labelKey="pamenu.admin.participatingSites" selected="${requestScope.topic == 'abstractsite'}"/>
                            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                <pa:menuLink href="collaborators.action" labelKey="pamenu.admin.collaborators" selected="${requestScope.topic == 'abstractcollaborator'}"/>
                            </c:if>
                            <pa:menuLink href="trialDocumentquery.action" labelKey="pamenu.admin.documents" selected="${requestScope.topic == 'abstractdocs'}"/>
                        </ul>
                    </li>
                    <li>
                        <div><fmt:message key="pamenu.scientific"/></div>
                        <ul>
                            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                <pa:menuLink href="trialDescriptionquery.action" labelKey="pamenu.scientific.description" selected="${requestScope.topic == 'abstractdescription'}"/>
                                <c:choose>
                                    <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                                        <li class="hassubmenu">
                                            <fmt:message key="pamenu.scientific.interventionalDesign"/>
                                            <ul id="part_sites">
                                                <pa:menuLink href="interventionalStudyDesigndetailsQuery.action" labelKey="pamenu.scientific.design" selected="${requestScope.topic == 'abstractdesign'}"/>
                                                <pa:menuLink href="interventionalStudyDesignoutcomeQuery.action" labelKey="pamenu.scientific.outcome" selected="${requestScope.topic == 'abstractoutcome'}"/>
                                                <pa:menuLink href="eligibilityCriteriaquery.action" labelKey="pamenu.scientific.eligibility" selected="${requestScope.topic == 'abstracteligibility'}"/>
                                            </ul>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="hassubmenu">
                                            <fmt:message key="pamenu.scientific.observationalDesign"/>
                                            <ul id="part_sites">
                                                <pa:menuLink href="observationalStudyDesigndetailsQuery.action" labelKey="pamenu.scientific.design" selected="${requestScope.topic == 'abstractdesign'}"/>
                                                <pa:menuLink href="interventionalStudyDesignoutcomeQuery.action" labelKey="pamenu.scientific.outcome" selected="${requestScope.topic == 'abstractoutcome'}"/>
                                                <pa:menuLink href="eligibilityCriteriaquery.action" labelKey="pamenu.scientific.eligibility" selected="${requestScope.topic == 'abstracteligibility'}"/>
                                            </ul>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <pa:menuLink href="disease.action" labelKey="pamenu.scientific.disease" selected="${requestScope.topic == 'abstractdisease'}"/>
                            <pa:menuLink href="anatomicSite.action" labelKey="pamenu.scientific.anatomicSite" selected="${requestScope.topic == 'abstractanatomicsite'}"/>
                            <pa:menuLink href="plannedMarker.action" labelKey="pamenu.scientific.markers" selected="${requestScope.topic == 'abstractmarkers'}"/>
                            <pa:menuLink href="trialInterventions.action" labelKey="pamenu.scientific.interventions" selected="${requestScope.topic == 'abstractinterventions'}"/>
                            <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                <c:choose>
                                    <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                                        <pa:menuLink href="trialArms.action" labelKey="pamenu.scientific.arms" selected="${requestScope.topic == 'abstractarms'}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <pa:menuLink href="trialArmsobservational.action" labelKey="pamenu.scientific.groups"/>
                                    </c:otherwise>
                                </c:choose>
                                <pa:menuLink href="subGroupsquery.action" labelKey="pamenu.scientific.subGroups" selected="${requestScope.topic == 'abstractsubgroups'}"/>
                            </c:if>
                        </ul>
                    </li>
                    <li>
                        <div><fmt:message key="pamenu.completion"/></div>
                        <ul>
                            <pa:menuLink href="abstractionCompletionquery.action" labelKey="pamenu.completion.abstraction" selected="${requestScope.topic == 'validateabstract'}"/>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </li>
    </c:if>
</s:form>
