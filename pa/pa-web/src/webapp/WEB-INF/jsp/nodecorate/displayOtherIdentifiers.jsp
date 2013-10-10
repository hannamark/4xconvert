<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:if test="${requestScope.duplicateIdentifier != null && requestScope.duplicateIdentifier != ''}">
		<div style="color: red">			
			<%= request.getAttribute("duplicateIdentifier") %>
			<% request.removeAttribute("duplicateIdentifier"); %>
		</div>
</c:if>
<c:choose>	
    <c:when test="${fn:length(sessionScope.otherIdentifiersList) > 0}">
		<display:table class="data" decorator="" sort="list" size="false"
			id="row" name="${sessionScope.otherIdentifiersList}" requestURI=""
			export="false">
			<display:column title="Other Identifier" sortable="true"
				headerClass="sortable">
				<div id="identifierDiv_${row_rowNum}">
					<c:out value="${row.extension}" />
				</div>
				<div id="identifierInputDiv_${row_rowNum}" style="display: none;">
					<label for="identifier_${row_rowNum}" style="display:none">Identifier</label>
					<input id="identifier_${row_rowNum}" type="text"
						value="<c:out value="${row.extension}"/>" />
				</div>
			</display:column>
			<display:column title="Identifier Type" sortable="true"
				headerClass="sortable">
				<div id="identifierTypeDiv_${row_rowNum}">
					<c:out value="${row.identifierName}" />
				</div>
				<div id="identifierTypeInputDiv_${row_rowNum}"
					style="display: none;">
					<label for="identifierType_${row_rowNum}" style="display:none">Identifier Type</label>
					<select id="identifierType_${row_rowNum}"
						name="otherIdentifierType" id="otherIdentifierType"
						style="margin-top: 0px;">
						<option value="0">Other</option>
						<option value="1">Obsolete NCT ID</option>
						<option value="2">Duplicate NCI ID</option>
					</select>
				</div>
			</display:column>	
			<display:column title="Action" class="action" sortable="false" style="width:110px">
				<div id="actionEdit_${row_rowNum}">
					<input type="button" value="Edit"
						onclick="editIdentifierRow('${row_rowNum}')" />&nbsp;
					<input type="button" value="Delete"
						onclick="if (confirm('Click OK to remove selected identifier from the study. Cancel to abort.')) {deleteOtherIdentifierRow('${row_rowNum}')}" />
				</div>
				<div id="actionSave_${row_rowNum}" style="display: none;">
					<input type="button" value="Done"
						onclick="saveIdentifierRow('${row_rowNum}')" />&nbsp;
					<input type="button" value="Delete"
						onclick="if (confirm('Click OK to remove selected identifier from the study. Cancel to abort.')) {deleteOtherIdentifierRow('${row_rowNum}')}" />
				</div>				
			</display:column>
		</display:table>
	</c:when>
    <c:when test="${gtdDTO.otherIdentifiers != null && fn:length(gdtDTO.otherIdentifiers) > 0}">
        <display:table class="data" decorator="" sort="list" size="false" id="row" name="${gtdDTO.otherIdentifiers}" requestURI="" export="false">
            <display:column escapeXml="true" titleKey="Other Identifier" property="extension" sortable="true" headerClass="sortable" />
            <display:column escapeXml="true" title="Identifier Type" property="identifierName" sortable="true" headerClass="sortable" />
        </display:table>
    </c:when>
</c:choose>
