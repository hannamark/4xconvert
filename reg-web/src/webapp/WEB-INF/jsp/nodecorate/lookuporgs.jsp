<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
	function submitform(orgid)
	{		
	
		top.window.setorgid(orgid);
		window.top.hidePopWin(true); 
	}	
	function createOrg(){
		var orgName = document.getElementById("poOrganizations_createOrg_orgName;").value;
		var stAddress = document.getElementById("poOrganizations_createOrg_orgStreetAddress").value;
		var city = document.getElementById("poOrganizations_createOrg_orgCity;").value;
		var state = document.getElementById("poOrganizations_createOrg_orgState").value;
		var zip = document.getElementById("poOrganizations_createOrg_orgZip;").value;
		var country = document.getElementById("poOrganizations_createOrg_orgCountry;").value;
		var phone = document.getElementById("poOrganizations_createOrg_orgPhone").value;
		var email = document.getElementById("poOrganizations_createOrg_orgEmail;").value;
		var orgUrl = document.getElementById("poOrganizations_createOrg_orgURL;").value;
		var tty = document.getElementById("poOrganizations_createOrg_orgTTY;").value;
		var fax = document.getElementById("poOrganizations_createOrg_orgFax;").value;
		var url = '/registry/protected/popupcreateOrganization.action?orgName='+orgName+'&orgStAddress='+stAddress+'&countryName='+country+'&cityName='+city+'&zipCode='+zip+'&stateName='+state+'&phoneNumber='+phone+'&email='+email+'&tty='+tty+'&url='+orgUrl+'&fax='+fax;
		var div = document.getElementById('getOrgs'); 
		div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
		ajaxCall(div, url);
	}
	function loadDiv() {
		var orgName = document.getElementById("poOrganizations_orgSearchCriteria_orgName").value;		
		var orgCountry = document.getElementById("poOrganizations_orgSearchCriteria_orgCountry").value;
		var orgCity = document.getElementById("poOrganizations_orgSearchCriteria_orgCity").value;
		var orgZip = document.getElementById("poOrganizations_orgSearchCriteria_orgZip").value;	
		var ctepid = document.getElementById("poOrganizations_orgSearchCriteria_ctepId").value;	
		var url = '/registry/protected/popupdisplayOrgList.action?orgName='+orgName+'&countryName='+orgCountry+'&cityName='+orgCity+'&zipCode='+orgZip+'&ctepid='+ctepid;
	    var div = document.getElementById('getOrgs');   	   
	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';
	    ajaxCall(div, url);    
	}
	function ajaxCall(div, url){
	    var aj = new Ajax.Updater(div,url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;	
	}
	function setSearchFormVisible(){
		document.getElementById("searchOrgJsp").style.display="";
		document.getElementById("createOrgJsp").style.display="none";
	}

	function setCreateFormVisible(){
		document.getElementById("searchOrgJsp").style.display="none";
		document.getElementById("createOrgJsp").style.display="";
	}	
	function closePopup(){
		window.top.hidePopWin(true);
	}
</SCRIPT>

</head> 
<body>
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