<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<fieldset>
    <legend>Email Addresses</legend>
    <c:url value="contactable/email/edit.action" var="viewEmailAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="email-list">Loading...</div>
    <script>
        loadDiv('${viewEmailAction}', 'email-list');
    </script>
</fieldset>

<fieldset>
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
    <legend>Web Sites</legend>
    <c:url value="contactable/url/edit.action" var="viewUrlAction">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <div id="url-list">Loading...</div>
    <script>
        loadDiv('${viewUrlAction}', 'url-list');
    </script>
</fieldset>