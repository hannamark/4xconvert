<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="list_accural_submissions"/> 
<head>
    <title><fmt:message key="accrual.view.accrual.submission.page.title"/></title>   
    <s:head/>
    <SCRIPT LANGUAGE="JavaScript">

function cancel(){
    document.forms[0].action="accrualSubmissions.action";
    document.forms[0].submit(); 
   
}

</SCRIPT>
    
</head>

<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1><fmt:message key="accrual.view.accrual.submission.page.header"/></h1>
  
   <s:form name="viewDetails" >
    <table class="form">
    
      <tr>     
        <td scope="row" class="label">
          <label for="Submission Label">
              <fmt:message key="accrual.view.accrual.submission.label"/> 
          </label>
        </td>
        <td class="value">
            <s:text name="submission.label.value" />
        </td>
      </tr> 
      
      <tr>     
        <td scope="row" class="label">
          <label for="Description">
              <fmt:message key="accrual.view.accrual.submission.description"/> 
           </label>
         </td>
         <td class="value">
            <s:text name="submission.description.value" />
        </td>
       </tr> 
       <tr>     
        <td scope="row" class="label">
          <label for="Submission Cut off Date">
              <fmt:message key="accrual.view.accrual.submission.cutOffDate"/>
           </label>
         </td>
          <td class="value">
            <s:text name="submissionCutOffDate" />
        </td>
      </tr> 
      
      <tr>     
        <td scope="row" class="label">
          <label for="Status">
              <fmt:message key="accrual.view.accrual.submission.status"/> 
           </label>
         </td>
          <td class="value">
            <s:text name="submissionStatus" />
        </td>
         
      </tr> 
      
       <tr>     
        <td scope="row" class="label">
          <label for="Created Date">
              <fmt:message key="accrual.view.accrual.submission.createdDate"/> 
           </label>
         </td>
         <td class="value">
            <s:text name="submissionCreatedDate" />
        </td>
         
      </tr> 
       
        <tr>     
        <td scope="row" class="label">
          <label for="User Created">
              <fmt:message key="accrual.view.accrual.submission.createUser"/> 
           </label>
         </td>
         <td class="value">
            <s:text name="submissionCreateUser" />
        </td>
         
      </tr> 
       
        <tr>     
        <td scope="row" class="label">
          <label for="Submitted Date">
              <fmt:message key="accrual.view.accrual.submission.submittedDate"/> 
           </label>
         </td>
        <td class="value">
            <s:text name="submissionSubmittedDate" />
        </td>
         
      </tr> 
       
        <tr>     
        <td scope="row" class="label">
          <label for="User Submitted">
              <fmt:message key="accrual.view.accrual.submission.submitUser"/> 
           </label>
         </td>
         <td class="value">
            <s:text name="submissionSubmitUser" />
        </td>
         
      </tr> 
       
       
    
    </table>
    
     <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">
                <li>
                  <s:a href="#" cssClass="btn" onclick="cancel()"><span class="btn_img"><span class="back">Back</span></span></s:a>
                </li>
               </ul>
            </del>
         </div>
    </s:form>
</body>