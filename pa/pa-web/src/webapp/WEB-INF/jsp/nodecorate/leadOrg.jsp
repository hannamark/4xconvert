<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="Organization Name" name="gtdDTO.leadOrganizationName" size="30" cssStyle="width:200px" readonly="true" cssClass="readonly"/>
 </td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookup4loadleadorg();"/><span class="btn_img"><span class="organization">Look Up Org</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.leadOrganizationIdentifier" id="gtdDTO.leadOrganizationIdentifier"/>
                   </td>
      </tr>
</table>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>