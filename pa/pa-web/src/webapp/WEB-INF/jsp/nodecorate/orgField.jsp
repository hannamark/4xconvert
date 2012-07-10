<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td> 
<s:textfield name="nciSpecificInformationWebDTO.organizationName" size="30"  readonly="true" cssClass="readonly"/>
</td>
<td>
<s:if test="nciSpecificInformationWebDTO.organizationIi != null">
<a href="javascript:void(0)" onclick="displayOrgDetails(<c:out value="${nciSpecificInformationWebDTO.organizationIi}"/>);">
    <img src="<%=request.getContextPath()%>/images/details.gif"/>
</a>
</s:if>
</td>
<td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a></li>
                  </ul><s:hidden name="nciSpecificInformationWebDTO.organizationIi" />
                   </td>
      </tr>
</table>
<span class="formErrorMsg"> 
   <s:fielderror>
   	  <s:param>nciSpecificInformationWebDTO.organizationName</s:param>
    </s:fielderror>                            
</span>
