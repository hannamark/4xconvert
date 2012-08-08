<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
    <tr>
        <td>
            <s:textfield label="First Name" name="gtdDTO.piName" size="30" cssStyle="width:200px" readonly="true" cssClass="readonly" />
            <a href="javascript:void(0)" onclick="displayPersonDetails($('gtdDTO.piIdentifier').value);">
                <img src="<%=request.getContextPath()%>/images/details.gif"/>
            </a>
        </td>
        <td>
            <ul style="margin-top: -1px;">
            <li style="padding-left: 0">
                <a href="javascript:void(0)" class="btn" onclick="lookup4loadleadpers();">
                    <span class="btn_img"><span class="person">Look Up Person</span></span>
                </a>
            </li>
            </ul>
            <s:hidden name="gtdDTO.piIdentifier" id="gtdDTO.piIdentifier"/>
        </td>
    </tr>
</table>
<span class="formErrorMsg"> <s:fielderror>
    <s:param>LeadPINotSelected</s:param>
</s:fielderror> </span>