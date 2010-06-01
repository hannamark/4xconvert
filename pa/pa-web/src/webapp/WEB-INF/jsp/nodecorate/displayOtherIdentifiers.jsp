<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:choose>
    <c:when test="${fn:length(sessionScope.otherIdentifiersList) > 0}">
        <display:table class="data" decorator="" sort="list" size="false" id="row" name="${sessionScope.otherIdentifiersList}" requestURI="" export="false">
            <display:column title="Other Identifier" property="extension" sortable="true" headerClass="sortable" />
            <display:column title="Action" class="action" sortable="false">
                <input type="button" value="Delete" onclick="deleteOtherIdentifierRow('${row_rowNum}')" />
            </display:column>
        </display:table>
    </c:when>
    <c:when test="${gtdDTO.otherIdentifiers != null && fn:length(gdtDTO.otherIdentifiers) > 0}">
        <display:table class="data" decorator="" sort="list" size="false" id="row" name="${gtdDTO.otherIdentifiers}" requestURI="" export="false">
            <display:column titleKey="Other Identifier" property="extension" sortable="true" headerClass="sortable" />
        </display:table>
    </c:when>
</c:choose>
