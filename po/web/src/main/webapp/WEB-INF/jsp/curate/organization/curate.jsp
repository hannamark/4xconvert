<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <c:if test="${fn:length(organization.changeRequests) > 0}">
    <title>Organization Details - Comparison</title>
    </c:if>
    <c:if test="${fn:length(organization.changeRequests) == 0}">
    <title>Organization Details</title>
    </c:if>
</head>
<body>


<%-- page conditional variable --%>
<c:url value="/notYetImplemented.jsp" var="urlNotYetImplemented"/>

<s:form action="organization/curate/curate.action" id="curateOrgForm">
<div class="boxouter">
<h2>Basic Identifying Information</h2>
    <div class="box_white">
        <s:hidden key="cr.id"/>
        <s:hidden key="organization.id"/>
        
        <s:actionerror/>
        
		<s:textfield key="organization.name" required="true" cssClass="required"/>
		<s:textfield key="organization.abbreviatedName" required="false" cssClass="required"/>
		<s:textfield key="organization.description" required="false" cssClass="required"/>
        <div class="dotted_line"></div>
        <%--@ include file="../../contactinfo/contacts.jsp" --%>        		
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Address Information</h2>
    <div class="box_white">
        <po:addressForm addressKeyBase="organization.postalAddress" address="${organization.postalAddress}" />
        <div class="dotted_line"></div>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Contact Information</h2>
    <div class="box_white">
        <div class="clear"></div>
    </div>
</div>
<div class="clearfloat"></div>
</s:form>

<div class="btnwrapper">
    <po:buttonRow>
        <po:button href="${urlNotYetImplemented}" style="search" text="Search for Duplicates"/>
        <po:button href="javascript://noop/" onclick="document.forms.curateOrgForm.submit();" style="confirm" text="Mark as Accepted"/>
        <po:button href="${urlNotYetImplemented}" style="reject" text="Mark as Rejected"/>
    </po:buttonRow>
</div>
</body>
</html>