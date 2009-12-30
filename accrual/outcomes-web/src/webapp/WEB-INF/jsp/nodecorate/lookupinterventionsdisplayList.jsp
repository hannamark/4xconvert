<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="interWebList != null">
<display:table class="data" sort="list" uid="row" name="interWebList" export="false">
    <display:column title="Name" property="name.value"  headerClass="sortable"/>
    <display:column title="Other Names" property="otherNames"  headerClass="sortable"/> 
    <display:column title="Type Code" property="typeCode.code"  headerClass="sortable"/> 
    <display:column title="CTGOV<br>Type Code" property="ctGovTypeCode.code"  headerClass="sortable"/> 
    <display:column title="Description" property="description.value"  headerClass="sortable"/> 
    <display:column title="Action" headerClass="centered" class="action" sortable="false">
        <a href="#" class="btn" onclick="populateFields('${row.id.extension}','${row.name.value}', '${row.type.value}')">
            <span class="btn_img"><span class="add">Select</span></span>
        </a>  
    </display:column>
</display:table>
</s:if>