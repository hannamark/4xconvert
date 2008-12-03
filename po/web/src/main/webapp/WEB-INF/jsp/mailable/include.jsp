<div class="box_white" id="postalAddresses.div">
    Loading...
</div>
<c:url value="/protected/mailable/addresses.action" var="viewAddressesAction">
     <c:param name="rootKey" value="${rootKey}"/>
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
