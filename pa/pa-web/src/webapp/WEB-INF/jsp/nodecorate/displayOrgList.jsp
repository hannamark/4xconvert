<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${orgs != null}">

<display:table class="data" sort="list" pagesize="10" uid="row" 
	name="orgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
	<display:column title="PO-ID" property="id"  headerClass="sortable"/>
	<display:column title="Organization Name" property="name"  headerClass="sortable"/>
	<display:column title="City" property="city"  headerClass="sortable"/> 
	<display:column title="State" property="state"  headerClass="sortable"/>
	<display:column title="Country" property="country"  headerClass="sortable"/> 
	<display:column title="Zip" property="zip"  headerClass="sortable"/> 
		<display:column title="Action" class="action" sortable="false">
		<a href="#" class="btn" onclick="submitform('${row.id}')">
		<span class="btn_img"><span class="add">Select</span></span></a>  
	</display:column>
</display:table>

</c:if>