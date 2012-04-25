<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<accrual:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="data" sort="list" uid="row" name="disWebList" export="false">
    <display:column escapeXml="true" title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column title="ICD9Code" property="icd9Code"  headerClass="sortable"/>
    <display:column title="SDCCode" property="sdcCode"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Menu Display Name" property="displayName"  headerClass="sortable"/>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
        <c:choose>
        <c:when test="${(row.displayName!=null)&&(row.displayName!='')}">
            <a href="#" class="btn" onclick="submitform('${row.diseaseIdentifier}', '${row.type}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
        </c:when>
        <c:otherwise>
                    not suitable for reporting
        </c:otherwise>
        </c:choose>
    </display:column>
</display:table>
</c:if>