<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Person Information</h2>
    <div class="box_white">
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="person.id">${person.id}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusCode">${person.statusCode}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusDate"><s:date name="person.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
        </po:inputRow>
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="person.firstName">${person.firstName}</po:field></po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.lastName">${person.lastName}</po:field></po:inputRowElement>
        </po:inputRow>
        <div class="clear"></div>
    </div>
</div>