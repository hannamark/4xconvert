<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="organization.search.title"/></title>
    <script type="text/javascript">
        var returnVal;
	    function markAsDuplicate(id) {
		     var text = '<s:text name="curation.reject.mark.as.duplicate.confirmation"/>';
	    	 var result = confirm(text);
	    	 if (result == true)
	    	 {
	    		 returnVal = id;
	             window.top.hidePopWin(true);
	    	 }
	    }
	</script>
</head>
<body> 
<div id="findDuplicates">
<div class="po_wrapper">
    <div class="po_inner">
        <h1>Find Duplicate Organization(s)</h1>
        <div class="box_white">
            <div class="po_form">
                <po:successMessages />
                <s:actionerror/>
                <s:form action="ajax/duplicates/organization/search.action" id="duplicateOrganizationForm">
                    <s:hidden name="rootKey"/>
                    <s:hidden name="source.id"/>
			        <div class="boxouter">
			        <h2>Basic Identifying Information</h2>
			            <div class="box_white">
			                <s:actionerror/>
			                <s:textfield label="%{getText('organization.name')}" name="criteria.organization.name" size="70"/>
			                <s:textfield label="%{getText('organization.abbreviatedName')}" name="criteria.organization.abbreviatedName" size="70"/>
			                <s:textfield label="%{getText('organization.description')}" name="criteria.organization.description" size="70"/>
			                <div class="clear"></div>
			            </div>
			        </div>
			        
			        <div class="boxouter">
			        <h2>Address Information</h2>
			            <div class="box_white">
			                <po:addressForm addressKeyBase="criteria.organization.postalAddress" address="${criteria.organization.postalAddress}" required="false"/>
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
				<div style="float:right;">
				    <po:button href="javascript://nop/" 
				        onclick="$('duplicateSearchResultDetails').hide(); submitAjaxForm('duplicateOrganizationForm', 'duplicateSearchResults', null, true);" 
				        style="search" text="Search for Duplicates" 
				        id="submitDuplicateOrganizationForm"/>
				</div>                
            </div>
        </div>
    </div>
</div>
<div style="height=10px;">
<br>
<br>
</div>

<div class="po_wrapper">
    <div class="po_inner">
        <div class="box_white">
			<div class="boxouter">
			<h2>Possible Duplicate Organization(s)</h2>
				<div id="duplicateSearchResults">     
	            <%@ include file="results.jsp" %>
	            </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="po_wrapper">
    <div class="po_inner">
        <div class="box_white" id="duplicateSearchResultDetails" style="display:none;">
            <%@ include file="detail.jsp" %>
        </div>
    </div>
</div>
</body>
</html>