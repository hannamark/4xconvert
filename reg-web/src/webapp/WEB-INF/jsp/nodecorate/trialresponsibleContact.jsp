<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<span class="info">Select Either Personal Contact or Generic Contact</span>
<table>
<tr>
<td><s:textfield label="Responsible Party Name" name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
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
</table>

<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>ResponsiblePartyNotSelected</s:param>
    </s:fielderror>                            
</span>