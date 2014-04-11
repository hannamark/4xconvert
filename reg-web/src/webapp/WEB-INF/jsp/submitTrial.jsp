<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>
<s:head/>
  <c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
  <c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
  <c:url value="/protected/ajaxSubmitTrialActionshowWaitDialog.action" var="submitProtocol"/>
  <c:url value="/protected/ajaxorganizationContactgetOrganizationContacts.action" var="lookupOrgContactsUrl"/>
  <c:url value="/protected/ajaxorganizationGenericContactlookupByTitle.action" var="lookupOrgGenericContactsUrl"/>
  
  <script type="text/javascript" src="${scriptPath}/js/submitTrial.js"></script>
  <script type="text/javascript">
	  function lookup4loadleadorg() {
	      showPopup("${lookupOrgUrl}",loadLeadOrgDiv, 'Select Lead Organization');
	  }
	  
	  function lookup4loadleadpers() {
	      showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
	  }
	  
	  function lookup4sponsor() {
	      showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
	  }
	  
	  function lookup4loadSummary4Sponsor() {
	      showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
	  }
	  document.observe("dom:loaded", function() {
	                                     displayTrialStatusDefinition('trialDTO_statusCode');
	                                 });
	  
	             
	  Event.observe(window, "load", setDisplayBasedOnTrialType);
  
  </script>
