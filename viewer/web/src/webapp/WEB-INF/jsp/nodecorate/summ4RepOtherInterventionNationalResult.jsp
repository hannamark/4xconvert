<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>

<c:url value="/ctro/ajax/refreshOtherIntNationalResultsSumm4Rep.action" var="otherIntNationalSortUrl"/>
<s:set name="nationalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@NATIONAL" />        
<h3>National</h3>               
<table width="100%">
    <tr>               
    <td colspan="2">
        <ajax:displayTag id="summ4SearchResultsotherInterventionListNational" tableClass="data">
        <display:table class="data" pagesize="20" uid="otherIntNatRow" name="${sessionScope.otherInterventionMap[nationalStr]}" 
            requestURI="${otherIntNationalSortUrl}" export="true">
            <viewer:summ4RepResultTableColumns table_uid="${otherIntNatRow}"/>
        </display:table>
        </ajax:displayTag>
    </td>
    </tr>
</table>
        