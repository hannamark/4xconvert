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
                <legend>
                    <c:if test="${cr.emailChanged}">
                        <span class="changed">*</span>
                    </c:if>
                    Email Addresses
                </legend>
                <div>
                    <ul>
                        <s:iterator value="cr.email" status="e">
                        
                            <c:if test="${!isReadonly && cr.noChange}">
                                <po:removeButton id="remove_emailEntry_value${e.index}" onclick="removeChangeRequest();"
                                    buttonStyle="float:right;"/>                              
                            </c:if>
                            
                            <c:choose>
				              <c:when test="${isReadonly}">
				                <li>${value}</li>
				              </c:when>
				              <c:otherwise>
				                <po:copyButton id="copy_emailEntry_value${e.index}" onclick="addValue('${func:escapeJavaScript(value)}', 'emailEntry_value','email-add');" 
                                    bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
	                               <li>${value}</li>
	                            </po:copyButton>
				              </c:otherwise>
				            </c:choose>
                        </s:iterator>
                    </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>
                    <c:if test="${cr.phoneChanged}">
                        <span class="changed">*</span>
                    </c:if>
                    Phone Numbers
                </legend>
                <div>
                    <ul>
                        <s:iterator value="cr.phone" status="e">
                        
                            <c:if test="${!isReadonly && cr.noChange}">
                                <po:removeButton id="remove_phoneEntry_value${e.index}" onclick="removeChangeRequest();"
                                    buttonStyle="float:right;"/>                               
                            </c:if>
				                                   
                            <c:choose>
                              <c:when test="${isReadonly}">
                                <li>${value}</li>
                              </c:when>
                              <c:otherwise>
                                <po:copyButton id="copy_phoneEntry_value${e.index}" onclick="addValue('${func:escapeJavaScript(value)}', 'phoneEntry_value','phone-add');" 
                                    bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
	                                <li>${value}</li>
	                            </po:copyButton>
                              </c:otherwise>
                            </c:choose>
                            
                        </s:iterator>
                    </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>
                <c:if test="${cr.faxChanged}">
                        <span class="changed">*</span>
                </c:if>
                Fax Numbers</legend>
                <div>
                    <ul>
                        <s:iterator value="cr.fax" status="e">
                            <c:if test="${!isReadonly && cr.noChange}">
                                <po:removeButton id="remove_faxEntry_value${e.index}" onclick="removeChangeRequest();"
                                    buttonStyle="float:right;"/>                               
                            </c:if>
				            
                            <c:choose>
                              <c:when test="${isReadonly}">
                                <li>${value}</li>
                              </c:when>
                              <c:otherwise>
                                <po:copyButton id="copy_faxEntry_value${e.index}" onclick="addValue('${func:escapeJavaScript(value)}', 'faxEntry_value','fax-add');" 
	                                bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
	                                <li>${value}</li>
	                            </po:copyButton> 
                              </c:otherwise>
                            </c:choose>                                              
                        </s:iterator>
                    </ul>           
                </div>
            </fieldset>
            <fieldset>
                <legend>
                    <c:if test="${cr.ttyChanged}">
                        <span class="changed">*</span>
                    </c:if>
                     TTY Numbers</legend>
                <div>
                    <ul>
                        <s:iterator value="cr.tty" status="e">
                        
				            <c:if test="${!isReadonly && cr.noChange}">
                                <po:removeButton id="remove_ttyEntry_value${e.index}" onclick="removeChangeRequest();"
                                    buttonStyle="float:right;"/>                                
				            </c:if>
				                                   
                            <c:choose>
                              <c:when test="${isReadonly}">
                                <li>${value}</li>
                              </c:when>
                              <c:otherwise>
                                <po:copyButton id="copy_ttyEntry_value${e.index}" onclick="addValue('${func:escapeJavaScript(value)}', 'ttyEntry_value','tty-add');" 
	                                bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
	                                <li>${value}</li>
	                            </po:copyButton>   
                              </c:otherwise>
                            </c:choose>                           
                        </s:iterator>
                    </ul>
                </div>
            </fieldset>
            <fieldset>
                <legend>
                    <c:if test="${cr.urlChanged}">
                        <span class="changed">*</span>
                    </c:if>
                    Web Sites
                </legend>
                <div>
                    <ul>
                        <s:iterator value="cr.url" status="e">
                        
				            <c:if test="${!isReadonly && cr.noChange}">
                                <po:removeButton id="remove_urlEntry_value${e.index}" onclick="removeChangeRequest();"
                                    buttonStyle="float:right;"/> 
				            </c:if>				                                    
                        
                            <c:choose>
                              <c:when test="${isReadonly}">
                                <li>${value}</li>
                              </c:when>
                              <c:otherwise>
                                <po:copyButton id="copy_urlEntry_value${e.index}" onclick="addValue('${func:escapeJavaScript(value)}', 'urlEntry_value','url-add');" 
	                                bodyStyle="clear:left;float:left;" buttonStyle="float:right;margin-left:10px;">
	                                <li>
	                                    <a href="${value}" target="_blank"><s:property value="@java.net.URLDecoder@decode(value)" /></a>
	                                </li>
	                            </po:copyButton> 
                              </c:otherwise>
                            </c:choose>                                                 
                        </s:iterator>
                    </ul>
                </div>
            </fieldset>  
        </div>
	</div>