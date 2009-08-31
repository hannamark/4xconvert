<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="accrual.search.trials.page.title"/></title>   
    <s:head/>
<SCRIPT LANGUAGE="JavaScript">

function resetValues(){
    document.searchTrial.reset();
    document.getElementById("officialTitle").value="";
    document.getElementById("assignedIdentifier").value="";
    document.getElementById("leadOrgTrialIdentifier").value="";
   
}
function handleAction(){
     document.forms[0].action="viewTrialssearchQuery.action";
     document.forms[0].submit();  
}

</SCRIPT>
</head>

<c:set var="topic" scope="request" value="search_trials"/> 
<body>

<h1><fmt:message key="accrual.search.trials.page.header"/></h1>
  <s:form name="searchTrial">
    <table class="form">
    <tr>     
        <td scope="row" class="label">
          <label for="NCI Trial Number">
              <fmt:message key="accrual.search.trials.nciTrialNumber"/>                
          </label>
         </td>
         <td class="value">
            <s:textfield id ="assignedIdentifier" name="criteria.assignedIdentifier" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
           </td>
      </tr> 
      <tr>     
        <td scope="row" class="label">
          <label for="NCT Trial Number">
              <fmt:message key="accrual.search.trials.nctTrialNumber"/>                
          </label>
         </td>
         <td class="value">
            <s:textfield id ="leadOrgTrialIdentifier" name="criteria.leadOrgTrialIdentifier" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
           </td>
      </tr> 
            
            <tr>
                <td scope="row" class="label">
                 <label for="Official Title">
                    <fmt:message key="accrual.search.trials.officialTitle"/>                
                </label>
                </td>
                <td colspan="4">
                <s:textfield id ="officialTitle" name="criteria.officialTitle" maxlength="400" size="50"  cssStyle="width:98%;max-width:250px" />
                </td>
            </tr>
    
    </table>
    
        <div class="actionsrow">
            <del class="btnwrapper">
               <ul class="btnrow">         
                <li>           
                <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search Trials</span></span></s:a>
                <s:a href="#" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                </li>                
               </ul>
            </del>
         </div>
       </s:form>
       
       <div class="line"></div>
         <c:if test="${listOfTrials != null}">                       
               <c:set var="topic" scope="request" value="search_trials"/>   
                <jsp:include page="/WEB-INF/jsp/listTrials.jsp">
                    <jsp:param name="listOfTrials" value="listOfTrials" />        
                </jsp:include>
         </c:if>
         
</body>
</html>