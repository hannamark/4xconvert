<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<SCRIPT LANGUAGE="JavaScript">
function generateReport() {
    document.forms[0].action="pdqstartProcess.action";
    document.forms[0].submit();
}
</script>
 <body>
 <s:form name="pdq">
   <s:if test="hasActionErrors()">
        <div class="error_msg">
            <s:actionerror/>
        </div>
   </s:if>
   <s:actionmessage/>
    <display:table class="data" sort="list" pagesize="200" id="row"
              name="${requestScope.listOfFileNames}" export="false">
    <display:column class="title" title="File Name" sortable="true" headerScope="col" scope="row">
            <a href="pdqgetFileByDate.action?date=${row}">${row}</a>
      </display:column>
    </display:table>
    <c:if test="${empty requestScope.showButton}">
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#" class="btn" onclick="generateReport();">
                    <span class="btn_img"><span class="save">Run</span></span></a></li>
                </ul>   
            </del>
        </div>
   </c:if>
   </s:form> 
 </body>
 </html>