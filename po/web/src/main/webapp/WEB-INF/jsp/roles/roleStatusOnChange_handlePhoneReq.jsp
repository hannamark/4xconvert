<script type="text/javascript">
function handlePhoneReq() {

if ($('curateRoleForm.role.status').value != 'ACTIVE') {
	$('onload_phone_number_required').hide();
} else {
	$('onload_phone_number_required').show();
}
	return true;
}
</script>
