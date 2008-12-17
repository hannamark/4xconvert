<%@ taglib prefix="s" uri="/struts-tags"%>
<table  class="form">
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.name">Organization Name:</s:label><span class="required">*</span>
	    
		    
		    </td>
			<td class="value" style="width:250px">
				<s:textfield name="orgFromPO.orgName" maxlength="80" size="80" cssStyle="width: 300px" disabled="disabled" readonly="true"/>
				<s:if test="%{currentAction == 'create'}">
				<span class="info">Click <strong>Look Up</strong> to choose an organization.</span>
				<span class="formErrorMsg"></span>
				</s:if>
				         <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>editOrg.name</s:param>
                              </s:fielderror>                            
                        </span>	
			</td>			        
			<td class="value">
			         <s:if test="%{currentAction == 'create'}">
						<ul style="margin-top:-6px;">			
							<li style="padding-left:0"><a href="#" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
						</ul>
			     </s:if>
			 </td> 
		</tr>
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.city">City:</s:label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.orgCity" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
		    </td>
		</tr>
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.countryName">Country:</s:label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.orgCountry" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
		</tr>
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.postalCode">Zip/Postal Code(*US/Canada):</s:label></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.orgZip" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
		    </td>
		</tr>

</table>