<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshSummary4ReportagencyDeviceNationalResult.action" var="agencyDeviceNationalSortUrl"/>
<s:set name="nationalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@NATIONAL" />    
<h3><fmt:message key="summary4Report.resultHead.national"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsagentDeviceListNational" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.agentDeviceMap[nationalStr]}" requestURI="${agencyDeviceNationalSortUrl}" uid="agentDeviceNatRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>        
         
