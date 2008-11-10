<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="studyProtocol.general.title"/></title>
    <s:head />
    <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
    <script type="text/javascript"> 
    function tooltip() {
		BubbleTips.activateTipOn("acronym");
		BubbleTips.activateTipOn("dfn"); 
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
    		<s:textarea name="gtdDTO.publicTitle" cssStyle="width:206px" rows="2"/> 
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
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:206px" rows="2"/> 
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
    		<s:textarea name="gtdDTO.publicDescription" cssStyle="width:206px" rows="4"/> 
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
   			 <s:textarea name="gtdDTO.scientificDescription" cssStyle="width:206px" rows="5" /> 
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
   			 <s:textarea name="gtdDTO.keywordText" cssStyle="width:206px" rows="6" /> 
   		</td>
    </tr>
    </table>  
                  
    </s:form>
   </div>

 </body>
 </html>