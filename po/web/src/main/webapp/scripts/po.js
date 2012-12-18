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
    var div = $(divId);
    var fields = div.getElementsByTagName("input");
    var params="";
    for (var i=0; i<fields.length; i++) {
    	if (fields[i].name.length > 0 && fields[i].value.length > 0) {
    		params += fields[i].name + "=" + escape(fields[i].value) + "&";
    	}
    }
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'post',
        evalScripts: true,
        parameters: params
    });
    return false;
}

function loadDiv(url, divId, showLoadingIcon, customOnComplete) {
    var div = $(divId);
    div.show();
    if (showLoadingIcon) {
        div.innerHTML = '<div><img alt="Indicator" align="absmiddle" src="' + contextPath + '/images/loading.gif"/>&nbsp;Loading...</div>';
    }
    var aj = new Ajax.Updater(div, url, {
        asynchronous: true,
        method: 'get',
        evalScripts: true,
        onComplete: function(transport) {
            if(customOnComplete != null) {
                customOnComplete()
            }
        }
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
    if ($(textFieldId) && $(textFieldId).type == 'text') {
        $(textFieldId).value = value;
    }
}

function checkForValidFundingMech(crFundingMechValue, selectFormFundingMech, selectFormROType, crFundingMech) {
    var found = false;
    if ($(selectFormFundingMech)) {
        for ( var i = 0; i <= $(selectFormFundingMech).length - 1; i = i + 1) {
            var fundingMechId = $(selectFormFundingMech).options[i].value;
            if (fundingMechId == crFundingMechValue) {
                found = true;
                break;
            }
        }
    }

    if (!found) {
        var selectedIndex = $(selectFormROType).selectedIndex;
        var selectedFormROTypeCodeValue = $(selectFormROType).options[$(selectFormROType).selectedIndex].value;
        var selectedFormROType = $(selectFormROType).options[$(selectFormROType).selectedIndex].text;
        alert("Funding Mechanism '" + crFundingMech + "' is not valid for Research Organization Type '" + selectedFormROType + "'.");
        return false;
    }

    return true;
}

function selectValueInSelectField(value, selectBoxId, firefoxEvent, ieEvent) {
    var found = false;
    if ($(selectBoxId) && $(selectBoxId).type == 'select-one') {
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

var IdValue = Class.create();
IdValue.prototype = {
  initialize: function(id, value) {
    this.id  = id;
    this.value = value;
  }
};

function showPopup(url, callbackFunc) {
    showPopWin(url, calculateMaxPopupWidth(), calculateMaxPopupHeight(), callbackFunc);
}

function assembleAndSubmitPhoneNumber(type, url, divId) {

    $(type + 'Entry_value').value = $(type + 'Entry_part1').value + '-' + $(type + 'Entry_part2').value + '-' + $(type + 'Entry_part3').value;

    if ($(type + 'Entry_value').value.length < 12) {
        alert('The entire ' + type + ' number must be provided.');
        return false;
    }

    var myRe = new RegExp ("\\d{3}-\\d{3}-\\d{4}");
    var OK = myRe.exec($(type + 'Entry_value').value);
    if (!OK) {
        alert($(type + 'Entry_value').value + ' must match ###-###-####.');
        return false;
    }

    if ($(type + 'Entry_part4').value.length > 0) {
        myRe = new RegExp ("^\\d{0,}$");
        OK = myRe.exec($(type + 'Entry_part4').value);
        if (!OK) {
            alert('Ext ' + $(type + 'Entry_part4').value + ' must match digits only.');
            return false;
        }
        $(type + 'Entry_value').value = $(type + 'Entry_value').value + 'x' + $(type + 'Entry_part4').value;
    }

    submitDivAsForm(url, divId);

    return false;
}

/*
Auto tabbing script- By JavaScriptKit.com
http://www.javascriptkit.com
*/
function autotab(original,destination) {
    if (original.getAttribute && original.value.length == original.getAttribute("maxlength")) {
        destination.focus();
    }
}

function switchContactNumberFormats(country) {

    var countryField = $(country);
    if (!countryField) {
        return;
    }
    var countryName = countryField.options[countryField.selectedIndex].text;

    if ($('no_format_phone')) {
        if (countryName == 'United States' || countryName == 'Canada') {
            $('no_format_phone').hide();
            $('no_format_fax').hide();
            $('no_format_tty').hide();
            $('us_format_phone').show();
            $('us_format_fax').show();
            $('us_format_tty').show();
        } else {
            $('no_format_phone').show();
            $('no_format_fax').show();
            $('no_format_tty').show();
            $('us_format_phone').hide();
            $('us_format_fax').hide();
            $('us_format_tty').hide();
        }
    }
}

function displayWaitPanel() {	
	if ($('progress_indicator_panel')==null) {
		return;
	}
	
	// retrieve required dimensions	
	var eltDims     = $('progress_indicator_panel').getDimensions();
	var browserDims = $(document).viewport.getDimensions();
	var scrollOffset = $(document).viewport.getScrollOffsets();
	 
	// calculate the center of the page using the browser and element dimensions
	var y  = (browserDims.height - eltDims.height) / 2 + scrollOffset.top;
	var x = (browserDims.width - eltDims.width) / 2;	
	
	$('progress_indicator_panel').absolutize();	
	$('progress_indicator_panel').style.left = x + 'px';
	$('progress_indicator_panel').style.top = y + 'px';
	$('progress_indicator_panel').show();
}

function hideWaitPanel() {
	if ($('progress_indicator_panel')==null) {
		return;
	}
	$('progress_indicator_panel').hide();
}