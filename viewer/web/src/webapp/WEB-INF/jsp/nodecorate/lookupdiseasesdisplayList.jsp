<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/common/includejs.jsp" %>
<pa:failureMessage/>
<s:if test="disWebList != null">
<s:set name="disWebList" value="disWebList" scope="request"/>
<display:table class="data" sort="list" uid="row"
    name="disWebList" export="false">
    <display:column escapeXml="true" title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Code" property="code"  headerClass="sortable"/>
    <display:column escapeXml="true" title="NCI Thesaurus Concept ID" property="conceptId"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Menu Display Name" property="menuDisplayName"  headerClass="sortable"/>
    <display:column escapeXml="true" title="Parent Name" property="parentPreferredName"  headerClass="sortable"/>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
        <c:choose>
        <c:when test="${(row.menuDisplayName!=null)&&(row.menuDisplayName!='')}">
            <a href="#" class="btn" onclick="submitform('${row.diseaseIdentifier}','${row.preferredName}' )">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
        </c:when>
        <c:otherwise>
                    not suitable for reporting
        </c:otherwise>
        </c:choose>
    </display:column>
</display:table>
</s:if>