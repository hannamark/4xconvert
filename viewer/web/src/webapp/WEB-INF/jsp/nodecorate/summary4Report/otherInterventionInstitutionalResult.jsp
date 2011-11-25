<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/ctro/ajax/refreshSummary4ReportotherInterventionInstitutionalResult.action" var="otherIntInstitutionalSortUrl"/>
<s:set name="institutionalStr" value="@gov.nih.nci.pa.viewer.util.ViewerConstants@INSTITUTIONAL" />
<h3><fmt:message key="summary4Report.resultHead.institutional"/></h3>
<table width="100%">
    <tr>               
        <td colspan="2">
            <ajax:displayTag id="summ4SearchResultsotherInterventionListInstitutional" tableClass="data">
                <viewer:summary4ReportResultTable name="${sessionScope.otherInterventionMap[institutionalStr]}" requestURI="${otherIntInstitutionalSortUrl}" uid="otherIntInstRow"/>
            </ajax:displayTag>
        </td>
    </tr>
</table>
      