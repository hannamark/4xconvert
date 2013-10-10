<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:url value="/protected/popuplookuporgs.action" var="lookupOrgUrl"/>
<c:url value="/protected/popuplookuppersons.action" var="lookupPersUrl"/>
<table>
	<tr>
		<th colspan="2"><fmt:message key="submit.trial.sponsorResParty" /></th>
	</tr>
	<tr>
		<td colspan="2" class="space">&nbsp;</td>
	</tr>
	<tr>
		<td scope="row" class="label"><reg-web:displayTooltip
				tooltip="tooltip.sponsor">
				<label for="trialDTO.sponsorName"> Sponsor:<span
					class="required">*</span></label>
			</reg-web:displayTooltip></td>
		<td class="value">
			<div id="loadSponsorField">
				<%@ include file="/WEB-INF/jsp/nodecorate/trialSponsor.jsp"%>
			</div>
		</td>
	</tr>
	<tr>
		<td scope="row" class="label-noinput"><reg-web:displayTooltip
				tooltip="tooltip.responsible_party">
				<label for="trialDTO.responsiblePartyType"><fmt:message key="submit.trial.responsibleParty" /></label>
				<span class="required">*</span>
			</reg-web:displayTooltip></td>
		<td><s:select onchange="respPartyTypeChanged()"
		        headerKey=""
                headerValue="--Select--"
				id="trialDTO.responsiblePartyType"
				name="trialDTO.responsiblePartyType"
				list="#{'sponsor':'Sponsor','pi':'Principal Investigator','si':'Sponsor-Investigator'}"
				value="trialDTO.responsiblePartyType" cssStyle="width:206px" />
			<span class="formErrorMsg"> <s:fielderror>
					<s:param>trialDTO.responsiblePartyType</s:param>
				</s:fielderror>
		</span></td>
	</tr>
	
	<tr style="display:none" class="resppartysection">
        <td scope="row" class="label">
            <label for="trialDTO.responsiblePersonName">Investigator:<span class="required">*</span></label> 
        </td>               
        <td class="value">
            <div id="loadResponsibleContactField">
                <s:hidden name="trialDTO.responsiblePersonIdentifier" id="trialDTO.responsiblePersonIdentifier"/>
                <table>
                    <tr>
                        <td>
                            <s:textfield name="trialDTO.responsiblePersonName" id="trialDTO.responsiblePersonName" size="30"  
                                cssStyle="width:200px; " readonly="true" cssClass="readonly"/>                            
                        </td>
                        <td id="investigatorlookupcell"> 
                          <ul style="margin-top:-1px;">             
                                <li style="padding-left:0"><a href="javascript:void(0)" class="btn" onclick="lookupInvestigator();">
                                <span class="btn_img"><span class="person">Look Up Person</span></span></a></li>
                          </ul>
                        </td>
                    </tr>
                </table>
                <span class="formErrorMsg" id="responsiblePersonNameErr"> 
                    <s:fielderror>
                    <s:param>responsiblePersonName</s:param>
                    </s:fielderror>                            
                </span>
            </div>                                    
        </td>
    </tr>
        
    <tr style="display:none" class="resppartysection">
        <td scope="row" class="label">
           <label for="trialDTO.responsiblePersonTitle">Investigator Title:<span class="required">*</span></label> 
        </td>
        <td class="value">
            <s:textfield name="trialDTO.responsiblePersonTitle"  id="trialDTO.responsiblePersonTitle" maxlength="200" cssStyle="width:200px; " />
            <span class="formErrorMsg"> 
                <s:fielderror>
                <s:param>responsiblePersonTitle</s:param>
               </s:fielderror>                            
             </span>
        </td>
    </tr>
    
    <tr style="display:none" class="resppartysection">
        <td scope="row" class="label">
            <label for="trialDTO.responsiblePersonAffiliationOrgName">Investigator Affiliation:<span class="required">*</span></label> 
        </td>               
        <td class="value">
            <div id="investigatorAffiliationDiv">
                <s:hidden name="trialDTO.responsiblePersonAffiliationOrgId" id="trialDTO.responsiblePersonAffiliationOrgId"/>
                <table>
                    <tr>
                        <td>
                            <s:textfield name="trialDTO.responsiblePersonAffiliationOrgName" id="trialDTO.responsiblePersonAffiliationOrgName" size="30"  
                                cssStyle="width:200px; " readonly="true" cssClass="readonly"/>                            
                        </td>
                        <td id="affiliationLookupcell"> 
                              <ul style="margin-top:-1px;">
                                <li style="padding-left:0">
                                    <a href="javascript:void(0)" class="btn" onclick="lookupAffiliation();">
                                        <span class="btn_img"><span class="organization">Look Up Organization</span></span>
                                    </a>
                                </li>
                              </ul>
                        </td>
                    </tr>
                </table>
                <span class="formErrorMsg" id="affiliationOrgErr"> 
                    <s:fielderror>
                    <s:param>responsiblePersonAffiliationOrgName</s:param>
                    </s:fielderror>                            
                </span>
            </div>                                    
        </td>
    </tr> 
