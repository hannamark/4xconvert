<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialFunding.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">


function handleAction(){
     var studyResourcingId;
    len = document.forms[0].cbValue.length;
    if ((typeof document.forms[0].cbValue.length == 'undefined') && (document.forms[0].cbValue.checked))
    {
      studyResourcingId = document.forms[0].cbValue.value;
    }
    else
    {
      for (i = 0; i < len; i++)
      {
        if (document.forms[0].cbValue[i].checked)
        {
          studyResourcingId = document.forms[0].cbValue[i].value;
          break;
        }
      } // end for
    }
	document.forms[0].cbValue.value = studyResourcingId;
    document.forms[0].page.value = "Edit";
    document.forms[0].action="trialFundingedit.action";
    document.forms[0].submit();  
}

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form><s:actionerror/>
    <h2>NIH Grant Information</h2>
    <table class="data">
    <s:if test="trialFundingList != null">
    <tr><td colspan="5">
    <input type="hidden" name="page" />
	<display:table name="${trialFundingList}" id="list">    
    <display:column>
	    	<s:checkbox name="cbValue" fieldValue="%{#attr.list.id}"></s:checkbox>
    </display:column>
    <display:column titleKey="trialFunding.funding.mechanism" property="fundingMechanismCode" />
    <display:column titleKey="trialFunding.institution.code" property="institutionCode" />
    <display:column titleKey="trialFunding.serial.number" property="serialNumber"   />
    <display:column titleKey="studyProtocol.monitorCode" property="monitorCode" />
    
</display:table>
 </td></tr>
    </s:if> 
    </table>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="trialFundingdisplayJs.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
				<s:if test="trialFundingList != null">	
					<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="edit">Edit</span></span></s:a></li>
					<li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
				</s:if>
					<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="studyProtocolView.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>

		           
  	</s:form>
   </div>
 </body>
 </html>