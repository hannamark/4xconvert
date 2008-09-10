<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="StatusHistory.title"/></title>
    <s:head />
</head>


<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="StatusHistory.title" /></h1>

<!--Help Content-->
    <!-- <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
  <div id="box">
    <s:form action="studyOverallStatus"><s:actionerror/>
        <table class="data">
            <tr>
                <td colspan="2" style="text-align"right;">                        
                    <INPUT TYPE="button" value="Close" class="button"  onclick="javascript: self.close ();" />           
                </td> 				
            </tr>
        </table>
    </s:form>
   </div>
 <!-- </div> -->
 </body>
 </html>