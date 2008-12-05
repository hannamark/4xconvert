<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><fmt:message key="trialValidation.page.title"/></title>   
    <s:head/>
    <script type="text/javascript"> 
        function tooltip() {
            BubbleTips.activateTipOn("acronym");
            BubbleTips.activateTipOn("dfn"); 
        }
    function handleAction(){
        document.forms[0].action="trialValidationupdate.action";
        document.forms[0].submit(); 
    }
    
    </script>
    
</head>
<body onload="setFocusToFirstControl();">
    <h1><fmt:message key="trialValidation.title" /></h1>
    <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
    <div class="box" >
  <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form >
    <s:actionerror/> 
     <h2>Trial Details</h2>
    <table class="form">
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    <fmt:message key="studyCoordinatingCenterLead.localProtocolIdentifer"/>
                 </dfn><span class="required">*</span>
           </label>
         </td>
         <td class="value">
            <s:textfield name="gtdDTO.localProtocolIdentifier" cssStyle="width:206px" /> 
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
            <s:textarea name="gtdDTO.officialTitle" cssStyle="width:406px" rows="5"/> 
       </td>
    </tr>
    <tr>
        <td scope="row" class="label"><label for="studyPhase"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
             <fmt:message key="studyProtocol.studyPhase"/></dfn><span class="required">*</span></label> </td>
        <s:set name="phaseCodeValues" value="@gov.nih.nci.pa.enums.PhaseCode@getDisplayNames()" />
        <td>
            <s:select headerKey="" headerValue="" name="gtdDTO.phaseCode" list="#phaseCodeValues" 
                value="gtdDTO.phaseCode" cssStyle="width:120px" />
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.phaseCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
        <td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.phase.comment"/></dfn></label></td>
        <td>
            <s:textarea name="gtdDTO.phaseOtherText" rows="2" cssStyle="width:300px" />
        </td>
    </tr>    
    <tr>
        <td  scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.primary.purpose"/></dfn><span class="required">*</span></label></td>
        <s:set name="primaryPurposeCodeValues" value="@gov.nih.nci.pa.enums.PrimaryPurposeCode@getDisplayNames()" />
        <td>
          <s:select headerKey="" headerValue="" name="gtdDTO.primaryPurposeCode" list="#primaryPurposeCodeValues"  
                   value="gtdDTO.primaryPurposeCode" cssStyle="width:150px" onchange="activate()"/>
          <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeCode</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr id="primaryPurposeOtherText">
        <td   scope="row" class="label"><label><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();">
            <fmt:message key="isdesign.details.primary.purpose.other"/></dfn></label></td>
        <td>
            <s:textarea name="gtdDTO.primaryPurposeOtherText" cssStyle="width:300px" rows="2"/>
            <span class="formErrorMsg"> 
             <s:fielderror>
               <s:param>gtdDTO.primaryPurposeOtherText</s:param>
             </s:fielderror>                            
          </span>
        </td>
    </tr>
    <tr>
        <th colspan="2"> Lead Organization/Principal Investigator</th>
    </tr>
    
    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    Lead Organization
                 </dfn><span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/leadOrg.jsp" %>
            </div>      
        </td>
    </tr>

    <tr>
        <td scope="row" class="label">
           <label for="nciIdentifier">
                <dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> 
                    Principal Investigator
                 </dfn><span class="required">*</span>
           </label>
         </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/leadPI.jsp" %>
            </div>      
        </td>
    </tr>
    
    <tr>
         <th colspan="2">Sponsor/Responsible Party</th>
    </tr>          
    <tr>
        <td scope="row" class="label">
            <label for="sponsor"> Sponsor:<span class="required">*</span></label> 
        </td>
        <td class="value">
            <div id="loadSponsorField">
            <%@ include file="/WEB-INF/jsp/nodecorate/sponsor.jsp" %>
            </div>      
        </td>
    </tr>   
                <tr>
                <td scope="row" class="label">Responsible Party:<span class="required">*</span></td>
                <td>
                <input type="radio" name="gtdDTO.responsibleParty" value="pi"  onclick="manageRespPartyLookUp();"> PI 
                <input type="radio" name="gtdDTO.responsibleParty" value="sponsor" onclick="manageRespPartyLookUp();"> Sponsor
                </td>
                </tr>               
                <tr id="rpcid" style="display:none">
                <td scope="row" class="label">
                    <label for="responsiblepartycontact"> Responsible Party Contact :</label> 
                </td>               
                <td class="value">
                    <div id="loadResponsibleContactField">
                        <%@ include file="/WEB-INF/jsp/nodecorate/responsibleContact.jsp" %>
                    </div>                                    
                </td>
                </tr>
          <tr>
                <td scope="row" class="label">
                   Email Address:<span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield name="gtdDTO.contactEmail"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>contactEmail</s:param>
                       </s:fielderror>                            
                     </span>
                </td>
                </tr>
                <tr>
                <td scope="row" class="label">Phone Number:<span class="required">*</span></td>
                <td class="value">
                    <s:textfield name="gtdDTO.contactPhone"  maxlength="200" size="100"  cssStyle="width:200px" />
                    <span class="formErrorMsg"> 
                        <s:fielderror>
                        <s:param>contactPhone</s:param>
                       </s:fielderror>                            
                     </span>
                </td>           
          </tr>             
    
    <tr>
        <th colspan="2"> Summary 4 Information</th>
    </tr>

     <tr>
          <td scope="row" class="label">
               <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> <fmt:message key="studyProtocol.summaryFourFundingCategoryCode"/></dfn></label>
          </td>
          <s:set name="summaryFourFundingCategoryCodeValues" value="@gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode@getDisplayNames()" />
          <td class="value">
            <s:select headerKey="" headerValue="" 
                name="gtdDTO.summaryFourFundingCategoryCode" 
                list="#summaryFourFundingCategoryCodeValues"  
                value="gtdDTO.summaryFourFundingCategoryCode" 
                cssStyle="width:206px" />
          </td>                                 
     </tr>    
    <tr>
        <td scope="row" class="label">
            <label for="summary4TypeCode"><dfn title="Context sensitive help text or tooltip here." onmouseover="tooltip();"> Summary 4 Funding Source:</dfn></label>
        </td>
        <td class="value">
            <div id="loadOrgField">
            <%@ include file="/WEB-INF/jsp/nodecorate/summFour.jsp" %>
            </div>      
        </td>
        
    </tr>

    
    </table>  
         <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><a href="#" class="btn" onclick="handleAction();"><span class="btn_img"><span class="save">Save</span></span></a></li>
                </ul>   
            </del>

        </div>          

    </s:form>
   </div>

 </body>
 </html>