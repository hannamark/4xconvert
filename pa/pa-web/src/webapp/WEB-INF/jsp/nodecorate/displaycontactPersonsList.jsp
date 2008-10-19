<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:if test="${persons != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  name="persons" export="false" >
	<display:column title="Last Name" property="name.part[0].value"  headerClass="sortable"/> 
	<display:column title="First Name" property="name.part[1].value"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="name.part[2].value"  headerClass="sortable"/> 
	 	
		<display:column title="Action" class="action" sortable="false">	
	<A href="javascript: callCreateContactPerson('${row.identifier.extension}')">Select</A>
	</display:column>
</display:table>
</c:if>