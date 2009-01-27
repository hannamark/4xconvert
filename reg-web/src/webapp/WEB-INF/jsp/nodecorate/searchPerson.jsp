<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Search Persons</h2>
<p align="center" class="info">
    Type-in one/several initial characters in any text field on the upper frame
    or one/several initial characters in CTEP Identifier field on the lower frame.
    <br>
    (example: first name Mary should come as M or Ma or Mary, etc.).
    Please do not use wildcard characters.
</p>
<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name">First Name :</label>
        </td>
 		<td>
 			<s:textfield name="firstName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>	
	<tr>  
  		<td scope="row" class="label">
            <label for="nciorgname"> Last Name :</label>
        </td>
 		<td> 			
 			<s:textfield name="lastName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr>  
  		<td scope="row" class="label">
            <label for="nciorgname"> Email :</label>
        </td>
 		<td> 			
 			<s:textfield name="email"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr><td colspan="2"> <hr></td> </tr>
		<tr>  
  		<td scope="row" class="label">
            <label for="nciorgname"> CTEP Identifier :</label>
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