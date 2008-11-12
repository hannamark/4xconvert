<%@ tag display-name="field" description="Renders the CSS_XHTML theme field as read-only text" body-content="empty" %>
<%@ attribute name="labelKey" type="java.lang.String" required="true" %>
<%@ attribute name="value" type="java.lang.Object" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="wwgrp" id="wwgrp_${labelKey}">
    <div class="wwlbl" id="wwlbl_${labelKey}">
        <label class="label" for="${labelKey}">        
        <s:text name="%{#attr.labelKey}"/>:
        </label>
    </div> 
    <br/>
    <div class="wwctrl" id="wwctrl_${labelKey}">
        ${value}
    </div>
</div>