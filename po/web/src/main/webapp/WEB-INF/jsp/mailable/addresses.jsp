<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:if test="${fn:length(mailable.postalAddresses) == 0}">
No Postal Address(es) found.
</c:if>
<c:forEach items="${mailable.postalAddresses}" var="addy" varStatus="e">
<fieldset id="postalAddress${e.index}">
    <legend>Address ${e.index + 1}</legend>
    <div>
    <po:address address="${addy}" idSuffix="${e.index + 1}"/>
    </div>
    <c:url var="removeAction" value="../../mailable/remove.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="${e.index}"/>
    </c:url>
    <c:url var="addAddressUrl" value="../../popup/address/input.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="${e.index}"/>
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
</fieldset>
</c:forEach>
<div>
    <c:url value="../../popup/address/input.action" var="addAddressUrl">
       <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="index" value="-1"/>
    </c:url>
    <po:buttonRow>
    <po:button id="add_address" href="javascript://noop/" onclick="addEditAddressPopup('${addAddressUrl}');" style="add_contact" text="Add Postal Address"/>
    <%-- po:button id="refresh_address" href="javascript://noop/" onclick="showPopupAddAddressCallback();" style="refresh" text="Refresh"/ --%>
    </po:buttonRow>
</div>
<div class="clear"></div>