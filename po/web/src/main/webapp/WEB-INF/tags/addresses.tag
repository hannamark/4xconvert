<%@ tag display-name="addresses" description="Renders the ajax address(es) with add/edit/delete capability" body-content="empty" %>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="Display read-only view, if not provied readonly is false"%>
<%@ attribute name="usOrCanadaFormatForValidationOnly" type="java.lang.Boolean" required="false" description="Whether US/Canada format should be used for validation only."%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:if test="%{#attr.readonly == true}">
<s:set name="readonlyBool" value="true"/>
</s:if>
<s:else>
<s:set name="readonlyBool" value="false" />
</s:else>
<div class="box_white" id="postalAddresses.div">
    Loading...
</div>
<c:url value="/protected/mailable/addresses.action" var="viewAddressesAction">
     <c:param name="rootKey" value="${rootKey}"/>
     <c:param name="readonly" value="${readonlyBool}"/>
     <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
</c:url>
<script type="text/javascript">
<!--
loadDiv('${viewAddressesAction}', 'postalAddresses.div');
function showPopupAddAddressCallback(returnVal) {
    loadDiv('${viewAddressesAction}', 'postalAddresses.div');
}
function addEditAddressPopup(url) {
    showPopWin(url, 550, 350, showPopupAddAddressCallback);
}

//-->
</script>