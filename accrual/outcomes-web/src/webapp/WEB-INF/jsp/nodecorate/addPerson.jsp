<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Add Person</h2>
<br><p><b><i>Please provide professional contact information only.</i></b></p>
<table  class="form">  
   <tr>             
        <td scope="row" class="label"><label>First Name :</label><span class="required">*</span></td><td><s:textfield id= "firstName" name="firstName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label>Last Name :</label><span class="required">*</span></td><td><s:textfield id= "lastName" name="lastName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
   </tr>
    <tr>   
        <td scope="row" class="label"><label>Prefix :</label></td><td><s:textfield id="preFix" name="preFix"  maxlength="10" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label>Middle Name :</label></td><td><s:textfield id="middleName" name="middleName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
    </tr>   
    <tr>   
        <td scope="row" class="label"><label>Suffix :</label></td><td><s:textfield id="suffix" name="suffix"  maxlength="10" size="100"  cssStyle="width:200px" /></td>
<td scope="row" class="label"><label>Street Address :</label><span class="required">*</span></td><td><s:textfield id="streetAddress" name="streetAddress"  maxlength="254" size="100"  cssStyle="width:200px"  /></td>       
    </tr>   
   <tr>
        <td scope="row" class="label"><label>City :</label><span class="required">*</span></td><td><s:textfield id="city" name="city"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label>State :<br></label></td><td><s:textfield id="state" name="state"  maxlength="50" size="100"  cssStyle="width:200px" /><br><span class="tiny">(2-letter state code for USA/Canada and 2 or 3-letter state code for Australia.)</span></td>
   </tr>
   <tr>
        <td scope="row" class="label"><label>ZIP :</label><span class="required">*</span></td><td><s:textfield id="zip" name="zip"  maxlength="20" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label>Country :</label><span class="required">*</span></td><td>
            <s:select id="country"
             name="country" 
             list="physicianSearchCriteria.countries"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>       
   </tr>
   <tr>
       <td scope="row" class="label"><label>Email :</label><span class="required">*</span></td><td><s:textfield id="email" name="email"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
       <td scope="row" class="label"><label>Phone :</label></td><td><s:textfield id="phone" name="phone"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
   <tr>
        <td scope="row" class="label"><label>URL :</label></td><td><s:textfield id="url" name="url"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label>TTY :</label></td><td><s:textfield id="tty" name="tty"  maxlength="30" size="100"  cssStyle="width:200px" /></td>       
   </tr>
   <tr> 
        
        <td scope="row" class="label"><label>Fax :</label></td><td><s:textfield id="fax" name="fax"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
   </tr>        
</table>
 <div align="center">
 <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
 </div>
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