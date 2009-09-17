<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<table>
<tr>
<td><s:textfield label="Site Pi Full Name" name="trialDTO.sitePiName" id="trialDTO.sitePiName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="#" class="btn" onclick="lookup4loadSitePerson();" title="Opens a popup form to select Principal Investigator"/>
         <span class="btn_img"><span class="person">Look Up Person</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>trialDTO.sitePiIdentifier</s:param>
    </s:fielderror>                            
  </span>