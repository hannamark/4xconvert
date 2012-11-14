<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="plannedMarker.details.title" /></title>
<s:head />
<script type="text/javascript"
	src='<c:url value="/scripts/js/coppa.js"/>'></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/js/prototype.js'/>"></script>
	<script type="text/javascript" language="javascript">
            function handleAction() {     
                 var extensionValue = document.forms[0].trialId.value;
                 var name = document.forms[0].markerName.value;  
                 document.forms[0].action="bioMarkerssearch.action";
                 document.forms[0].submit();
            }
            function resetValues() {
                $("trialId").value="";
                $("markerName").value="";
            }
   </script>
</head>
<body>
	<h1>
		<fmt:message key="plannedMarker.pending.markers.report" />
	</h1>
    <c:set var="topic" scope="request" value="biomarkers"/>
	<div class="box">
		<s:if test="hasActionErrors()">
			<div class="error_msg">
				<s:actionerror />
			</div>
		</s:if>
		<s:form name="diseaseForm">
		 <h1><fmt:message key="plannedMarker.search.header"/></h1>
		<table  class="form">
		
            <tr>
                <td  scope="row" class="label">
                   <label for="trialId"> <fmt:message key="plannedMarker.protocolId"/></label>
                </td>
                <td>
                    <s:textfield id="trialId" name="trialId" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
            
                <td  scope="row" class="label">
                   <label for="markerName"> <fmt:message key="plannedMarker.markerName"/></label>
                </td>
                <td>
                    <s:textfield id="markerName" name="markerName" maxlength="200" size="100" cssStyle="width:200px"  />
                </td>
            </tr>
        </table>
        <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction();return false"><span class="btn_img"><span class="search">Search</span></span></s:a>
                            <s:a href="javascript:void(0)" cssClass="btn" onclick="resetValues();return false"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>
                            </li>
                        </ul>
                    </del>
                </div>
			 <table class="form">
			 <tr>
                    <td colspan="2">
                    <s:set name="plannedMarkerList" value="plannedMarkerList" scope="request" /> 
							<display:table
							name="plannedMarkerList" htmlId="plannedMarkerTable" id="row"
							class="data" defaultsort="1" sort="list" pagesize="200"
							requestURI="bioMarkersexecute.action">
							<display:column escapeXml="true" property="nciIdentifier"
								sortable="true" titleKey="plannedMarker.protocolId"
								headerClass="sortable"/>
							<display:column escapeXml="true" property="trialStatus" sortable="false" 
							titleKey="plannedMarker.TrialStatus" headerClass="sortable"/>
							<display:column escapeXml="true" property="name" sortable="true"
								titleKey="plannedMarker.markerName" headerClass="sortable" />
							<display:column titleKey="plannedMarker.edit"
								headerClass="centered" class="action">
								<del class="btnwrapper">
									<ul class="btnrow">
										<li><s:url id="editUrl" namespace="/protected"
												action="bioMarkers" method="edit">
												<s:param name="selectedRowIdentifier"
													value="%{#attr.row.id}" />
											</s:url> <s:a href="%{editUrl}" cssClass="btn">
												<span class="btn_img">Edit</span>
											</s:a>
										</li>
									</ul>
								</del>
							</display:column>
							<display:column titleKey="plannedMarker.question"
								headerClass="centered" class="action">
								<del class="btnwrapper">
									<ul class="btnrow">
										<li><s:url id="sendQuestionUrl" namespace="/protected"
												action="bioMarkers" method="sendQuestion">
												<s:param name="selectedRowIdentifier"
													value="%{#attr.row.id}" />
											</s:url> <s:a href="%{sendQuestionUrl}" cssClass="btn">
												<span class="btn_img">?</span>
											</s:a>
										</li>
									</ul>
								</del>
							</display:column>
							<display:column titleKey="plannedMarker.action"
								headerClass="centered" class="action">
								<del class="btnwrapper">
									<ul class="btnrow">
										<li><s:url id="addUrl" namespace="/protected"
												action="bioMarkers" method="accept">
												<s:param name="selectedRowIdentifier"
													value="%{#attr.row.id}" />
											</s:url> <s:a href="%{addUrl}" cssClass="btn">
												<span class="btn_img">Accept</span>
											</s:a>
										</li>
									</ul>
								</del>
							</display:column>
						</display:table>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>
