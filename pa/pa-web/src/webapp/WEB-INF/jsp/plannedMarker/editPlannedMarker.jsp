<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
                toggleAssayTypeOtherText();
                toggleAssayPurposeOtherText();
            }
            function toggleAssayTypeOtherText() {
            	if ($('assayType').value == 'Other') {
                    $('assayTypeOtherTextRow').show();
                } else {
                    $('assayTypeOtherTextRow').hide();
                }
            }
            function toggleAssayPurposeOtherText() {
            	if ($('assayPurpose').value == 'Other') {
            		$('assayPurposeOtherTextRow').show();
            	} else {
            		$('assayPurposeOtherTextRow').hide();
            	}	
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title" /></h1>
        <c:set var="topic" scope="request" value="planned_marker"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <s:url id="cancelUrl" namespace="/protected" action="plannedMarker"/>
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}">
                    <s:set var="submitUrl" value="'plannedMarkerupdate'"/>
                    <fmt:message key="plannedMarker.edit.title"/>
                </s:if>
                <s:elseif test="%{currentAction == 'create'}">
                    <s:set var="submitUrl" value="'plannedMarkeradd'"/>
                    <fmt:message key="plannedMarker.add.title"/>
                </s:elseif>
            </h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form id="plannedMarkerForm" action="%{#submitUrl}">
                            <table class="form">
                                <s:hidden name="currentAction"/>
                                <s:hidden name="plannedMarker.id"/>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.name"><fmt:message key="plannedMarker.name"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:textfield name="plannedMarker.name" id="name" maxlength="200" size="200" cssStyle="width:280px"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.name</s:param>
                                            </s:fielderror>
                                         </span>
                                    </td>                                        
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.assayType"><fmt:message key="plannedMarker.assayType"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:set name="assayTypeValues" value="@gov.nih.nci.pa.enums.AssayTypeCode@getDisplayNames()" />
                                        <s:select headerKey="" headerValue="" name="plannedMarker.assayType" id="assayType"
                                            list="#assayTypeValues"  onchange="toggleAssayTypeOtherText();"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.assayType</s:param>
                                            </s:fielderror>
                                         </span>
                                    </td>
                                </tr>
                                <tr id="assayTypeOtherTextRow">
                                    <td class="label">
                                        <s:label for="plannedMarker.assayTypeOtherText"><fmt:message key="plannedMarker.assayTypeOtherText"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:textfield name="plannedMarker.assayTypeOtherText" id="assayTypeOtherText" maxlength="200" size="200" cssStyle="width:280px"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.assayTypeOtherText</s:param>
                                            </s:fielderror>
                                         </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.assayUse"><fmt:message key="plannedMarker.assayUse"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:set name="assayUseValues" value="@gov.nih.nci.pa.enums.AssayUseCode@getDisplayNames()" />
                                        <s:select headerKey="" headerValue="" name="plannedMarker.assayUse" id="assayUse"
                                            list="#assayUseValues"/> 
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.assayUse</s:param>
                                            </s:fielderror>
                                         </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.assayPurpose"><fmt:message key="plannedMarker.assayPurpose"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:set name="assayPurposeValues" value="@gov.nih.nci.pa.enums.AssayPurposeCode@getDisplayNames()" />
                                        <s:select headerKey="" headerValue="" name="plannedMarker.assayPurpose" id="assayPurpose"
                                            list="#assayPurposeValues" onchange="toggleAssayPurposeOtherText()"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.assayPurpose</s:param>
                                            </s:fielderror>
                                         </span> 
                                     </td>
                                </tr>
                                <tr id="assayPurposeOtherTextRow">
                                    <td class="label">
                                        <s:label for="plannedMarker.assayPurposeOtherText"><fmt:message key="plannedMarker.assayPurposeOtherText"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:textfield name="plannedMarker.assayPurposeOtherText" id="assayPurposeOtherText" maxlength="200" size="200" cssStyle="width:280px"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.assayPurposeOtherText</s:param>
                                            </s:fielderror>
                                         </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.tissueSpecimenType"><fmt:message key="plannedMarker.tissueSpecimenType"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:set name="tissueSpecimenTypeValues" value="@gov.nih.nci.pa.enums.TissueSpecimenTypeCode@getDisplayNames()" />
                                        <s:select headerKey="" headerValue="" name="plannedMarker.tissueSpecimenType" id="tissueSpecimenType"
                                            list="#tissueSpecimenTypeValues"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.tissueSpecimenType</s:param>
                                            </s:fielderror>
                                         </span> 
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.tissueCollectionMethod"><fmt:message key="plannedMarker.tissueCollectionMethod"/>:</s:label><span class="required">*</span>
                                    </td>
                                    <td>
                                        <s:set name="tissueCollectionMethodValues" value="@gov.nih.nci.pa.enums.TissueCollectionMethodCode@getDisplayNames()" />
                                        <s:select headerKey="" headerValue="" name="plannedMarker.tissueCollectionMethod" id="tissueCollectionMethod"
                                            list="#tissueCollectionMethodValues"/>
                                        <span class="formErrorMsg">
                                            <s:fielderror>
                                                <s:param>plannedMarker.tissueCollectionMethod</s:param>
                                            </s:fielderror>
                                         </span> 
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label">
                                        <s:label for="plannedMarker.status"><fmt:message key="plannedMarker.status"/>:</s:label>
                                    </td>
                                    <td>
                                        <s:textfield name="plannedMarker.status" id="status" cssClass="readonly" readonly="true" />
                                    </td>
                                </tr>
                            </table>
                            <div class="actionsrow">
                                <del class="btnwrapper">
                                    <ul class="btnrow">
                                        <li>
                                            <s:a cssClass="btn" href="#" onclick="document.forms[0].submit();">
                                                <span class="btn_img"><span class="add">Save</span></span>
                                            </s:a>
                                            <s:a href="%{cancelUrl}" cssClass="btn">
                                                <span class="btn_img"><span class="add">Cancel</span></span>
                                            </s:a>
                                        </li>
                                    </ul>
                                </del>
                            </div>
                        </s:form>
                    </td>
                </tr>
            </table>
         </div>
    </body>
</html>