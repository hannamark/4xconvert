<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshAgencyDeviceEPPResultsSumm4Rep.action" var="agencyDeviceEPPSortUrl"/>
<s:set name="eppStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@EXTERNALLY_PEER_REVIEWED" />
<h3><fmt:message key="summary4Report.resultHead.epr"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListExternallyPeerReviewed" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.agentDeviceMap[eppStr]}" requestURI="${agencyDeviceEPPSortUrl}" uid="agentDeviceEPRRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>         
