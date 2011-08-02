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
      <fmt:message key="submit.proprietary.trial.docInstructionalText"/>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <a href="#" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a>
    </td>
  </tr>
  <tr>
    <td scope="row" class="label">
      <label for="submitTrial_protocolDoc">
        <fmt:message key="submit.proprietary.trial.protocolDocument"/>
      </label>
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
    <label for="submitTrial_otherDocument"><fmt:message key="submit.trial.otherDocument"/></label>
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