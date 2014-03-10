<script type="text/javascript" src="${scriptPath}/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
<script type="text/javascript">
 jQuery.noConflict();
 var registryApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
</script>
<script type="text/javascript" src="${scriptPath}/js/calendarpopup.js"></script>
<script type="text/javascript" src="${scriptPath}/js/tooltip.js"></script>
<script type="text/javascript" src="${scriptPath}/js/showhide.js"></script>
<script type="text/javascript" src="${scriptPath}/js/popup.js"></script>
<script type="text/javascript" src="${scriptPath}/js/Help.js"></script>
<script type="text/javascript">
    Help.url = '<s:property value="@gov.nih.nci.pa.util.PaEarPropertyReader@getRegistryHelpUrl()" />';
    var contextPath = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript" src="${scriptPath}/js/overlib.js"></script>
<script type="text/javascript" src="${scriptPath}/js/cal2.js"></script>
<script type="text/javascript" src="${scriptPath}/js/ajaxHelper.js"></script>
<!-- Javascript -->
<script type='text/javascript' src="${scriptPath}/js/css3-mediaqueries.js"></script>
