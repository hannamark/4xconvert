<%@ tag display-name="displayWhenCheckedOut" description="Renders the enclosed mark-up only if the study is checked out by the current user." body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<c:if test="${(sessionScope.loggedUserName == sessionScope.trialSummary.studyCheckoutBy && (sessionScope.isAdminAbstractor || sessionScope.isScientificAbstractor))
                                    || (sessionScope.role == 'SuAbstractor')}">
    <jsp:doBody />
</c:if>
