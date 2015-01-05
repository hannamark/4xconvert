<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <link href="<c:url value='/scripts/js/jquery-ui-1.11.2.custom/jquery-ui.css'/>" rel="stylesheet" media="all" type="text/css" />
        <link rel="stylesheet" type="text/css"
            href="<c:url value='/scripts/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css'/>">
        
		<script type="text/javascript"
		    src="<c:url value='/scripts/js/jquery-1.11.1.min.js'/>"></script>
		<script type="text/javascript"
		    src="<c:url value='/scripts/js/jquery-ui-1.11.2.custom/jquery-ui.min.js'/>"></script>
		<!-- DataTables -->
		<script type="text/javascript" charset="utf8"
		    src="<c:url value='/scripts/js/DataTables-1.10.4/media/js/jquery.dataTables.min.js'/>"></script>  
		    
		<script type="text/javascript" language="javascript">
		  jQuery.noConflict();
		</script>
		          
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript">
            function closepopup() {
                window.top.hidePopWin(true); 
            }
            
            function deleteStatus(statusId) {
            	if (confirm('<fmt:message key="studyOverallStatus.delete.confirm" />')) {
            		$('statusId').value = statusId;
            		document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupdelete.action';
            		document.forms['studyOverallStatus'].submit();
            	}
            }
            
            function undoStatus(statusId) {
                if (confirm('<fmt:message key="studyOverallStatus.undo.confirm" />')) {
                    $('statusId').value = statusId;
                    document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupundo.action';
                    document.forms['studyOverallStatus'].submit();
                }
            }
            
            function editStatus(statusId, statusCode, statusDate, reason) {
            	document.forms['studyOverallStatus'].action='studyOverallStatusHistorypopupsave.action';
            	$('statusId').value = statusId;
            	$('statusCode').setValue(statusCode);
            	$('statusDate').setValue(statusDate);
            	$('reason').setValue(reason);
            	showEditBox();
            }     
            
            
            function showEditBox() {       
                // retrieve required dimensions 
                var eltDims     = $('edit-dialog').getDimensions();
                var browserDims = $(document).viewport.getDimensions();
                 
                // calculate the center of the page using the browser and element dimensions
                var y  = (browserDims.height - eltDims.height) / 2;
                var x = (browserDims.width - eltDims.width) / 2;    
                
                $('edit-dialog').absolutize(); 
                $('edit-dialog').style.left = x + 'px';
                $('edit-dialog').style.top = y + 'px';
                $('edit-dialog').show();
            }  
            
            function closepopupAndRefreshParentIfNeeded() {
            	closepopup();
            	if ($F('changesMadeFlag') == 'true') {
            		var parentForm = window.parent.document.forms['studyoverallstatus'];
            		if (parentForm != null) {
            			parentForm.action = 'studyOverallStatus.action';
            			parentForm.submit();
            		}
            	}
            }
            
            
        </script>
    </head> 
    <body>
        <s:form action="studyOverallStatus">
            <div class="box">            
                <s:hidden name="statusId" value="" id="statusId"></s:hidden>
                <s:hidden name="changesMadeFlag" id="changesMadeFlag"></s:hidden>
                <h2><fmt:message key="statusHistory.title" /></h2>
                <br/>                
                <pa:sucessMessage/>
                <pa:failureMessage/>
                <table class="form">
                    <tr>
                        <td>
                            <s:set name="overallStatusList" value="overallStatusList" scope="request"/>
                            <display:table class="data" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" uid="row"
                                    name="overallStatusList" export="false">
                                <display:column escapeXml="true" property="statusDate" title="Status Date" sortable="false"/>
                                <display:column escapeXml="true" property="statusCode" title="Status" sortable="false"/>                                
                                <display:column escapeXml="true" property="comments" title="Comments" sortable="false"/>                                
                                <display:column titleKey="studyOverallStatus.actions" sortable="false">                                
                                    <s:if test="%{#attr.row.editable}">
                                       <a href="javascript:void(0);" onclick="editStatus(<s:property value="%{#attr.row.id}"/>,
                                           '<s:property value="%{#attr.row.statusCode}"/>', '<s:property value="%{#attr.row.statusDate}"/>',
                                               '<s:property value="%{@org.apache.commons.lang.StringEscapeUtils@escapeJavaScript(#attr.row.reason)}"/>');">  
	                                       <img 
		                                        src='<c:url value="/images/ico_edit.gif"/>' alt="<fmt:message key="studyOverallStatus.edit" />"
		                                        title="<fmt:message key="studyOverallStatus.edit" />" 
		                                        width="16" height="16"/></a>
	                                   
                                    </s:if>                                                                  
	                                <s:if test="%{#attr.row.deletable}">
	                                        <a href="javascript:void(0);" onclick="deleteStatus(<s:property value="%{#attr.row.id}"/>);">                                        
		                                    <img 
		                                        src='<c:url value="/images/ico_delete.gif"/>' alt="<fmt:message key="studyOverallStatus.delete" />"
		                                        title="<fmt:message key="studyOverallStatus.delete" />" 
		                                        width="16" height="16"/></a>
	                                </s:if>	                                
	                                <s:if test="%{#attr.row.undoable}">   
	                                    <a href="javascript:void(0);" onclick="undoStatus(<s:property value="%{#attr.row.id}"/>);"> 
	                                    <img 
	                                        src='<c:url value="/images/ico_back.gif"/>' alt="<fmt:message key="studyOverallStatus.undo" />"
	                                        title="<fmt:message key="studyOverallStatus.undo" />" 
	                                        width="16" height="16"/></a>
                                    </s:if>                                    
                                </display:column>                              
                            </display:table>
                        </td>
                    </tr>
                </table>            
        </div>
        <div id="edit-dialog" style="display:none;">
           <div class="header"><fmt:message key="studyOverallStatus.edit.header"/></div>
           <div class="body">
	           <div class="label"><fmt:message key="studyOverallStatus.edit.status"/></div>
	           <div>
	               <s:select name="statusCode" id="statusCode"
                        list="@gov.nih.nci.pa.enums.StudyStatusCode@values()" listKey="code"
                        listValue="code" value=""/>               
	           </div>
               <div class="label"><fmt:message key="studyOverallStatus.edit.date"/></div>
               <div>
                   <s:textfield name="statusDate" id="statusDate" maxlength="10"></s:textfield>               
               </div>
               <div class="label"><fmt:message key="studyOverallStatus.edit.reason"/></div>
               <div>
                   <s:textarea name="reason" id="reason" rows="3"></s:textarea>
               </div>      
	           <div align="center">
	               <input type="button" value="Save" 
	                   onclick="$('edit-dialog').hide();displayWaitPanel();this.form.submit();"/>&nbsp;<input type="button" value="Cancel"
	                   onclick="$('edit-dialog').hide();"/>
	           </div>
           </div>
        </div>        
        </s:form>
        
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="javascript:void(0)" cssClass="btn" onclick="closepopupAndRefreshParentIfNeeded();"><span class="btn_img"><span class="logout">Close</span></span></s:a>  
                    </li>
                </ul>
            </del>
        </div>
        
        <jsp:include page="/WEB-INF/jsp/common/misc.jsp"/>
        
    </body>
</html>