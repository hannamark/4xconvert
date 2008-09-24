<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialFunding.title"/></title>
    <s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">


function handleAction(){
 document.forms[0].action="trialFundingdelete.action";
 document.forms[0].submit();
} 

</SCRIPT>

<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialFunding.title" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form><s:actionerror/>
    <h2>NIH Grant Information</h2>
   <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="cbValue" value="${cbValue}" />
    <table class="form">
                <tr>
                    <td scope="row" class="label">
                        <label for="deletion"><dfn title="Context sensitive help text or tooltip here.">                      
                            <fmt:message key="trialFunding.inactiveCommentText"/>*</dfn>
                        </label>
                     </td>                     
                    <td class="value">
                        <s:textarea name="trialFundingWebDTO.inactiveCommentText" rows="4" cssStyle="width:206px"/>
                      </td>         
                </tr>                 
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Done</span></span></s:a></li>                
                </ul>   
            </del>
        </div>
                   
    </s:form>
   </div>
 </body>
 </html>