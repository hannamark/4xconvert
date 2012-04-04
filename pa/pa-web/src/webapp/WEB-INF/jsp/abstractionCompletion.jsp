<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>Abstraction Validation</title>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/showhide.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <s:head />
        <script type="text/javascript" language="javascript">
            function generateReport(pid) {
                document.aForm.target = "CLinical Trial XML Generation";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxCTGovgenerateXML.action?studyProtocolId=" + pid;
                document.aForm.submit();
            }
            
            function generateTSR() {
                document.aForm.target = "TSR";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxAbstractionCompletionviewTSR.action";
                document.aForm.submit();
            }
            
            function generateTSRWord() {
                document.aForm.target = "TSR";
                document.aForm.action = "${pageContext.request.contextPath}/protected/ajaxAbstractionCompletionviewTSRWord.action";
                document.aForm.submit();
            }
    </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="validateabstract"/>
        <h1>Abstraction Validation</h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <c:set var="abstractionSuccess" value="${empty requestScope.failureMessage}"/>
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <c:if test="${abstractionSuccess}">
                <s:form name="aForm">
                    <s:actionerror/>
                    <pa:studyUniqueToken/>
                    <h2>
                        <s:if test="%{abstractionError==true}">
                            Abstraction validation failed. Please check error(s).
                        </s:if>
                        <s:if test="%{abstractionError==false}">
                            Abstraction is valid.
                        </s:if>
                    </h2>
                    <s:if test="abstractionList != null">
                        <s:set name="abstractionList" value="abstractionList" scope="request"/>
                        <display:table name="abstractionList" id="row" class="data" sort="list"  pagesize="30" requestURI="abstractionCompletionquery.action" export="false">
                            <display:column escapeXml="true" title="Type" property="errorType"  sortable="true" headerClass="sortable" />
                            <display:column escapeXml="true" title="Description" property="errorDescription" sortable="true" headerClass="sortable" />
                            <display:column escapeXml="true" title="Comment" property="comment" sortable="true" headerClass="sortable" />
                        </display:table>
                    </s:if>
                    <div class="actionsrow">
                        <del class="btnwrapper">
                            <ul class="btnrow">
                                <s:if test="abstractionError == false">
                                    <c:if test="${!sessionScope.trialSummary.proprietaryTrial}">
                                        <li><a href="javascript:void(0)" class="btn" onclick="generateReport('${sessionScope.trialSummary.studyProtocolId}');"><span class="btn_img"><span class="save">View XML</span></span></a></li>
                                    </c:if>
                                    <li><a href="javascript:void(0)"  class="btn" onclick="generateTSR();"><span class="btn_img"><span class="save">View TSR</span></span></a></li>
                                </s:if>
                            </ul>
                        </del>
                    </div>
                </s:form>
            </c:if>    
        </div>
    </body>
</html>
