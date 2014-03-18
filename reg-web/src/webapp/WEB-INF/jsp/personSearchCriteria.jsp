<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script type="text/javascript" language="javascript">
    function handleAction() {
         $('#personSearchForm').submit();
    }
    function resetValues() {
    	$('#poID').val("");
     	$('#ctepID').val("");
     	$('#firstName').val("");
     	$('#lastName').val("");
     	$('#affiliation').val("");
     	$('#functionalRole').val("");    
    	$('#searchResults').innerHTML='';
    }

    document.onkeypress = runEnterScript;
    function runEnterScript(e) {
        var KeyID = (window.event) ? event.keyCode : e.keyCode;
        if (KeyID == 13) {
            handleAction();
            return false;
        }
    }
    
    function displayPersonDetails(personID) {
    	var width = 700;
    	var height = 550;
    	/*if (Prototype.Browser.IE) {
    		width = 670;
            height = 500;            		
    	}*/
    	showPopWin('personsSearchshowDetailspopup.action?personID='+personID, width, height, '', 'Person Details');            	
    }
    
</script>

<!-- main content begins-->
<div class="container">
  <ul class="nav nav-tabs">
    <li><a href="<s:url action='searchTrial.action' />"><i class="fa-flask"></i><fmt:message key="search.trial.page.header"/></a></li>
    <li class="active"><a href="#search-persons" data-toggle="tab"><i class="fa-user"></i><fmt:message key="person.search.header"/></a></li>
    <li><a href="<s:url action='organizationsSearch.action' />"><i class="fa-sitemap"></i><fmt:message key="organization.search.header"/></a></li>
  </ul>
  
  <!-- main content begins-->
<div class="tab-content">
 <div class="tab-pane fade active in " id="search-persons">
        <c:set var="topic" scope="request" value="searchperson"/>
        <div class="box" id="filters">
            <s:form id="personSearchForm"  action="personsSearchquery.action">        
                 <reg-web:failureMessage/>
                 <reg-web:sucessMessage/>
                 
                <p align="center" class="info">
                   Enter information for at least one of the criteria and then click Search. 
                   The maximum number of results returned for any search is 500 records. If necessary, 
                   limit your search by providing additional search criteria.
                </p>                   
                 
                <table class="form fluid_width">
                   
                    <tr>
                        <td  scope="row" class="label">
                            <label for="poID"><fmt:message key="person.search.poID"/></label>
                        </td>
                        <td>
                            <s:textfield id="poID" name="criteria.id" maxlength="10" cssStyle="width:294px"  />
                        </td>
                        <td  scope="row" class="label">
                            <label for="functionalRole"> <fmt:message key="person.search.role"/></label>
                        </td>
                        <td>
                             <s:select name="criteria.functionalRole" id="functionalRole" cssStyle="width:300px;"
                                list="#{'Healthcare Provider':'Healthcare Provider',
                                'Clinical Research Staff':'Clinical Research Staff',
                                'Organizational Contact':'Organizational Contact'}" 
                                headerKey="" headerValue="Any" value="criteria.functionalRole" />
                        </td>
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="ctepID"><fmt:message key="person.search.ctepID"/></label>
                        </td>
                        <td>
                            <s:textfield id="ctepID" name="criteria.ctepId" maxlength="75" cssStyle="width:294px"  />
                        </td>                        
                    </tr>                   
                    <tr>
                      
                        <td  scope="row" class="label">
                            <label for="firstName"><fmt:message key="person.search.firstName"/></label>
                        </td>
                        <td>
                            <s:textfield id="firstName" name="criteria.firstName" maxlength="10" cssStyle="width:294px"  />
                        </td>                        
                    </tr>  
                    <tr>
                        <td  scope="row" class="label">
                            <label for="lastName"><fmt:message key="person.search.lastName"/></label>
                        </td>
                        <td>
                            <s:textfield id="lastName" name="criteria.lastName" maxlength="200" cssStyle="width:294px"  />
                        </td>                       
                    </tr>
                    <tr>
                        <td  scope="row" class="label">
                            <label for="affiliation"><fmt:message key="person.search.affiliation"/></label>
                        </td>
                        <td>
                            <s:textfield id="affiliation" name="criteria.affiliation" maxlength="200" cssStyle="width:294px"  />
                        </td>                        
                    </tr>                                      
                </table>
                
                <div class="bottom">
            		<button type="button" class="btn btn-icon btn-primary" onclick="handleAction()"> <i class="fa-search"></i>Search </button>
            		<button type="button" class="btn btn-icon btn-default" onclick="resetValues();return false"><i class="fa-repeat"></i>Reset</button>
          		</div>
            </s:form>
        </div>
        <div class="line"></div>
        <div id="searchResults">        
        <s:if test="results!=null && results.empty">
            <div align="center">
            No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria.
            </div>
        </s:if>        
		<s:if test="results!=null && !results.empty">
		    <h2><fmt:message key="person.search.results"/></h2>		
		    <s:set name="persons" value="results" scope="request" />
		    <display:table class="data" sort="list" pagesize="10" uid="row" name="persons" export="false"
		        requestURI="personsSearchquery.action">
		        <display:setProperty name="basic.msg.empty_list"
		            value="No Persons found. Please verify search criteria and/or broaden your search by removing one or more search criteria." />
		        <display:column escapeXml="false" title="PO-ID" headerClass="sortable" sortable="true">
		              <a href="javascript:void(0);" onclick="displayPersonDetails(<c:out value="${row.id}"/>)"><c:out value="${row.id}"/></a>
		        </display:column>
		        <display:column escapeXml="true" title="CTEP ID" property="ctepId" headerClass="sortable"  sortable="true"/>
			    <display:column decorator="gov.nih.nci.registry.decorator.HtmlEscapeDecorator" escapeXml="false" title="First Name" property="firstName"  headerClass="sortable" sortable="true"/>			    		    
			    <display:column decorator="gov.nih.nci.registry.decorator.HtmlEscapeDecorator" escapeXml="false" title="Last Name" property="lastName" sortable="true" headerClass="sortable" />			    
			    <display:column escapeXml="true" title="Email" property="email" sortable="true"/>
		        <display:column escapeXml="false" title="Organization Affiliation" sortable="false">
		            <c:forEach items="${row.organizations}" var="org">
		                <c:out value="${org.name.part[0].value}" />
		                <br />
		            </c:forEach>
		        </display:column>
                <display:column escapeXml="false" title="Role" sortable="false">
                    <c:forEach items="${row.roles}" var="role">
                        <c:out value="${role}" />
                        <br />
                    </c:forEach>
                </display:column>		        
		        <display:column escapeXml="true" title="City" property="city" sortable="true"/>
		        <display:column escapeXml="true" title="State" property="state" sortable="true"/>
		        
		    </display:table>		
		</s:if>        
        </div>     
        
        </div></div></div>   
