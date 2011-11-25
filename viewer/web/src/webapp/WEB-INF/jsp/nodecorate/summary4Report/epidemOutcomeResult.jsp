<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table width="100%">
    <tr>               
        <td colspan="2">
            <c:url value="/ctro/ajax/refreshSummary4ReportepidemOutcomeResult.action" var="epidemSortUrl"/>
            <ajax:displayTag id="summ4SearchResultsepidemiologicOutcomeList" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.epidemiologicOutcomeList}" requestURI="${epidemSortUrl}" uid="epidemRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>