<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<li class="stdnav"><div>Protocol Abstraction</div> 
	<ul>
		<li><a href="#">Home</a></li>			
		<li><a href="studyProtocolquery.action" >Trial Search</a></li>
		<c:if test="${pageContext.request.remoteUser != null}">	
			<li><a href="logout.action">Logout</a></li>
		</c:if>	
	</ul>
</li>


<c:if test="${(sessionScope.trialSummary  != null) && (sessionScope.role == 'Abstractor')}">
	<li class="sub"><div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
		<ul>
			<li><div>Trial Overview</div>
				<ul>
					<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Identification</a></li>
					<li><a href="milestone.action" >Trial Milestones</a></li>
                    <li><a href="onhold.action" >On-hold Information</a></li>
				</ul>
			</li>
			<s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
			<li><div>Validation</div>
                <ul>
                    <li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                    <li><a href="studyOverallStatus.action" >Trial Status</a></li>
                    <li><a href="trialFundingquery.action" >Trial Funding</a></li>
                    <li><a href="trialIndidequery.action" >Trial IND/IDE</a></li>
                    <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Validation</a></li>
                    <li><a href="milestone.action" >Trial Milestones</a></li>
                    <li><a href="onhold.action" >On-hold Information</a></li>
                </ul>
			</s:if>
			<s:if test="${docWFSMenu  == 'Accepted'}">
    			<li><div>Administrative Data</div>
    				<ul>
                        <li><a href="generalTrialDesignquery.action" >General Trial Details</a></li>
    					<li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
                        <li class="hassubmenu">Regulatory Information
                           <ul id="part_sites">
                                <li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>      
                                <li><a href="irb.action">Human Subject Safety</a></li>
                                <li><a href="trialIndidequery.action" >Trial IND/IDE</a></li>
                           </ul>
                        </li>    
    					<li><a href="studyOverallStatus.action" >Trial Status</a></li>
    					<li><a href="trialFundingquery.action" >Trial Funding</a></li>
                        <li><a href="participatingOrganizations.action">Participating Sites</a></li>
                        <li><a href="collaborators.action">Collaborators</a></li>
    					<li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                      </ul>
    			</li>
    			<li><div>Scientific Data</div>
    				<ul>
    				    <li><a href="trialDescriptionquery.action" >Trial Description</a></li>
                        <s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
    					<li class="hassubmenu">Interventional Trial Design
                            <ul id="part_sites">
                                <li><a href="interventionalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="interventionalStudyDesignoutcomeQuery.action" >Outcome Measures</a></li>
                                <li><a href="eligibilityCriteriaquery.action" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </s:if>
                        <s:else>
    					<li class="hassubmenu">Observational Trial Design
                            <ul id="part_sites">
                                <li><a href="observationalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="interventionalStudyDesignoutcomeQuery.action" >Outcome Measures</a></li>
                                <li><a href="eligibilityCriteriaquery.action" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </s:else>
                        <li><a href="disease.action" >Disease/Condition</a></li>
                        <li><a href="trialInterventions.action" >Interventions</a></li>
                        <s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                            <li><a href="trialArms.action" >Arms</a></li>
                        </s:if>
                        <s:else>
                            <li><a href="trialArmsobservational.action" >Groups</a></li>
                        </s:else>
                        <li><a href="subGroupsquery.action" >Sub-groups</a></li>
    				</ul>
    			</li>
			<li><div>Completion</div>
				<ul>
					<li><a href="abstractionCompletionquery.action" >Abstraction Validation</a></li>
				</ul>
			</li>	
            </s:if>
		</ul>
	</li>
</c:if>
        

