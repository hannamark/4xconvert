<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manage.accrual.access.page.title" /></title>

<s:head />
<script>

    var studiesToAssign = new Array();
    var studiesToAssignRowsSelected = new Array();
    
    var studiesToUnassign = new Array();
    var studiesToUnassignRowsSelected = new Array();    
    
    function change(el) {
        if(el.value) {
            if(el.id=='userId'){
                el.form.ofUserId.value = null;
            }
            if(el.id=='ofUserId'){
                el.form.userId.value = null;
            }
        }
        el.form.action='manageAccrualAccesschange.action';
        displayWaitPanel();
        el.form.submit();
    }
    
    function assignAll(el) {
    	el.form.action='manageAccrualAccessassignAll.action';
    	showCommentsBox();
    }
    
    function unassignAll(el) {
        el.form.action='manageAccrualAccessunassignAll.action';
        showCommentsBox();
    }
    
    
    function assignSelected(el) {
    	if (studiesToAssign.length == 0) {
    	    alert('<fmt:message key="manage.accrual.access.plzSelectToAssign"/>');    	    
    	} else {
    		var studiesStr = '';
    		studiesToAssign.each(function (s) {
    			studiesStr = studiesStr + s + ';';
    		});
    		el.form.trialsToAssign.value = studiesStr;
    	    el.form.action = 'manageAccrualAccessassignSelected.action';
    	    showCommentsBox();
    	}
    }
    
    function unassignSelected(el) {
        if (studiesToUnassign.length == 0) {
            alert('<fmt:message key="manage.accrual.access.plzSelectToUnAssign"/>');          
        } else {
            var studiesStr = '';
            studiesToUnassign.each(function (s) {
                studiesStr = studiesStr + s + ';';
            });
            el.form.trialsToUnassign.value = studiesStr;
            el.form.action = 'manageAccrualAccessunassignSelected.action';
            showCommentsBox();
        }
    }
    
    function unassignableTrialClicked(row, studyProtocolId, evt) {
    	alert('<fmt:message key="manage.accrual.access.unselectableTrialError"/>');
    	return stopEventPropagation(evt);
    }
    
    function showCommentsBox() {    	
    	// retrieve required dimensions 
        var eltDims     = $('comments-dialog').getDimensions();
        var browserDims = $(document).viewport.getDimensions();
         
        // calculate the center of the page using the browser and element dimensions
        var y  = (browserDims.height - eltDims.height) / 2;
        var x = (browserDims.width - eltDims.width) / 2;    
        
        $('comments-dialog').absolutize(); 
        $('comments-dialog').style.left = x + 'px';
        $('comments-dialog').style.top = y + 'px';
        $('comments-dialog').show();
    }    
    
    function unassignedTrialClicked(row, studyProtocolId, evt) {
    	if (studiesToAssign.indexOf(studyProtocolId)==-1) {
    		if (!isCTRL(evt)) {
    			studiesToAssign = new Array();
    			clearRowSelections(studiesToAssignRowsSelected);
    		}
    		row.className='accrual_trial_selected';
    		studiesToAssign.push(studyProtocolId);
    		studiesToAssignRowsSelected.push(row);
    	} else {    		
            if (isCTRL(evt)) {
            	studiesToAssign = studiesToAssign.without(studyProtocolId);
                row.className='accrual_trial_unselected';
                studiesToAssignRowsSelected = studiesToAssignRowsSelected.without(row);
            } else {
                studiesToAssign = new Array();
                clearRowSelections(studiesToAssignRowsSelected);
                row.className='accrual_trial_selected';
                studiesToAssign.push(studyProtocolId);
                studiesToAssignRowsSelected.push(row);            	
            }
    	}    	
         
        return stopEventPropagation(evt);
    }
    
    function assignedTrialClicked(row, studyProtocolId, evt) {
        if (studiesToUnassign.indexOf(studyProtocolId)==-1) {
            if (!isCTRL(evt)) {
            	studiesToUnassign = new Array();
                clearRowSelections(studiesToUnassignRowsSelected);
            }
            row.className='accrual_trial_selected';
            studiesToUnassign.push(studyProtocolId);
            studiesToUnassignRowsSelected.push(row);
        } else {            
            if (isCTRL(evt)) {
            	studiesToUnassign = studiesToUnassign.without(studyProtocolId);
                row.className='accrual_trial_unselected';
                studiesToUnassignRowsSelected = studiesToUnassignRowsSelected.without(row);
            } else {
            	studiesToUnassign = new Array();
                clearRowSelections(studiesToUnassignRowsSelected);
                row.className='accrual_trial_selected';
                studiesToUnassign.push(studyProtocolId);
                studiesToUnassignRowsSelected.push(row);              
            }
        }       
         
        return stopEventPropagation(evt);
    }
    
    function isCTRL(evt) {
    	return evt.ctrlKey==true || evt.metaKey==true;
    }
    
    function stopEventPropagation(evt) {
    	 Event.extend(evt);
         evt.stop();
         evt.cancelBubble = true;
         
         if (document.selection) {
               document.selection.empty();
         }        
         return false;    	
    }
    
    function clearRowSelections(rows) {
    	rows.each(function (row) {
    		row.className='accrual_trial_unselected';
    	});
    	rows.length = 0;
    }
    
    function assignUnassignSASubmitter(el) {
        displayWaitPanel();
        el.form.action='manageAccrualAccessassignUnAssignSASubmitter.action';
        el.form.submit();
    }

    function assignUnassignOFSubmitter(el) {
        displayWaitPanel();
        el.form.action='manageAccrualAccessassignUnAssignOFSubmitter.action';
        el.form.submit();
    }

