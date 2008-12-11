<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="person.search.title"/></title>
    <%@include file="../selectAndClose.jsp" %>
</head>
<body> 
<c:set var="isResults" value="${fn:length(results.list) > 0}" />
<div id="findDuplicates">
    <div id="findDuplicatesForm" <c:if test="${isResults}">style="display:none;"</c:if>>
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
				                <po:inputRow>
				                <po:inputRowElement>
		                        <s:textfield label="%{getText('person.id')}" name="criteria.person.id" size="10"/>
				                </po:inputRowElement>
				                <po:inputRowElement>
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
				                </po:inputRowElement>
				                </po:inputRow>
				                 <s:textfield label="%{getText('person.prefix')}" name="criteria.person.prefix" size="10"/>
				                 <s:textfield label="%{getText('person.firstName')}" name="criteria.person.firstName" size="50"/>
				                 <s:textfield label="%{getText('person.middleName')}" name="criteria.person.middleName" size="50"/>
				                 <s:textfield label="%{getText('person.lastName')}" name="criteria.person.lastName" size="50"/>
				                 <s:textfield label="%{getText('person.suffix')}" name="criteria.person.suffix" size="10"/>
				                <div class="clear"></div>
				            </div>
				        </div>
				        <%-- 
				        --%>
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
	                <%--
	                 --%>
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