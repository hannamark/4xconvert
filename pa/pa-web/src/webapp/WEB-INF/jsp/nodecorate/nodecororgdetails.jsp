<%@ taglib prefix="s" uri="/struts-tags"%>	
<b>Organization Name:</b><s:textfield name="selectedOrgDTO.name.part[0].value" size="30"  readonly="true"/> <input type="button" value="Look Up" onclick="lookup();"/>
<br><br>
<b>City:</b><s:textfield name="selectedOrgDTO.postalAddress.part[1].value" size="30"  readonly="true"/>
<br><br>
<b>Country:</b><s:textfield name="selectedOrgDTO.postalAddress.part[3].code" size="30"  readonly="true"/>
<br><br>
<b>Zip:</b><s:textfield name="selectedOrgDTO.postalAddress.part[2].value" size="30"  readonly="true"/>
<br><br>
				