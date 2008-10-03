<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialDocument.addtitle" /></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
var page;
page=document.forms[0].page.value;
if (page == "Edit"){
 document.forms[0].action="trialDocumentupdate.action";
 document.forms[0].submit(); 
} else {
 document.forms[0].action="trialDocumentcreate.action";
 document.forms[0].submit();   
 } 
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
</SCRIPT>
<body onload="setFocusToFirstControl();">

 <h1><fmt:message key="trialDocument.addtitle" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
    <s:form method="POST" enctype="multipart/form-data"><s:actionerror/>
    <h2><fmt:message key="trialDocument.addtitle" /></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="id" value="${id}" />
    <table class="form">                
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="trialDocument.type"/>*</dfn>
                     </label>
                    </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.DocumentTypeCode@getDisplayNames()" />
                      <td class="value">
                        <s:select headerKey="" headerValue="" 
                           name="trialDocumentWebDTO.typeCode" 
                           list="#typeCodeValues"  
                           cssStyle="width:206px"/>
                      </td>         
                </tr>                
                <tr>
                     <td scope="row" class="label">
                     <label for="fileName"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="trialDocument.fileName"/>*</dfn>
                     </label>
                    </td>
                    <td class="value">
                        <s:file name="upload" cssStyle="width:270px"/>
                      </td>         
                </tr>                 
        </table>
		<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                </ul>   
            </del>
        </div> 

                   
    </s:form>
   </div>
 </body>
 </html>
