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


<c:if test="${sessionScope.trialSummary  != null}">
	<li class="sub"><div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
		<ul>
			<li><div>Trial Overview</div>
				<ul>
					<li><a href="studyProtocolview.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Identification</a></li>
				</ul>
			</li>
			
			<s:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Accepted'}">
    			<li><div>Administrative Data</div>
    				<ul>
    					<li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
    					<li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>      
    					<li><a href="studyOverallStatus.action" >Trial Status</a></li>
    					<li><a href="trialFundingquery.action" >Trial Funding</a></li>
                        <li class="hassubmenu">Participating Sites
                           <ul id="part_sites">
                                <li><a href="participatingOrganizations.action">Treating Sites</a></li>
                                <li><a href="collaborators.action">Collaborators</a></li>
                            </ul>
                        </li>    
    					<li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
    				</ul>
    			</li>
    			<li><div>Scientific Data</div>
    				<ul>
    					<li><a href="subGroupsquery.action" >SubGroups</a></li>
                        <s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
    					<li class="hassubmenu">Interventional Trial Design
                            <ul id="part_sites">
                                <li><a href="interventionalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="#" >Outcome Measures</a></li>
                                <li><a href="#" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </s:if>
                        <s:else>
    					<li class="hassubmenu">Observational Trial Design
                            <ul id="part_sites">
                                <li><a href="observationalStudyDesigndetailsQuery.action" >Design Details</a></li>
                                <li><a href="#" >Outcome Measures</a></li>
                                <li><a href="#" >Eligibility Criteria</a></li>
                            </ul>
    					</li>
                        </s:else>
    				</ul>
    			</li>
            </s:if>

		</ul>
	</li>
</c:if>
        

