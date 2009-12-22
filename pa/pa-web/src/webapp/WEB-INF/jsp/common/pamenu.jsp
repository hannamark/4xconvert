<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<s:form name="qForm">
<li class="stdnav"><div>Protocol Abstraction</div> 
	<ul>
	 <c:choose>
        <c:when test="${requestScope.topic == 'search_trial'}">
            <li><a href="studyProtocolexecute.action" class="selected">Trial Search</a></li>
        </c:when>
        <c:otherwise>
            <li><a href="studyProtocolexecute.action" >Trial Search</a></li>
        </c:otherwise>
     </c:choose>
		<c:choose>
        <c:when test="${requestScope.topic == 'inbox_process'}">
     		<li><a href="inboxProcessingexecute.action" class="selected" >Inbox</a></li>
     	</c:when>
     	<c:when test="${requestScope.topic == 'inbox_access'}">
            <li><a href="inboxProcessingexecute.action" class="selected" >Inbox</a></li>
        </c:when>
     	<c:otherwise>
     	  <li><a href="inboxProcessingexecute.action" >Inbox</a></li>
     	</c:otherwise>	
     	</c:choose>
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
				    <c:choose>
				        <c:when test="${requestScope.topic == 'trial_details'}">
				        <li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="selected">Trial Identification</a></li>
				        </c:when>
				        <c:otherwise>
				        <li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Identification</a></li>
				        </c:otherwise>
				    </c:choose>
					<c:choose>
					   <c:when test="${requestScope.topic == 'trial_history'}">
					       <li><a href="trialHistory.action" class="selected">Trial History</a></li>
					   </c:when>
					   <c:otherwise>
					       <li><a href="trialHistory.action" >Trial History</a></li>
					   </c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${requestScope.topic == 'trial_milestones'}">
						   <li><a href="milestone.action" class="selected">Trial Milestones</a></li>
						</c:when>
						<c:otherwise>
						   <li><a href="milestone.action" >Trial Milestones</a></li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${requestScope.topic == 'trial_onhold'}">
						   <li><a href="onhold.action" class="selected">On-hold Information</a></li>
						</c:when>
						<c:otherwise>
						   <li><a href="onhold.action" >On-hold Information</a></li>
						</c:otherwise>
					</c:choose>
					<c:choose>
					   <c:when test="${requestScope.topic == 'accrual_access'}">
					       <li><a href="manageAccrualAccess.action" class="selected">Manage Accrual Access</a></li>
					   </c:when>
						<c:otherwise>
						   <li><a href="manageAccrualAccess.action">Manage Accrual Access</a></li>
						</c:otherwise>
					</c:choose>
                     <li><a href="#" onclick="generateTSRWord();" >View TSR</a></li>
				</ul>
			</li>
			<c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted' 
			|| sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Amendment Submitted'}">
			<div>Validation</div>
                <ul>
                    <c:choose>
                        <c:when test="${requestScope.topic == 'review_docs'}">
                            <li><a href="trialDocumentquery.action" class="selected">Trial Related Documents</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                        </c:otherwise>
                    </c:choose>
                    
                    <c:choose>
                    <c:when test="${sessionScope.trialSummary.isProprietaryTrial == 'true'}">
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_site'}">
                                <li><a href="participatingOrganizations.action?trialType=proprietary" class="selected">Participating Sites</a></li>
                            </c:when>
                            <c:otherwise>
                               <li><a href="participatingOrganizations.action?trialType=proprietary">Participating Sites</a></li>
                            </c:otherwise>    
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${requestScope.topic == 'review_status'}">
                                <li><a href="studyOverallStatus.action" class="selected">Trial Status</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="studyOverallStatus.action" >Trial Status</a></li>
                            </c:otherwise>
                         </c:choose>
                         <c:choose>
                            <c:when test="${requestScope.topic == 'review_funding'}">
                                <li><a href="trialFundingquery.action" class="selected">Trial Funding</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="trialFundingquery.action" >Trial Funding</a></li>
                            </c:otherwise>
                         </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.topic == 'review_ind'}">
                                <li><a href="trialIndidequery.action" class="selected">Trial IND/IDE</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="trialIndidequery.action">Trial IND/IDE</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                    </c:choose>
                    <c:choose>
	                    <c:when test="${requestScope.topic == 'validate_trial'}">
	                    <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="selected">Trial Validation</a></li>
	                    </c:when>
	                    <c:otherwise>
	                        <li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Validation</a></li>
	                    </c:otherwise>
                    </c:choose>
                  </ul>
			</c:if>
			<c:if test="${docWFSMenu  == 'Accepted'}">
    			<li><div>Administrative Data</div>
    				<ul>
    				    <c:choose>
    				        <c:when test="${requestScope.topic =='abstract_general'}">
    				            <li><a href="generalTrialDesignquery.action"  class="selected">General Trial Details</a></li>
    				        </c:when>
    				        <c:otherwise>
    				            <li><a href="generalTrialDesignquery.action" >General Trial Details</a></li>
    				        </c:otherwise>
    				    </c:choose>
    				    <c:choose>
    				         <c:when test="${requestScope.topic =='abstract_nci'}">
    				            <li><a href="nciSpecificInformationquery.action" class="selected" >NCI Specific Information</a></li>
    				         </c:when>
   				             <c:otherwise>
   				                 <li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
   				             </c:otherwise>
    				    </c:choose>
    				    
    					
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <li class="hassubmenu">Regulatory Information
                           <ul id="part_sites">
                                <c:choose>
                                    <c:when test="${requestScope.topic =='abstract_regulatory'}">
                                        <li><a href="regulatoryInfoquery.action" class="selected">Regulatory Information</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                <c:when test="${requestScope.topic == 'abstract_safety'}">
                                    <li><a href="irb.action" class="selected">Human Subject Safety</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="irb.action">Human Subject Safety</a></li>
                                </c:otherwise>
                                </c:choose>      
                                
                                <c:choose>
	                               <c:when test="${requestScope.topic == 'abstract_ind'}">
                                        <li><a href="trialIndidequery.action" class="selected">Trial IND/IDE</a></li>
                                    </c:when>
	                                <c:otherwise>
	                                   <li><a href="trialIndidequery.action" >Trial IND/IDE</a></li>
	                                </c:otherwise>
                                </c:choose>
                           </ul>
                        </li>
                         <c:choose>
                           <c:when test="${requestScope.topic == 'abstract_status'}">
                                <li><a href="studyOverallStatus.action" class="selected">Trial Status</a></li>
                           </c:when>
                            <c:otherwise>
                                <li><a href="studyOverallStatus.action" >Trial Status</a></li>
                            </c:otherwise>
                       </c:choose>
                       <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_funding'}">
                                <li><a href="trialFundingquery.action" class="selected">Trial Funding</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="trialFundingquery.action" >Trial Funding</a></li>
                            </c:otherwise>
                        </c:choose>
                        </c:if>    
    					<c:choose>
                            <c:when test="${requestScope.topic == 'abstract_site'}">
                                <li><a href="participatingOrganizations.action" class="selected">Participating Sites</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="participatingOrganizations.action">Participating Sites</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_collaborator'}">
                                <li><a href="collaborators.action" class="selected">Collaborators</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="collaborators.action">Collaborators</a></li>
                            </c:otherwise>
                        </c:choose>
                        
                        </c:if>
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_docs'}">
                                <li><a href="trialDocumentquery.action" class="selected">Trial Related Documents</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
                            </c:otherwise>
                        </c:choose>
                      </ul>
    			</li>
    			<li><div>Scientific Data</div>
    				<ul>
    				<c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
    				    <c:choose>
    				        <c:when test="${requestScope.topic == 'abstract_description'}">
    				            <li><a href="trialDescriptionquery.action" class="selected">Trial Description</a></li>
    				        </c:when>
    				        <c:otherwise>
    				            <li><a href="trialDescriptionquery.action" >Trial Description</a></li>
    				        </c:otherwise>
    				    </c:choose>
    				    <c:choose>
                        <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
    					<li class="hassubmenu">Interventional Trial Design
                            <ul id="part_sites">
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'abstract_design'}">
                                        <li><a href="interventionalStudyDesigndetailsQuery.action" class="selected">Design Details</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="interventionalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                    </c:otherwise>
                                </c:choose>
                                
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'abstract_outcome'}" >
                                        <li><a href="interventionalStudyDesignoutcomeQuery.action" class="selected">Outcome Measures</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="interventionalStudyDesignoutcomeQuery.action" >Outcome Measures</a></li>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.topic == 'abstract_eligibility'}">
                                        <li><a href="eligibilityCriteriaquery.action" class="selected">Eligibility Criteria</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a href="eligibilityCriteriaquery.action" >Eligibility Criteria</a></li>
                                    </c:otherwise>
                                </c:choose>
                                
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
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_disease'}">
                                <li><a href="disease.action" class="selected">Disease/Condition</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="disease.action" >Disease/Condition</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_interventions'}">
                                <li><a href="trialInterventions.action" class="selected" >Interventions</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="trialInterventions.action">Interventions</a></li>
                            </c:otherwise>
                        </c:choose>
                        
                        <c:if test="${sessionScope.trialSummary.isProprietaryTrial == null || sessionScope.trialSummary.isProprietaryTrial == 'false'}">
                        <c:choose>
                        <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                            <c:choose>
                                <c:when test="${requestScope.topic == 'abstract_arms'}">
                                    <li><a href="trialArms.action" class="selected">Arms</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li><a href="trialArms.action" >Arms</a></li>    
                                </c:otherwise>
                            </c:choose>
                            
                        </c:when>
                        <c:otherwise>
                            <li><a href="trialArmsobservational.action" >Groups</a></li>
                        </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.topic == 'abstract_subgroups'}">
                                <li><a href="subGroupsquery.action" class="selected">Sub-groups</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="subGroupsquery.action" >Sub-groups</a></li>
                            </c:otherwise>
                        </c:choose>
                        </c:if>
    				</ul>
    			</li>
			<li><div>Completion</div>
				<ul>
				    <c:choose>
				        <c:when test="${requestScope.topic == 'validate_abstract'}">
				            <li><a href="abstractionCompletionquery.action" class="selected">Abstraction Validation</a></li>
				        </c:when>
				        <c:otherwise>
				            <li><a href="abstractionCompletionquery.action" >Abstraction Validation</a></li>
				        </c:otherwise>
				    </c:choose>
					
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
