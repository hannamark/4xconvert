<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="subGroups.addtitle" /></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

function handleAction(){
var page;
page=document.forms[0].page.value;
input_box=confirm("Click OK to save changes or Cancel to Abort.");
if (input_box==true){
    if (page == "Edit"){
 		document.forms[0].action="subGroupsupdate.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="subGroupscreate.action";
 		document.forms[0].submit();   
 	} 
 }
} 
function tooltip() {
BubbleTips.activateTipOn("acronym");
BubbleTips.activateTipOn("dfn"); 
}
</SCRIPT>
<body onload="setFocusToFirstControl();">
<c:set var="topic" scope="request" value="abstract_subgroups"/>
 <h1><fmt:message key="subGroups.addtitle" /></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2><fmt:message key="subGroups.addtitle" /></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="id" value="${id}" />
    <table class="form">                
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode">
                            <fmt:message key="subGroups.code"/><span class="required">*</span>
                     </label>
                    </td>
    				<td class="value">    					
                    		<s:textfield name="subGroupsWebDTO.groupNumberText" maxlength="200"  cssStyle="width:80px"/><i><fmt:message key="subGroups.maxCodeLength"/></i>                   
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>subGroupsWebDTO.code</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>         
                </tr>                
                <tr>
                     <td scope="row" class="label">
                     <label for="fileName">
                            <fmt:message key="subGroups.description"/><span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                        <s:textarea name="subGroupsWebDTO.description" cssStyle="width:606px" rows="4" />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>subGroupsWebDTO.description</s:param>
                               </s:fielderror>                            
                             </span>
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
