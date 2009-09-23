<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.tty" status="e">
        <c:url var="removeAction" value="../../contactable/tty/remove.action">
            <c:param name="ttyEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="tty-entry-${e.index}">
            ${value}
            <c:if test="${not readonly}">
            | <a id="tty-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'tty-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>
<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/tty/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="ttyEntry.value" onkeypress="return submitDivOnReturn(event, 'tty-add');">
            <s:param name="after">
                <a id="tty-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'tty-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
