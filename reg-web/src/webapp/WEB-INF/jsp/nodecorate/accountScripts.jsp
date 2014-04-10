<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/registry/ajaxUsersviewAdminUsers.action" var="displayUrl"/>

<script type="text/javascript" language="javascript">
    var orgid;
    var originalorgid = -1;
    var chosenname;
    
    function setorgid(orgIdentifier, oname) {
        orgid = orgIdentifier;
        chosenname = oname.replace(/&apos;/g,"'");
    }
    
    function handleRegisterUserAction(){
        var form = document.myAccountForm;
        form.page.value = "Submit";
        form.action="registerUserupdateAccount.action";
        form.submit();
    }

    function lookupAffiliateOrg() {
        if (originalorgid == -1) {
          originalorgid = $('registryUserWebDTO.affiliatedOrganizationId').value;
        }
        if (originalorgid) {
          var r = confirm("Warning: You will lose any existing Site Admin and Accrual Submission privileges if you change your organizaiton affiliation.");
          if (r == false) {
            return;
          }
        }
        showPopWin('${lookupOrgUrl}', 850, 550, loadAffliatedOrgDiv, 'Select Affiliated Organization');
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
