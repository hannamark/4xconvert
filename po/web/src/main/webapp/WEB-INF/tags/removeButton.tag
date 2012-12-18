<%@ tag display-name="removeButton" description="Displays the button"
	body-content="empty"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po"%>
<%@ attribute name="onclick" required="true" type="java.lang.String"%>
<%@ attribute name="buttonStyle" required="true" type="java.lang.String"%>
<%@ attribute name="id" required="true" type="java.lang.String"%>
<div style="${buttonStyle}">
	<po:button id="${id}" href="javascript://nop/" onclick="${onclick}"
		style="remove" text="Remove" />
</div>
