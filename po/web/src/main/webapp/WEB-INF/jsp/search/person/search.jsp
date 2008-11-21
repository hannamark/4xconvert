<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="person.search.title"/></title>
</head>
<body> 
<s:actionerror/>
<div class="boxouter">
<h2>Person Criteria Information</h2>
		<s:form action="search/person/search.action" id="searchPersonForm">
			<s:hidden name="rootKey"/>
			<div>
			<div style="float:left;">
				<div class="boxouter" style="float:left;margin-right: 10px;">
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
				<div class="boxouter" style="float:left;margin-right: 10px;">
					  <h2>Address Information</h2>
				    <div class="box_white">
				        <po:addressForm formNameBase="searchPersonForm" addressKeyBase="criteria.person.postalAddress" address="${criteria.person.postalAddress}" required="false"/>
				        <div class="clear"></div>
				    </div>
				</div>
			</div>
	          
	          <div class="boxouter" style="float:left;margin-right: 10px;">
	          <h2>Contact Information</h2>
	              <div class="box_white">
	                  <div class="clear"></div>
	                  <po:inputRow>
	                  <po:inputRowElement><s:textfield label="%{getText('emailEntry.value')}" name="criteria.emailEntry.value" size="20"/></po:inputRowElement>
	                  <po:inputRowElement><s:textfield label="%{getText('urlEntry.value')}" name="criteria.urlEntry.value" size="20"/></po:inputRowElement>
	                  </po:inputRow>
	                  <po:inputRow>
	                  <po:inputRowElement><s:textfield label="%{getText('phoneEntry.value')}" name="criteria.phoneEntry.value" size="20"/></po:inputRowElement>
	                  <po:inputRowElement><s:textfield label="%{getText('faxEntry.value')}" name="criteria.faxEntry.value" size="20"/></po:inputRowElement>
	                  </po:inputRow>
	              </div>
	          </div>
         <div class="clearfloat"></div>
        </div>
        <input id="enableEnterSubmit" type="submit"/>
     </s:form>
     <div style="clear:left;"></div>
</div>
<div class="btnwrapper" style="margin-bottom:20px;">
	<po:buttonRow>
		<po:button href="javascript://nop/" 
		    onclick="$('searchPersonForm').submit();" 
		    style="search" text="Search" 
		    id="submitSearchOrganizationForm"/>
	</po:buttonRow>
</div> 

<div class="boxouter">
   <h2><fmt:message key="person.search.results"/></h2>
   <div id="personSearchResults">     
    <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>