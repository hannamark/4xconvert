<script type="text/javascript" src="${scriptPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.selectmenu.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.sizes.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.hotkeys.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.jstree.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery.ui.potato.menu.js"></script>
<script type="text/javascript">
 jQuery.noConflict();
 var viewerApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
</script>
<script type="text/javascript" src="${scriptPath}/js/Help.js"></script>
<script type="text/javascript" src="${scriptPath}/ajax/prototype.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/effects.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/builder.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/controls.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/dragdrop.js"></script>
<script type="text/javascript" src="${scriptPath}/scriptaculous/slider.js"></script>
<script type="text/javascript" src="${scriptPath}/ajax/ajaxtags.js"></script>
<script type="text/javascript" src="${scriptPath}/ajax/ajaxtags_controls.js"></script>
<script type="text/javascript" src="${scriptPath}/ajax/ajaxtags_parser.js"></script>
<script type="text/javascript">
    Help.url = '<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getViewerHelpUrl()" />';
    var contextPath = '${pageContext.request.contextPath}';
</script>

