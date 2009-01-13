<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield name="nciSpecificInformationWebDTO.organizationName" size="30"  readonly="true"/>
<input type="button" value="Look Up" onclick="lookup();"/>
<s:hidden name="nciSpecificInformationWebDTO.organizationIi" />
<span class="formErrorMsg"> 
   <s:fielderror>
   	  <s:param>nciSpecificInformationWebDTO.organizationName</s:param>
    </s:fielderror>                            
</span>
