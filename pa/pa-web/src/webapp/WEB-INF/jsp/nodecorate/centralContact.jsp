<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<s:textfield name="gtdDTO.centralContactName" size="30"  readonly="true"/>

<input type="button" value="Look Up" onclick="lookupCentralContact();"/>
<span class="formErrorMsg"> 
<s:fielderror>
<s:param>gtdDTO.centralContactName</s:param>
</s:fielderror>                            
</span>

<s:hidden name="gtdDTO.centralContactIdentifier" />

