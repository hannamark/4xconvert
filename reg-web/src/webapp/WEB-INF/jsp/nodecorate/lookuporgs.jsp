<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/orgSearch.js'/>"></script>
        <script type="text/javascript" language="javascript">
        		
        	function createOrg() {
                var url = '/registry/protected/popupcreateOrganization.action';
                var country = $("orgCountry").value;
                var params = {
                    cityName: $("orgCity").value,
                    countryName: country,
                    email: $("orgEmail").value,
                    fax: $("orgFax").value,
                    orgName: $("orgName").value,
                    orgStAddress: $("orgAddress").value,
                    phoneNumber: $("orgPhone").value,
                    stateName: ((country == 'USA') ? $("orgStateSelect").value : $("orgStateText").value),
                    tty: $("orgTty").value,
                    url: $("orgUrl").value,
                    zipCode: $("orgZip").value
                };
            
                var div = $('getOrgs'); 
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
        	function loadDiv() {
        		var url = '/registry/protected/popupdisplayOrgList.action';
        		var params = {
                    cityName: $("orgCitySearch").value,
                    countryName: $("orgCountrySearch").value,
                    ctepid: $("orgCtepIdSearch").value,
                    orgName: $("orgNameSearch").value,
                    stateName: $("orgStateSearch").value,
                    zipCode: $("orgZipSearch").value
                };
        	    var div = $('getOrgs');   	   
        	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
        	    var aj = callAjaxPost(div, url, params);   
        	}
        </script>
    </head> 
    <body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
        <div class="box">
            <s:form id="poOrganizations" name="poOrganizations" >
                <s:label name="orgErrorMessage"/>
                <div id="searchOrgJsp">
                	<jsp:include page="/WEB-INF/jsp/nodecorate/searchOrgForm.jsp"/>
                </div>
                <div id="createOrgJsp" style="display:none">
                	<jsp:include page="/WEB-INF/jsp/nodecorate/createOrg.jsp"/>
                </div>
                <div id="getOrgs" align="center">	
                	<jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>        	      
                </div>
            </s:form>
        </div>
    </body>
</html>