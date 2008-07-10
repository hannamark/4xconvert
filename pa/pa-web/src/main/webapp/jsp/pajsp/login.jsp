<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<s:set name="menuPage" value="%{'login'}"/>  
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trail.login.title"/></title>
	<s:head />
</head>

<body onload="setFocusToFirstControl();">
<div id="contentwide">
 <h1><fmt:message key="trail.login.title" /></h1>

<!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a>

        <!--Box-->

<div id="box">

<s:form action="authenticate">
  <span class="formErrorMsg"> <s:actionerror /> </span>
     <table cellspacing="2" >  
            <tr>
                <td align=right>
                     <label for="userName"> <fmt:message key="trail.login.username"/></label>
                </td>
                <td align=left>
                    <s:textfield name="userName" size="15" maxlength="20" required="true"/>
                    <span class="formErrorMsg"> <s:fielderror><s:param>userName</s:param></s:fielderror> </span>
                </td> 
                <td><font color="red"><b>*</b></font></td>
             </tr>    
			<tr>
                <td align=right>
                     <label for="Password"> <fmt:message key="trail.login.password"/></label>
                </td>
                <td align=left>
                    <s:password name="password" size="15" maxlength="20" required="true"/>
                     <span class="formErrorMsg"><s:fielderror> <s:param>password</s:param></s:fielderror></span>
                </td>              
                <td><font color="red"><b>*</b></font></td>
             </tr>   
			 <tr>
                <td align=right>
                     <INPUT TYPE="submit" NAME="submit"  value="Login" class="button"/>  
                </td>                            
             </tr>                
		</table>
</s:form>

</div>
</div>

</body>
</html>