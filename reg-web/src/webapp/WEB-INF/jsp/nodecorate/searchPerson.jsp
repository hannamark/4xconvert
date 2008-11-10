<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<h2>Search Persons</h2>
<table  class="form">  
   	<tr> 	
 		<td scope="row" class="label">
            <label for="name">First Name:</label>
        </td>
 		<td>
 			<s:textfield name="firstName"  maxlength="200" size="100"  cssStyle="width:200px" />
 		</td>
	</tr>
	<tr>  
  		<td scope="row" class="label">
            <label for="nciorgname"> Last Name:</label>
        </td>
 		<td> 			
 			<s:textfield name="lastName"  maxlength="200" size="100"  cssStyle="width:200px" />
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