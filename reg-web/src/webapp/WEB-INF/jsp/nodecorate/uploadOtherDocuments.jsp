<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>  
  <c:set var="hasOtherDocs" scope="request" value="${not empty sessionScope.otherDoc}"/>
  <c:forEach items="${sessionScope.otherDoc}" var="doc" varStatus="varStatus">
  <tr>
    <td scope="row" class="label">
      <reg-web:displayTooltip tooltip="tooltip.other">
        <fmt:message key="submit.trial.otherDocument"/>
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
        <label for="submitTrial_otherDocument_${varStatus.index}"><fmt:message key="submit.trial.otherDocument"/></label>
      </reg-web:displayTooltip>
    </td>
    <td class="value">
        <input id="submitTrial_otherDocument_${varStatus.index}" type="file" style="width:270px" value="" name="otherDocument">
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
