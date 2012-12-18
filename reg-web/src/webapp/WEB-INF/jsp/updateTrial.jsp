<%@ page import="gov.nih.nci.registry.util.Constants" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="updateOrAmendMode" value="${true}" />
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="update.trial.page.title"/></title>
        <s:head/>
        <!-- po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/showhide.js'/>"></script>
        <!-- /po integration -->
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "trialDTO.statusDate", "updateTrial");
            addCalendar("Cal2", "Select Date", "trialDTO.startDate", "updateTrial");
            addCalendar("Cal3", "Select Date", "trialDTO.primaryCompletionDate", "updateTrial");
            addCalendar("Cal4", "Select Date", "trialDTO.completionDate", "updateTrial");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        </script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxManageGrantsActionshowWaitDialog.action" var="reviewProtocolUrl"/>
        <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            
            function reviewProtocolUpdate() {
                submitFirstForm("save", "updateTrialreviewUpdate.action");
                showPopWin('${reviewProtocolUrl}', 600, 200, '', 'Review Register Trial');
            }
            
            function cancelProtocol(){
                submitFirstForm("Submit", "updateTrialcancel.action");
            }
            
            function submitFirstForm(value, action) {
                var form = document.forms[0];
                if (value != null) {
                    form.page.value = value;
                }
                form.action = action;
                form.submit();
            }
            
            function trim(val) {
                var ret = val.replace(/^\s+/, '');
                ret = ret.replace(/\s+$/, '');
                return ret;
            }
            
            function addGrant() {
                var fundingMechanismCode = $('fundingMechanismCode').value;
                var nihInstitutionCode = $('nihInstitutionCode').value;
                var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
                var serialNumber = trim($('serialNumber').value);
                var isValidGrant;
                var isSerialEmpty = false;
                var alertMessage = "";
                if (fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
                    isValidGrant = false;
                    alertMessage = "Please select a Funding Mechanism";
                }
                if (nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
                    isValidGrant = false;
                    alertMessage += "\nPlease select an Institute Code";
                }
                if (serialNumber.length == 0 || serialNumber == null) {
                    isValidGrant = false;
                    isSerialEmpty = true;
                    alertMessage += "\nPlease enter a Serial Number";
                }
                if (nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
                    isValidGrant = false;
                    alertMessage += "\nPlease select a NCI Division/Program Code";
                }
                if (isSerialEmpty == false && isNaN(serialNumber)) {
                    isValidGrant = false;
                    alertMessage += "\nSerial Number must be numeric";
                } else if (isSerialEmpty == false && serialNumber != null) {
                           var numericExpression = /^[0-9]+$/;
                           if (!numericExpression.test(serialNumber)) {
                               isValidGrant = false;
                               alertMessage += "\nSerial Number must be numeric";
                           }
                }
                if (isSerialEmpty == false && (serialNumber.length < 5 || serialNumber.length > 6)) {
                    isValidGrant = false;
                    alertMessage += "\nSerial Number must be 5 or 6 digits";
                }
                if (isValidGrant == false) {
                    alert(alertMessage);
                    return false;
                }
                var  url = '/registry/protected/ajaxManageGrantsActionaddGrantForUpdate.action';
                var params = {
                    fundingMechanismCode: fundingMechanismCode,
                    nciDivisionProgramCode: nciDivisionProgramCode,
                    nihInstitutionCode: nihInstitutionCode,
                    serialNumber: serialNumber
                };
                var div = $('grantadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                var aj = callAjaxPost(div, url, params);
                resetGrantRow();
            }
            
            function deleteGrantRow(rowid) {
                var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrantForUpdate.action';
                var params = { uuid: rowid };
                var div = $('grantadddiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function resetGrantRow() {
                $('fundingMechanismCode').value = '';
                $('nihInstitutionCode').value = '';
                $('serialNumber').value = '';
                $('nciDivisionProgramCode').value = '';
            }
            
            function addOtherIdentifier() {
                var orgValue = trim($("otherIdentifierOrg").value);
                if (orgValue != null && orgValue != '') {
                    var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
                    var params = { otherIdentifier: orgValue };
                    var div = $('otherIdentifierdiv');
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value="";
                } else {
                    alert("Please enter a valid Other identifier to add");
                }
            }
            
            function deleteOtherIdentifierRow(rowid) {
                var  url = '/registry/protected/ajaxManageOtherIdentifiersActiondeleteOtherIdentifierUpdate.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            document.observe("dom:loaded", function() {
                                               displayTrialStatusDefinition('trialDTO_statusCode');
                                           });
        </script>
    </head>
    <body>
    
        <!-- main content begins-->
        <h1><fmt:message key="update.trial.page.header"/></h1>
        <c:set var="topic" scope="request" value="updatetrial"/>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <s:form name="updateTrial" id="updateTrial" method="POST" validate="false" enctype="multipart/form-data" cssStyle="display:block; width:100%;">
                <s:token/>
                <s:if test="hasActionErrors()">
                    <div class="error_msg"><s:actionerror/></div>
                </s:if>
                <s:else>
                    <s:actionerror/>
                </s:else>
                <s:hidden name="trialDTO.identifier" id="trialDTO.identifier"/>
                <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
                <s:hidden name="trialDTO.xmlRequired" id="trialDTO.xmlRequired" />
                <s:hidden name="page" />
                <s:hidden name="uuidhidden"/>
                <p>Use this form to update trial information. You can not change the information in certain fields, including the trial title.</p>
                <table class="form">
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateIdentifiersSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/trialOtherIdsSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateDetailsSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateLeadOrganizationSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateSponsorResponsiblePartySection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateSummary4InfoSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateGrantsSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>  
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateIdeIndIndicatorSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateRegulatoryInformationSection.jsp" %>
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateParticipatingSitesSection.jsp" %>      
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateCollaboratorsSection.jsp" %>   
                    <%@ include file="/WEB-INF/jsp/nodecorate/updateTrialDocumentsSection.jsp" %>
                </table>     
                              
                <div id="uploadDocDiv">
                    <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
                </div>
                <p>
                    Please verify ALL the trial information you provided on this screen before clicking the &#34;Review Trial&#34; button below.
                </p>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="reviewProtocolUpdate()"><span class="btn_img"><span class="save">Review Trial</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="cancelProtocol()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>