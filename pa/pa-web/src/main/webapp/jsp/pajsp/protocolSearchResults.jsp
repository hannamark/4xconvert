<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ajax:displayTag id="contentwide" ajaxFlag="true" tableClass="data">

    <display:table class="its" sort="list" excludedParams="sortingUrl listName"
                pagesize="10" uid="row" name="${param.listName}" requestURI="${sortUrl}">


        <display:column title="NCI Accession Number" property="nciIdentifier" sortable="true" headerClass="sortable" />
        <display:column title="Title" property="longTitleText" sortable="true" headerClass="sortable" />
        <display:column title="Study Phase Code" property="studyPhaseCode.name" sortable="true" headerClass="sortable" />
        <display:column title="Trial Status" property="studyStatusCode.name" sortable="true" headerClass="sortable" />
        <display:column title="Trial Status Date" property="studyStatusDate" sortable="true" headerClass="sortable" />
        <display:column title="Lead Organization" property="leadOrganizationName" sortable="true" headerClass="sortable" />
        <display:column title="Principal Investigator " property="principalInvestigatorFullName" sortable="true" headerClass="sortable" />       
    </display:table>
</ajax:displayTag>
