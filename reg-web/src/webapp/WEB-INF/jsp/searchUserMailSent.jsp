<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="register.searchUserMailSent.page.title"/></title>
        <s:head/>
        <script type="text/javascript" language="javascript">
            function homeAction() {
                submitForm("${pageContext.request.contextPath}/home.action");
            }
            
            function submitForm(action) {
                var form = document.getElementById("searchUserMailSentForm");
                form.action = action;
                form.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="register.searchUserMailSent.page.header"/></h1>
        <div class="box" id="filters">
            <s:form id="searchUserMailSentForm" name="searchUserMailSentForm">
                <p>
                    <fmt:message key="register.searchUserMailSent.formTitle">
                        <fmt:param><s:property value="emailAddress"/></fmt:param>
                    </fmt:message>
                </p>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="#" cssClass="btn" onclick="homeAction()"><span class="btn_img"><span class="cancel"><fmt:message key="register.searchUserMailSent.button.home"/></span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>