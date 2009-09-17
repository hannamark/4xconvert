<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
    function submitform(persid, name)
    {   
        window.top.hidePopWin(true); 
    }
    function loadSelectedTrialType() {
    	 var action = '<%=request.getContextPath()%>';   
    	    
    	if(document.getElementById('selectedTrialTypeyes').checked==true) { 
    	    action = action + "/protected/submitProprietaryTrial.action";
        }
    	if(document.getElementById('selectedTrialTypeno').checked==true) {
    		action = action + "/protected/submitTrial.action"; 
        }
    	window.top.location = action;
        window.top.hidePopWin(true); 
    }
     
</SCRIPT> 
</head> 
<body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
<div class="box">
<s:form id="selectTypeOfTrial" name="selectTypeOfTrial" >
    <div class="box" align="center">
    Is Trial Proprietary 
       <s:radio name="selectedTrialType" id="selectedTrialType" list="#{'yes':'Yes', 'no':'No'}" onclick="loadSelectedTrialType();"/>
</div>
</s:form>
</div>

</body>
</html>