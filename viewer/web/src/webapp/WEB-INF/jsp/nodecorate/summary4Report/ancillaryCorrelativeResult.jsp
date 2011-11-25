<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table width="100%">
    <tr>               
        <td colspan="2">
            <c:url value="/ctro/ajax/refreshSummary4ReportancillaryCorrelativeResult.action" var="ancCorSortUrl"/>
            <ajax:displayTag id="summ4SearchResultsancillaryCorrelativeList" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.ancillaryCorrelativeList}" requestURI="${ancCorSortUrl}" uid="ancCorRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>