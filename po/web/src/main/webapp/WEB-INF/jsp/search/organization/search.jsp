<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html>
<head>
    <title><fmt:message key="organization.search.title"/></title>
</head>
<body> 
<s:actionerror/>
<div class="boxouter_nobottom">
<h2>Organization Criteria Information</h2>
     <s:form action="search/organization/search.action" id="searchOrganizationForm">
         <s:hidden name="rootKey"/>
        <div>
         <div class="boxouter" style="float:left;margin-right: 10px;">
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
                 <s:textfield label="%{getText('organization.name')}" name="criteria.organization.name" size="70"/>
                 <div class="clear"></div>
             </div>
         </div>
         <div style="float:left;margin-right: 10px;">
          <div class="boxouter" style="float:left;margin-right: 10px;">
          <h2>Address Information</h2>
              <div class="box_white">
                  <po:addressForm formNameBase="searchOrganizationForm" addressKeyBase="criteria.organization.postalAddress" address="${criteria.organization.postalAddress}" required="false"/>
                  <div class="clear"></div>
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
		    onclick="$('searchOrganizationForm').submit();" 
		    style="search" text="Search" 
		    id="submitSearchOrganizationForm"/>
	</po:buttonRow>
</div> 

<div class="boxouter">
   <h2><fmt:message key="organization.search.results"/></h2>
   <div id="organizationSearchResults">     
    <%@ include file="results.jsp" %>
   </div>
</div>
</body>
</html>