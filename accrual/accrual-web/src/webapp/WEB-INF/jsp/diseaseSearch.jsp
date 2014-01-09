<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<c:set var="topic" scope="request" value="diseasesearch"/>
<head>
    <title><fmt:message key="disease.search"/></title>   
    <s:head/>    
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery-1.7.1.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
<SCRIPT LANGUAGE="JavaScript">

jQuery(function(){
    jQuery(window).keypress(function(e) {
        if(e.keyCode == 13) {                 
            loadDiv();
        }
      });           
});

function loadDiv() {     
    var url = '/accrual/protected/popupdisplayList.action';
    var params = { searchName: $("searchName").value,
            searchCode: $("searchCode").value,
            page: "searchMenu",
            searchCodeSystem: $("searchCodeSystem").value
            };
    var div = $('getDiseases');
    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
    var aj = callAjaxPost(div, url, params);   
}

</SCRIPT>
</head>
<body>

<a href="#" class="helpbutton" onclick="Help.popHelp('<c:out value="${requestScope.topic}"/>');">Help</a>
<h1><fmt:message key="disease.search"/></h1>
  <div class="box">
            <s:form id="diseases" name="diseases" >
                <table class="form">
                    <tr>
                        <td scope="row" class="label"><label for="searchName"><fmt:message key="disease.name"/></label></td>
                        <td><s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px" /></td>
                        <td scope="row" class="label"><label for="searchCode"><fmt:message key="disease.code"/></label></td>
                        <td><s:textfield id="searchCode" name="searchCode" maxlength="60" size="60" cssStyle="width:200px" /></td>
                    </tr>
                    <tr> 
                        <td scope="row" class="label"><label for="searchCodeSystem"><fmt:message key="disease.codeSystem"/></label></td>
                        <td>
                            <s:select id ="searchCodeSystem" name="searchCodeSystem" headerValue="" headerKey=""
                                      list="listOfDiseaseCodeSystems"/>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>  
                            </li>
                        </ul>
                    </del>
                </div>
                <div class="line"></div>
                <div id="getDiseases" align="center">   
                    <jsp:include page="/WEB-INF/jsp/nodecorate/lookupdiseasesdisplayList.jsp"/>
                </div>
            </s:form>
        </div>
    </body>
</html>