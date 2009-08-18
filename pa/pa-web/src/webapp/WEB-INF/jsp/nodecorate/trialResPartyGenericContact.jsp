<%@ taglib prefix="s" uri="/struts-tags"%>

<table>
<tr>
<td><s:textfield label="Responsible Party Generic Contact" name="gtdDTO.responsibleGenericContactName" id="gtdDTO.responsibleGenericContactName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="#" class="btn" id="lookupbtn4RP" onclick="lookup4loadresponsiblepartygenericcontact();" title="Opens a popup form to select Responsible Party Generic Contact"/>
         <span class="btn_img"><span class="person">Look Up Generic Contact</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>gtdDTO.responsibleGenericContactName</s:param>
    </s:fielderror>                            
</span>