</table>

<script type="text/javascript">
      
      Event.observe(window, "load", prepareResponsiblePartySection);
      
      function prepareResponsiblePartySection() {
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='' || partyType=='sponsor') {
              $$('tr.resppartysection').each(function(el) {el.hide()});
          } else {
              $$('tr.resppartysection').each(function(el) {el.show()});
          }
          if (partyType=='pi') {                
              $('investigatorlookupcell').hide();
              $('affiliationLookupcell').show();
          }
          if (partyType=='si') {                
              $('investigatorlookupcell').show();
              $('affiliationLookupcell').hide();
          }
      }
      
      function respPartyTypeChanged() {
          
          $('responsiblePersonNameErr').innerHTML='';
          
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='' || partyType=='sponsor') {
              $('trialDTO.responsiblePersonIdentifier').value='';
              $('trialDTO.responsiblePersonName').value='';
              $('trialDTO.responsiblePersonTitle').value='';
              $('trialDTO.responsiblePersonAffiliationOrgId').value='';
              $('trialDTO.responsiblePersonAffiliationOrgName').value='';
              $('responsiblePersonNameErr').innerHTML='';
              $('affiliationOrgErr').innerHTML='';
          } else if (partyType=='pi') {   
        	  $('affiliationOrgErr').innerHTML='';
              if ($F('trialDTO.piIdentifier')=='') {
                  $('responsiblePersonNameErr').innerHTML='This field cannot be edited. Please select a Principal Investigator in the section above.';
                  $('piIdentifierErr').innerHTML='Please select a Principal Investigator.';
              } 
              copyPiAsRespParty();            
              
              if ($F('trialDTO.responsiblePersonAffiliationOrgId')=='') {
                  copySponsorAsAffiliation();
              }
              if ($F('trialDTO.responsiblePersonTitle')=='') {
                  $('trialDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          } else if (partyType=='si') {    
              if ($F('trialDTO.responsiblePersonIdentifier')=='') {
                  copyPiAsRespParty();
              }
              
              if ($F('trialDTO.sponsorIdentifier')=='') {
                  $('affiliationOrgErr').innerHTML='The affiliation cannot be edited. Please select a Sponsor above.';
                  $('sponsorErr').innerHTML='Please select a Sponsor.';
              }
              copySponsorAsAffiliation();
              
              if ($F('trialDTO.responsiblePersonTitle')=='') {
                  $('trialDTO.responsiblePersonTitle').value='Principal Investigator';
              }
          }
          prepareResponsiblePartySection();
      }
      
      function copyPiAsRespParty() {
          $('trialDTO.responsiblePersonIdentifier').value=$F('trialDTO.piIdentifier');
          $('trialDTO.responsiblePersonName').value=$F('trialDTO.piName');
      }
      
      function copySponsorAsAffiliation() {
          $('trialDTO.responsiblePersonAffiliationOrgId').value=$F('trialDTO.sponsorIdentifier');
          $('trialDTO.responsiblePersonAffiliationOrgName').value=$F('trialDTO.sponsorName');
      }
      
      function lookupInvestigator() {
          showPopup('${lookupPersUrl}', updateInvestigatorFieldsWithSelection, 'Select Investigator');
      }
      
      function updateInvestigatorFieldsWithSelection() {         
          $('trialDTO.responsiblePersonIdentifier').value = persid;
          $('trialDTO.responsiblePersonName').value = chosenname;
      }
      
      function lookupAffiliation() {
          showPopup('${lookupOrgUrl}', updateAffiliationWithSelection, 'Select Affiliation Organization');
      }
      
      function updateAffiliationWithSelection() {         
          $('trialDTO.responsiblePersonAffiliationOrgId').value = orgid;
          $('trialDTO.responsiblePersonAffiliationOrgName').value=chosenname;
      }
      
      
</script>
