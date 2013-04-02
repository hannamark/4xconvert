<%@ tag display-name="contacts" description="Renders the contactable form" body-content="empty" %>
<%@ attribute name="contactableKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="emailRequired" type="java.lang.Boolean" required="false" description="By default email is required"%>
<%@ attribute name="phoneRequired" type="java.lang.Boolean" required="false" description="By default phone is not required"%>
<%@ attribute name="emailOrPhoneRequired" type="java.lang.Boolean" required="false" description="Either email or phone required"%>
<%@ attribute name="readonly" type="java.lang.Boolean" required="false" description="Display read-only view, if not provied readonly is false"%>
<%@ attribute name="defaultEmails" type="java.lang.Boolean" required="false" description="Use player's emails to pre-populate with."%>
<%@ attribute name="defaultPhones" type="java.lang.Boolean" required="false" description="Use player's phones to pre-populate with."%>
<%@ attribute name="defaultFaxes" type="java.lang.Boolean" required="false" description="Use player's faxes to pre-populate with."%>
<%@ attribute name="usOrCanadaFormatForValidationOnly" type="java.lang.Boolean" required="false" description="Whether US/Canada format should be used for validation only."%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://ctrp.nci.nih.gov/taglib/utility-functions" prefix="func" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:if test="%{#attr.emailRequired == null || #attr.emailRequired == true}">
<s:set name="emailRequiredBool" value="true"/>
</s:if>
<s:else>
<s:set name="emailRequiredBool" value="false"/>
</s:else>

<s:if test="%{#attr.phoneRequired == null || #attr.phoneRequired == false}">
<s:set name="phoneRequiredStyle" value="'display:none'"/>
</s:if>
<s:else>
<s:set name="phoneRequiredStyle" value=""/>
</s:else>

<s:if test="%{#attr.emailOrPhoneRequired == null || #attr.emailOrPhoneRequired == false}">
    <s:set name="phoneOrEmailRequiredStyle" value="'display:none'"/>
</s:if>
<s:else>
    <s:set name="phoneOrEmailRequiredStyle" value=""/>
</s:else>


<s:if test="%{#attr.readonly == true}">
<s:set name="readonlyBool" value="true"/>
</s:if>
<s:else>
<s:set name="readonlyBool" value="false" />
</s:else>
<script type="text/javascript">
<!--
function clearErrorMessages() {
	$$('span.errorMessage').invoke('remove');
	
}
function isTelecomFieldsBlank() {
     //creating a few similar objects
     var emailMsg = {field: '<s:text name="emailEntry.value"/>'};
     var phoneMsg = {field: '<s:text name="phoneEntry.value"/>'};
     var faxMsg = {field: '<s:text name="faxEntry.value"/>'};
     var ttyMsg = {field: '<s:text name="ttyEntry.value"/>'};
     var urlMsg = {field: '<s:text name="urlEntry.value"/>'};

     //the template  
     var templ = new Template('#{field} contains a value that has not been added. Do you want to submit the form and lose this data?');

     //let's format each object
     [emailMsg, phoneMsg, faxMsg, ttyMsg, urlMsg].each( function(conv){
         templ.evaluate(conv);
     });
  
     var continueResult = true; 
     if( ($F('emailEntry_value').length != 0)) {
         continueResult = continueResult && confirm(templ.evaluate(emailMsg));
     }
     if(($F('phoneEntry_value').length != 0)) {
         continueResult = continueResult && confirm(templ.evaluate(phoneMsg));
     }
     if(($F('faxEntry_value').length != 0)) {
         continueResult = continueResult && confirm(templ.evaluate(faxMsg));
     }
     if(($F('ttyEntry_value').length != 0)) {
         continueResult = continueResult && confirm(templ.evaluate(ttyMsg));
     }
     if(($F('urlEntry_value').length != 0)) {
         continueResult = continueResult && confirm(templ.evaluate(urlMsg));
     }
     return continueResult;
 }
//-->
</script>
    <span id="onload_phone_or_email_required" style="${phoneOrEmailRequiredStyle}">
        <span class="required">*</span>&nbsp; Please note: either an email address or a phone number is required.
    </span>
<fieldset>    
    <legend><s:if test="%{emailRequiredBool}"><span class="required">*</span>&nbsp;</s:if>Email Addresses</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.email'}"/>
   </s:fielderror>
    <c:url value="contactable/email/edit.action" var="viewEmailAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
        <c:param name="usePlayerContactsAsDefault" value="${defaultEmails}"/>
    </c:url>
    <div id="email-list">Loading...</div>
    <script>
        loadDiv('${viewEmailAction}', 'email-list');
    </script>
</fieldset>

<fieldset>
    <legend>
        <span id="onload_phone_number_required" class="required" style="${phoneRequiredStyle}">*&nbsp;</span>         
        Phone Numbers
    </legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.phone'}"/>
   </s:fielderror>
    <c:url value="contactable/phone/edit.action" var="viewPhoneAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
        <c:param name="create" value="${isCreate}"/>
        <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
        <c:param name="usePlayerContactsAsDefault" value="${defaultPhones}"/>
    </c:url>
    <div id="phone-list">Loading...</div>
    <script>
        loadDiv('${viewPhoneAction}', 'phone-list');
    </script>
</fieldset>

<fieldset>
    <legend>Fax Numbers</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.fax'}"/>
   </s:fielderror>
    <c:url value="contactable/fax/edit.action" var="viewFaxAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
        <c:param name="create" value="${isCreate}"/>
        <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
        <c:param name="usePlayerContactsAsDefault" value="${defaultFaxes}"/>
    </c:url>
    <div id="fax-list">Loading...</div>
    <script>
        loadDiv('${viewFaxAction}', 'fax-list');
    </script>
</fieldset>

<fieldset>
    <legend>TTY Numbers</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.tty'}"/>
   </s:fielderror>
    <c:url value="contactable/tty/edit.action" var="viewTtyAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
        <c:param name="create" value="${isCreate}"/>
        <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
    </c:url>
    <div id="tty-list">Loading...</div>
    <script>
        loadDiv('${viewTtyAction}', 'tty-list');
    </script>
</fieldset>

<fieldset>
    <legend>Web Sites</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.url'}"/>
   </s:fielderror>
    <c:url value="contactable/url/edit.action" var="viewUrlAction">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="readonly" value="${readonlyBool}"/>
    </c:url>
    <div id="url-list">Loading...</div>
    <script>
        loadDiv('${viewUrlAction}', 'url-list');
    </script>
</fieldset>