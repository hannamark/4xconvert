<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<reg-web:failureMessage/>
<s:if test="fundingDtos != null && fundingDtos.size() > 0">
<table class="form">
                    <tbody> 
                       <tr>
                            <th>Funding Mechanism</th>
                            <th>Institute Code</th>
                            <th>Serial Number</th>
                            <th>NCI Division/Program Code</th>
                         </tr>
                        <s:iterator id="fundingDtos" value="fundingDtos" status="fundstats">
                       <tr>
                            <s:set name="fundingMechanismValues" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getFundingMechanisms()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="fundingDtos[%{#fundstats.index}].fundingMechanismCode" 
                                     list="#fundingMechanismValues"                             
                                     listKey="fundingMechanismCode"  
                                     listValue="fundingMechanismCode" 
                                     id="fundingMechanismCode"
                                     value="%{fundingMechanismCode}" 
                                     cssStyle="width:150px" />
                                     <span class="formErrorMsg" >
                                        <s:fielderror>
                                        <s:param>updfundingMechanismCode</s:param>
                                       </s:fielderror>                            
                                     </span>
                            </td>
                            <s:set name="nihInstituteCodes" value="@gov.nih.nci.pa.util.PaRegistry@getLookUpTableService().getNihInstitutes()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" 
                                     name="fundingDtos[%{#fundstats.index}].nihInstitutionCode" 
                                     list="#nihInstituteCodes"
                                     listKey="nihInstituteCode" 
                                     listValue="nihInstituteCode"
                                     id="nihInstitutionCode"
                                     value="%{nihInstitutionCode}" 
                                     cssStyle="width:150px"  />
                                     <span class="formErrorMsg" >
                                        <s:fielderror>
                                        <s:param>updnihInstitutionCode</s:param>
                                       </s:fielderror>                            
                                     </span>
                            </td>
                            <td>
                                <s:textfield name="fundingDtos[%{#fundstats.index}].serialNumber" value="%{serialNumber}" id="serialNumber" maxlength="200" size="100"  cssStyle="width:150px"  />
                                <span class="formErrorMsg"> 
                                    <s:fielderror>
                                    <s:param>updserialNumber</s:param>
                                    </s:fielderror>                            
                                </span>
                            </td>
                            <s:set name="fundingDTOs.programCodes" value="@gov.nih.nci.pa.enums.NciDivisionProgramCode@getDisplayNames()" />
                            <td>                                             
                                <s:select headerKey="" headerValue="--Select--" name="fundingDtos[%{#fundstats.index}].nciDivisionProgramCode" id="nciDivisionProgramCode" list="#programCodes"  value="%{nciDivisionProgramCode}" cssStyle="width:150px" />
                                <span class="formErrorMsg"> 
                                   <s:fielderror>
                                   <s:param>updnciDivisionProgramCode</s:param>
                                  </s:fielderror>                            
                                </span>
                                <s:hidden  name="fundingDtos[%{#fundstats.index}].id" value="%{id}"/>      
                            </td>
                           </tr>
                      </s:iterator>
                      </tbody>
                    </table>
  </s:if>