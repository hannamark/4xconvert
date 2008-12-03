 <!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="subGroups.title"/></title>
	<s:head />
</head>
 <body onload="setFocusToFirstControl();">

 <h1><fmt:message key="subGroups.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box"> 
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>
    <h2><fmt:message key="subGroups.subtitle" /></h2>
    <s:if test="subGroupsList != null">
    <display:table name="${subGroupsList}" id="row" class="data" sort="list"  pagesize="10" requestURI="subGroupsquery.action" export="false">    
	    <display:column titleKey="subGroups.code" property="groupNumberText" sortable="true" headerClass="sortable" />
	    <display:column titleKey="subGroups.description" property="description" sortable="true" headerClass="sortable" />
	    <display:column title="Edit" class="action">
    		<s:url id="url" action="subGroupsedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_edit.gif" alt="Edit" width="16" height="16"/></s:a>
    	</display:column>    	
    	<display:column title="Delete" class="action">
			<s:url id="url" action="subGroupsdelete"><s:param name="id" value="%{#attr.row.id}" /></s:url>
    		<s:a href="%{url}"><img src="<%=request.getContextPath()%>/images/ico_delete.gif" alt="Delete" width="16" height="16"/></s:a>
		</display:column>  
    	</display:table>
  </s:if> 
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="subGroupsinput.action" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
					<s:if test="${sessionScope.trialSummary.studyProtocolType  == 'InterventionalStudyProtocol'}">
                       <li><a href="trialArms.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                    </s:if>
                    <s:else>
                      <li><a href="trialArmsobservational.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
                    </s:else>
					<li><a href="abstractionCompletionquery.action" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>
				</ul>	
			</del>
		</div>
		           
  	</s:form>
   </div>
 </body>
 </html>
