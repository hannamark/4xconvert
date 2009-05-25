<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${interWebList != null}">

<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" 
    name="interWebList" export="false">
    <display:column title="Preferred Name" property="name"  headerClass="sortable"/>
    <display:column title="Other Names" property="otherNames"  headerClass="sortable"/> 
    <display:column title="Description" property="description"  headerClass="sortable"/> 
    <display:column title="Action" headerClass="centered" class="action" sortable="false">
        <a href="#" class="btn" onclick="submitform('${row.identifier}')">
            <span class="btn_img"><span class="add">Select</span></span>
        </a>  
    </display:column>
</display:table>

</c:if>