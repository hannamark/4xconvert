<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="nciSpecificInfo.title"/></title>
    <s:head />
</head>

<body onload="setFocusToFirstControl();">
<div id="contentwide">
 <h1><fmt:message key="nciSpecificInfo.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>
  <div id="box">
    <s:form ><s:actionerror/>
        <table >
        
             <c:if test="${sessionScope.trialSummary  != null}">
                <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
            </c:if> 

<th colspan="2"><fmt:message key="studyProtocol.view.title"/></th>
<tr>
    <td scope="row" class="label" nowrap>
        <label for="principalInvestigator"> 
            <fmt:message key="studyProtocol.studyPhase"/>
        </label>
    </td>           
    <td >
        <c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/>

    </td>


</tr>
        </table>            
    </s:form>
   </div>
 </div>
 </body>
 </html>