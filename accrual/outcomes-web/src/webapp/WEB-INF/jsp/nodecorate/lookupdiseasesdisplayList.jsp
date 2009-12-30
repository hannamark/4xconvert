<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="data" sort="list" uid="row" 
    name="disWebList" export="false">
    <display:column title="Name"  headerClass="sortable"><s:label value="%{#attr.row.preferredName}"
         cssStyle="font-weight:normal"/></display:column>
    <display:column title="Code" headerClass="sortable"><s:label value="%{#attr.row.code}"
         cssStyle="font-weight:normal"/></display:column> 
    <display:column title="NCI Thesaurus Concept ID" headerClass="sortable"><s:label value="%{#attr.row.conceptId}"
         cssStyle="font-weight:normal"/></display:column> 
    <display:column title="Menu Display Name" headerClass="sortable"><s:label value="%{#attr.row.menuDisplayName}"
         cssStyle="font-weight:normal"/></display:column> 
    <display:column title="Parent Name" headerClass="sortable"><s:label value="%{#attr.row.parentPreferredName}"
         cssStyle="font-weight:normal"/></display:column> 
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
        <c:choose>
        <c:when test="${(row.menuDisplayName!=null)&&(row.menuDisplayName!='')}">
            <a href="#" class="btn" onclick="submitform('${row.preferredName}')">
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