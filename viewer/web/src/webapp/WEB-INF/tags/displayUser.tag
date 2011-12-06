<%@ tag display-name="displayUser"  description="Displays the username"  body-content="empty" %>
<%=(request.getUserPrincipal() != null) ? gov.nih.nci.pa.util.CsmUserUtil.getGridIdentityUsername(request.getUserPrincipal().getName()) : ""%>
