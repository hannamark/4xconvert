<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--<ul class="cactusmenu"> -->


		<li class="stdnav"><div>Protocol Abstraction</div>
			<ul>
				<li><a href="#">Home</a></li>			
				<li><a href="studyProtocolQuery.action" >Protocol Search</a></li>			
<!--        		<c:if test="${sessionScope.loggedUserName  != null}"> -->
        		
<!--        		</c:if> -->

				<li><a href="#">Help</a></li>
				<li><a href="#">Logout</a></li>
        	</ul>
        </li>
        <c:if test="${sessionScope.trialSummary  != null}">
        <li class="sub"><div><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></div>
        	<ul>
				<li><div>Protocol Overview</div>
                	<ul>
        				<li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >Trial Identification</a></li>
        			</ul>
        		</li>
        		<li><div>Administrative Data</div>
        			<ul>
		        		<li><a href="nciSpecificInformationquery.action" >NCI Specific Information</a></li>
		        		<li><a href="regulatoryInfoquery.action" >Regulatory Information</a></li>      
		        		<li><a href="studyOverallStatus.action" >Trial Status</a></li>
		        		<li><a href="trialFunding.action" >Trial Funding</a></li>
                        <li><a href="participatingOrganizations.action" >Participating Organizations</a></li>
		     		</ul>
        		</li>
        	</ul>
		</li>
        		
    </c:if>
        

