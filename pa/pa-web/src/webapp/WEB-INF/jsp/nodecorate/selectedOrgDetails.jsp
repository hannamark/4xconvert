<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table  class="form">
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.name">Organization Name:</s:label><span class="required">*</span>
	    
		    
		    </td>
			<td>
                <s:textfield name="orgFromPO.name" maxlength="80" size="80" cssStyle="width: 300px" readonly="true" cssClass="readonly"/>
                <s:if test="%{currentAction == 'edit'}">
                    <a href="javascript:void(0)" onclick="displayOrgDetails(<c:out value="${editOrg.identifier}"/>);">
                        <img src="<%=request.getContextPath()%>/images/details.gif"/>
                    </a>
                </s:if>
            
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
							<li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
						</ul>
			     </s:if>
			 </td> 
		</tr>
		<s:if test="proprietaryTrialIndicator == 'false'">
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.city">City:</s:label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.city" maxlength="200" size="200" 
		         cssStyle="width: 200px" readonly="true" cssClass="readonly"/>
		    </td>
		</tr>
		<tr>
		    <td scope="row" class="label"><s:label for="orgFromPO.state">State:</s:label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.state" maxlength="200" size="200" 
		         cssStyle="width: 200px" readonly="true" cssClass="readonly"/>
		    </td>
		</tr>		
		<tr>
		    <td scope="row" class="label"><s:label for="orgFromPO.country">Country:</s:label><span class="required">*</span></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.country" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" cssClass="readonly"/>
		    </td>
		</tr>
		<tr>
		    <td scope="row" class="label"><s:label for="editOrg.postalCode">Zip/Postal Code(*US/Canada):</s:label></td>
		    <td class="value" colspan="2">
		        <s:textfield name="orgFromPO.zip" maxlength="200" size="200" 
		        disabled="disabled" cssStyle="width: 200px" cssClass="readonly"/>
		    </td>
		</tr>
    </s:if>
    <s:hidden name="editOrg.identifier" id="editOrg.identifier" ></s:hidden>
</table>