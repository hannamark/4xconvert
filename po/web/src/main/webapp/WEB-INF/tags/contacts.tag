<%@ tag display-name="contacts" description="Renders the contactable form" body-content="empty" %>
<%@ attribute name="contactableKeyBase" type="java.lang.String" required="true" %>
<%@ attribute name="emailRequired" type="java.lang.Boolean" required="false" description="By default email is required"%>
<%@ attribute name="phoneRequired" type="java.lang.Boolean" required="false" description="By default phone is not required"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<s:if test="%{#attr.emailRequired == null || #attr.emailRequired == true}">
<s:set name="emailRequiredBool" value="true"/>
</s:if>
<s:else>
<s:set name="emailRequiredBool" value="false"/>
</s:else>

<s:if test="%{#attr.phoneRequired == null || #attr.phoneRequired == false}">
<s:set name="phoneRequiredBool" value="false"/>
</s:if>
<s:else>
<s:set name="phoneRequiredBool" value="true"/>
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
<fieldset>
    <legend><s:if test="%{emailRequiredBool}"><span class="required">*</span>&nbsp;</s:if>Email Addresses</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.email'}"/>
   </s:fielderror>
    <c:url value="contactable/email/edit.action" var="viewEmailAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="email-list">Loading...</div>
    <script>
        loadDiv('${viewEmailAction}', 'email-list');
    </script>
</fieldset>

<fieldset>
    <legend><s:if test="%{phoneRequiredBool}"><span class="required">*</span>&nbsp;</s:if>Phone Numbers</legend>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.phone'}"/>
   </s:fielderror>
    <c:url value="contactable/phone/edit.action" var="viewPhoneAction">
        <c:param name="rootKey" value="${rootKey}"/>
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
    </c:url>
    <div id="url-list">Loading...</div>
    <script>
        loadDiv('${viewUrlAction}', 'url-list');
    </script>
</fieldset>