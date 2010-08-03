<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(sessionScope.secondaryIdentifiersList) >0}">
<display:table class="data" decorator="" sort="list" size="false" id="row"
     name="${sessionScope.secondaryIdentifiersList}" requestURI="" export="false">
     <display:column escapeXml="true" titleKey="submit.trial.addedOtherIdentifiers" property="extension"   sortable="true" headerClass="sortable"/>
      <display:column title="Action" class="action" sortable="false">
        <input type="button" value="Delete" onclick="deleteOtherIdentifierRow('${row_rowNum}')"/>
      </display:column>
</display:table>
</c:when>
<c:when test="${trialDTO.secondaryIdentifierAddList != null && fn:length(trialDTO.secondaryIdentifierAddList) >0}">
   <display:table class="data" decorator="" sort="list" size="false" id="row"
     name="${trialDTO.secondaryIdentifierAddList}" requestURI="" export="false">
     <display:column escapeXml="true" titleKey="submit.trial.addedOtherIdentifiers" property="extension"   sortable="true" headerClass="sortable"/>
   </display:table>
</c:when>
</c:choose>
