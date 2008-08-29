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
        <ul>
        <li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >General Trial Details</a></li>
        </ul>
        <li class="stdnav"><div>Administrative Data</div>
        	<ul>
		        <li><a href="nciSpecificInformation.action" >NCI Specific Information</a></li>
		        <li><a href="regulatoryInfo.action" >Regulatory Information</a></li>      
		        <li><a href="studyOverallStatus.action" >Trial Status</a></li>
		     </ul>
        </li>
    </c:if>
        

