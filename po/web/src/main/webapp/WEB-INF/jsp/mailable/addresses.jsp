<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:if test="${fn:length(mailable.postalAddresses) == 0}">
No Postal Address(es) found.
</c:if>
<c:set var="showUsFormattedTel" value="${usOrCanadaFormat && !usOrCanadaFormatForValidationOnly}"/>
<c:forEach items="${mailable.postalAddresses}" var="addy" varStatus="e">
<fieldset id="postalAddress${e.index}">
    <legend>Address ${e.index + 1}</legend>
    <div>
    <po:address address="${addy}" idSuffix="${e.index + 1}"/>
    </div>
<c:if test="${not readonly}">
    <c:url var="removeAction" value="../../mailable/remove.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="${e.index}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
    </c:url>
    <c:url var="addAddressUrl" value="../../popup/address/input.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="${e.index}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
    </c:url>
    <div>
        <po:buttonRow>
        <po:button href="javascript://noop/" 
            onclick="addEditAddressPopup('${addAddressUrl}');" 
            style="edit" text="Edit" 
            id="address-edit-${e.index}"/>
        <po:button href="javascript://noop/" 
            onclick="return loadDiv('${removeAction}', 'postalAddresses.div');" 
            style="delete" text="Remove" 
            id="address-remove-${e.index}"/>
        </po:buttonRow>
    </div>        
</c:if>
</fieldset>
</c:forEach>
<c:if test="${not readonly}">
<div>
    <c:url value="../../popup/address/input.action" var="addAddressUrl">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="-1"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
    </c:url>
    <po:buttonRow>
    <po:button id="add_address" href="javascript://noop/" onclick="addEditAddressPopup('${addAddressUrl}');" style="add_contact" text="Add Postal Address"/>    
    </po:buttonRow>
</div>
</c:if>
<div class="clear"></div>

<script type="text/javascript">
<!--
if (${showUsFormattedTel}) {
        $('no_format_phone').hide();
        $('no_format_fax').hide();
        $('no_format_tty').hide();
        $('us_format_phone').show();
        $('us_format_fax').show();
        $('us_format_tty').show();
} else {
        $('no_format_phone').show();
        $('no_format_fax').show();
        $('no_format_tty').show();
        $('us_format_phone').hide();
        $('us_format_fax').hide();
        $('us_format_tty').hide();
}

-->
</script>