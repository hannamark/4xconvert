<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="irb.main.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <c:url value="/protected/popuplookuporgs.action" var="lookupUrl" />
        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            function irbSave() {
                 document.irbForm.action="irbsave.action";
                 document.irbForm.submit();     
            }
            function statusChange() {
                var newStatus = document.irbForm.approvalStatus.value;
                if (newStatus == "Submission not required") {
                    hideRow($('approvalNumberrow'));
                    hideRow($('boradNamerow'));
                    hideRow($('mailAddressrow'));
                    hideRow($('cityrow'));
                    hideRow($('staterow'));
                    hideRow($('ziprow'));
                    hideRow($('countryrow'));
                    hideRow($('phonerow'));
                    hideRow($('emailrow'));
                    hideRow($('boardAffiliationrow'));
                } else if (newStatus == "Submitted, approved") {
                    showRow($('approvalNumberrow'));
                    showRow($('boradNamerow'));
                    showRow($('mailAddressrow'));
                    showRow($('cityrow'));
                    showRow($('staterow'));
                    showRow($('ziprow'));
                    showRow($('countryrow'));
                    showRow($('phonerow'));
                    showRow($('emailrow'));
                    showRow($('boardAffiliationrow'));
                } else if (newStatus=="Submitted, exempt" || newStatus=="Submitted, pending" || newStatus=="Submitted, denied") {
                    hideRow($('approvalNumberrow'));
                    showRow($('boradNamerow'));
                    showRow($('mailAddressrow'));
                    showRow($('cityrow'));
                    showRow($('staterow'));
                    showRow($('ziprow'));
                    showRow($('countryrow'));
                    showRow($('phonerow'));
                    showRow($('emailrow'));
                    showRow($('boardAffiliationrow'));
                } else {
                    hideRow($('approvalNumberrow'));
                    hideRow($('boradNamerow'));
                    hideRow($('mailAddressrow'));
                    hideRow($('cityrow'));
                    hideRow($('staterow'));
                    hideRow($('ziprow'));
                    hideRow($('countryrow'));
                    hideRow($('phonerow'));
                    hideRow($('emailrow'));
                    hideRow($('boardAffiliationrow'));
                }
            }
            function setContactDisabled(value) {
                $('address').disabled=value;
                $('city').disabled=value;
                $('state').disabled=value;
                $('zip').disabled=value;
                $('country').disabled=value;
                $('phone').disabled=value;
                $('email').disabled=value;
            }
            function changeName() {
                var orgid = document.irbForm.id.value;
                document.irbForm.action='irbfromPO.action?orgId='+orgid;
                document.irbForm.submit();
            }
            // do not remove these two callback methods!
            function setpersid(persid) {}
            function setorgid(orgid) {}
            function lookup() {
                if ($('name').disabled!=true) {
                    showPopup('${lookupUrl}', null, 'Organization');
                }
            }   
            function loadDiv(orgid) {
                document.irbForm.action='irbfromPO.action?orgId='+orgid;
                document.irbForm.submit();
                return false;
            }
            function hideRow(row) {          
                row.style.display = 'none'; 
            }
            function showRow(row) {
                row.style.display = '';
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="irb.main.title" /></h1>
        <c:set var="topic" scope="request" value="abstractsafety"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if> 
            <h2>
                <fmt:message key="irb.details.title" />
            </h2>
            <s:form name="irbForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <table class="form">
                    <s:hidden name="selectedArmIdentifier"/>
                    <s:hidden name="boardChanged"/> 
                    <s:hidden name="newOrgId"/>
                    <s:hidden name="newOrgName"/> 
                    <s:hidden name="ct.id"/> 
                    <tr>
                        <td class="label">
                            Board Approval Status:<span class="required">*</span>
                        </td>
                        <s:set name="approvalStatusValues"  value="@gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode@getDisplayNames()" />
                        <td class="value" colspan="2">
                            <s:select headerKey="" headerValue="--Select--" name="approvalStatus" list="#approvalStatusValues" 
                                      onchange="statusChange()" onfocus="statusChange()"/>
                        </td>
                    </tr>
                    <tr id="approvalNumberrow">
                        <td class="label">Board Approval Number:<span class="required">*</span></td>
                        <td class="value" colspan="2"><s:textfield name="approvalNumber" maxlength="30" size="30" cssStyle="width:200px;float:left"/></td>
                    </tr>
                    <tr id="boradNamerow">
                         <td class="label">Board Name:<span class="required">*</span></td>
                         <td class="value"> 
                             <s:textfield name="ct.name" id="name" size="30"  readonly="true" cssClass="readonly" onchange="changeName()"/>
                         </td>
                         <td>  
                            <ul style="margin-top:-3px;">
                                <li style="padding-left:0">
                                    <a href="#" class="btn" onclick="lookup();"><span class="btn_img"><span class="search">Look Up</span></span></a>
                                </li>
                            </ul>
                         </td>
                    </tr>
                    <tr id="mailAddressrow">
                        <td class="label">Board Contact Mailing Address:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="address" name="ct.address" cssStyle="width:280px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                     <tr id="cityrow">
                        <td class="label">Board Contact City:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="city" name="ct.city" cssStyle="width:140px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                     <tr id="staterow">
                        <td class="label">Board Contact State/Province:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="state" name="ct.state" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                     <tr id="ziprow">
                        <td class="label">Board Contact Zip/Postal Code:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="zip" name="ct.zip" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                     <tr id="countryrow">
                        <td class="label">Board Contact Country:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="country" name="ct.country" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                    <tr  id="phonerow">
                        <td class="label">Board Contact Phone:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="phone" name="ct.phone" cssStyle="width:100px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                    <tr id="emailrow">
                        <td class="label">Board Contact Email Address:</td>
                        <td class="value" colspan="2">
                            <s:textfield id="email" name="ct.email" cssStyle="width:140px;float:left" readonly="true" cssClass="readonly"/>
                        </td>
                    </tr>
                    <tr id="boardAffiliationrow">
                        <td class="label">Board Affiliation:<span class="required">*</span></td>
                        <td colspan="2" class="value">
                            <s:textarea name="contactAffiliation" cssStyle="width:606px" rows="4"/><i><fmt:message key="irbaffiliation.maxCodeLength"/></i>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:adminAbstractorDisplayWhenCheckedOut>
                                <li>
                                    <s:a href="#" cssClass="btn" onclick="irbSave();"><span class="btn_img"> <span class="save">Save</span></span></s:a>
                                </li>
                            </pa:adminAbstractorDisplayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>