<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Person Information</h2>
    <div class="box_white">
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="person.id">${person.id}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusCode">${person.statusCode}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusDate"><s:date name="person.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
        </po:inputRow>
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="person.firstName">${person.firstName}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.middleName">${person.middleName}&nbsp;</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.lastName">${person.lastName}</po:field></po:inputRowElement>
        </po:inputRow>
        <fieldset>
            <legend>Email Addresses</legend>
            <div>
                <ul>
                    <s:iterator value="person.email">
                        <li>${value}</li>
                    </s:iterator>
               </ul>
            </div>
        </fieldset>        
        <div class="clear"></div>
    </div>
</div>