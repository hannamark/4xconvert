<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/orgSearch.js'/>"></script>
<SCRIPT language="JavaScript">
	
	function createOrg(){
        var orgName = document.getElementById("orgName").value;
        var stAddress = document.getElementById("orgAddress").value;
        var city = document.getElementById("orgCity").value;
        var stateSelect = document.getElementById("orgStateSelect").value;
        var stateText = document.getElementById("orgStateText").value;
        var zip = document.getElementById("orgZip").value;
        var country = document.getElementById("orgCountry").value;
        var state = "";
        if (country == 'USA')
         state = stateSelect;
        else 
         state = stateText;
        var phone = document.getElementById("orgPhone").value;
        var email = document.getElementById("orgEmail").value;
        var orgUrl = document.getElementById("orgUrl").value;
        var tty = document.getElementById("orgTty").value;
        var fax = document.getElementById("orgFax").value;
        var url = '/registry/orgPopcreateOrganization.action?orgName='+orgName+'&orgStAddress='+stAddress+'&countryName='+country+'&cityName='+city+'&zipCode='+zip+'&stateName='+state+'&phoneNumber='+phone+'&email='+email+'&tty='+tty+'&url='+orgUrl+'&fax='+fax;
        var div = document.getElementById('getOrgs'); 
        div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="./images/loading.gif"/>&nbsp;Creating...</div>';
        ajaxCall(div, url);
    }
	function loadDiv() {
		var orgName = document.getElementById("orgNameSearch").value;		
		var orgCountry = document.getElementById("orgCountrySearch").value;
		var orgCity = document.getElementById("orgCitySearch").value;
		var orgZip = document.getElementById("orgZipSearch").value;	
		var ctepid = document.getElementById("orgCtepIdSearch").value;		
		var orgState = document.getElementById("orgStateSearch").value;
		var url = '/registry/orgPopdisplayOrgList.action?orgName='+orgName+'&countryName='+orgCountry+'&cityName='+orgCity+'&zipCode='+orgZip+'&ctepid='+ctepid+'&stateName='+orgState;
	    var div = document.getElementById('getOrgs');   	   
	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="./images/loading.gif"/>&nbsp;Loading...</div>';
	    ajaxCall(div, url);    
	}
</SCRIPT>

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
	<jsp:include page="/WEB-INF/jsp/nodecorate/displayAffOrgList.jsp"/>        	      
</div>
</s:form>
</div>
</body>
</html>