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
    <table>
    <tr>
        <td colspan="2">
        Is your trial proprietary?  
        <s:radio name="selectedTrialType" id="selectedTrialType" list="#{'yes':'Yes', 'no':'No'}"/>
       </td>
       </tr>
       <tr>
       <td colspan="2">&nbsp; </td>
       </tr>
       <tr>
       <td colspan="2">&nbsp; </td>
       </tr>
       <tr>
       <td colspan="2">
       <div class="actionsrow">
                <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#"                
                        class="btn" onclick="loadSelectedTrialType();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
                    <li><a href="#"                
                        class="btn" onclick="submitform();"><span class="btn_img"><span class="cancel">Cancel</span></span></a></li>          
                </ul>   
                </del>
            </div>
       </td>
       </tr>
       <tr>
            <td>
                <b><i><fmt:message key="trial.proprietary"/></i></b>
            </td>
            
            <td>
               <fmt:message key="trial.docInstructionalText_one"/>
            </td>
        </tr>
        <tr>
            <td>
                <b><i><fmt:message key="trial.non-proprietary"/></i></b>
            </td>
            <td>
               <fmt:message key="trial.docInstructionalText_two"/>
            </td>
        </tr>
        </table>
</div>
</s:form>
</div>

</body>
</html>