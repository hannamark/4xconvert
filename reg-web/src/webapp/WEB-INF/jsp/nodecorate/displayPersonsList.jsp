<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<c:if test="${persons != null}">
<display:table class="data" summary="This table contains Person search results. Please use column headers to sort results"  sort="list" pagesize="10" uid="row"  name="persons" export="false" 
															requestURI="popupdisplayPersonsListDisplayTag.action">
	<display:column title="PO-ID" property="id"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="First Name" property="firstName"  sortable="true"  headerClass="sortable"  headerScope="col"/> 
	<display:column title="Middle Name" property="middleName"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Last Name" property="lastName"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Address" property="address"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Email" property="email"  sortable="true"  headerClass="sortable"  headerScope="col"/>
	<display:column title="Action" class="action" sortable="false"  headerScope="col">	
	<a href="#" class="btn" onclick="submitform('${row.id}','${row.lastName}'+','+'${row.firstName}')">
							<span class="btn_img"><span class="confirm">Select</span></span></a>	
	</display:column>
</display:table>
</c:if>