<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <div class="accordion">
     <div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#parent" href="#section1"><fmt:message key="view.trial.trialIDs"/><span class="required">*</span></a></div>
     <div id="section1" class="accordion-body in">
       <div class="container">
         <div class="form-group">
             <label for="trialDTO.leadOrgTrialIdentifier"  class="col-xs-4 control-label"><fmt:message key="submit.trial.leadOrgidentifier"/><span class="required">*</span></label>
             <div class="col-xs-4">
                 <s:textfield id="trialDTO.leadOrgTrialIdentifier" name="trialDTO.leadOrgTrialIdentifier" maxlength="30" size="100" cssClass="form-control charcounter"/>
             	 <span class="alert-danger">
             	 	<s:fielderror>
             	 		<s:param>trialDTO.leadOrgTrialIdentifier</s:param>
             	 	</s:fielderror>
             	 </span>
             </div>
             <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Enter the unique identification used to identify, name, or characterize the protocol document, exactly the same as it appears in the protocol document. For Inter-Group trials, enter the Lead Group's trial number. For multi-site trials that have no assigned single center, use the protocol ID."  data-placement="top" data-trigger="hover"></i></div>
         </div>
         <div class="form-group">
            <label for="trialDTO.nctIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.nctNumber"/></label>
            <div class="col-xs-4">
                <s:textfield id="trialDTO.nctIdentifier" name="trialDTO.nctIdentifier"  maxlength="200" cssClass="form-control"/>
                <span class="alert-danger">
                    <s:fielderror cssStyle = "alert-danger">
                        <s:param>trialDTO.nctIdentifier</s:param>
                    </s:fielderror>
                </span>
            </div>
            <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="If the trial has been submitted to ClinicalTrials.gov previously, enter the number assigned to the trial by PRS (ClinicalTrials.gov)."  data-placement="top" data-trigger="hover"></i> </div>
         </div>
         <div class="form-group">
            <label  class="col-xs-4 control-label"><fmt:message key="view.trial.identifier"/></label>
            <div class="col-xs-4">
                <s:property value="trialDTO.assignedIdentifier"/>
            </div>
         </div>
         <c:if test="${trialDTO.ctepIdentifier != null }">
		    <div class="form-group">
	            <label for="trialDTO.ctepIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.ctepIdentifier"/></label>
	            <div class="col-xs-4">
	                <s:textfield id="trialDTO.ctepIdentifier" name="trialDTO.ctepIdentifier"  maxlength="200"  cssClass="form-control"/>
	                <span class="alert-danger">
	                    <s:fielderror cssStyle = "alert-danger">
	                        <s:param>trialDTO.ctepIdentifier</s:param>
	                    </s:fielderror>
	                </span>
	            </div>
	            <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="CTEP Identifier"  data-placement="top" data-trigger="hover"></i></div> 
		   </div>
		</c:if>
		<c:if test="${trialDTO.dcpIdentifier != null }">
		    <div class="form-group">
	            <label for="trialDTO.dcpIdentifier" class="col-xs-4 control-label"><fmt:message key="submit.trial.dcpIdentifier"/></label>
	            <div class="col-xs-4">
	                <s:textfield id="trialDTO.dcpIdentifier" name="trialDTO.dcpIdentifier"  maxlength="200"  cssClass="form-control"/>
	                <span class="alert-danger">
	                    <s:fielderror cssStyle = "alert-danger">
	                        <s:param>trialDTO.dcpIdentifier</s:param>
	                    </s:fielderror>
	                </span>
	            </div>
	            <div class="col-xs-4"><i class="fa-question-circle help-text" id="popover" rel="popover" data-content="CTEP Identifier"  data-placement="top" data-trigger="hover"></i></div> 
		   </div>
		</c:if>
     </div>
 </div>

