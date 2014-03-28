<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <div class="accordion">
     <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="view.trial.trialIDs"/><span class="required">*</span></a>
     </div>
          <div id="section1" class="accordion-body in">
            <div class="container">
              <div class="form-group">
                  <label for="trialDTO.leadOrgTrialIdentifier"  class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
                  <div class="col-xs-2">
                      <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier" maxlength="30" size="100"  cssStyle="width:200px" cssClass="charcounter"/>
                      <span class="formErrorMsg">
                      <s:fielderror>
                          <s:param>trialDTO.leadOrgTrialIdentifier</s:param>
                      </s:fielderror>
                      </span>
                  </div>
                  <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the unique identification used to identify, name, or characterize the protocol document, exactly the same as it appears in the protocol document. For Inter-Group trials, enter the Lead Group's trial number. For multi-site trials that have no assigned single center, use the protocol ID."  data-placement="top" data-trigger="hover"></i> 
              </div>
              <div class="form-group">
                 <label for="trialDTO.nctIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.nctNumber"/></label>
                 <div class="col-xs-2">
                     <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                     <span class="formErrorMsg">
                         <s:fielderror cssStyle = "white-space:pre-line;">
                             <s:param>trialDTO.nctIdentifier</s:param>
                         </s:fielderror>
                     </span>
                 </div>
                 <i class="fa-question-circle help-text" id="popover" rel="popover" data-content="If the trial has been submitted to ClinicalTrials.gov previously, enter the number assigned to the trial by PRS (ClinicalTrials.gov)."  data-placement="top" data-trigger="hover"></i> </div>
              </div>
          </div>
      </div>
           
