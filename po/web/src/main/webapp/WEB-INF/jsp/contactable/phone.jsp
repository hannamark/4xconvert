<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.phone" status="e">
        <c:url var="removeAction" value="../../contactable/phone/remove.action">
            <c:param name="entry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="phone-entry-${e.index}">
            ${value}
            | <a id="phone-remove-${e.index}" href="javascript://noop/" onclick="return loadDiv('${removeAction}', 'phone-list')" >Remove</a>
        </li>
    </s:iterator>

    <c:url var="addAction" value="../../contactable/phone/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="phoneEntry.value" onkeypress="return submitDivOnReturn(event, 'phone-add');">
            <s:param name="after">
                <a id="phone-add" class="formElementButton" href="javascript://noop/" onclick="return submitDivAsForm('${addAction}', 'phone-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</ul>
