<%@ tag display-name="successMessage"  description="Displays the succes messages"  body-content="empty" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Success Messages --%>

<c:if test="${requestScope.successMessage  != null}">

<div class="confirm_msg">
	<strong>Message:</strong> <c:out value="${requestScope.successMessage }"/>.
</div>
<c:remove var="successMessage" scope="request"/>
</c:if>
