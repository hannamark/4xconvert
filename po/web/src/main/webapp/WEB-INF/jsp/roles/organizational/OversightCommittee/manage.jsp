<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<title><s:text name="oversightCommittee.manage.title"/></title>
<c:set var="topic" scope="request" value="manageoc"/>
</head> 
<body>

<po:successMessages/>

    <div class="boxouter">
    <h2><s:text name="oversightCommittee"/> Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
		<h2><s:text name="oversightCommittee"/> Roles</h2>
		    <div id="roles">
		    <%@include file="list.jsp"%> 
		    </div> 
		</div>
    </div> 
    <div class="btnwrapper" style="margin-bottom:20px;">
        <po:buttonRow>
            <c:url var="addUrl" value="/protected/roles/organizational/OversightCommittee/input.action">
                <c:param name="organization" value="${organization.id}"/>
            </c:url>
            <po:button id="add_button" href="${addUrl}" style="add" text="Add"/>
            <c:url var="curateUrl" value="/protected/organization/curate/start.action">
                <c:param name="organization.id" value="${organization.id}"/>
            </c:url>
            <s:set name="returnToPageTitle" value="%{'Return to ' + getText('organization.details.title')}"/>
            <po:button id="return_to_button" href="${curateUrl}" style="continue" text="${returnToPageTitle}"/>
        </po:buttonRow>
    </div>
</body>
</html>    

