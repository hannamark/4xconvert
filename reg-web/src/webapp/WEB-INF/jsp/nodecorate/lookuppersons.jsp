<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
	function submitform(persid)
	{	
		//top.window.loadPersDiv(persid, func);
		top.window.setpersid(persid);
		window.top.hidePopWin(true); 
	}
	 
	function callCreatePerson(persid, rolecode)
	{		
		top.window.loadPersDiv(persid, rolecode, 'add');
		window.top.hidePopWin(true); 
	}
	function loadDiv() {
		var firstName = document.forms[0].firstName.value;
		var lastName = document.forms[0].lastName.value;      
		var url = '/registry/protected/popupdisplayPersonsList.action?firstName='+firstName+'&lastName='+lastName;
	    var div = document.getElementById('getPersons');   	   
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
		document.getElementById("searchPersJsp").style.display="";
		document.getElementById("createPersJsp").style.display="none";
	}

	function setCreateFormVisible(){
		document.getElementById("searchPersJsp").style.display="none";
		document.getElementById("createPersJsp").style.display="";
	}
	function createPerson() {
		var fname = document.getElementById("poOrganizations_personDTO_firstName").value;		
		var lname = document.getElementById("poOrganizations_personDTO_lastName").value;
		var prefx = document.getElementById("poOrganizations_personDTO_preFix").value;
		var mname = document.getElementById("poOrganizations_personDTO_middleName").value;
		var stadd = document.getElementById("poOrganizations_personDTO_streetAddress").value;
		var city = document.getElementById("poOrganizations_personDTO_city").value;
		var st = document.getElementById("poOrganizations_personDTO_state").value;
		var zip = document.getElementById("poOrganizations_personDTO_zip").value;
		var ct = document.getElementById("poOrganizations_personDTO_country").value;
		var url = '/registry/protected/popupcreatePerson.action?firstName='+fname+'&lastName='+lname+'&preFix='+prefx+'&midName='+mname+'&streetAddr='+stadd+'&city='+city+'&state='+st+'&zip='+zip+'&country='+ct;
		var div = document.getElementById('getPersons');   	   
		div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
		ajaxCall(div, url);    
	}
</SCRIPT> 
</head> 
<body>
<div class="box">
<s:form id="poOrganizations" name="poOrganizations" >

<div id="searchPersJsp">
	<jsp:include page="/WEB-INF/jsp/nodecorate/searchPerson.jsp"/>
</div>
<div id="createPersJsp" style="display:none">
	<jsp:include page="/WEB-INF/jsp/nodecorate/addPerson.jsp"/>
</div>
<div class="line"></div>
	<div class="box" align="center">
		<div id="getPersons">	
			 <jsp:include page="/WEB-INF/jsp/nodecorate/displayPersonsList.jsp"/>
   	</div>
</div>
</s:form>
</div>

</body>
</html>