<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select id="auths" name="selectedRegAuth" list="regIdAuthOrgList" listKey="id" listValue="name" />	
 <span class="formErrorMsg"> 
                       <s:fielderror>
                       <s:param>regulatory.oversight.auth.name</s:param>
                      </s:fielderror>                            
                    </span>