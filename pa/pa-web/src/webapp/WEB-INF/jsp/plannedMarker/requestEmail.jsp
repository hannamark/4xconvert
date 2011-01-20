<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
<script LANGUAGE="JavaScript">

     function handleMailAction(){
    	    var jsFromEmail = document.getElementById("fromEmail").value;
    	    var jsemailMsg = document.getElementById("emailMessage").value;
    	    var url = '/pa/protected/popupPlannedMarker.action?fromEmail='+jsFromEmail+'&emailMsg=' + jsemailMsg;
    	    var div = document.getElementById('cdeDetailsDiv');
    	    div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
    	    var aj = new Ajax.Updater(div,url, {
    	        asynchronous: true,
    	        method: 'get',
    	        evalScripts: false
    	        });
    	    }
     function sendEmail() {
    	 var toEmail = $('toEmail').value;
    	 var fromEmail = $('fromEmail').value;
    	 var subject = $('subject').value;
    	 var name = $('name').value;
    	 var foundInHugo = $('foundInHugo').value;
    	 var hugoCode = $('hugoCode').value;
    	 var message = $('message').value;
    	 var url = '/pa/protected/popupPlannedMarkersendEmailRequest.action?plannedMarker.toEmail=' + toEmail + '&plannedMarker.fromEmail=' + fromEmail
    			 + '&plannedMarker.subject=' + subject + '&plannedMarker.name=' + name + '&plannedMarker.foundInHugo=' + foundInHugo
    			 + '&plannedMarker.hugoCode=' + hugoCode + '&plannedMarker.message=' + message;
    	 var div = $('cdeRequest');
    	 div.innerHTML = '<div><img  alt="Indicator" align="absmiddle" src="../images/loading.gif"/>&nbsp;Loading...</div>';    
         var aj = new Ajax.Updater(div,url, {
             asynchronous: true,
             method: 'get',
             evalScripts: false
         });
     }
</script>
<div id="cdeRequest">
    <div>
        <pa:failureMessage/>
        <pa:sucessMessage/>
    </div>
    <h2><fmt:message key="plannedMarker.request.title"/></h2>
    <table class="form">
        <tr>
            <td scope="row" class="label"><s:label for="plannedmMarker.toEmail"><fmt:message key="plannedMarker.request.toEmail"/>:</s:label></td>
            <td class="value" colspan="2">
                <s:textfield id="toEmail" name="plannedMarker.toEmail" maxlength="200" size="200" cssStyle="width: 200px"/>
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.toEmail</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedMarker.fromEmail"><fmt:message key="plannedMarker.request.fromEmail" />:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2">
                <s:textfield id="fromEmail" name="plannedMarker.fromEmail" maxlength="200" size="200" cssStyle="width: 200px" /> 
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.fromEmail</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="plannedMarker.subject"><fmt:message key="plannedMarker.request.subject" />:</s:label></td>
            <td class="value" colspan="2">
                <s:textfield id="subject" name="plannedMarker.subject" readonly="true" cssClass="readonly"/>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="plannedMarker.name"><fmt:message key="plannedMarker.request.markerName" />:</s:label></td>
            <td class="value" colspan="2">
                <s:textfield id="name" name="plannedMarker.name" maxlength="200" size="200" cssStyle="width: 200px"/>
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.name</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="plannedMarker.foundInHugo"><fmt:message key="plannedMarker.request.foundInHugo"/>:</s:label></td>
            <td class="value">
                <s:checkbox id="foundInHugo" name="plannedMarker.foundInHugo" />
            </td>
            <td><a href="http://nciterms.nci.nih.gov/ncitbrowser/pages/vocabulary.jsf?dictionary=Human%20Genome%20Organisation&version=July_2010" target="_blank">HUGO</a></td>
        </tr>
        <tr>
            <td scope="row" class="label"><s:label for="plannedMarker.hugoCode"><fmt:message key="plannedMarker.request.hugoCode"/>:</s:label></td>
            <td class="value" colspan="2">
                <s:textfield id="hugoCode" name="plannedMarker.hugoCode" maxlength="200" size="200" cssStyle="width: 200px"/>
                <span class="formErrorMsg">
                    <s:fielderror>
                        <s:param>plannedMarker.hugoCode</s:param>
                    </s:fielderror> 
                </span>
            </td>
        </tr>
        <tr>
            <td scope="row" class="label">
                <s:label for="plannedMarker.message"><fmt:message key="plannedMarker.request.message"/>:</s:label><span class="required">*</span>
            </td>
            <td class="value" colspan="2"><s:textarea id="message" name="plannedMarker.message" cssStyle="width:606px" rows="4" /> 
                <span class="formErrorMsg"> 
                    <s:fielderror>
                        <s:param>plannedMarker.message</s:param>
                    </s:fielderror>
                </span>
            </td>
        </tr>
    </table>
    <div class="actionsrow">
        <del class="btnwrapper">
            <ul class="btnrow">
                <li>
                    <s:a href="#" cssClass="btn" onclick="sendEmail()">
                        <span class="btn_img"><span class="save">Send Email</span></span>
                    </s:a>
                    <s:a href="#" cssClass="btn" onclick="window.top.hidePopWin();">
                        <span class="btn_img"><span class="cancel"><fmt:message key="plannedMarker.lookup.cancel"/></span></span>
                    </s:a>
                </li>
            </ul>
        </del>
    </div>
</div>
