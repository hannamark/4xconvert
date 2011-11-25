<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshSummary4ReportotherInterventionEPRResult.action" var="otherIntEPPSortUrl"/>
<s:set name="eppStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@EXTERNALLY_PEER_REVIEWED" />
<h3><fmt:message key="summary4Report.resultHead.epr"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsotherInterventionListExternallyPeerReviewed" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.otherInterventionMap[eppStr]}" requestURI="${otherIntEPPSortUrl}" uid="otherIntEPRRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>
        