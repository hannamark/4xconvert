<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Organization</h2>
<table  class="form">  
   <tr> 	
 		<td scope="row" class="label"><label for="createOrgName"> Organization Name :</label></td><td><s:textfield name="createOrg.name"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="createOrgAbr"> Organization Abbreviation :</label></td><td><s:textfield name="createOrg.abr"  maxlength="200" size="100"  cssStyle="width:200px" /></td> 		
 		 		
   </tr>
   <tr>
 		<td scope="row" class="label"><label for="createOrgStAddr">Street Address :</label></td><td><s:textfield name="createOrg.streetAddress"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   		<td scope="row" class="label"><label for="createOrgDlAddr">Delivery Address :</label></td><td><s:textfield name="createOrg.deliveryAddress"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
   <tr>        
 		<td scope="row" class="label"><label for="createOrgCity">City :</label></td><td><s:textfield name="createOrg.city"  maxlength="200" size="100"  cssStyle="width:200px" /></td>        
 		<td scope="row" class="label"><label for="createOrgState">State :</label></td><td><s:textfield name="createOrg.state"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
   <tr>     
 		<td scope="row" class="label"><label for="createOrgZip">Zip :</label></td><td><s:textfield name="createOrg.Zip"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="createOrgCountry">Country :</label></td><td>
           	<s:select
             name="createOrg.orgCountry" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="aaa" headerValue="--Select--" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr><td scope="row" class="label"><label for="createOrgPhoneNumber">Phone Number :</label></td><td><s:textfield name="createOrg.phoneNumber"  maxlength="200" size="100"  cssStyle="width:200px" /></td></tr>
</table>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="#" cssClass="btn" onclick="createOrg()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Search</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin(true);"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
       </li>
       </ul>   
  </del>
  </div>