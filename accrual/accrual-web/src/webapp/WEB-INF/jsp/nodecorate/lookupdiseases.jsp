<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
            function submitform(disid, type) {
                top.window.loadDiv(disid, type);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {     
                var url = '/accrual/protected/popupdisplayList.action';
                var params = { searchName: $("searchName").value,
                        includeSDC: $("includeSDC") != null && $("includeSDC").checked == true
                		};
                var div = $('getDiseases');
                div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>'; 
                var aj = callAjaxPost(div, url, params);   
            }
        </script>
    </head> 
    <body>
        <div class="box">
            <s:form id="diseases" name="diseases" >
                <h2>Search Diseases</h2>
                <table class="form">
                    <tr>
                        <td scope="row" class="label"><label for="searchName"><fmt:message key="disease.name"/></label></td>
                        <td><s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px" /></td>
                    </tr>
                    <tr> 
                        <td scope="row" class="label"><label for="includeSDC"><fmt:message key="disease.includeSDC"/></label></td>
                        <td>
                            <s:checkbox name="includeSDC" id="includeSDC" />
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