<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ajax:displayTag id="roleSearchResults" tableClass="data">
    <display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="results" requestURI="${sortUrl}" >
        <po:displayTagProperties/>
        <display:setProperty name="pagination.sort.param" value="results.sortCriterion" />
        <display:setProperty name="pagination.sortdirection.param" value="results.sortDirection" />
        <display:setProperty name="pagination.pagenumber.param" value="results.pageNumber" />
        
        <!-- Fill in as part of PO-3144 -->
        
    </display:table>
</ajax:displayTag>


