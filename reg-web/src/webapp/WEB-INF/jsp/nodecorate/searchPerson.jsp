<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<p align="center" class="info">
    Type a single initial character or string of initial characters in any of the 
    text fields in the upper frame or in CTEP Identifier field in the lower frame.
    <br>
    (Example: to search for the person with the first name Mary, type M, Ma, etc).
     Please do not use wildcard characters.
</p>
<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="poOrganizations_firstName">First Name :</label>
        </td>
 		<td>
 			<s:textfield name="firstName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>	
	<tr>  
  		<td scope="row" class="label">
            <label for="poOrganizations_lastName"> Last Name :</label>
        </td>
 		<td> 			
 			<s:textfield name="lastName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr>  
  		<td scope="row" class="label">
            <label for="poOrganizations_email"> Email :</label>
        </td>
 		<td> 			
 			<s:textfield name="email"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr><td colspan="2"> <hr></td> </tr>
		<tr>  
  		<td scope="row" class="label">
            <label for="poOrganizations_ctepId"> CTEP Identifier :</label>
        </td>
 		<td> 			
 			<s:textfield name="ctepId"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	
	</table>
	<div class="actionsrow">
         <del class="btnwrapper">
            <ul class="btnrow">
               <li><li>            
                   <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                   <s:a href="#" cssClass="btn" onclick="setCreateFormVisible();"><span class="btn_img"><span class="add">Add Person</span></span></s:a> 
                   </li>
               </ul>   
          </del>
     </div> 