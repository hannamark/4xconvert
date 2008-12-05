<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield name="gtdDTO.leadOrganizationName" size="30"  readonly="true"/>
<input type="button" value="Look Up" onclick="lookup();"/>
<s:hidden name="gtdDTO.leadOrganizationIdentifier" />

