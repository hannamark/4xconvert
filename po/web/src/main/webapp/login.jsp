<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<script type="text/javascript">
  window.location='<c:url value="/login/login.action?fromAjax=${header['X-Requested-With'] == 'XMLHttpRequest' ? 'true' : 'false'}"/>';
</script>