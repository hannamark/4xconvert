<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table width="100%">
    <tr>               
        <td colspan="2">
            <c:url value="/ctro/ajax/refreshAncCorResultsSumm4Rep.action" var="ancCorSortUrl"/>
            <ajax:displayTag id="summ4SearchResultsancillaryCorrelativeList" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.ancillaryCorrelativeList}" requestURI="${ancCorSortUrl}" uid="ancCorRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>