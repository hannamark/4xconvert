<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>	
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set name="menuPage" value="%{'QueryProtocol'}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="protocol.search.title"/></title>
	<s:head />
</head>
<body>
	<!-- main content begins-->

<!--Content-->

<div id="contentwide">


    <!--ADD CONTENT HERE-->

	<h1><fmt:message key="protocol.search.header"/></h1>

    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>

    <!--Search Box-->

    <div id="searchbox">
		<s:form action="protocolSearchCriteria">
            <table cellspacing="8">	
            	<tr>
                    <td align=right>
                    	<b><fmt:message key="protocol.search.label.nciNumber"/></b>
                    </td>
                    <td align=left>
                    	<s:textfield name="nci" size="15" maxlength="10" theme="simple"/>
                    </td>
                    <td align=right>
                    	<b><fmt:message key="protocol.search.label.officialTitle"/></b> 
                    </td>
                    <td align=left>                                             
                    	<s:textfield name="title"  size="15" maxlength="10" theme="simple"/>
                    </td>
                 </tr>                                               
				 <tr>
                    <td align=right>
                     	<b><fmt:message key="protocol.search.label.leadOrganization"/></b> 
                    </td>
                    <td align=left>
                     	<s:textfield name="leadOrg" size="15" maxlength="10" theme="simple"/>                                             
                    </td>
                    <td align=right>
                    	<b><fmt:message key="protocol.search.label.leadOrganizationProtocolID"/></b> 
                    </td>
                    <td align=left>
                    	<s:textfield name="leadOrgPID" size="15" maxlength="10" theme="simple"/>                                                 
                    </td>                    
                </tr>      
                <tr>
                    <td align=right>
                    	<b><fmt:message key="protocol.search.label.studyPhase"/></b>                     	
                    </td>
                    <td align=left>
						<s:select name="sphase"
						          theme="simple"
						          headerKey="0"
						          headerValue="-- Please Select --"
						          list="%{#{'1':'I','1/2':'I/II','2':'II','2/3':'II/III', '3':'III','3/4':'III,IV','4':'IV'}}">						          
						</s:select>
                  	</td>                  	
                  	<td align=right>
                  		<b><fmt:message key="protocol.search.label.studyStatus"/></b>                      
                    </td>
					<td align=left>
						<s:select label="Study Status:"
						          name="sStatus"
						          theme="simple"
						          headerKey="1"
						          headerValue="-- Please Select --"
						          list="%{#{'o':'Open','c':'Closed','s':'Suspended'}}"/>						          						
                  	</td>                  
                </tr>                                        
                <tr>
                <td align=right>
                    	<b><fmt:message key="protocol.search.label.abstractionStatus"/></b>    
                    </td>
					<td >
						<s:select label="Abstraction Status:"
						          name="aStatus"
						          theme="simple"
						          headerKey="1"
						          headerValue="-- Please Select --"
						          list="%{#{'sub':'Submitted','inp':'In Progress','acc':'Accepted','onh':'On Hold'}}"/>						          						
                  	</td>    
                    <td colspan="2">                    	
                    	<INPUT TYPE="button" NAME="submit"  class="button" value="Search" onClick="submitValues()"/>          
                        <INPUT TYPE="button" NAME="reset"  class="button" value="Reset" onClick="resetValues()"/>
                    </td>
                </tr>
            </table>

       </s:form>

    </div>
    <!--/Search Box-->

</div>

<!--/Content-->

</body>
</html>
