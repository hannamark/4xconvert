<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.general.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
	<c:url value="/protected/popuplookuporgs.action" var="lookupUrl"/>
	<c:url value="/protected/popuplookuppersons.action" var="lookupPersonsUrl"/>
<link href="<s:url value='/styles/subModalstyle.css'/>" rel="stylesheet" type="text/css" media="all"/>
<link href="<s:url value='/styles/subModal.css'/>" rel="stylesheet" type="text/css" media="all"/>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModalcommon.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/subModal.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/prototype.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>

    <script type="text/javascript"> 
    function tooltip() {
        BubbleTips.activateTipOn("acronym");
        BubbleTips.activateTipOn("dfn"); 
    }
    function handleAction(){
        document.forms[0].action="generalTrialDesignupdate.action";
        document.forms[0].submit(); 
    }

    function lookup(){
        showPopWin('${lookupUrl}', 1050, 400, '', 'Organization');
    }   
    function lookupPerson(){
        showPopWin('${lookupPersonsUrl}', 1050, 400, '', 'Persons');
    }   
    
    function loadDiv(orgid) {
        var url = '/pa/protected/ajaxorgdisplayOrg.action?orgId='+orgid;
        var div = document.getElementById('loadOrgField');   
        div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
        var aj = new Ajax.Updater(div, url, {
            asynchronous: true,
            method: 'get',
            evalScripts: false
        });
        return false;
    }

    </SCRIPT>
	
</head>

<body onload="setFocusToFirstControl();">
<!-- <div id="contentwide"> -->
 <h1><fmt:message key="studyProtocol.general.title" /></h1>

<!--Help Content-->
   <!--  <a href="#" class="helpbutton" onclick="Help.popHelp('login');">Help</a> -->
   <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>

  <div class="box">
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form ><s:actionerror/> 
	 <h2><fmt:message key="studyProtocol.information"/></h2>
    <table class="form">
    <tr>
    	<td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
         <td class="value">
    		<s:textfield name="gtdDTO.assignedIdentifier" cssStyle="width:106px" readonly="true"/> 
    	</td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for=briefTitle>
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.briefTitle"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
         <td class="value">
    		<s:textarea name="gtdDTO.publicTitle" cssStyle="width:306px" rows="5"/> 
    	</td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for="acronym">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.acronym"/>
                 </dfn>
           </label>
         </td>
         <td class="value">
    	<s:textfield name="gtdDTO.acronym" cssStyle="width:86px" maxlength="12"/> 
    	</td>
    </tr>
    <tr>
       <td scope="row" class="label">
          <label for="officialTitle">
              <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                   <fmt:message key="studyProtocol.officialTitle"/>
              </dfn><span class="required">*</span>
          </label>
       </td>
       <td class="value">
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:306px" rows="5"/> 
       </td>
    </tr>
    <tr>
    	<th colspan="2"> <fmt:message key="studyProtocol.trialDescription"/></th>
    </tr>
    
	<tr>
		<td class="space" colspan="2">
		&nbsp;
		</td>
	</tr>
     <tr>
    	<td scope="row" class="label">
           <label for=briefSummary>
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.briefSummary"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
         <td class="value">
    		<s:textarea name="gtdDTO.publicDescription" cssStyle="width:306px" rows="6"/> 
    	</td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for=detailedDescription>
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.detailedDescription"/>
                 </dfn>
           </label>
         </td>
         <td class="value">
   			 <s:textarea name="gtdDTO.scientificDescription" cssStyle="width:306px" rows="7" /> 
   		</td>
    </tr>
    <tr>
    	<td scope="row" class="label">
           <label for=keywordText>
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.keywordText"/>
                 </dfn>
           </label>
         </td>
         <td class="value">
   			 <s:textarea name="gtdDTO.keywordText" cssStyle="width:306px" rows="7" /> 
   		</td>
    </tr>
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyProtocol.nciIdentifier"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/orgField.jsp" %>
            </div>      
        </td>
    </tr>

      <tr>
                <td scope="row" class="label">
                    <label for="org">Pi<span class="required">*</span></label> 
                </td>
                <td class="value">
                    <div id="loadPersField">
                    <%@ include file="/WEB-INF/jsp/nodecorate/displayPerson.jsp" %>
                    </div>      
                </td>
                
            </tr>
      <tr>          

    </table>  
         <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                    <li><a href="#" class="btn" onclick="this.blur();"><span class="btn_img"><span class="back">Back</span></span></a></li>
					<li><a href="#" class="btn" onclick="this.blur();"><span class="btn_img"><span class="next">Next</span></span></a></li>                
                </ul>   
            </del>

        </div>          
    </s:form>
   </div>

 </body>
 </html>