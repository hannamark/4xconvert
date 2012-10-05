<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<table class="form">
    <s:hidden name="currentAction" />
    <s:hidden name="plannedMarker.id" />
    <s:hidden id="foundInHugo" name="plannedMarker.foundInHugo" />
    <s:hidden id="saveReset" name="saveReset"/>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.name"><fmt:message key="plannedMarker.name" />:</s:label><span class="required">*</span>
        </td>
        <td class="value" style="width: 250px">
            <s:textfield name="plannedMarker.name" id="name" maxlength="200" size="200" cssStyle="width:250px" />
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.name</s:param>
                </s:fielderror>
            </span>
        </td>
        <td class="value">
            <ul style="margin-top: -6px;">
                <li style="padding-left: 0">
                    <s:a href="javascript:void(0)" cssClass="btn" id="cadsrLookup" onclick="cadsrLookup();">
                        <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.caDSR"/></span></span>
                    </s:a>
                </li>
            </ul>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.meaning"><fmt:message key="plannedMarker.meaning" />:</s:label>
        </td>
        <td>
            <s:hidden name="plannedMarker.meaning" id="meaning"/>
            <s:property value="plannedMarker.meaning"/>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.description"><fmt:message key="plannedMarker.description" />:</s:label>
        </td>
        <td>
            <s:hidden name="plannedMarker.description" id="description"/>
            <s:property value="plannedMarker.description"/>
        </td>
    </tr>
    <tr id="hugoCodeRow" style="display:none">
        <td class="label">
            <s:label for="plannedMarker.hugoCode"><fmt:message key="plannedMarker.hugoCode"/>:</s:label>
        </td>
        <td>
            <s:hidden name="plannedMarker.hugoCode" id="hugoCode" />
            <s:property value="plannedMarker.hugoCode"/>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.assayType"><fmt:message key="plannedMarker.assayType" />:</s:label><span class="required">*</span>
        </td>
        <td style="border:1px solid lavender">
            <s:set name="assayTypeValues" value="@gov.nih.nci.pa.enums.AssayTypeCode@getDisplayNames()" />
            <s:set name="selectedValues" value="@gov.nih.nci.pa.enums.AssayTypeCode@getSelectedDisplayNames(plannedMarker.selectedAssayType)"/>
            <s:checkboxlist list="#assayTypeValues" name="plannedMarker.assayType" value="#selectedValues" id="assayType" onclick="toggleAssayTypeOtherText(this);" theme="vertical-checkbox"/> 
            
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.assayType</s:param>
                </s:fielderror>
            </span>
        </td>
        <td>  
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>
            <br>               
            <p> Note: Users have the option to multi-select attribute values for Assay Type </p>
        </td>
    </tr>
    <tr id="assayTypeOtherTextRow" style="display: none;">
        <td class="label">
            <s:label for="plannedMarker.assayTypeOtherText"><fmt:message key="plannedMarker.assayTypeOtherText" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.assayTypeOtherText" id="assayTypeOtherText" maxlength="200" size="200" cssStyle="width:280px" /> 
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayTypeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.assayUse"><fmt:message key="plannedMarker.assayUse" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:set name="assayUseValues" value="@gov.nih.nci.pa.enums.AssayUseCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="" name="plannedMarker.assayUse" id="assayUse" list="#assayUseValues" /> 
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayUse</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.assayPurpose"><fmt:message key="plannedMarker.assayPurpose" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px;border:1px solid lavender">
            <s:set name="assayPurposeValues" value="@gov.nih.nci.pa.enums.AssayPurposeCode@getDisplayNames()" /> 
            <s:set name="selectedPurposeValues" value="@gov.nih.nci.pa.enums.AssayPurposeCode@getSelectedDisplayNames(plannedMarker.selectedAssayPurpose)"/>
          
            <s:checkboxlist list="#assayPurposeValues" name="plannedMarker.assayPurpose" value="#selectedPurposeValues" id="assayPurpose" onclick="toggleAssayPurposeOtherText(this);" theme="vertical-checkbox"/>
            
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayPurpose</s:param>
                </s:fielderror>
            </span>
        </td>
        
             
        <td class="value">
        <s:if test="%{plannedMarker.id == null}"> 
            <ul style="margin-top: -6px;">
                <li style="padding-left: 0">
                    <s:a href="javascript:void(0)" cssClass="btn" id="addVariation" onclick="addVariation('true');">
                        <span class="btn_img"><fmt:message key="plannedMarker.addVariation"/></span></span>
                    </s:a>
                </li>
            </ul>
            </s:if>      
            <br>
            <br>
            <br>      
           <p> Note: Users have the option to multi-select attribute values for Assay Purpose</p>
            
    </tr>
        </td>
        
    </tr>
    <tr id="assayPurposeOtherTextRow" style="display: none;">
        <td class="label">
            <s:label for="plannedMarker.assayPurposeOtherText"><fmt:message key="plannedMarker.assayPurposeOtherText" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:textfield name="plannedMarker.assayPurposeOtherText" id="assayPurposeOtherText" maxlength="200" size="200" cssStyle="width:280px" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.assayPurposeOtherText</s:param>
                </s:fielderror>
            </span>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.tissueSpecimenType"><fmt:message key="plannedMarker.tissueSpecimenType" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px;border:1px solid lavender">
            <s:set name="tissueSpecimenTypeValues" value="@gov.nih.nci.pa.enums.TissueSpecimenTypeCode@getDisplayNames()" />
            <s:set name="selectedTissueValues" value="@gov.nih.nci.pa.enums.TissueSpecimenTypeCode@getSelectedDisplayNames(plannedMarker.selectedTissueSpecType)"/>
           
            <s:checkboxlist list="#tissueSpecimenTypeValues" name="plannedMarker.tissueSpecimenType" value="#selectedTissueValues" id="tissueSpecimenType" theme="vertical-checkbox" />
           
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.tissueSpecimenType</s:param>
                </s:fielderror>
            </span>
        </td>
        <td style="width:350px">
            <br>
            <br>
            <br>
            <p> Note: Users have the option to multi-select attribute values for Tissue Specimen Type </p> 
            </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.tissueCollectionMethod"><fmt:message key="plannedMarker.tissueCollectionMethod" />:</s:label><span class="required">*</span>
        </td>
        <td style="width: 250px">
            <s:set name="tissueCollectionMethodValues" value="@gov.nih.nci.pa.enums.TissueCollectionMethodCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="" name="plannedMarker.tissueCollectionMethod" id="tissueCollectionMethod"
                list="#tissueCollectionMethodValues" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.tissueCollectionMethod</s:param>
                </s:fielderror> 
            </span>
        </td>
    </tr>
    <tr>
        <td class="label">
            <s:label for="plannedMarker.status"><fmt:message key="plannedMarker.status" />:</s:label>
        </td>
        <td>
            <s:hidden name="plannedMarker.status" id="status"/>
            <s:property value="plannedMarker.status"/>
        </td>
    </tr>
</table>