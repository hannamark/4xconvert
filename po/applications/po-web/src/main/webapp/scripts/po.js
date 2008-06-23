
function submitDivAsForm(url, divId) {
    var div = document.getElementById(divId);
    var fields = div.getElementsByTagName("input");
    var params="";
    for (var i=0; i<fields.length; i++) {
        params += fields[i].name + "=" + escape(fields[i].value) + "&";
    }
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'post',
        evalScripts: true,
        parameters: params
    });
    return false;
}

function loadDiv(url, divId, showLoadingIcon) {
    var div = document.getElementById(divId);
    if (showLoadingIcon) {
        div.innerHTML = '<div><img alt="Indicator" align="absmiddle" src="' + contextPath + '/images/loading.gif"/>&nbsp;Loading...</div>';
    }
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: true
    });
    return false;
}

function submitDivOnReturn(e, addId) {
    var characterCode;
    if(e && e.which){
        e = e;
        characterCode = e.which;
    } else {
        e = event;
        characterCode = e.keyCode;
    }
    if(characterCode == 13){
        var add = document.getElementById(addId);
        add.onclick();
        return false
    }
    else{
        return true
    }
}

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

