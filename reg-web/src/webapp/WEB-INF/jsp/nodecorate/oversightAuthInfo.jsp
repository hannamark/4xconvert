<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
     <!--  Trial Oversignt Authority Organization Name -->
        <s:select name="trialDTO.selectedRegAuth" list="trialDTO.regIdAuthOrgList" listKey="id" listValue="name" />  
        <span class="formErrorMsg"> 
        <s:fielderror>
        <s:param>regulatory.oversight.auth.name</s:param>
        </s:fielderror>                            
       </span>
     