<script type="text/javascript">
function handleDuplicateOf() {
    $('duplicateOfDiv')[$('curateRoleForm.role.status').value == 'NULLIFIED' ? 'show' : 'hide'](); 
            
    if ($('curateRoleForm.role.status').value != 'NULLIFIED') {
        if ($('curateRoleForm.role.duplicateOf')) {
            $('curateRoleForm.role.duplicateOf').value = '';
        }
        //Why was this ever here?
        //$('wwctrl_curateRoleForm_role_scoper_id').innerHTML = '';
    }
    return true;
}
</script>