<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Organization Name" name="trialDTO.leadOrganizationName"  id="trialDTO.leadOrganizationName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> <input type="button" value="Look Up" 
            onclick="lookup4loadleadorg();" title="Opens a popup form to select Lead Organization" />
            
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>