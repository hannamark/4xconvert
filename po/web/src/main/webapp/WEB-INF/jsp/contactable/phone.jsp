<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />

<div id="no_format_phone" style="display:none"> 
<ul>
<s:iterator value="contactable.phone" status="e">
        <c:url var="removeAction" value="../../contactable/phone/remove.action">
            <c:param name="phoneEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="false"/> 
        </c:url>
        <li id="phone-entry-${e.index}">
            ${value}
            <c:if test="${not readonly}">
            | <a id="phone-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'phone-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>

<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/phone/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="usOrCanadaFormat" value="false"/> 
    </c:url>
    <li>
        <s:textfield key="phoneEntry.value" onkeypress="return submitDivOnReturn(event, 'phone-add');">
            <s:param name="after">
                <a id="phone-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return submitDivAsForm('${addAction}', 'phone-list')">Add</a>
            </s:param>
        </s:textfield>
    </li>
</c:if>
</ul>
</div>

<div id="us_format_phone" style="display:none">
<ul>
    <s:iterator value="contactable.phone" status="e">
        <c:url var="removeAction" value="../../contactable/phone/remove.action">
            <c:param name="phoneEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="true"/> 
        </c:url>
        <li id="phone-entry-${e.index}">
            ${value}
            <c:if test="${not readonly}">
            | <a id="phone-remove-${e.index}" href="javascript://noop/" onclick="clearErrorMessages(); return loadDiv('${removeAction}', 'phone-list')" >Remove</a>
            </c:if>
        </li>
    </s:iterator>

<c:if test="${not readonly}">
    <c:url var="addAction" value="../../contactable/phone/add.action">
        <c:param name="rootKey" value="${rootKey}"/>
        <c:param name="usOrCanadaFormat" value="true"/> 
    </c:url>
    <li>
        <s:hidden id="phoneEntry_value" name="phoneEntry.value" value=""/>
        <po:inputRow>
            <po:inputRowElement>
                <s:textfield key="phoneEntry.value" name="phoneEntry.part1" size="3" maxlength="3" onkeyup="autotab(this, $('phoneEntry_part2'));"/>
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield name="phoneEntry.part2" size="3" maxlength="3" onkeyup="autotab(this, $('phoneEntry_part3'));"/> 
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield name="phoneEntry.part3" size="4" maxlength="4" onkeyup="autotab(this, $('phoneEntry_part4'));"/>
            </po:inputRowElement>
            <po:inputRowElement>
                &nbsp;x&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
	            <s:textfield size="5" name="phoneEntry.part4" onkeypress="return submitDivOnReturn(event, 'phone-add');">
		            <s:param name="after">
		                <a id="phone-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return assembleAndSubmitPhoneNumber('phone', '${addAction}', 'phone-list')">Add</a>
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
    $('no_format_phone').hide();
    $('us_format_phone').show();
} else {
    $('no_format_phone').show();
    $('us_format_phone').hide();
}
-->
</script>