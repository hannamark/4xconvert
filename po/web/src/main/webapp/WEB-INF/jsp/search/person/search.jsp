<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="person.search.title"/></title>
    <c:set var="topic" scope="request" value="searchperson"/>
</head>
<body> 
<s:actionerror/>
<div class="boxouter">
<h2>Person Criteria Information</h2>
	<s:form action="search/person/search.action" id="searchPersonForm">
        <s:token/>
		<s:hidden name="rootKey"/>
        <%@include file="searchFormFields.jsp" %>
        <input id="enableEnterSubmit" type="submit"/>
    </s:form>
    <div style="clear:left;"></div>
</div>
<div class="btnwrapper" style="margin-bottom:20px;">
	<po:buttonRow>
		<po:button href="javascript://nop/" 
		    onclick="$('searchPersonForm').submit();" 
		    style="search" text="Search" 
		    id="submitSearchOrganizationForm"/>
	</po:buttonRow>
</div> 

<div class="boxouter">
   <h2><fmt:message key="person.search.results"/></h2>
   <div id="personSearchResults">     
    <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>