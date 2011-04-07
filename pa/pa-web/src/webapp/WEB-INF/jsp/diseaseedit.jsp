<!DOCTYPE html PUBLIC   
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="disease.details.title" /></title>
        <s:head />
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/scriptaculous.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
        <c:url value="/protected/popupDis.action" var="lookupUrl" />
        
        <script type="text/javascript" language="javascript" >
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
            }

            function diseaseAdd() {
                document.diseaseForm.action="diseaseadd.action";
                document.diseaseForm.submit();
            }

            function diseaseUpdate() {
                input_box = confirm("Click OK to save changes.  Cancel to Abort.");
                if (input_box == true){
                    document.diseaseForm.action="diseaseupdate.action";
                    document.diseaseForm.submit();
                }
            }

            function lookup() {
                showPopup('${lookupUrl}', '', 'Disease');
            }

            function loadDiv(intid) {
                 var url = '/pa/protected/ajaxptpDiseasedisplay.action';
                 var params = { diseaseId: intid };
                 var div = document.getElementById('loadDetails');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                 var aj = callAjaxPost(div, url, params);
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="disease.details.title" /></h1>
        <c:set var="topic" scope="request" value="abstractdisease"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()"><div class="error_msg"><s:actionerror /></div></s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}"> 
                    <fmt:message key="disease.edit.details.title"/>
                </s:if>
                <s:elseif test="%{currentAction == 'create'}">
                    <fmt:message key="disease.add.details.title"/>
                </s:elseif>
            </h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form name="diseaseForm">
                           <div id="loadDetails">
                                <%@ include file="/WEB-INF/jsp/nodecorate/selectedDiseaseDetails.jsp"%>
                            </div>
                        </s:form>
                        <div class="actionsrow">
                            <del class="btnwrapper">
                                <ul class="btnrow">
                                    <li>
                                        <s:if test="%{currentAction == 'edit'}">
                                            <s:a href="#" cssClass="btn" onclick="diseaseUpdate();">
                                                <span class="btn_img"> <span class="save">Save</span></span>
                                            </s:a>
                                        </s:if> 
                                        <s:else>
                                            <s:a href="#" cssClass="btn" onclick="diseaseAdd();">
                                                <span class="btn_img"> <span class="save">Save</span></span>
                                            </s:a>
                                        </s:else>
                                    </li>
                                </ul>
                            </del>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>