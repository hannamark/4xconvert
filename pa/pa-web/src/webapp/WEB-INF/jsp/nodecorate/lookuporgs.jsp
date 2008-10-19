<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

<SCRIPT language="JavaScript">
	function submitform(orgid)
	{
		
		top.window.loadDiv(orgid);
		window.top.hidePopWin(true); 
	}
	
	function callParentSubmit(orgid)
	{	
		top.window.loadDiv(orgid);
		window.top.hidePopWin(true); 
	}
	
	
	function loadDiv() {
		var orgName = document.forms[0].orgName.value;
		var nciOrgName = document.forms[0].nciOrgName.value;
        var countryName = document.forms[0].countryName.value;
        var cityName = document.forms[0].cityName.value;    
        var zipCode = document.forms[0].zipCode.value;
		var url = '/pa/protected/popupdisplayOrgList.action?orgName='+orgName+'&nciOrgName='+nciOrgName+'&countryName='+countryName+'&cityName='+cityName+'&zipCode='+zipCode;
	    var div = document.getElementById('getOrgs');   	   
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
<h2>Search Organizations</h2>


<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name"> <fmt:message key="popUpOrg.name"/></label>
        </td>
 		<td>
 			<s:textfield name="orgName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
 		<td scope="row" class="label">
            <label for="nciorgname"> <fmt:message key="popUpOrg.nciorgnumber"/></label>
        </td>
 		<td> 			
 			<s:textfield name="nciOrgName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
 		<td scope="row" class="label">
            <label for="country"> <fmt:message key="popUpOrg.country"/></label>
        </td>
        <td>
             <s:select  
                name="countryName" 
                list="countryRegDTO"  
                listKey="id" listValue="name"  />
        </td>		
	</tr>
	<tr>  
 		 <td scope="row" class="label">
            <label for="city"> <fmt:message key="popUpOrg.city"/></label>
        </td>
 		<td> 			
 			<s:textfield name="cityName"  maxlength="200" size="100"  cssStyle="width:200px" />
		</td>
 		 <td scope="row" class="label">
            <label for="zip"> <fmt:message key="popUpOrg.zip"/></label>
        </td>
 		<td>
 			<input type="text" name="zipCode" maxlength="75" size="20">
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
		<div id="getOrgs">	
		   	<jsp:include page="/WEB-INF/jsp/nodecorate/displayOrgList.jsp"/>        	      
	   	</div>
	</div>
</body>
</html>