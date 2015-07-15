var addUrl;
var editUrl;
var deleteUrl;
var submitCtroUrl;
var summitCcctUrl;


function setDocumentPageUrl(addAction , editAction , deleteAction, submitCtroAction, submitCcctAction) {
	addUrl = addAction;
	editUrl = editAction;
	deleteUrl = deleteAction;
	submitCtroUrl = submitCtroAction;
	summitCcctUrl = submitCcctAction;
}

function handleMultiDeleteResultReporting(confirmationMessage ) {
	   
    var atLeastOne = false;
    var input_box = false;
    
  var isResultDeleted = false;
  var isComparisonPresent = false;
    
    $(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete' && el.checked) {
            atLeastOne = true;
            var elementIdString =el.id;
            var tokens = elementIdString.split("_");
            if(tokens.length==3) {
            	var type =tokens[2];
            	//check if one of the result documents is deleted
            	if(type=="Before results" || type== "After results") {
            		isResultDeleted = true;
            	}
            	
            	//check if comparison document is also delete
            	if(type=="Comparison") {
            		isComparisonPresent = true;
                }
            	
            }
           
        }
    });
    

    if (atLeastOne) {
        input_box = confirmationMessage!=''?confirm(confirmationMessage):true;
    }
    
    if (input_box || !atLeastOne) {
        if (document.forms[0].page!=undefined) {     
            document.forms[0].page.value = "Delete";
        }
        
        //display alert indicating comparison will be deleted if 
        //user is not already deleting comparison document
        if(isResultDeleted ) {
         
        	if(!isComparisonPresent) {
        		var isProceed =  confirm("If \"Before results\" or \"After results\" documents is removed the \"Comparison document\" will also be removed. Do you wish to proceed?");
        		if (!isProceed) {
        			return;
        		}
        		//select comparison document to delete 
                selectComparisonForDelete();
        	}
        }
        if(jQuery("#deleteType")) {
        	deleteUrl = deleteUrl+"?deleteType=trialDocument";
        }
        document.forms[0].action = deleteUrl;
        document.forms[0].submit();
    } else {
        $(document.forms[0]).getInputs().each(function(el) {
            if (el.name=='objectsToDelete') {
                el.checked = false;
            }
        });
    }
    
}

function selectComparisonForDelete() {
	
	  $(document.forms[0]).getInputs().each(function(el) {
	        if (el.name=='objectsToDelete') {
	            var elementIdString =el.id;
	            var tokens = elementIdString.split("_");
	            if(tokens.length==3) {
	                var type =tokens[2];
	                
	                //select comparion document for deletion
	                if(type=="Comparison") {
	                	el.checked = true;
	                }
	                
	            }
	           
	        }
	    });
}

function showreviewCtroDialog(id) {
	
	jQuery("#docId").val(id);
	jQuery("#reviewCtroUser").dialog({
		autoOpen : false,
        height : 'auto',
        width : 450,
        title :"CTRO Review Complete"
       
     }); 
    jQuery("#reviewCtroUser").dialog("open");
}

function showreviewCCCTDialog(id) {
	jQuery("#docId").val(id);
    jQuery("#reviewCcctUser").dialog({
    	autoOpen : false,
    	height : 'auto',
        width : 450,
        title :"CCCT Review Complete"
       
     }); 
    jQuery("#reviewCcctUser").dialog("open");
}

function closeDialog(ctroDialog) {
	if(ctroDialog) {
		 jQuery("#reviewCtroUser").dialog("close");
	}
	else {
		 jQuery("#reviewCcctUser").dialog("close");
	}
	
}

function saveCtroUserReview() {
	
	   var ctroUserId = jQuery("#ctroUserId").val();
	   jQuery('#trialDocumentsForm')[0].action = submitCtroUrl+"?ctroUserId="+ctroUserId;
       jQuery('#trialDocumentsForm').submit();
	 
	    
}

function saveCcctUserReview() {
    
    var ccctUserId = jQuery("#ccctUserId").val();
    jQuery('#trialDocumentsForm')[0].action = summitCcctUrl+"?ccctUserId="+ccctUserId;
    jQuery('#trialDocumentsForm').submit();
     
}

function submitAddDocument() {
	
	var studyProtocolId = jQuery("#studyProtocolId").val();
	addUrl = addUrl+"&studyProtocolId="+studyProtocolId;
	jQuery(location).attr("href",addUrl);
	

}

function submitEditDocument(id) {
	
	var studyProtocolId = jQuery("#studyProtocolId").val();
	editUrl = editUrl+"&studyProtocolId="+studyProtocolId+"&page=Edit&id="+id;
	jQuery(location).attr("href",editUrl);

}