<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <s:set name="isCreate" value="person.id == null"/>
    <s:set name="isNotCreate" value="person.id != null"/>
    <s:if test="%{isCreate}">
        <title>Create Person</title>
    </s:if>
    <s:else>
	    <c:if test="${fn:length(person.changeRequests) > 0}">
	       <title><s:text name="person.details.title"/> - Comparison</title>
	    </c:if>
	    <c:if test="${fn:length(person.changeRequests) == 0}">
	       <title><s:text name="person.details.title"/></title>
	    </c:if>
    </s:else>
    <script type="text/javascript">
    function handleDuplicateOf() {
    	$('duplicateOfDiv')[$('curatePersonForm.person.statusCode').value == 'NULLIFIED' ? 'show' : 'hide'](); 
            	
    	if ($('curatePersonForm.person.statusCode').value != 'NULLIFIED') {
    		$('curatePersonForm.person.duplicateOf.id').value = '';
    	}
    	return true;
    }
    </script>
</head>
<body>


<%-- page conditional variable --%>
<c:url value="/notYetImplemented.jsp" var="urlNotYetImplemented"/>

<s:if test="%{isNotCreate}">
	<c:if test="${fn:length(person.changeRequests) > 0}">
	<s:form action="ajax/person/curate/changeCurrentChangeRequest.action" id="changeCrForm">
	    <s:hidden key="rootKey"/>
	    <s:select
	       label="Current Change Request"
	       name="cr"
	       list="selectChangeRequests"
	       value="cr.id"
	       onchange="document.getElementById('curatePersonForm_cr_id').value = this.value; submitAjaxForm('changeCrForm','crinfo', null, true);" 
	       />
	</s:form>
	</c:if> 
</s:if> 

<po:successMessages/>

<div id="page" style="margin-top:10px;">
	<div class="boxouter_nobottom">
	<h2>Person Information</h2>
	
	<s:if test="%{isCreate}">
	   <s:set name="formAction" value="'create/person/create.action'"/>
	</s:if>
	<s:else>
	   <s:set name="formAction" value="'person/curate/curate.action'"/>
	</s:else>
	<s:form action="%{formAction}" id="curatePersonForm" onsubmit="return confirmThenSubmit(document.forms.curatePersonForm);">
	    <input id="enableEnterSubmit" type="submit"/>
		<s:hidden key="rootKey"/>
	    <s:hidden key="cr.id"/>
	    <s:hidden key="person.id"/>
		<div class="boxouter">
		<h2>Basic Identifying Information</h2>
		    <div class="box_white">
		        <s:actionerror/>
	        <s:if test="isCreate">
	            <s:select
	               label="%{getText('person.statusCode')}"
	               name="person.statusCode"
	               list="availableStatus"
	               listKey="name()"
	               listValue="name()"
	               value="person.statusCode" 
	               headerKey="" headerValue="--Select a Status--" 
	               required="true" cssClass="required" 
	               id="curatePersonForm.person.statusCode"/>		        
	        </s:if>
	        <s:else>
		        <po:inputRow>
		        <po:inputRowElement><po:field labelKey="person.id">${person.id}</po:field></po:inputRowElement>
		        <po:inputRowElement>&nbsp;</po:inputRowElement>
		        <po:inputRowElement><po:field labelKey="person.statusCode">${person.statusCode}</po:field></po:inputRowElement>
		        <po:inputRowElement>&nbsp;</po:inputRowElement>
		        <po:inputRowElement><po:field labelKey="person.statusDate"><s:date name="person.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
		        </po:inputRow>
			    <s:select
			       label="New %{getText('person.statusCode')}"
			       name="person.statusCode"
			       list="person.priorEntityStatus.allowedTransitions"
			       value="person.statusCode" 
			       headerKey="" headerValue="--Select a Status--" 
			       onchange="handleDuplicateOf();"
			       required="true" cssClass="required" 
			       id="curatePersonForm.person.statusCode"/>         
        	    <div id="duplicateOfDiv" <s:if test="person.statusCode != @gov.nih.nci.po.data.bo.EntityStatus@NULLIFIED">style="display:none;"</s:if>>
	                <div class="wwgrp" id="wwgrp_curatePersonForm_person_duplicateOf_id">
	                    <div style="float:right;">
	                        <c:url value="/notYetImplemented.jsp" var="duplicatesUrl">
	                            <c:param name="source.id" value="${person.id}"/>
	                        </c:url>
	                        <po:buttonRow>
	                        <po:button id="select_duplicate" href="javascript://noop/" onclick="showPopWin('${duplicatesUrl}', 800, 800, showPopWinCallback);" style="search" text="Select Duplicate"/>
	                        </po:buttonRow>
                        </div>
                        
	                    <div class="wwlbl" id="wwlbl_curatePersonForm_person_duplicateOf_id">
		                    <label class="label" for="curatePersonForm_person_duplicateOf_id">        
		                    <s:text name="person.duplicateOf.id"/>:
		                    </label>
	                    </div>
	                    <br/>
	                    <div class="wwctrl" id="wwctrl_curatePersonForm_person_duplicateOf_id">
	                       ${person.duplicateOf.id} 
	                    </div>
	                    
	                </div>
				    <s:hidden key="person.duplicateOf" id="curatePersonForm.person.duplicateOf.id"/>
			    </div>
	        </s:else>
				<s:textfield key="person.prefix" size="10"/>
				<s:textfield key="person.firstName" required="true" cssClass="required" size="50"/>
				<s:textfield key="person.middleName" size="50"/>
				<s:textfield key="person.lastName" required="true" cssClass="required" size="50"/>
				<s:textfield key="person.suffix" size="10"/>
		        <div class="clear"></div>
		    </div>
		</div>
		
		<div class="boxouter">
		<h2>Address Information</h2>
		    <div class="box_white">
		        <po:addressForm formNameBase="curatePersonForm" addressKeyBase="person.postalAddress" address="${person.postalAddress}" required="true"/>
		        <div class="clear"></div>
		    </div>
		</div>
	</s:form>
		
		<div class="boxouter_nobottom">
		<h2>Contact Information</h2>
		    <div class="box_white">
		        <div class="clear"></div>
		        <po:contacts contactableKeyBase="person"/>
		    </div>
		</div>
<!-- Insert Person Roles content here -->	
    </div>
</div>

<c:if test="${fn:length(person.changeRequests) > 0}">
<div id="page" style="margin-top:10px;">
<div id="crinfo">
<%@ include file="crInfo.jsp" %>
</div>
</div>
</c:if>

<div style="clear:left;">
</div>



<div class="btnwrapper" style="margin-bottom:20px;">
	<script type="text/javascript">
		function showPopWinCallback(returnVal) {
			$('curatePersonForm.person.duplicateOf.id').value = returnVal;
			$('wwctrl_curatePersonForm_person_duplicateOf_id').innerHTML = $('curatePersonForm.person.duplicateOf.id').value;
		}
	</script>
    <script type="text/javascript">
       function confirmThenSubmit(formId) {
           if ($('curatePersonForm.person.statusCode').value == 'NULLIFIED') {
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
        <po:button id="save_button" href="javascript://noop/" onclick="confirmThenSubmit(document.forms.curatePersonForm);" style="save" text="Save"/>
    </po:buttonRow>
</div>

</body>
</html>