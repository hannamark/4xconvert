<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:set name="rolecode" value="@gov.nih.nci.pa.enums.StudySiteContactRoleCode@getRoleCodes()" />
<s:if test="persons != null">
<s:set name="persons" value="persons" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  
	name="persons" export="false" requestURI="/pa/protected/popupdisplayPersonsListDisplayTag.action">
	<display:column title="PO-ID" property="id"  headerClass="sortable"/>
	<display:column title="First Name" property="firstName"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="middleName"  headerClass="sortable"/>
	<display:column title="Last Name" property="lastName"  headerClass="sortable"/>
	<display:column title="Address" property="address"  headerClass="sortable"/>
	<display:column title="Emails" property="email"/>
	<display:column title="Role Code">
			 <s:select id="%{#attr.row.id}"  list="#rolecode"/>
  	</display:column>
	<display:column title="Action" class="action" sortable="false">
	<a href="#" class="btn" onclick="callCreatePerson('${row.id}',document.getElementById('${row.id}').value)">
							<span class="btn_img"><span class="add">Select</span></span></a>
	</display:column>
</display:table>
</s:if>



