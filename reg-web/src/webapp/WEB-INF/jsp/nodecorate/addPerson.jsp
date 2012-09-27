<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<script language="JavaScript">

   function alertMsgState(val) {
        if(val.value == 'USA')
        {
         document.getElementById('poOrganizations_orgStateDivSelect').style.display = '';
         document.getElementById('poOrganizations_orgStateDivText').style.display = 'none';
        } else {
         document.getElementById('poOrganizations_orgStateDivSelect').style.display = 'none';
         document.getElementById('poOrganizations_orgStateDivText').style.display = '';
        
        }
      }  
      
   </script>
<h2>Add Person</h2>
<s:set name="usStates" value="@gov.nih.nci.pa.enums.USStateCode@getAbbreviatedNames()" />
<br><p><b><i>Please provide professional contact information only.</i></b></p>
<table  class="form">  
   <tr>             
        <td scope="row" class="label"><label for="poOrganizations_firstName">First Name :</label><span class="required">*</span></td><td><s:textfield name="firstName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_lastName">Last Name :</label><span class="required">*</span></td><td><s:textfield name="lastName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
   </tr>
    <tr>   
        <td scope="row" class="label"><label for="poOrganizations_preFix">Prefix :</label></td><td><s:textfield name="preFix"  maxlength="10" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_middleName">Middle Name :</label></td><td><s:textfield name="middleName"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
    </tr>   
    <tr>   
        <td scope="row" class="label"><label for="poOrganizations_suffix">Suffix :</label></td><td><s:textfield name="suffix"  maxlength="10" size="100"  cssStyle="width:200px" /></td>
   <td scope="row" class="label"><label for="poOrganizations_country">Country :</label><span class="required">*</span></td><td>
            <s:select
             name="country" 
             list="countryList"  
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" onblur="alertMsgState(this);" cssStyle="width:206px" onclick="alertMsgState(this);" />
        </td>   
       
    </tr>   
   <tr>
        <td scope="row" class="label"><label for="poOrganizations_streetAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield name="streetAddress"  maxlength="254" size="100"  cssStyle="width:200px"  /></td>
        <td scope="row" class="label"><label for="poOrganizations_city">City :</label><span class="required">*</span></td><td><s:textfield name="city"  maxlength="50" size="100"  cssStyle="width:200px" /></td>
        
   </tr>
   <tr>
   
     <td scope="row" class="label"><label for="poOrganizations_orgStateSelect">State :<br></label></td>
     <td>
      <div id="poOrganizations_orgStateDivSelect" style="display:''">
       <s:select id="poOrganizations_orgStateSelect" name="state" headerKey="" headerValue="-Select a State-"  list="#usStates" />
      </div>
      <div id="poOrganizations_orgStateDivText" style="display:none">
       <s:textfield id ="poOrganizations_orgStateText" name="state"  maxlength="50" size="100"  cssStyle="width:200px"/>
       <br><span class="tiny">(2-letter state code for Canada <br> and 2 or 3-letter state code for Australia.)</span>
      </div> 
     </td>
     <td scope="row" class="label"><label for="poOrganizations_zip">ZIP :</label><span class="required">*</span></td><td><s:textfield name="zip"  maxlength="20" size="100"  cssStyle="width:200px" /></td>
  </tr>
   <tr>
       <td scope="row" class="label"><label for="poOrganizations_email">Email :</label><span class="required">*</span></td><td><s:textfield name="email"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
       <td scope="row" class="label"><label for="poOrganizations_phone">Phone :</label></td><td><s:textfield name="phone"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
   <tr>
        <td scope="row" class="label"><label for="poOrganizations_url">URL :</label></td><td><s:textfield name="url"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_tty">TTY :</label></td><td><s:textfield name="tty"  maxlength="30" size="100"  cssStyle="width:200px" /></td>       
   </tr>
   <tr> 
        
        <td scope="row" class="label"><label for="poOrganizations_fax">Fax :</label></td><td><s:textfield name="fax"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
   </tr>        
</table>
 <div align="center">
 <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
 </div>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="createPerson()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="javascript:void(0)" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Cancel</span></span></s:a>          
       </li>
       </ul>   
  </del>
  </div>