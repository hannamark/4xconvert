<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${disWebList != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" 
    name="disWebList" export="false">
    <display:column title="Name" property="preferredName"  headerClass="sortable"/>
    <display:column title="Code" property="code"  headerClass="sortable"/> 
    <display:column title="NCI Thesaurus Concept ID" property="conceptId"  headerClass="sortable"/> 
    <display:column title="Menu Display Name" property="menuDisplayName"  headerClass="sortable"/> 
    <display:column title="Parent Name" property="parentPreferredName"  headerClass="sortable"/> 
    <display:column title="Select" class="action" sortable="false">
        <s:if test="${(row.menuDisplayName!=null)&&(row.menuDisplayName!='')}">
            <s:a href="#" cssClass="btn" onclick="submitform('${row.identifier}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </s:a>
        </s:if><s:else>
            not suitable for reporting
        </s:else>  
    </display:column>
</display:table>
</c:if>