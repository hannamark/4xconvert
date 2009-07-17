<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="milestone.details.title" /></title>
<s:head />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/datetimepicker.js"/>"></script>
<script type="text/javascript">
    addCalendar("Cal1", "Select Date", "milestone.date", "milestoneForm");
    setWidth(90, 1, 15, 1);
    setFormat("mm/dd/yyyy");

    function milestoneAdd(){
        input_box=confirm("Click OK to add milestone.  Cancel to Abort.");
        if (input_box==true){
            var date = document.getElementById("date").value;
            document.forms["milestoneForm"].elements["milestone.date"].value=date;
            document.milestoneForm.action="milestoneadd.action?date="+date;
            document.milestoneForm.submit();
        }
    }
    function cancel(){
        document.milestoneForm.action="milestone.action";
        document.milestoneForm.submit();
    }
</script>
</head>
<body onload="setFocusToFirstControl();">
<h1><fmt:message key="milestone.details.title" /></h1>

<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <h2>
        <fmt:message key="milestone.add.details.title"/>
    </h2>

    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
 
        <tr><td colspan="2">
        <table class="form">
            <tr><td colspan="2">
                <h3><fmt:message key="milestone.milestone"/></h3>
                <s:form name="milestoneForm">
                  <table class="form">
                  <tr>
                      <s:set name="milestoneValues" value="@gov.nih.nci.pa.enums.MilestoneCode@getDisplayNames()" />
                      <td class="label"><s:label><fmt:message key="milestone.milestone"/></s:label><span class="required">*</span></td>
                      <td class="value" style="width: 250px">
                           <s:select headerKey="" headerValue="--Select--" name="milestone.milestone" list="#milestoneValues"/>
                      </td>
                  </tr>
                  <tr>
                      <td class="label"><s:label><fmt:message key="milestone.date"/></s:label><span class="required">*</span></td>
                      <td class="value">
                        <s:textfield name="milestone.date" id="date" maxlength="30" size="30" cssStyle="width:170px;float:left"/>
                        <a href="javascript:NewCal('date','mmddyyyy',true,24)">
                       <img src="<%=request.getContextPath()%>/images/cal.gif" alt="select date" class="calendaricon" /></a>
                      </td> 
                  </tr>
                  <tr>
                      <td class="label"><s:label><fmt:message key="milestone.comment"/></s:label></td>
                      <td class="value">
                          <s:textarea name="milestone.comment" rows="3" cssStyle="width:280px;float:left"/>
                      </td>
                  </tr>
                  </table>
                </s:form>
                <div class="actionsrow"><del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="milestoneAdd();">
                            <span class="btn_img"> <span class="save">Save</span></span>
                        </s:a>
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