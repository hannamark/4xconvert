             <tr >
                    <td scope="row"  class="label"><label>
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></label>
                    </td>
                    <td>
                        <s:textfield name="webDTO.criterionName" maxlength="30" size="120" cssStyle="width:200px;float:left" />
                        <s:hidden name="webDTO.cdePublicIdentifier"/>
                        <s:hidden name="webDTO.cdeVersionNumber"/>
                          <ul >              
                    <li >
                     <a href="#" class="btn" onclick="lookup();" />
                     <span class="btn_img"><span class="search">Look Up</span></span></a>
                    </li>
                </ul>
                     </td>
                  
                </tr>
                <tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="isdesign.eligibilitycriteria.operator"/>
                     </label>
                    </td>
                    <td class="value">                        
                      <s:select name="webDTO.operator" list="#{'=':'=', '<':'<', '<=':'<=', '>':'>', '>=':'>=', 'In':'In'}" cssStyle="width:106px"/>
                    </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueInteger"/>
                        </label>
                    </td>
                    <td>
                      Min: <s:textfield name="webDTO.valueIntegerMin" maxlength="12" cssStyle="width:80px" />
                      Max: <s:textfield name="webDTO.valueIntegerMax" maxlength="12" cssStyle="width:80px" />
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.valueIntegerMin</s:param>
                                </s:fielderror>                            
                         </span>
                     </td> 
                 </tr>
                  <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueText"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:textfield name="webDTO.valueText" maxlength="12" cssStyle="width:100px" onblur='inactivateText();'/>
                      </td>
                 </tr>