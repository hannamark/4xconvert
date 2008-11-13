<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
<title>Manage Identified Organization(s)</title>
</head> 
<body>

<po:successMessages/>

    <div class="boxouter">
    <h2>Identified Organization Information</h2>
        <div class="boxouter">
        <h2>Organization Information</h2>
            <div class="box_white">
                <s:form action="roles/organizational/IdentifiedOrganization/start.action" id="basicOrgDetailForm" theme="css_xhtml_readonly">
	            <po:inputRow>
	            <po:inputRowElement><s:textfield key="organization.id" /></po:inputRowElement>
	            <po:inputRowElement><s:textfield key="organization.statusCode" /></po:inputRowElement>
	            </po:inputRow>
	            <po:inputRowElement><s:textfield key="organization.name" required="false" cssClass="required" size="70"/></po:inputRowElement>
                </s:form>
                <div class="clear"></div>
            </div>
        </div>
		<div class="boxouter">
		<h2>Identified Organizational Roles</h2>
		    <div id="roles">
		    <%@include file="list.jsp"%> 
		    </div> 
		</div>
    </div> 
    <div class="btnwrapper" style="margin-bottom:20px;">
        <po:buttonRow>
            <c:url var="addUrl" value="/protected/roles/organizational/IdentifiedOrganization/input.action">
                <c:param name="organization.id" value="${organization.id}"/>
            </c:url>
            <po:button id="add_button" href="${addUrl}" style="add" text="Add"/>
            <c:url var="curateUrl" value="/protected/organization/curate/start.action">
                <c:param name="organization.id" value="${organization.id}"/>
            </c:url>
            <po:button id="continue_button" href="${curateUrl}" style="continue" text="Close"/>
        </po:buttonRow>
    </div>
</body>
</html>    

