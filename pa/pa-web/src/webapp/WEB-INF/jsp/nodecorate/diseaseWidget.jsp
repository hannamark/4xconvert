<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="runadhoc" />

<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>

<title><fmt:message key="diseases.widget.title" /></title>

<link href="${stylePath}/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${stylePath}/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<link href="${stylePath}/style.css" media="all" rel="stylesheet" type="text/css"/>
<link rel="address bar icon" href="${imagePath}/favicon.ico" />
<link rel="icon" href="${imagePath}/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${imagePath}/favicon.ico" type="image/x-icon" />

<link href="<c:url value='/styles/disease/jquery.ui.potato.menu.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/jquery-ui/ui-lightness/jquery-ui-1.8.16.custom.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/jquery-ui/ui-lightness/jquery.ui.selectmenu.css'/>" media="all" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/disease/ml_breadcrumbs.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<c:url value='/styles/disease/reportui.css'/>" rel="stylesheet" type="text/css" media="all" />


<script type="text/javascript">
    var diseaseTree = <s:property escape="false" value="diseaseTree"/>;
</script>

<script type="text/javascript" src="${scriptPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.selectmenu.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.sizes.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.hotkeys.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.jstree.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.potato.menu.js"></script>

<script type="text/javascript">
 jQuery.noConflict();
 var paApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
</script>

<script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>
<script type="text/javascript" src="${scriptPath}/js/coppa.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModalcommon.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModal.js"></script>

<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/ml_breadcrumbs.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/generic_tree.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/cookies_support.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/disease/diseasesFilter.js'/>"></script>

<script type="text/javascript">
function addDisease(diseaseid) {
    // first get the elements into a list
    var domelts = jQuery('#pdqDiseases option');
    // next translate that into an array of just the values
    var values = jQuery.map(domelts, function(elt, i) { return jQuery(elt).val();});
    
    var url = '<%=request.getContextPath()%>/protected/popupDisaddDiseases.action?selectedDiseaseIds='+values;
    jQuery.get(url, function(data) {
        window.top.hidePopWin(true);
    });
    
}
</script>
</head>
<body>
    <h2>
        <a href="#"><fmt:message key="diseases.widget.header" /></a>
    </h2>
    
     <div id="diseasesSection">
         <fmt:message key="diseases.widget.tableTitle" var="tableTitle" />
         <fmt:message key="diseases.widget.tableSummary" var="tableSummary" />
         
         <table class="form-table" title="${tableTitle}" summary="${tableSummary}">
             <tr>
                 <td>
                     <div class="selectwidget">
                         <div class="diseaserescol">
                             <fmt:message key="diseases.widget.searchHeader" var="searchHeader" />
                             <fmt:message key="diseases.widget.searchHeader.alt" var="alt" />
                             <h3>
                                 <img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align: middle;" />${searchHeader}
                             </h3>
                             <div class="quickresults">
                                 <div class="quickresults_header">
                                     <fmt:message key="diseases.widget.diseaseTitle" var="title" />
                                     <div class="ui-widget">
                                         <input type="text" class="hintTextbox" id="disease" name="disease" maxlength="255" size="30" title="${title}" value="${title}" />
                                     </div>
                                     <fmt:message key="diseases.widget.searchMagnifier.alt" var="title" />
                                     <input type="image" src="${imagePath}/ico_magnifier.gif" alt="${title}" class="search_inner_button" />
                                     <div class="quickresults_header_buttons right">
                                         <fmt:message key="diseases.widget.button.add.title" var="title" />
                                         <a id="add_all_disease" title="${title}" href="#"><fmt:message key="diseases.widget.button.add" /></a>
                                         <fmt:message key="diseases.widget.button.tree.title" var="title" />
                                         <a id="show_tree_disease" title="${title}" href="#">
                                         <fmt:message key="diseases.widget.button.tree" /></a>
                                         <fmt:message key="diseases.widget.button.reset.title" var="title" />
                                         <a id="reset_disease" title="${title}" href="#">
                                         <fmt:message key="diseases.widget.button.reset" /></a>
                                         <div class="clear"></div>
                                     </div>
                                     <div class="whiteline"></div>
                                     <div class="quickresults_count">
                                         <fmt:message key="diseases.widget.quickResultCount" />
                                     </div>
                                 </div>
                                 <div class="quickresults_body">
                                     <div id="diseasebreadcrumbs">
                                         <!-- Boxes with breadcrumbs are built and inserted dynamically -->
                                     </div>
                                 </div>
                             </div>
                         </div>

                         <div class="selectionscol">
                             <fmt:message key="diseases.widget.selectionHeader" var="selectionHeader" />
                             <fmt:message key="diseases.widget.selectionHeader.alt" var="alt" />
                             <h3>
                                 <img src="${imagePath}/arrow_white_down.gif" width="9" height="9" alt="${alt}" style="vertical-align: middle;" />${selectionHeader}
                             </h3>
                             <div id="disease_selections_count" class="selections_count_normal">
                                 <fmt:message key="diseases.widget.noselection" />
                             </div>
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

         <div style="display: none">
             <s:select multiple="true" id="pdqDiseases" name="pdqDiseases" list="#{}" value="pdqDiseases" />
         </div>
     </div>

     <div class="actionsrow">
         <del class="btnwrapper">
             <ul class="btnrow">
                 <pa:scientificAbstractorDisplayWhenCheckedOut>
                      <a href="javascript:void(0)" class="btn" onclick="addDisease()">
                          <span class="btn_img"><span class="add">Add</span></span>
                      </a>                                
                 </pa:scientificAbstractorDisplayWhenCheckedOut>
             </ul>
         </del>
     </div>        
</body>
</html>