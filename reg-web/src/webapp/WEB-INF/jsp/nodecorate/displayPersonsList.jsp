<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:if test="${persons != null}">
<display:table class="data" sort="list" pagesize="10" uid="row"  name="persons" export="false" 
															requestURI="popupdisplayPersonsListDisplayTag.action">
	<display:column title="PO-ID" property="id"  headerClass="sortable"/>
	<display:column title="First Name" property="firstName"  headerClass="sortable"/> 
	<display:column title="Middle Name" property="middleName"  headerClass="sortable"/>
	<display:column title="Last Name" property="lastName"  headerClass="sortable"/>
	<display:column title="Email" property="email"  headerClass="sortable"/>
	<display:column title="Action" class="action" sortable="false">	
	<s:a href="#" cssClass="btn" onclick="submitform('${row.id}')">
							<span class="btn_img"><span class="add">Select</span></span></s:a>	
	</display:column>
</display:table>
</c:if>