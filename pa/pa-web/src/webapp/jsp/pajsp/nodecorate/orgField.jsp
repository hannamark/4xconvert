<%@ taglib prefix="s" uri="/struts-tags"%>

&nbsp;
<label for="summary4Name" style="font-family:arial, height:40px, helvetica,sans-serif; color:#333; font-weight:bold; font-style:normal; text-align:right; padding:3px 0 0 0">Summary4Organization Name:</label>
<s:textfield name="nciSpecificInformationWebDTO.organizationName" size="30" maxlength="40" />
<input type="button" value="Look Up" onclick="lookup();"/>