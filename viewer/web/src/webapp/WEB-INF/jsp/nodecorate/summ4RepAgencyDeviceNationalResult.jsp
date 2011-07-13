<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshAgencyDeviceNationalResultsSumm4Rep.action" var="agencyDeviceNationalSortUrl"/>
<s:set name="nationalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@NATIONAL" />    
<h3>National</h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListNational" tableClass="data">
                <viewer:summ4RepResultTable name="${sessionScope.agentDeviceMap[nationalStr]}" requestURI="${agencyDeviceNationalSortUrl}" uid="agentDeviceNatRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>        
         
