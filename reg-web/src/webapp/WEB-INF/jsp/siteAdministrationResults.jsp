<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:set name="records" value="registryUsers" scope="request"/>
<h2 id="search_results">Search Results</h2>
<display:table class="data" summary="This table contains your search results."
            decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
              name="records" requestURI="siteAdministrationview.action" export="false">
    <display:column escapeXml="true" titleKey="siteadministration.results.firstname" property="firstName" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column escapeXml="true" titleKey="siteadministration.results.lastname" property="lastName" sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column escapeXml="true" titleKey="siteadministration.results.email" property="emailAddress" sortable="true" headerClass="sortable" headerScope="col"/>
    <display:column titleKey="siteadministration.results.usertype" sortable="true" headerClass="sortable">
        <c:set var="chkId" value="chk${row.id}" />
        <c:choose>
            <c:when test="${row.affiliatedOrgUserType.code == 'Admin'}">
               <label for=${chkId} class="hidden-label"><fmt:message key="siteadministration.results.usertype"/></label><input type="checkbox" name="${chkId}" value="true" id="${chkId}" checked="checked" onclick="updateUserOrgType('${row.id}')"/>
            </c:when>
            <c:otherwise>
                <label for=${chkId} class="hidden-label"><fmt:message key="siteadministration.results.usertype"/></label><input type="checkbox" name="${chkId}" value="true" id="${chkId}" onclick="updateUserOrgType('${row.id}')"/>
            </c:otherwise>
        </c:choose>

    </display:column>
</display:table>



