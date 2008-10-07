<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="submit.trial.page.title"/></title>   
    <s:head/>
</head>

<SCRIPT LANGUAGE="JavaScript">


function handleAction(){   
    document.forms[0].page.value = "Submit";
    document.forms[0].action="submitTrialcreate.action";
    document.forms[0].submit();  
}

</SCRIPT>

<body>
<!-- main content begins-->
    <h1><fmt:message key="submit.trial.page.header"/></h1>
    <div class="box" id="filters">
    <s:form><s:actionerror/>
        <input type="hidden" name="page" />
        <table class="form"> 
          <tr>
                <th colspan="2"><fmt:message key="submit.trial.trialDetails"/></th>
          </tr>
          <tr>
                <td colspan="2" class="space">&nbsp;</td>
          </tr>   
          <tr>
                <td scope="row" class="label">
                    <label for="identifier"> <fmt:message key="submit.trial.identifier"/></label>
                </td>
                <td>
                    <s:textfield name="leadOrgIdentifier"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td scope="row" class="label">
                     <label for="trialTitle"> <fmt:message key="submit.trial.title"/></label>
                </td>
                <td>
                    <s:textfield name="trialTitle"  maxlength="200" size="100"  cssStyle="width:200px" />
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialPhase"> <fmt:message key="submit.trial.phase"/></label> 
                </td>
                <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="trialPhase" list="#phaseCodeValues"  value="trialPhase" cssStyle="width:206px" />
                </td>
          </tr>
          <tr>
                <td  scope="row" class="label">
                    <label for="trialType"> <fmt:message key="submit.trial.type"/></label> 
                </td>
                    <s:set name="typeCodeValues" value="@gov.nih.nci.pa.enums.StudyTypeCode@getDisplayNames()" />
                <td>                                             
                    <s:select headerKey="" headerValue="All" name="trialType" list="#typeCodeValues"  value="trialType" cssStyle="width:206px" />
                </td>
          </tr>

        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">         
                    <li><li>            
                            <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Submit Trial Details</span></span></s:a>  
                        </li>
                </ul>   
            </del>
        </div>  
        
   </s:form>

 </div>
   
</body>
</html>
