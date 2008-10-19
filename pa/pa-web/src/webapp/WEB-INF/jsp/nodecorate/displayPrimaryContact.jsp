<%@ taglib prefix="s" uri="/struts-tags"%>	
<b>First Name:</b><s:textfield name="personContactWebDTO.firstName" size="30"  readonly="true"/> <input type="button" value="Look Up" onClick="lookupcontactperson();"/>
<br><br>
<b>Middle Name:</b><s:textfield size="30"  readonly="true"/>
<br><br>
<b>Last Name: </b><s:textfield name="personContactWebDTO.lastName" size="30"  readonly="true"/>
