<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all" />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/orgSearch.js'/>"></script>
        <script type="text/javascript" language="javascript">
            function submitform(persid, name) {   
                window.top.hidePopWin(false); 
            }
            
            function loadSelectedTrialType() {
            	var action = '${pageContext.request.contextPath}';   
            	var sum4FundingCatCode = document.getElementById('summaryFourFundingCategoryCode').value;
            	if (sum4FundingCatCode == '' ) {
                	alert('Please Select Summary Four Funding Category');
                	return;
            	}    
            	if (sum4FundingCatCode == 'Industrial') { 
            	    action = action + "/protected/submitProprietaryTrialinputNct.action?sum4FundingCatCode=" + sum4FundingCatCode;
                } else {
            		action = action + "/protected/submitTrial.action?sum4FundingCatCode=" + sum4FundingCatCode; 
                }
            	window.top.hidePopWin(false); 
            	window.top.location = action;
            }
        </script> 
        <style type="text/css">
            div.trial_categories div {
                padding-bottom: 5px;
            }
            div.trial_categories div ul {
                padding-left: 30px;
            }
            div.trial_categories div ul li {
                list-style-type: disc;
            }
            div.trial_categories div ul li ul li {
                list-style-type: circle;
            }
        </style>
    </head> 
    <body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
        <div class="box">
            <s:form id="selectTypeOfTrial" name="selectTypeOfTrial" >
                <div class="box" align="center">
                    <table>
                        <!--  summary4 information -->
                        <tr>
                            <td align="left" colspan="2">
                                <b>Select one of the following categories that best matches the trial you want to submit</b>
                            </td>
                        </tr>
                        <tr>
                            <td scope="row" class="label">
                                <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_sponsor_type">
                                    <label for="summaryFourFundingCategoryCode">Trial Submission Category: </label>
                                    <span class="required">*</span>
                                </reg-web:displayTooltip>
                            </td>                            
                            <td class="value">
                                <s:select headerKey="" headerValue="--Select--"
                                        name="summaryFourFundingCategoryCode"
                                        id = "summaryFourFundingCategoryCode"
                                        list="#{'National':'National', 'Externally Peer-Reviewed':'Externally Peer-Reviewed','Institutional':'Institutional','Industrial':'Industrial/Other'}"
                                        cssStyle="width:206px" />
                                <span class="formErrorMsg">
                                    <s:fielderror>
                                        <s:param>trialDTO.summaryFourFundingCategoryCode</s:param>
                                    </s:fielderror>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">&nbsp; </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div align="left" class="info trial_categories"> 
                                    <div><b>National</b>: <s:property escapeHtml="false" escapeXml="false" 
                                        value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.National','Registry')"/></div>
                                    <div><b>Externally Peer-Reviewed</b>: <s:property escapeHtml="false" escapeXml="false" 
                                        value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.ExternallyPeerReviewed','Registry')"/></div>
                                    <div><b>Institutional</b>: <s:property escapeHtml="false" escapeXml="false" 
                                        value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Institutional','Registry')"/></div>
                                    <div><b>Industrial/Other</b>: <s:property escapeHtml="false" escapeXml="false" 
                                        value="@gov.nih.nci.pa.util.MiscDocumentUtils@getDocumentContent('TrialCategory.Industrial','Registry')"/></div>
                                </div> 
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div class="actionsrow">
                                    <del class="btnwrapper">
                                        <ul class="btnrow">
                                            <li>
                                                <a href="javascript:void(0)" class="btn" onclick="loadSelectedTrialType();"><span class="btn_img"><span class="save">Submit</span></span></a>
                                            </li>
                                            <li>
                                                <a href="javascript:void(0)" class="btn" onclick="submitform();"><span class="btn_img"><span class="cancel">Cancel</span></span></a>
                                            </li>          
                                        </ul>   
                                    </del>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </s:form>
        </div>
    </body>
</html>
