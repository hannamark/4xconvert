<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <script type="text/javascript" language="javascript">
             function reset() {
                 $('searchName').value = '';
                 $('searchMeaning').value = '';
                 $('searchDescription').value = '';
                 $('searchPublicId').value = '';
             }

             function loadResults() {     
                 var url= '/pa/protected/popupPlannedMarkerlookup.action';
                 var params = {
                         description: $('searchDescription').value,
                         meaning: $('searchMeaning').value,
                         name: $('searchName').value,
                         publicId: $('searchPublicId').value
                 };
                 var div = $('getCaDSR');
                 div.innerHTML = '<div><img alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, params);
             }

             function loadRequestEmail() {
                 $('searchcaDSR').hide();
                 $('getCaDSR').hide();
                 $('actionsrow').hide();
                 $('line').hide();
                 $('requestEmail').show();
                 var url = '/pa/protected/popupPlannedMarkersetupEmailRequest.action';
                 var div = $('requestEmail');
                 div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, {});
             }
        </script>
    </head>
    <body>
        <s:form id="cadsrLookup" name="cadsrLookup">
            <div class="box" id="searchcaDSR">
                <h2><fmt:message key="plannedMarker.lookup.title"/></h2>
                <table class="form">
                    <tr>
                        <td scope="row" class="label">
                            <label for="searchName"><fmt:message key="plannedMarker.lookup.permissibleValue"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchName" name="searchName" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                        <td scope="row" class="label">
                            <label for="searchMeaning"><fmt:message key="plannedMarker.lookup.meaning"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchMeaning" name="searchMeaning" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row" class="label">
                            <label for="searchDescription"><fmt:message key="plannedMarker.lookup.description"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchDescription" name="searchDescription" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                        <td scope="row" class="label">
                            <label for="searchPublicId"><fmt:message key="plannedMarker.lookup.publicIdExact"/>:</label>
                        </td>
                        <td>
                            <s:textfield id="searchPublicId" name="searchPublicId" maxlength="60" size="60" cssStyle="width:200px"/>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="requestEmail" style="display: none">
                <jsp:include page="/WEB-INF/jsp/plannedMarker/requestEmail.jsp"/>
            </div>
            <div id="actionsrow" class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li>
                            <s:a href="#" cssClass="btn" onclick="loadResults();">
                                <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.lookup.search"/></span></span>
                            </s:a>
                            <s:a href="#" cssClass="btn" onclick="reset();">
                                <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.reset"/></span></span>
                            </s:a>
                            <s:a href="#" cssClass="btn" onclick="loadRequestEmail();">
                                <span class="btn_img"><span class="search"><fmt:message key="plannedMarker.lookup.createRequest"/></span></span>
                            </s:a>
                            <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();">
                                <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.cancel"/></span></span>
                            </s:a>
                        </li>
                    </ul>
                </del>
            </div>
            <div id="line" class="line"></div>
            <div id="getCaDSR" align="center">
                <jsp:include page="/WEB-INF/jsp/plannedMarker/cadsrLookupResults.jsp"/>
            </div>
        </s:form>
    </body>
</html>