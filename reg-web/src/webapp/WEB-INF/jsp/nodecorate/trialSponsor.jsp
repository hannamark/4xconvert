<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<table>
<tr>
<td><s:textfield label="First Name" name="trialDTO.sponsorName" id="trialDTO.sponsorName" size="30"  readonly="true" cssClass="readonly" cssStyle="width:200px"/>
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="#" class="btn" onclick="lookup4sponsor();" title="Opens a popup form to select Sponsor"/><span class="btn_img">
         <span class="organization">Look Up Sponsor</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
<span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>trialDTO.sponsorIdentifier</s:param>
    </s:fielderror>                            
</span>