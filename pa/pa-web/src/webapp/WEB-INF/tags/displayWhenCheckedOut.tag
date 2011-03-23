<%@ tag display-name="displayWhenCheckedOut" description="Renders the enclosed mark-up only if the study is checked out by the current user." body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${(sessionScope.trialSummary.studyCheckoutBy != null && sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy)
                                    || (sessionScope.role == 'SuAbstractor')}">
    <jsp:doBody />
</c:if>
