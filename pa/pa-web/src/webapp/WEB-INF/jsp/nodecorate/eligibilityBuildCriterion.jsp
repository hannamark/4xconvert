<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<table class="form">
<tr>
    <th colspan="3"><fmt:message key="isdesign.eligibilitycriteria.buildDescription" /><span class="required">*</span></th>
</tr>
            <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="cadsr.classificationCode"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:textfield name="webDTO.cdeCategoryCode" readonly="true" cssClass="readonly" cssStyle="width:100px" />
                      </td>
                 </tr> 
             <tr >
                    <td scope="row"  class="label"><label>
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriterianame"/></label>
                    </td>
                    <td>
                        <s:textfield name="webDTO.criterionName" maxlength="30" size="120" readonly="true" cssClass="readonly" cssStyle="width:200px;float:left" />
                        <s:hidden name="webDTO.cdePublicIdentifier"/>
                        <s:hidden name="webDTO.cdeVersionNumber"/>
                          <ul >              
                    <li >
                     <a href="#" class="btn" id="criteriaNameLookup" onclick="lookup();" />
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
                      <s:select name="webDTO.operator" list="#{'=':'=', '<':'<', '<=':'<=', '>':'>', '>=':'>=', 'In':'In'}" cssStyle="width:106px" value="webDTO.operator" onclick="activateMax()"/>
                    </td>         
                </tr> 
                <s:if test="permValues == null">
                <s:if test="cdeDatatype != null && cdeDatatype.equals(\"NUMBER\")">
                
                <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueInteger"/>
                        </label>
                    </td>
                    <td>
                      Min: <s:textfield name="webDTO.valueIntegerMin" maxlength="12" cssStyle="width:80px" />
                      <s:if test="%{webDTO.operator=='In'}">
                       Max: <s:textfield name="webDTO.valueIntegerMax" maxlength="12" cssStyle="width:80px" />
                       </s:if>
                       <s:else>
                        Max: <s:textfield name="webDTO.valueIntegerMax" maxlength="12" cssStyle="width:80px" disabled="true"/>
                       </s:else>
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.valueIntegerMin</s:param>
                                </s:fielderror>                            
                         </span>
                     </td> 
                 </tr>
                 </s:if>
                 <s:if test="cdeDatatype == null || !cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueText"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:textfield name="webDTO.valueText" maxlength="12" cssStyle="width:100px" />
                      </td>
                 </tr>
                 </s:if>
                 </s:if>
                 <s:if test="permValues != null">
                 <s:if test="cdeDatatype != null && cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueInteger"/>
                        </label>
                    </td>
                    <td>
                      Min: <s:select headerKey="" headerValue="" name="webDTO.valueIntegerMin" value="webDTO.valueIntegerMin"
                list="permValues"  cssStyle="width:150px" />
                <s:if test="%{webDTO.operator=='In'}">
                      Max: <s:select headerKey="" headerValue="" name="webDTO.valueIntegerMax" value="webDTO.valueIntegerMax"
                list="permValues" cssStyle="width:150px" /> 
                 </s:if>
                  <s:else>
                    Max: <s:select headerKey="" headerValue="" name="webDTO.valueIntegerMax" value="webDTO.valueIntegerMax"
                list="permValues" cssStyle="width:150px" disabled="true"/> 
                  </s:else>
                                <s:fielderror>
                                <s:param>webDTO.valueIntegerMin</s:param>
                                </s:fielderror>                            
                         </span>
                     </td> 
                 </tr>
                 </s:if>
                 <s:if test="cdeDatatype == null || !cdeDatatype.equals(\"NUMBER\")">
                  <tr>
                     <td scope="row" class="label">
                        <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.valueText"/>
                        </label>
                    </td>
                    <td class="value">
                        <s:select headerKey="" headerValue="" name="webDTO.valueText" value="webDTO.valueText"
                list="permValues"  cssStyle="width:150px" />
                      </td>
                 </tr>
                 </s:if>
                 </s:if>
                 <tr>
                      <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="isdesign.eligibilitycriteria.unit"/>
                     </label>
                   </td>
                    <td class="value">
                    <div id="loadUOMDetails">
                        <%@ include file="/WEB-INF/jsp/nodecorate/displayUOM.jsp"%>
                    </div> 
                    </td> 
                      <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.buldcriterion</s:param>
                               </s:fielderror>                            
                         </span>           
                </tr> 
                  <tr>
                      <td scope="row" class="label">
                     
                   </td>
                    <td class="value">
                    <s:a href="#" cssClass="btn" id="generateTextButton" onclick="handleGenerateCriteriaText()"><span class="btn_img"><span class="save">Generate Criteria Text</span></span></s:a>
                </tr> 
            <tr>
                <th colspan="2"><fmt:message key="isdesign.eligibilitycriteria.description"/></th>              
                </tr>
                <tr>
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.mandatory</s:param>
                               </s:fielderror>                            
                         </span>
                    <td scope="row"  class="label"><label>
                        <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriadescription"/>(Max 5,000 chars)</label>
                    </td>
                    <td class="value">
                        <s:textarea name="webDTO.textDescription" rows="6" cssStyle="width:600px" onblur='activate();' />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.TextDescription</s:param>
                               </s:fielderror>                            
                         </span>
                    </td>
                </tr>           
                 
</table>                 