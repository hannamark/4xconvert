<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="register.user.myaccount.title"/></title>
        <s:head/>
        <%@include file="nodecorate/accountScripts.jsp" %>
    </head>
    <body>
        <!-- main content begins-->
        <h1><fmt:message key="register.user.myaccount.header"/></h1>
        <c:set var="topic" scope="request" value="myaccount"/>
        <div class="box" id="filters">
            <reg-web:sucessMessage/>
            <p>You may update your account information. Please note: asterisks (<span class="required">*</span>)
            indicate required fields.<br>
            <b><i> Please provide professional contact information only. </i></b>
            </p>
            <s:form name="myAccount" method="POST" >
                <s:token/>
                <s:actionmessage cssClass="confirm_msg"/>
                <s:actionerror/>
                <s:hidden name="registryUserWebDTO.id" />
                <s:hidden name="registryUserWebDTO.csmUserId" />
                <s:hidden name="userWebDTO.username" />
                <s:hidden name="page" />
                <table class="form">
                    <tbody>
	                    <tr>
							<td class="label-noinput" scope="row">
							 	<fmt:message key="register.user.username"/>
							</td>
							<td class="value"><c:out value="${userName}"/></td>
						</tr>
                        <%@include file="nodecorate/accountCommonForm.jsp" %>
                    </tbody>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="login">Submit</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>
