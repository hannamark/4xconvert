<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<jsp:forward page="/login/login.action">
<jsp:param name="fromAjax" value="${header['X-Requested-With'] == 'XMLHttpRequest'}"/>
</jsp:forward>
