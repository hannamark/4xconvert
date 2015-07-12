<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<link href="<c:url value='/styles/style.css?1'/>" rel="stylesheet"
	type="text/css" media="all" />
<link
	href="<c:url value='/scripts/js/jquery-ui-1.11.2.custom/jquery-ui.css'/>"
	rel="stylesheet" media="all" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/scripts/js/DataTables-1.10.4/media/css/jquery.dataTables.min.css'/>">

<style type="text/css">

.error {
	color: red;
}

.warning {
	color: blue;
}

div.error_msg {
    white-space: pre-wrap;
}

span.warning {
    white-space: pre-wrap;
}
span.error {
    white-space: pre-wrap;
}

</style>

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

<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript">
 
            function closePCpopup() {
                if($F('changeMadeFlag')=='true') {
                	window.top.addNewProgramCode($F('orgFamProgramCodeDto.programName'), $F('orgFamProgramCodeDto.programCode'));
                }
                window.top.hidePopWin(true); 
            }

            function createProgramCode() {
            	document.forms['orgFamilyProgramCodeCreate'].action='orgFamilyProgramCodepopupcreate.action';
            	document.forms['orgFamilyProgramCodeCreate'].submit();
            }
        </script>
</head>
<body>
	<div class="box">
		<s:form id="orgFamilyProgramCodeCreate" action="orgFamilyProgramCodepopup*">
		    <s:token name="struts.token.popup"/>
            <s:hidden name="studySiteId" id="studySiteId"></s:hidden>
			<s:hidden name="poOrgFamilyId" id="poOrgFamilyId"></s:hidden>
			<%-- <s:hidden name="poOrgFamilyName" id="poOrgFamilyName"></s:hidden> --%>
			<s:hidden name="changeMadeFlag" id="changeMadeFlag"></s:hidden>
			<h2>
				<fmt:message key="orgFamilyProgramCode.create.title" />
			</h2>
			<br />
			<pa:sucessMessage />
			<pa:failureMessage />

			<div id="add-prgcd-dialog">
				
				<table class="form">
                    <tr>
                        <td scope="row" class="label">
						<label for="poOrgFamilyName"><fmt:message key="orgFamilyProgramCode.create.family" /></label><span class="required">*</span>
					   </td>
						<td>
							<s:textfield name="poOrgFamilyName" id="poOrgFamilyName" readonly="true" style="width:200px;"/>
						</td>
					</tr>
					<tr>
                        <td scope="row" class="label">
                        <label for="orgFamProgramCodeDto.programName"><fmt:message key="orgFamilyProgramCode.create.programName" /></label><span class="required">*</span>
                    </td>
                        <td>
                        <s:textfield name="orgFamProgramCodeDto.programName" id="orgFamProgramCodeDto.programName" style="width:200px;"></s:textfield>
                        <span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>orgFamProgramCodeDto.programName</s:param>
                              </s:fielderror>                            
                        </span>
                    </td>
                    </tr>
					<tr>
                        <td scope="row" class="label">
						<label for="orgFamProgramCodeDto.programCode"><fmt:message key="orgFamilyProgramCode.create.programcode" /></label><span class="required">*</span>
					</td>
                        <td>
						<s:textfield name="orgFamProgramCodeDto.programCode" id="orgFamProgramCodeDto.programCode" style="width:200px;"></s:textfield>
						<span class="formErrorMsg"> 
                              <s:fielderror>
                              <s:param>orgFamProgramCodeDto.programCode</s:param>
                              </s:fielderror>                            
                        </span>
					</td>
                    </tr>
                    </table>
			</div>

		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
					<li><s:a href="javascript:void(0)" cssClass="btn"
							onclick="createProgramCode();">
							<span class="btn_img"><span class="add">Save</span></span>
						</s:a></li>
					<li><s:a href="javascript:void(0)" cssClass="btn"
							onclick="closePCpopup();">
							<span class="btn_img"><span class="cancel">Close</span></span>
						</s:a></li>
				</ul>
			</del>
		</div>
		
        </s:form>
	</div>
</body>
</html>