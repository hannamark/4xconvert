<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_ad_hoc"/> 
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="adHocReport.title" /></title>
        <link href="${stylePath}/ml_breadcrumbs.css" rel="stylesheet" type="text/css" media="all"/>
        <link href="${stylePath}/reportui.css" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript">
            var diseaseTree = <s:property escape="false" value="diseaseTree"/>;
        </script>
        <script type="text/javascript" src="${scriptPath}/js/ml_breadcrumbs.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/generic_tree.js"></script>
        <script type="text/javascript" src="${scriptPath}/pages/adHocReport/diseasesFilter.js"></script>
        <script type="text/javascript">
            jQuery(function() {
                   var tabOptions = {
                           selected: (jQuery("#resultTable").size() == 0) ? 0 : 1
                   };
                   jQuery("#reportui").tabs(tabOptions);       
                   var accordionOptions = {
                           autoHeight: false
                   };
                   jQuery("#filtersAccordion").accordion(accordionOptions);
                   jQuery("#runButton").bind("click", function(ev) {
                        jQuery("#resultsTab").html(jQuery("div.template.loadingMessage").children().html());
                        jQuery("#reportui").tabs("select", 1);
                        var form = jQuery("#searchForm").get(0);
                        form.action="resultsAdHocReport.action";
                        form.submit();
                        });
                   jQuery("#resetButton").bind("click", function(ev) {
                        var form = jQuery("#searchForm").get(0);
                        form.action="criteriaAdHocReport.action";
                        form.submit();
                        });
                   });
        </script>
    </head>
    <body>
    	<input type="hidden" id="imagePath" name="imagePath" value="${imagePath}" />
        <h1><fmt:message key="adHocReport.title"/></h1>
        <s:form id="searchForm" name="sForm">
            <s:token/>
            <s:if test="hasActionErrors()">
                <div class="error_msg">
                    <s:actionerror />
                </div>
            </s:if> 
            <div id="reportui">
                <!--Tabs-->
                <ul id="reporttabs" class="clearfix">
                    <li><a href="#filtersTab"><fmt:message key="report.tab.filters"/></a></li>
                    <li><a href="#resultsTab"><fmt:message key="report.tab.results"/></a></li>
                </ul>
                <!--/Tabs-->
                <div id="filtersTab">
                    <!--Filters-->
                    <div id="filtersAccordion">
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/registrationDetailsFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/diseasesFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/interventionsFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/participatingSitesFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/geographicAreaFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/biomarkersFilter.jsp"/>
                        <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/summary4Filter.jsp"/>
                    </div>
                    <viewer:buttonBar>
                        <viewer:button labelKey="report.button.run" id="runButton" imgClass="search" />
                        <viewer:button labelKey="report.button.reset" id="resetButton" imgClass="cancel" />
                    </viewer:buttonBar>
                </div>
                <div id="resultsTab">
                    <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/result.jsp"/>
                </div>
            </div>    
        </s:form>
        <div id="templates">
            <jsp:include page="/WEB-INF/jsp/nodecorate/templates/loadingMessage.jsp"/>
        </div>
    </body>
</html>