<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="rolecode" value="@gov.nih.nci.pa.enums.StudyParticipationContactRoleCode@getRoleCodes()" />
<c:if test="${persons != null}">
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  
	name="persons" export="false" requestURI="/pa/protected/popupdisplayPersonsListDisplayTag.action">
	<display:column title="PO-ID" property="id"  headerClass="sortable"/>
	<display:column title="First Name" property="firstName"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="middleName"  headerClass="sortable"/>
	<display:column title="Last Name" property="lastName"  headerClass="sortable"/>	
	<display:column title="Emails" property="email"/>
	<display:column title="Role Code">
			 <s:select id="${row.id}"  list="#rolecode"/>
  	</display:column>
	<display:column title="Action" class="action" sortable="false">
	<s:a href="#" cssClass="btn" onclick="callCreatePerson('${row.id}',document.getElementById('${row.id}').value)">
							<span class="btn_img"><span class="add">Select</span></span></s:a>
	</display:column>
</display:table>
</c:if>



