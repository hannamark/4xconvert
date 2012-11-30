<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title><fmt:message key="studyProtocol.general.title"/></title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

        <c:url value="/protected/popupOrglookuporgs.action" var="lookupOrgUrl"/>
        <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
        <c:url value="/protected/ajaxTrialValidationgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
        <c:url value="/protected/ajaxGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
        <script type="text/javascript" language="javascript">
            var orgid;
            var persid;
            var contactMail;
            var contactPhone;
            var selectedName;

            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function handleAction() {
                document.forms[0].action="generalTrialDesignupdate.action";
                document.forms[0].submit();
            }

            //function which handles the remove of the Central contact.
            function handleRemove() {
                document.forms[0].action="generalTrialDesignremoveCentralContact.action";
                document.forms[0].submit();
            }

            function lookup4loadresponsibleparty() {
               var orgid = $('sponsorIdentifier').value;
               showPopup('${lookupOrgContactsUrl}?orgContactIdentifier='+orgid, createOrgContactDiv, 'Select Responsible contact');
            }
        
            function setorgid(orgIdentifier) {
                orgid = orgIdentifier;
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

            function lookupCentralContact() {
                showPopup('${lookupPersUrl}', loadCentralContactDiv, 'Select Central Contact');
            }

            function loadCentralContactDiv() {
                var url = 'ajaxTrialValidationdisplayCentralContact.action';
                var params = { persId: persid };
                $('gtdDTO.centralContactTitle').value = '';
                $('gtdDTO.centralContactIdentifier').value =  persid;
                var div = $('loadCentralContactDiv');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }

            function lookupGenericCentralContact() {
                var orgid = $('gtdDTO.leadOrganizationIdentifier').value;
                showPopup('${lookupOrgGenericContactsUrl}?orgGenericContactIdentifier='+orgid+'&type=Site', createGenericCentralContactDiv, 'Select Generic Contact');
            }

            function createGenericCentralContactDiv() {
               $('gtdDTO.centralContactName').value = '';
               $('gtdDTO.centralContactTitle').value = selectedName;
               $('gtdDTO.centralContactIdentifier').value =  persid;
               $("gtdDTO.centralContactEmail").value = contactMail;
               $("gtdDTO.centralContactPhone").value = extractPhoneNumberNoExt(contactPhone);
               $("gtdDTO.centralContactPhoneExtn").value = extractPhoneNumberExt(contactPhone);
            }

            function loadDiv(orgid) {
            	clearValue('gtdDTO.centralContactName');
            	clearValue('gtdDTO.centralContactTitle');
            	clearValue('gtdDTO.centralContactIdentifier');
            	clearValue("gtdDTO.centralContactEmail");
            	clearValue("gtdDTO.centralContactPhone");
            	clearValue("gtdDTO.centralContactPhoneExtn");
            }
            
            function clearValue(elID) {
            	if ($(elID)!=null) {
            		$(elID).value = "";
            	}
            }

            function loadPersDiv(persid, func) {
            }

            function deleteOtherIdentifierRow(rowid){ 
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
                    var params = { otherIdentifier: orgValue, otherIdentifierType: otherIdentifierTypeValue };  
                                      
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
        <h1><fmt:message key="studyProtocol.general.title" /></h1>
        <c:set var="topic" scope="request" value="abstractgeneral"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form>
                <s:token/>
                <pa:studyUniqueToken/>
                <s:actionerror/>                
                <h2>General Trial Details</h2>
                <div class="actionstoprow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><a href="javascript:void(0)" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
                <table class="form">
                    <s:hidden name="gtdDTO.submissionNumber" id="gtdDTO.submissionNumber"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.extension" id="gtdDTO.nonOtherIdentifiers.extension"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.root" id="gtdDTO.nonOtherIdentifiers.root"/>
                    <s:hidden name="gtdDTO.nonOtherIdentifiers.identifierName" id="gtdDTO.nonOtherIdentifiers.identifierName"/>
                    <s:hidden name="gtdDTO.programCodeText" id="gtdDTO.programCodeText"/>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <s:hidden name="gtdDTO.phaseCode" id= "gtdDTO.phaseCode"></s:hidden>
                        <s:hidden name="gtdDTO.phaseAdditionalQualifierCode" id= "gtdDTO.phaseAdditionalQualifierCode"></s:hidden>
                        <s:hidden name="gtdDTO.primaryPurposeCode" id= "gtdDTO.primaryPurposeCode"></s:hidden>
                        <s:hidden name="gtdDTO.primaryPurposeAdditionalQualifierCode" id= "gtdDTO.primaryPurposeAdditionalQualifierCode"></s:hidden>
                        <s:hidden name="gtdDTO.primaryPurposeOtherText" id= "gtdDTO.primaryPurposeOtherText"></s:hidden>        
                        <tr>
                            <td scope="row" class="label">
                                <a href="http://ClinicalTrials.gov" target="_blank">ClinicalTrials.gov</a> XML required?
                            </td>
                            <td>
                                <s:radio name="gtdDTO.ctGovXmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="toggledisplayDivs(this);"/>
                           </td>
                       </tr>
                    </c:if>
                    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
                        <s:hidden name="gtdDTO.centralContactName" id="gtdDTO.centralContactName"></s:hidden>
                        <s:hidden name="gtdDTO.centralContactTitle" id="gtdDTO.centralContactTitle"></s:hidden>
                        <s:hidden name="gtdDTO.centralContactIdentifier" id="gtdDTO.centralContactIdentifier"></s:hidden>
                        <s:hidden name="gtdDTO.centralContactEmail" id="gtdDTO.centralContactEmail"></s:hidden>
                        <s:hidden name="gtdDTO.centralContactPhone" id="gtdDTO.centralContactPhone"></s:hidden>
                    </c:if>
                    <tr>
                        <td scope="row" class="label"><label for="LocalProtocolIdentifier">
                             <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/><span class="required">*</span></label> </td>
                        <td>
                            <s:textfield name="gtdDTO.localProtocolIdentifier" id="gtdDTO.localProtocolIdentifier"/>
                            <span class="formErrorMsg">
                                 <s:fielderror>
                                   <s:param>gtdDTO.localProtocolIdentifier</s:param>
                                 </s:fielderror>
                            </span> 
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="Nct Identifier"><fmt:message key="studyProtocol.nctNumber"/></label> 
                        </td>
                        <td>
                            <s:textfield name="gtdDTO.nctIdentifier" id="gtdDTO.nctIdentifier"/>
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>gtdDTO.nctIdentifier</s:param>
                                </s:fielderror>
                            </span> 
                        </td>
                    </tr>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <td scope="row" class="label">
                                <label for="CtepIdentifier"><fmt:message key="studyProtocol.ctepId"/></label> 
                            </td>
                            <td><s:textfield name="gtdDTO.ctepIdentifier" id="gtdDTO.ctepIdentifier"/>
                            <span class="formErrorMsg">
                                 <s:fielderror>
                                   <s:param>gtdDTO.ctepIdentifier</s:param>
                                 </s:fielderror>
                              </span> </td>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <label for="dcpIdentifier"><fmt:message key="studyProtocol.dcpId"/></label> 
                            </td>
                            <td>
                                <s:textfield name="gtdDTO.dcpIdentifier" id="gtdDTO.dcpIdentifier"/>
                                <span class="formErrorMsg">
                                    <s:fielderror>
                                        <s:param>gtdDTO.dcpIdentifier</s:param>
                                    </s:fielderror>
                                </span> 
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${sessionScope.trialSummary.proprietaryTrial}">
                        <%@ include file="/WEB-INF/jsp/nodecorate/phaseAndPurpose.jsp" %>
                    </c:if>
                    <tr>
                        <th colspan="2"> Title</th>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="acronym"><fmt:message key="studyProtocol.acronym"/></label>
                        </td>
                        <td class="value">
                            <s:textfield name="gtdDTO.acronym" cssStyle="width:86px" maxlength="14"/>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="officialTitle">
                                <fmt:message key="studyProtocol.officialTitle"/>
                                <span class="required">*</span>
                            </label>
                        </td>
                        <td class="value">
                            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:606px" rows="4" 
                                maxlength="4000" cssClass="charcounter" />
                            <span class="formErrorMsg">
                                <s:fielderror>
                                    <s:param>gtdDTO.officialTitle</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2"> <fmt:message key="studyProtocol.trialDescription"/></th>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                           <label for=keywordText><fmt:message key="studyProtocol.keywordText"/></label>
                        </td>
                        <td class="value">
                            <s:textarea name="gtdDTO.keywordText" cssStyle="width:606px" rows="4"
                                maxlength="4000" cssClass="charcounter" />
                        </td>
                    </tr>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <th colspan="2">Other Identifiers</th>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <label for="submitTrial_protocolWebDTO_otherIdentifiers">Other Identifier</label>
                            </td>
                            <td>                               
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
							</td>
                        </tr>
                        <tr>
                            <td colspan="2" class="space">
                                <div id="otherIdentifierdiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp"%>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <%@ include file="/WEB-INF/jsp/nodecorate/gtdValidationpo.jsp" %>
                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                        <tr>
                            <th colspan="2"> Central Contact</th>
                        </tr>
                        <tr>
                            <td/>
                            <td class="info" colspan="2">Assign values to all fields below if central contact information is recorded; otherwise leave these fields empty.</td>
                        </tr>
                        <tr>
                            <td scope="row" class="label" >
                                <label for="nciIdentifier"> Personal Contact </label>
                            </td>
                            <td class="value">
                                <div id="loadCentralContactDiv">
                                    <%@ include file="/WEB-INF/jsp/nodecorate/centralContact.jsp" %>
                                </div>
                            </td>
                        </tr>
                      <tr>
                          <td> OR    </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label"><s:label for="Generic Contact">Generic Contact:</s:label></td>
                          <td>
                              <table>
                                  <tr>
                                      <td>
                                          <s:textfield label="Central Contact title" name="gtdDTO.centralContactTitle" id="gtdDTO.centralContactTitle" size="30"
                                                       readonly="true" cssClass="readonly" cssStyle="width:200px"/>
                                      </td>
                                      <td>
                                          <ul style="margin-top:-1px;">
                                              <li style="padding-left:0">
                                                  <a href="javascript:void(0)" class="btn" onclick="lookupGenericCentralContact();" title="Opens a popup form to select Central Contact">
                                                      <span class="btn_img"><span class="person">Look Up Generic Contact</span></span>
                                                  </a>
                                              </li>
                                          </ul>
                                      </td>
                                  </tr>
                              </table>
                          </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label">
                               Email Address:
                          </td>
                          <td class="value">
                              <s:textfield name="gtdDTO.centralContactEmail" id="gtdDTO.centralContactEmail" maxlength="200" size="100"  cssStyle="width:200px" />
                              <span class="formErrorMsg">
                                  <s:fielderror>
                                      <s:param>gtdDTO.centralContactEmail</s:param>
                                  </s:fielderror>
                              </span>
                          </td>
                      </tr>
                      <tr>
                          <td scope="row" class="label">Phone Number:</td>
                          <td class="value">
                              <s:textfield name="gtdDTO.centralContactPhone" id="gtdDTO.centralContactPhone" maxlength="200" size="15"  cssStyle="width:100px" />
                                Extn:<s:textfield name="gtdDTO.centralContactPhoneExtn" id="gtdDTO.centralContactPhoneExtn" maxlength="15" size="10"  cssStyle="width:60px" />
                              <span class="formErrorMsg">
                                  <s:fielderror>
                                      <s:param>gtdDTO.centralContactPhone</s:param>
                                  </s:fielderror>
                              </span>
                          </td>
                      </tr>
                    </c:if>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li><a href="javascript:void(0)" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>