<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="person.search.title"/></title>
    <script type="text/javascript">
        var returnVal;
	    function markAsDuplicate(id) {
    		 returnVal = id;
             window.top.hidePopWin(true);
	    }
	</script>
</head>
<body> 
<div id="findDuplicates">
	<div class="po_wrapper">
	    <div class="po_inner">
	        <h1><fmt:message key="person.search.title"/></h1>
	        <div class="box_white">
	            <div class="po_form">
	                <s:actionerror/>
	                <s:form action="selector/person/search.action" id="duplicatePersonForm" onsubmit="$('duplicateSearchResultDetails').hide();">
	                    <s:hidden name="rootKey"/>
	                    <s:hidden name="source.id"/>
				        <div class="boxouter">
				        <h2>Basic Identifying Information</h2>
				            <div class="box_white">
		                        <s:textfield label="%{getText('person.id')}" name="criteria.person.id" size="10"/>
		                        <s:set name="statusValues" value="@gov.nih.nci.po.data.bo.EntityStatus@values()" />
		                        <s:select
		                           label="%{getText('person.statusCode')}"
		                           name="criteria.person.statusCode"
		                           list="#statusValues"
		                           listKey="name()"
		                           listValue="name()"
		                           value="criteria.person.statusCode" 
		                           headerKey="" headerValue="--Select a Status--" 
		                           />				            
				                 <s:textfield label="%{getText('person.prefix')}" name="criteria.person.prefix" size="10"/>
				                 <s:textfield label="%{getText('person.firstName')}" name="criteria.person.firstName" size="50"/>
				                 <s:textfield label="%{getText('person.middleName')}" name="criteria.person.middleName" size="50"/>
				                 <s:textfield label="%{getText('person.lastName')}" name="criteria.person.lastName" size="50"/>
				                 <s:textfield label="%{getText('person.suffix')}" name="criteria.person.suffix" size="10"/>
				                <div class="clear"></div>
				            </div>
				        </div>
				        
				        <div class="boxouter">
				        <h2>Address Information</h2>
				            <div class="box_white">
				                <po:addressForm formNameBase="duplicatePersonForm" addressKeyBase="criteria.person.postalAddress" address="${criteria.person.postalAddress}" required="false"/>
				                <div class="clear"></div>
				            </div>
				        </div>
				        
				        <div class="boxouter">
				        <h2>Contact Information</h2>
				            <div class="box_white">
				                <div class="clear"></div>
	                            <s:textfield label="%{getText('emailEntry.value')}" name="criteria.emailEntry.value" size="20"/>
	                            <s:textfield label="%{getText('urlEntry.value')}" name="criteria.urlEntry.value" size="20"/>
	                            <s:textfield label="%{getText('phoneEntry.value')}" name="criteria.phoneEntry.value" size="20"/>
	                            <s:textfield label="%{getText('faxEntry.value')}" name="criteria.faxEntry.value" size="20"/>
	                            <s:textfield label="%{getText('ttyEntry.value')}" name="criteria.ttyEntry.value" size="20"/>
				            </div>
				        </div>
				        <div class="clearfloat"></div>
				        <input id="enableEnterSubmit" type="submit"/>
	                </s:form>
					<div style="float:right;">
					    <po:button href="javascript://nop/" 
					        onclick="$('duplicatePersonForm').submit();" 
					        style="search" text="Search" 
					        id="submitDuplicatePersonForm"/>
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
				<h2><fmt:message key="person.search.results"/></h2>
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