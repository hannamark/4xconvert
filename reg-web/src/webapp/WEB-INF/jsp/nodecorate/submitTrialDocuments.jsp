<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="form">
  <tr>
    <th colspan="2"><fmt:message key="submit.trial.documents"/></th>
  </tr>
  <tr>
    <td colspan="2" class="space">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="submit.trial.docInstructionalText"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a>
    </td>
  </tr>
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.protocol_document">
        <label for="submitTrial_protocolDoc"><fmt:message key="submit.trial.protocolDocument"/><span class="required">*</span></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
      <s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
        <s:property value="%{#session.protocolDoc.fileName}"/>
        <input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file name="protocolDoc" value="true" cssStyle="width:270px"/>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.protocolDocFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </td>
  </tr>
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.irb_approval">
        <label for="submitTrial_irbApproval"><fmt:message key="submit.trial.irbApproval"/><span class="required">*</span></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
      <s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
        <s:property value="%{#session.irbApprovalDoc.fileName}"/>
        <input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file name="irbApproval" cssStyle="width:270px"/>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.irbApprovalFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </td>
  </tr>
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.list_of_participating_sites">
        <label for="submitTrial_participatingSites"><fmt:message key="submit.trial.participatingSites"/></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
      <s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
        <s:property value="%{#session.participatingSitesDoc.fileName}"/>
        <input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file name="participatingSites" cssStyle="width:270px"/>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.participatingSitesFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </td>
  </tr>
  <tr>
  <td scope="row" class="label">
    <reg-web:displayTooltip tooltip="tooltip.informed_consent_document">
      <label for="submitTrial_informedConsentDocument"><fmt:message key="submit.trial.informedConsent"/></label>
    </reg-web:displayTooltip>
  </td>
  <td class="value">
    <s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
      <s:property value="%{#session.informedConsentDoc.fileName}"/>
      <input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')"/>
    </s:if>
    <s:else>
      <s:file name="informedConsentDocument" cssStyle="width:270px"/>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.informedConsentDocumentFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </td>
  </tr>  

  <%@ include file="/WEB-INF/jsp/nodecorate/uploadOtherDocuments.jsp" %>
  
</table>