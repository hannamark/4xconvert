<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript">
   function confirmThenSubmit(fieldId, formId) {
       if ($(fieldId).value == 'NULLIFIED') {
           var r = confirm('<s:text name="entity.curation.nullified.confirmation"/>');
           if (r == true) {
               $(formId).submit();
               return true;
           } else {
               return false;
           }
       } else if ($(fieldId).value == 'INACTIVE') {
           var r = confirm('<s:text name="entity.curation.inactive.confirmation"/>');
           if (r == true) {
               $(formId).submit();
               return true;
           } else {
               return false;
           }
       } else {
           $(formId).submit();
           return true;
       }
   } 
</script>