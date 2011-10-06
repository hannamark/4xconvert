<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="register.user.myaccount.title"/></title>
        <s:head/>
        <%@ include file="/WEB-INF/jsp/nodecorate/accountScripts.jsp" %>
    </head>
    <body>
        <!-- main content begins-->
        <h1><fmt:message key="register.user.myaccount.header"/></h1>
        <c:set var="topic" scope="request" value="myaccount"/>
        <div class="box" id="filters">
        <reg-web:sucessMessage />
            <p>To register for NCI Clinical Trials Reporting Program, please begin by creating an account. <br>
            Please note: asterisks (<span class="required">*</span>) indicate required fields.<br>
            <b><i> Please provide professional contact information only.</i></b></p>
        <s:form name="myAccount" method="POST" action="registerUsercreateAccount">
        <s:actionerror />
        
        <table class="form">
        <s:hidden name="userWebDTO.username" />
        <tbody>
            <%@ include file="/WEB-INF/jsp/nodecorate/accountCommonForm.jsp" %>
        </tbody>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="document.forms[0].submit()"><span class="btn_img"><span class="login">Submit</span></span></s:a>
                    </li>
                </ul>
            </del>
        </div>
        </s:form>
       </div>
    </body>
</html>
        