<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="organization.search.title"/></title>
    <%@include file="../selectAndClose.jsp" %>
    <style type="text/css">
		.po_form LABEL {
		    display: inherit !important;		    
		}        
    </style>
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
		                <s:form action="selector/organization/searchdt.action" id="duplicateOrganizationForm" onsubmit="$('duplicateSearchResultDetails').hide();">
		                    <s:hidden name="rootKey"/>
		                    <s:hidden name="source.id"/>
	                        <%@include file="../../search/organization/searchFormFields.jsp" %>
					        <input id="enableEnterSubmit" type="submit"/>
		                </s:form>
						<div>
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
				                <po:button href="javascript://nop/" onclick="backToSearchForm();" style="continue" text="Back to Search Form" id="selector_org_back_to_search_form_top"/>
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
				                <po:button href="javascript://nop/" onclick="backToSearchForm();" style="continue" text="Back to Search Form" id="selector_org_back_to_search_form_bottom"/>
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