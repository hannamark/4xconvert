<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL"
	value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
<c:set var="wsURL" value="${baseURL}/services" />

<!-- Redirect to the page to show the list of the web-services -->
<c:redirect url="${wsURL}" />
