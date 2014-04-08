      var orgid;
      var chosenname;
      var p30GrantSerialNumber;
      var persid;
      var respartOrgid;
      var contactMail;
      var contactPhone;
      var countOfOtherIdentifiers = 0;

      jQuery(function() {
      	//TODO : Enable this function after bringing in jquery-ui*.js support for ajax calls
      	
          /*jQuery("#serialNumber").autocomplete({delay: 250,
                source: function(req, responseFn) {
                  var instCode = jQuery("#nihInstitutionCode").val();
                  if ('CA' != instCode) {
                      return;
                  }
                  var url = registryApp.contextPath + '/ctro/json/ajaxI2EGrantsloadSerialNumbers.action?serialNumberMatchTerm=' + req.term;
                  jQuery.getJSON(url,null,function(data){
                         responseFn(jQuery.map(data.serialNumbers, function (value, key) { 
                              return {
                                  label: value,
                                  value: key
                              };
                         }));
                  });
              }
          });*/
      });

      function countLeft(field, count, max) {
          // if the length of the string in the input field is greater than the max value, trim it 
          if ($(field).value.length > max)
          	$(field).value = $(field).value.substring(0, max);
          else
          // calculate the remaining characters  
          $(count).value = max - $(field).value.length;
      }
     
      
      function setorgid(orgIdentifier, oname, p30grant) {
          orgid = orgIdentifier;
          chosenname = oname.replace(/&apos;/g,"'");
          p30GrantSerialNumber = p30grant;
      }
      
      function setpersid(persIdentifier, sname, email, phone) {
          persid = persIdentifier;
          chosenname = sname.replace(/&apos;/g,"'");
          contactMail = email;
          contactPhone = phone;
      }
      
      function lookup4loadleadorg() {
          showPopup('${lookupOrgUrl}',loadLeadOrgDiv, 'Select Lead Organization');
      }
      
      function lookup4loadleadpers() {
          showPopup('${lookupPersUrl}', loadLeadPersDiv, 'Select Principal Investigator');
      }
      
      function lookup4sponsor() {
          showPopup('${lookupOrgUrl}', loadSponsorDiv, 'Select Sponsor');
      }
      
      function lookup4loadSummary4Sponsor() {
          showPopup('${lookupOrgUrl}', loadSummary4SponsorDiv, 'Select Summary 4 Sponsor/Source');
      }
      
      function loadLeadOrgDiv() {
          $("trialDTO.leadOrganizationIdentifier").value = orgid;
          $('trialDTO.leadOrganizationName').value = chosenname;
          deleteP30Grants();
          if( p30GrantSerialNumber){
              var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
              var params = {
                  fundingMechanismCode: 'P30',
                  nciDivisionProgramCode: 'OD',
                  nihInstitutionCode: 'CA',
                  serialNumber: p30GrantSerialNumber
              };
              var div = $('grantdiv');
              div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
              var aj = callAjaxPost(div, url, params);
              resetGrantRow();
          } 
      }
      
      function loadLeadPersDiv() {
          $("trialDTO.piIdentifier").value = persid;
          $('trialDTO.piName').value = chosenname;
          
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='pi') {                
              $('trialDTO.responsiblePersonIdentifier').value=persid;
              $('trialDTO.responsiblePersonName').value=chosenname;
          }
      }
      
      function loadSponsorDiv() {
          $("trialDTO.sponsorIdentifier").value = orgid;
          $('trialDTO.sponsorName').value = chosenname;                             
          respartOrgid = orgid;
          
          var partyType = $F('trialDTO.responsiblePartyType');
          if (partyType=='si') {                
               $('trialDTO.responsiblePersonAffiliationOrgId').value=orgid;
               $('trialDTO.responsiblePersonAffiliationOrgName').value=chosenname;
          }
      }
     
      function loadSummary4SponsorDiv() {
          var url = '/registry/protected/popupaddSummaryFourOrg.action';
          var params = { orgId: orgid, chosenName : chosenname };
          var div = document.getElementById('loadSummary4FundingSponsorField');   
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
          var aj = callAjaxPost(div, url, params);
      }

      function deleteSummary4SponsorRow(rowid) {
          var  url = '/registry/protected/popupdeleteSummaryFourOrg.action';
          var params = { uuid: rowid };
          var div = $('loadSummary4FundingSponsorField');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
          var aj = callAjaxPost(div, url, params);
      }
      
      function reviewProtocol () {
          submitFirstForm("review", "submitTrialreview.action");
      }
      
      function partialSave() {
          submitFirstForm(null, "submitTrialpartialSave.action");
      }
      
      function cancelProtocol() {
          var retVal = confirm("All data entered will be lost. Do you want to continue ?");
          if( retVal == true ){
              submitFirstForm("Submit", "submitTrialcancel.action");
              return true;
          }else{
              return false;
          } 
      }
      
      function submitFirstForm(value, action) {
          var form = document.forms[0];
          if (value != null) {
              form.page.value = value;
          }
          form.action = action;
          form.submit();
      }
      
      function addGrant() {
          var fundingMechanismCode = $('fundingMechanismCode').value;
          var nihInstitutionCode = $('nihInstitutionCode').value;
          var nciDivisionProgramCode = $('nciDivisionProgramCode').value;
          var serialNumber = $('serialNumber').value;
          serialNumber = trim(serialNumber);
          var fundingPercent =  $('fundingPercent').value;
          var isValidGrant;
          var isSerialEmpty = false;
          var alertMessage = "";
          if (fundingMechanismCode.length == 0 || fundingMechanismCode == null) {
              isValidGrant = false;
              alertMessage="Please select a Funding Mechanism";
          }
          if (nihInstitutionCode.length == 0 || nihInstitutionCode == null) {
              isValidGrant = false;
              alertMessage=alertMessage+ "\n Please select an Institute Code";
          }
          if (serialNumber.length == 0 || serialNumber == null) {
              isValidGrant = false;
              isSerialEmpty = true;
              alertMessage=alertMessage+ "\n Please enter a Serial Number";
          }
          if (nciDivisionProgramCode.length == 0 || nciDivisionProgramCode == null) {
              isValidGrant = false;
              alertMessage=alertMessage+ "\n Please select a NCI Division/Program Code";
          }
          if (isSerialEmpty == false && isNaN(serialNumber)){
              isValidGrant = false;
              alertMessage=alertMessage+ "\n Serial Number must be numeric";
          } else if (isSerialEmpty == false && serialNumber != null) {
                     var numericExpression = /^[0-9]+$/;
                     if (!numericExpression.test(serialNumber)) {
                         isValidGrant = false;
                         alertMessage=alertMessage+ "\n Serial Number must be numeric";
                     }
          }
          if (fundingPercent.length != 0 && fundingPercent != null) {
              if (isNaN(fundingPercent)){
                  isValidGrant = false;
                  alertMessage=alertMessage+ "\n % of Grant Funding must be numeric";
              } else if(Number(fundingPercent) > 100.0 || Number(fundingPercent) < 0.0){
                  isValidGrant = false;
                  alertMessage=alertMessage+ "\n % of Grant Funding must be positive and <= 100";
              }
          }
          if (isValidGrant == false) {
              alert(alertMessage);
              return false;
          }
          var  url = '/registry/protected/ajaxManageGrantsActionaddGrant.action';
          var params = {
              fundingMechanismCode: fundingMechanismCode,
              nciDivisionProgramCode: nciDivisionProgramCode,
              nihInstitutionCode: nihInstitutionCode,
              serialNumber: serialNumber,
              fundingPercent: fundingPercent
          };
          var div = $('grantdiv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
          var aj = callAjaxPost(div, url, params);
          resetGrantRow();
      }

      function deleteGrantRow(rowid) {
          var  url = '/registry/protected/ajaxManageGrantsActiondeleteGrant.action';
          var params = { uuid: rowid };
          var div = $('grantdiv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
          var aj = callAjaxPost(div, url, params);
      }

      function deleteP30Grants(rowid) {
          var  url = '/registry/protected/ajaxManageGrantsActiondeleteP30Grants.action';
          var div = $('grantdiv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
          var aj = callAjaxPost(div, url, null);
      }

      function deleteOtherIdentifierRow(rowid) {
          var  url = '/registry/protected/ajaxManageOtherIdentifiersActiondeleteOtherIdentifier.action';
          var params = { uuid: rowid };
          var div = $('otherIdentifierdiv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
          var aj = callAjaxPost(div, url, params);
      }
      
      function resetGrantRow() {
          $('fundingMechanismCode').value = '';
          $('nihInstitutionCode').value = '';
          $('serialNumber').value = '';
          $('nciDivisionProgramCode').value = '';
          $('fundingPercent').value = '';
      }
      
      function deleteIndIde(rowid) {
          var  url = '/registry/protected/ajaxManageIndIdeActiondeleteIndIde.action';
          var params = { uuid: rowid };
          var div = $('indidediv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Deleting...</div>';
          var aj = callAjaxPost(div, url, params);
      }
      
      function addIndIde(indIde, number, grantor, holderType, programCode, expandedAccess, expandedAccessType, exemptIndicator) {
          var  url = '/registry/protected/ajaxManageIndIdeActionaddIdeIndIndicator.action';
          var params = {
              exemptIndicator: exemptIndicator,
              expandedAccess: expandedAccess,
              expandedAccessType: expandedAccessType,
              grantor: grantor,
              holderType: holderType,
              indIde: indIde,
              number: number,
              programCode: programCode
          };
          var div = $('indidediv');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
          var aj = callAjaxPost(div, url, params);
          resetValues();
      }
      
      function loadRegAuthoritiesDiv() {
          var url = '/registry/protected/ajaxgetOAuthOrgsgetTrialOversightAuthorityOrganizationNameList.action';
          var params = { countryid: $('countries').value };
          var div = $('loadAuthField');
          div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
          var aj = callAjaxPost(div, url, params);
          return false;
      }
      
      function checkFDADropDown() {
          if ($('trialDTO.fdaRegulatoryInformationIndicator').value == ''| $('trialDTO.fdaRegulatoryInformationIndicator').value == 'No') {
              input_box = confirm("Section 801 and Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
              if (input_box == true) {
                  $('trialDTO.section801Indicator').value = '';
                  $('trialDTO.delayedPostingIndicator').value = '';
                  hideRow($('sec801row'));
                  hideRow($('delpostindrow'));
              } else {
                  $('trialDTO.fdaRegulatoryInformationIndicator').value = 'Yes';
              }
          } else {
              showRow($('sec801row'));
              showRow($('delpostindrow'));
          }
      }
  
      function checkSection108DropDown() {
          if ($('trialDTO.section801Indicator').value == '' | $('trialDTO.section801Indicator').value == 'No') {
              input_box = confirm("Delayed Posting Indicator will be NULLIFIED? \nPlease Click OK to continue or Cancel");
              if (input_box == true) {
                  hideRow($('delpostindrow'));
                  $('trialDTO.delayedPostingIndicator').value = '';
              } else {
                  $('trialDTO.section801Indicator').value = 'Yes';
              }
          } else {
              showRow($('delpostindrow'));
          }
      }
      
      function hideRow(row) {
          row.style.display = 'none';
      }
      
      function showRow(row) {
          row.style.display = '';
      }
      
      function addOtherIdentifier() {
          var orgValue=trim($("otherIdentifierOrg").value);
          if (orgValue != null && orgValue != '') {
              var  url = '/registry/protected/ajaxManageOtherIdentifiersActionaddOtherIdentifier.action';
              var params = { otherIdentifier: orgValue };
              var div = $('otherIdentifierdiv');
              div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Adding...</div>';
              var aj = callAjaxPost(div, url, params);
              $("otherIdentifierOrg").value = "";
          } else {
              alert("Please enter a valid Other identifier to add");
          }
      }

      
  