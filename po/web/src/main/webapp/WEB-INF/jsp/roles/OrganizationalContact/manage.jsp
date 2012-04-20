<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="setOCType.jsp" %>
<html>
<head>
<title><s:text name="organizationalContact.manage.title"/></title>
<s:if test="%{#ocType == 'organizational'}">
    <c:set var="topic" scope="request" value="manageorgcontact"/>
</s:if>
<s:else>
    <c:set var="topic" scope="request" value="managepersoncontact"/>
</s:else>
</head> 
<body>

<po:successMessages/>

    <div class="boxouter">
    <h2><s:text name="organizationalContact"/> Information</h2>
        <s:if test="%{#ocType == 'organizational'}">
            <%@ include file="../organizational/orgInfo.jsp" %>
        </s:if>
        <s:else>
            <%@ include file="../person/personInfo.jsp" %>
        </s:else>
		<div class="boxouter">
		<h2><s:text name="organizationalContact"/> Roles</h2>
		    <div id="roles">
		    <%@include file="list.jsp"%> 
		    </div> 
		</div>
    </div> 
    <div class="btnwrapper" style="margin-bottom:20px;">
        <po:buttonRow>
            <s:if test="%{#ocType == 'organizational'}">
                <c:url var="addUrl" value="/protected/roles/${ocType}/OrganizationalContact/input.action">
                    <c:param name="organization" value="${organization.id}"/>
                </c:url>
                <s:set name="returnToPageTitle" value="%{'Return to ' + getText('organization.details.title')}"/>
                <c:url var="curateUrl" value="/protected/organization/curate/start.action">
                    <c:param name="organization.id" value="${organization.id}"/>
                </c:url>
            </s:if>
            <s:else>
                <c:url var="addUrl" value="/protected/roles/${ocType}/OrganizationalContact/input.action">
                    <c:param name="person" value="${person.id}"/>
                </c:url>
                <s:set name="returnToPageTitle" value="%{'Return to ' + getText('person.details.title')}"/>
                <c:url var="curateUrl" value="/protected/person/curate/start.action">
                    <c:param name="person.id" value="${person.id}"/>
                </c:url>
            </s:else>
            <po:button id="add_button" href="${addUrl}" style="add" text="Add"/>
            <po:button id="return_to_button" href="${curateUrl}" style="continue" text="${returnToPageTitle}"/>
        </po:buttonRow>
    </div>
</body>
</html>    
