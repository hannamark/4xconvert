<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield name="gtdDTO.centralContactName" size="30"  readonly="true" cssClass="readonly"/></td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookupCentralContact();"/><span class="btn_img"><span class="person">Look Up</span></span></a><a href="#" class="btn" onclick="handleRemove();"/><span class="btn_img"><span class="delete">Remove</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.centralContactIdentifier" />
            </td>
      </tr>
</table>


<span class="formErrorMsg"> 
<s:fielderror>
<s:param>gtdDTO.centralContactName</s:param>
</s:fielderror>                            
</span>