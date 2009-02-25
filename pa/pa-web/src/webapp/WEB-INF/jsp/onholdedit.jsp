<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="onhold.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>

<script type="text/javascript">
    addCalendar("Cal1", "Select Date", "onhold.dateLow", "editForm");
    addCalendar("Cal2", "Select Date", "onhold.dateHigh", "editForm");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");

    function onholdAdd(){
        input_box=confirm("Click OK to add the on-hold information.  Cancel to Abort.");
        if (input_box==true){
            document.editForm.action="onholdadd.action";
            document.editForm.submit();
        }
    }
    function onholdUpdate(){
        input_box=confirm("Click OK to update the on-hold information.  Cancel to Abort.");
        if (input_box==true){
            document.editForm.action="onholdupdate.action";
            document.editForm.submit();
        }
    }
    function cancel(){
        document.editForm.action="onhold.action";
        document.editForm.submit();
    }
</script>
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="onhold.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <h2><fmt:message key="onhold.title"/></h2>
    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
 
        <tr><td colspan="2">
        <table class="form">
            <tr><td colspan="2">
                <h3>
                    <s:if test="%{currentAction == 'create'}"><fmt:message key="onhold.create.title"/></s:if>
                    <s:else><fmt:message key="onhold.edit.title"/></s:else>
                </h3>
                <s:form name="editForm">
                  <s:hidden name="currentAction"/>
                  <s:hidden name="onhold.identifier"/> 
                  <table class="form">
                  <tr>
                      <s:set name="onholdCodeValues" value="@gov.nih.nci.pa.enums.OnholdReasonCode@getDisplayNames()" />
                      <td class="label"><s:label><fmt:message key="onhold.reason.code"/></s:label></td>
                      <td class="value" style="width: 250px">
                        <s:if test="%{currentAction == 'create'}">
                            <s:select headerKey="" headerValue="--Select--" name="onhold.reasonCode" list="#onholdCodeValues"/>
                        </s:if><s:else>
                            <s:textfield name="onhold.reasonCode" cssStyle="width:200px;float:left" readonly="true" cssClass="readonly"/>
                        </s:else>
                        <span class="formErrorMsg"><s:fielderror>
                            <s:param>onhold.reasonCode</s:param>
                        </s:fielderror></span>
                      </td>
                  </tr>
                  <tr>
                      <td class="label"><s:label><fmt:message key="onhold.reason.text"/></s:label></td>
                      <td class="value">
                        <s:if test="%{currentAction == 'create'}">
                            <s:textarea name="onhold.reasonText" rows="3" cssStyle="width:280px;float:left"/>
                        </s:if><s:else>
                            <s:textarea name="onhold.reasonText" rows="3" cssStyle="width:280px;float:left;" disabled="true" readonly="true" cssClass="readonly"/>
                        </s:else>
                      </td>
                  </tr>
                  <tr>
                      <td class="label"><s:label><fmt:message key="onhold.date.low"/></s:label><span class="required">*</span></td>
                      <td class="value">
                        <s:if test="%{currentAction == 'create'}">
                          <s:textfield name="onhold.dateLow" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                          <a href="javascript:showCal('Cal1')">
                              <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                          </a>
                        </s:if><s:else>
                          <s:textfield name="onhold.dateLow" cssStyle="width:70px;float:left" readonly="true" cssClass="readonly"/>
                        </s:else>
                        <span class="formErrorMsg"><s:fielderror>
                            <s:param>onhold.dateLow</s:param>
                        </s:fielderror></span>
                      </td> 
                  </tr>
                  <tr>
                      <td class="label"><s:label><fmt:message key="onhold.date.high"/></s:label></td>
                      <td class="value">
                        <s:textfield name="onhold.dateHigh" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                        <a href="javascript:showCal('Cal2')">
                            <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                        </a>
                        <span class="formErrorMsg"><s:fielderror>
                            <s:param>onhold.dateHigh</s:param>
                        </s:fielderror></span>
                      </td> 
                  </tr>
                  </table>
                </s:form>
                <div class="actionsrow"><del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:if test="%{currentAction == 'create'}">
                            <s:a href="#" cssClass="btn" onclick="onholdAdd();"><span class="btn_img"><span class="save">Save</span></span></s:a>
                        </s:if>
                        <s:else>
                            <s:a href="#" cssClass="btn" onclick="onholdUpdate();"><span class="btn_img"><span class="save">Save</span></span></s:a>
                        </s:else>
                    </li>
                    <li>
                        <s:a href="#" cssClass="btn" onclick="cancel();">
                            <span class="btn_img"><span class="cancel">Cancel</span></span>
                        </s:a>
                    </li>
                </ul>
                </del></div>
            </td></tr>
        </table>
        </td></tr>
    </table>
</div>
</body>
</html>