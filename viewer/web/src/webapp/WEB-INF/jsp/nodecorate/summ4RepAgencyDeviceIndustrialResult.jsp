<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<s:set name="industrialStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INDUSTRIAL" />
<c:url value="/ctro/ajax/refreshAgencyDeviceIndustrialResultsSumm4Rep.action" var="agencyDeviceIndustrialSortUrl"/>
<h3>Industrial</h3>
<table width="100%">
    <tr>               
    <td colspan="2">
        <ajax:displayTag id="summ4SearchResultsagentDeviceListIndustrial" tableClass="data">
        <display:table class="data" pagesize="20" uid="agentDeviceIndustRow" name="${sessionScope.agentDeviceMap[industrialStr]}" 
            requestURI="${agencyDeviceIndustrialSortUrl}" export="true">                         
            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceIndustRow}"/>
        </display:table>
        </ajax:displayTag>
    </td>
    </tr>
</table> 
         
