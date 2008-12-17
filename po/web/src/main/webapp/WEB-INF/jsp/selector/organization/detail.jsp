<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Organization Details</h2>
<s:form action="no.action" id="orgReadOnlyDetailForm" theme="css_xhtml_readonly">
<div class="boxouter">
<h2>Basic Identifying Information</h2>
    <div class="box_white">
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="organization.id">${organization.id}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="organization.statusCode">${organization.statusCode}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="organization.statusDate"><s:date name="organization.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
        </po:inputRow>    
        <s:textfield key="organization.name" required="true" cssClass="required" size="70"/>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Address Information</h2>
    <div class="box_white">
        <po:addressForm formNameBase="orgReadOnlyDetailForm" addressKeyBase="organization.postalAddress" address="${organization.postalAddress}" required="false"/>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Contact Information</h2>
    <div class="box_white">
        <div class="clear"></div>
        <fieldset>
            <legend>Email Addresses</legend>
            <div id="email-list-ro">
            <ul>
                <s:iterator value="organization.email" status="e">
                    <li id="email-entry-${e.index}">
                        ${value}
                    </li>
                </s:iterator>
            </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Phone Numbers</legend>
            <div id="phone-list-ro">
            <ul>
                <s:iterator value="organization.phone" status="e">
                    <li id="phone-entry-${e.index}">
                        ${value}
                    </li>
                </s:iterator>
            </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Fax Numbers</legend>
            <div id="fax-list-ro">
            <ul>
                <s:iterator value="organization.fax" status="e">
                    <li id="fax-entry-${e.index}">
                        ${value}
                    </li>
                </s:iterator>
            </ul>           
            </div>
        </fieldset>
        <fieldset>
            <legend>TTY Numbers</legend>
            <div id="tty-list-ro">
            <ul>
                <s:iterator value="organization.tty" status="e">
                    <li id="tty-entry-${e.index}">
                        ${value}
                    </li>
                </s:iterator>
            </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Web Sites</legend>
            <div id="url-list-ro">
            <ul>
                <s:iterator value="organization.url" status="e">
                    <li id="url-entry-${e.index}">          
                        <a href="${value}" target="_blank"><s:property value="@java.net.URLDecoder@decode(value)" /></a>
                    </li>
                </s:iterator>
            </ul>
            </div>
        </fieldset>  
    </div>
</div>
</s:form>   
<div class="clearfloat"></div>
</div>
<div class="btnwrapper">
	<po:buttonRow>
	    <po:button href="javascript://nop/" onclick="$('duplicateSearchResultDetails').hide(); $('findDuplicates').show();" style="continue" text="Back to Search Results" />
	    <po:button href="javascript://nop/" onclick="selectAndClose(new IdValue('${organization.id}', '${organization.name}'));" style="reject" text="Select" />
	</po:buttonRow>
</div>