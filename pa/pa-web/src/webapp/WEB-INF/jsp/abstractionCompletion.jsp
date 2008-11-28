<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Abstraction Completion</title>
    <s:head />
</head>
<body onload="setFocusToFirstControl();">
 <h1>Abstraction Completion</h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2>Abstraction Completion</h2>
    <s:if test="abstractionList != null">    
    <display:table name="${abstractionList}" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="true">
    	<display:setProperty name="export.excel.filename" value="AbstractionCompletion.xls"/>
    	<display:column title="errorCode" property="errorCode" sortable="true" headerClass="sortable" />
    	<display:column title="errorDescription" property="errorDescription" sortable="true" headerClass="sortable" />
	    <display:column title="errorType" property="errorType"  sortable="true" headerClass="sortable" />
	    <display:column title="comment" property="comment" sortable="true" headerClass="sortable" />	
	</display:table>
	</s:if>
		<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="abstractionCompletionquery.action" cssClass="btn"><span class="btn_img"><span class="save">Validate</span></span></s:a></li>                
                </ul>   
            </del>
        </div> 

                   
    </s:form>
   </div>
 </body>
 </html>