<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <script language="JavaScript">

   function alertMsg(val) {
        if(val.value == 'USA')
        {
         document.getElementById('orgStateDivSelect').style.display = '';
         document.getElementById('orgStateDivText').style.display = 'none';
        } else {
         document.getElementById('orgStateDivSelect').style.display = 'none';
         document.getElementById('orgStateDivText').style.display = '';
        
        }
      }  
      
   </script>
<h2>Add Organization</h2>
<s:set name="usStates" value="@gov.nih.nci.pa.enums.USStateCode@getAbbreviatedNames()" />
<table  class="form">  
   <tr>     
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgName;"> Organization Name :</label><span class="required">*</span></td><td><s:textfield  id = "orgName"  name="createOrg.orgName;" maxlength="159" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgCountry;">Country :</label><span class="required">*</span></td><td>
            <s:select
             id = "orgCountry"
             name="createOrg.orgCountry;" 
             list="countryList" 
             listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" onblur="alertMsg(this);" cssStyle="width:206px" onclick="alertMsg(this);"/>
        </td>
        </tr>
   <tr>        
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgStreetAddress">Street Address :</label><span class="required">*</span></td><td><s:textfield id = "orgAddress" name="createOrg.orgStreetAddress"  maxlength="254" size="100"  cssStyle="width:200px" /></td>       
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgCity;">City :</label><span class="required">*</span></td><td><s:textfield id="orgCity" name="createOrg.orgCity;" maxlength="50" size="100"  cssStyle="width:200px" /></td>        
    
   </tr>
   <tr> 
   <td scope="row" class="label"><label for="poOrganizations_createOrg_orgState">State :<br></label></td>
   <td>
    <div id="orgStateDivSelect" style="display:''">
      <s:select id="orgStateSelect" name="createOrg.orgState" headerKey="" headerValue="-Select a State-"  list="#usStates" />
    </div>
    <div id="orgStateDivText" style="display:none">
       <s:textfield id ="orgStateText" name="createOrg.orgState"  maxlength="50" size="100"  cssStyle="width:200px"/>
       <br><span class="tiny">(2-letter state code for Canada <br> and 2 or 3-letter state code for Australia.)</span>
   </div>    
  </td>    
    <td scope="row" class="label"><label for="poOrganizations_createOrg_orgZip;">ZIP :</label><span class="required">*</span></td><td><s:textfield id="orgZip" name="createOrg.orgZip;" maxlength="20" size="100"  cssStyle="width:200px" /></td>
                
    </tr>
    <tr>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgEmail;">Email :</label><span class="required">*</span></td><td><s:textfield id="orgEmail" name="createOrg.orgEmail;"  maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgPhone">Phone Number :</label></td><td><s:textfield  id="orgPhone"  name="createOrg.orgPhone" maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgURL;">URL :</label></td><td><s:textfield id="orgUrl" name="createOrg.orgURL;" maxlength="254" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgTTY;">TTY :</label></td><td><s:textfield id="orgTty" name="createOrg.orgTTY;"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="poOrganizations_createOrg_orgFax;">Fax Number :</label></td><td><s:textfield id="orgFax"  name="createOrg.orgFax;"  maxlength="30" size="100"  cssStyle="width:200px" /></td>
    </tr>       
</table>
 <div class="actionsrow">
 <del class="btnwrapper">
    <ul class="btnrow">
       <li>
           <s:a href="#" cssClass="btn" onclick="createOrg()"><span class="btn_img"><span class="save">Save</span></span></s:a>
           <s:a href="#" cssClass="btn" onclick="setSearchFormVisible();"><span class="btn_img"><span class="search">Cancel</span></span></s:a>
       </li>
       </ul>   
  </del>
  </div>