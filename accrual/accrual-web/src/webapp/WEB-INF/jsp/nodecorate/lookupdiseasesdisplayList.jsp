<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<accrual:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="table table-striped sortable" sort="list" uid="row" name="disWebList" export="false">
    <display:column escapeXml="true" title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column title="Code" property="diseaseCode"  headerClass="sortable"/>
    <display:column title="System" property="codeSystem"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Menu Display Name" property="displayName"  headerClass="sortable"/>
    <c:if test="${page == 'searchLookup'}">
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <button type="button" class="btn btn-icon btn-default" onclick="submitform('${row.diseaseIdentifier}', '${row.codeSystem}')">
                <i class="fa-hand-o-up"></i>Select</button>
    </display:column>
    </c:if>
</display:table>
</c:if>