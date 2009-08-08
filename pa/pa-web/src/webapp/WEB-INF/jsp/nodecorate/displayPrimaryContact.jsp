<%@ taglib prefix="s" uri="/struts-tags"%>	
<pa:failureMessage/>
<pa:sucessMessage /> 
<div id="saveAndShowPersons">
	<%@ include file="/WEB-INF/jsp/nodecorate/selectedName.jsp" %>
</div>	
<table class="form">
	<tr>
	    <td scope="row" class="label"><s:label for="editOrg.postalCode">Phone Number:</s:label><span class="required">*</span></td>
	  
	    <td class="value" colspan="2">
	    	
	        <s:textfield name="personContactWebDTO.telephone"  maxlength="200" size="200"				         
	        cssStyle="width: 200px" />
                     <span class="formErrorMsg"> 
                           <s:fielderror>
                           <s:param>personContactWebDTO.telephone</s:param>
                           </s:fielderror>                            
                     </span>				        
	    </td>				    
	</tr>
		<tr>
	    <td scope="row" class="label"><s:label for="editOrg.postalCode">Email Address:</s:label><span class="required">*</span></td>
	 
	    <td class="value" colspan="2">
	        <s:textfield name="personContactWebDTO.email"  maxlength="200" size="200" 
	         cssStyle="width: 200px" />
                     <span class="formErrorMsg"> 
                           <s:fielderror>
                           <s:param>personContactWebDTO.email</s:param>
                           </s:fielderror>                            
                     </span>				          
	    </td>
	     
	</tr>
	 <tr>
                    <td class="label"><s:label for="statusCode">Status:</s:label></td>
                    <td class="value" colspan="2">
                        <s:textfield name="personContactWebDTO.statusCode" readonly="true" cssClass="readonly" maxlength="80" size="80" cssStyle="width: 200px"/>
                    </td>               
                </tr>
</table>