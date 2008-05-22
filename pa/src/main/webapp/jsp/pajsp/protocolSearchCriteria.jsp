<!DOCTYPE html PUBLIC 
	"-//W3C//DTD XHTML 1.1 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	
<%@taglib prefix="s" uri="/struts-tags" %>
<s:set name="menuPage" value="%{'QueryProtocol'}"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Protocol Search Criteria</title>
	<s:head />
</head>
<body>
	<!-- main content begins-->

<!--Content-->

<div id="contentwide">


    <!--ADD CONTENT HERE-->


    <h1>Search Protocols</h1>


    <!--Help Content-->
    <a href="#" class="helpbutton" onclick="Help.popHelp('query_protocol')">Help</a>

    <!--Search Box-->

    <div id="searchbox">
		<s:form action="protocolSearchCriteria">
            <table cellspacing="8">	
            	<tr>
                    <td align=right>
                    	<b>NCI accession number:</b>
                    </td>
                    <td align=left>
                    	<s:textfield label="NCI accession number" name="nci" size="15" maxlength="10" theme="simple"/>
                    </td>
                    <td align=right>
                    	<b>Official Title:</b> 
                    </td>
                    <td align=left>                                             
                    	<s:textfield label="Official Title" name="title"  size="15" maxlength="10" theme="simple"/>
                    </td>
                 </tr>                                               
				 <tr>
                    <td align=right>
                     	<b>Lead Organization:</b>
                    </td>
                    <td align=left>
                     	<s:textfield label="" name="leadOrg" size="15" maxlength="10" theme="simple"/>                                             
                    </td>
                    <td align=right>
                    	<b>Lead Organization Protocol ID:</b>
                    </td>
                    <td align=left>
                    	<s:textfield label="Lead Organization Protocol ID" name="leadOrgPID" size="15" maxlength="10" theme="simple"/>                                                 
                    </td>                    
                </tr>      
                <tr>
                    <td align=right>
                    	<b>Study Phase:</b>                    	
                    </td>
                    <td align=left>
						<s:select label="Study Phase"
						          name="sphase"
						          theme="simple"
						          headerKey="0"
						          headerValue="-- Please Select --"
						          list="%{#{'1':'I','1/2':'I/II','2':'II','2/3':'II/III', '3':'III','3/4':'III,IV','4':'IV'}}">						          
						</s:select>
                  	</td>                  	
                  	<td align=right>
                    	<b>Study Phase:</b>
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
                    	<b>Abstraction Status:</b>
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
