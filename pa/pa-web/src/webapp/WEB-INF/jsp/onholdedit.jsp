<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="onhold.title" /></title>
        <s:head />
        <script type="text/javascript">
            addCalendar("Cal1", "Select Date", "onhold.dateLow", "editForm");
            addCalendar("Cal2", "Select Date", "onhold.dateHigh", "editForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
            
            jQuery(function() {
                setFocusToFirstControl();
                jQuery("#addButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onholdadd.action";
                    form.submit();
                });
                jQuery("#updateButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onholdupdate.action";
                    form.submit();
                });
                jQuery("#cancelButton").bind("click", function(ev) {
                    var form = document.editForm;
                    form.action = "onhold.action";
                    form.submit();
                });
            });
        </script>
    </head>
    <body>
        <h1><fmt:message key="onhold.title" /></h1>
        <c:set var="topic" scope="request" value="trialonhold"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <h2><fmt:message key="onhold.title"/></h2>
            <s:form name="editForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="currentAction"/>
                <s:hidden name="onhold.identifier"/>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <h3>
                                <s:if test="%{currentAction == 'create'}"><fmt:message key="onhold.create.title"/></s:if>
                                <s:else><fmt:message key="onhold.edit.title"/></s:else>
                            </h3>
                        </td>
                    </tr>    
                    <pa:valueRow labelKey="onhold.reason.code" required="true">
                        <s:set name="onholdCodeValues" value="@gov.nih.nci.pa.enums.OnholdReasonCode@getDisplayNames()" />
                        <s:if test="%{currentAction == 'create'}">
                            <s:select headerKey="" headerValue="--Select--" name="onhold.reasonCode" list="#onholdCodeValues"/>
                        </s:if>
                        <s:else>
                            <s:textfield name="onhold.reasonCode" cssStyle="width:200px;float:left" readonly="true" cssClass="readonly"/>
                        </s:else>
                        <pa:fieldError fieldName="onhold.reasonCode"/>
                    </pa:valueRow>
                    <pa:valueRow labelKey="onhold.reason.text">
                        <s:if test="%{currentAction == 'create'}">
                            <s:textarea name="onhold.reasonText" rows="3" cssStyle="width:280px;" maxlength="200" cssClass="charcounter"/>
                        </s:if>
                        <s:else>
                            <s:textarea name="onhold.reasonText" rows="3" cssStyle="width:280px;float:left;" disabled="true" readonly="true" cssClass="readonly"/>
                        </s:else>
                    </pa:valueRow>
                    <pa:valueRow labelKey="onhold.date.low" required="true">
                        <s:textfield name="onhold.dateLow" cssStyle="width:70px;float:left" readonly="true" cssClass="readonly"/>
                        <pa:fieldError fieldName="onhold.dateLow"/>
                    </pa:valueRow>
                    <s:if test="%{currentAction != 'create'}">
	                    <pa:valueRow labelKey="onhold.date.high">
	                        <s:textfield name="onhold.dateHigh" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
	                        <a href="javascript:showCal('Cal2')">
	                            <img src="${imagePath}/ico_calendar.gif" alt="select date" class="calendaricon" />
	                        </a>
	                        <pa:fieldError fieldName="onhold.dateHigh"/>
	                    </pa:valueRow>
                    </s:if>
                    <s:if test="%{onhold.processingLog!=null && onhold.processingLog!=''}">
	                    <pa:valueRow labelKey="onhold.processingLog">
	                        <s:textarea name="onhold.processingLog" rows="5" cssStyle="width:500px;float:left;" 
	                         readonly="true" cssClass="readonly"/>
	                    </pa:valueRow>                    
                    </s:if>                    
                </table>
                <pa:buttonBar>
                    <s:if test="%{currentAction == 'create'}">
                        <pa:button id="addButton" imgClass="save" labelKey="onhold.button.save"/>
                    </s:if>
                    <s:else>
                        <pa:button id="updateButton" imgClass="save" labelKey="onhold.button.save"/>
                    </s:else>
                    <pa:button id="cancelButton" imgClass="cancel" labelKey="onhold.button.cancel"/>
                </pa:buttonBar>
            </s:form>
        </div>
    </body>
</html>