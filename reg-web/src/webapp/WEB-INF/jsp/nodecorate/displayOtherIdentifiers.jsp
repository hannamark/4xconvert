<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:choose>
<c:when test="${fn:length(sessionScope.secondaryIdentifiersList) >0}">          
<display:table class="data" decorator="" sort="list" size="false" id="row"
     name="${sessionScope.secondaryIdentifiersList}" requestURI="" export="false">    
     <display:column titleKey="submit.trial.otherIdentifier" property="extension"   sortable="true" headerClass="sortable"/>
      <display:column title="Action" class="action" sortable="false">
        <input type="button" value="Delete" onclick="deleteOtherIdentifierRow('${row_rowNum}')"/>
      </display:column>
</display:table>
</c:when>
<c:when test="${trialDTO.secondaryIdentifierList != null && fn:length(trialDTO.secondaryIdentifierList) >0}">
   <display:table class="data" decorator="" sort="list" size="false" id="row"
     name="${trialDTO.secondaryIdentifierList}" requestURI="" export="false">    
     <display:column titleKey="submit.trial.otherIdentifier" property="extension"   sortable="true" headerClass="sortable"/>
   </display:table>
</c:when>
</c:choose>
