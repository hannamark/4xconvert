<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="accrual_submitting"/> 
<head>
    <title><fmt:message key="accrual.new.accrual.submission.page.title"/></title>   
    <s:head/>
<SCRIPT LANGUAGE="JavaScript">

function cancel(){
	document.forms[0].action="accrualSubmissions.action";
    document.forms[0].submit(); 
   
}
function handleAction(){

	   	document.forms[0].action="accrualSubmissionsaddNew.action";
        document.forms[0].submit();  
       
}


</SCRIPT>
<script type="text/javascript" src="<c:url value="/scripts/js/popup.js"/>"></script>
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
        addCalendar("Cal1", "Select Date", "submission.cutOffDate.value", "addNew");
        setWidth(90, 1, 15, 1);
        setFormat("mm/dd/yyyy");
</script>


</head>

<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="accrual.new.accrual.submission.page.header"/></h1>
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <s:form name="addNew" validate="true">
    <table class="form">
      <tr>     
        <td scope="row" class="label">
          <label for="Submission Title">
              <fmt:message key="accrual.new.accrual.submission.submissionTitle"/> 
              <span class="required">*</span>
          </label>
        </td>
        <td class="value">
            <s:textfield id ="label" name="submission.label.value" maxlength="400" size="50" cssStyle="width:98%;max-width:250px" />
        </td>
      </tr> 
    
      
       <tr>     
        <td scope="row" class="label">
          <label for="Submission Cut off Date">
              <fmt:message key="accrual.new.accrual.submission.submissionCutoffDate"/>
              <span class="required">*</span>
          </label>
         </td>
                   
           <td class="value"><s:textfield name="submission.cutOffDate.value"
                maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                <a href="javascript:showCal('Cal1')">
                    <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" /></a> (mm/dd/yyyy) 
                </td>
                
      </tr> 
       <tr>     
        <td scope="row" class="label">
          <label for="Description">
              <fmt:message key="accrual.new.accrual.submission.description"/> 
              <span class="required">*</span>
          </label>
         </td>
         <td class="value">
           <s:textfield id ="description" name="submission.description.value" maxlength="400" size="50" 
            cssStyle="width:98%;max-width:250px" />
      </tr> 
    
    </table>
    
     <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">
                <li>
                <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="cancel()"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                </li>
               </ul>
            </del>
         </div>
    </s:form>
</body>