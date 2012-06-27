<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<SCRIPT LANGUAGE="JavaScript">
function formReset(){
    document.getElementById('firstName').value = '';
    document.getElementById('lastName').value = '';
    document.getElementById('city').value = '';
    document.getElementById('state').value = '';
    document.getElementById('country').value = 'USA';
    document.getElementById('zip').value = '';
    document.getElementById('email').value = '';
    document.getElementById('perCtepIdSearch').value = '';
    document.getElementById('perPOIdSearch').value = '';
}

function formClose() {
    var winObj = window.top;
    if (window.parent!=window.top && window.parent!=window.self) {
        winObj = window.parent;
    }
	winObj.hidePopWin();	
}

</SCRIPT>
</head>
<p align="center" class="info">
        Type a string of characters in any of the text fields in the upper frame OR
        enter PO ID or CTEP Identifier in the lower frame.
    <br>
    Please do not use wildcard characters.<br>

</p>
<table  class="form">
       <tr>
         <td scope="row" class="label"><label for="poOrganizations_firstName">First Name</label></td>
         <td><s:textfield name="personDTO.firstName" id="firstName" maxlength="200" size="100"  cssStyle="width:200px" /></td>
         <td scope="row" class="label"><label for="poOrganizations_lastName"> Last Name</label></td>
         <td><s:textfield name="personDTO.lastName"  id="lastName" maxlength="200" size="100"  cssStyle="width:200px" /></td>
         <td scope="row" class="label"><label for="poOrganizations_email"> Email</label></td>
        <td><s:textfield name="personDTO.email" id="email" maxlength="200" size="100"  cssStyle="width:150px" /></td>

    </tr>
       <tr>
           <td scope="row" class="label"><label for="city">City:</label></td>
         <td><s:textfield name="personDTO.city"  id="city" maxlength="200" size="100"  cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="state">State</label></td>
        <td><s:textfield name="personDTO.state"  id="state" maxlength="200" size="100"  cssStyle="width:200px" /><br><font size="1"><span class="info">please enter two letter identifier for US states<br> for ex: 'MD' for Maryland</span></font></td>
        <td scope="row" class="label"><label for="zip">Zip</label></td>
        <td><s:textfield name="personDTO.zip"  id="zip" maxlength="200" size="100"  cssStyle="width:150px" /></td>
    </tr>
       <tr>
           <td scope="row" class="label"><label for="country">Country</label></td>
        <td>
                  <s:select
                name="personDTO.country"
                list="countryList"
                id = "country"
                listKey="alpha3" listValue="name" headerKey="USA" headerValue="United States" cssStyle="width:206px" />
        </td>
    </tr>
    <tr><td colspan="6"> <hr></td> </tr>
        <tr>
        <td><label for="poId">PO ID (Exact Match)</label></td>
        <td><s:textfield id="perPOIdSearch" name="perPOIdSearch" maxlength="10" size="100" cssStyle="width:200px" /></td>
        <td scope="row" class="label"><label for="poOrganizations_ctepId">CTEP Identifier</label></td>
         <td><s:textfield name="perCtepIdSearch" id="perCtepIdSearch" maxlength="200" size="100"  cssStyle="width:200px" /></td>
    </tr>

    </table>
    <div class="actionsrow">
        <div class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0);" cssClass="btn" onclick="loadDiv();" id="search_person_btn"><span class="btn_img"><span class="search">Search</span></span></s:a>
                    <s:a href="javascript:void(0);" cssClass="btn" onclick="setCreateFormVisible();" id="add_person_btn"><span class="btn_img"><span class="add">Add Person</span></span></s:a>
                    <s:a href="javascript:void(0);" cssClass="btn" onclick="formReset();" id="search_person_reset_btn"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                    <s:a href="javascript:void(0);" cssClass="btn" onclick="formClose();" id="search_person_close_btn"><span class="btn_img"><span class="close">Close</span></span></s:a>
                </li>
            </ul>
        </div>
    </div>
