<%@ tag display-name="createdBy"
	description="Renders createdBy in the form" body-content="empty"%>
<%@ attribute name="createdByUserName" type="java.lang.String"
	required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="wwlbl" id ="wwlbl_createdBy">
	<label class="label" for="createdBy"> 
	   <s:text name="createdBy" />:
	</label>	
	<s:if test='%{#attr.createdByUserName != ""}'>	   
	   <s:text name="createdByUserName">${createdByUserName}</s:text>
	</s:if>
</div>