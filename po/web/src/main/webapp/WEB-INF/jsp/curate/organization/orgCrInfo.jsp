<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="boxouter">
    <h2>Change Request Information</h2>
    <s:form action="ajax/organization/curate/no.action" id="curateOrgCrForm" theme="css_xhtml_readonly">
    <div class="boxouter">
    <h2>Basic Identifying Information</h2>
        <div class="box_white">
            <s:textfield key="cr.name" required="true" cssClass="required" size="70"/>
            <s:textfield key="cr.abbreviatedName" required="false" cssClass="required" size="70"/>
            <s:textfield key="cr.description" required="false" cssClass="required" size="70"/>
            <div class="clear"></div>
        </div>
    </div>
    <div class="boxouter">
    <h2>Address Information</h2>
        <div class="box_white">
            <po:addressForm addressKeyBase="cr.postalAddress" address="${cr.postalAddress}"/>
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
                    <s:iterator value="cr.email" status="e">
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
                    <s:iterator value="cr.phone" status="e">
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
                    <s:iterator value="cr.fax" status="e">
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
                    <s:iterator value="cr.tty" status="e">
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
                    <s:iterator value="cr.url" status="e">
                        <li id="url-entry-${e.index}">          
                            <s:property value="@java.net.URLDecoder@decode(value)" />
                            | <a href="${value}" target="_blank">Visit...</a>
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