<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
    function submitform() {
        var deleteReason = document.getElementById("deleteReason");
        var deleteReasonValue = deleteReason.options[deleteReason.selectedIndex].value;
        top.window.setDeleteReason(deleteReasonValue);
        window.top.hidePopWin(true); 
    }
</SCRIPT> 
</head> 
<body>
<div class="box">
<s:form id="subjectDeleteReason" name="subjectDeleteReason" >
    <div class="box">
        <table>
            <tr>
                <td>
                    Please select a reason below and then click OK to remove the subject from the study. Cancel to abort. 
                </td>
            </tr>
            <tr>
           <td>&nbsp; </td>
           </tr>
            <tr>
            <td>
            <label>Reason:<s:select id="deleteReason" name="deleteReason" headerValue="-Select-" headerKey="" list="reasonsList"  /></label>
           </td>
           </tr>
           
       </table>
       <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="submitform();"><span class="btn_img"><span class="save">OK</span></span></s:a>
                    <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
                </li>
            </ul>   
        </del>
       </div>
    </div>
</s:form>
</div>
</body>
</html>