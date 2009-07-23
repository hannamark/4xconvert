<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialIndide.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">


function handleAction(studyProtocolIi){   
    document.forms[0].cbValue.value = studyProtocolIi;
    document.forms[0].page.value = "Edit";
    document.forms[0].action="trialIndideedit.action";
    document.forms[0].submit();  
}
function handleDelete(studyProtocolIi){ 
	input_box=confirm("Click OK to remove the IND/IDE from the Study.  Cancel to Abort.");
	if (input_box==true){
    document.forms[0].cbValue.value = studyProtocolIi;
    document.forms[0].page.value = "Delete";
    document.forms[0].action="trialIndidedelete.action";
    document.forms[0].submit();
	}
}

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialIndide.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
  <pa:sucessMessage/>
    <s:form><s:actionerror/>
    <h2><fmt:message key="trialIndide.subtitle" /></h2>
    <s:if test="studyIndideList != null">
    <input type="hidden" name="page" />
    <input type="hidden" name="cbValue" />
    <c:if test="${fn:length(studyIndideList) > 10}">
    <div style="overflow:auto; height:356px;width:968px;">
    </c:if>
    <display:table name="${studyIndideList}" id="row" class="data" sort="list"  pagesize="200" requestURI="trialIndidequery.action" export="false">    
        <display:column titleKey="trialIndide.indldeType" property="indldeType" sortable="true" headerClass="sortable" />
        <display:column titleKey="trialIndide.indideNumber" property="indldeNumber" sortable="true" headerClass="sortable" />
        <display:column titleKey="trialIndide.grantor" property="grantor"   sortable="true" headerClass="sortable"/>
        <display:column titleKey="trialIndide.holderType" property="holderType"   sortable="true" headerClass="sortable"/>
    	<display:column titleKey="trialIndide.nihInstHolderCode" property="nihInstHolder"   sortable="true" headerClass="sortable"/>
    	<display:column titleKey="trialIndide.nciDivProgHolderCode" property="nciDivProgHolder"   sortable="true" headerClass="sortable"/>
    	<display:column titleKey="trialIndide.expandedAccessIndicator" property="expandedAccessIndicator"   sortable="true" headerClass="sortable"/>
    	<display:column titleKey="trialIndide.expandedAccessStatusCode" property="expandedAccessStatus"   sortable="true" headerClass="sortable"/>
    	
        <display:column title="Edit" class="action">
            <s:a href="#" onclick="handleAction(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
        </display:column>
        <display:column title="Delete" class="action">
        	<s:a href="#" onclick="handleDelete(%{#attr.row.id})"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
        </display:column>
    </display:table>
    <c:if test="${fn:length(studyIndideList) > 10}">
    </div>
    </c:if>
  </s:if> 
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="trialIndide.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					<c:choose>
                    <c:when test="${sessionScope.trialSummary.documentWorkflowStatusCode.code  == 'Submitted'}">
						<li><a href="trialFundingquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
						<li><a href="trialValidationquery.action?studyProtocolId=<c:out value='${sessionScope.trialSummary.studyProtocolId }'/>" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Next</span></span></a></li>
					</c:when>
					<c:otherwise>
                    	<li><a href="irb.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                    	<li><a href="studyOverallStatus.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
					</c:otherwise>
					</c:choose>
                </ul>   
            </del>
        </div>
                   
    </s:form>
   </div>
 </body>
 </html>