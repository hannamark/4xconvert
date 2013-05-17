<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <s:if test="%{siteLookUp}">
            <fmt:message key="site.title" var="pageTitle" />
        </s:if>
        <s:else>
            <fmt:message key="disease.title" var="pageTitle" />
        </s:else>
        <title>${pageTitle}</title>        
        <s:head/>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/jquery-1.7.1.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
        
	        jQuery(function(){
	            jQuery(window).keypress(function(e) {
	                if(e.keyCode == 13) {                 
	                	loadDiv();
	                }
	              });           
	        });
	        
            function submitform(disid, type) {
                top.window.loadDiv(disid, type, $("siteLookUp").value);
                window.top.hidePopWin(true); 
            }
            
            function loadDiv() {     
                var url = '/accrual/protected/popupdisplayList.action?siteLookUp=' + $("siteLookUp").value;
                var params = { searchName: $("searchName").value,
                		searchCode: $("searchCode").value,
                        page: "searchLookup",
                        searchCodeSystem: $("searchCodeSystem").value
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
                <s:hidden id="siteLookUp" name="siteLookUp"/>
                <h2>
	                <s:if test="%{siteLookUp}">
	                     <fmt:message key="site.search"/>
	                </s:if>
	                <s:else>
	                    <fmt:message key="disease.search"/>
	                </s:else>
                </h2>
                <table class="form">
                    <tr>
                        <td scope="row" class="label"><label for="searchName">
                        <s:if test="%{siteLookUp}">
                            <fmt:message key="site.name"/>
                        </s:if>
                        <s:else>
                            <fmt:message key="disease.name"/>
                        </s:else>
                        </label></td>
                        <td><s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px" /></td>
                        <td scope="row" class="label"><label for="searchCode">
	                        <s:if test="%{siteLookUp}">
	                            <fmt:message key="site.code"/>
	                        </s:if>
	                        <s:else>
	                            <fmt:message key="disease.code"/>
	                        </s:else>
                        </label></td>
                        <td><s:textfield id="searchCode" name="searchCode" maxlength="60" size="60" cssStyle="width:200px" /></td>
                    </tr>
                    <tr> 
                        <td scope="row" class="label"><label for="includeSDC">
	                        <s:if test="%{siteLookUp}">
	                            <fmt:message key="site.codeSystem"/>
	                        </s:if>
	                        <s:else>
	                            <fmt:message key="disease.codeSystem"/>
	                        </s:else>    
                        </label></td>
                        <td>
                            <s:select id ="searchCodeSystem" name="searchCodeSystem" list="listOfDiseaseCodeSystems"/>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                                <s:a href="#" cssClass="btn" onclick="loadDiv()"><span class="btn_img"><span class="search">Search</span></span></s:a>
                                <s:a href="javascript:void(0)" cssClass="btn" onclick="window.top.hidePopWin();"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
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