<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="disWebList" value="disWebList" scope="request"/>
<display:table class="data" sort="list" uid="row" 
    name="disWebList" export="false">
    <display:column title="Name"  headerClass="sortable"><s:label value="%{#attr.row.type}"
         cssStyle="font-weight:normal"/></display:column>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <s:a href="#" cssClass="btn" onclick="submitform('%{#attr.row.id.extension}', '%{#attr.row.type}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </s:a>
    </display:column>
</display:table>
