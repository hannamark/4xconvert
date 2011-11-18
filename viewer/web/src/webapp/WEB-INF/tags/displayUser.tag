<%@ tag display-name="displayUser"  description="Displays the username"  body-content="empty" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:property value="@gov.nih.nci.pa.util.CsmUserUtil@getGridIdentityUsername(@com.fiveamsolutions.nci.commons.util.UsernameHolder@getUser())" />