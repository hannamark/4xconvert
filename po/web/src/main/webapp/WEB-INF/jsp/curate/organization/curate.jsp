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
	<h2>Organization Information</h2>
	<s:form action="organization/curate/curate.action" id="curateOrgForm">
		<s:hidden key="rootKey"/>
	    <s:hidden key="cr.id"/>
	    <s:hidden key="organization.id"/>
		<div class="boxouter">
		<h2>Basic Identifying Information</h2>
		    <div class="box_white">
		        <s:actionerror/>
                <div class="wwgrp" id="wwgrp_curateOrgForm_organization_statusCode">
		            <div class="wwlbl" id="wwlbl_curateOrgForm_organization_statusCode">
		            <label class="label" for="curateOrgForm_organization_statusCode">        
		            <s:text name="organization.statusCode"/>:
		            </label></div> <br/><div class="wwctrl" id="wwctrl_curateOrgForm_organization_statusCode">
		            ${organization.statusCode} 
		            </div>
	            </div>
                <div class="wwgrp" id="wwgrp_curateOrgForm_organization_id">
		            <div class="wwlbl" id="wwlbl_curateOrgForm_organization_id">
		            <label class="label" for="curateOrgForm_organization_id">        
		            <s:text name="organization.id"/>:
		            </label></div> <br/><div class="wwctrl" id="wwctrl_curateOrgForm_organization_id">
		            ${organization.id} 
		            </div>
	            </div>
				<s:textfield key="organization.name" required="true" cssClass="required" size="70"/>
				<s:textfield key="organization.abbreviatedName" required="false" cssClass="required" size="70"/>
				<s:textfield key="organization.description" required="false" cssClass="required" size="70"/>
		        <div class="clear"></div>
		    </div>
		</div>
		
		<div class="boxouter">
		<h2>Address Information</h2>
		    <div class="box_white">
		        <po:addressForm addressKeyBase="organization.postalAddress" address="${organization.postalAddress}" required="true"/>
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
<script type="text/javascript">
function showPopWinCallback(returnVal) {
	window.location = '' + returnVal;
}
</script>

    <c:url value="/protected/duplicates/organization/start.action" var="duplicatesUrl">
        <c:param name="source.id" value="${organization.id}"/>
    </c:url>
    <c:url value="/protected/organization/curate/reject.action" var="rejectUrl">
        <c:param name="organization.id" value="${organization.id}"/>
    </c:url>
    <po:buttonRow>
        <po:button id="mark_as_accepted_button" href="javascript://noop/" onclick="document.forms.curateOrgForm.submit();" style="confirm" text="Mark as Accepted"/>
        <po:button id="mark_as_rejected_button" href="${rejectUrl}" style="reject" text="Mark as Rejected"/>
        <po:button href="javascript://noop/" onclick="showPopWin('${duplicatesUrl}', 800, 800, showPopWinCallback);" style="search" text="Mark as Duplicate"/>
    </po:buttonRow>
</div>
<div style="height=10px;">
<br>
<br>
</div>
</body>
</html>