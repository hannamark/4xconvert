<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.fax" status="e">
        <c:url var="removeAction" value="../../contactable/fax/remove.action">
            <c:param name="faxEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="fax-entry-${e.index}">
            ${value}
            <c:if test="${not readonly}">
            | <a id="fax-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'fax-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>

<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/fax/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="faxEntry.value" onkeypress="return submitDivOnReturn(event, 'fax-add');">
            <s:param name="after">
                <a id="fax-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'fax-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
