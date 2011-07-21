<%@ tag display-name="displayWhenCheckedOut" 
    description="Renders the enclosed mark-up only if the study is checked out by the current user and that user is an admin abstractor." body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<c:if test="${sessionScope.trialSummary.adminCheckout.checkoutBy != null &&
              sessionScope.loggedUserName == sessionScope.trialSummary.adminCheckout.checkoutBy && (sessionScope.isAdminAbstractor || sessionScope.isSuAbstractor)}">
    <jsp:doBody />
</c:if>
