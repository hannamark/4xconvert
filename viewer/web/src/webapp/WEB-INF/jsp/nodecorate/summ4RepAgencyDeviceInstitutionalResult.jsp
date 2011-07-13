<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshAgencyDeviceInstitutionalResultsSumm4Rep.action" var="agencyDeviceInstitutionalSortUrl"/>
<s:set name="institutionalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INSTITUTIONAL" />    
<h3>Institutional</h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListInstitutional" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.agentDeviceMap[institutionalStr]}" requestURI="${agencyDeviceInstitutionalSortUrl}" uid="agentDeviceInstRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>             