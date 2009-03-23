<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:if test="${orgs != null}">

<display:table class="data" summary="This table contains Organization search results. Please use column headers to sort results"  sort="list" pagesize="10" uid="row" 
	name="orgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
	<display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Organization Name" property="name"  sortable="true"  headerClass="sortable"  headerScope="col"/>	
	<display:column title="City" property="city"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
	<display:column title="State" property="state"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Country" property="country"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
	<display:column title="Zip" property="zip"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
		<display:column title="Action" class="action" sortable="false">
		<s:a href="#" cssClass="btn" onclick="submitform('${row.id}','${row.name}')"><span class="btn_img"><span class="add">Select</span></span></s:a>  
	</display:column>
</display:table>
</c:if>