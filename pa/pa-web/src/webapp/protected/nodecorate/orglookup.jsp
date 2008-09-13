<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<SCRIPT language="JavaScript">
function submitform(orgid)
{
window.top.hidePopWin(true); 
parent.location.href='/pa/protected/displaySelectedOrg.action?orgId='+orgid;
}
function getOrganizationsFromPO(){
try{
document.getElementById("loadimg").style.display="inline";
} catch (err){
}
document.poOrganizations.action='/pa/protected/poOrganizationsAjax.action?orgName='+document.forms[0].orgName.value;
document.poOrganizations.submit();
}
function hideimg(){
document.getElementById("loadimg").style.display="none";
}
</SCRIPT> 
</head>
<body onload="hideimg();">
<!-- form id="poOrganizations" name="poOrganizations" onsubmit="return true;" action="/pa/protected/poOrganizationsAjax.action" method="POST" -->
<form id="poOrganizations" name="poOrganizations" >
<table cellspacing="2" > 
    <tr>
    <td><label for="nciIdentifier"> Search  Organizations</label></td>
    <td><input type="text" name="orgName"></td>
    <td><INPUT TYPE="button" NAME="lookup" value="Search" onclick="getOrganizationsFromPO();"/> </td>
    </tr> 
</table>
<c:choose>
<c:when test="${orgs!=null}">
<display:table class="its" decorator="gov.nih.nci.pa.decorator.PADisplayTagDecorator" sort="list" pagesize="10" uid="row" name="orgs" export="false">
<display:column titleKey="Oranization Name" property="name.part[0].value" sortable="true" headerClass="sortable"/> 

<display:column titleKey="Action" class="action" sortable="false">
<A href="javascript: submitform('${row.identifier.extension}')">Select</A>
</display:column>
</display:table>
</c:when>
<c:otherwise>
<img id="loadimg" src="../images/loading.gif">
</c:otherwise>
</c:choose>
</form>
</body>