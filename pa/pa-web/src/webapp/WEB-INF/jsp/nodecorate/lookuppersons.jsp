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
		var firstName = document.getElementById("personFirstName").value;
		var lastName = document.getElementById("personLastName").value;
		var persCountry = document.getElementById("personCountry").value;
		var persCity = document.getElementById("personCity").value;
		var persZip = document.getElementById("personZip").value;
		var persState = document.getElementById("personState").value;		
		//
		var url = '/pa/protected/popupdisplayPersonsList.action?firstName='+firstName+'&lastName='+lastName+'&countryName='+persCountry+'&cityName='+persCity+'&zipCode='+persZip+'&stateName='+persState;
	    var div = document.getElementById('getPersons');   	   
	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
	    var aj = new Ajax.Updater(div,url, {
	        asynchronous: true,
	        method: 'get',
	        evalScripts: false
	    });
	    return false;
	}

    function formReset(){
    document.forms[0].reset();
    document.getElementById("personFirstName").value = '';
    document.getElementById("personLastName").value = '';
    document.getElementById("personCity").value = '';
    document.getElementById("personCountry").value = 'USA';
    document.getElementById("personState").value = '';
    document.getElementById("personZip").value = '';
 }
</SCRIPT>

</head> 
<body>
<div class="box">
<s:form id="poOrganizations" name="poOrganizations" >
<h2>Search Persons</h2>
<table  class="form">  
   	<tr>
   		<td scope="row" class="label"><label for="firstname">First Name:</label></td>
 		<td><s:textfield id="personFirstName" name="perSearchCriteria.firstName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="lastname"> Last Name:</label></td>
 		<td><s:textfield id="personLastName" name="perSearchCriteria.lastName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
   	<tr>
   		<td scope="row" class="label"><label for="city">City:</label></td>
 		<td><s:textfield id="personCity" name="perSearchCriteria.city"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="state">State:</label></td>
 		<td><s:textfield id="personState" name="perSearchCriteria.state"  maxlength="200" size="100"  cssStyle="width:200px" /><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font></td>
	</tr>		
   	<tr>
   		<td scope="row" class="label"><label for="country">Country:</label></td>
        <td>
              	<s:select 
              	id="personCountry" 
                name="perSearchCriteria.country" 
                list="countryList"  
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>	
		<td scope="row" class="label"><label for="zip">Zip:</label></td>
 		<td><s:textfield id="personZip" name="perSearchCriteria.zip"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>	
	
</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                     <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>  
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