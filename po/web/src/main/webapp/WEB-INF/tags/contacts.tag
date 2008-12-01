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

<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.email'}"/>
   </s:fielderror>
    <legend><s:if test="%{emailRequiredBool}"><span class="required">*</span>&nbsp;</s:if>Email Addresses</legend>
    <c:url value="contactable/email/edit.action" var="viewEmailAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="email-list">Loading...</div>
    <script>
        loadDiv('${viewEmailAction}', 'email-list');
    </script>
</fieldset>

<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.phone'}"/>
   </s:fielderror>
    <legend><s:if test="%{phoneRequiredBool}"><span class="required">*</span>&nbsp;</s:if>Phone Numbers</legend>
    <c:url value="contactable/phone/edit.action" var="viewPhoneAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="phone-list">Loading...</div>
    <script>
        loadDiv('${viewPhoneAction}', 'phone-list');
    </script>
</fieldset>

<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.fax'}"/>
   </s:fielderror>
    <legend>Fax Numbers</legend>
    <c:url value="contactable/fax/edit.action" var="viewFaxAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="fax-list">Loading...</div>
    <script>
        loadDiv('${viewFaxAction}', 'fax-list');
    </script>
</fieldset>

<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.tty'}"/>
   </s:fielderror>
    <legend>TTY Numbers</legend>
    <c:url value="contactable/tty/edit.action" var="viewTtyAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="tty-list">Loading...</div>
    <script>
        loadDiv('${viewTtyAction}', 'tty-list');
    </script>
</fieldset>

<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.url'}"/>
   </s:fielderror>
    <legend>Web Sites</legend>
    <c:url value="contactable/url/edit.action" var="viewUrlAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="url-list">Loading...</div>
    <script>
        loadDiv('${viewUrlAction}', 'url-list');
    </script>
</fieldset>