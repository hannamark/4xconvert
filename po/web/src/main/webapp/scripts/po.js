function submitAjaxForm(formId, divId, options, showLoadingIcon) {
    var formData = Form.serialize(formId);
    options = options || {};
    if (options.extraArgs) {
        formData = formData + '&' + Hash.toQueryString(options.extraArgs);
    }
    var div = document.getElementById(divId);
    if (showLoadingIcon) {
        div.innerHTML = '<div><img alt="Indicator" align="absmiddle" src="' + contextPath + '/images/loading.gif"/>&nbsp;Loading...</div>';
    }
    var url = options.url || $(formId).action;
    new Ajax.Updater(divId, url, {parameters: formData, evalScripts: true, insertion: options.insertion} );
    return false;
}

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
    var div = $(divId);
    div.show();
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

//Fires an event for the element passed in.
//Params:
//       1. eventElement - If contextName is not blank or null, the topic will be appended.
//       2. includeNav - If true, the left navigation pane appears. Else, the navigation pane is not displayed.
function fireEvent(eventElement, firefoxEvent, ieEvent) {
	if (document.createEvent) {
		var e = document.createEvent('Events');
		e.initEvent(firefoxEvent, true, true);
	} else if (document.createEventObject) {
		var e = document.createEventObject();
	}

	if (eventElement.dispatchEvent) {
		eventElement.dispatchEvent(e);
	} else if (eventElement.fireEvent) {
		eventElement.fireEvent(ieEvent, e);
	}
}


function copyValueToTextField(value, textFieldId) {
    $(textFieldId).value = value;
}

function selectValueInSelectField(value, selectBoxId, firefoxEvent, ieEvent) {
	var found = false;
    for ( var i = 0; i <= $(selectBoxId).length - 1; i = i + 1) {
		var selectedText = $(selectBoxId).options[i].value;
		if (selectedText == value) {
			var isFireEvent = false;
			if ($(selectBoxId).selectedIndex != i) {
				isFireEvent = true;
			}
			$(selectBoxId).selectedIndex = i;
			found = true;
			if (isFireEvent) {
				if (firefoxEvent == null) {
					firefoxEvent = 'change';
				}
				if (ieEvent == null) {
					ieEvent = 'onchange';
				}
				fireEvent($(selectBoxId), firefoxEvent, ieEvent);
			}
			break;
		}
	}     
    if (!found) {
    	alert('' + value + ' not found!');
    }
}

function selectValuesInMultiSelectField(values, multiSelectBoxId, firefoxEvent, ieEvent) {
	var found = false;
	var value;
	var isFireEvent = false;
	for (var j = 0; j <= values.length -1; j = j + 1) {
		value = values[j];
	    for ( var i = 0; i <= $(multiSelectBoxId).length - 1; i = i + 1) {
	    	var option = $(multiSelectBoxId).options[i];
			var selectBoxValue = option.value;
			if (selectBoxValue == value) {
				if (!option.selected) {
					isFireEvent = true;
				}
				option.selected = true;
				found = true;
				break;
			}
		}
	}
	if (isFireEvent) {
		if (firefoxEvent == null) {
			firefoxEvent = 'change';
		}
		if (ieEvent == null) {
			ieEvent = 'onchange';
		}
		fireEvent($(multiSelectBoxId), firefoxEvent, ieEvent);
	}
}