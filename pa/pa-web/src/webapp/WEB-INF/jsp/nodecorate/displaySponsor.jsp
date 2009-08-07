<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="First Name" name="gtdDTO.sponsorName" size="30"  cssStyle="width:200px" readonly="true" cssClass="readonly"/>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookup4sponsor();"/><span class="btn_img"><span class="organization">Look Up Sponsor</span></span></a></li>
                  </ul><s:hidden id="sponsorIdentifier"  name="gtdDTO.sponsorIdentifier"/>
                     </td>
      </tr>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>gtdDTO.sponsorName</s:param>
    </s:fielderror>                            
</span>