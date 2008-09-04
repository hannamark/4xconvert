<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="nciSpecificInformation.title"/></title>
    <s:head />
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="nciSpecificInformation.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/jsp/pajsp/protocolDetailSummary.jsp"/>

  <div class="box">
    <s:form ><s:actionerror/>
	<h2>Trial Identification</h2>

        <table class="form">
         
             <c:if test="${sessionScope.trialSummary  != null}">
                <jsp:include page="/jsp/pajsp/trialDetailSummary.jsp"/> 
            </c:if> 

<!-- <th colspan="2"><fmt:message key="studyProtocol.view.title"/></th>
<tr>
    <td scope="row" class="label" nowrap>
        <label for="principalInvestigator"> 
            <fmt:message key="studyProtocol.studyPhase"/>
        </label>
    </td>           
    <td >
        <c:out value="${sessionScope.trialSummary.studyStatusCode.code }"/>

    </td>


</tr> -->
        </table>  
 <div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><a href="studyProtocolEdit.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="edit">Edit</span></span></a></li>
		</ul>	
	</del>
</div>

                  
    </s:form>
   </div>
<!--  </div> -->
 </body>
 </html>