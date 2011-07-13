<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshOtherIntNationalResultsSumm4Rep.action" var="otherIntNationalSortUrl"/>
<s:set name="nationalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@NATIONAL" />        
<h3>National</h3>               
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsotherInterventionListNational" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.otherInterventionMap[nationalStr]}" requestURI="${otherIntNationalSortUrl}" uid="otherIntNatRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>
        