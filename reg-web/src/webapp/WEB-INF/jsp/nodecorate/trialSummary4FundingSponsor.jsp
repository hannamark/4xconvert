<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield label="Summary4sponsorName" name="trialDTO.summaryFourOrgName" id="trialDTO.summaryFourOrgName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> <input type="button" value="Look Up" 
onclick="lookup4loadSummary4Sponsor();" title="Opens a popup form to select Summary4 Sponsor"/>
<span class="formErrorMsg"> 
   <s:fielderror>
   <s:param>summary4FundingSponsor</s:param>
  </s:fielderror>                            
</span>