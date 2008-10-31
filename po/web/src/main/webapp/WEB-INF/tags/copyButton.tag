<%@ tag display-name="button"  description="Displays the button"  body-content="scriptless" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ attribute name="onclick" required="true" type="java.lang.String"%>
<%@ attribute name="bodyStyle" required="true" type="java.lang.String"%>
<%@ attribute name="buttonStyle" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="true" type="java.lang.String"%>
<div style="${bodyStyle}">
<jsp:doBody />
</div>
<div style="${buttonStyle}"><po:button id="${id}" href="javascript://nop/" onclick="${onclick}" style="copy" text="Copy"/></div>
    