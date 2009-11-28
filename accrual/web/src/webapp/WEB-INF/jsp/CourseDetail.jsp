<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<script type="text/javascript">

	function handleSaveAction() {
        document.forms[0].action = "addCourse.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "executeCourse.action";
        document.forms[0].submit();
    }   
	
	function handleEditAction(){
	    document.forms[0].action="editCourse.action";
	    document.forms[0].submit();
	}

</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "courseDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction == 'create'}">
        <c:set var="topic" scope="request" value="course_adding"/> 
        <fmt:message key="course.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <c:set var="topic" scope="request" value="course_update"/> 
        <fmt:message key="course.updatePage" /></s:elseif>
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
   <s:if test="%{currentAction == 'create'}">
        <fmt:message key="course.addPage" /></s:if>
    <s:elseif test="%{currentAction == 'update'}">
        <fmt:message key="course.updatePage" /></s:elseif>
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
	<s:hidden name = "currentAction"/>
	<s:hidden name = "selectedRowIdentifier"/>
	<s:hidden name = "course.identifier"/>
<table class="form">
<tr>
        <td scope="row" class="label"><label><fmt:message key="course.name"/>:<span class="required">*</span></label></td>
        <td>
            <s:textfield size="50" name="course.name" cssStyle="width:280px;float:left" />
            <s:fielderror cssClass="formErrorMsg"><s:param>course.name</s:param></s:fielderror>
        </td>
    </tr>
    
    <tr>
        <td scope="row" class="label"><label><fmt:message key="course.startDate"/>:<span class="required">*</span></label></td>
        <td class="value">
            <s:textfield id="courseDate" name="course.createDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
            <s:fielderror cssClass="formErrorMsg"><s:param>course.createDate</s:param></s:fielderror>
        </td>      
    </tr>
        
</table>
</s:form>

<div class="actionsrow">
   <del class="btnwrapper">
      <ul class="btnrow">
       <li>        
         <s:if test="%{currentAction == 'create'}">
            <s:a href="#" cssClass="btn" onclick="handleSaveAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
        </s:if>
         <s:elseif test="%{currentAction == 'update'}">
         	<s:a href="#" cssClass="btn" onclick="handleEditAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
         </s:elseif>
            <s:a href="#" cssClass="btn" onclick="handleCancelAction()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
        </li>
      </ul>
   </del>
</div>

</div>
</body>
</html>
