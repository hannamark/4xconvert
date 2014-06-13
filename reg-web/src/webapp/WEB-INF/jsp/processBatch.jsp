<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<SCRIPT LANGUAGE="JavaScript">
function handleBatchUploadAction(){
	var form = document.batchUploadForm;
	form.page.value = "Submit";
	form.action="/registry/admin/batchUploadprocess.action";
    form.submit();
}
</SCRIPT>
<div class="modal fade" id="batchUpload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Batch Trial Upload</h4>
      </div>
      <div class="modal-body">
      	<c:set var="topic" scope="request" value="batchupload"/>
		 <s:actionmessage cssClass="alert alert-info"/>
         <s:if test="hasActionErrors()">
              <div class="alert alert-danger">
                  <s:actionerror/>
              </div>
          </s:if>
        <p> Register multiple trials in the NCI Clinical Trials Reporting Program by uploading the Trial Data file and the Zip file that contains trial documents.
          Note the following requirements:</p>
        <ol>
          <li>Use this form to submit complete trials only</li>
          <li>The trial data file must conform to the specifications in the CTRP Registry Complete Batch Upload Template, available on the <a href="https://wiki.nci.nih.gov/display/CTRP/CTRP+Trial+Registration+Batch+File+Templates" target="_blank">CTRP Trial Registration Batch File Templates</a> page</li>
          <li>You must submit a Zip file that contains trial documents for each new and amended trial</li>
          <li>You do not have to submit a Zip file that contains trial documents for updates to registered trials</li>
        </ol>
        <form id="batchUploadForm" name="batchUploadForm" role="form" class="form-horizontal" method="POST" enctype="multipart/form-data">
        	<s:token/>
        <s:actionerror/>
        <s:hidden name="page" />
          <div class="form-group">
            <label for="OrgName" class="col-xs-4 control-label" for=""> <fmt:message key="process.batch.orgName"/><span class="required">*</span></label>
            <div class="col-xs-7">
              <s:textfield name="orgName"  maxlength="200"  cssStyle="form-control" />
                    <span class="alert-danger"> 
                        <s:fielderror>
                        <s:param>orgName</s:param>
                       </s:fielderror>                            
                     </span>
            </div>
          </div>
          <div class="form-group">
            <label for="trialData" class="col-xs-4 control-label"><fmt:message key="process.batch.trialData"/><span class="required">*</span></label>
            <div class="col-xs-8">
              <s:file id="trialData" name="trialData" value="true" />
                 <span class="alert-danger"> 
                    <s:fielderror>
                    <s:param>trialDataFileName</s:param>
                   </s:fielderror>                            
                 </span>
            </div>
          </div>
          <div class="form-group">
            <label for="docZip" class="col-xs-4 control-label"><fmt:message key="process.batch.docZip"/></label>
            <div class="col-xs-8">
              <s:file id="docZip" name="docZip" />
                 <span class="alert-danger"> 
                    <s:fielderror>
                    <s:param>docZipFileName</s:param>
                   </s:fielderror>                            
                 </span>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-icon btn-primary" data-dismiss="modal" onclick="handleBatchUploadAction();"><i class="fa-upload"></i>Upload Trials</button>
        <button type="button" class="btn btn-icon btn-default" data-dismiss="modal"><i class="fa-times"></i>Close</button>
      </div>
    </div>
  </div>
</div>
	 