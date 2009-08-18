<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Organization Information</h2>
    <div class="box_white">
        <po:inputRow>
            <po:inputRowElement><po:field labelKey="organization.id">${organization.id}</po:field></po:inputRowElement>
            <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
            <po:inputRowElement><po:field labelKey="organization.statusCode">${organization.statusCode}</po:field></po:inputRowElement>
            <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
            <po:inputRowElement><po:field labelKey="organization.statusDate"><s:date name="organization.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
        </po:inputRow>
        <po:inputRow>
            <po:inputRowElement><po:field labelKey="organization.name">${organization.name}</po:field></po:inputRowElement>
        </po:inputRow>
        <div class="clear"></div>
    </div>
</div>
