<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/popup.action" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleEditAction() {
        document.forms[0].action = "saveDiagnosis.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "cancelDiagnosis.action";
        document.forms[0].submit();
    }

    function handleNextAction() {
        document.getElementsByName("nextTarget")[0].value = "Staging";
        document.forms[0].action = "nextDiagnosis.action";
        document.forms[0].submit();
    }
	function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Diagnosis');
	}
</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "diagnosisCreateDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="diagnosis_detail"/> 
     </s:if>
     Diagnosis
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Diagnosis
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="nextTarget"/>
<s:hidden name="selectedDiagnosis"/>
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="diagnosis.label.name"/><span class="required">*</span></label></td>
<td><s:textfield readonly="true" size="50" name="diagnosis.name"
               cssStyle="width:280px;float:left" cssClass="readonly"/><s:hidden name="diagnosis.identifier"/>
               <a href="#" class="btn" onclick="lookup();" /><span class="btn_img"><span class="search">Look Up</span></span></a>
               <s:fielderror cssClass="formErrorMsg"><s:param>diagnosis.name</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="diagnosis.label.date"/><span class="required">*</span></label></td>
<td><s:textfield id="diagnosisCreateDate" name="diagnosis.createDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>diagnosis.createDate</s:param></s:fielderror></td></tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
            <s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleNextAction()"><span class="btn_img"><span class="next">Next</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
