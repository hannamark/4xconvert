<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.interventions.header" /></a></h2>
<div id="interventionsSection">
    <fmt:message key="adHocReport.interventions.tableTitle" var="tableTitle"/>
    <fmt:message key="adHocReport.interventions.tableSummary" var="tableSummary"/>
    <table class="form-table" title="${tableTitle}" summary="${tableSummary}">
    	<tr><td>
            <div class="selectwidget">
                <div class="interventionrescol">
                    <fmt:message key="adHocReport.interventions.searchHeader" var="searchHeader"/>
                    <fmt:message key="adHocReport.interventions.searchHeader.alt" var="alt"/>
                    <h3><img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align:middle;" />${searchHeader}</h3>
                    <div class="quickresults">
                        <div class="quickresults_header">
                            <s:set name="interventionTypeValues" value="@gov.nih.nci.pa.enums.ActivitySubcategoryCode@getDisplayNames()" />
                            <s:select headerKey="All" headerValue="All" id="interventionType" name="interventionType" list="#interventionTypeValues"  
                                    value="criteria.interventionType" />
                            <div class="quickresults_header_buttons">
                                <fmt:message key="adHocReport.interventions.button.addType.title" var="title"/>
                                <a id="add_type_intervention" title="${title}" href="#"><fmt:message key="adHocReport.interventions.button.addType"/></a>
                            </div>
                            <div>
	                            <fmt:message key="adHocReport.interventions.interventionTitle" var="title"/>
	                            <div class="ui-widget">
	                                <input type="text" class="hintTextbox" id="intervention" name="intervention" maxlength="255" size="30" title="${title}" value="${title}"/>
	                            </div>
	                            <fmt:message key="adHocReport.interventions.searchMagnifier.alt" var="title"/>
	                            <input type="image" src="${imagePath}/ico_magnifier.gif" alt="${title}" class="search_inner_button" />
								<div class="quickresults_header_buttons">
									<fmt:message key="adHocReport.interventions.button.add.title" var="title"/>
	                                <a id="add_all_intervention" title="${title}" href="#"><fmt:message key="adHocReport.interventions.button.add"/></a>
	                                <fmt:message key="adHocReport.interventions.button.reset.title" var="title"/>
	                                <a id="reset_intervention" title="${title}" href="#"><fmt:message key="adHocReport.interventions.button.reset"/></a>
	                                <div class="clear"></div>
	                            </div>
	                        </div>    
                            <div class="whiteline"></div>
                            <div class="quickresults_count"><fmt:message key="adHocReport.interventions.quickResultCount"/></div>
                        </div>
                        <div class="quickresults_body">
                            <ul id="interventionslist">
                                <!-- List is built and inserted dynamically -->
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="selectionscol">
                    <fmt:message key="adHocReport.interventions.selectionHeader" var="selectionHeader"/>
                    <fmt:message key="adHocReport.interventions.selectionHeader.alt" var="alt"/>
                    <h3><img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align:middle;" />${selectionHeader}</h3>
                    <div id="intervention_selections_count" class="selections_count_normal"><fmt:message key="adHocReport.interventions.noselection"/></div>
                    <div class="clear"></div>
                    <div class="selectionslist">
                        <ul class="selectionslist_body">
                        </ul>
                    </div>  
                </div>
                <div class="clear"></div>
            </div>
    	</td></tr>
    </table>

	<div style="display:none">
		<s:select multiple="true" id="interventions" name="criteria.interventionIds" list="#{}"  value="criteria.interventionIds" />
        <s:select multiple="true" id="interventionTypes" name="criteria.interventionTypes" list="#{}"  value="criteria.interventionTypes" />
	</div>    
</div>