<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<accrual:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="data" sort="list" uid="row" name="disWebList" export="false">
    <display:column escapeXml="true" title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column title="Code" property="diseaseCode"  headerClass="sortable"/>
    <display:column title="System" property="codeSystem"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Menu Display Name" property="displayName"  headerClass="sortable"/>
    <c:if test="${page == 'searchLookup'}">
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <a href="#" class="btn" onclick="submitform('${row.diseaseIdentifier}', '${row.codeSystem}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
    </display:column>
    </c:if>
</display:table>
</c:if>