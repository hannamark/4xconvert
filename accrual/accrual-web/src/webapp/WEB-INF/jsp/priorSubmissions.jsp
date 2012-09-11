<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="prior_submissions"/>
<head>
    <title><fmt:message key="priorSubmissions.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/overlib.js"/>"></script>
    <script type="text/javascript" language="javascript">
      addCalendar("CalFrom", "Select Date", "dateFrom", "listForm");
      addCalendar("CalTo", "Select Date", "dateTo", "listForm");
      setWidth(90, 1, 15, 1);
      setFormat("mm/dd/yyyy");
      function handleSearch(){
        document.forms[0].action="priorSubmissions.action";
        document.forms[0].submit();
      }
    </script>
</head>
<body>
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="priorSubmissions.title"/></h1>
  <s:form name="listForm">
   <h3><fmt:message key="priorSubmissions.dates.label"/></h3>
    <table class="form">
      <tr>
        <td class="label">
          <accrual:displayTooltip tooltip="tooltip.from">
          <label for="dateFrom"><fmt:message key="priorSubmissions.dates.from"/></label>
          </accrual:displayTooltip>
        </td>
        <td>
          <s:textfield id ="dateFrom" name="dateFrom" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
          <a href="javascript:showCal('CalFrom')"><img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
        </td>
      </tr>
      <tr>
        <td class="label">       
          <accrual:displayTooltip tooltip="tooltip.to">
          <label for="dateTo"><fmt:message key="priorSubmissions.dates.to"/></label>
          </accrual:displayTooltip>
        </td>
        <td> 
          <s:textfield id ="dateTo" name="dateTo" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
          <a href="javascript:showCal('CalTo')"><img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a>
        </td>
      </tr>
    </table>
    <div class="actionsrow">
        <del class="btnwrapper">
           <ul class="btnrow">
            <li>
            <s:a href="#" cssClass="btn" onclick="handleSearch()"><span class="btn_img"><span class="search">Search</span></span></s:a>
            </li>
           </ul>
        </del>
    </div>
  </s:form>
  <div class="line"></div>
  <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>

  <display:table class="data" summary="This table contains list of submissions.  Please use column headers to sort results"
                  sort="list" pagesize="10" id="row" name="displayTagList" requestURI="priorSubmissions.action" export="false">
       <display:column titleKey="priorSubmissions.list.nciNumber" property="nciNumber" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.file" property="fileHtml" sortable="false" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.submissionType" property="submissionType.code" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.date" property="date" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.user" property="username" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.result" property="result" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table>
</body>
</html>