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
      <a href="javascript:void(0)" onclick="Help.popHelp('pdfconversion');">Tips for creating CTRP compatible PDF documents</a>
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
  <c:set var="hasOtherDocs" scope="request" value="${not empty sessionScope.otherDoc}"/>
  <c:forEach items="${sessionScope.otherDoc}" var="doc" varStatus="varStatus">
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.other">
        <label for="updateTrial_otherDocument"><fmt:message key="update.trial.otherDocument"/></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">   
        <c:out value="${doc.fileName}"/>
        <input type="button" value="Remove" onclick="deleteDocument('Other',${varStatus.index})"/>      
    </td>
  </tr>
  </c:forEach>
  
  <c:set var="addMoreRendered" value="${false}" scope="page"/>
  <c:forEach begin="0" end="50" varStatus="varStatus">
  <c:set var="fieldErrorKey" scope="page" value="trialDTO.otherDocumentFileName[${varStatus.index}]"/>
  <c:set var="hasFieldError" scope="page" value="${not empty request.action.fieldErrors[fieldErrorKey]}"/>
  <c:set var="hideUploadRow" value="${(!hasFieldError && (hasOtherDocs || varStatus.index>0))}"/>
  
  <tr style="${hideUploadRow?'display:none':''}" id="otherUploadRow_${varStatus.index}">
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.other">
        <label for="updateTrial_otherDocument_${varStatus.index}"><fmt:message key="update.trial.otherDocument"/></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
        <input id="updateTrial_otherDocument_${varStatus.index}" type="file" style="width:270px" value="" name="otherDocument">
        <span class="formErrorMsg">
          <s:fielderror>
            <s:param>trialDTO.otherDocumentFileName[${varStatus.index}]</s:param>
          </s:fielderror>
        </span>        
    </td>
  </tr>
  <tr style="${(hideUploadRow && !addMoreRendered)?'':'display:none'}" id="addMoreRow_${varStatus.index}">
    <td></td>
    <td>
        <a href="javascript:void(0);" 
           onclick="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
           onkeypress="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
           >Add more...</a>
    </td>
  </tr> 
     
  <c:set var="addMoreRendered" value="${addMoreRendered || (hideUploadRow && !addMoreRendered)}" scope="page"/>
  </c:forEach>  
</table>