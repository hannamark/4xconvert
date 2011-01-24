<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popupPlannedMarker.action" var="lookupUrl" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all" />
        <link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>

        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
                toggleAssayTypeOtherText();
                toggleAssayPurposeOtherText();
                toggleHugoCode();
            }
            function toggleHugoCode() {
            	if ($('foundInHugo').value == 'true') {
                    $('hugoCodeRow').show();
                } else {
                    $('hugoCodeRow').hide();
                }
            }
            
            function toggleAssayTypeOtherText() {
            	if ($('assayType').value == 'Other') {
                    $('assayTypeOtherTextRow').show();
                } else {
                    $('assayTypeOtherTextRow').hide();
                }
            }
            function toggleAssayPurposeOtherText() {
            	if ($('assayPurpose').value == 'Other') {
            		$('assayPurposeOtherTextRow').show();
            	} else {
            		$('assayPurposeOtherTextRow').hide();
            	}	
            }
            function cadsrLookup(){
                showPopWin('${lookupUrl}', 1200, 600, '', 'Marker Search in caDSR');
            }
            function loadDiv(markerId) {
                window.top.hidePopWin(true);
                var url = '/pa/protected/ajaxptpPlannedMarkerdisplaySelectedCDE.action?cdeId='+markerId;
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = new Ajax.Updater(div, url, {
                   asynchronous: true,
                   method: 'get',
                   evalScripts: false
                });
            }      
            function loadMarkerWithRequestedCDE(markerName, foundInHugo, hugoCode) {
            	window.top.hidePopWin(true);
            	var url = '/pa/protected/ajaxptpPlannedMarkerdisplayRequestedCDE.action?plannedMarker.name='+ markerName
            			+ '&plannedMarker.hugoCode=' + hugoCode + '&plannedMarker.foundInHugo=' + foundInHugo;
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                var aj = new Ajax.Updater(div, url, {
                   asynchronous: true,
                   method: 'get',
                   evalScripts: false,
                   onComplete: function(transport) {
                	   toggleHugoCode();
                   }
                });
            }
            
        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title" /></h1>
        <c:set var="topic" scope="request" value="planned_marker"/>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp" />
        <s:url id="cancelUrl" namespace="/protected" action="plannedMarker"/>
        <div class="box">
            <pa:sucessMessage /> 
            <s:if test="hasActionErrors()">
                <div class="error_msg"><s:actionerror/></div>
            </s:if>
            <h2>
                <s:if test="%{currentAction == 'edit'}">
                    <s:set var="submitUrl" value="'plannedMarkerupdate'"/>
                    <fmt:message key="plannedMarker.edit.title"/>
                </s:if>
                <s:elseif test="%{currentAction == 'create'}">
                    <s:set var="submitUrl" value="'plannedMarkeradd'"/>
                    <fmt:message key="plannedMarker.add.title"/>
                </s:elseif>
            </h2>
            <table class="form">
                <tr>
                    <td colspan="2">
                        <s:form id="plannedMarkerForm" action="%{#submitUrl}">
                            <div id="plannedMarkerDetails">
                                <%@ include file="/WEB-INF/jsp/plannedMarker/selectedPlannedMarker.jsp"%>
                            </div>
                            <div class="actionsrow">
                                <del class="btnwrapper">
                                    <ul class="btnrow">
                                        <li>
                                            <s:a cssClass="btn" href="#" onclick="document.forms[0].submit();">
                                                <span class="btn_img"><span class="add">Save</span></span>
                                            </s:a>
                                            <s:a href="%{cancelUrl}" cssClass="btn">
                                                <span class="btn_img"><span class="cancel">Cancel</span></span>
                                            </s:a>
                                        </li>
                                    </ul>
                                </del>
                            </div>
                        </s:form>
                    </td>
                </tr>
            </table>
         </div>
    </body>
</html>