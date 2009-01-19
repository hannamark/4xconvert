<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  name="personWebDTOList" export="false" >
	<display:column title="Last Name" property="lastName"  headerClass="sortable"/> 
	<display:column title="First Name" property="firstName"  headerClass="sortable"/>
	<display:column title="Role" property="roleName.code"  headerClass="sortable"/>
	<s:if test="%{newParticipation}">
		<display:column title="Set as Site Primary Contact" class="action" sortable="false">	
			<s:a href="#" onclick="setAsPrimaryContact('${row.id}','del');"> <img src="<%=request.getContextPath()%>/images/ico_select_person.gif" alt="Set as Primary" width="16" height="16"/></s:a>
		</display:column>	
	</s:if>
	<s:else>
		<display:column title="Set as Site Primary Contact" class="action" sortable="false">	
			<s:a href="#" onclick="loadContactPersDivEditMode('${row.id}');   "> <img src="<%=request.getContextPath()%>/images/ico_select_person.gif" alt="Set as Primary" width="16" height="16"/></s:a>
		</display:column>	
	</s:else>
	<display:column title="Delete" class="action" sortable="false">	
		<s:a href="#" onclick="loadPersDiv('${row.id}','del'); "> <img src="<%=request.getContextPath()%>/images/ico_cancel.gif" alt="Delete" width="16" height="16"/></s:a>
	</display:column>
</display:table>
