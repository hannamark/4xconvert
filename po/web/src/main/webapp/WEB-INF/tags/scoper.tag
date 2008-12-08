<%@ tag display-name="scoper" description="Renders the Scoper form field w/ popup selector" body-content="scriptless" %>
<%@ attribute name="key" type="java.lang.String" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="wwgrp" id="wwgrp_curateRoleForm_role_scoper_id">
    <s:fielderror>
        <s:param value="%{'role.scoper'}"/>
    </s:fielderror>
    <div style="float:right;">
        <c:url value="/protected/selector/organization/start.action" var="findScoperUrl">
            <c:param name="source" value="${organization.id}"/>
        </c:url>
        <po:buttonRow>
        <po:button id="select_scoper" href="javascript://noop/" onclick="showPopWin('${findScoperUrl}', 800, 800, showPopWinCallback);" style="search" text="Select Scoper"/>
        </po:buttonRow>
    </div>
    <div class="wwlbl" id="wwlbl_curateRoleForm_role_scoper_id">
        <label class="label" for="curateRoleForm_role_scoper_id">   
        <span class="required">*</span>     
        <s:text name="%{#attr.key}"/>:
        </label>
    </div>
    <br/>
    <div class="wwctrl" id="wwctrl_curateRoleForm_role_scoper_id">
       ${role.scoper.id} 
    </div>
</div>
<s:hidden key="role.scoper" id="curateRoleForm.role.scoper.id" required="true" cssClass="required"/>
<script type="text/javascript">
<!--
    function showPopWinCallback(returnVal) {
	    $('curateRoleForm.role.scoper.id').value = returnVal.id;
	    $('wwctrl_curateRoleForm_role_scoper_id').innerHTML = '' + returnVal.value + ' (' + returnVal.id + ')';
    }
//-->
</script>