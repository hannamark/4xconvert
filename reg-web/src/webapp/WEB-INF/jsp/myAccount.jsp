<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@include file="/WEB-INF/jsp/nodecorate/accountScripts.jsp" %>
<div class="modal fade" id="myAccount" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">My Account</h4>
			</div>
			<div class="modal-body">
	            <c:set var="topic" scope="request" value="myaccount"/>
	            <s:form cssClass="form-horizontal" role="form" name="myAccount" method="POST" >
	                <s:token/>
	                <s:actionmessage cssClass="confirm_msg"/>
	                <s:actionerror/>
	                <s:hidden name="registryUserWebDTO.id" />
	                <s:hidden name="registryUserWebDTO.csmUserId" />
	                <s:hidden name="userWebDTO.username" />
	                <s:hidden name="page" />
                     <%@include file="nodecorate/accountCommonForm.jsp" %>
	                <div class="form-group">
			            <label for="notifications" class="col-xs-4 control-label">Color Scheme</label>
			            <div class="col-xs-7">
			              <div class="btn-group" data-toggle="buttons">
			                <label class="btn btn-default active" onClick="setActiveStyleSheet('default'); return false;">
			                  <input type="radio" name="options" id="option1">
			                  Light </label>
			                <label class="btn btn-default" onClick="setActiveStyleSheet('alternate 1'); return false;">
			                  <input type="radio" name="options" id="option3">
			                  Dark </label>
			              </div>
			              <i class="fa-question-circle help-text inside" id="popover" rel="popover" data-content="Select a color scheme to change the colors on all pages of the application" data-placement="top" data-trigger="hover"></i> </div>
			          </div>
	                <div class="modal-footer">
				        <button type="button" class="btn btn-icon btn-primary" data-dismiss="modal" onClick="handleAction()"><i class="fa-floppy-o"></i>Save</button>
				        <button type="button" class="btn btn-icon btn-default" data-dismiss="modal"><i class="fa-times-circle"></i>Cancel</button>
				      </div>
	            </s:form>
	        </div>
	    </div>
	</div>
</div>
