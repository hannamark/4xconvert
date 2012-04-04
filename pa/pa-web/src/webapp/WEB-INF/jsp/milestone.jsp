<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="milestone.details.title" /></title>
        <s:head />

        <script type="text/javascript" language="javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/datetimepicker.js"/>"></script>
        
        <script type="text/javascript" language="javascript">
            addCalendar("Cal1", "Select Date", "milestone.date", "milestoneForm");
            setWidth(90, 1, 15, 1);
            setFormat("mm/dd/yyyy");
        
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();         
            }
            
            function milestoneAdd() {
                document.addmilestoneForm.action = "milestoneadd.action";
                document.addmilestoneForm.submit();
            }
            
            function cancel() {
                if (winCal != null ) { 
                    winCal.close();
                }    
                var form = document.addmilestoneForm;
                form.action="milestoneview.action";
                form.submit();
            }
            
            function hideCol(col) {          
                col.style.display = 'none'; 
            }
            
            function showCol(col) {
                col.style.display = '';
            }
            
            function statusChange() {
                var selectedMilestone = document.forms["addmilestoneForm"].elements["milestone.milestone"].value;
                if (selectedMilestone == "Late Rejection Date") {
                    hideCol(document.getElementById('milestoneComments'));
                    showCol(document.getElementById('latemilestoneComments'));
                } else {
                    hideCol(document.getElementById('latemilestoneComments'));
                    showCol(document.getElementById('milestoneComments'));
                }
            }
        
            function handleChange() {
                var form = document.listmilestoneForm;
                form.action="milestoneview.action";
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
                <div class="error_msg">
                    <s:actionerror />
                </div>
            </s:if>
            <s:form name="listmilestoneForm">
                <s:token/>
                <pa:studyUniqueToken/>
                <s:hidden name="selectedRowIdentifier"/>
                
                <s:if test="%{amendmentMap.size > 1}">
                    <div>
                        <span class="label">
                            <label for="submissionNumber"><fmt:message key="milestone.submissionNumber"/></label>
                        </span>
                        <s:select id="submissionNumber" list="amendmentMap" listValue="value.submissionNumber" 
                                  name="submissionNumber" onchange="handleChange();" value="submissionNumber"/>
                    </div>
                </s:if>
                
                <h2>
                    <fmt:message key="milestone.details.title"/>
                </h2>
                <table class="form">
                    <tr>
                        <td colspan="2">
                            <s:set name="milestoneList" value="milestoneList" scope="request"/>
                            <display:table name="milestoneList" id="row" class="data" sort="list" pagesize="200" requestURI="milestone.action" >
                                <display:column escapeXml="true" property="milestone" sortable="false" titleKey="milestone.milestone" />
                                <display:column escapeXml="true" property="date" sortable="false" titleKey="milestone.date" />
                                <display:column sortable="false" titleKey="milestone.comment" >
                                    <c:out value="${row.comment}"/>
                                </display:column>
                                <display:column escapeXml="true" property="creator" sortable="false" titleKey="milestone.creator" />
                                <display:column escapeXml="true" property="creationDate" sortable="false" titleKey="milestone.creationDate" />
                            </display:table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
        
        <pa:displayWhenCheckedOut>
            <s:if test="%{addAllowed}">
		        <div class="box">
		            <h2>
		                <fmt:message key="milestone.add.details.title"/>
		            </h2>
		            <table class="form">
		                <tr>
		                    <td colspan="2">
		                        <table class="form">
		                            <tr>
		                                <td colspan="2">
		                                    <h3><fmt:message key="milestone.milestone"/></h3>
		                                    <s:form name="addmilestoneForm">
		                                        <s:token/>
		                                        <pa:studyUniqueToken/>
		                                        <table class="form">
		                                            <tr>
		                                                <td class="label">
		                                                    <s:label><fmt:message key="milestone.milestone"/></s:label>
		                                                    <span class="required">*</span>
		                                                </td>
		                                                <td class="value" style="width: 250px">
		                                                    <s:select headerKey="" headerValue="--Select--" name="milestone.milestone" list="%{allowedMilestones}" 
		                                                              onchange="statusChange()" onfocus="statusChange()"/>
		                                                </td>
		                                            </tr>
		                                            <tr>
		                                                <td class="label">
		                                                    <s:label><fmt:message key="milestone.date"/></s:label>
		                                                    <span class="required">*</span>
		                                                </td>
		                                                <td class="value">
		                                                    <s:textfield name="milestone.date" id="date" maxlength="30" size="30" cssStyle="width:170px;float:left" readonly="true"/>
		                                                    <a href="javascript:NewCal('date','mmddyyyy',false,24)">
		                                                        <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
		                                                    </a>
		                                                </td> 
		                                            </tr>
		                                            <tr>
		                                                <td class="label" id="milestoneComments">
		                                                    <s:label><fmt:message key="milestone.comment"/></s:label>
		                                                </td>
		                                                <td class="label" id="latemilestoneComments">
		                                                    <s:label>Late Rejection <fmt:message key="milestone.comment"/></s:label>
		                                                    <span class="required">*</span>
		                                                </td>
		                                                <td class="value">
		                                                    <s:textarea name="milestone.comment" rows="3" cssClass="charcounter" cssStyle="width:280px;" maxlength="200"/>
		                                                </td>
		                                            </tr>
		                                        </table>
		                                    </s:form>
		                                    <div class="actionsrow">
		                                        <del class="btnwrapper">
		                                            <ul class="btnrow">
		                                                <li>
		                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="milestoneAdd();">
		                                                        <span class="btn_img"> <span class="add">Add Milestone</span></span>
		                                                    </s:a>
		                                                </li>
		                                                <li>
		                                                    <s:a href="javascript:void(0)" cssClass="btn" onclick="cancel();">
		                                                        <span class="btn_img"><span class="cancel">Cancel</span></span>
		                                                    </s:a>
		                                                </li>
		                                            </ul>
		                                        </del>
		                                    </div>
		                                </td>
		                            </tr>
		                        </table>
		                    </td>
		                </tr>
		            </table>
		        </div>
            </s:if>
        </pa:displayWhenCheckedOut>            
    </body>
</html>