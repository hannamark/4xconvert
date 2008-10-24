<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.email" status="e">
        <c:url var="removeAction" value="../../contactable/email/remove.action">
            <c:param name="entry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="email-entry-${e.index}">
            ${value}
            | <a id="email-remove-${e.index}" href="javascript://noop/" onclick="return loadDiv('${removeAction}', 'email-list')" >Remove</a>
        </li>
    </s:iterator>

    <c:url var="addAction" value="../../contactable/email/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="emailEntry.value" onkeypress="return submitDivOnReturn(event, 'email-add');">
            <s:param name="after">
                <a id="email-add" class="formElementButton" href="javascript://noop/" onclick="return submitDivAsForm('${addAction}', 'email-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</ul>
