<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@include file="../confirmThenSubmit.jsp" %>
<script type="text/javascript">
function confirmThenSubmit(fieldId, formId){
    var map = new Array();
    map['NULLIFIED'] = '<s:text name="entity.curation.nullified.confirmation"/>';
    map['INACTIVE'] = '<s:text name="entity.curation.inactive.confirmation"/>';
    finalConfirmThenSubmit($(fieldId),$(formId),map);
 }
</script>