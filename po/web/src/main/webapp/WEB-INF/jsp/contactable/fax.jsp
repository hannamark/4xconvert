<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<po:errorMessages />
<c:choose>
    <c:when test="${usOrCanadaFormat && !usOrCanadaFormatForValidationOnly}">
        <c:set var="divUsOrCanadaFormatDisplayStyle" value="display:block"/>
        <c:set var="divNoFormatDisplayStyle" value="display:none"/>
    </c:when>
    <c:otherwise>
        <c:set var="divUsOrCanadaFormatDisplayStyle" value="display:none"/>
        <c:set var="divNoFormatDisplayStyle" value="display:block"/>
    </c:otherwise>
</c:choose>

<div id="no_format_fax" style="${divNoFormatDisplayStyle}">
<ul>
<s:iterator value="contactable.fax" status="e">
        <c:url var="removeAction" value="../../contactable/fax/remove.action">
            <c:param name="faxEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>
            <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
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
        <c:param name="usOrCanadaFormat" value="${usOrCanadaFormat}"/>
        <c:param name="usOrCanadaFormatForValidationOnly" value="${usOrCanadaFormatForValidationOnly}"/>
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
</div>

<div id="us_format_fax" style="${divUsOrCanadaFormatDisplayStyle}">
<ul>
    <s:iterator value="contactable.fax" status="e">
        <c:url var="removeAction" value="../../contactable/fax/remove.action">
            <c:param name="faxEntry.value" value="${value}"/>
            <c:param name="rootKey" value="${rootKey}"/>
            <c:param name="usOrCanadaFormat" value="true"/>
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
        <c:param name="usOrCanadaFormat" value="true"/>
    </c:url>
    <li>
        <s:hidden id="faxEntry_value" name="faxEntry.value" value=""/>
        <s:hidden id="faxEntry_part4" value=""/>
        <po:inputRow>
            <po:inputRowElement>
                <s:textfield key="faxEntry.value" name="" id="faxEntry_part1" size="3" maxlength="3" onkeyup="autotab(this, $('faxEntry_part2'));"/>
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield id="faxEntry_part2" size="3" maxlength="3" onkeyup="autotab(this, $('faxEntry_part3'));"/>
            </po:inputRowElement>
             <po:inputRowElement>
                &nbsp;-&nbsp;
            </po:inputRowElement>
            <po:inputRowElement>
                <s:textfield id="faxEntry_part3" size="4" maxlength="4" onkeyup="return submitDivOnReturn(event, 'fax-add');">
                <s:param name="after">
                        <a id="fax-add" class="formElementButton" href="javascript://noop/" onclick="clearErrorMessages(); return assembleAndSubmitPhoneNumber('fax', '${addAction}', 'fax-list')">Add</a>
                    </s:param>
                </s:textfield>
            </po:inputRowElement>
        </po:inputRow>

    </li>
</c:if>
</ul>
</div>
