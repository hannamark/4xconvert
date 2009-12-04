<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">
    function handleEditAction() {
    if (confirm("Select OK to complete the Save. Once saved can not be Deleted!")) {
	        document.forms[0].action = "saveOffTreatment.action";
	        document.forms[0].submit();
        }
    }

    function handleCancelAction() {
        document.forms[0].action = "cancelOffTreatment.action";
        document.forms[0].submit();
    }
</script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "offTreatLastDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="offTreatment_detail"/> 
    </s:if>
    Off Treatment/Off Study
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Off Treatment/Off Study
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="offTreat.id"/>
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="offtreat.label.date"/><span class="required">*</span></label></td>
<td><s:textfield id="offTreatLastDate" name="offTreat.lastTreatmentDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>offTreat.lastTreatmentDate</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="offtreat.label.reason"/><span class="required">*</span></label></td>
<td><s:select name="offTreat.offTreatmentReason" headerKey="" headerValue="--Select--"
              list="offTreat.offTreatmentReasons" listKey="code" listValue="code" value="offTreat.offTreatmentReason.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>offTreat.offTreatmentReason</s:param></s:fielderror></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
