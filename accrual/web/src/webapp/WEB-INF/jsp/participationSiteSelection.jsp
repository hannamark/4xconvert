<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="participation_site_selection"/> 
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<h1><fmt:message key="accrual.participation.site.selection.page.header"/></h1>
   <p>Click the drop-down box to select the participation site:</p>
   
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="Participation Site">
              <fmt:message key="accrual.participation.site.selection.participationSite"/>                
          </label>
         </td>
         <td class="value">
           
           </td>
      </tr> 
    
    </table>
</body>