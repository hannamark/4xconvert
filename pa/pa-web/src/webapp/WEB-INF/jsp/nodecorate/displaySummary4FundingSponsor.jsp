<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Summary4sponsorName" name="gtdDTO.summaryFourOrgName" size="30" cssStyle="width:200px" readonly="true"/> <input type="button" value="Look Up" onclick="lookup4loadSummary4Sponsor();"/>
<s:hidden name="gtdDTO.summaryFourOrgIdentifier"/>
<span class="formErrorMsg"> 
   <s:fielderror>
   <s:param>summary4FundingSponsor</s:param>
  </s:fielderror>                            
</span>