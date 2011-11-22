<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<h2><a href="#"><fmt:message key="adHocReport.diseases.header" /></a></h2>
<div id="diseasesSection">
    <fmt:message key="adHocReport.diseases.tableTitle" var="tableTitle"/>
    <fmt:message key="adHocReport.diseases.tableSummary" var="tableSummary"/>
    <table class="form-table" title="${tableTitle}" summary="${tableSummary}">
    	<tr>
            <td>
                <div class="selectwidget">
                    <div class="diseaserescol">
                        <fmt:message key="adHocReport.diseases.searchHeader" var="searchHeader"/>
                        <fmt:message key="adHocReport.diseases.searchHeader.alt" var="alt"/>
                        <h3><img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align:middle;" />${searchHeader}</h3>
                        <div class="quickresults">
                            <div class="quickresults_header">
                                <fmt:message key="adHocReport.diseases.diseaseTitle" var="title"/>
                                <div class="ui-widget">
                                    <input type="text" class="hintTextbox" id="disease" name="disease" maxlength="255" size="30" title="${title}" value="${title}"/>
                                </div>
                                <fmt:message key="adHocReport.diseases.searchMagnifier.alt" var="title"/>
                                <input type="image" src="${imagePath}/ico_magnifier.gif" alt="${title}" class="search_inner_button" />
                                <div class="quickresults_header_buttons">
                                    <fmt:message key="adHocReport.diseases.button.add.title" var="title"/>
                                    <a id="add_all_disease" title="${title}" href="#"><fmt:message key="adHocReport.diseases.button.add"/></a>
                                    <fmt:message key="adHocReport.diseases.button.tree.title" var="title"/>
                                    <a id="show_tree_disease" title="${title}" href="#"><fmt:message key="adHocReport.diseases.button.tree"/></a>
                                    <fmt:message key="adHocReport.diseases.button.reset.title" var="title"/>
                                    <a id="reset_disease" title="${title}" href="#"><fmt:message key="adHocReport.diseases.button.reset"/></a>
                                    <div class="clear"></div>
                                </div>
                                <div class="whiteline"></div>
                                <div class="quickresults_count"><fmt:message key="adHocReport.diseases.quickResultCount"/></div>
                            </div>
                            <div class="quickresults_body">
                                <div id="diseasebreadcrumbs">
                                    <!-- Boxes with breadcrumbs are built and inserted dynamically -->
                                </div>
                            </div>
                        </div>
                    </div>
    
                    <div class="selectionscol">
                        <fmt:message key="adHocReport.diseases.selectionHeader" var="selectionHeader"/>
                        <fmt:message key="adHocReport.diseases.selectionHeader.alt" var="alt"/>
                        <h3><img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align:middle;" />${selectionHeader}</h3>
                        <div id="disease_selections_count" class="selections_count_normal"><fmt:message key="adHocReport.diseases.noselection"/></div>
                        <div class="clear"></div>
                        <div class="selectionslist">
                            <ul class="selectionslist_body">
                            </ul>
                        </div>  
                    </div>
                    <div class="clear"></div>
                </div>
    	    </td>
        </tr>
    </table>

	<div style="display:none">
		<s:select multiple="true" id="pdqDiseases" name="criteria.pdqDiseases" list="#{}"  value="criteria.pdqDiseases" />
	</div>    
</div>