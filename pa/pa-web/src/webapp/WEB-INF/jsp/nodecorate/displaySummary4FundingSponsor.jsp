<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td class="value">
<s:textfield label="Summary4sponsorName" name="gtdDTO.summaryFourOrgName" size="30" cssStyle="width:200px" readonly="true"/>

<s:if test="gtdDTO.summaryFourOrgIdentifier != null && gtdDTO.summaryFourOrgIdentifier != ''">
       <a href="javascript:void(0)" onclick="displayOrgDetails($('gtdDTO.summaryFourOrgIdentifier').value);">
            <img src="${pageContext.request.contextPath}/images/details.gif"/>
       </a>
</s:if> 

</td><td> 
                  <ul style="margin-top:-2px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup4loadSummary4Sponsor();"/><span class="btn_img"><span class="organization">Look Up Sponsor</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.summaryFourOrgIdentifier" id="gtdDTO.summaryFourOrgIdentifier"/>
                   </td>
      </tr>
</table>
<span class="formErrorMsg"> 
   <s:fielderror>
   <s:param>summary4FundingSponsor</s:param>
  </s:fielderror>                            
</span>