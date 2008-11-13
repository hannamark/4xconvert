<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<title>Manage Oversight Committee(s)</title>
</head> 
<body>

<po:successMessages/>

    <div class="boxouter">
    <h2>Oversight Committee Information</h2>
        <%@ include file="../orgInfo.jsp" %>
		<div class="boxouter">
		<h2>Oversight Committee Roles</h2>
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
                <c:param name="organization" value="${organization.id}"/>
            </c:url>
            <po:button id="continue_button" href="${curateUrl}" style="continue" text="Close"/>
        </po:buttonRow>
    </div>
</body>
</html>    

