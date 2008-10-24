<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${persons != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  name="persons" export="false" >
	<display:column title="Last Name" property="name.part[0].value"  headerClass="sortable"/> 
	<display:column title="First Name" property="name.part[1].value"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="name.part[2].value"  headerClass="sortable"/> 
	 	
		<display:column title="Action" class="action" sortable="false">	
	
	
	<s:a href="#" cssClass="btn" onclick="callCreateContactPerson('${row.identifier.extension}')">
							<span class="btn_img"><span class="add">Select</span></span></s:a>	
	</display:column>
</display:table>
</c:if>