</head>
<body>
<!-- main content begins-->
  <h1 class="heading"><span><fmt:message key="submit.trial.page.header"/></span></h1>
  <p>Use this form to register trials with the NCI Clinical Trials Reporting Program. Required fields are marked by asterisks (<span class="required">*</span>).</p>
  <c:set var="topic" scope="request" value="submittrial"/>
  
      <reg-web:failureMessage/>
      <s:form cssClass="form-horizontal" role="form" name="submitTrial" method="POST" enctype="multipart/form-data">
          <s:token/>
          <s:if test="hasActionErrors()">
              <div class="error_msg">
                  <s:actionerror/>
              </div>
          </s:if>
          <s:hidden name="trialDTO.leadOrganizationIdentifier" id="trialDTO.leadOrganizationIdentifier"/>
          <s:hidden name="trialDTO.piIdentifier" id="trialDTO.piIdentifier"/>
          <s:hidden name="trialDTO.sponsorIdentifier" id="trialDTO.sponsorIdentifier"/>
          <s:hidden name="trialDTO.studyProtocolId" id="trialDTO.studyProtocolId"/>
          <s:hidden name="trialDTO.summaryFourFundingCategoryCode" id="trialDTO.summaryFourFundingCategoryCode" />
          <s:hidden name="page" />
          <div class="form-group">
              <label for="organization-type" class="col-xs-4 control-label left-align">XML Required, Enable "Upload from NCI CTRP" in <a data-placement="top" rel="tooltip" data-original-title="Open in new window" href="http://www.clinicaltrials.gov/" target="_new">ClinicalTrials.gov</a>?</label>                    
              <div class="col-xs-4">
                  <s:radio cssClass="radio-inline" name="trialDTO.xmlRequired" id="xmlRequired"  list="#{true:'Yes', false:'No'}" onclick="hidePrimaryCompletionDate(), toggledisplayDivs(this);"/>
                  <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Indicate whether you need an XML file to submit/update your trial to ClinicalTrials.gov." data-placement="top" data-trigger="hover"></i>
              </div>                    
          </div>
          <button type="button" class="expandcollapse btn btn-icon btn-sm btn-default" state="0"><i class="fa-minus-circle"></i> Collapse All</button>
          <div class="accordion-group">
          	<!-- section 1 trial identifiers -->
              <%@ include file="/WEB-INF/jsp/nodecorate/trialIdentifiers.jsp" %>
              <!-- section 2 other identifiers -->
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section2"><fmt:message key="submit.trial.otherIdentifiers"/></a></div>
                      <div id="section2" class="accordion-body in">
                          <div class="container">
                              <div class="form-group">
                                  <label for="otherIdentifierOrg" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherIdentifier"/></label>
                                  <div class="col-xs-4">
                                      <input type="text" name="otherIdentifierOrg" id="otherIdentifierOrg" value="" class="form-control"/>
                                  </div>
                                  <div class="col-xs-4">
                                      <button onclick="addOtherIdentifier();" id="otherIdbtnid" type="button" class="btn btn-icon btn-default"><i class="fa-plus"></i> Add Other Identifier</button>
                                      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Enter an additional trial identifier such as a unique identifier from other registries, NIH grant numbers, or protocol numbers assigned by the Review Board."  data-placement="top" data-trigger="hover"></i> 
                                  </div>
                              </div>
                              <div class="form-group">
                                  <div class="col-xs-8 align-center" id="otherIdentifierdiv">
                                      <%@ include file="/WEB-INF/jsp/nodecorate/displayOtherIdentifiers.jsp" %>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="accordion">
                   <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section3"><fmt:message key="submit.trial.trialDetails"/><span class="required">*</span></a>
                   </div>
                       <div id="section3" class="accordion-body in">
                           <div class="container">
                               <div class="form-group">
                               <label for="trialDTO.officialTitle" class="col-xs-4 control-label"><fmt:message key="submit.trial.title"/><span class="required">*</span></label>
                                   <div class="col-xs-4">
                                    <s:textarea id="trialDTO.officialTitle" name="trialDTO.officialTitle"  cols="75" rows="4" maxlength="4000" cssClass="form-control charcounter"/>
                                    <span class="alert-danger">
                                        <s:fielderror>
                                            <s:param>trialDTO.officialTitle</s:param>
                                        </s:fielderror>
                                    </span>
                                  </div>
                                    <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the name of the clinical trial as it appears in the protocol document."  data-placement="top" data-trigger="hover"></i>
                          </div>
                           <%@ include file="/WEB-INF/jsp/nodecorate/phasePurpose.jsp" %>
                       </div>
                   </div>
               </div>
               <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section4"><fmt:message key="submit.trial.leadOrgInvestigator"/><span class="required">*</span></a>
                  </div>
                  <div id="section4" class="accordion-body in">
                      <div class="container">
                      <div class="form-group">                                
                          <label for="trialDTO.leadOrganizationName" class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrganization"/><span class="required">*</span></label>
                          <div id="loadOrgField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadOrganization.jsp" %>
                          </div>
                      </div>                
                      <div class="form-group">
                          <label for="trialDTO.piName" class="col-xs-4 control-label"><fmt:message key="submit.trial.principalInvestigator"/><span class="required">*</span></label>
                          <div id="loadPersField">
                              <%@ include file="/WEB-INF/jsp/nodecorate/trialLeadPrincipalInvestigator.jsp" %>
                          </div>              
                      </div>
                  </div>
              </div>
              </div>
              <s:if test="%{trialDTO.xmlRequired == true}">
                  <div id="sponsorDiv" style="display:''">
                      <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                  </div>
              </s:if>
              <s:else>
                  <div id="sponsorDiv" style="display:none">
                      <%@ include file="/WEB-INF/jsp/nodecorate/trialResponsibleParty.jsp" %>
                  </div>
              </s:else>
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section6"><fmt:message key="update.proprietary.trial.summary4Info"/><span class="required">*</span></a>
                  </div>
                  <div id="section6" class="accordion-body in">
                      <div class="container">
                          <div class="form-group">
                              <label for="trialDTO.summaryFourFundingCategoryCode" class="col-xs-4 control-label"><fmt:message key="update.trial.summary4FundingCategory"/></label>
                              <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                              <div class="col-xs-4">
                                 <s:select headerKey="" headerValue="--Select--" id="trialDTO.summaryFourFundingCategoryCode" 
                                     name="trialDTO.summaryFourFundingCategoryCode" list="#summaryFourFundingCategoryCodeValues" 
                                     cssClass="form-control" disabled="true"/>
                                 <span class="alert-danger">
                                     <s:fielderror>
                                         <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                                     </s:fielderror>
                                 </span>
                              </div>
                              <div class="col-xs-4">
                              <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Trial category you selected for trial submission."  data-placement="top" data-trigger="hover"></i>
                              </div>
                          </div>
                          <div class="form-group">
                              <label id="trialDTO.summaryFourOrgNameTR" for="trialDTO.summaryFourOrgName" class="col-xs-4 control-label"><fmt:message key="update.proprietary.trial.summary4Sponsor"/><span class="required">*</span></label>
                              <div id="loadSummary4FundingSponsorField"  class="col-xs-8">
                                  <%@ include file="/WEB-INF/jsp/nodecorate/trialSummary4FundingSponsor.jsp" %>
                              </div>                                    
                          </div>
                          <div class="form-group">                               
                              <label for="trialDTO.programCodeText" class="col-xs-4 control-label"><fmt:message key="studyProtocol.summaryFourPrgCode"/></label>
                              <div class="col-xs-4">
                                  <s:textfield id="trialDTO.programCodeText" name="trialDTO.programCodeText"  maxlength="100" size="100"  cssClass="form-control"/>
                                  <span class="alert-danger">
                                      <s:fielderror>
                                          <s:param>trialDTO.programCodeText</s:param>
                                      </s:fielderror>
                                  </span>
                              </div>
                              <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the Program Code that includes the study."  data-placement="top" data-trigger="hover"></i>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="accordion">
                  <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section7"><fmt:message key="submit.trial.grantInfo"/><span class="required">*</span></a></div>
                  <div id="section7" class="accordion-body in">
                      <div class="container">
                      <p><fmt:message key="submit.trial.grantInstructionalText"/></p>              
                          <div class="form-group">
                              <label for="nciGrant" class="col-xs-4 control-label">Is this trial funded by an NCI grant? <span class="required">*</span></label>
                              <div class="col-xs-4">
                                  <s:radio cssClass="radio-inline" name="trialDTO.nciGrant" id="nciGrant"  list="#{true:'Yes', false:'No'}" />
                              </div>
                          </div> 
                          <table class="table table-bordered">
                              <thead>
                                  <tr>
                                      <th><label for="fundingMechanismCode"><fmt:message key="submit.trial.fundingMechanism"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the NIH funding mechanism unique identifier, a 3-character code used to identify areas of extramural research activity applied to funding mechanisms."  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="nihInstitutionCode"><fmt:message key="submit.trial.instituteCode"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the two-letter code identifying the first major-level subdivision, the organization that supports an NIH grant, contract, or inter-agency agreement. The support may be financial or administrative."  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="serialNumber"><fmt:message key="submit.trial.serialNumber"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the number generally assigned sequentially to a series within an Institute, Center, or Division."  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th><label for="nciDivisionProgramCode"> <fmt:message key="submit.trial.divProgram"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the NCI organizational unit that provides funding for the study."  data-placement="top" data-trigger="hover"></i></label></th>
                                      <th style="display:none">
                                          <label for="fundingPercent"><fmt:message key="submit.trial.fundingPercent"/></label>
                                      </th>
                                      <th>&nbsp;</th>
                                  </tr>
                              </thead>
                              <tbody>
                                  <tr>
                                      <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--"
                                               name="fundingMechanismCode"
                                               list="#fundingMechanismValues"
                                               listKey="fundingMechanismCode"
                                               listValue="fundingMechanismCode"
                                               id="fundingMechanismCode"
                                               cssClass="form-control"/>
                                      </td>
                                      <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--"
                                               name="nihInstitutionCode"
                                               list="#nihInstituteCodes"
                                               listKey="nihInstituteCode"
                                               listValue="nihInstituteCode"
                                               id="nihInstitutionCode"
                                                cssClass="form-control"/>
                                      </td>
                                      <td>
                                          <s:textfield name="serialNumber" id="serialNumber" maxlength="200" cssClass="form-control"/>
                                      </td>
                                      <s:set name="programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                                      <td>
                                          <s:select headerKey="" headerValue="--Select--" name="nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  
                                              cssClass="form-control"/>
                                      </td>
                                      <td style="display:none">
                                          <s:textfield name="fundingPercent" id="fundingPercent" maxlength="5" size="5"  cssClass="form-control"/>%
                                      </td>
                                      <td><button type="button" id="grantbtnid" class="btn btn-icon btn-default" onclick="addGrant();"><i class="fa-plus"></i>Add Grant</button></td>
                                  </tr>
                              </tbody>
                          </table>
                          <div class="form-group" id="grantdiv">
                              <%@ include file="/WEB-INF/jsp/nodecorate/displayTrialViewGrant.jsp" %>
                          </div>
                          <span class="alert-danger">
                              <s:fielderror>
                                  <s:param>trialDTO.nciGrant</s:param>
                              </s:fielderror>
                          </span>                              
                   </div>
              </div>
          </div>
           <!-- Status section -->
          <%@ include file="/WEB-INF/jsp/nodecorate/updateStatusSection.jsp" %>
          <div class="accordion">
          	<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section9"><fmt:message key="submit.trial.indInfo"/></a></div>
          		<div id="section9" class="accordion-body in">
            		<div class="container">
              			<p><fmt:message key="submit.trial.indInstructionalText"/></p>
              			<%@ include file="/WEB-INF/jsp/nodecorate/indide.jsp" %>
		              <div id="indidediv">
		                  <%@ include file="/WEB-INF/jsp/nodecorate/addIdeIndIndicator.jsp" %>
		              </div>
          			  <div class="mt10 align-center scrollable"><i class="fa-angle-left"></i> Scroll left/right to view full table <i class="fa-angle-right"></i></div>
            </div>
          </div>
        </div>
        
        <s:if test="%{trialDTO.xmlRequired == true}">
             <div id="regDiv" style="display:''">
                 <!-- Regulatory page -->
                 <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
             </div>
         </s:if>
         <s:else>
             <div id="regDiv" style="display:none">
                 <!-- Regulatory page -->
                 <%@ include file="/WEB-INF/jsp/nodecorate/regulatoryInformation.jsp" %>
             </div>
         </s:else>
              
          <div id="uploadDocDiv">
              <%@ include file="/WEB-INF/jsp/nodecorate/uploadDocuments.jsp" %>
          </div>
         </div>
          <div class="align-center button-row">
		      <button type="button" class="btn btn-icon btn-primary" onclick="partialSave()"><i class="fa-floppy-o"></i>Save as Draft</button>
		      <button type="button" class="btn btn-icon btn-primary" onclick="reviewProtocol()"><i class="fa-file-text-o"></i>Review Trial</button>
		      <button type="button" class="btn btn-icon btn-default" onclick="cancelProtocol()"><i class="fa-times-circle"></i>Cancel</button>
		    </div>
          <s:hidden name="uuidhidden"/>
      </s:form>
</body>
</html>