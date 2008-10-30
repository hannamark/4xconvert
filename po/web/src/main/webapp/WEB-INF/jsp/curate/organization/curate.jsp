<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <s:set name="isCreate" value="organization.id == null"/>
    <s:if test="%{isCreate}">
        <title>Create Organization</title>
    </s:if>
    <s:else>
	    <c:if test="${fn:length(organization.changeRequests) > 0}">
	       <title>Organization Details - Comparison</title>
	    </c:if>
	    <c:if test="${fn:length(organization.changeRequests) == 0}">
	       <title>Organization Details</title>
	    </c:if>
    </s:else>
    <script type="text/javascript">
    function handleDuplicateOf() {
    	$('duplicateOfDiv')[$('curateOrgForm_organization_statusCode').value == 'NULLIFIED' ? 'show' : 'hide'](); 
            	
    	if ($('curateOrgForm_organization_statusCode').value != 'NULLIFIED') {
    		$('curateOrgForm.organization.duplicateOf.id').value = '';
    	}
    	return true;
    }
    </script>
</head>
<body>


<%-- page conditional variable --%>
<c:url value="/notYetImplemented.jsp" var="urlNotYetImplemented"/>

<s:if test="%{!isCreate}">
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
</s:if> 

<po:successMessages/>

<div id="page" style="margin-top:10px;">
	<div class="boxouter_nobottom">
	<h2>Organization Information</h2>
	<s:if test="%{isCreate}">
	   <s:set name="formAction" value="'create/organization/create.action'"/>
	</s:if>
	<s:else>
	   <s:set name="formAction" value="'organization/curate/curate.action'"/>
	</s:else>
	<s:form action="%{formAction}" id="curateOrgForm">
		<s:hidden key="rootKey"/>
	    <s:hidden key="cr.id"/>
	    <s:hidden key="organization.id"/>
		<div class="boxouter">
		<h2>Basic Identifying Information</h2>
		    <div class="box_white">
		        <s:actionerror/>
	        <s:if test="isCreate">
	            <s:select
	               label="%{getText('organization.statusCode')}"
	               name="organization.statusCode"
	               list="availableStatus"
	               listKey="name()"
	               listValue="name()"
	               value="organization.statusCode" 
	               headerKey="" headerValue="--Select a Status--" 
	               />		        
	        </s:if>
	        <s:else>
                <div class="wwgrp" id="wwgrp_curateOrgForm_organization_statusCode">
		            <div class="wwlbl" id="wwlbl_curateOrgForm_organization_statusCode">
		            <label class="label" for="curateOrgForm_organization_statusCode">        
		            Current <s:text name="organization.statusCode"/>:
		            </label></div> <br/><div class="wwctrl" id="wwctrl_curateOrgForm_organization_statusCode">
		            ${organization.priorEntityStatus} 
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
			    <s:select
			       label="New %{getText('organization.statusCode')}"
			       name="organization.statusCode"
			       list="organization.priorEntityStatus.allowedTransitions"
			       value="organization.statusCode" 
			       headerKey="" headerValue="--Select a Status--" 
			       onchange="handleDuplicateOf();"
			       />         
        	    <div id="duplicateOfDiv" <s:if test="organization.statusCode != @gov.nih.nci.po.data.bo.EntityStatus@NULLIFIED">style="display:none;"</s:if>>
	                <div class="wwgrp" id="wwgrp_curateOrgForm_organization_duplicateOf_id">
	                    <div style="float:right;">
	                        <c:url value="/protected/duplicates/organization/start.action" var="duplicatesUrl">
	                            <c:param name="source.id" value="${organization.id}"/>
	                        </c:url>
	                        <po:buttonRow>
	                        <po:button id="select_duplicate" href="javascript://noop/" onclick="showPopWin('${duplicatesUrl}', 800, 800, showPopWinCallback);" style="search" text="Select Duplicate"/>
	                        </po:buttonRow>
                        </div>
                        
	                    <div class="wwlbl" id="wwlbl_curateOrgForm_organization_duplicateOf_id">
		                    <label class="label" for="curateOrgForm_organization_duplicateOf_id">        
		                    <s:text name="organization.duplicateOf.id"/>:
		                    </label>
	                    </div>
	                    <br/>
	                    <div class="wwctrl" id="wwctrl_curateOrgForm_organization_duplicateOf_id">
	                       ${organization.duplicateOf.id} 
	                    </div>
	                    
	                </div>
				    <s:hidden key="organization.duplicateOf" id="curateOrgForm.organization.duplicateOf.id"/>
	
			    </div>
	        </s:else>
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
	</s:form>
		
		<div class="boxouter_nobottom">
		<h2>Contact Information</h2>
		    <div class="box_white">
		        <div class="clear"></div>
                <%@ include file="../../contactable/contacts.jsp" %>    		
		    </div>
		</div>
    </div>
</div>

<c:if test="${fn:length(organization.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
<div id="crinfo">
<%@ include file="orgCrInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>

<div class="btnwrapper" style="margin-bottom:20px;">
	<script type="text/javascript">
		function showPopWinCallback(returnVal) {
			$('curateOrgForm.organization.duplicateOf.id').value = returnVal;
			$('wwctrl_curateOrgForm_organization_duplicateOf_id').innerHTML = $('curateOrgForm.organization.duplicateOf.id').value;
		}
	</script>
	<script type="text/javascript">
	   function confirmThenSubmit(formId) {
		   if ($('curateOrgForm_organization_statusCode').value == 'NULLIFIED') {
			   var r = confirm('<s:text name="curation.nullified.confirmation"/>');
			   if (r == true) {
				   $(formId).submit();
				   return true;
			   } else {
				   return false;
			   }
		   } else {
			   $(formId).submit();
			   return true;
		   }
	   } 
	</script>
	    <po:buttonRow>
	        <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit(document.forms.curateOrgForm);" style="save" text="Save"/>
	    </po:buttonRow>
</div>

</body>
</html>