<%@ tag display-name="saveAndCloseBtn"
	description="Renders Save and Close buttons" body-content="scriptless"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="actionsrow">
	<del class="btnwrapper">
		<ul class="btnrow">
			<li><s:a href="javascript:void(0);" cssClass="btn"
					onclick="doSave()" onkeypress="doSave()">
					<span class="btn_img"><span class="save">Save</span></span>
				</s:a> <s:a href="javascript:void(0);" cssClass="btn" onclick="window.top.hidePopWin();"
					onkeypress="window.top.hidePopWin();" id="search_organization_close_btn">
					<span class="btn_img"><span class="close">Close</span> </span>
				</s:a></li>
		</ul>
	</del>
</div>
