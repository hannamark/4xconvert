<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_submitted_institution" />
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <title>
      <fmt:message key="sbiReport.title" />
   </title>
   <link href="${stylePath}/reportui.css" rel="stylesheet" type="text/css" media="all" />
   <script type="text/javascript">
            jQuery(function() {
                   var tabOptions = {
                           selected: (jQuery("#resultTable").size() == 0) ? 0 : 1
                   };
                   jQuery("#reportui").tabs(tabOptions);   
                   var dateOptions = { 
                           buttonImage: viewerApp.imagePath + "/ico_calendar.gif",
                           buttonText: "<fmt:message key="selectDate.alt"/>",
                           showOn: "button" 
                   };
                   jQuery("#intervalStartDate").datepicker(dateOptions);
                   jQuery("#intervalEndDate").datepicker(dateOptions);
                   jQuery("#runButton").bind("click", function(ev) {
                        jQuery("#resultsTab").html(jQuery("div.template.loadingMessage").children().html());
                        jQuery("#reportui").tabs("select", 1);
                        var form = jQuery("#searchForm").get(0);
                        form.action="resultsSubmissionByInstitution.action";
                        form.submit();
                        });
                   jQuery("#resetButton").bind("click", function(ev) {
                       var form = jQuery("#searchForm").get(0);
                       form.action="criteriaSubmissionByInstitution.action";
                       form.submit();
                       });
                   });
   </script>
 </head>
 <body>
    <h1>
        <fmt:message key="sbiReport.title" />
    </h1>
    <s:if test="hasActionErrors()">
        <div class="error_msg">
            <s:actionerror />
        </div>
    </s:if>
    <div id="reportui">
        <!--Tabs-->
        <ul id="reporttabs" class="clearfix">
            <li><a href="#filtersTab"><fmt:message key="report.tab.filters" /> </a></li>
            <li><a href="#resultsTab"><fmt:message key="report.tab.results" /> </a></li>
        </ul>
        <!--/Tabs-->
        <div id="filtersTab">
            <s:form id="searchForm" name="sForm">
                <!--Filters-->
                <jsp:include page="/WEB-INF/jsp/nodecorate/submissionByInstitution/criteria.jsp" />
                <viewer:buttonBar>
                    <viewer:button labelKey="report.button.run" id="runButton" imgClass="search" />
                    <viewer:button labelKey="report.button.reset" id="resetButton" imgClass="cancel" />
                </viewer:buttonBar>
            </s:form>
        </div>
        <div id="resultsTab">
            <jsp:include page="/WEB-INF/jsp/nodecorate/submissionByInstitution/result.jsp" />
        </div>
    </div>
    <div id="templates">
        <jsp:include page="/WEB-INF/jsp/nodecorate/templates/loadingMessage.jsp" />
    </div>
  </body>