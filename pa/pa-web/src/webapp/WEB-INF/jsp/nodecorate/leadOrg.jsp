<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Organization Name" name="gtdDTO.leadOrganizationName" size="30" cssStyle="width:200px" readonly="true"/> <input type="button" value="Look Up" onclick="lookup4loadleadorg();"/>
<s:hidden name="gtdDTO.leadOrganizationIdentifier" />
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>