<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:url value="/protected/ajax/selector/person/results/search.action" var="sortUrl" />
<ajax:displayTag id="duplicatePersonSearchResults" ajaxFlag="true" tableClass="data">
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
        <display:column titleKey="person.firstName" property="firstName" sortable="true" sortProperty="PERSON_FIRSTNAME" maxLength="30"/>
        <display:column titleKey="person.lastName" property="lastName" sortable="true" sortProperty="PERSON_LASTNAME" maxLength="30"/>
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
            <po:buttonRow>
                <po:button id="mark_as_dup_${row.id}"href="javascript://nop/" onclick="selectAndClose(new IdValue('${row.id}', '${row.lastName}, ${row.firstName} ${row.middleName}'));" style="reject" text="Select" />
            </po:buttonRow>
        </display:column>
    </display:table>
</ajax:displayTag>
  