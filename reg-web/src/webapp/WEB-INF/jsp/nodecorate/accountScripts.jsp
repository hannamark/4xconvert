
<link href="<%=request.getContextPath()%>/styles/subModalstyle.css" rel="stylesheet" type="text/css" media="all"/>
<link href="<%=request.getContextPath()%>/styles/subModal.css" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

<c:url value="/orgPoplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/registry/ajaxUsersloadAdminUsers.action" var="displayUrl"/>

<script type="text/javascript" language="javascript">
    var orgid;
    var chosenname;
    
    function setorgid(orgIdentifier, oname) {
        orgid = orgIdentifier;
        chosenname = oname.replace(/&apos;/g,"'");
    }
    
    function handleAction(){
        var form = document.forms[0];
        form.page.value = "Submit";
        form.action="registerUserupdateAccount.action";
        form.submit();
    }
    
    function lookupAffiliateOrg() {
       showPopWin('${lookupOrgUrl}', 900, 400, loadAffliatedOrgDiv, 'Select Affiliated Organization');
    }
    
    function loadAffliatedOrgDiv() {
        $('registryUserWebDTO.affiliatedOrganizationId').value = orgid;
        $('registryUserWebDTO.affiliateOrg').value = chosenname;
        var  url = '/registry/ajaxUsersloadAdminUsers.action';
        var params = {
            "registryUserWebDTO.affiliatedOrganizationId": orgid
        };
        var div = $('adminAccessDiv');
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
        var aj = callAjaxPost(div, url, params);
        return false;
    }
    
    function viewAdmin() {
        var orgId = $('registryUserWebDTO.affiliatedOrganizationId').value ;
        showPopWin('${displayUrl}?registryUserWebDTO.affiliatedOrganizationId='+orgId, 900, 400, loadnothing, 'Affiliated Organization Admin');
    }
    
    function loadnothing() {
        return false;
    }
</script>