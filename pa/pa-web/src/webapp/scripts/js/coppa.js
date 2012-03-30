function setFocusToFirstControl() {
    for (var f=0; f < document.forms.length; f++) {
        for(var i=0; i < document.forms[f].length; i++) {
            var elt = document.forms[f][i];
            if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
                try {
                    elt.focus();
                    return;
                } catch(er) {
                }
            }
        }
    }
}

function handleMultiDelete(confirmationMessage, submitAction) {
    // PO-3390: Only display confirmation if at least one box is checked.
    // If nothing is checked, submit to the action class without warnings.
    // Action class will handle this sitation and will come back with a message.
    var atLeastOne = false;
    var input_box = false;
    
    $(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete' && el.checked) {
            atLeastOne = true;
        }
    });
    
    if (atLeastOne) {
        input_box = confirm(confirmationMessage);
    }
    
    if (input_box || !atLeastOne) {
        if (document.forms[0].page!=undefined) {     
            document.forms[0].page.value = "Delete";
        }
        document.forms[0].action = submitAction;
        document.forms[0].submit();
    }
    
}

