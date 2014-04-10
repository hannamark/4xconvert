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
		<reg-web:failureMessage/>
        <p> Register multiple trials in the NCI Clinical Trials Reporting Program by uploading the Trial Data file and the Zip file that contains trial documents.
          Note the following requirements:</p>
        <ol>
          <li>This form can only be used for submitting complete trials.</li>
          <li>Trial data file attached must conform to the specifications in the CTRP Registry Complete Batch Upload Template that is valid for the current release, found here.</li>
          <li>Zip file containing trial documents is mandatory for a Trial data file consisting of new submissions and amendments.</li>
          <li>Zip file containing trial documents is optional for a Trial data file consisting of only trial updates.</li>
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
	 