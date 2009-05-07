<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="trial_list"/> 
<SCRIPT LANGUAGE="JavaScript">
function handleAction(){
     document.forms[0].action="trialListgetReport.action";
     document.forms[0].submit();
}
</SCRIPT>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="trialList.header"/></h1>
    <s:form>
        <table class="form">    
            <tr> 
                <td scope="row" class="label">
                    <label for="ctrpOnly"> <fmt:message key="trialList.criteria.ctrpOnly"/></label>
                </td>
                <td>
                    <s:checkbox name="criteria.ctrpOnly" />
                </td>
            </tr>
            <tr> 
                <td scope="row" class="label">
                    <label for="userOnly"> <fmt:message key="trialList.criteria.userOnly"/></label>
                </td>
                <td>
                    <s:checkbox name="criteria.userOnly" />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                    </li>
                </ul>   
            </del>
        </div>
    </s:form>

    <div class="line"></div>
    <s:textfield name="results" maxlength="200" size="100" cssStyle="width:200px"/>  
</body>
</html>