<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Person</h2>
<table  class="form">  
   <tr>   		 	
 		<td scope="row" class="label"><label for="firstname">First Name :</label><span class="required">*</span></td><td><s:textfield name="personDTO.firstName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="lastname">Last Name :</label><span class="required">*</span></td><td><s:textfield name="personDTO.lastName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
	<tr>   
		<td scope="row" class="label"><label for="prefix">Prefix :</label></td><td><s:textfield name="personDTO.preFix"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="middlename">Middle Name :</label></td><td><s:textfield name="personDTO.middleName"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>   
	<tr>   
		<td scope="row" class="label"><label for="prefix">Suffix :</label></td><td><s:textfield name="personDTO.suffix"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
<td scope="row" class="label"><label for="staddress">Street Address :</label><span class="required">*</span></td><td><s:textfield name="personDTO.streetAddress"  maxlength="200" size="100"  cssStyle="width:200px"  /></td>		
	</tr> 	
   <tr>
 		<td scope="row" class="label"><label for="city">City :</label><span class="required">*</span></td><td><s:textfield name="personDTO.city"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="state">State :<br><span class="tiny">(2-letter State/Province Code required for USA/Canada/Australia)</span></label></td><td><s:textfield name="personDTO.state"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
   </tr>
   <tr>
 		<td scope="row" class="label"><label for="zip">Zip :</label><span class="required">*</span></td><td><s:textfield name="personDTO.zip"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
 		<td scope="row" class="label"><label for="country">Country :</label><span class="required">*</span></td><td>
           	<s:select
             name="personDTO.country" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td> 		
   </tr>
   <tr>
       <td scope="row" class="label"><label for="email">Email :</label><span class="required">*</span></td><td><s:textfield name="personDTO.email"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
       <td scope="row" class="label"><label for="state">Phone :</label></td><td><s:textfield name="personDTO.phone"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
	</tr>
   <tr>
 		<td scope="row" class="label"><label for="zip">URL :</label></td><td><s:textfield name="personDTO.url"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
		<td scope="row" class="label"><label for="state">TTY :</label></td><td><s:textfield name="personDTO.tty"  maxlength="200" size="100"  cssStyle="width:200px" /></td> 		
   </tr>
   <tr> 
 		
 		<td scope="row" class="label"><label for="zip">Fax :</label></td><td><s:textfield name="personDTO.fax"  maxlength="200" size="100"  cssStyle="width:200px" /></td>
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