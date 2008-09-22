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

<c:if test="${fn:length(organization.changeRequests) > 0}">
<s:form action="ajax/organization/curate/changeCurrentChangeRequest.action" id="changeCrForm">
    <s:hidden key="rootKey"/>
    <s:select
       label="Current Change Request"
       name="cr"
       list="selectChangeRequests"
       value="cr.id"
       onchange="document.getElementById('curateOrgForm_cr_id').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);" 
       />
</s:form>
</c:if>


<div id="page">
	<div class="boxouter">
	<h2>Person Information</h2>
	<s:form action="organization/curate/curate.action" id="curateOrgForm">
		<s:hidden key="rootKey"/>
	    <s:hidden key="cr.id"/>
	    <s:hidden key="organization.id"/>
		<div class="boxouter">
		<h2>Basic Identifying Information</h2>
		    <div class="box_white">
		        <s:actionerror/>
				<s:textfield key="organization.name" required="true" cssClass="required" size="70"/>
				<s:textfield key="organization.abbreviatedName" required="false" cssClass="required" size="70"/>
				<s:textfield key="organization.description" required="false" cssClass="required" size="70"/>
		        <div class="clear"></div>
		    </div>
		</div>
		
		<div class="boxouter">
		<h2>Address Information</h2>
		    <div class="box_white">
		        <po:addressForm addressKeyBase="organization.postalAddress" address="${organization.postalAddress}"/>
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
    </div>
</div>

<c:if test="${fn:length(organization.changeRequests) > 0}">
<%-- 
<s:iterator value="organization.changeRequests" id="cr">
--%>
<div id="page">
<div id="crinfo">
<%@ include file="orgCrInfo.jsp" %>
</div>
</div>
<%-- 
</s:iterator>
--%>
</c:if>


<div class="btnwrapper">
    <po:buttonRow>
        <po:button href="${urlNotYetImplemented}" style="search" text="Search for Duplicates"/>
        <po:button id="mark_as_accepted_button" href="javascript://noop/" onclick="document.forms.curateOrgForm.submit();" style="confirm" text="Mark as Accepted"/>
        <po:button id="mark_as_rejected_button" href="${urlNotYetImplemented}" style="reject" text="Mark as Rejected"/>
    </po:buttonRow>
</div>
</body>
</html>