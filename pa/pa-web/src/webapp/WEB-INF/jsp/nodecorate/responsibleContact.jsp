<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield label="Responsible Party Name" name="gtdDTO.responsiblePersonName" size="30"  cssStyle="width:200px" readonly="true" cssClass="readonly"/>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookup4loadresponsibleparty();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.responsiblePersonIdentifier"/>
                   </td>
      </tr>
</table>
<span class="formErrorMsg"> 
<s:fielderror>
<s:param>gtdDTO.responsiblePersonName</s:param>
</s:fielderror>                            
</span>