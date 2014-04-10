<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <!--  Trial Oversignt Authority Organization Name -->
 <div class="col-xs-4">
   <div id="loadAuthField">
    <s:select id="trialDTO.selectedRegAuth" name="trialDTO.selectedRegAuth" list="trialDTO.regIdAuthOrgList" listKey="id" listValue="name" cssClass="form-control" />  
    <span class="alert-danger"> 
	    <s:fielderror>
	    <s:param>regulatory.oversight.auth.name</s:param>
	    </s:fielderror>                            
   </span>
 </div>
</div>
<i class="fa-question-circle help-text" id="popover" rel="popover" data-content="Select the name of each national or international health organization with authority over the protocol." data-placement="top" data-trigger="hover"></i>
     