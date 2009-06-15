<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Person Details</h2>
<table  class="form">  
  <tr>              
    <td scope="row" class="label"><label for="poOrganizations_personDTO_firstName">First Name :</label><span class="required">*</span></td><td><s:textfield name="firstName"  maxlength="50" size="100"  cssStyle="width:170px" /></td>
    <td scope="row" class="label"><label for="poOrganizations_personDTO_lastName">Last Name :</label><span class="required">*</span></td><td><s:textfield name="lastName"  maxlength="50" size="100"  cssStyle="width:170px" /></td>
    <td scope="row" class="label"><label for="poOrganizations_personDTO_middleName">Middle Name :</label></td><td><s:textfield name="middleName"  maxlength="50" size="100"  cssStyle="width:170px" /></td>
   </tr>
   <tr>   
    <td scope="row" class="label"><label for="poOrganizations_personDTO_preFix">Prefix :</label></td><td><s:textfield name="preFix"  maxlength="10" size="100"  cssStyle="width:170px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_personDTO_suffix">Suffix :</label></td><td><s:textfield name="suffix"  maxlength="10" size="100"  cssStyle="width:170px" /></td>      
  </tr>   
  </table>
    
    <h2>Work Information</h2>
    <table  class="form">
    <tr>   
    <td scope="row" class="label"><label for="poOrganizations_personDTO_streetAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield name="streetAddress"  maxlength="254" size="100"  cssStyle="width:250px"  /></td>       
    </tr>
    <tr>
    <td scope="row" class="label"><label for="poOrganizations_personDTO_city">City :</label><span class="required">*</span></td><td><s:textfield name="city"  maxlength="50" size="100"  cssStyle="width:170px" /></td>
    <td scope="row" class="label"><label for="poOrganizations_personDTO_state">State :<br></label></td><td><s:textfield name="state"  maxlength="50" size="100"  cssStyle="width:170px" /><br><span class="tiny">(2-letter state code for USA/Canada and 2 or 3-letter state code for Australia.)</span></td>
    </tr>
     <tr>
        <td scope="row" class="label"><label for="poOrganizations_personDTO_zip">ZIP :</label><span class="required">*</span></td><td><s:textfield name="zip"  maxlength="20" size="100"  cssStyle="width:170px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_personDTO_country">Country :</label><span class="required">*</span></td><td>
            <s:select
             name="country" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>       
   </tr>
   </table>
    
    <h2>Work Contact Information</h2>
    <table  class="form">
   <tr>
       <td scope="row" class="label"><label for="poOrganizations_personDTO_email">Email :</label><span class="required">*</span></td><td><s:textfield name="email"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
       <td scope="row" class="label"><label for="oOrganizations_personDTO_phone">Phone :</label></td><td><s:textfield name="phone"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
   <tr>
        <td scope="row" class="label"><label for="poOrganizations_personDTO_url">URL :</label></td><td><s:textfield name="url"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_personDTO_tty">TTY :</label></td><td><s:textfield name="tty"  maxlength="30" size="100"  cssStyle="width:200px" /></td>       
   </tr>
   <tr> 
        
        <td scope="row" class="label"><label for="poOrganizations_personDTO_fax">Fax :</label></td><td><s:textfield name="fax"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
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