<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_summ4_types"/>  
<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><fmt:message key="summary4Report.title" /></title>
        <link href="${stylePath}/reportui.css" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript">
            jQuery(function() {
                var tabOptions = {
                    selected: (jQuery("#resultTable").size() == 0) ? 0 : 1
                };
                jQuery("#reportui").tabs(tabOptions); 
                if (tabOptions.selected == 1) {
                    jQuery("#resultui").tabs({});
                }
                var dateOptions = { 
                    buttonImage: viewerApp.imagePath + "/ico_calendar.gif",
                    buttonText: "<fmt:message key="selectDate.alt"/>",
                    showOn: "button" 
                };
                jQuery("#intervalStartDate").datepicker(dateOptions);
                jQuery("#intervalEndDate").datepicker(dateOptions);
                var orgNameAutoCompleteOptions = {
                    minLength: 2,
                    source: "${pageContext.request.contextPath}/ctro/json/summary4ReportGetOrganizationNames.action"
                };
                jQuery("#orgName").autocomplete(orgNameAutoCompleteOptions);
                jQuery("#orgSearchTypebyOrgName").bind("click", function(ev) {
                   jQuery("#orgName").removeAttr("disabled");
                   jQuery("#familyId").val("").attr("disabled", "disabled");     
                   jQuery("#organization_choices").html("");
                });
                jQuery("#orgSearchTypebyFamily").bind("click", function(ev) {
                    jQuery("#familyId").removeAttr("disabled");     
                    jQuery("#orgName").val("").attr("disabled", "disabled");
                 });
                jQuery("#familyId").bind("change", function(ev) {
                    var familyId = jQuery("#familyId").val();
                    if (familyId == "") {
                        jQuery("#organization_choices").html("");
                        return false;
                    }
                    var url = "${pageContext.request.contextPath}/ctro/ajax/refreshOrganizationsSummary4Report.action";
                    var params = { "criteria.familyId": familyId };
                    jQuery("#organization_choices").html(jQuery("div.template.loadingMessage").children().html()).load(url, params, function() {
                        jQuery("#orgSelectAllCheckbox").bind("click", function(ev) {
                            var selected = jQuery("#orgSelectAllCheckbox")[0].checked;
                            var options = jQuery("#orgNames")[0].options;
                            for (var i = 0; i < options.length; i++) {
                                options[i].selected = selected;
                            }
                        });
                    });
                    return false;
                });
                
                jQuery("#runButton").bind("click", function(ev) {
                    jQuery("#resultsTab").html(jQuery("div.template.loadingMessage").children().html());
                    jQuery("#reportui").tabs("select", 1);
                    var form = jQuery("#searchForm").get(0);
                    form.action="resultsSummary4Report.action";
                    form.submit();
                });
                jQuery("#resetButton").bind("click", function(ev) {
                    var form = jQuery("#searchForm").get(0);
                    form.action="criteriaSummary4Report.action";
                    form.submit();
                });
           });
        </script>
    </head>
    <body>
        <h1><fmt:message key="summary4Report.title"/></h1>
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
                <s:form id="searchForm" name="sForm">
                    <!--Filters-->
                    <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/criteria.jsp"/>
                    <viewer:buttonBar>
                        <viewer:button labelKey="report.button.run" id="runButton" imgClass="search" />
                        <viewer:button labelKey="report.button.reset" id="resetButton" imgClass="cancel" />
                    </viewer:buttonBar>
                </s:form>
            </div>
            <div id="resultsTab">
                <jsp:include page="/WEB-INF/jsp/nodecorate/summary4Report/result.jsp"/>
            </div>
        </div>    
        <div id="templates">
            <jsp:include page="/WEB-INF/jsp/nodecorate/templates/loadingMessage.jsp"/>
        </div>
    </body>
</html>