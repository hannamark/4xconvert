<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<title><s:text name="identifiedOrganization.manage.title"/></title>
</head> 
<body>

<po:successMessages/>

    <div class="boxouter">
    <h2>Clinical Research Staff Information</h2>
        <%@ include file="../personInfo.jsp" %>
		<div class="boxouter">
		<h2>Clinical Research Staff Roles</h2>
		    <div id="roles">
		    <%@include file="list.jsp"%> 
		    </div> 
		</div>
    </div> 
    <div class="btnwrapper" style="margin-bottom:20px;">
        <po:buttonRow>
            <c:url var="addUrl" value="/protected/roles/person/ClinicalResearchStaff/input.action">
                <c:param name="person" value="${person.id}"/>
            </c:url>
            <po:button id="add_button" href="${addUrl}" style="add" text="Add"/>
            <c:url var="curateUrl" value="/protected/person/curate/start.action">
                <c:param name="person" value="${person.id}"/>
            </c:url>
            <s:set name="returnToPageTitle" value="%{'Return to ' + getText('person.details.title')}"/>
            <po:button id="return_to_button" href="${curateUrl}" style="continue" text="${returnToPageTitle}"/>
        </po:buttonRow>
    </div>
</body>
</html>    

