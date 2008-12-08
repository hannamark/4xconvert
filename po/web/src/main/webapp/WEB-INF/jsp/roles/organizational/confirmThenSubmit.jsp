<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@include file="../../curate/confirmThenSubmit.jsp" %>
<script type="text/javascript">
   function confirmThenSubmit(formId) {
       confirmThenSubmit('curateRoleForm.role.status', formId);
   } 
</script>