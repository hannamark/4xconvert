<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="orgs != null">

<s:set name="orgs" value="orgs" scope="request"/>
<display:table class="data" summary="This table contains Organization search results. Please use column headers to sort results"  sort="list" pagesize="10" uid="row" 
	name="orgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
	<display:setProperty name="basic.empty.showtable" value="true" />
	<display:setProperty name="paging.banner.no_items_found">
            <div class="pagingtop"><span class="pagebanner">No Organizations found. Please verify search criteria and/or broaden your search by removing one/more search criteria.</span>
    </display:setProperty>
    <display:setProperty name="paging.banner.onepage">
        <span class="pagelinks"></span></div>
    </display:setProperty>
	<display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Organization Name" property="name"  sortable="true"  headerClass="sortable"  headerScope="col"/>	
	<display:column title="City" property="city"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
	<display:column title="State" property="state"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Country" property="country"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
	<display:column title="Zip" property="zip"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
		<display:column title="Action" class="action" sortable="false">
		<a href="#" class="btn" onclick="submitform('${row.id}','${fn:replace(row.name,'\'','&apos;')}')">
		<span class="btn_img"><span class="confirm">Select</span></span></a>  
	</display:column>
</display:table>
</s:if>