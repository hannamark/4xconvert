<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td>
<s:textfield name="gtdDTO.centralContactName" id="gtdDTO.centralContactName" size="30"  readonly="true" cssClass="readonly"/>
<s:if test="gtdDTO.centralContactIdentifier != null">
<a href="javascript:void(0)" onclick="displayPersonDetails(<c:out value="${gtdDTO.centralContactIdentifier}"/>);">
    <img src="<%=request.getContextPath()%>/images/details.gif"/>
</a>
</s:if>
</td><td> 
                  <ul style="margin-top:-1px;">             
                        <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupCentralContact();"/><span class="btn_img"><span class="person">Look Up Person</span></span></a><a href="javascript:void(0)" class="btn" onclick="handleRemove();"/><span class="btn_img"><span class="delete">Remove</span></span></a></li>
                  </ul><s:hidden name="gtdDTO.centralContactIdentifier" id="gtdDTO.centralContactIdentifier" />
            </td>
      </tr>
</table>


<span class="formErrorMsg"> 
<s:fielderror>
<s:param>gtdDTO.centralContactName</s:param>
</s:fielderror>                            
</span>