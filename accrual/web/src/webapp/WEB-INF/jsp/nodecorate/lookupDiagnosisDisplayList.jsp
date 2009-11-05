<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="disWebList" value="disWebList" scope="request"/>
<display:table class="data" sort="list" uid="row" 
    name="disWebList" export="false">
    <display:column title="Name"  headerClass="sortable"><s:label value="%{#attr.row.name}"
         cssStyle="font-weight:normal"/></display:column>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <a href="#" class="btn" onclick="submitform('${row.identifier.extension}', '${row.name.value}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
    </display:column>
</display:table>
