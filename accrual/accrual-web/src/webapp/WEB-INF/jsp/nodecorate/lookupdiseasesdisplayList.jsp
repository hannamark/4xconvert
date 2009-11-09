<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="data" decorator="gov.nih.nci.accrual.web.decorator.SubmissionDecorator" sort="list" uid="row" 
    name="disWebList" export="false">
    <display:column title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column title="Code" property="code"  headerClass="sortable"/> 
    <display:column title="NCI Thesaurus Concept ID" property="conceptId"  headerClass="sortable"/> 
    <display:column title="Menu Display Name" property="menuDisplayName"  headerClass="sortable"/> 
    <display:column title="Parent Name" property="parentPreferredName"  headerClass="sortable"/> 
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
        <c:choose>
        <c:when test="${(row.menuDisplayName!=null)&&(row.menuDisplayName!='')}">
            <a href="#" class="btn" onclick="submitform('${row.diseaseIdentifier}')">
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