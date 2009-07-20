<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
	function submitform(orgid)
	{
		top.window.setorgid(orgid);
		top.window.loadDiv(orgid);
		window.top.hidePopWin(true); 
	}
	
	function callParentSubmit(orgid)
	{	
		top.window.setorgid(orgid);
		top.window.loadDiv(orgid);
		window.top.hidePopWin(true); 
	}
	
	
	function loadDiv() {
		var orgName = document.getElementById("orgNameSearch").value;
		//var orgNumber = document.getElementById("poOrganizations_orgSearchCriteria_nciOrgNumber").value;	
		var orgNumber;
		var orgCountry = document.getElementById("orgCountrySearch").value;
		var orgCity = document.getElementById("orgCitySearch").value;
		var orgZip = document.getElementById("orgZipSearch").value;
		var orgState = document.getElementById("orgStateSearch").value;
		var url = '/pa/protected/popupdisplayOrgList.action?orgName='+orgName+'&nciNumber='+orgNumber+'&countryName='+orgCountry+'&cityName='+orgCity+'&zipCode='+orgZip+'&stateName='+orgState;
		
	    var div = document.getElementById('getOrgs');   	   
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
    document.getElementById("orgNameSearch").value = '';
    document.getElementById("orgCitySearch").value = '';
    document.getElementById("orgStateSearch").value = '';
    document.getElementById("orgCountrySearch").value = 'USA';
    document.getElementById("orgZipSearch").value = '';
 }
    
</SCRIPT>

</head> 
<body>
<div class="box">
<s:form id="poOrganizations" name="poOrganizations" >
<h2>Search Organizations</h2>
<s:label name="orgErrorMessage"/>

<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name"> <fmt:message key="popUpOrg.name"/></label>
        </td>
 		<td>
 			<s:textfield id="orgNameSearch" name="orgSearchCriteria.name"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
<!-- 		<td scope="row" class="label">
            <label for="nciorgname"> <fmt:message key="popUpOrg.nciorgnumber"/></label>
        </td>
 		<td> 			
 			<s:textfield name="orgSearchCriteria.nciOrgNumber"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td> -->
 		
 		<td scope="row" class="label">
            <label for="country"> <fmt:message key="popUpOrg.country"/></label>
        </td>
        <td>
              	<s:select  
              	id = "orgCountrySearch"
                name="orgSearchCriteria.orgCountry" 
                list="countryList"  
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr>  
 		 <td scope="row" class="label">
            <label for="city"> <fmt:message key="popUpOrg.city"/></label>
        </td>
 		<td> 			
 			<s:textfield id="orgCitySearch" name="orgSearchCriteria.city"  maxlength="200" size="100"  cssStyle="width:200px" />
		</td>
 		<td scope="row" class="label">
            <label for="state"> State</label>
        </td>
 		<td>
 			<s:textfield id="orgStateSearch" name="orgSearchCriteria.state" maxlength="75" size="20"/><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font>
		</td>
		
	</tr>
	<tr>
 		 <td scope="row" class="label">
            <label for="zip"> <fmt:message key="popUpOrg.zip"/></label>
        </td>
 		<td>
 			<s:textfield id="orgZipSearch" name="orgSearchCriteria.zip" maxlength="75" size="20"/>
		</td>	
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
	<div id="getOrgs" align="center">	
	   	<jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>        	      
   	</div>
	  
</s:form>
</div>


</body>
</html>