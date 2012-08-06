<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page import="gov.nih.nci.po.service.external.CtepPersonImporter" %>
<c:url value="/protected/ajax/search/person/results/searchdt.action" var="sortUrl"/>
<c:set var="ctepRoot" value="<%=CtepPersonImporter.CTEP_PERSON_ROOT%>"/>
<ajax:displayTag id="personSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}">
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <display:column titleKey="search.person.id" property="id" sortable="true" sortProperty="PERSON_ID"/>
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
            ${row.statusCode}
        </display:column>
        <display:column title="Change Request(s)" >
            <c:choose>
            <c:when test="${fn:length(row.changeRequests) > 0}">
               <div class="difference_found">${fn:length(row.changeRequests)}</div>
            </c:when>
            <c:otherwise>NONE</c:otherwise>
            </c:choose>
        </display:column>
        <display:column titleKey="th.action" class="action">
            <c:url var="curateUrl" value="/protected/person/curate/start.action">
                <c:param name="person.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="select_person" text="Curate" id="person_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>