<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${orgs != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  name="orgs" export="false" >
	<display:column title="Organization Name" property="name.part[0].value"  headerClass="sortable"/> 

	<display:column title="City" property="postalAddress.part[1].value"  headerClass="sortable"/> 
	<display:column title="Country" property="postalAddress.part[3].code"  headerClass="sortable"/> 
	<display:column title="Zip" property="postalAddress.part[2].value"  headerClass="sortable"/> 
		<display:column title="Action" class="action" sortable="false">
	<A href="javascript: submitform('${row.identifier.extension}')">Select</A>
	</display:column>
</display:table>
</c:if>