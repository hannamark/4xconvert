<%@ tag display-name="field" description="Renders the CSS_XHTML theme field" body-content="scriptless" dynamic-attributes="true"%>
<%@ attribute name="labelKey" type="java.lang.String" required="true" %>
<%@ attribute name="fieldRequired" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<div class="wwgrp" id="wwgrp_${labelKey}">
    <div class="wwlbl" id="wwlbl_${labelKey}">
        <label class="label" for="${labelKey}">
        <c:if test="${fieldRequired == true}"><span class='required'>*</span></c:if>
        <s:text name="%{#attr.labelKey}"/>:
        </label>
    </div> 
    <br/>
    <div class="wwctrl" id="wwctrl_${labelKey}">
        <jsp:doBody />
    </div>
</div>