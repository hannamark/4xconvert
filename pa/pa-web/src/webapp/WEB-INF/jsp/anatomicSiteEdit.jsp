<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="anatomicSite.details.title" /></title>
<s:head />
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<c:url value="/protected/popupDis.action" var="lookupUrl" />

<script type="text/javascript">
    // this function is called from body onload in main.jsp (decorator)
    function callOnloadFunctions(){
        setFocusToFirstControl();         
    }
    function anatomicSiteAdd(){
        document.anatomicSiteForm.action="anatomicSiteadd.action";
        document.anatomicSiteForm.submit();
    }   
</script>
</head>
<body>
<h1><fmt:message key="anatomicSite.details.title" /></h1>
<c:set var="topic" scope="request" value="anatomic_site"/>
<jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
<div class="box">
    <pa:sucessMessage /> 
    <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
    <s:form name="anatomicSiteForm">
    <h2>
        <fmt:message key="anatomicSite.add.details.title"/>
    </h2>

    <table class="form">
        <%--  <jsp:include page="/WEB-INF/jsp/trialDetailSummary.jsp"/> --%>
        <tr>
            <td colspan="2">
            <s:set name="anatomicSiteList" value="anatomicSiteList" scope="request"/>
            
            <s:select name="anatomicSite.code" id="anatomicSite_code" list="anatomicSiteList" listKey="code"
                        listValue="code"/>
        </td>
        </tr>
        <tr>
            <td>
            <div class="actionsrow"><del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="#" cssClass="btn" onclick="anatomicSiteAdd();">
                        <span class="btn_img"> <span class="save">Save</span></span>
                    </s:a>
                </li>
            </ul>
            </del></div>
            </td>
        </tr>
    </table>
</s:form>
 </div>
</body>
</html>