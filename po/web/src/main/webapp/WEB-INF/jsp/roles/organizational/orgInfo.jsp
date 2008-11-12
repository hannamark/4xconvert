<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Organization Information</h2>
    <div class="box_white">
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="organization.id" value="${organization.id}"/></po:inputRowElement>
        <po:inputRowElement><po:field labelKey="organization.statusCode" value="${organization.statusCode}"/></po:inputRowElement>
        </po:inputRow>
        <po:inputRowElement><po:field labelKey="organization.name" value="${organization.name}"/></po:inputRowElement>
        <div class="clear"></div>
    </div>
</div>