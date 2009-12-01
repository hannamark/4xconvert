<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/selector/person/results/search.action" var="sortUrl" />
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
        <display:column title="CTEP ID" sortable="false">
            <ul>
                <c:forEach items="${row.identifiedPersons}" var="e">
                    <c:if test="${e.assignedIdentifier.root == ctepRoot}">
                        <li>${e.assignedIdentifier.extension}</li>
                    </c:if>
                </c:forEach>
            </ul>
        </display:column>
		<display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" maxLength="30"/>
		<display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" maxLength="30"/>
        <display:column titleKey="person.email" sortable="false">
            <ul>
                <c:forEach items="${row.email}" var="e">
                    <li>${e.value}</li>
                </c:forEach>
            </ul>
        </display:column>
        <display:column title="Organization Affiliation" sortable="false" class="orgNameColumn" >
            <ul>
                <c:forEach items="${row.organizationalContacts}" var="e">
                    <li>${e.scoper.name}</li>
                </c:forEach>
                <c:forEach items="${row.clinicalResearchStaff}" var="e">
                    <li>${e.scoper.name}</li>
                </c:forEach>
                <c:forEach items="${row.healthCareProviders}" var="e">
                    <li>${e.scoper.name}</li>
                </c:forEach>
                <c:forEach items="${row.identifiedPersons}" var="e">
                    <li>${e.scoper.name}</li>
                </c:forEach>
            </ul>
        </display:column>
        <display:column titleKey="person.postalAddress.cityOrMunicipality" property="postalAddress.cityOrMunicipality" sortable="false" maxLength="20"/>
		<display:column titleKey="person.postalAddress.stateOrProvince" property="postalAddress.stateOrProvince" sortable="false" maxLength="20"/>
        <display:column titleKey="person.statusCode" sortable="true" sortProperty="PERSON_STATUS" >
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
               ${row.statusCode} <br/>
                <div class="difference_found">Change Requests (${fn:length(row.changeRequests)})</div>
            </c:when>
            <c:otherwise>${row.statusCode}</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="th.action" class="action">
            <po:buttonRow>
                <c:set var="personFullName">${pofn:escapeJavaScript(row.lastName)}, ${pofn:escapeJavaScript(row.firstName)} ${pofn:escapeJavaScript(row.middleName)}</c:set>
                <po:button id="mark_as_dup_${row.id}"href="javascript://nop/" onclick="selectAndClose(new IdValue('${row.id}', '${personFullName}'));" style="reject" text="Select" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
