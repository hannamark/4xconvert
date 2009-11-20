<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:url value="/outcomes/lookUpanatomicSites.action?type=diseaseAutopsy" var="lookupUrl" />
<head>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript">
    function handleEditAction() {
        document.forms[0].action = "saveDeathInformation.action";
        document.forms[0].submit();
    }

    function handleCancelAction() {
        document.forms[0].action = "cancelDeathInformation.action";
        document.forms[0].submit();
    }

    function lookup(){
        showPopWin('${lookupUrl}', 900, 400, '', 'Autopsy Site');
    }
</script>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "deathEventDate", "detailForm");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>
<title>
    <s:if test="%{currentAction== 'detail'}">
        <c:set var="topic" scope="request" value="deathInformation_detail"/> 
     </s:if>
     Death Information
</title>        
    <s:head/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1>
    Death Information
</h1>
<div class="box">
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
<s:form name="detailForm">
<s:hidden name="selectedSite"/>
<table class="form">
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.cause"/><span class="required">*</span></label></td>
<td><s:select name="deathInfo.cause" headerKey="" headerValue="--Select--"
              list="deathInfo.causes" listKey="code" listValue="code" value="deathInfo.cause.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>deathInfo.cause</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.date"/><span class="required">*</span></label></td>
<td><s:textfield id="deathEventDate" name="deathInfo.eventDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy)
                    <s:fielderror cssClass="formErrorMsg"><s:param>deathInfo.eventDate</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.autopsyInd"/><span class="required">*</span></label></td>
<td><s:select name="deathInfo.autopsyInd" headerKey="" headerValue="--Select--"
              list="deathInfo.autopsyInds" listKey="code" listValue="code" value="deathInfo.autopsyInd.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>deathInfo.autopsyInd</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.causeByAutopsy"/><span class="required">*</span></label></td>
<td><s:select name="deathInfo.causeByAutopsy" headerKey="" headerValue="--Select--"
              list="deathInfo.causesByAutopsy" listKey="code" listValue="code" value="deathInfo.causeByAutopsy.code"/>
               <s:fielderror cssClass="formErrorMsg"><s:param>deathInfo.causeByAutopsy</s:param></s:fielderror></td></tr>
<tr><td scope="row" class="label"><label><fmt:message key="deathInfo.label.autopsySite"/><span class="required">*</span></label></td>
<td><s:textfield readonly="true" size="50" name="deathInfo.autopsySite"
               cssStyle="width:280px;float:left" cssClass="readonly"/><s:hidden name="deathInfo.id"/>
               <a href="#" class="btn" onclick="lookup();" /><span class="btn_img"><span class="search">Look Up</span></span></a>
               <s:fielderror cssClass="formErrorMsg"><s:param>deathInfo.autopsySite</s:param></s:fielderror></td></tr>
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
