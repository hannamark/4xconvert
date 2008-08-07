<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="cactusmenu">
<li class="liheader">Protocol Abstraction </li>
        <c:if test="${sessionScope.loggedUserName  != null}">
        <li><a href="studyProtocolQuery.action" >Search Trial</a></li>
        </c:if>
        <c:if test="${sessionScope.trialSummary  != null}">
        <li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" >General Trial Details</a></li>
        <li class="liheader">Administrative Data </li>
        <li><a href="nciSpecificInformation.action" >NCI Specific Information</a></li>
         <li><a href="regulatoryInfo.action" >Regulatory Information</a></li>
        
        <li><a href="?l" >Trial Status</a></li>
    </c:if>
        
</ul>
