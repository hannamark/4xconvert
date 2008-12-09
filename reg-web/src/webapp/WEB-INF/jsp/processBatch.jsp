<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="process.batch.page.title"/></title>   
    <s:head/>
</head>
<SCRIPT LANGUAGE="JavaScript">
function handleAction(){
	document.forms[0].page.value = "Submit";
	document.forms[0].action="batchUploadprocess.action";
    document.forms[0].submit();
}
</SCRIPT>
	
<body>
<!-- main content begins-->
    <h1><fmt:message key="process.batch.page.header"/></h1>
    <div class="box" id="filters">
    <reg-web:failureMessage/>
    <s:form name="batchUpload" method="POST" enctype="multipart/form-data"><s:actionerror/>
        <input type="hidden" name="page" />
        <p>Add Trial into NCI Clinical Trials Reporting Portal  by submitting this form. Please note: asterisks (<span class="required">*</span>) indicate required fields. </p>
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="process.batch.details"/></th>
          </tr>
          <tr><td colspan="2" class="space">&nbsp;</td></tr>
          <tr>
                <td scope="row" class="label">
                    <label for="OrgName"> <fmt:message key="process.batch.orgName"/><span class="required">*</span></label>
                </td>
                <td>
                    <s:textfield name="orgName"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>orgName</s:param>
                       </s:fielderror>                            
                     </span>
                </td>                
          </tr>
           <tr>
              <td scope="row" class="label">
              <label for="trialData">
                     <fmt:message key="process.batch.trialData"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
                 <s:file name="trialData" value="true" accept="application/vnd.ms-excel" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>trialDataFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr>
         <tr>
              <td scope="row" class="label">
              <label for="docZip">
                     <fmt:message key="process.batch.docZip"/>
                     <span class="required">*</span>
              </label>
             </td>
             <td class="value">
                 <s:file name="docZip" accept="application/x-zip-compressed" cssStyle="width:270px"/>
                 <span class="formErrorMsg"> 
                    <s:fielderror>
                    <s:param>docZipFileName</s:param>
                   </s:fielderror>                            
                 </span>
               </td>         
         </tr> 
           </table>
            <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Upload Trial</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>  
           </s:form>

 </div> 
</body>
</html>