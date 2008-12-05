<%@ taglib prefix="s" uri="/struts-tags"%>
<s:textfield name="gtdDTO.responsiblePersonName" size="30"  readonly="true"/>
<input type="button" value="Look Up" onclick="lookupPerson();"/>
<s:hidden name="gtdDTO.responsiblePersonIdentifier" />

