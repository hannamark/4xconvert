<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshOtherIntInstitutionalResultsSumm4Rep.action" var="otherIntInstitutionalSortUrl"/>
<s:set name="institutionalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INSTITUTIONAL" />
<h3>Institutional</h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsotherInterventionListInstitutional" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.otherInterventionMap[institutionalStr]}" requestURI="${otherIntInstitutionalSortUrl}" uid="otherIntInstRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>
      