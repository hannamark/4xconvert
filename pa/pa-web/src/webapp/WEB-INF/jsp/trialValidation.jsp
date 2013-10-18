<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="trialValidation.page.title"/></title>   
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <script type="text/javascript" language="javascript"> 
            var orgid;
            var persid;
            var respartOrgid;
            var contactMail;
            var contactPhone;
            var selectedName;    
            var orgname;
            
            jQuery(function() {
                setFocusToFirstControl();
                jQuery("#saveButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationupdate.action";
                    form.submit();
                });
                jQuery("#acceptButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationaccept.action";
                    form.submit();
                });
                jQuery("#rejectButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "trialValidationreject.action";
                    form.submit();
                });
                jQuery("#onholdButton").bind("click", function(ev) {
                    var form = document.forms[0];
                    form.action = "onhold.action";
                    // We are essentially doing a redirect to a different Action here using Form POST submit.
                    // Since Trial Validation form here is being submitted to a different Action, this generates
                    // a bunch of harmless error messages in the log, which are, however, bothering the QA: https://tracker.nci.nih.gov/browse/PO-4815
                    // We'll effectively disable all form controls, except struts token, here to avoid those errors.
                    try {                    
                    	$(form).getElements().each(function (el) {
                    		if (!((el.name.indexOf("token") == 0) || (el.name.indexOf("struts.token") == 0))) 
                    			el.disable();
                    	});
                    } catch(err) {
                    	// oops.
                    }
                    form.submit();
                });
            });
        
            function setorgid(orgIdentifier, name) {
                orgid = orgIdentifier;
                orgname = name;
            }
            
            function setpersid(persIdentifier,name,email,phone) {
                persid = persIdentifier;
                selectedName = name;
                contactMail = email;
                contactPhone = phone;
            }
            
            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }
            
            function loadSummary4SponsorDiv() {
                var url = 'ajaxTrialValidationdisplaySummary4FundingSponsor.action';
                var params = { orgId: orgid };
                var div = $('loadSummary4FundingSponsorField');   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading Summary 4 Sponsor...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function deleteSummary4SponsorRow(rowid) {
                var  url = '/pa/protected/ajaxTrialValidationdeleteSummaryFourOrg.action';
                var params = { uuid: rowid };
                var div = $('loadSummary4FundingSponsorField');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);
            }
        
            function loadDiv(orgid) {
            }
            
            function loadPersDiv(persid, func) {
            }
            
            function lookup4loadresponsibleparty() {
                var orgid = document.getElementById('sponsorIdentifier').value;
                showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
            }
            
            function lookup4loadresponsiblepartygenericcontact() {
                var orgid = document.getElementById('sponsorIdentifier').value;
                showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid,  createOrgGenericContactDiv, 'Select Responsible Party Generic Contact');
            }
        
            function deleteOtherIdentifierRow(rowid) { 
                var  url = 'ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action';
                var params = { uuid: rowid };
                var div = $('otherIdentifierdiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
                var aj = callAjaxPost(div, url, params);           
            }

            function saveIdentifierRow(rowid){
            	var orgValue = $("identifier_"+rowid).value;
                var otherIdentifierTypeValue = $("identifierType_"+rowid).value;
                if (orgValue != null && orgValue != '') {
	            	var  url = 'ajaxManageOtherIdentifiersActionsaveOtherIdentifierRow.action';
	                var params = { uuid: rowid, otherIdentifier : orgValue,
	    	                 otherIdentifierType : otherIdentifierTypeValue };
	                var div = $('otherIdentifierdiv');
	                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Saving...</div>';
	                var aj = callAjaxPost(div, url, params); 
                } else {
                    alert("Please enter a valid Other identifier.");
                }
            }

            function editIdentifierRow(rowid){
            	jQuery("#identifierDiv_"+rowid).hide();
            	jQuery("#identifierInputDiv_"+rowid).show(); 
            	jQuery("#identifierTypeDiv_"+rowid).hide();
            	jQuery("#identifierTypeInputDiv_"+rowid).show();
            	jQuery("#actionEdit_"+rowid).hide();
            	jQuery("#actionSave_"+rowid).show();            	            	
            }
        
            function addOtherIdentifier() {
                var orgValue = $("otherIdentifierOrg").value;
                var otherIdentifierTypeValue = $("otherIdentifierType").value;
                if (orgValue != null && orgValue != '') {
                    var  url = 'ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';   
                    var params = { otherIdentifier: orgValue, otherIdentifierType: otherIdentifierTypeValue}; 
                    var div = $('otherIdentifierdiv');   
                    div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
                    var aj = callAjaxPost(div, url, params);
                    $("otherIdentifierOrg").value="";
                } else {
                    alert("Please enter a valid Other identifier.");
                }
            }
        </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="validatetrial"/>
        <h1><fmt:message key="trialValidation.page.title" /></h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box" >
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form>
                <s:token/>
                <pa:studyUniqueToken/>
                <s:actionerror/> 
                <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
                <s:hidden name="gtdDTO.studyProtocolId" id="gtdDTO.studyProtocolId"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.extension" id="gtdDTO.nonOtherIdentifiers.extension"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.root" id="gtdDTO.nonOtherIdentifiers.root"/>
                <s:hidden name="gtdDTO.nonOtherIdentifiers.identifierName" id="gtdDTO.nonOtherIdentifiers.identifierName"/>
                <s:hidden name="gtdDTO.ctepIdentifier"  id="gtdDTO.ctepIdentifier"/>
                <s:hidden name="gtdDTO.dcpIdentifier" id ="gtdDTO.dcpIdentifier"/>
                <s:hidden name="gtdDTO.keywordText" id ="gtdDTO.keywordText"/>
                <h2><fmt:message key="trialValidation.trialDetails" /></h2>
                <table class="form">
                    <pa:valueRow labelKey="studyProtocol.nciIdentifier">
                        <c:out value="${sessionScope.trialSummary.nciIdentifier }"/>
                    </pa:valueRow>
                    <c:choose>
                        <c:when test="${!sessionScope.trialSummary.proprietaryTrial}">
                            <tr>
                                <td scope="row" class="label">
                                    <a href="http://ClinicalTrials.gov" target="_blank"><fmt:message key="trialValidation.ctgov" /></a> <fmt:message key="trialValidation.xmlRequired" />
                                </td>
                                <td>
                                    <s:radio name="gtdDTO.ctGovXmlRequired" id="gtdDTO.ctGovXmlRequired" list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <s:hidden name="gtdDTO.ctGovXmlRequired" id="gtdDTO.ctGovXmlRequired"/>
                        </c:otherwise>
                    </c:choose>
                    <pa:valueRow labelFor="localProtocolIdentifier" labelKey="studyCoordinatingCenterLead.localProtocolIdentifer" required="true">
                        <s:textfield id="localProtocolIdentifier" name="gtdDTO.localProtocolIdentifier" cssStyle="width:206px" maxlength="50"/> 
                        <pa:fieldError fieldName="gtdDTO.LocalProtocolIdentifier"/>
                    </pa:valueRow>
                    <tr>
                        <td scope="row" class="label">
                            <label for="nctIdentifier"><fmt:message key="studyProtocol.nctNumber"/></label> 
                        </td>
                        <td class="value">
                            <s:textfield id="nctIdentifier" name="gtdDTO.nctIdentifier" cssStyle="width:206px" maxlength="50"/>
                            <span class="formErrorMsg">
                                <s:fielderror cssStyle = "white-space:pre-line;">
                                    <s:param>gtdDTO.nctIdentifier</s:param>
                                </s:fielderror>
                            </span> 
                        </td>
                    </tr>
                    <pa:valueRow labelKey="studyProtocol.proprietaryTrial">
                        <s:property value="gtdDTO.proprietarytrialindicator"/>
                    </pa:valueRow>
                    <pa:valueRow labelFor="officialTitle" labelKey="studyProtocol.officialTitle" required="true">
                        <s:textarea id="officialTitle" name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4"/>
                        <pa:fieldError fieldName="gtdDTO.OfficialTitle"/>
                    </pa:valueRow>
                    <%@ include file="/WEB-INF/jsp/nodecorate/phaseAndPurpose.jsp" %>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <pa:titleRow titleKey="trialValidation.otherIdentifiers"/>
                        <pa:valueRow labelKey="studyProtocol.otherId">
						<table>
							<tr>								
								<td><select name="otherIdentifierType"
									id="otherIdentifierType" style="margin-top: 0px;">
										<option value="0">Other</option>
										<option value="1">Obsolete NCT ID</option>
										<option value="2">Duplicate NCI ID</option>
								</select></td>
								<td><input type="text" name="otherIdentifierOrg"
									id="otherIdentifierOrg" value="" />&nbsp;</td>
								<td><input type="button" id="otherIdbtnid"
									value="Add Other Identifier" onclick="addOtherIdentifier();" />
								</td>
							</tr>
						</table>
					</pa:valueRow>
                        <tr>
                            <td colspan="2" class="space">
                                <div id="otherIdentifierdiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp"%>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>  
                    <pa:titleRow titleKey="trialValidation.summary4Info"/>  
                    <pa:valueRow labelFor="summaryFourFundingCategoryCode" labelKey="studyProtocol.summaryFourFundingCategoryCode">
                        <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                        <s:select id="summaryFourFundingCategoryCode" name="gtdDTO.summaryFourFundingCategoryCode" headerKey="" headerValue="" 
                                list="#summaryFourFundingCategoryCodeValues" value="gtdDTO.summaryFourFundingCategoryCode" 
                                cssStyle="width:206px" />
                    </pa:valueRow>
                    <pa:valueRow labelKey="studyProtocol.summaryFourFundingSource">
                        <div id="loadSummary4FundingSponsorField">
                            <%@ include file="/WEB-INF/jsp/nodecorate/displaySummary4FundingSponsor.jsp" %>
                        </div> 
                    </pa:valueRow>
                    <pa:valueRow labelFor="programCodeText" labelKey="studyProtocol.summaryFourPrgCode">
                        <s:textfield id="programCodeText" name="gtdDTO.programCodeText"  maxlength="100" size="100" cssStyle="width:200px" />
                        <pa:fieldError fieldName="gtdDTO.programCodeText"/>
                    </pa:valueRow>
                    <s:if test="gtdDTO.submissionNumber > 1">
                        <pa:titleRow titleKey="trialValidation.amendmentInfo"/>
                        <pa:valueRow labelFor="amendmentReasonCode" labelKey="studyProtocol.amendmentReasonCodeValues" required="true">
                            <s:set name="amendmentReasonCodeValues" value="@gov.nih.nci.pa.enums.AmendmentReasonCode@getDisplayNames()" />
                            <s:select id="amendmentReasonCode" name="gtdDTO.amendmentReasonCode" headerKey="" headerValue="" 
                                      list="#amendmentReasonCodeValues" value="gtdDTO.amendmentReasonCode" 
                                      cssStyle="width:206px" />
                            <pa:fieldError fieldName="gtdDTO.amendmentReasonCode"/>          
                        </pa:valueRow>
                    </s:if>    
                </table>  
                <pa:buttonBar>
                    <pa:displayWhenCheckedOut>
                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Submitted' || sessionScope.trialSummary.documentWorkflowStatusCode.code == 'Amendment Submitted' }">
                            <pa:button id="saveButton" imgClass="save" labelKey="trialValidation.button.save"/>
                            <pa:button id="acceptButton" imgClass="confirm" labelKey="trialValidation.button.accept"/>
                            <pa:button id="rejectButton" imgClass="cancel"  labelKey="trialValidation.button.reject"/>
                            <pa:button id="onholdButton" imgClass="history"  labelKey="trialValidation.button.onhold"/>
                        </c:if>
                        <c:if test="${sessionScope.trialSummary.documentWorkflowStatusCode.code == 'On-Hold'}">
                            <pa:button id="onholdButton" imgClass="save"  labelKey="trialValidation.button.offhold"/>
                        </c:if>
                    </pa:displayWhenCheckedOut>  
                </pa:buttonBar>   
             </s:form>
         </div>
     </body>
 </html>