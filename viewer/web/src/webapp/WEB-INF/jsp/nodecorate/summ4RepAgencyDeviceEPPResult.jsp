<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<c:url value="/ctro/ajax/refreshAgencyDeviceEPPResultsSumm4Rep.action" var="agencyDeviceEPPSortUrl"/>
<s:set name="eppStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@EXTERNALLY_PEER_REVIEWED" />
<h3>Externally Peer Reviewed</h3>
<table width="100%">
    <tr>               
    <td colspan="2">
    <c:if test = "${not empty sessionScope.agentDeviceMap[eppStr]}">                     
        <ajax:displayTag id="summ4SearchResultsagentDeviceListExternallyPeerReviewed" tableClass="data">
        <display:table class="data" pagesize="20" uid="agentDeviceEPRRow" name="${sessionScope.agentDeviceMap[eppStr]}" 
            requestURI="${agencyDeviceEPPSortUrl}" export="true">                         
            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceEPRRow}"/>
        </display:table>
        </ajax:displayTag>
    </c:if>
    </td>
    </tr>
</table>         
