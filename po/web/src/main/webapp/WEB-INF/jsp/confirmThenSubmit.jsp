<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript">
   function finalConfirmThenSubmit(fieldId, formId, statusMsgMap) {
       if ($(fieldId).value == 'NULLIFIED'
           && (($('curateEntityForm.duplicateOf.id')==null || $('curateEntityForm.duplicateOf.id').value=='')
               && ($('curateRoleForm.duplicateOf')==null || $('curateRoleForm.duplicateOf').value==''))) {
           alert("Please select a duplicate since you are Nullifying the existing one.");
           return false;
       }
       return finalConfirmThenSubmitNoDuplicateOfCheck(fieldId, formId, statusMsgMap);
   } 

   function finalConfirmThenSubmitNoDuplicateOfCheck(fieldId, formId, statusMsgMap) {
       if ($(fieldId).value != 'NULLIFIED' &&  $(fieldId).value != 'INACTIVE') {
           $(formId).submit();
           return true;
       } else {
           var msgText = $(statusMsgMap)[$(fieldId).value];
           var r = confirm(msgText);
           if (r == true) {
               $(formId).submit();
               return true;
           } else {
               return false;
           }
       }
   }
</script>