</script>
</head>
<body>
	<!-- main content begins-->
	<c:set var="topic" scope="request" value="accrualaccess" />
	<h1 class="heading"><span><fmt:message key="manage.accrual.access.page.header" /></span></h1>

	<div class="box" id="filters">

		<reg-web:failureMessage />
		<reg-web:sucessMessage />

		<s:form name="manageAccrualAccess" action="manageAccrualAccess.action" cssClass="form-horizontal" role="form">
		    <s:token/>
		    <s:hidden name="trialsToAssign" value=""/>
		    <s:hidden name="trialsToUnassign" value=""/>
            <s:hidden name="families"/>
            <s:hidden name="organization.name"/>
			
		    <p>Affiliated Site: <strong><c:out value="${organization.name}"/></strong></p>
			<p>Member of Family(s): <strong><c:out value="${families}"/></strong></p>
			 <div class="form-group">   
			    <label for="trialCategory" class="col-xs-3 control-label"><fmt:message
								key="manage.accrual.access.selectTrialCat" /></label>
				<div class="col-xs-4">
					<s:select name="trialCategory" id="trialCategory" cssClass="form-control"
                                onchange="change(this);"
                                list="trialCategoryList" 
                                    listKey="name" listValue="code"
                                 value="model.trialCategory.name" />
				</div>
				<i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message
                            key="manage.accrual.access.selectTrial.help"/>' data-placement="top" data-trigger="hover"></i></div>
			<div class="form-group">
          		<label for="userId" class="col-xs-3 control-label"> <fmt:message
                            key="manage.accrual.access.selectUser" /></label>
                <div class="col-xs-4">
                   <s:select name="userId" id="userId" cssClass="form-control"
                            onchange="change(this);"
                            list="model.users" 
                            listKey="id"  
                            listValue="%{siteAccrualSubmitter == true ? lastName + ', ' +  firstName + '(site submitter)' : lastName + ', ' +  firstName}"                                      
                            headerKey="" headerValue=" " value="model.user.id" />
				</div>
				<i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message
                            key="manage.accrual.access.selectUser.help"/>' data-placement="top" data-trigger="hover"></i>
                <button type="button" class="btn btn-icon btn-primary" value="Assign/Unassign Site Accrual Submitter" onclick="assignUnassignSASubmitter(this);" ><i class="fa-save"></i>Assign/Unassign Site Accrual Submitter</button>
             </div>
             <div class="form-group">
                 <label for="ofUserId" class="col-xs-3 control-label"><fmt:message key="manage.accrual.access.family.selectUser" /></label>
                 <div class="col-xs-4">                       
                    <s:select name="ofUserId" id="ofUserId" cssClass="form-control"
                             onchange="change(this);"
                             list="model.ofUsers" 
                             listKey="id"  
                             listValue="%{familyAccrualSubmitter == true ? lastName + ', ' +  firstName + '(org family submitter)' : lastName + ', ' +  firstName}"                                      
                             headerKey="" headerValue=" " value="ofUserId" />
                 </div>
                 <i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message key="manage.accrual.access.selectUser.help"/>' data-placement="top" data-trigger="hover"></i>
                    <button type="button" class="btn btn-icon btn-primary" value="Assign/Unassign Org Family Accrual Submitter" onclick="assignUnassignOFSubmitter(this);"><i class="fa-save"></i>Assign/Unassign Org Family Accrual Submitter</button> 
            </div>
                
                <c:if test="${model.user != null}">	
                <c:if test="${!(not empty model.assignedTrials || not empty model.unassignedTrials)}"> 
                    <tr>
                        <td colspan="2" class="info" align="center"> <fmt:message key="manage.accrual.access.noTrials"/> </td>
                    </tr>                
                </c:if>
                <c:if test="${(not empty model.assignedTrials || not empty model.unassignedTrials)}">  
                <table class="accrualAccess">
                <tbody>  
                <tr>
                    <td colspan="2">
                        <table width="100%">
                            <tr>
                                <td width="45%" nowrap="nowrap" align="center"><h3 class="notAssigned"><fmt:message key="manage.accrual.access.notAssigned"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message key="manage.accrual.access.selectHelp"/>' data-placement="top" data-trigger="hover"></i></h3></td>
                                <td></td>
                                <td width="45%" nowrap="nowrap" align="center"><h3 class="assigned"><fmt:message key="manage.accrual.access.assigned"/><i class="fa-question-circle help-text" id="popover" rel="popover" data-content='<fmt:message key="manage.accrual.access.selectHelp"/>' data-placement="top" data-trigger="hover"></i></h3></td>
                            </tr>                        
                            <tr>
                                <td>
                                    <div class="accrual_trial_list_container">
	                                    <table width="100%">
		                                    <c:forEach var="entry" items="${model.unassignedTrials}">
		                                       <tr>
		                                           <td colspan="3" align="left" class="accrual_trial_cat_title" nowrap="nowrap">
		                                               <c:out value="${entry.key}"/>
		                                           </td>
		                                       </tr>	                                       
		                                       <c:forEach var="trial" items="${entry.value}">
	                                                <tr class="${trial.selectable?'accrual_trial_unselected':'accrual_trial_unselectable'}"
	                                                       onclick="${trial.selectable?'unassignedTrialClicked':'unassignableTrialClicked'}(this, ${trial.protocolDTO.studyProtocolId}, event);">
	                                                    <td align="left" class="accrual_trial_id" nowrap="nowrap" width="1%">	                                       
		                                                   <c:out value="${trial.protocolDTO.nciIdentifier}"/>
		                                                </td>
		                                                <td width="1%">
		                                                &nbsp;&nbsp;
		                                                </td>
		                                                <td align="left">
		                                                   <span title="<c:out value="${trial.protocolDTO.officialTitle}" escapeXml="true"/>"><c:out value="${func:abbreviate(trial.protocolDTO.officialTitle, 60)}"/></span>
		                                                </td>
		                                            </tr>
		                                       </c:forEach> 
		                                       <tr>
		                                           <td></td>
		                                       </tr>                     
		                                    </c:forEach>
	                                    </table>
                                    </div>
                                </td>
                                <td class="accrual_btn_column" valign="middle" align="center" nowrap="nowrap">
                                    <div class="accrual_btn_column">
                                        <c:if test="${model.hasAssignableTrials}">
	                                        <div>
	                                            <b><fmt:message key="manage.accrual.access.assign"/></b>
	                                        </div>
	                                        <div>
	                							<button type="button" value="&gt;"
	                							    onclick="assignSelected(this);" onkeypress="assignSelected(this);"
	                							    class="btn btn-light" data-placement="left" rel="tooltip" data-original-title="Assign selected"><i class="fa-angle-right"></i></button>                                      
	                                        </div>
	                                        <div>
	                                            <button type="button"
	                                               onclick="assignAll(this);" onkeypress="assignAll(this);"
	                                               class="btn btn-light" data-placement="left" rel="tooltip" data-original-title="Assign all"><i class="fa-angle-double-right"></i></button>                                      
	                                        </div>  
	                                        <br/><br/>
                                        </c:if>
                                        <c:if test="${not empty model.assignedTrials}">                                        
	                                        <div>
	                                            <b><fmt:message key="manage.accrual.access.unassign"/></b>
	                                        </div>
	                                        <div>
	                                            <button type="button" 
	                                               onclick="unassignSelected(this);" onkeypress="unassignSelected(this);"
	                                               class="btn btn-default" data-placement="right" rel="tooltip" data-original-title="Unassign selected"><i class="fa-angle-left"></i></button>                                    
	                                        </div>
	                                        <div>
	                                            <button type="button"
	                                               onclick="unassignAll(this);" onkeypress="unassignAll(this);"
	                                               class="btn btn-default" data-placement="right" rel="tooltip" data-original-title="Unassign all"><i class="fa-angle-double-left"></i></button>                                      
	                                        </div>                         
                                        </c:if>        
                                    </div>
                                </td>
                                <td>
                                    <div class="accrual_trial_list_container">
                                        <table width="100%">
                                            <c:forEach var="entry" items="${model.assignedTrials}">
                                               <tr>
                                                   <td colspan="3" align="left" class="accrual_trial_cat_title" nowrap="nowrap">
                                                       <c:out value="${entry.key}"/>
                                                   </td>
                                               </tr>                                           
                                               <c:forEach var="trial" items="${entry.value}">
                                                    <tr class="accrual_trial_unselected" 
                                                        onclick="assignedTrialClicked(this, ${trial.studyProtocolId}, event);">
                                                        <td align="left" class="accrual_trial_id" nowrap="nowrap" width="1%">                                          
                                                           <c:out value="${trial.nciIdentifier}"/>
                                                        </td>
                                                        <td width="1%">
                                                        &nbsp;&nbsp;
                                                        </td>
                                                        <td align="left">
                                                            <span title="<c:out value="${trial.officialTitle}" escapeXml="true"/>">
                                                                <c:out value="${func:abbreviate(trial.officialTitle, 60)}"/>
                                                            </span>
                                                        </td>
                                                    </tr>
                                               </c:forEach> 
                                               <tr>
                                                   <td></td>
                                               </tr>                     
                                            </c:forEach>
                                        </table>
                                    </div>
                                </td>                                
                            </tr>
                        </table>
                    </td>
                </tr>			
                </c:if>
                </c:if>
			</table>
	        <div id="comments-dialog" style="display: none">
	        	<div class="modal-dialog">
	    			<div class="modal-content">
		      			<div class="modal-header">
			      			<h4 class="modal-title" id="myModalLabel"><fmt:message key="manage.accrual.access.comments"/></h4>
			      		</div>
			           <div class="modal-body">
			           	   <div>
				           <label for="comments">Please provide an <i>optional</i> comment for this action and then click either
				           Save to execute or Cancel to abort:</label>
				           <s:textarea id="comments" name="comments" value="" rows="8" cssClass="form-control"/>
				           </div>
				           <br/>
				           <div align="center">
				               <button type="button" class="btn btn-icon btn-default" data-dismiss="modal"value="Save" 
				                   onclick="$('comments-dialog').hide();displayWaitPanel();this.form.submit();"><i class="fa-save"></i>Save</button>
				                  <button type="button" class="btn btn-icon btn-primary" data-dismiss="modal" value="Cancel"
				                   onclick="$('comments-dialog').hide();"><i class="fa-times-circle"></i>Cancel</button>
				           </div>
			           </div>
		           </div>
	           </div>
	        </div>
		</s:form>
	</div>
</body>
</html>
