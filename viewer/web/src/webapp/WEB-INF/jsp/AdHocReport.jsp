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
            var interventions = <s:property escape="false" value="interventions"/>;
        </script>
        <script type="text/javascript" src="${scriptPath}/js/ml_breadcrumbs.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/generic_tree.js"></script>
        <script type="text/javascript" src="${scriptPath}/js/cookies_support.js"></script>
        <script type="text/javascript" src="${scriptPath}/pages/adHocReport/diseasesFilter.js"></script>
        <script type="text/javascript" src="${scriptPath}/pages/adHocReport/interventionsFilter.js"></script>
        <script type="text/javascript">
            jQuery(function() {
                    var tabOptions = {
                        selected: (jQuery("#resultTable").size() == 0) ? 0 : 1,
                        select: function(event, ui) {
                            if( ui.index != 0 ) { // This is the index of the tab in the group of tabs. 0 is for "Report Filters" so the logic is 'clicking on any tab except report filters'. 
                                jQuery.diseasesFilter.saveState();
                                jQuery.interventionsFilter.saveState();
                            }
                        }
                    };
                    jQuery("#reportui").tabs(tabOptions);       
                    var accordionOptions = {
                        autoHeight: false,
                        active: false,
                        collapsible: true
                    };
                    var firstAccordionOptions = {
                        autoHeight: false,
                        collapsible: true
                    };
                    // Activate accordions
                    jQuery("#detailsAccordion").accordion(firstAccordionOptions);
                    jQuery("#diseaseAccordion").accordion(accordionOptions);
                    jQuery("#interventionsAccordion").accordion(accordionOptions);
                    jQuery("#sitesAccordion").accordion(accordionOptions);
                    jQuery("#geographicAreaAccordion").accordion(accordionOptions);
                    jQuery("#biomarkersAccordion").accordion(accordionOptions);
                    jQuery("#summaryAccordion").accordion(accordionOptions);
                    // Expand/Collapse all functionality
                    jQuery("#expandAll a").bind("click", function(ev) {
                        expandOrCollapseAll(false);
                        jQuery("#expandAll").toggle();
                        jQuery("#collapseAll").toggle();
                    });
                    jQuery("#collapseAll a").bind("click", function(ev) {
                        expandOrCollapseAll(true);
                        jQuery("#expandAll").toggle();
                        jQuery("#collapseAll").toggle();
                    });
                    jQuery("#runButton").bind("click", function(ev) {
                        jQuery("#resultsTab").html(jQuery("div.template.loadingMessage").children().html());
                        jQuery("#reportui").tabs("select", 1);
                        jQuery("#pdq_tree_dialog").dialog('close');
                        jQuery.diseasesFilter.saveState();
                        jQuery.interventionsFilter.saveState();
                        var form = jQuery("#searchForm").get(0);
                        form.action="resultsAdHocReport.action";
                        form.submit();
                        });
                    jQuery("#resetButton").bind("click", function(ev) {
                        var form = jQuery("#searchForm").get(0);
                        form.action="criteriaAdHocReport.action";
                        form.submit();
                        });
                    
                    jQuery("#country").bind("change", function(ev) {
                        selectStatesBasedOnCountry();
                    });
                    
                    function selectStatesBasedOnCountry() {
                        if (jQuery("#country").val() == "USA" ) {
                            jQuery("#states").prop("disabled",false);
                            jQuery("#states option[value=INTERNATIONAL]").remove();
                        } else {
                            if (jQuery("#states option[value=INTERNATIONAL]").length == 0) {
                                jQuery('#states option[value=]').after('<option value="INTERNATIONAL">None (International)</option>');
                            }
                            if (jQuery("#country").val() == "") {
                                jQuery("#states").prop("disabled",false);
                            } else {
                                jQuery("#states").prop("disabled",true);
                                jQuery("#states").val("INTERNATIONAL");
                            }  
                        }
                    }
                    selectStatesBasedOnCountry();
                    
                    jQuery("#familyId").bind("change", function(ev) {
                         var familyId = jQuery("#familyId").val();                                          
                         var url = viewerApp.contextPath + "/ctro/ajax/refreshAdHocOrganizations.action";                    
                         var params = { "criteria.familyId": familyId};
                         jQuery("#organization_choices").html(jQuery("div.template.loadingMessage").children().html()).load(url, params, function() {
                         if (jQuery("#familyId").val() != "0") {
                             jQuery("#orgSelectAllCheckbox")[0].checked = true;
                             selectAllLeadOrganizations();
                         }
                         else {
                             jQuery("#orgSelectAllCheckbox")[0].checked = false; 
                         }
                             jQuery("#orgSelectAllCheckbox").bind("click", selectAllLeadOrganizations);
                         });
                         return false;
                     });  
                     jQuery("#orgSelectAllCheckbox").bind("click", selectAllLeadOrganizations);
                     
                     jQuery("#participatingSiteFamilyId").bind("change", function(ev) {
                         var familyId = jQuery("#participatingSiteFamilyId").val();                                          
                         var url = viewerApp.contextPath + '/ctro/ajax/refreshAdHocParticipatingSites.action';                    
                         var params = { "criteria.participatingSiteFamilyId": familyId};
                         jQuery("#sites_choices").html(jQuery("div.template.loadingMessage").children().html()).load(url, params, function() {
                             if (jQuery("#participatingSiteFamilyId").val() != "0") {
                                 jQuery("#participatingSitesSelectAllCheckbox")[0].checked = true;
                                 selectAllParticipatingSite();
                             }
                             else {
                                 jQuery("#participatingSitesSelectAllCheckbox")[0].checked = false; 
                             }
                             jQuery("#participatingSitesSelectAllCheckbox").bind("click", selectAllParticipatingSite);
                         });
                         return false;
                     });   
                     jQuery("#participatingSitesSelectAllCheckbox").bind("click", selectAllParticipatingSite);
                    });
            
                    // Select all the lead organizations of a family
                    function selectAllLeadOrganizations(ev) {
                        var selected = jQuery("#orgSelectAllCheckbox")[0].checked;
                        var options = jQuery("#orgNames")[0].options;
                        for (var i = 0; i < options.length; i++) {
                            options[i].selected = selected;
                        }
                    }
                    
                    // Select all the participating sites of a family
                    function selectAllParticipatingSite(ev) {
                        var selected = jQuery("#participatingSitesSelectAllCheckbox")[0].checked;
                        var options = jQuery("#siteNames")[0].options;
                        for (var i = 0; i < options.length; i++) {
                            options[i].selected = selected;
                        }
                    }
            
                    // Expands all sections if 'currentlyVisible' is false, otherwise collapses all
                    function expandOrCollapseAll(currentlyVisible) {
                        expandOrCollapseSection('#detailsSection', '#detailsAccordion', currentlyVisible);
                        expandOrCollapseSection('#diseasesSection', '#diseaseAccordion', currentlyVisible);
                        expandOrCollapseSection('#interventionsSection', '#interventionsAccordion', currentlyVisible);
                        expandOrCollapseSection('#participatingSitesSection', '#sitesAccordion', currentlyVisible);
                        expandOrCollapseSection('#geographicAreaSection', '#geographicAreaAccordion', currentlyVisible);
                        expandOrCollapseSection('#biomarkersSection', '#biomarkersAccordion', currentlyVisible);
                        expandOrCollapseSection('#summary4Section', '#summaryAccordion', currentlyVisible);
                    }
            
                    // Triggers expand/collapse section if 'currentlyVisible' matches
                    function expandOrCollapseSection(sectionName, accordionName, currentlyVisible) {
                        if (jQuery(sectionName).is(":visible") == currentlyVisible) {
                            jQuery(accordionName + " .ui-accordion-header").click();
                        }
                    }
                    
                    function generateTSR(id) {
                        var form = document.sForm;
                        form.target = "TSR";
                        form.action = viewerApp.contextPath + "/ctro/ajax/resultsAdHocReportviewTSR.action?studyProtocolId=" + id;
                        form.submit();
                    }
                     
        </script>     
    </head>
    <body>
        <h1><fmt:message key="adHocReport.title"/></h1>
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
                    <s:token/>
                    <!--Filters-->
                    <div id="filtersAccordion">
                        <div id="expandAll">
                            <a href="#"><fmt:message key="adHocReport.expand" /></a>
                        </div>
                        <div id="collapseAll" style="display: none;">
                            <a href="#"><fmt:message key="adHocReport.collapse" /></a>
                        </div>
                        <div>
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/registrationDetailsFilter.jsp"/>
                        </div>
                        <div id="diseaseAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/diseasesFilter.jsp"/>
                        </div>
                        <div id="interventionsAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/interventionsFilter.jsp"/>
                        </div>
                        <div id="sitesAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/participatingSitesFilter.jsp"/>
                        </div>
                        <div id="geographicAreaAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/geographicAreaFilter.jsp"/>
                        </div>
                        <div id="biomarkersAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/biomarkersFilter.jsp"/>
                        </div>
                        <div id="summaryAccordion">
                            <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/summary4Filter.jsp"/>
                        </div>
                    </div>
                    <viewer:buttonBar>
                        <viewer:button labelKey="report.button.run" id="runButton" imgClass="search" />
                        <viewer:button labelKey="report.button.reset" id="resetButton" imgClass="cancel" />
                    </viewer:buttonBar>
                </s:form>
            </div>
            <div id="resultsTab">
                <jsp:include page="/WEB-INF/jsp/nodecorate/adHocReport/result.jsp"/>
            </div>
        </div>    
        <div id="templates">
            <jsp:include page="/WEB-INF/jsp/nodecorate/templates/loadingMessage.jsp"/>
        </div>
    </body>
</html>