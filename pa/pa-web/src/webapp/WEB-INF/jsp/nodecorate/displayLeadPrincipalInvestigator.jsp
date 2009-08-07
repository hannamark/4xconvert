<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="First Name" name="gtdDTO.piName" size="30"  cssStyle="width:200px" readonly="true" cssClass="readonly"/>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookup4loadleadpers();"/><span class="btn_img"><span class="person">Look Up Person</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.piIdentifier"/>
            </td>
      </tr>
</table>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>LeadPINotSelected</s:param>
    </s:fielderror>                            
  </span>