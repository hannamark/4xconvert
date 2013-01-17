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
    <s:form name="batchUpload" method="POST" enctype="multipart/form-data">
        <s:token/>
        <s:actionerror/>
        <s:hidden name="page" />
        <p> Register multiple trials in the NCI Clinical Trials Reporting Program by uploading the Trial Data file and the Zip file that contains trial documents.
         <br>&nbsp;Note the following requirements:
         <br> &nbsp;&nbsp; 1) This form can only be used for submitting complete trials.
         <br> &nbsp;&nbsp; 2) Trial data file attached must conform to the specifications in the CTRP Registry Complete Batch Upload Template that is valid for the current release, found <a href="http://www.cancer.gov/clinicaltrials/conducting/ncictrp/resources" target="_blank">here</a>.
         <br> &nbsp;&nbsp; 3) Zip file containing trail documents is mandatory for a Trail data file consisting of new submissions and amendments.
         <br> &nbsp;&nbsp; 4) Zip file containing trail documents is optional for a Trail data file consisting of only trial updates.
         </p>
        <table class="form"> 
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
                 <s:file name="trialData" value="true"  cssStyle="width:270px"/>
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
              </label>
             </td>
             <td class="value">
                 <s:file name="docZip" cssStyle="width:270px"/>
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
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Upload Trials</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>  
           </s:form>

 </div> 
</body>
</html>