<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.email" status="e">
        <c:url var="removeAction" value="../../contactable/email/remove.action">
            <c:param name="emailEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="email-entry-${e.index}">
            ${value}
            <c:if test="${not readonly}">
            | <a id="email-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'email-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>
<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/email/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        
        <s:textfield key="emailEntry.value" onkeypress="return submitDivOnReturn(event, 'email-add');">
            <s:param name="after">
                <a id="email-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'email-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
