<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Organization Details</h2>
<s:form action="no.action" id="orgReadOnlyDetailForm" >
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
        <po:field labelKey="organization.name" fieldRequired="true">${organization.name}</po:field>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Address Information</h2>
    <div class="box_white">
        <po:address address="${organization.postalAddress}"/>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Contact Information</h2>
    <div class="box_white">
        <div class="clear"></div>
        <fieldset>
            <legend>Email Addresses</legend>
            <div>
                <ul>
                    <s:iterator value="organization.email">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Phone Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="organization.phone">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Fax Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="organization.fax">
                        <li>${value}</li>
                    </s:iterator>
                </ul>           
            </div>
        </fieldset>
        <fieldset>
            <legend>TTY Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="organization.tty">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Web Sites</legend>
            <div>
                <ul>
                    <s:iterator value="organization.url">
                        <li>          
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
	    <po:button href="javascript://nop/" onclick="$('duplicateSearchResultDetails').hide(); $('findDuplicates').show();" style="continue" text="Back to Search Results" id="selector_org_back_to_search_results"/>
	    <po:button href="javascript://nop/" onclick="selectAndClose(new IdValue('${organization.id}', '${func:escapeJavaScript(organization.name)}'));" style="reject" text="Select" id="selector_select_org"/>
	</po:buttonRow>
</div>