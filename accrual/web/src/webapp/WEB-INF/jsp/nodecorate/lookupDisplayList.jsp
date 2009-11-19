<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<display:table class="data" sort="list" uid="row" name="lookUpList" export="false">
    <display:column title="Code" property="code.value"  headerClass="sortable"/>
	<display:column title="Display Name" property="displayName.value"  headerClass="sortable"/> 
	<display:column title="Description" property="description.value"  headerClass="sortable"/>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <a href="#" class="btn" onclick="populateFields('${row.code.value}', '${row.type.value}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
    </display:column>
</display:table>
