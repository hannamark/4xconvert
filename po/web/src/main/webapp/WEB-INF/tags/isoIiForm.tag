<%@ tag display-name="assignedIi" description="Renders the Ii form" body-content="empty" %>
<%@ attribute name="formNameBase" type="java.lang.String" required="true" %>
<%@ attribute name="iiKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="iiLabelKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="ii" type="gov.nih.nci.iso21090.Ii" required="true" %>
<%@ attribute name="required" type="java.lang.Boolean" required="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<s:if test="%{#attr.required == null || #attr.required == true}">
<s:set name="cssClass" value="required"/>
<s:set name="required" value="true"/>
</s:if>
<s:else>
<s:set name="cssClass" value=""/>
</s:else>
<s:set name="displayableValues" value="#{'true':'TRUE', 'false':'FALSE'}" />
<s:select 
    name="%{#attr.iiKeyBase + '.displayable'}" 
    label="%{getText(#attr.iiLabelKeyBase + '.displayable')}"
    list="#displayableValues" 
    headerKey="" headerValue="--Is Displayable?--"
    value="#attr.ii.displayable" 
    id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.displayable'}">
</s:select>
<s:textfield name="%{#attr.iiKeyBase + '.extension'}" required="%{#attr.required}" cssClass="%{cssClass}" 
    size="55" maxlength="255"
    label="%{getText(#attr.iiLabelKeyBase + '.extension')}" 
    id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.extension'}"/>
<s:textfield name="%{#attr.iiKeyBase + '.identifierName'}" 
	size="55" maxlength="255"
	label="%{getText(#attr.iiLabelKeyBase + '.identifierName')}"
	id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.identifierName'}"/>
<s:set name="reliabilityValues" value="@gov.nih.nci.iso21090.IdentifierReliability@values()" />
<s:select 
    name="%{#attr.iiKeyBase + '.reliability'}" 
    label="%{getText(#attr.iiLabelKeyBase + '.reliability')}"
    list="availableReliability" 
    listKey="name()" listValue="name()" 
    headerKey="" headerValue="--Select a Reliability--"
    value="#attr.ii.reliability"
    id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.reliability'}"
    onchange="return confirmReliability(this);">
</s:select>
<script type="text/javascript" language="javascript">
    var ${fn:replace(formNameBase, ".", "_")}_${fn:replace(iiKeyBase, ".", "_")}_reliability = '${ii.reliability}';
    function confirmReliability(elem) {
        var r = confirm('<s:text name="ii.reliability.confirmation"/>');
        if (r == true) {
            ${fn:replace(formNameBase, ".", "_")}_${fn:replace(iiKeyBase, ".", "_")}_reliability = $F(elem)
            return true;
        } else {
            $(elem).value = ${fn:replace(formNameBase, ".", "_")}_${fn:replace(iiKeyBase, ".", "_")}_reliability;
            return false;
        }
    }
</script>
<s:textfield name="%{#attr.iiKeyBase + '.root'}" required="%{#attr.required}" cssClass="%{cssClass}"
	size="55" maxlength="255"
	label="%{getText(#attr.iiLabelKeyBase + '.root')}" 
	id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.root'}"/>
<s:set name="scopeValues" value="@gov.nih.nci.iso21090.IdentifierScope@values()" />
<s:select 
    name="%{#attr.iiKeyBase + '.scope'}" 
    label="%{getText(#attr.iiLabelKeyBase + '.scope')}"
    list="#scopeValues" 
    listKey="name()" listValue="name()" 
    headerKey="" headerValue="--Select a Scope--"
    value="#attr.ii.scope"
    id="%{#attr.formNameBase + '.' +  #attr.iiLabelKeyBase + '.scope'}">
</s:select>
