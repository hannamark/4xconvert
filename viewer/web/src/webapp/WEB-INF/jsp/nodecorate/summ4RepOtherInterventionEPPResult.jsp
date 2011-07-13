<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshOtherIntEPPResultsSumm4Rep.action" var="otherIntEPPSortUrl"/>
<s:set name="eppStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@EXTERNALLY_PEER_REVIEWED" />
<h3>Externally Peer Reviewed</h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsotherInterventionListExternallyPeerReviewed" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.otherInterventionMap[eppStr]}" requestURI="${otherIntEPPSortUrl}" uid="otherIntEPRRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>
        