<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>

<s:textfield label="Organization Name" name="gtdDTO.leadOrganizationName" size="30" cssStyle="width:200px" readonly="true" cssClass="readonly"/>
<a href="javascript:void(0)" onclick="displayOrgDetails(<c:out value="${gtdDTO.leadOrganizationIdentifier}"/>);">
    <img src="<%=request.getContextPath()%>/images/details.gif"/>
</a>
 </td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4loadleadorg();"/><span class="btn_img"><span class="organization">Look Up Organization</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.leadOrganizationIdentifier" id="gtdDTO.leadOrganizationIdentifier"/>
                   </td>
      </tr>
</table>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadOrgNotSelected</s:param>
    </s:fielderror>                            
  </span>