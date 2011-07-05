<script type="text/javascript" language="javascript">
    function reloadFamilyOrgRelationship() {
        window.top.hidePopWin(true);
        location.reload();
    }

    function removeOrgRelationship(id) {
        <c:url value="/protected/popup/organization/relationship/create/remove.action" var="removeUrl"/>
        <s:text name="organizationRelationship.confirmationMessage" var="confirmationMessage"/>
        var confirmation = confirm('${confirmationMessage}');
        if (confirmation) {
            var params = {
                'orgRelationship.id' : id
            };
            var options = {
                onComplete : function(transport) {
                    reloadFamilyOrgRelationship();
                }
            };
            var aj = callAjaxPost(null, '${removeUrl}', params, options);
        }
    }
</script>