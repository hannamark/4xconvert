<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:choose>
    <c:when test="${not empty trialDTO.nctIdentifier}">
        <c:out value="${trialDTO.nctIdentifier}"/>
        <input type="hidden" name="trialDTO.nctIdentifier" id="trialDTO.nctIdentifier" value='<c:out value="${trialDTO.nctIdentifier}"/>'/>
    </c:when>
    <c:otherwise>
      <div id="nctIdentifierdiv">
        <s:textfield id="nctId" name="nctId"  maxlength="200" size="100"  cssStyle="width:200px" />&nbsp; 
        <input type="button" value="Add NCT Identifier" onclick="addNCTIdentifier();" />
	    <span class="formErrorMsg">
	        <s:fielderror>
	              <s:param>trialDTO.nctIdentifier</s:param>
	        </s:fielderror>
	    </span>
      </div>
    </c:otherwise>
</c:choose>