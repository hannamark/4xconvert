<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="new_accrual_submission"/> 
<body>

<h1><fmt:message key="accrual.new.accrual.submission.page.header"/></h1>
   <p>Click the drop-down box to select the participation site:</p>
   
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="Submission Title">
              <fmt:message key="accrual.new.accrual.submission.submissionTitle"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
      
      <tr>     
        <td scope="row" class="label">
          <label for="Submission Cut off Date">
              <fmt:message key="accrual.new.accrual.submission.submissionCutoffDate"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
    
    </table>
</body>