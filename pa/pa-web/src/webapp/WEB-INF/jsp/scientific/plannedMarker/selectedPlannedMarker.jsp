<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<table class="form">
    <s:hidden name="currentAction" />
    <s:hidden name="plannedMarker.id" />
    <s:hidden id="foundInHugo" name="plannedMarker.foundInHugo" />
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
        <td style="width: 250px">
            <s:set name="assayTypeValues" value="@gov.nih.nci.pa.enums.AssayTypeCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="" name="plannedMarker.assayType" id="assayType" list="#assayTypeValues"
                onchange="toggleAssayTypeOtherText();" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.assayType</s:param>
                </s:fielderror>
            </span>
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
        <td style="width: 250px">
            <s:set name="assayPurposeValues" value="@gov.nih.nci.pa.enums.AssayPurposeCode@getDisplayNames()" /> 
            <s:select headerKey="" headerValue="" name="plannedMarker.assayPurpose" id="assayPurpose" list="#assayPurposeValues"
                onchange="toggleAssayPurposeOtherText()" />
            <span class="formErrorMsg">
                <s:fielderror>
                    <s:param>plannedMarker.assayPurpose</s:param>
                </s:fielderror>
            </span>
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
        <td style="width: 250px">
            <s:set name="tissueSpecimenTypeValues" value="@gov.nih.nci.pa.enums.TissueSpecimenTypeCode@getDisplayNames()" />
            <s:select headerKey="" headerValue="" name="plannedMarker.tissueSpecimenType" id="tissueSpecimenType"
                list="#tissueSpecimenTypeValues" />
            <span class="formErrorMsg"> 
                <s:fielderror>
                    <s:param>plannedMarker.tissueSpecimenType</s:param>
                </s:fielderror>
            </span>
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