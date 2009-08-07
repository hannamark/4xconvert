<%@ taglib prefix="s" uri="/struts-tags"%>	
<table class="form">
	<tr>
	    <td scope="row" class="label"><s:label for="editOrg.name">First Name:</s:label><span class="required">*</span></td>
		<td class="value" style="width:250px">
		<s:textfield name="personContactWebDTO.firstName" maxlength="80" size="80" cssStyle="width: 200px" disabled="disabled" readonly="true" />                            
		<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>personContactWebDTO.firstName</s:param>
                               </s:fielderror>                            
                             </span>
		</td>			        
		<td class="value">
			<ul style="margin-top:-6px;">			
				<li style="padding-left:0"><a href="#" class="btn" onclick="lookupcontactperson();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
			</ul>
		</td> 
	</tr>
	<tr>
	    <td scope="row" class="label"><s:label for="editOrg.city">Middle Name:</s:label></td>
	    <td class="value" colspan="2">
	        <s:textfield name="personContactWebDTO.middleName" maxlength="200" size="200" 
	        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
	    </td>
	</tr>
	<tr>
	    <td scope="row" class="label"><s:label for="editOrg.countryName">Last Name:</s:label></td>
	    <td class="value" colspan="2">
	        <s:textfield name="personContactWebDTO.lastName" maxlength="200" size="200" 
	        disabled="disabled" cssStyle="width: 200px" readonly="true"/>
	</tr>
	<tr><td> OR	</td></tr>
	<tr>
    <td><s:textfield label="Responsible Party Name" name="personContactWebDTO.responsiblePersonName" id="personContactWebDTO.responsiblePersonName" size="30"  
        readonly="true" cssClass="readonly" cssStyle="width:200px"/>
    </td>
    <td class="value">
        <ul style="margin-top:-5px;">              
            <li style="padding-left:0">
            <a href="#" class="btn" id="lookupbtn4RP" onclick="lookup4loadresponsibleparty();" title="Opens a popup form to select Responsible Party Contact"/><span class="btn_img">
            <span class="person">Look Up Person</span></span></a>
            </li>
        </ul>
    </td>
    </tr>
	
	<s:hidden name="personContactWebDTO.selectedPersId"/>
</table>