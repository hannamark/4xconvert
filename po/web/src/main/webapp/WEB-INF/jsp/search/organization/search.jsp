<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="organization.search.title"/></title>
</head>
<body> 
	        <s:actionerror/>
    <div class="boxouter_nobottom">
    <h2>Organization Criteria Information</h2>
	        <s:form action="search/organization/search.action" id="searchOrganizationForm">
	            <s:hidden name="rootKey"/>
	            <div class="boxouter">
	            <h2>Basic Identifying Information</h2>
	                <div class="box_white">
	                    <s:textfield label="%{getText('organization.id')}" name="criteria.organization.id" size="10"/>
	                    <s:set name="statusValues" value="@gov.nih.nci.po.data.bo.EntityStatus@values()" />
                        <s:select
		                   label="%{getText('organization.statusCode')}"
		                   name="criteria.organization.statusCode"
		                   list="#statusValues"
		                   listKey="name()"
		                   listValue="name()"
		                   value="criteria.organization.statusCode" 
		                   headerKey="" headerValue="--Select a Status--" 
		                   /> 
	                    <s:textfield label="%{getText('organization.name')}" name="criteria.organization.name" size="70"/>
	                    <s:textfield label="%{getText('organization.abbreviatedName')}" name="criteria.organization.abbreviatedName" size="70"/>
	                    <s:textfield label="%{getText('organization.description')}" name="criteria.organization.description" size="70"/>
	                    <div class="clear"></div>
	                </div>
	            </div>
	            
	            <div class="boxouter">
	            <h2>Address Information</h2>
	                <div class="box_white">
	                    <po:addressForm addressKeyBase="criteria.organization.postalAddress" address="${criteria.organization.postalAddress}" required="false"/>
	                    <div class="clear"></div>
	                </div>
	            </div>
	            
	            <div class="boxouter">
	            <h2>Contact Information</h2>
	                <div class="box_white">
	                    <div class="clear"></div>
	                    <%@ include file="../../contactable/contacts.jsp" %>            
	                </div>
	            </div>
	            <div class="clearfloat"></div>
	        </s:form>
	        <div style="float:right;">
	            <po:button href="javascript://nop/" 
	                onclick="$('searchOrganizationForm').submit();" 
	                style="search" text="Search" 
	                id="submitSearchOrganizationForm"/>
	        </div>                
    </div>
<div style="height=10px;"> 
	<br>
	<br>
</div>

<div class="boxouter">
   <h2><fmt:message key="organization.search.results"/></h2>
   <div id="organizationSearchResults">     
   <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>