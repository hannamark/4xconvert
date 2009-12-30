<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="tumorMarkerList" value="tumorMarkerList" scope="request"/>
<display:table class="data" name="tumorMarkerList" sort="list" uid="row" export="false">
    <display:column title="Name" property="tumorMarker.code" headerClass="sortable"/>
    <display:column title="Select" headerClass="centered" class="action" sortable="false">
        <a href="#" class="btn" onclick="populateTumorMarkerField('${row.id.extension}', '${row.tumorMarker.code}')">
            <span class="btn_img"><span class="add">Select</span></span>
        </a>
    </display:column>
</display:table>