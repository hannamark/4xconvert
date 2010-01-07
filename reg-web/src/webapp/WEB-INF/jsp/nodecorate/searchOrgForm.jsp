<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<SCRIPT LANGUAGE="JavaScript">
function formReset(){
    document.getElementById("orgNameSearch").value = '';
    document.getElementById("orgCitySearch").value = '';
    document.getElementById("orgStateSearch").value = '';
    document.getElementById("orgCountrySearch").value = 'USA';
    document.getElementById("orgZipSearch").value = '';
    document.getElementById("orgCtepIdSearch").value = '';
}
</SCRIPT>
</head>
<p align="center" class="info">
	Type a string of characters in any of the text fields in the upper frame 
    or in CTEP Identifier field  in the lower frame.
    <br>
    Please do not use wildcard characters.<br>
</p>
<table  class="form">
    <table>
   	<tr> 	
 		<td scope="row" class="label">
            <label for="poOrganizations_orgSearchCriteria_orgName"> <fmt:message key="popUpOrg.name"/></label>
        </td>
 		<td>
 			<s:textfield id="orgNameSearch" name="orgSearchCriteria.orgName" maxlength="200" size="100"  cssStyle="width:195px" />
 		</td>
 		
 		<td scope="row" class="label">
            <label for="poOrganizations_orgSearchCriteria_orgCountry"> <fmt:message key="popUpOrg.country"/></label>
        </td>
        <td colspan="3">
              	<s:select  
                id="orgCountrySearch"
                name="orgSearchCriteria.orgCountry" 
                list="countryList"  
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:270px" />
        </td>
	</tr>
	<tr><td colspan="6"></td></tr>
	<tr>  
 		<td scope="row" class="label">
            <label for="poOrganizations_orgSearchCriteria_orgCity"> <fmt:message key="popUpOrg.city"/></label>
        </td>
 		<td> 			
 			<s:textfield id="orgCitySearch" name="orgSearchCriteria.orgCity"  maxlength="200" size="100"  cssStyle="width:195px" />
		</td>
        <td scope="row" class="label"> <label for="poOrganizations_orgSearchCriteria_orgState"> <fmt:message key="popUpOrg.state"/></label></td>
        <td><s:textfield id="orgStateSearch" name="orgSearchCriteria.orgState"  maxlength="55" size="30" cssStyle="width:135px" /><br><font size="1"><span class="info">please enter two letter <br>identifier for US states for ex:<br> 'MD' for Maryland</span></font></td>

        <td scope="row" class="value">
            <label for="poOrganizations_orgSearchCriteria_orgZip"> <fmt:message key="popUpOrg.zip"/> </label>
        </td>
        <td>
            <s:textfield id="orgZipSearch" name="orgSearchCriteria.orgZip"  maxlength="15" size="20" cssStyle="width:100px"/>
        </td>

	</tr>
	<tr><td colspan="6"> <hr></td> </tr>
	<tr>  
 		<td >
            <label for="poOrganizations_orgSearchCriteria_ctepId">CTEP Identifier :</label>
            
        </td>
 		<td> 			
 			<s:textfield id="orgCtepIdSearch" name="orgSearchCriteria.ctepId"  maxlength="200" size="100"  cssStyle="width:200px" />
 			
		</td>
	</tr>
	</table>
	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Org</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="formReset();"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="close">Close</span></span></s:a>
                   </li>
               </ul>   
          </del>
    </div>
