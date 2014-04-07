<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <p><fmt:message key="submit.trial.docInstructionalText"/></p>
  	<p class="mb20"><a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a></p>
  	<div class="form-group">
        <label for="protocolDoc" class="col-xs-4 control-label"><fmt:message key="submit.trial.protocolDocument"/><span class="required">*</span></label>
      <div class="col-xs-4">
      <s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
        <s:property value="%{#session.protocolDoc.fileName}"/>
        <input id="protocolDoc" type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file id="protocolDoc" name="protocolDoc" value="true" />
        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Browse to locate and upload the protocol document."  data-placement="top" data-trigger="hover"></i>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.protocolDocFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
  </div>
  <div class="form-group">
        <label for="irbApproval" class="col-xs-4 control-label"><fmt:message key="submit.trial.irbApproval"/><span class="required">*</span></label>
      <div class="col-xs-4">
      <s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
        <s:property value="%{#session.irbApprovalDoc.fileName}"/>
        <input id="irbApproval" type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"/>      
      </s:if>
      <s:else>
        <s:file id="irbApproval" name="irbApproval" />
        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Browse to locate and upload the IRB approval document."  data-placement="top" data-trigger="hover"></i>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.irbApprovalFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
  </div>
  <div class="form-group">
        <label for="participatingSites" class="col-xs-4 control-label"><fmt:message key="submit.trial.participatingSites"/></label>
      <div class="col-xs-4">
      <s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
        <s:property value="%{#session.participatingSitesDoc.fileName}"/>
        <input id="participatingSites" type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file id="participatingSites" name="participatingSites" />
        <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Browse to locate and upload the Participating Sites document. This document includes site recruitment status and date, target accrual, and site principal investigators."  data-placement="top" data-trigger="hover"></i> 
      	<span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.participatingSitesFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
  </div>
  <div class="form-group">
      <label for="informedConsentDocument" class="col-xs-4 control-label"><fmt:message key="submit.trial.informedConsent"/></label>
    <div class="col-xs-4">
    <s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
      <s:property value="%{#session.informedConsentDoc.fileName}"/>
      <input id="informedConsentDocument" type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"/>
    </s:if>
    <s:else>
      <s:file id="informedConsentDocument" name="informedConsentDocument" />
      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="If the informed consent is not included in the protocol document, click Browse to locate and upload the Informed Consent document."  data-placement="top" data-trigger="hover"></i>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.informedConsentDocumentFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </div>
  </div>  

  <%@ include file="/WEB-INF/jsp/nodecorate/uploadOtherDocuments.jsp" %>
  