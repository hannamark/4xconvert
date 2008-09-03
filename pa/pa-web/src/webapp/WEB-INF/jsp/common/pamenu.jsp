<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--<ul class="cactusmenu"> -->


		<li class="stdnav"><div>Protocol Abstraction</div> 
        	<ul>
        		<c:if test="${sessionScope.loggedUserName  != null}">
        		<li><a href="studyProtocolQuery.action" >Search Trial</a></li>
        		</c:if>
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
		        		<li><a href="nciSpecificInformation.action" >NCI Specific Information</a></li>
		        		<li><a href="regulatoryInfo.action" >Regulatory Information</a></li>      
		        		<li><a href="studyOverallStatus.action" >Trial Status</a></li>
		     		</ul>
        		</li>
        	</ul>
		</li>
        		
    </c:if>
        

