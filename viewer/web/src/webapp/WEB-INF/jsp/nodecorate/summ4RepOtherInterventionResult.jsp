<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<c:url value="/ctro/ajax/refreshOtherIntResultsSumm4Rep.action" var="otherIntSortUrl"/>
<!-- main content begins-->
            <table width="100%">
            <tr>               
            <td colspan="2">
                <h3>National</h3>               
                    <table width="100%">
                    <tr>               
                    <td colspan="2">
                    <c:if test = "${not empty sessionScope.otherInterventionMap['NATIONAL']}">                     
                        <ajax:displayTag id="summ4SearchResultsotherInterventionListNational" tableClass="data">
                        <display:table class="data" pagesize="20" uid="otherIntNatRow" name="${sessionScope.otherInterventionMap['NATIONAL']}" 
                            requestURI="${otherIntSortUrl}" export="true">
                            <viewer:summ4RepResultTableColumns table_uid="${otherIntNatRow}"/>
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
                    <c:if test = "${not empty sessionScope.otherInterventionMap['EXTERNALLY_PEER_REVIEWED']}">                     
                        <ajax:displayTag id="summ4SearchResultsotherInterventionListExternallyPeerReviewed" tableClass="data">
                        <display:table class="data" pagesize="20" uid="otherIntEPRRow" name="${sessionScope.otherInterventionMap['EXTERNALLY_PEER_REVIEWED']}" 
                            requestURI="${otherIntSortUrl}" export="true">
                            <viewer:summ4RepResultTableColumns table_uid="${otherIntEPRRow}"/>
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
                    <c:if test = "${not empty sessionScope.otherInterventionMap['INSTITUTIONAL']}">                     
                        <ajax:displayTag id="summ4SearchResultsotherInterventionListInstitutional" tableClass="data">
                        <display:table class="data" pagesize="20" uid="otherIntInstRow" name="${sessionScope.otherInterventionMap['INSTITUTIONAL']}" 
                            requestURI="${otherIntSortUrl}" export="true">                         
                            <viewer:summ4RepResultTableColumns table_uid="${otherIntInstRow}"/>
                        </display:table>
                        </ajax:displayTag>
                    </c:if>
                    </td>
                    </tr>
                </table>
            </td>
            </tr>
            
            </table>
