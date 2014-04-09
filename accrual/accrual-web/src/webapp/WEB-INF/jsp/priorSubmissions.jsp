<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="topic" scope="request" value="priorsubmissions"/>
<head>
    <title><fmt:message key="priorSubmissions.title"/></title>
    <s:head/>
    <script type="text/javascript" language="javascript">
      function handleSearch(){
        document.forms[0].action="priorSubmissions.action";
        document.forms[0].submit();
      }
    </script>
</head>
<body>
<script>
$(function()
		{
		    var export1 = document.getElementById('export');
		    if(export1 != null)
		    {
		        var export2 = export1.cloneNode(true);
		        $("#pageCountPar").after(export2.firstChild);
	            export1.parentNode.removeChild(export1);
		    }
		});

$('#priorSubmissions').addClass("active");
</script>
<div class="container">
    <h1 class="heading"><span><fmt:message key="priorSubmissions.title"/></span></h1>
  <s:form name="listForm" cssClass="form-horizontal" role="form">
    <div class="form-group">
        <p style = "inline-block;font-weight: 700;" class="col-xs-3 control-label"><fmt:message key="priorSubmissions.dates.label"/><br/>
          <small>(optional)</small> </p>
        <div class="col-xs-3 p0">
          <div id="datetimepicker" class="datetimepicker input-append">
            <p class="form-control-static pull-left mr10"><label for="dateFrom"><em><fmt:message key="priorSubmissions.dates.from"/></em></label></p>
            <s:textfield data-format="MM/dd/yyyy" cssClass="form-control" placeholder="mm/dd/yyyy" id ="dateFrom" name="dateFrom"/>
            <span class="add-on btn-default"><i class="fa-calendar"></i></span><accrual:displayTooltip tooltip="tooltip.from"/></div>
            
        </div>
        <div class="col-xs-3">
          <div id="datetimepicker" class="datetimepicker input-append">
            <p class="form-control-static pull-left mr10"><label for="dateTo"><em><fmt:message key="priorSubmissions.dates.to"/></em></label></p>
            <s:textfield data-format="MM/dd/yyyy" cssClass="form-control" placeholder="mm/dd/yyyy" id ="dateTo" name="dateTo"/>
            <span class="add-on btn-default"><i class="fa-calendar"></i></span><accrual:displayTooltip tooltip="tooltip.to"/> </div>
            
        </div>
      </div>
      <div class="form-group">
        <div class="col-xs-4 col-xs-offset-3 mt20">
          <button type="button" class="btn btn-icon btn-primary" onclick="handleSearch()"> <i class="fa-search"></i>Search</button>
        </div>
      </div>
  </s:form>
  <s:if test="hasActionErrors()"><div class="alert alert-danger"> <i class="fa-exclamation-circle"></i><strong>Error:</strong><s:actionerror />.</div></s:if>
<h3 class="heading mt20"><span>Search Results</span></h3>
  <display:table class="table table-striped sortable" summary="This table contains list of submissions.  Please use column headers to sort results"
                  sort="list" pagesize="10" id="row" name="displayTagList" requestURI="priorSubmissions.action" export="true">                                             
       <display:setProperty name="export.xml" value="false"/>
       <display:setProperty name="export.excel.filename" value="resultsPriorSubmissions.xls"/>
       <display:setProperty name="export.excel.include_header" value="true"/>
       <display:setProperty name="export.csv.filename" value="resultsPriorSubmissions.csv"/>
       <display:setProperty name="export.csv.include_header" value="true"/>
       <display:column titleKey="priorSubmissions.list.nciNumber" property="nciNumber" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.file" sortable="false" headerClass="sortable" headerScope="col" media="html">
        <s:if test="%{#attr.row.batchFileIdentifier != null}">
	        <s:url id="viewDocUrl" action="priorSubmissionsviewDoc">
	            <s:param name="batchFileId" value="%{#attr.row.batchFileIdentifier}" />
	        </s:url>
	        <s:a href="%{viewDocUrl}"><s:property value="%{#attr.row.fileName}" /></s:a>
        </s:if>
		<s:elseif test="%{#attr.row.completeTrialId != null}">
		    <s:url id="completeTrialUrl" action="patientsretrieve">
                <s:param name="studyProtocolId" value="%{#attr.row.completeTrialId}" />
                <s:param name="selectedRowIdentifier" value="%{#attr.row.studySubjectId}" />
            </s:url>
            <s:a href="%{completeTrialUrl}"><s:property value="%{#attr.row.assignedIdentifier}" /></s:a>
		</s:elseif>
		<s:elseif test="%{#attr.row.abbreviatedTrialId != null}">
		    <s:url id="abbreviatedTrialUrl" action="industrialPatients">
                <s:param name="studyProtocolId" value="%{#attr.row.abbreviatedTrialId}" />
            </s:url>
            <s:a href="%{abbreviatedTrialUrl}">Trial counts</s:a>
		</s:elseif>
       </display:column>
       <display:column titleKey="priorSubmissions.list.file" sortable="false" headerClass="sortable" headerScope="col" media="excel csv">
        <s:if test="%{#attr.row.batchFileIdentifier != null}">
            <s:property value="%{#attr.row.fileName}" />
        </s:if>
        <s:elseif test="%{#attr.row.completeTrialId != null}">
            <s:property value="%{#attr.row.assignedIdentifier}" />
        </s:elseif>
        <s:elseif test="%{#attr.row.abbreviatedTrialId != null}">
            Trial counts
        </s:elseif>
       </display:column>
       <display:column titleKey="priorSubmissions.list.submissionType" property="submissionType.code" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.date" property="date" format="{0,date,yyyy-MM-dd HH.mm.ss}" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.user" property="username" sortable="true" headerClass="sortable" headerScope="col"/>
       <display:column titleKey="priorSubmissions.list.result" property="result" sortable="true" headerClass="sortable" headerScope="col"/>
   </display:table>
   </div> 
</body>