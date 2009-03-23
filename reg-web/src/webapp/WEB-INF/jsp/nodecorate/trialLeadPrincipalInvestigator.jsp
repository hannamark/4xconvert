<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:textfield label="poLeadPi Full Name" name="trialDTO.piName" id="trialDTO.piName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
<input type="button" value="Look Up" onclick="lookup4loadleadpers();" title="Opens a popup form to select Principal Investigator"/>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadPINotSelected</s:param>
    </s:fielderror>                            
  </span>