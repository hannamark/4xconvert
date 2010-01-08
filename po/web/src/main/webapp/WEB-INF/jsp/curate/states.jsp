<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<s:if test="country.states.isEmpty()">
    <s:textfield
            id="%{field}"
            name="%{field}" size="38"
            label="%{getText(field)}"
            value="%{value}"
            />
</s:if>
<s:else>
    <po:field labelKey="${field}" fieldRequired="${fieldRequired}">
    <select id="${field}" name="${field}">
        <option value="">--Select a State--</option>
       <s:iterator value="country.states" status="statusStatus">
           <option value="<s:property value="code"/>" <s:if test="code == value">selected="selected"</s:if>><s:property value="code"/> (<s:property value="name"/>)</option>
       </s:iterator>
    </select>
    </po:field>
</s:else>