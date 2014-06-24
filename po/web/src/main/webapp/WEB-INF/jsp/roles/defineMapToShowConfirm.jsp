<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@include file="../confirmThenSubmit.jsp" %>
<script type="text/javascript">
function confirmThenSubmit(fieldId, formId){
    var map = new Array();
    map['NULLIFIED'] = '<s:text name="role.curation.nullified.confirmation"/>';
    finalConfirmThenSubmit($(fieldId),$(formId),map);
 }
 
function submitForm(formId){
	$(formId).submit();
    return true;
 }
</script>