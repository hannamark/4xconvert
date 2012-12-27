<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/ajax/search/person/results/searchdt.action" var="sortUrl"/>
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}" export="true">
        <po:displayTagProperties/>
        <display:setProperty name="export.xml" value="false" />
        <display:setProperty name="export.excel.filename"
            value="search_results.xls" />
        <display:setProperty name="export.excel.include_header" value="true" />
        <display:setProperty name="export.csv.filename"
            value="search_results.csv" />
        <display:setProperty name="export.csv.include_header" value="true" />        
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />

        <%@ include file="resultsTableBaseCols.jsp"%>
        
        <display:column titleKey="th.action" class="action" media="html">
            <c:url var="curateUrl" value="/protected/person/curate/start.action">
                <c:param name="person.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="select_person" text="Curate" id="person_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
    </display:table>