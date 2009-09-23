<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<ul>
    <s:iterator value="contactable.url" status="e">
        <c:url var="removeAction" value="../../contactable/url/remove.action">
            <c:param name="urlEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="url-entry-${e.index}">          
            <a href="${value}" target="_blank"><s:property value="@java.net.URLDecoder@decode(value)" /></a>
            <c:if test="${not readonly}">
            | <a id="url-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'url-list')">Remove</a>
            </c:if>
        </li>
    </s:iterator>
<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/url/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="urlEntry.value" onkeypress="return submitDivOnReturn(event, 'url-add');">
            <s:param name="after">
                <a id="url-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'url-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
