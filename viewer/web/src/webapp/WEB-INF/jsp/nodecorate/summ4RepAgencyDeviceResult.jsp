<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<c:url value="/ctro/ajax/refreshAgencyDeviceResultsSumm4Rep.action" var="agencyDeviceSortUrl"/>

<!-- main content begins-->
            <table width="100%">
            <tr>               
            <td colspan="2">
                <h3>National</h3>
                <table width="100%">
                    <tr>               
                    <td colspan="2">
                    <c:if test = "${not empty sessionScope.agentDeviceMap['NATIONAL']}">                     
                        <ajax:displayTag id="summ4SearchResultsagentDeviceListNational" tableClass="data">
                        <display:table class="data" pagesize="20" uid="agentDeviceNatRow" name="${sessionScope.agentDeviceMap['NATIONAL']}" 
                            requestURI="${agencyDeviceSortUrl}" export="true">                         
                            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceNatRow}"/>
                        </display:table>
                        </ajax:displayTag>
                    </c:if>
                    </td>
                    </tr>
                </table>        
            </td>
            </tr>
            <tr>               
            <td colspan="2">
                <h3>Externally Peer Reviewed</h3>
                <table width="100%">
                    <tr>               
                    <td colspan="2">
                    <c:if test = "${not empty sessionScope.agentDeviceMap['EXTERNALLY_PEER_REVIEWED']}">                     
                        <ajax:displayTag id="summ4SearchResultsExternallyPeerReviewed" tableClass="data">
                        <display:table class="data" pagesize="20" uid="agentDeviceEPRRow" name="${sessionScope.agentDeviceMap['EXTERNALLY_PEER_REVIEWED']}" 
                            requestURI="${agencyDeviceSortUrl}" export="true">                         
                            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceEPRRow}"/>
                        </display:table>
                        </ajax:displayTag>
                    </c:if>
                    </td>
                    </tr>
                </table>         
            </td>
            </tr>
            <tr>               
            <td colspan="2">
                <h3>Institutional</h3>
                <table width="100%">
                    <tr>               
                    <td colspan="2">
                    <c:if test = "${not empty sessionScope.agentDeviceMap['INSTITUTIONAL']}">                     
                        <ajax:displayTag id="summ4SearchResultsInstitutional" tableClass="data">
                        <display:table class="data" pagesize="20" uid="agentDeviceInstRow" name="${sessionScope.agentDeviceMap['INSTITUTIONAL']}" 
                            requestURI="${agencyDeviceSortUrl}" export="true">                         
                            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceInstRow}"/>
                        </display:table>
                        </ajax:displayTag>
                    </c:if>
                    </td>
                    </tr>
                </table>             
            </td>
            </tr>
            <tr>               
            <td colspan="2">
                <h3>Industrial</h3>
               <table width="100%">
                    <tr>               
                    <td colspan="2">
                    <c:if test = "${not empty sessionScope.agentDeviceMap['INDUSTRIAL']}">                     
                        <ajax:displayTag id="summ4SearchResultsIndustrial" tableClass="data">
                        <display:table class="data" pagesize="20" uid="agentDeviceIndustRow" name="${sessionScope.agentDeviceMap['INDUSTRIAL']}" 
                            requestURI="${agencyDeviceSortUrl}" export="true">                         
                            <viewer:summ4RepResultTableColumns table_uid="${agentDeviceIndustRow}"/>
                        </display:table>
                        </ajax:displayTag>
                    </c:if>
                    </td>
                    </tr>
                </table> 
            </td>
            </tr>
            </table>
       
