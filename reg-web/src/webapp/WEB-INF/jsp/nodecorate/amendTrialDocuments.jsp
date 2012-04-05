<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="box">
  <h3>Amendment Related Documents</h3>
  <table class="form">
    <tr>
      <td colspan="2" class="space">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2"><fmt:message key="submit.trial.docInstructionalText" /></td>
    </tr>
    <tr>
      <td colspan="2"><a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a></td>
    </tr>
    <tr>
      <td scope="row" class="label">
        <label for="submitTrial_protocolDoc"> 
          <fmt:message key="amend.trial.protocolDocument" /> <span class="required">*</span>
        </label>
      </td>
      <td class="value">
        <s:if test="%{#session.protocolDoc.typeCode.equals('Protocol Document')}">
          <s:property value="%{#session.protocolDoc.fileName}" />
          <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.protocolDoc.typeCode}'/>')" />
        </s:if>
        <s:else>
          <s:file name="protocolDoc" id="protocolDoc" value="true" cssStyle="width:270px" />
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
        <label for="submitTrial_otherDocument">
          <fmt:message key="amend.trial.changeMemo" />
          <span class="required">**</span>
        </label>
      </td>
      <td class="value">
        <s:if test="%{#session.changeMemoDoc.typeCode.equals('Change Memo Document')}">
          <s:property value="%{#session.changeMemoDoc.fileName}" />
          <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.changeMemoDoc.typeCode}'/>')" />
        </s:if> 
        <s:else>
          <s:file name="changeMemoDoc" id="changeMemoDoc" cssStyle="width:270px" />
          <span class="formErrorMsg"> 
            <s:fielderror>
              <s:param>trialDTO.changeMemoDocFileName</s:param>
            </s:fielderror>
          </span>
        </s:else>
      </td>
    </tr>
    <tr>
      <td scope="row" class="label">
        <label for="submitTrial_otherDocument">
          <fmt:message key="amend.trial.protocolHighlight" /> <span class="required">**</span> 
        </label>
      </td>
      <td class="value">
        <s:if  test="%{#session.protocolHighlightDoc.typeCode.equals('Protocol Highlighted Document')}">
          <s:property value="%{#session.protocolHighlightDoc.fileName}" />
            <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.protocolHighlightDoc.typeCode}'/>')" />
        </s:if> 
        <s:else>
          <s:file name="protocolHighlightDocument" id="protocolHighlightDocument" cssStyle="width:270px" />
          <span class="formErrorMsg"> 
            <s:fielderror>
              <s:param>trialDTO.protocolHighlightDocumentFileName</s:param>
            </s:fielderror>
          </span>
        </s:else>
      </td>
    </tr>
    <tr>
      <td scope="row" class="label">
        <label for="submitTrial_irbApproval"> 
          <fmt:message  key="submit.trial.irbApproval" /> 
          <span class="required">*</span> 
        </label>
      </td>
      <td class="value">
        <s:if test="%{#session.irbApprovalDoc.typeCode.equals('IRB Approval Document')}">
          <s:property value="%{#session.irbApprovalDoc.fileName}" />
          <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.irbApprovalDoc.typeCode}'/>')" />
        </s:if>
        <s:else>
          <s:file name="irbApproval" id="irbApproval" cssStyle="width:270px" />
          <span class="formErrorMsg">
            <s:fielderror>
              <s:param>trialDTO.irbApprovalFileName</s:param>
            </s:fielderror> </span>
        </s:else>
      </td>
    </tr>
    <tr>
      <td scope="row" class="label">
        <label for="submitTrial_participatingSites">
          <fmt:message key="submit.trial.participatingSites" />
        </label>
      </td>
      <td class="value">
        <s:if test="%{#session.participatingSitesDoc.typeCode.equals('Participating sites')}">
          <s:property value="%{#session.participatingSitesDoc.fileName}" />
            <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.participatingSitesDoc.typeCode}'/>')" />
        </s:if> 
        <s:else>
          <s:file name="participatingSites" id="participatingSites" cssStyle="width:270px" />
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
        <label for="submitTrial_informedConsentDocument">
          <fmt:message key="submit.trial.informedConsent" />
        </label>
      </td>
      <td class="value">
        <s:if test="%{#session.informedConsentDoc.typeCode.equals('Informed Consent Document')}">
          <s:property value="%{#session.informedConsentDoc.fileName}" />
          <input type="button" value="Remove"
                    onclick="deleteDocument('<s:property value='%{#session.informedConsentDoc.typeCode}'/>')" />
        </s:if>
        <s:else>
          <s:file name="informedConsentDocument" id="informedConsentDocument" cssStyle="width:270px" />
          <span class="formErrorMsg"> 
            <s:fielderror>
              <s:param>trialDTO.informedConsentDocumentFileName</s:param>
            </s:fielderror>
          </span>
        </s:else>
      </td>
    </tr>

    <%@ include file="/WEB-INF/jsp/nodecorate/uploadOtherDocuments.jsp" %>    
    
    <tr>
      <td colspan="2"><span class="required">**</span> <fmt:message key="error.submit.changeMemoOrProtocolHighlight"/></td>
    </tr>
  </table>
</div>
