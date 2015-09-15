 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<c:url value="/protected/popupOrglookuporgs.action" var="lookupUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
    
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

var dscTable;
var pscTable;

jQuery(document).ready(function() {
    
    dscTable = initSectionDataTable('dscWeb');
    pscTable = initSectionDataTable('pscWeb');
});

function initSectionDataTable(tableId) {
    var table = jQuery('#'+tableId).DataTable({
        "paging":   true,
        "ordering": false,
        "info":     true,
        "bFilter" :false
    });
    return table;
}

function lookupdesigneeorg() {
	$('contactType').value = "dc";
    showPopup('${lookupUrl}', null, 'Organization');
}

function lookupdesigneeperson() {
	$('contactType').value = "dc";
    showPopup('${lookupPersonsUrl}', null, 'Persons');
}

function lookuppioperson() {
    $('contactType').value = "pc";
    showPopup('${lookupPersonsUrl}', null, 'Persons');
}

function setorgid(orgId, orgName) {
	var ct = $('contactType').value;
    if(ct=='dc') {
        $('dscOrgId').value = orgId;
        $('dscOrg').value = orgName;
    }
}

function setpersid(persIdentifier,name,email,phone) {
	var ct = $('contactType').value;
	if(ct=='pc') {
		$('pscPrsnId').value = persIdentifier;
	    $('pscPrsn').value = name;
	} else if(ct=='dc') {
		$('dscPrsnId').value = persIdentifier;
	    $('dscPrsn').value = name;
	}
}

function loadDiv(orgId) {
}

function loadPersDiv(persid, func) {
}

function addOrUpdateDesigneeContact() {
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactaddOrEditDesigneeContact.action');
}

function addOrUpdatePioContact() {
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactaddOrEditPIOContact.action');
}

function editDesigneeContact(selSCId) {
	$('process').value='edit';
    $('dscToEdit').value=selSCId;
    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactviewDesigneeStudyContact.action');
}

function editPioContact(selSCId) {
	$('process').value='edit';
	$('pscToEdit').value=selSCId;
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactviewPioStudyContact.action');
}

function saveStudyContacts() {
	submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactsaveFinalChanges.action');
}

function cancelStudyContacts() {
	 var result = confirm("All changes made to study contacts will be cancelled. Please confirm?");
	 if (result == true) {
		    submitStudyContact('reportStudyContactsForm', 'ajaxResultsReportingContactcancel.action');
	 }
}

function submitStudyContact(scFormName, scUrl) {
    jQuery.ajax(
            {
                type : "POST",
                url : scUrl,
                data : jQuery('#reportStudyContactsForm').serialize(),
                success : function(data) {
                    jQuery('#studycontacts').html(data);
                    }
            });
}

</SCRIPT>

</head>

 <div id="scdiv" class="box">
    <pa:sucessMessage/>
    <pa:failureMessage/>
    <s:if test="hasActionErrors()">
        <div class="action_error_msg"><s:actionerror escape="false"/></div>
    </s:if>
    <s:form name="reportStudyContactsForm" id ="reportStudyContactsForm" action="">
        <pa:studyUniqueToken/>
        
        <input type="hidden" id="contactType" name="contactType" />
        <s:hidden id="process" name="process"/>
        <s:hidden id="objectsToDelete" name="objectsToDelete"/>
        <s:hidden id="studyProtocolId" name="studyProtocolId"/>
        
        <jsp:include page="reportAddEditDesigneeStudyContact.jsp"/>
        <jsp:include page="reportAddEditPioStudyContact.jsp"/>
        
        <div class="actionsrow">
             <del class="btnwrapper">
              <ul class="btnrow">
                  <li><s:a href="javascript:void(0);" onclick="saveStudyContacts();"
                          cssClass="btn" id="saveSC">
                          <span class="btn_img"><span class="save">Save </span></span>
                      </s:a></li>
                      <li><s:a href="javascript:void(0);" onclick="cancelStudyContacts();"
                          cssClass="btn" id="cancelSC">
                          <span class="btn_img"><span class="cancel">Cancel </span></span>
                      </s:a></li>
              </ul>
             </del>
         </div>   
    </s:form>
 </div>
 