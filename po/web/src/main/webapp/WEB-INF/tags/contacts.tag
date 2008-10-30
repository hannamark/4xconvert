<%@ tag display-name="contacts" description="Renders the contactable form" body-content="empty" %>
<%@ attribute name="contactableKeyBase" type="java.lang.String" required="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<fieldset>
   <s:fielderror>
        <s:param value="%{#attr.contactableKeyBase + '.email'}"/>
   </s:fielderror>
    <legend><span class="required">*</span>&nbsp;Email Addresses</legend>
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
    <legend>Phone Numbers</legend>
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
    <legend><span class="required">*</span>&nbsp;Web Sites</legend>
    <c:url value="contactable/url/edit.action" var="viewUrlAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="url-list">Loading...</div>
    <script>
        loadDiv('${viewUrlAction}', 'url-list');
    </script>
</fieldset>