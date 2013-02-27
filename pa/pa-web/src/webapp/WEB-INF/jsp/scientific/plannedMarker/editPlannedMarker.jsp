<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popupPlannedMarker.action" var="lookupUrl" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title><fmt:message key="plannedMarker.details.title" /></title>
        <s:head />
        <script type="text/javascript" src='<c:url value="/scripts/js/coppa.js"/>'></script>
        <script type="text/javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>

        <script type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions(){
                setFocusToFirstControl();
                toggleAssayTypeOtherText();
                toggleEvalTypeOtherText();
                toggleSpecimenTypeOtherText();
                toggleHugoCode();
            }
            
            function toggleHugoCode() {
                if ($('foundInHugo').value == 'true') {
                    $('hugoCodeRow').show();
                } else {
                    $('hugoCodeRow').hide();
                }
            }
            function toggleEvalTypeOtherText() {
               var element = document.getElementsByName("plannedMarker.evaluationType");                
                for (var i=0; i <= element.length; i++) {
                    if (element[i].value == 'Other' && element[i].checked == true) {    
                         $('evalTypeOtherTextRow').show();
                         break; 
                    } else {
                        $('evalTypeOtherTextRow').hide(); 
                    }     
                }
                return false;
            }
            
            function toggleAssayTypeOtherText() {
            var element = document.getElementsByName("plannedMarker.assayType");
                for (var i=0; i <= element.length; i++) {
                    if (element[i].value == 'Other' && element[i].checked == true) {    
                         $('assayTypeOtherTextRow').show();
                         break;
                         
                    } else {
                        $('assayTypeOtherTextRow').hide(); 
                    }     
                } 
                return false;  
            }
            
            function toggleAssayPurposeOtherText() {
            var element = document.getElementsByName("plannedMarker.assayPurpose");
                for (var i=0; i <= element.length; i++) {
                    if (element[i].value == 'Other' && element[i].checked == true) {    
                         $('assayPurposeOtherTextRow').show();
                         break;
                         return false;
                    } else {
                        $('assayPurposeOtherTextRow').hide(); 
                    }     
                } 
            }
            
            function toggleSpecimenTypeOtherText() {
            var element = document.getElementsByName("plannedMarker.tissueSpecimenType");
            
                for (var i=0; i <= element.length; i++) {
                    if (element[i].value == 'Other' && element[i].checked == true) {  
                         $('specimenTypeOtherTextRow').show();
                         break;
                         return false; 
                    } else {
                        $('specimenTypeOtherTextRow').hide(); 
                    }     
                } 
                return false;
            }
            
            function cadsrLookup(){
                showPopWin('${lookupUrl}', 1000, 600, '', 'Marker Search in caDSR');
            }
            
            function addVariation(status){               
                $('saveReset').value=status;
                document.forms[0].submit();
            }
            function loadDiv(markerId) {
                window.top.hidePopWin(true);
                var url = '/pa/protected/ajaxptpPlannedMarkerdisplaySelectedCDE.action';
                var split = location.search.replace('?', '').split('=');
                if (split != null) {
                 var rowValue = split[1];
                }
                var params = { cdeId: markerId, selectedRowIdentifier:rowValue};
                
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
            
            function loadMarkerWithRequestedCDE(markerName, foundInHugo, hugoCode) {
                window.top.hidePopWin(true);
                var url = '/pa/protected/ajaxptpPlannedMarkerdisplayRequestedCDE.action';
                var params = {
                    'plannedMarker.foundInHugo': foundInHugo,
                    'plannedMarker.hugoCode': hugoCode,
                    'plannedMarker.name': markerName
                };
                var div = $('plannedMarkerDetails');
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var options = {
                   onComplete: function(transport) {
                                   toggleHugoCode();
                               }
                };
                var aj = callAjaxPost(div, url, params, options);
            }

            function cancelAction(url){
            	document.forms[0].setAttribute("action", url);
            	document.forms[0].submit();
            }

        </script>
    </head>
    <body>
        <h1><fmt:message key="plannedMarker.details.title" /></h1>
        <c:set var="topic" scope="request" value="abstractmarkers"/>
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
                            <s:token/>
                            <pa:studyUniqueToken/>
                            <div id="plannedMarkerDetails">
                                <%@ include file="/WEB-INF/jsp/scientific/plannedMarker/selectedPlannedMarker.jsp"%>
                            </div>
                            <div class="actionsrow">
                                <del class="btnwrapper">
                                    <ul class="btnrow">
                                        <li>
                                            <s:a cssClass="btn" href="javascript:void(0)" onclick="document.forms[0].submit();">
                                                <span class="btn_img"><span class="add">Save</span></span>
                                            </s:a>
                                            <s:a onclick="javascript:cancelAction('%{cancelUrl}');" href="javascript:void(0)" cssClass="btn">
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
