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


var deleteAllToggled = false;
function toggleDeleteCheckboxes() {
	deleteAllToggled = !deleteAllToggled;
	$(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete') {
        	el.checked = deleteAllToggled;
        }
    });
	if ($('multiDeleteBtnText')!=null) {
		$('multiDeleteBtnText').innerHTML = deleteAllToggled?'Deselect All':'Select All';
	}
}



/*
If you want to use this script, please keep the original author in this header!

Purpose:	Script for applying maxlengths to textareas and monitoring their character lengths.
Author: 	James O'Cull (adjustments for CTRP by Denis Krylov)
Date: 		08/14/08

To use, simply apply a maxlenth value to a textarea.
If you need it to prevent typing past a certain point, add lengthcut="true"

Example:
<textarea maxlength="1000" lengthcut="true"></textarea>

If you add a new text area with javascript, simply call parseCharCounts() again find the new textarea(s) and label them!
*/

var LabelCounter = 0;

function parseCharCounts()
{
	//Get Everything...
	var elements = $$('textarea.charcounter');;
	var element = null;	
	var newlabel = null;
	
	for(var i=0; i < elements.length; i++)
	{
		element = elements[i];
		
		if(element.getAttribute('maxlength') != null && element.getAttribute('limiterid') == null)
		{
			//Create new label
			newlabel = $(document.createElement('span'));
			newlabel.id = 'limitlbl_' + LabelCounter;
			newlabel.addClassName('info');
			newlabel.addClassName('charcounter');
			newlabel.innerHTML = "";
			
			//Attach limiter to our textarea
			element.setAttribute('limiterid', newlabel.id);
			element.observe('keyup', function(event) { displayCharCounts(this, event); });
			element.observe('change', function(event) { displayCharCounts(this, event); });
			
			if (element.nextSibling!=null) {
				element.parentNode.insertBefore(newlabel, element.nextSibling);
			} else {
				element.parentNode.appendChild(newlabel);
			}
			
			// Force the update now!
			displayCharCounts(element);
		}
		
		//Push up the number
		LabelCounter++;
	}
}

function displayCharCounts(element, event)
{
	var limitLabel = $(element.getAttribute('limiterid'));
	var maxlength = element.getAttribute('maxlength');	
	
	//Replace \r\n with \n then replace \n with \r\n
	//Can't replace \n with \r\n directly because \r\n will be come \r\r\n

	//We do this because different browsers and servers handle new lines differently.
	//Internet Explorer and Opera say a new line is \r\n
	//Firefox and Safari say a new line is just a \n
	//ASP.NET seems to convert any plain \n characters to \r\n, which leads to counting issues
	var value = element.value.replace(/\u000d\u000a/g,'\u000a').replace(/\u000a/g,'\u000d\u000a');
	var currentLength = value.length;
	var remaining = 0;
	
	if(maxlength == null || limitLabel == null)
	{
		return true;
	}
	remaining = maxlength - currentLength;
	
	if(remaining >= 0)
	{		
		limitLabel.innerHTML = remaining + ' character';
		if(remaining != 1)
			limitLabel.innerHTML += 's';
		limitLabel.innerHTML += ' left';
	}
	else
	{
		limitLabel.innerHTML = '0 characters left';
		if (element.disabled != true && element.readOnly != true && element.readonly != true) {
			value = value.substring(0, maxlength);
			element.value = value;
			element.setSelectionRange(maxlength, maxlength);			
		}
	}
}

//Go find our textareas with maxlengths and handle them when we load!
Event.observe(window, "load", parseCharCounts);