<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="siteadministration.page.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    
        <script type="text/javascript" language="javascript">
            function submitForm() {
                var form = document.forms[0];
                form.action="siteAdministrationsearch.action";
                form.submit();
            }
            
            function save() {
                var form = document.forms[0];
                form.action="siteAdministrationsave.action";
                form.submit();
            }
            
            function resetSearch() {
                $("firstName").value="";
                $("lastName").value="";
                $("emailAddress").value="";
                submitForm();
            }
            
            function updateUserOrgType(regUserId) {
                var  url = '/registry/protected/siteAdministrationsetUserOrgType.action';
                var params = {
                    isAdmin: $("chk" + regUserId).checked ? "true" : "false",
                    regUserId: regUserId
                };
                var aj = callAjaxPost(null, url, params);
                return false;
            }
        </script>
    </head>
    <body>
        <!-- main content begins-->
        <c:set var="topic" scope="request" value="siteadmin"/>
        <h1><fmt:message key="siteadministration.page.header"/></h1>
        <div class="box" id="filters">
            <reg-web:failureMessage/>
            <reg-web:sucessMessage/>
            <s:form name="formSiteAdministration" action="siteAdministrationview.action">
                <s:token/>
                <table class="form">
                    <tr>
                        <td scope="row" class="label">
                            <label for="siteAdministration_criteria_firstName"> <fmt:message key="siteadministration.criteria.firstname"/></label>
                        </td>
                        <td>
                            <s:textfield id="firstName" name="criteria.firstName" maxlength="200" size="100"  cssStyle="width:200px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="siteAdministration_criteria_lastName"> <fmt:message key="siteadministration.criteria.lastname"/></label>
                        </td>
                        <td>
                            <s:textfield id="lastName" name="criteria.lastName"   maxlength="200" size="100"  cssStyle="width:200px" />
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="siteAdministration_criteria_email"> <fmt:message key="siteadministration.criteria.email"/></label>
                        </td>
                        <td>
                            <s:textfield id="emailAddress" name="criteria.emailAddress"  maxlength="200" size="100"  cssStyle="width:200px" />
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="submitForm();"><span class="btn_img"><span class="search"><fmt:message key="siteadministration.buttons.search"/></span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="resetSearch();"><span class="btn_img"><span class="cancel"><fmt:message key="siteadministration.buttons.reset"/></span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
                <div class="line"></div>
                <jsp:include page="/WEB-INF/jsp/siteAdministrationResults.jsp"/>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="save();"><span class="btn_img"><span class="save"><fmt:message key="siteadministration.buttons.save"/></span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
