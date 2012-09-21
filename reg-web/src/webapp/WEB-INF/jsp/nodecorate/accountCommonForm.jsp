
<tr><td class="space" colspan="2">&nbsp;</td></tr>
<tr>
    <th colspan="3">
      Your Account Profile
    </th>
</tr>
<tr><td class="space" colspan="2">&nbsp;</td></tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.emailAddress"><fmt:message key="register.user.emailAddress"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.emailAddress" name="registryUserWebDTO.emailAddress"  maxlength="255" size="35" cssStyle="width:200px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.emailAddress</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.firstName"> <fmt:message key="register.user.firstName"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.firstName"  name="registryUserWebDTO.firstName"  maxlength="200" size="50"  cssStyle="width:150px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.firstName</s:param>
           </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.middleName"> <fmt:message key="register.user.middleInitial"/></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.middleName" name="registryUserWebDTO.middleName"  maxlength="2" size="35"  cssStyle="width:20px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.middleName</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.lastName"> <fmt:message key="register.user.lastName"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield  id="registryUserWebDTO.lastName"  name="registryUserWebDTO.lastName"  maxlength="200" size="50"  cssStyle="width:150px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.lastName</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
                        <tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.addressLine"><fmt:message key="register.user.streetAddress"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.addressLine" name="registryUserWebDTO.addressLine"  maxlength="200" size="50"  cssStyle="width:200px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.addressLine</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.city"> <fmt:message key="register.user.city"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.city" name="registryUserWebDTO.city"  maxlength="200" size="35"  cssStyle="width:200px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.city</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.state"><fmt:message key="register.user.state"/><span class="required">*</span></label>
    </td>
    <td>
        <s:set name="stateCodeValues" value="@gov.nih.nci.pa.enums.USStateCode@getDisplayNames()" />
        <s:select id="registryUserWebDTO.state" headerKey="" headerValue="--Select--"
            name="registryUserWebDTO.state"
            list="#stateCodeValues"
            value="registryUserWebDTO.state"
             />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.state</s:param>
           </s:fielderror>
        </span>
    </td>
</tr>
                        <tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.postalCode"><fmt:message key="register.user.zipCode"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.postalCode" name="registryUserWebDTO.postalCode"  maxlength="15" size="8"  cssStyle="width:80px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.postalCode</s:param>
           </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.country"> <fmt:message key="register.user.country"/><span class="required">*</span></label>
    </td>
    <td>
        <s:set name="countries"
                value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().
                getCountries()" />
        <s:select headerKey="United States" headerValue="United States"
        		 id="registryUserWebDTO.country"
                 name="registryUserWebDTO.country"
                 list="#countries"
                 listKey="name"
                 listValue="name"
                 value="registryUserWebDTO.country"
                 />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.country</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.phone"> <fmt:message key="register.user.phone"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.phone" name="registryUserWebDTO.phone"  maxlength="50" size="15"  cssStyle="width:120px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.phone</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<s:hidden name="registryUserWebDTO.affiliatedOrganizationId" id="registryUserWebDTO.affiliatedOrganizationId"/>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.affiliateOrg"> <fmt:message key="register.user.affiliateOrg"/><span class="required">*</span></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.affiliateOrg" readonly="true" size="30" name="registryUserWebDTO.affiliateOrg" cssStyle="float:left; width:250px" cssClass="readonly"/>
        <a href="javascript:void(0)" class="btn" onclick="lookupAffiliateOrg();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.affiliateOrg</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td colspan="2" class="space">
        <div id="adminAccessDiv">
            <%@ include file="/WEB-INF/jsp/nodecorate/adminUsers.jsp" %>
        </div>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.prsOrgName"> <fmt:message key="register.user.prsOrgName"/></label>
    </td>
    <td>
        <s:textfield id="registryUserWebDTO.prsOrgName"  name="registryUserWebDTO.prsOrgName"  maxlength="200" size="100"  cssStyle="width:200px" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.prsOrgName</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td scope="row" class="label">
        <label for="registryUserWebDTO.enableEmails"> <fmt:message key="register.user.enableEmails"/></label>
    </td>
    <td>
        <s:checkbox id="registryUserWebDTO.enableEmails" name="registryUserWebDTO.enableEmails" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.enableEmails</s:param>
            </s:fielderror>
        </span>
    </td>
</tr>
<tr>
    <td colspan="2" align="left">
        <p><b><I>Contact information required for internal administrative use only; not revealed to public</I></b></p>
    </td>
</tr>
