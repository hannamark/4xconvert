<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<s:form name="qForm">
<li class="stdnav"><div>Protocol Abstraction</div> 
	<ul>
		<li><a href="studyProtocolexecute.action" >Trial Search</a></li>
		<li><a href="inboxProcessingexecute.action" >Inbox</a></li>
		<c:if test="${pageContext.request.remoteUser != null}">	
			<li><a href="logout.action">Logout</a></li>
		</c:if>	
	</ul>
</li>


<c:if test="${(sessionScope.trialSummary  != null) && (sessionScope.role == 'Abstractor' || sessionScope.role == 'SuAbstractor')}">
	<li class="sub"><div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
		<ul>
			<li><div>Trial Overview</div>
				<ul>
					<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Identification</a></li>
					<li><a href="trialHistory.action" >Trial History</a></li>
					<li><a href="milestone.action" >Trial Milestones</a></li>
                    <li><a href="onhold.action" >On-hold Information</a></li>
                    <li><a href="manageAccrualAccess.action">Manage Accrual Access</a></li>
                     <li><a href="#" onclick="generateTSRWord();" >View TSR</a></li>
				</ul>
			</li>
			<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted' 
			|| sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Amendment Submitted'}">
			<div>Validation</div>
                <ul>
                    <li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                    <c:choose>
                    <c:when test="${sessionScope.trialSummary.isProprietaryTrial == 'true'}">
                        <li><a href="participatingOrganizations.action?trialType=proprietary">Participating Sites</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="studyOverallStatus.action" >Trial Status</a></li>
                        <li><a href="trialFundingquery.action" >Trial Funding</a></li>
                        <li><a href="trialIndidequery.action" >Trial IND/IDE</a></li>
                    </c:otherwise>
                    </c:choose>
                    <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Validation</a></li>
                  </ul>
			</c:if>
			<c:if test="${docWFSMenu  == 'Accepted'}">
    			<li><div>Administrative Data</div>
    				<ul>
    				    <li><a href="generalTrialDesignquery.action" >General Trial Details</a></li>
    					<li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <li class="hassubmenu">Regulatory Information
                           <ul id="part_sites">
                                <li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>      
                                <li><a href="irb.action">Human Subject Safety</a></li>
                                <li><a href="trialIndidequery.action" >Trial IND/IDE</a></li>
                           </ul>
                        </li>
                         
                            <li><a href="studyOverallStatus.action" >Trial Status</a></li>
                            <li><a href="trialFundingquery.action" >Trial Funding</a></li>
                        </c:if>    
    					
                        <li><a href="participatingOrganizations.action">Participating Sites</a></li>
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <li><a href="collaborators.action">Collaborators</a></li>
                        </c:if>
    					<li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                      </ul>
    			</li>
    			<li><div>Scientific Data</div>
    				<ul>
    				<c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
    				    <li><a href="trialDescriptionquery.action" >Trial Description</a></li>
    				    <c:choose>
                        <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
    					<li class="hassubmenu">Interventional Trial Design
                            <ul id="part_sites">
                                <li><a href="interventionalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="interventionalStudyDesignoutcomeQuery.action" >Outcome Measures</a></li>
                                <li><a href="eligibilityCriteriaquery.action" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </c:when>
                        <c:otherwise>
    					<li class="hassubmenu">Observational Trial Design
                            <ul id="part_sites">
                                <li><a href="observationalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="interventionalStudyDesignoutcomeQuery.action" >Outcome Measures</a></li>
                                <li><a href="eligibilityCriteriaquery.action" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </c:otherwise>
                        </c:choose>
                        </c:if>
                        <li><a href="disease.action" >Disease/Condition</a></li>
                        <li><a href="trialInterventions.action" >Interventions</a></li>
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <c:choose>
                        <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                            <li><a href="trialArms.action" >Arms</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="trialArmsobservational.action" >Groups</a></li>
                        </c:otherwise>
                        </c:choose>
                        <li><a href="subGroupsquery.action" >Sub-groups</a></li>
                        </c:if>
    				</ul>
    			</li>
			<li><div>Completion</div>
				<ul>
					<li><a href="abstractionCompletionquery.action" >Abstraction Validation</a></li>
				</ul>
			</li>	
            </c:if>
		</ul>
	</li>
</c:if>
</s:form>        
<SCRIPT LANGUAGE="JavaScript">

function generateTSRWord() {
  document.qForm.target = "TSR";
   document.qForm.action = "/pa/protected/ajaxAbstractionCompletionviewTSRWord.action";
   document.qForm.submit();

}
</SCRIPT>
