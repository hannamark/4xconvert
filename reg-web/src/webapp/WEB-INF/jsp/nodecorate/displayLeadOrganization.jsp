<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Organization Name" name="selectedLeadOrg.name.part[0].value" size="30" cssStyle="width:200px" readonly="true"/> <input type="button" value="Look Up" onclick="lookup4loadleadorg();"/>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>