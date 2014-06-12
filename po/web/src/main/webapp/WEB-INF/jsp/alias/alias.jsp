<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="aliases" status="e">
        <c:url var="removeAction" value="../../alias/remove.action">
            <c:param name="alias.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="alias-entry-${e.index}">
            <c:out value="${value}"></c:out>
            <c:if test="${not readonly}">
            | <a id="alias-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'alias-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>
<c:if test="${not readonly}">
    <c:url var="addAction" value="../../alias/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        
        <s:textfield key="alias.value" onkeypress="return submitDivOnReturn(event, 'alias-add');">
            <s:param name="after">
                <a id="alias-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'alias-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
