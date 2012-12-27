<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/selector/person/results/searchdt.action" var="sortUrl" />
<s:set name="ctepRoot" value="@gov.nih.nci.po.service.external.CtepOrganizationImporter@CTEP_ROOT"/>
<ajax:displayTag id="duplicatePersonSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <display:column titleKey="person.id" sortable="true" sortProperty="PERSON_ID" >
            <c:url var="viewDetailsUrl" value="/protected/ajax/selector/person/detail.action">
                <c:param name="person.id" value="${row.id}"/>
            </c:url>
            <a href="javascript://nop/" onclick="$('findDuplicates').hide(); loadDiv('${viewDetailsUrl}','duplicateSearchResultDetails', true);">
                ${row.id}
            </a>
        </display:column>
        <display:column title="CTEP ID" sortable="true" property="ctepID" sortProperty="CTEP_ID"/> 
		<display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" maxLength="30"/>
		<display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" maxLength="30"/>
        <display:column titleKey="person.email" sortable="true" property="emailAddresses" sortProperty="EMAIL"/>
        <display:column title="Organization Affiliation" sortable="false" class="orgNameColumn" media="html">
            <c:forEach items="${row.affiliation}" var="e">
                ${e.group} &mdash; <c:out value="${e.orgName}"/>
                <c:if test="${e.pending}">
                    <span class="pending">(P)</span>
                </c:if>
                <br/>
            </c:forEach>        
        </display:column>
        <display:column titleKey="person.postalAddress.cityOrMunicipality" property="city" sortable="true" sortProperty="CITY" maxLength="20"/>
		<display:column titleKey="person.postalAddress.stateOrProvince" property="state" sortable="true" sortProperty="STATE" maxLength="20"/>
        <display:column titleKey="person.statusCode" sortable="true" sortProperty="STATUS" property="statusCode" />        
        <display:column titleKey="th.action" class="action">
            <po:buttonRow>
                <c:set var="personFullName">${func:escapeJavaScript(row.lastName)}, ${func:escapeJavaScript(row.firstName)} ${func:escapeJavaScript(row.middleName)}</c:set>
                <po:button id="mark_as_dup_${row.id}"href="javascript://nop/" onclick="selectAndClose(new IdValue('${row.id}', '${personFullName}'));" style="add" text="Select" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
