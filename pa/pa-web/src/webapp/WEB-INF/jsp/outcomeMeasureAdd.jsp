<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="osdesign.outcome.title"/></title>
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
 		document.forms[0].action="interventionalStudyDesignoutcomeupdate.action";
 		document.forms[0].submit();  	
	} else {
 		document.forms[0].action="interventionalStudyDesignoutcomecreate.action";
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

 <h1><fmt:message key="osdesign.outcome.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
  <div class="box">  
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form><s:actionerror/>    
    <h2><fmt:message key="osdesign.outcome.title"/></h2>
    <input type="hidden" name="page" value="${page}" />
    <input type="hidden" name="id" value="${id}" />
    <table class="form">
    			<tr>
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="osdesign.outcome.primary"/> </dfn><span class="required">*</span></label>
					</td>
					<td class="value">
						<s:select  name="webDTO.primaryIndicator" list="#{' ':' ', 'false':'No', 'true':'Yes'}" />
						<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.primaryIndicator</s:param>
                               </s:fielderror>                            
                         </span>
					</td>
				</tr>
				<tr>
                     <td scope="row" class="label">
                     <label for="fileName"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="osdesign.outcome.description"/></dfn><span class="required">*</span>
                     </label>
                    </td>
                    <td class="value">
                        <s:textarea name="webDTO.name" rows="4" cssStyle="width:400px" />
                        <span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.name</s:param>
                               </s:fielderror>                            
                         </span>
                      </td>         
                </tr> 
                <tr>
                     <td scope="row" class="label">
                     <label for="typeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
                            <fmt:message key="osdesign.outcome.timeFrame"/></dfn><span class="required">*</span>
                     </label>
                    </td>
    				<td class="value">
							<s:textarea name="webDTO.timeFrame" rows="4" cssStyle="width:400px" />
                           	<span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.timeFrame</s:param>
                               </s:fielderror>                            
                            </span> 
                      </td>         
                </tr>                      
				<tr>
					<td scope="row"  class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
						<fmt:message key="osdesign.outcome.safety"/> </dfn><span class="required">*</span></label>
					</td>
					<td class="value">
						<s:select name="webDTO.safetyIndicator" list="#{' ':' ', 'false':'No', 'true':'Yes'}" /><span class="formErrorMsg"> 
                                <s:fielderror>
                                <s:param>webDTO.safetyIndicator</s:param>
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
