<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="orgs != null">
    <s:set name="orgs" value="orgs" scope="request"/>
    <display:table class="data" summary="This table contains Organization search results. Please use column headers to sort results"  
                   sort="list" pagesize="10" uid="row" name="orgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list" value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Organization Name" property="name"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="false" title="Family Name" sortable="false">
            <c:forEach items="${row.families}" var="family">
                <c:out value="${family.value}"/><br/>
            </c:forEach>
        </display:column>
        <display:column escapeXml="true" title="City" property="city"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="State" property="state"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Country" property="country"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Zip" property="zip"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column title="Action" class="action" sortable="false">
            <a href="javascript:void(0)" class="btn" onclick="submitform('${row.id}','${func:escapeJavaScript(row.name)}')">
                <span class="btn_img"><span class="confirm">Select</span></span>
            </a>
        </display:column>
    </display:table>
</s:if>
