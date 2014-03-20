<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript">	
	
	function handleAction() {
	    $('organizationSearchForm').submit();
	}
	function resetValues() {
		$('organizationSearchForm').getElements().each(function (el) {
			if (el.type!='button') {
				el.setValue('');
			}
		});
		$('searchResults').innerHTML='';
	}

    document.onkeypress = runEnterScript;
    function runEnterScript(e) {
        var KeyID = (window.event) ? event.keyCode : e.keyCode;
        if (KeyID == 13) {
            handleAction();
            return false;
        }
    }
    
    function displayOrgDetails(orgID) {
    	var width = 650;
    	var height = 450;
    	if (Prototype.Browser.IE) {
    		width = 670;
            height = 500;            		
    	}
    	showPopWin('organizationsSearchshowDetailspopup.action?orgID='+orgID, width, height, '', 'Organization Details');            	
    }
    
</script>

<!-- main content begins-->
  <div class="container">
  <ul class="nav nav-tabs">
    <li><a href="<s:url action='searchTrial.action' />" ><i class="fa-flask"></i><fmt:message key="search.trial.page.header"/></a></li>
    <li><a href="<s:url action='personsSearch.action' />" ><i class="fa-user"></i><fmt:message key="person.search.header"/></a></li>
    <li class="active"><a href="#search-organizations" data-toggle="tab" ><i class="fa-sitemap"></i><fmt:message key="organization.search.header"/></a></li>
  </ul>
  
  <!-- main content begins-->
<div class="tab-content">
 <div class="tab-pane fade active in " id="search-organizations">
	<c:set var="topic" scope="request" value="searchorganization"/>
    <s:form id="organizationSearchForm" action="organizationsSearchquery.action" cssClass="form-horizontal" role="form">       
       <reg-web:failureMessage/>
       <reg-web:sucessMessage/>
         
    	<p class="mb20"><fmt:message key="search.instructions"/></p>                
		<div class="row">              
    	<div class="col-xs-6">
          <h3 class="heading"><span><fmt:message key="organization.search.idinfoHeader"/></span></h3>
          
          <div class="form-group">
            <label for="poID" class="col-xs-4 control-label"><fmt:message key="organization.search.poID"/></label>
            <div class="col-xs-8">
              <s:textfield id="poID" name="criteria.identifier" cssClass="form-control"  />
            </div>
          </div>
          <div class="form-group">
            <label for="ctepID" class="col-xs-4 control-label"><fmt:message key="organization.search.ctepID"/></label>
            <div class="col-xs-8">
               <s:textfield id="ctepID" name="criteria.ctepId" cssClass="form-control" />
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-xs-4 control-label"><fmt:message key="organization.search.name"/></label>
            <div class="col-xs-8">
              <s:textfield id="name" name="criteria.name" cssClass="form-control" />
            </div>
          </div>
          <div class="form-group">
            <label for="family" class="col-xs-4 control-label"><fmt:message key="organization.search.family"/></label>
            <div class="col-xs-8">
               <s:textfield id="family" name="criteria.familyName" maxlength="200" cssClass="form-control" />
            </div>
          </div>
        </div>
        <div class="col-xs-6">
          <h3 class="heading"><span><fmt:message key="organization.search.addressHeader"/></span></h3>
          <div class="form-group">
            <label for="country" class="col-xs-4 control-label"><fmt:message key="organization.search.country"/></label>
            <div class="col-xs-8">
              <s:select name="criteria.country" id="country" 
                    list="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getCountries()" listKey="alpha3"
                    listValue="name" headerKey="" headerValue="" value="criteria.country"  cssClass="form-control"/>
            </div>
          </div>
          <div class="form-group">
            <label for="city" class="col-xs-4 control-label"><fmt:message key="organization.search.city"/></label>
            <div class="col-xs-8">
              <s:textfield id="city" name="criteria.city" cssClass="form-control" />
            </div>
          </div>
          <div class="form-group">
            <label for="state" class="col-xs-4 control-label"><fmt:message key="organization.search.state"/></label>
            <div class="col-xs-8">
              <s:select name="criteria.state" id="state"
                    list="@gov.nih.nci.pa.enums.USStateCode@values()" listKey="name"
                    listValue="code" headerKey="" headerValue="" value="criteria.state" cssClass="form-control" />
            </div>
          </div>
          <div class="form-group">
            <label for="zip-code" class="col-xs-4 control-label"><fmt:message key="organization.search.zip"/></label>
            <div class="col-xs-8">
              <s:textfield id="zip" name="criteria.zip" maxlength="10" cssClass="form-control" />
            </div>
          </div>
          <div class="form-group">
            <label for="organization-type" class="col-xs-4 control-label"><fmt:message key="organization.search.functionalRole"/></label>
            <div class="col-xs-8">
              <s:select name="criteria.functionalRole" id="functionalRole" 
                    list="#{'Research Organization':'Lead Organization','Healthcare Facility':'Participating Site'}" 
                    headerKey="" headerValue="Both" value="criteria.functionalRole" cssClass="form-control"/>
            </div>
          </div>
        </div>  
       </div>
        <div class="bottom">
    		<button type="button" class="btn btn-icon btn-primary" onclick="handleAction()"> <i class="fa-search"></i>Search </button>
    		<button type="button" class="btn btn-icon btn-default" onclick="resetValues();return false"><i class="fa-repeat"></i>Reset</button>
  		</div>
        </s:form>
        <div class="line"></div>
        <div id="searchResults">        
	        <s:if test="results!=null && results.empty">
	            <div align="center">
	            No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria.
	            </div>
	        </s:if>        
			<s:if test="results!=null && !results.empty">
			    <h2><fmt:message key="organization.search.results"/></h2>		
			    <s:set name="orgs" value="results" scope="request" />
			    <display:table class="data" sort="list" pagesize="10" uid="row" name="orgs" export="false"
			        requestURI="organizationsSearchquery.action">
			        <display:setProperty name="basic.msg.empty_list"
			            value="No Organizations found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
			        <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
			          <a href="javascript:void(0);" onclick="displayOrgDetails(<c:out value="${row.id}"/>)"><c:out value="${row.id}"/></a>
			        </display:column>
			        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
			        <display:column title="Name" headerClass="sortable" sortable="true">
			            <c:out value="${row.name}"/>
			        </display:column>
			        <display:column escapeXml="false" title="Family Name" sortable="false">
			            <c:forEach items="${row.families}" var="family">
			                <c:out value="${family.value}" />
			                <br />
			            </c:forEach>
			        </display:column>
			        <display:column escapeXml="true" title="City" property="city" headerClass="sortable"  sortable="true"/>
			        <display:column escapeXml="true" title="State" property="state" headerClass="sortable"  sortable="true"/>
			        <display:column escapeXml="true" title="Country" property="country" headerClass="sortable"  sortable="true"/>
			        <display:column escapeXml="true" title="Zip" property="zip" headerClass="sortable"  sortable="true"/>
			        
			    </display:table>		
			</s:if>        
        </div>        
     </div>
  </div>
  </div>