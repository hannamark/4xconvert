<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="persons != null">
    <s:set name="persons" value="persons" scope="request"/>
    <display:table class="data" summary="This table contains Person search results. Please use column headers to sort results"
                   sort="list" pagesize="10" uid="row"  name="persons" export="false" requestURI="popupdisplayPersonsListDisplayTag.action">
        <display:setProperty name="basic.msg.empty_list" value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
        <display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="First Name" property="firstName"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Middle Name" property="middleName"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Last Name" property="lastName"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column escapeXml="true" title="Address" property="address"  sortable="true"  headerClass="sortable"  headerScope="col"/>
        <display:column title="Action" class="action" sortable="false"  headerScope="col">
            <a href="#" class="btn" onclick="submitform('${row.id}','${func:escapeJavaScript(row.lastName)}' + ',' + '${func:escapeJavaScript(row.firstName)}')">
                <span class="btn_img"><span class="confirm">Select</span></span>
            </a>
        </display:column>
    </display:table>
</s:if>
