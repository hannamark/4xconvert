<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table class="form">
  <tr>
    <th colspan="2"><fmt:message key="update.trial.documents"/></th>
  </tr>
  <tr>
    <td colspan="2" class="space">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">
      <fmt:message key="update.trial.docInstructionalText"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <a href="#" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a>
    </td>
  </tr>
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.protocol_document">
        <label for="updateTrial_protocolDoc"><fmt:message key="update.trial.protocolDocument"/></label>
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
        <label for="updateTrial_irbApproval"><fmt:message key="update.trial.irbApproval"/></label>
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
        <label for="updateTrial_participatingSites"><fmt:message key="update.trial.participatingSites"/></label>
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
      <label for="updateTrial_informedConsentDocument"><fmt:message key="update.trial.informedConsent"/></label>
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
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.other">
        <label for="updateTrial_otherDocument"><fmt:message key="update.trial.otherDocument"/></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
      <s:if test="%{#session.otherDoc.typeCode.equals('Other')}">
        <s:property value="%{#session.otherDoc.fileName}"/>
        <input type="button" value="Remove" onclick="deleteDocument('<s:property value='%{#session.otherDoc.typeCode}'/>')"/>
      </s:if>
      <s:else>
        <s:file name="otherDocument" cssStyle="width:270px"/>
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.otherDocumentFileName</s:param>
          </s:fielderror>
        </span>
      </s:else>
    </td>
  </tr>
</table>