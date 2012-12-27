<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/ajax/curate/search/organization/listOrgs.action" var="sortUrl"/>
	<display:table class="data" sort="list" pagesize="${initParam['defaultPageSize']}" uid="row" name="orgs" requestURI="${sortUrl}" export="true">
	    <po:displayTagProperties/>
	    <display:setProperty name="export.xml" value="false" />
	    <display:setProperty name="export.excel.filename"
	        value="org_inbox.xls" />
	    <display:setProperty name="export.excel.include_header" value="true" />
	    <display:setProperty name="export.csv.filename"
	        value="org_inbox.csv" />
	    <display:setProperty name="export.csv.include_header" value="true" />
	    	    
		<display:setProperty name="pagination.sort.param" value="orgs.sortCriterion" />
		<display:setProperty name="pagination.sortdirection.param" value="orgs.sortDirection" />
		<display:setProperty name="pagination.pagenumber.param"	value="orgs.pageNumber" />

         <%@ include file="/WEB-INF/jsp/search/organization/resultsTableBaseCols.jsp"%>
		
        <display:column titleKey="th.action" class="action" media="html">
            <c:url var="curateUrl" value="/protected/organization/curate/start.action">
                <c:param name="organization.id" value="${row.id}"/>
            </c:url>
            <po:buttonRow>
                <po:button href="${curateUrl}" style="select_person" text="Curate" id="org_id_${row.id}"/>
            </po:buttonRow>
        </display:column>
	</display:table>