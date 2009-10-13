<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<s:if test="persons != null">
<s:set name="persons" value="persons" scope="request"/>
<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row"  
			name="persons" export="false" requestURI="popupdisplaycontactPersonsListDisplayTag.action">
	<display:column title="PO-ID" property="id"  headerClass="sortable"/>
	<display:column title="First Nameddfsdfds" property="firstName"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="middleName"  headerClass="sortable"/>
	<display:column title="Last Name" property="lastName"  headerClass="sortable"/>
	<display:column title="E-mails" property="email" />
	<display:column title="Action" class="action" sortable="false">
	<a href="#" class="btn" onclick="callCreateContactPerson('${row.id}')">
		<span class="btn_img"><span class="add">Select</span></span></a>	
	</display:column>
</display:table>
</s:if> 