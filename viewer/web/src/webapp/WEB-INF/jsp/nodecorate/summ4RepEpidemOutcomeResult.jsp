<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table width="100%">
    <tr>               
        <td colspan="2">
            <c:url value="/ctro/ajax/refreshEpidemResultsSumm4Rep.action" var="epidemSortUrl"/>
            <ajax:displayTag id="summ4SearchResultsepidemiologicOutcomeList" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.epidemiologicOutcomeList}" requestURI="${epidemSortUrl}" uid="epidemRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>