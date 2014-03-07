<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
	<title>Participating sites</title>
	<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
	<script type="text/javascript" src="<c:url value="/scripts/js/calendarpopup.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/js/prototype.js"/>"></script>
	<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/js/scriptaculous.js"/>"></script>
</head>
<body>
	<div class="box">
		<s:token />
		<h2>Participating sites</h2>
		<table class="form">
			<tr>
				<td colspan="2"><s:set name="organizationList" value="organizationList" scope="request" /> 
					<display:table name="organizationList" id="row" class="data" pagesize="20" requestURI="participatingSitespopup.action">
							<display:column escapeXml="true" property="nciNumber" titleKey="participatingOrganizations.nciNumber" class="sortable" />
							<display:column escapeXml="true" property="name" titleKey="participatingOrganizations.name" class="sortable" />
	        				<display:column property="investigator" titleKey="participatingOrganizations.investigators"/>
					</display:table>
				</td>
			</tr>
		</table>
	</div>
	<div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li>       
                        <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="close">Close</span></span></s:a>
                    </li> 
                </ul>   
            </del>
    </div>
</body>
</html>