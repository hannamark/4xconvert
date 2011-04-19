<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="viewer" %>
<table width="100%">
    <tr>               
    <td colspan="2">
<!-- main content begins-->
    <c:if test = "${not empty sessionScope.epidemiologicOutcomeList}">
        <c:url value="/ctro/ajax/refreshEpidemResultsSumm4Rep.action" var="epidemSortUrl"/>
        <ajax:displayTag id="summ4SearchResultsepidemiologicOutcomeList" tableClass="data">
        <display:table class="data" pagesize="20" uid="epidemRow" name="${sessionScope.epidemiologicOutcomeList}" 
            requestURI="${epidemSortUrl}" export="true">
            <viewer:summ4RepResultTableColumns table_uid="${epidemRow}"/>
        </display:table>
        </ajax:displayTag>
    </c:if>
    </td>
    </tr>
</table>