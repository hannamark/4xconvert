<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>  
  <c:set var="hasOtherDocs" scope="request" value="${not empty sessionScope.otherDoc}"/>
  <c:forEach items="${sessionScope.otherDoc}" var="doc" varStatus="varStatus">
	  <div class="form-group">
	      <label for="deleteOtherDoc_${varStatus.index}" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherDocument"/></label>
	    <div class="col-xs-4">
	      <c:out value="${doc.fileName}"/>
	      <input id="deleteOtherDoc_${varStatus.index}" type="button" value="Remove" onclick="deleteDocument('<c:out value="${doc.fileName}"/>')"/>
	    </div>
	  </div> 
  </c:forEach>
  
  <c:set var="addMoreRendered" value="${false}" scope="page"/>
  <c:forEach begin="0" end="50" varStatus="varStatus">
	  <c:set var="fieldErrorKey" scope="page" value="trialDTO.otherDocumentFileName[${varStatus.index}]"/>
	  <c:set var="hasFieldError" scope="page" value="${not empty request.action.fieldErrors[fieldErrorKey]}"/>
	  <c:set var="hideUploadRow" value="${(!hasFieldError && (hasOtherDocs || varStatus.index>0))}"/>
	  
	  <div class="form-group" style="${hideUploadRow?'display:none':''}" id="otherUploadRow_${varStatus.index}">
	    <label for="submitTrial_otherDocument_${varStatus.index}" class="col-xs-4 control-label"><fmt:message key="submit.trial.otherDocument"/></label>
	    <div class="col-xs-4">
	      <s:file id="submitTrial_otherDocument" name="submitTrial_otherDocument" />
	      <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Click Browse to locate and upload any other trial-related document(s)."  data-placement="top" data-trigger="hover"></i>
	        <span class="alert-danger">
	          <s:fielderror>
	            <s:param>trialDTO.otherDocumentFileName[${varStatus.index}]</s:param>
	          </s:fielderror>
	        </span>
	    </div>
	  </div> 
	  
	  <div class="form-group" style="${(hideUploadRow && !addMoreRendered)?'':'display:none'}" id="addMoreRow_${varStatus.index}">
	    <label class="col-xs-4 control-label">&nbsp;</label>
	    <div class="col-xs-4">
	      <a href="javascript:void(0);" 
	           onclick="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
	           onkeypress="$('addMoreRow_${varStatus.index}').hide();$('otherUploadRow_${varStatus.index}').show();$('addMoreRow_${varStatus.index+1}').show();"
	           >Add more...</a>
	    </div>
	  </div>  
	     
	  <c:set var="addMoreRendered" value="${addMoreRendered || (hideUploadRow && !addMoreRendered)}" scope="page"/>
  
  </c:forEach>
