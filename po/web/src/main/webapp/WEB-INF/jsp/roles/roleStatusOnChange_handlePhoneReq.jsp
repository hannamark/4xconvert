<script type="text/javascript">
function handlePhoneReq() {

if ($('curateRoleForm.role.status').value != 'ACTIVE') {
	$('onload_phone_or_email_required').hide();
} else {
	$('onload_phone_or_email_required').show();
}
	return true;
}
</script>
