<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="milestone.details.title" /></title>
        <s:head />
        <script type="text/javascript" language="javascript">
            function handleCreate() {
                var form = document.milestoneForm;
                form.action="milestonecreate.action";
                form.submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="milestone.details.title"/></h1>
        <c:set var="topic" scope="request" value="trialmilestones"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <div class="box">
            <pa:sucessMessage />
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror /></div>
            </s:if>
            <s:form name="milestoneForm">
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                <h2>
                    <fmt:message key="milestone.details.title"/>
                </h2>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="milestoneList" value="milestoneList" scope="request"/>
                            <display:table name="milestoneList" id="row" class="data" sort="list" pagesize="200" requestURI="milestone.action">
                                <display:column escapeXml="true" property="milestone" sortable="false" titleKey="milestone.milestone" />
                                <display:column escapeXml="true" property="date" sortable="false" titleKey="milestone.date" />
                                <display:column escapeXml="true" property="comment" sortable="false" titleKey="milestone.comment" />
                            </display:table>
                        </td>
                    </tr>
                </table>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <pa:displayWhenCheckedOut>
                                <li>
                                    <a href="#" class="btn" onclick="this.blur();handleCreate();">
                                        <span class="btn_img"><span class="add">Add </span></span>
                                    </a>
                                </li>
                            </pa:displayWhenCheckedOut>
                        </ul>
                    </del>
                </div>
            </s:form>
        </div>
    </body>
</html>