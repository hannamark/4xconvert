<%@ tag display-name="displayWhenCheckedOut" 
    description="Renders the enclosed mark-up only if the study is checked out by the current user and that user is a scientific abstractor" body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<c:if test="${sessionScope.trialSummary.studyScientificCheckoutBy != null &&
              sessionScope.loggedUserName == sessionScope.trialSummary.studyScientificCheckoutBy && (sessionScope.isScientificAbstractor || sessionScope.isSuAbstractor)}">
    <jsp:doBody />
</c:if>
