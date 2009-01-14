<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Organization</h2>
<table  class="form">  
   <tr> 	
 		<td scope="row" class="label"><label for="createOrgName"> Organization Name :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgName;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="createOrgStAddr">Street Address :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgStreetAddress"  maxlength="200" size="100"  cssStyle="width:200px" /></td>		
   </tr>
   <tr>        
 		<td scope="row" class="label"><label for="createOrgCity">City :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgCity;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>        
 		<td scope="row" class="label"><label for="createOrgState">State :<br><span class="info">(2 digit state code required for US)</span></label></td><td><s:textfield name="createOrg.orgState"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
   <tr>     
 		<td scope="row" class="label"><label for="createOrgZip">Zip :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgZip;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="createOrgCountry">Country :</label><span class="required">*</span></td><td>
           	<s:select
             name="createOrg.orgCountry;" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr>
		<td scope="row" class="label"><label for="createOrgEmailAddress">Email :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgEmail;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="createOrgPhoneNumber">Phone Number :</label></td><td><s:textfield name="createOrg.orgPhone"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="createOrgurl">URL :</label></td><td><s:textfield name="createOrg.orgURL;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="createOrgTTY">TTY :</label></td><td><s:textfield name="createOrg.orgTTY;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="createOrgFax">Fax Number :</label></td><td><s:textfield name="createOrg.orgFax;"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>		
</table>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="#" cssClass="btn" onclick="createOrg()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Search</span></span></s:a>
       </li>
       </ul>   
  </del>
  </div>