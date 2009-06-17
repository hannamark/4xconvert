<script type="text/javascript">
function handleDuplicateOf() {
    $('duplicateOfDiv')[$('curateRoleForm.role.status').value == 'NULLIFIED' ? 'show' : 'hide']();

    if ($('curateRoleForm.role.status').value != 'NULLIFIED') {
        if ($('curateRoleForm.duplicateOf')) {
            $('curateRoleForm.duplicateOf').value = '';
        }
    }
    return true;
}
</script>
