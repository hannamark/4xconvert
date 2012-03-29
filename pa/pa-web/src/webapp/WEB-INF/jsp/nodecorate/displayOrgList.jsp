<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<pa:failureMessage />
<s:if test="orgs != null">

    <s:set name="orgs" value="orgs" scope="request" />
    <display:table class="data" sort="list" pagesize="10" uid="row" name="orgs" export="false"
        requestURI="popupOrgdisplayOrgListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list"
            value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column escapeXml="true" title="PO-ID" property="id" headerClass="sortable" />
        <display:column title="Organization Name" headerClass="sortable">
            <c:out value="${row.name}"/>
        </display:column>
        <display:column escapeXml="false" title="Family Name" sortable="false">
            <c:forEach items="${row.families}" var="family">
                <c:out value="${family.value}" />
                <br />
            </c:forEach>
        </display:column>
        <display:column escapeXml="true" title="City" property="city" headerClass="sortable" />
        <display:column escapeXml="true" title="State" property="state" headerClass="sortable" />
        <display:column escapeXml="true" title="Country" property="country" headerClass="sortable" />
        <display:column escapeXml="true" title="Zip" property="zip" headerClass="sortable" />
        <display:column title="Action" class="action" sortable="false">
            <a href="#" class="btn" onclick="submitform('${row.id}')"> <span class="btn_img"><span
                class="add">Select</span></span></a>
        </display:column>
    </display:table>

</s:if>