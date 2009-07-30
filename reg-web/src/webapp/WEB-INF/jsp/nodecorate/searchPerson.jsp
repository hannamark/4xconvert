<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<SCRIPT LANGUAGE="JavaScript">
function formReset(){
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('city').value = '';
    document.getElementById('state').value = '';
    document.getElementById('country').value = 'USA';
    document.getElementById('zip').value = '';
    document.getElementById('email').value = '';
    document.getElementById('ctepid').value = '';
}
</SCRIPT>
</head>
<p align="center" class="info">
    Type a string of characters in any of the text fields in the upper frame 
    or in CTEP Identifier field  in the lower frame.
    <br>
    Please do not use wildcard characters.<br>
         
</p>
<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label"><label for="poOrganizations_firstName">First Name:</label></td>
 		<td><s:textfield name="personDTO.firstName" id="firstName" maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_lastName"> Last Name :</label></td>
 		<td><s:textfield name="personDTO.lastName"  id="lastName" maxlength="200" size="100"  cssStyle="width:200px" /></td> 		
	</tr>	
   	<tr>
   		<td scope="row" class="label"><label for="city">City:</label></td>
 		<td><s:textfield name="personDTO.city"  id="city" maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="state">State:</label></td>
 		<td><s:textfield name="personDTO.state"  id="state" maxlength="200" size="100"  cssStyle="width:200px" /><br><font size="1"><span class="info">please enter two letter identifier for US states for ex: 'MD' for Maryland</span></font></td>
	</tr>	
   	<tr>
   		<td scope="row" class="label"><label for="country">Country:</label></td>
        <td>
              	<s:select  
                name="personDTO.country" 
                list="countryList"  
                id = "country"
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>	
		<td scope="row" class="label"><label for="zip">Zip:</label></td>
 		<td><s:textfield name="personDTO.zip"  id="zip" maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>		
	<tr>  
	</tr>
	<tr>  
  		<td scope="row" class="label"><label for="poOrganizations_email"> Email :</label></td>
 		<td><s:textfield name="personDTO.email" id="email" maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr><td colspan="2"> <hr></td> </tr>
		<tr>  
  		<td scope="row" class="label">
            <label for="poOrganizations_ctepId"> CTEP Identifier :</label>
        </td>
 		<td> 			
 			<s:textfield name="ctepId" id="ctepId" maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	
	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                   <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Person</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>     
                   </li>
               </ul>   
          </del>
     </div> 