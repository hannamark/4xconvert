<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<pa:failureMessage />
<s:if test="markers != null">
    <s:set name="markers" value="markers" scope="request" />
    <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row" name="markers" export="false">
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.permissibleValue" property="vmName" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.meaning" property="vmMeaning" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.description" property="vmDescription" headerClass="sortable" />
        <display:column escapeXml="true" titleKey="plannedMarker.lookup.publicId" property="publicId" headerClass="sortable" />
        <display:column title="Select" headerClass="centered" class="action" sortable="false">
            <a href="#" class="btn" onclick="top.window.loadDiv('${row.id}')">
                <span class="btn_img"><span class="add">Select</span></span>
            </a>
        </display:column>
    </display:table>
</s:if>