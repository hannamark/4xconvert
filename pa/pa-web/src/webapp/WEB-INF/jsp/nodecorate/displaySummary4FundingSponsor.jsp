<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Summary4sponsorName" name="organizationWebDTO.name" size="30" readonly="true" cssStyle="width:200px" /> <input type="button" value="Look Up" onclick="lookup4loadSummary4Sponsor();"/>
<span class="formErrorMsg"> 
   <s:fielderror>
   <s:param>organizationWebDTO.name</s:param>
  </s:fielderror>                            
</span>