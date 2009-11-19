<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpprocedureName.action?type=Surgery" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addSurgery.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeSurgery.action";
        document.forms[0].submit();
    }
    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Surgery Name');
	}

</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "surgeryDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
       <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="surgery_adding"/> 
        <fmt:message key="surgery.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="surgery_update"/> 
        <fmt:message key="surgery.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    <s:if test="%{currentAction == 'create'}"> 
        <fmt:message key="surgery.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="surgery.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<table class="form">
<tr>
        <td scope="row" class="label"><label><fmt:message key="surgery.name"/><span class="required">*</span></label></td>
        <td>
            <s:textfield readonly="true" size="50" name="surgery.name" cssStyle="width:280px;float:left" cssClass="readonly"/>
            <a href="#" class="btn" onclick="lookup();"/><span class="btn_img"><span class="search">Look Up</span></span></a>
            <s:fielderror cssClass="formErrorMsg"><s:param>surgery.name</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="surgery.startDate"/><span class="required">*</span></label></td>
        <td>
        	<s:textfield id="surgeryDate" name="surgery.createDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
             <s:fielderror cssClass="formErrorMsg"><s:param>surgery.createDate</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="surgery.relatedInfo"/><span class="required">*</span></label></td>
        <td class="value">
            <s:textarea name="surgery.info" cols="4" cssStyle="width:98%;max-width:250px"/>
            <s:fielderror cssClass="formErrorMsg"><s:param>surgery.info</s:param></s:fielderror>
        </td>      
    </tr>
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
             <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
