<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />

<div id="no_format_tty" style="display:none"> 
<ul>
<s:iterator value="contactable.tty" status="e">
        <c:url var="removeAction" value="../../contactable/tty/remove.action">
            <c:param name="ttyEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="false"/> 
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
        <c:param name="usOrCanadaFormat" value="false"/> 
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
</div>

<div id="us_format_tty" style="display:none">
<ul>
    <s:iterator value="contactable.tty" status="e">
        <c:url var="removeAction" value="../../contactable/tty/remove.action">
            <c:param name="ttyEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>  
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
        <c:param name="usOrCanadaFormat" value="true"/> 
    </c:url>
    <li>
        <s:hidden id="ttyEntry_value" name="ttyEntry.value" value=""/>
        <po:inputRow>
            <po:inputRowElement>
                <s:textfield key="ttyEntry.value" name="ttyEntry.part1" size="3" maxlength="3" onkeyup="autotab(this, $('ttyEntry_part2'));"/>
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield name="ttyEntry.part2" size="3" maxlength="3" onkeyup="autotab(this, $('ttyEntry_part3'));"/> 
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield name="ttyEntry.part3" size="4" maxlength="4" onkeyup="autotab(this, $('ttyEntry_part4'));"/>
            </po:inputRowElement>
            <po:inputRowElement>
                &nbsp;x&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield size="5" name="ttyEntry.part4" onkeypress="return submitDivOnReturn(event, 'tty-add');">
                    <s:param name="after">
                        <a id="tty-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return assembleAndSubmitPhoneNumber('tty', '${addAction}', 'tty-list')">Add</a>
                    </s:param>
                </s:textfield>
            </po:inputRowElement>
        </po:inputRow>
        
    </li>
</c:if>
</ul>
</div>

<script type="text/javascript">
<!--
if (${create || usOrCanadaFormat}) {
    $('no_format_tty').hide();
    $('us_format_tty').show();
} else {
    $('no_format_tty').show();
    $('us_format_tty').hide();
}
-->
</script>