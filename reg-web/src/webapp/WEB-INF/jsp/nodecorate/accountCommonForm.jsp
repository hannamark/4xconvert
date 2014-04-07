<i data-trigger="hover" data-placement="right" data-content="Please provide professional contact information only. Contact information required for internal administrative use only; not revealed to public." rel="popover" id="popover" name="popover" class="fa-info-circle help-text" data-original-title="" title=""></i>
 <div class="form-group">
   <label for="registryUserWebDTO.emailAddress" class="col-xs-4 control-label"><fmt:message key="register.user.emailAddress"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="email" cssClass="form-control" id="registryUserWebDTO.emailAddress" name="registryUserWebDTO.emailAddress"  maxlength="255" min="35" placeholder="example@email.com"/>
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.emailAddress</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.firstName" class="col-xs-4 control-label"><fmt:message key="register.user.firstName"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.firstName"  name="registryUserWebDTO.firstName"  maxlength="200" size="50"/>
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.firstName</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.middleName" class="col-xs-4 control-label"><fmt:message key="register.user.middleInitial"/></label>
   <div class="col-xs-2">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.middleName" name="registryUserWebDTO.middleName"  maxlength="2" size="35"/>
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.middleName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.lastName" class="col-xs-4 control-label"><fmt:message key="register.user.lastName"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.lastName"  name="registryUserWebDTO.lastName"  maxlength="200" size="50" />
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.lastName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.addressLine" class="col-xs-4 control-label"><fmt:message key="register.user.streetAddress"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.addressLine" name="registryUserWebDTO.addressLine"  maxlength="200" size="50" />
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.addressLine</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.city" class="col-xs-4 control-label"><fmt:message key="register.user.city"/> <span class="required">*</span></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.city" name="registryUserWebDTO.city"  maxlength="200" size="35" />
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.city</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.state" class="col-xs-4 control-label"><fmt:message key="register.user.state"/> <span class="required">*</span></label>
   <div class="col-xs-5">
   	 <s:set name="stateCodeValues" value="@gov.nih.nci.pa.enums.USStateCode@getDisplayNames()" />
     <s:select id="registryUserWebDTO.state" headerKey="" headerValue="--Select--"
            name="registryUserWebDTO.state"
            list="#stateCodeValues"
            value="registryUserWebDTO.state"
            cssClass="form-control" />
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.state</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.postalCode" class="col-xs-4 control-label"><fmt:message key="register.user.zipCode"/> <span class="required">*</span></label>
   <div class="col-xs-3">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.postalCode" name="registryUserWebDTO.postalCode"  maxlength="15" size="8" />
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.postalCode</s:param>
           </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.country" class="col-xs-4 control-label"><fmt:message key="register.user.country"/> <span class="required">*</span></label>
   <div class="col-xs-5">
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
                 cssClass="form-control"/>
        <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.country</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.phone" class="col-xs-4 control-label"><fmt:message key="register.user.phone"/> <span class="required">*</span></label>
   <div class="col-xs-3">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.phone" name="registryUserWebDTO.phone"  maxlength="50" size="15" placeholder="XXX-XXX-XXXX"/>
     <span class="formErrorMsg">
           <s:fielderror>
               <s:param>registryUserWebDTO.phone</s:param>
           </s:fielderror>
       </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.affiliateOrg" class="col-xs-4 control-label"><fmt:message key="register.user.affiliateOrg"/> <span class="required">*</span></label>
   <div class="col-xs-5">
   	 <s:hidden name="registryUserWebDTO.affiliatedOrganizationId" id="registryUserWebDTO.affiliatedOrganizationId"/>
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.affiliateOrg" readonly="true" size="30"/>
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.affiliateOrg</s:param>
            </s:fielderror>
        </span>
   </div>
   <div class="col-xs-3">
     <button type="button" class="btn btn-icon btn-default" onclick="lookupAffiliateOrg();"><i class="fa-search"></i>Look Up</button>
   </div>
 </div>
 
 <div class="form-group">
   <label for="registryUserWebDTO.prsOrgName" class="col-xs-4 control-label"><fmt:message key="register.user.prsOrgName"/></label>
   <div class="col-xs-7">
     <s:textfield type="text" cssClass="form-control" id="registryUserWebDTO.prsOrgName"  name="registryUserWebDTO.prsOrgName"  maxlength="200" size="100"/>
     <span class="formErrorMsg">
            <s:fielderror>
                <s:param>registryUserWebDTO.prsOrgName</s:param>
            </s:fielderror>
        </span>
   </div>
 </div>
 <div class="form-group">
   <label for="registryUserWebDTO.enableEmails" class="col-xs-4 control-label"><fmt:message key="register.user.enableEmails"/></label>
   <div class="col-xs-7">
     <label class="radio-inline">
       <input type="radio" id="registryUserWebDTO.enableEmails" value="true" id="inlineRadio1" selected="%{registryUserWebDTO.enableEmails or registryUserWebDTO.id ==null}"/>
       Yes </label>
     <label class="radio-inline">
       <input type="radio" id="registryUserWebDTO.enableEmails" value="false" id="inlineRadio2" selected="%{registryUserWebDTO.enableEmails or registryUserWebDTO.id ==null}"/>
       No </label>
   </div>
 </div>