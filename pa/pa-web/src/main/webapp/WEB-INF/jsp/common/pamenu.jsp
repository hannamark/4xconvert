<%@ taglib prefix="s" uri="/struts-tags" %>
      
	<s:if test="%{#menuPage=='other'}">             
		<ul class="cactusmenu">
			<li class="liheader">NCI Protocol Abstraction Portal</li>
				<li><a href="searchProtocol.action" >Query Protocols</a></li>
		    	<li><a href="Protocol_Details_Edit.html" >Protocol Detail</a></li>
		    	<li><a href="Protocol_Grant.html"  class="selected">Protocol Grants</a></li>
		    	<li><a href="Administrative_Data.html"  >Administrative Data</a></li>
				<li><a href="Participating_Organization.html" >Participating Organization </a></li>
				<li><a href="Safety_Regulation.html"  >Safety Regulation</a></li>
				<li><a href="Publication.html"  >Publication</a></li>
				<li><a href="Disease_Condition.html" >Disease Condition </a></li>
				<li><a href="Study_Design.html"  >Study Design</a></li>
				<li><a href="Participating_Subgroup.html"  >Subgroups</a></li>
				<li><a href="Study_Arm.html" >Study Arm </a></li>
				<li><a href="?" >Product Information (TBD)</a></li>
				<li><a href="?"  >Study IND/IDE (TBD)</a></li>
				<li><a href="?" >Intervention (TBD)</a></li>
				<li>Maintenance</li>
		
		    <li><a href="#" onclick="Help.popHelpMain('');return false;">Help</a></li>
		    <li><a href="login.action" > Logout</a></li>
		</ul>
	</s:if>
	<s:elseif test="%{#menuPage=='QueryProtocol'}">
       <ul class="cactusmenu">			
			<li><a href="searchProtocol.action" class="selected">Query Protocols</a></li>
			<li>Maintenance</li>
		    <li><a href="#" onclick="Help.popHelpMain('');return false;">Help</a></li>
		    <li><a href="login.action" > Logout</a></li>
		</ul>
    </s:elseif>
      