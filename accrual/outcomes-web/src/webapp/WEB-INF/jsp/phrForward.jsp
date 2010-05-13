<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript">
  window.onload = function() {
      document.forms[0].submit();
  };
</script>
<s:form name="phrForm" action="https://www.phrconnector.com/PHRConnector/viewPatientData.action">
    <s:hidden name="uuid" />
</s:form>