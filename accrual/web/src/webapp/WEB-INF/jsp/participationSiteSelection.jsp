<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="participation_site_selection"/> 
<body>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />

<h1><fmt:message key="accrual.participation.site.selection.page.header"/></h1>
   
  <s:form> 
   <p>Click the drop-down box to select the participation site:</p>
   
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="Participation Site">
              <fmt:message key="accrual.participation.site.selection.participationSite"/>                
          </label>
         </td>
         <td class="value">
                
            <s:select name="orgName" id="orgName" list="listOfStudySites" headerKey="" 
             listValue="ssIi" headerValue="--Select--"  cssStyle="width:206px"/>
          
         </td>
      </tr> 
    
    </table>
    </s:form>
    
</body>