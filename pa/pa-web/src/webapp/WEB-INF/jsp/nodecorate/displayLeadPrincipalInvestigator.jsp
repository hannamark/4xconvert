<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:textfield label="First Name" name="(selectedLeadPrincipalInvestigator.getName().getPart()).get(1).getValue()" size="30"  readonly="true" cssStyle="width:200px"/>
<input type="button" value="Look Up" onclick="lookup4loadleadpers();"/>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadPINotSelected</s:param>
    </s:fielderror>                            
  </span>