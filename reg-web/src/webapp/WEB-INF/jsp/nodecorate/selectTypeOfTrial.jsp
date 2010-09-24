<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<link href="<c:url value='/styles/style.css'/>" rel="stylesheet" type="text/css" media="all"/>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<SCRIPT language="JavaScript">
    function submitform(persid, name)
    {   
        window.top.hidePopWin(true); 
    }
    function loadSelectedTrialType() {
    	 var action = '<%=request.getContextPath()%>';   
    	var sum4FundingCatCode = document.getElementById('summaryFourFundingCategoryCode').value;
    	if (sum4FundingCatCode == '' ) {
        	alert('Please Select Summary Four Funding Category');
        	return;
    	}    
    	if(sum4FundingCatCode == 'Industrial') { 
    	    action = action + "/protected/submitProprietaryTrial.action?sum4FundingCatCode="+sum4FundingCatCode;
        } else {
    		action = action + "/protected/submitTrial.action?sum4FundingCatCode="+sum4FundingCatCode; 
        }
    	window.top.location = action;
        window.top.hidePopWin(true); 
    }
</SCRIPT> 
</head> 
<body onload="setFocusToFirstControl(); window.top.centerPopWin();" class="submodal">
<div class="box">
<s:form id="selectTypeOfTrial" name="selectTypeOfTrial" >
    <div class="box" align="center">
    <table>
          <!--  summary4 information -->
          <tr>
                <td scope="row" class="label">
                    <reg-web:displayTooltip tooltip="tooltip.summary_4_funding_sponsor_type">
                        <label for="submitTrial_summary4FundingCategory">Summary 4 Funding Sponsor Type:</label>
                        <span class="required">*</span>
                    </reg-web:displayTooltip>
                </td>
                     <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
                <td class="value">
                     <s:select headerKey="" headerValue="--Select--"
                            name="summaryFourFundingCategoryCode"
                            id = "summaryFourFundingCategoryCode"
                            list="#summaryFourFundingCategoryCodeValues"
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
       <div class="actionsrow">
                <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#"                
                        class="btn" onclick="loadSelectedTrialType();"><span class="btn_img"><span class="save">Submit</span></span></a></li>
                    <li><a href="#"                
                        class="btn" onclick="submitform();"><span class="btn_img"><span class="cancel">Cancel</span></span></a></li>          
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
