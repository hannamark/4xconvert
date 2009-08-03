<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
	function submitform(persid, name)
	{	
		//top.window.loadPersDiv(persid, func);
		var email = document.getElementById(persid+"email")[document.getElementById(persid+"email").selectedIndex].value;
		var phone = document.getElementById(persid+"phone")[document.getElementById(persid+"phone").selectedIndex].value;
		top.window.setpersid(persid, name,email,phone);
		window.top.hidePopWin(true); 
	}
	 
	function callCreatePerson(persid, rolecode)
	{		
		top.window.loadPersDiv(persid, rolecode, 'add');
		window.top.hidePopWin(true); 
	}
	function loadDiv() {
		var title = document.getElementById("search_title").value;
		var orgId = document.getElementById("orgContactId").value;
		var url = '/registry/protected/ajaxorganizationGenericContactdisplayTitleList.action?title='+title+'&orgGenericContactIdentifier='+orgId;
	    var div = document.getElementById('getTitles');   	   
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
		document.getElementById("searchTitleJsp").style.display="";
		document.getElementById("createTitleJsp").style.display="none";
	}

	function setCreateFormVisible(){
		document.getElementById("searchTitleJsp").style.display="none";
		document.getElementById("createTitleJsp").style.display="";
	}
	function createTitle() {
		var email = document.getElementById("email").value;		
		var phone = document.getElementById("phone").value;		
        var createtitle = document.getElementById("create_title").value;
        var orgId = document.getElementById("orgContactId").value;
		
		var url = '/registry/protected/ajaxorganizationGenericContactcreate.action?title='+createtitle+'&orgGenericContactIdentifier='+orgId+'&email='+email+'&phone='+phone;
		var div = document.getElementById('getTitles');   	   
		div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Creating...</div>';
		ajaxCall(div, url);    
	}

	   function setFocusToFirstControl() {
	        for (var f=0; f < document.forms.length; f++) {
	            for(var i=0; i < document.forms[f].length; i++) {
	                var elt = document.forms[f][i];
	                if (elt.type != "hidden" && elt.disabled != true && elt.id != 'enableEnterSubmit') {
	                    try {
	                        elt.focus();
	                        return;
	                    } catch(er) {
	                    }
	                }
	            }
	        }
	    }
</SCRIPT> 
</head> 
<body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
<div class="box">
<s:form id="titles" name="titles" >
<s:hidden name="orgContactId" id="orgContactId"></s:hidden>
<div id="searchTitleJsp">
	<jsp:include page="/WEB-INF/jsp/nodecorate/searchTitle.jsp"/>
</div>
<div id="createTitleJsp" style="display:none">
	<jsp:include page="/WEB-INF/jsp/nodecorate/addTitle.jsp"/>
</div>
<div class="line"></div>
	<div class="box" align="center">
		<div id="getTitles">	
			 <jsp:include page="/WEB-INF/jsp/nodecorate/displayTitlesList.jsp"/>
   	</div>
</div>
</s:form>
</div>

</body>
</html>