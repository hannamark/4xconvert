<%@ tag display-name="failureMessage"  description="Displays the succes messages"  body-content="empty" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- Success Messages --%>
<div class="box">
<c:if test="${request.failureMessage  != null}">
<div class="error_msg">
	<strong>Message.</strong> <c:out value="${request.failureMessage }"/>.
</div>
<c:remove var="failureMessage" scope="request"/>
</c:if>
