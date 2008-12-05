<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="boxouter_nobottom">
    <h2>Contact Information</h2>
        <script type="text/javascript">
        function addValue(value, textId, buttonId) {
            copyValueToTextField(value, textId);
            fireEvent($(buttonId), 'click', 'onclick');
        }
        </script>
        <div class="box_white">
            <div class="clear"></div>
            <fieldset>
                <legend>Email Addresses</legend>
                <div id="email-list-ro">
                <ul>
                    <s:iterator value="cr.email" status="e">
                        <po:copyButton id="copy_emailEntry_value${e.index}" onclick="addValue('${value}', 'emailEntry_value','email-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="email-entry-${e.index}">
                            ${value}
                        </li>
                        </po:copyButton>
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Phone Numbers</legend>
                <div id="phone-list-ro">
                <ul>
                    <s:iterator value="cr.phone" status="e">
                       <po:copyButton id="copy_phoneEntry_value${e.index}" onclick="addValue('${value}', 'phoneEntry_value','phone-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="phone-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Fax Numbers</legend>
                <div id="fax-list-ro">
                <ul>
                    <s:iterator value="cr.fax" status="e">
                       <po:copyButton id="copy_faxEntry_value${e.index}" onclick="addValue('${value}', 'faxEntry_value','fax-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="fax-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>           
                </div>
            </fieldset>
            <fieldset>
                <legend>TTY Numbers</legend>
                <div id="tty-list-ro">
                <ul>
                    <s:iterator value="cr.tty" status="e">
                       <po:copyButton id="copy_ttyEntry_value${e.index}" onclick="addValue('${value}', 'ttyEntry_value','tty-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="tty-entry-${e.index}">
                            ${value}
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>Web Sites</legend>
                <div id="url-list-ro">
                <ul>
                    <s:iterator value="cr.url" status="e">
                       <po:copyButton id="copy_urlEntry_value${e.index}" onclick="addValue('${value}', 'urlEntry_value','url-add');" 
                            bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
                        <li id="url-entry-${e.index}">          
                            <s:property value="@java.net.URLDecoder@decode(value)" />
                            | <a href="${value}" target="_blank">Visit...</a>
                        </li>
                       </po:copyButton>                    
                    </s:iterator>
                </ul>
                </div>
            </fieldset>  
        </div>
	</div>