<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="new_accrual_submission"/> 
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
</head>

<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1><fmt:message key="accrual.new.accrual.submission.page.header"/></h1>
  
   <s:form name="addNew">
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="Submission Title">
              <fmt:message key="accrual.new.accrual.submission.submissionTitle"/>                
          </label>
         </td>
         <td class="value">
           <s:textfield id ="officialTitle" name="submission.label.value" maxlength="400" size="50" 
            cssStyle="width:98%;max-width:250px" />
           </td>
      </tr> 
      
         
      <tr>     
        <td scope="row" class="label">
          <label for="Submission Cut off Date">
              <fmt:message key="accrual.new.accrual.submission.submissionCutoffDate"/>                
          </label>
         </td>
         <td class="value">
           <s:textfield id ="officialTitle" name="submission.cutOffDate.value" maxlength="400" size="50" 
            cssStyle="width:98%;max-width:250px" />
           </td>
      </tr> 
      
       <tr>     
        <td scope="row" class="label">
          <label for="Description">
              <fmt:message key="accrual.new.accrual.submission.description"/>                
          </label>
         </td>
         <td class="value">
           <s:textfield id ="officialTitle" name="submission.description.value" maxlength="400" size="50" 
            cssStyle="width:98%;max-width:250px" />
           </td>
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