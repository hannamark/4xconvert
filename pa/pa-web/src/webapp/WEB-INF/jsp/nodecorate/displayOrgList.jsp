<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<pa:failureMessage/>
<c:if test="${orgs != null}">

<display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row" 
	name="orgs" export="false" requestURI="popupdisplayOrgListDisplayTag.action">
	<display:column title="Organization Name" property="name.part[0].value"  headerClass="sortable"/>
	<display:column title="City" property="postalAddress.part[1].value"  headerClass="sortable"/> 
	<display:column title="Country" property="postalAddress.part[3].code"  headerClass="sortable"/> 
	<display:column title="Zip" property="postalAddress.part[2].value"  headerClass="sortable"/> 
		<display:column title="Action" class="action" sortable="false">
		<s:a href="#" cssClass="btn" onclick="submitform('${row.identifier.extension}')"><span class="btn_img"><span class="add">Select</span></span></s:a>  
	</display:column>
</display:table>

</c:if>