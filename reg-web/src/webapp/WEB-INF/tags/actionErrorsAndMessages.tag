<%@ tag display-name="actionErrorsAndMessages"
	description="Struts action errors and messages"
	body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="hasActionMessages()">
	<div class="confirm_msg">
		<s:actionmessage />
	</div>
</s:if>
<s:if test="hasActionErrors()">
	<div class="error_msg">
		<s:actionerror />
	</div>
</s:if>
<s:else>
	<s:actionerror />
</s:else>
