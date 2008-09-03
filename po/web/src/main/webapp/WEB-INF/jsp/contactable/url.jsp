<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:successMessages />
<ul>
    <s:iterator value="contactable.url" status="e">
        <c:url var="removeAction" value="../../contactable/url/remove.action">
            <c:param name="entry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
        </c:url>
        <li id="url-entry-${e.index}">          
            <s:property value="@java.net.URLDecoder@decode(value)" />
            | <a id="url-remove-${e.index}" href="javascript://noop/" onclick="return loadDiv('${removeAction}', 'url-list')">Remove</a>
            | <a href="${value}" target="_blank">Visit...</a>
        </li>
    </s:iterator>

    <c:url var="addAction" value="../../contactable/url/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
    </c:url>
    <li>
        <s:textfield key="urlEntry.value" onkeypress="return submitDivOnReturn(event, 'url-add');">
            <s:param name="after">
                <a id="url-add" class="formElementButton" href="javascript://noop/" onclick="return submitDivAsForm('${addAction}', 'url-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</ul>
