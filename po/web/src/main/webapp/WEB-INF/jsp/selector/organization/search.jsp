<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="organization.search.title"/></title>
    <%@include file="../selectAndClose.jsp" %>
</head>
<body> 
<c:set var="isResults" value="${fn:length(results.list) > 0}" />
<div id="findDuplicates">
    <div id="findDuplicatesForm" <c:if test="${isResults}">style="display:none;"</c:if>>
	<div class="po_wrapper">
	    <div class="po_inner">
	        <h1><fmt:message key="organization.search.title"/></h1>
	        <div class="box_white">
	            <div class="po_form">
	                <s:actionerror/>
	                <s:form action="selector/organization/search.action" id="duplicateOrganizationForm" onsubmit="$('duplicateSearchResultDetails').hide();">
	                    <s:hidden name="rootKey"/>
	                    <s:hidden name="source.id"/>
				        <div class="boxouter">
				        <h2>Basic Identifying Information</h2>
				            <div class="box_white">
				                <po:inputRow>
				                <po:inputRowElement>
		                        <s:textfield label="%{getText('organization.id')}" name="criteria.organization.id" size="10"/>
				                </po:inputRowElement>
				                <po:inputRowElement>
		                        <s:set name="statusValues" value="@gov.nih.nci.po.data.bo.EntityStatus@values()" />
		                        <s:select
		                           label="%{getText('organization.statusCode')}"
		                           name="criteria.organization.statusCode"
		                           list="#statusValues"
		                           listKey="name()"
		                           listValue="name()"
		                           value="criteria.organization.statusCode" 
		                           headerKey="" headerValue="--Select a Status--" 
		                           />				            
				                </po:inputRowElement>
				                </po:inputRow>
				                <s:textfield label="%{getText('organization.name')}" name="criteria.organization.name" size="50"/>
				                <div class="clear"></div>
				            </div>
				        </div>
				        
				        <div class="boxouter">
				        <h2>Address Information</h2>
				            <div class="box_white">
				                <po:addressForm formNameBase="duplicateOrganizationForm" addressKeyBase="criteria.organization.postalAddress" address="${criteria.organization.postalAddress}" required="false"/>
				                <div class="clear"></div>
				            </div>
				        </div>
				        <div class="clearfloat"></div>
				        <input id="enableEnterSubmit" type="submit"/>
	                </s:form>
					<div style="float:right;">
					    <po:button href="javascript://nop/" 
					        onclick="$('duplicateOrganizationForm').submit();" 
					        style="search" text="Search" 
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
	</div>
	<script type="text/javascript">
	<!--
		function backToSearchForm() {
		  $('duplicateSearchResultDetails').hide(); 
		  $('findDuplicatesResults').hide();
		  $('findDuplicatesForm').show();
		  $('findDuplicates').show(); 
		}
	//-->
	</script>
	<div id="findDuplicatesResults" <c:if test="${!isResults}">style="display:none;"</c:if>>
		<div class="po_wrapper">
		    <div class="po_inner">
		        <div class="box_white">
				    <div id="showDuplicatesFormTop">
				        <div class="btnwrapper">
				            <po:buttonRow>
				                <po:button href="javascript://nop/" onclick="backToSearchForm();" style="" text="Back to Search Form" />
				            </po:buttonRow>
				        </div>  
				    </div>
					<div class="boxouter">
					<h2><fmt:message key="organization.search.results"/></h2>
						<div id="duplicateSearchResults">     
			            <%@ include file="results.jsp" %>
			            </div>
		            </div>
				    <div id="showDuplicatesFormBottom">
				        <div class="btnwrapper">
				            <po:buttonRow>
				                <po:button href="javascript://nop/" onclick="backToSearchForm();" style="" text="Back to Search Form" />
				            </po:buttonRow>
				        </div>  
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