<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Organization</h2>
<table  class="form">  
   <tr> 	
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgName;"> Organization Name :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgName;"  maxlength="159" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgStreetAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgStreetAddress"  maxlength="254" size="100"  cssStyle="width:200px" /></td>		
   </tr>
   <tr>        
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgCity;">City :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgCity;"  maxlength="50" size="100"  cssStyle="width:200px" /></td>        
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgState">State :<br></label></td><td><s:textfield name="createOrg.orgState"  maxlength="50" size="100"  cssStyle="width:200px" /><br><span class="tiny">(2-letter state code for USA/Canada and 2 or 3-letter state code for Australia.)</span></td>
   </tr>
   <tr>     
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgZip;">ZIP :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgZip;"  maxlength="20" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgCountry;">Country :</label><span class="required">*</span></td><td>
           	<s:select
             name="createOrg.orgCountry;" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr>
		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgEmail;">Email :</label><span class="required">*</span></td><td><s:textfield name="createOrg.orgEmail;"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgPhone">Phone Number :</label></td><td><s:textfield name="createOrg.orgPhone"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgURL;">URL :</label></td><td><s:textfield name="createOrg.orgURL;"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgTTY;">TTY :</label></td><td><s:textfield name="createOrg.orgTTY;"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label for="poOrganizations_createOrg_orgFax;">Fax Number :</label></td><td><s:textfield name="createOrg.orgFax;"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
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