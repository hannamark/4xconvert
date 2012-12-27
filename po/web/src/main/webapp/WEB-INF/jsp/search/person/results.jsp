<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ page import="gov.nih.nci.po.service.external.CtepPersonImporter" %>
<c:url value="/protected/ajax/search/person/results/searchdt.action" var="sortUrl"/>
<ajax:displayTag id="personSearchResults" tableClass="data">
    <%@ include file="resultsTable.jsp"%>
</ajax:displayTag>