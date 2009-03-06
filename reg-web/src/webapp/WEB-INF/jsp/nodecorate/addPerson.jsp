<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Person</h2>
<table  class="form">  
   <tr>   		 	
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_firstName">First Name :</label><span class="required">*</span></td><td><s:textfield name="personDTO.firstName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_lastName">Last Name :</label><span class="required">*</span></td><td><s:textfield name="personDTO.lastName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
	<tr>   
		<td scope="row" class="label"><label for="poOrganizations_personDTO_preFix">Prefix :</label></td><td><s:textfield name="personDTO.preFix"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="poOrganizations_personDTO_middleName">Middle Name :</label></td><td><s:textfield name="personDTO.middleName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>   
	<tr>   
		<td scope="row" class="label"><label for="poOrganizations_personDTO_suffix">Suffix :</label></td><td><s:textfield name="personDTO.suffix"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
<td scope="row" class="label"><label for="poOrganizations_personDTO_streetAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield name="personDTO.streetAddress"  maxlength="200" size="100"  cssStyle="width:200px"  /></td>		
	</tr> 	
   <tr>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_city">City :</label><span class="required">*</span></td><td><s:textfield name="personDTO.city"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_state">State :<br><span class="tiny">(2-letter State/Province Code required for USA/Canada/Australia)</span></label></td><td><s:textfield name="personDTO.state"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
   <tr>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_zip">ZIP :</label><span class="required">*</span></td><td><s:textfield name="personDTO.zip"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_country">Country :</label><span class="required">*</span></td><td>
           	<s:select
             name="personDTO.country" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td> 		
   </tr>
   <tr>
       <td scope="row" class="label"><label for="poOrganizations_personDTO_email">Email :</label><span class="required">*</span></td><td><s:textfield name="personDTO.email"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
       <td scope="row" class="label"><label for="oOrganizations_personDTO_phone">Phone :</label></td><td><s:textfield name="personDTO.phone"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
   <tr>
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_url">URL :</label></td><td><s:textfield name="personDTO.url"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="poOrganizations_personDTO_tty">TTY :</label></td><td><s:textfield name="personDTO.tty"  maxlength="200" size="100"  cssStyle="width:200px" /></td> 		
   </tr>
   <tr> 
 		
 		<td scope="row" class="label"><label for="poOrganizations_personDTO_fax">Fax :</label></td><td><s:textfield name="personDTO.fax"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>		
</table>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="#" cssClass="btn" onclick="createPerson()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Search</span></span></s:a>          
       </li>
       </ul>   
  </del>
  </div>