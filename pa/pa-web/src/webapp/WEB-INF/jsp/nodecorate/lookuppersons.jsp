<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
	function submitform(persid)
	{	
	
		top.window.setpersid(persid);
		top.window.loadPersDiv(persid, func);
		window.top.hidePopWin(true); 
	}
	 
	function callCreatePerson(persid, rolecode)
	{		
		top.window.setpersid(persid);
		top.window.loadPersDiv(persid, rolecode, 'add');
		window.top.hidePopWin(true); 
	}
	function loadDiv() {
		var firstName = document.forms[0].firstName.value;
		var lastName = document.forms[0].lastName.value;      
		var url = '/pa/protected/popupdisplayPersonsList.action?firstName='+firstName+'&lastName='+lastName;
	    var div = document.getElementById('getPersons');   	   
	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
	    var aj = new Ajax.Updater(div,url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}

</SCRIPT> 
</head> 
<body>
<div class="box">
<s:form id="poOrganizations" name="poOrganizations" >
<h2>Search Persons</h2>
<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name">First Name:</label>
        </td>
 		<td>
 			<s:textfield name="firstName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr>  
  		<td scope="row" class="label">
            <label for="nciorgname"> Last Name:</label>
        </td>
 		<td> 			
 			<s:textfield name="lastName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                   </li>
               </ul>   
          </del>
     </div>  
</s:form>
</div>
<div class="line"></div>
	<div class="box" align="center">
		<div id="getPersons">	
			 <jsp:include page="/WEB-INF/jsp/nodecorate/displayPersonsList.jsp"/>
	   	</div>
	</div>
</body>
</html>