<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="boxouter">
<h2>Person Details</h2>
<s:form action="no.action" id="personReadOnlyDetailForm">
<div class="boxouter">
<h2>Basic Identifying Information</h2>
    <div class="box_white">
        <po:inputRow>
        <po:inputRowElement><po:field labelKey="person.id">${person.id}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusCode">${person.statusCode}</po:field></po:inputRowElement>
        <po:inputRowElement>&nbsp;</po:inputRowElement>
        <po:inputRowElement><po:field labelKey="person.statusDate"><s:date name="person.statusDate" format="yyyy-MM-dd" /></po:field></po:inputRowElement>
        </po:inputRow>    
        <po:field labelKey="person.prefix">${person.prefix}</po:field>
        <po:field labelKey="person.firstName" fieldRequired="true">${person.firstName}</po:field>
        <po:field labelKey="person.middleName">${person.middleName}</po:field>
        <po:field labelKey="person.lastName" fieldRequired="true">${person.lastName}</po:field>
        <po:field labelKey="person.suffix">${person.suffix}</po:field>
        <div class="clear"></div>
    </div>
</div>
<div class="boxouter">
<h2>Address Information</h2>
    <div class="box_white">
        <po:address address="${person.postalAddress}" />
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
                    <s:iterator value="person.email">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Phone Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="person.phone">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Fax Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="person.fax">
                        <li>${value}</li>
                    </s:iterator>
                </ul>           
            </div>
        </fieldset>
        <fieldset>
            <legend>TTY Numbers</legend>
            <div>
                <ul>
                    <s:iterator value="person.tty">
                        <li>${value}</li>
                    </s:iterator>
                </ul>
            </div>
        </fieldset>
        <fieldset>
            <legend>Web Sites</legend>
            <div>
                <ul>
                    <s:iterator value="person.url">
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
	    <po:button href="javascript://nop/" onclick="$('duplicateSearchResultDetails').hide(); $('findDuplicates').show();" style="continue" text="Back to Search Results" id="selector_person_back_to_search_results"/>
        <c:set var="personFullName">${func:escapeJavaScript(person.lastName)}, ${func:escapeJavaScript(person.firstName)} ${func:escapeJavaScript(person.middleName)}</c:set>
	    <po:button href="javascript://nop/" onclick="selectAndClose(new IdValue('${person.id}',  '${personFullName}' ));" style="reject" text="Select" id="selector_select_person"/>
	</po:buttonRow>
</div>