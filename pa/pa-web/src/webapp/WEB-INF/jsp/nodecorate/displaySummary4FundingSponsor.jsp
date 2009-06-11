<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td class="value">
<s:textfield label="Summary4sponsorName" name="gtdDTO.summaryFourOrgName" size="30" cssStyle="width:200px" readonly="true"/> 
<%--<input type="button" value="Look Up" onclick="lookup4loadSummary4Sponsor();"/>--%>
</td><td> 
                  <ul style="margin-top:-2px;">             
                        <li style="padding-left:0"><a href="#" class="btn" onclick="lookup4loadSummary4Sponsor();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.summaryFourOrgIdentifier"/>
                   </td>
      </tr>
</table>
<span class="formErrorMsg"> 
   <s:fielderror>
   <s:param>summary4FundingSponsor</s:param>
  </s:fielderror>                            
</span>