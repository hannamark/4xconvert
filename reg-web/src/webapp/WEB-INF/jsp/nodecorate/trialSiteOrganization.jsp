<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
<tr>
<td><s:textfield label="Organization Name" name="trialDTO.siteOrganizationName"  id="trialDTO.siteOrganizationName" size="30" readonly="true" cssClass="readonly" cssStyle="width:200px" /> 
</td>
<td class="value">
    <ul style="margin-top:-5px;">              
        <li style="padding-left:0">
         <a href="javascript:void(0)" class="btn" onclick="lookup4loadSiteOrg();" title="Opens a popup form to select Site Organization"/><span class="btn_img"><span class="organization">Look Up Org</span></span></a>
        </li>
    </ul>
</td>
</tr>
</table>
 <span class="formErrorMsg"> 
     <s:fielderror>
     <s:param>trialDTO.siteOrganizationIdentifier</s:param>
    </s:fielderror>                            
  </span>