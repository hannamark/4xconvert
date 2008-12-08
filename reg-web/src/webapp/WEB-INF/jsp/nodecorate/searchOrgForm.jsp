<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Search Organizations</h2>
<table  class="form">
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name"> <fmt:message key="popUpOrg.name"/></label>
        </td>
 		<td>
 			<s:textfield name="orgSearchCriteria.orgName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
 		
 		<td scope="row" class="label">
            <label for="country"> <fmt:message key="popUpOrg.country"/></label>
        </td>
        <td>
              	<s:select  
                name="orgSearchCriteria.orgCountry" 
                list="countryList"  
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>		
	</tr>
	<tr>  
 		<td scope="row" class="label">
            <label for="city"> <fmt:message key="popUpOrg.city"/></label>
        </td>
 		<td> 			
 			<s:textfield name="orgSearchCriteria.orgCity"  maxlength="200" size="100"  cssStyle="width:200px" />
		</td>
 		 <td scope="row" class="label">
            <label for="zip"> <fmt:message key="popUpOrg.zip"/></label>
        </td>
 		<td>
 			<s:textfield name="orgSearchCriteria.orgZip" maxlength="75" size="20"/>
		</td>
	</tr>
	<tr><td colspan="4"> <hr></td> </tr>
	<tr>  
 		<td scope="row" class="label">
            <label for="city">CTEP Identifier :</label>
            
        </td>
 		<td> 			
 			<s:textfield name="orgSearchCriteria.ctepId"  maxlength="200" size="100"  cssStyle="width:200px" />
 			
		</td>
	</tr>
	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv();"><span class="btn_img"><span class="search">Search</span></span></s:a>
                   <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Org</span></span></s:a>  
                   </li>
               </ul>   
          </del>
    </div>
