<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
			<li><div>Administrative Data</div>
				<ul>
					<li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
					<li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>      
					<li><a href="studyOverallStatus.action" >Trial Status</a></li>
					<li><a href="trialFundingquery.action" >Trial Funding</a></li>
                    <li class="sub"><a href="participatingOrganizations.action" style="color: black;">Participating Sites</a>
                        <ul>
                            <li><a href="participatingOrganizations.action" style="text-indent: .5cm">Treating Sites</a></li>
                            <li><a href="collaborators.action" style="text-indent: .5cm">Collaborators</a></li>
                        </ul>
                    </li>    
					<li><a href="trialDocumentquery.action" >Trial Related Documents</a></li>
				</ul>
			</li>
			<li><div>Scientific Data</div>
				<ul>
					<li><a href="subGroupsquery.action" >SubGroups</a></li>
					<li class="sub"><a href="interventionalStudyDesigndetailsQuery.action" style="color: black;">Interventional Trial Design </a>
                        <ul>
                            <li><a href="interventionalStudyDesigndetailsQuery.action" style="text-indent: .5cm">Design Details</a></li>
                            <li><a href="#" style="text-indent: .5cm">Outcome Measures</a></li>
                            <li><a href="#" style="text-indent: .5cm">Eligibility Criteria</a></li>
                        </ul>
					</li>
					<li class="sub"><a href="observationalStudyDesigndetailsQuery.action" style="color: black;">Observational Trial Design </a>
                        <ul>
                            <li><a href="observationalStudyDesigndetailsQuery.action" style="text-indent: .5cm">Design Details</a></li>
                            <li><a href="#" style="text-indent: .5cm">Outcome Measures</a></li>
                            <li><a href="#" style="text-indent: .5cm">Eligibility Criteria</a></li>
                        </ul>
					</li>
				</ul>
			</li>
		</ul>
	</li>
</c:if>
        

