<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Organization</h2>
<table  class="form">  
   <tr> 	
 		<td scope="row" class="label"><label> Organization Name :</label><span class="required">*</span></td><td><s:textfield  id = "orgName"  name="orgName" maxlength="159" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label>Street Address :</label><span class="required">*</span></td><td><s:textfield id = "orgAddress" name="orgAddress"  maxlength="254" size="100"  cssStyle="width:200px" /></td>		
   </tr>
   <tr>        
 		<td scope="row" class="label"><label>City :</label><span class="required">*</span></td><td><s:textfield id="orgCity" name="orgCity" maxlength="50" size="100"  cssStyle="width:200px" /></td>        
 		<td scope="row" class="label"><label>State :<br></label></td><td><s:textfield id ="orgState" name="orgState"  maxlength="50" size="100"  cssStyle="width:200px" /><br><span class="tiny">(2-letter state code for USA/Canada and 2 or 3-letter state code for Australia.)</span></td>
   </tr>
   <tr>     
 		<td scope="row" class="label"><label>ZIP :</label><span class="required">*</span></td><td><s:textfield id="orgZip" name="orgZip" maxlength="20" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label>Country :</label><span class="required">*</span></td><td>
           	<s:select
             id = "orgCountry"
             name="orgCountry" 
             list="treatmentSiteSearchCriteria.countries" 
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr>
		<td scope="row" class="label"><label>Email :</label><span class="required">*</span></td><td><s:textfield id="orgEmail" name="orgEmail"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label>Phone Number :</label></td><td><s:textfield  id="orgPhone"  name="orgPhone" maxlength="30" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>URL :</label></td><td><s:textfield id="orgUrl" name="orgURL" maxlength="254" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label>TTY :</label></td><td><s:textfield id="orgTty" name="orgTtY"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
	</tr>
	<tr>
		<td scope="row" class="label"><label>Fax Number :</label></td><td><s:textfield id="orgFax"  name="orgFax"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
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