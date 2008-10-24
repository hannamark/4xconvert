<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${persons != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  
	name="persons" export="false" requestURI="/pa/protected/popupdisplayPersonsListDisplayTag.action">
	<display:column title="Last Name" property="name.part[0].value"  headerClass="sortable"/> 
	<display:column title="First Name" property="name.part[1].value"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="name.part[2].value"  headerClass="sortable"/>
	<display:column title="Role Code">
			<select id="${row.identifier.extension}">					
					<option value="Study Principal Investigator">Study Principal Investigator</option>
					<option value="Study Sub Investigator">Study Sub Investigator</option>				
			</select>
  	</display:column>
	<display:column title="Action" class="action" sortable="false">
	<s:a href="#" cssClass="btn" onclick="callCreatePerson('${row.identifier.extension}', 
							document.getElementById('${row.identifier.extension}').value)">
							<span class="btn_img"><span class="add">Select</span></span></s:a>	
					
	</display:column>
</display:table>
</c:if>