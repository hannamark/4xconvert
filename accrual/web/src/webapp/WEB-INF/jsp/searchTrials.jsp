<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="search_trials"/> 
<body>

<h1><fmt:message key="aaccrual.search.trials.page.header"/></h1>
  
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="NCI Trial Number">
              <fmt:message key="accrual.search.trials.nciTrialNumber"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
      <tr>     
        <td scope="row" class="label">
          <label for="NCT Trial Number">
              <fmt:message key="accrual.search.trials.nctTrialNumber"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
      <tr>     
        <td scope="row" class="label">
          <label for="Official Title">
              <fmt:message key="accrual.search.trials.officialTitle"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
    
    </table>
</body>