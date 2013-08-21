<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript">
   function finalConfirmThenSubmit(fieldId, formId, statusMsgMap) {
       if ($(fieldId).value != 'NULLIFIED' &&  $(fieldId).value != 'INACTIVE') {
           $(formId).submit();
           return true;
       } else {
           var msgText = $(statusMsgMap)[$(fieldId).value];
           if ($(fieldId).value == 'NULLIFIED'
        		   && ($('curateEntityForm.duplicateOf.id')==null || $('curateEntityForm.duplicateOf.id').value=='')){
        	   alert("Please select a duplicate organization since you are Nullifying the existing organization.");
        	   return false;
           }
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