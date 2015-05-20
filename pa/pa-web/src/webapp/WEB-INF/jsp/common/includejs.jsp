<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty disableDefaultJQuery}">
	<script type="text/javascript" src="${scriptPath}/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.ui.selectmenu.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.sizes.min.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.jstree.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.json-2.3.min.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/jquery.ui.potato.menu.js"></script>
	<script type="text/javascript" src="${scriptPath}/js/select2.min.js"></script>
    <script type="text/javascript">
      jQuery.noConflict();
    </script>
 </c:if>
 
 <script type="text/javascript">
 var paApp = {
   contextPath: "${pageContext.request.contextPath}",
   imagePath: "${imagePath}",
   scriptPath: "${scriptPath}",
   staticPath: "${staticPath}",
   stylePath: "${stylePath}"
 };
</script>

<script type="text/javascript" src="${scriptPath}/js/Help.js"></script>
<script type="text/javascript" src="${scriptPath}/js/prototype.js"></script>
<script type="text/javascript" src="${scriptPath}/js/coppa.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModalcommon.js"></script>
<script type="text/javascript" src="${scriptPath}/js/subModal.js"></script>
<script type="text/javascript" src="${scriptPath}/js/ajaxHelper.js"></script>
<script type="text/javascript" src="${scriptPath}/js/cal2.js"></script>
<script type="text/javascript" src="${scriptPath}/js/showhide.js"></script>