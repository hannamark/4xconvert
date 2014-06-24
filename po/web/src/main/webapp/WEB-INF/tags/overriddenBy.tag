<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ tag display-name="overriddenBy"
	description="Renders overriddenBy in the form" body-content="empty"%>
<%@ attribute name="overriddenByUserName" type="java.lang.String"
	required="true"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div class="wwlbl" id ="wwlbl_overriddenBy">
	<label class="label" for="overriddenBy"> 
	   <s:text name="overriddenBy" />:
	</label>
	<s:if test='%{#attr.overriddenByUserName != ""}'>    
       <c:out value="${overriddenByUserName}"></c:out>
    </s:if>
    <s:else>
       <s:text name="overriddenByUserName">Not Overridden</s:text>
    </s:else>
</div>