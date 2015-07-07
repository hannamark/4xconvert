 <%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
 <h2><fmt:message key="reportCoverSheet.finalRecordCleanup.title" /></h2>
   <table class="form" style="width:100%">                
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.standardlanguage.label"/></label>
       </td>
       <td>
         <div class="col-xs-4">
         <s:select id="useStandardLanguage" name="useStandardLanguage"
                    list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control"
                      />
                    <span class="formErrorMsg">
                       <s:fielderror>
                       <s:param>studyProtocolDTO.useStandardLanguage</s:param>
                      </s:fielderror>
                    </span>
        </div>
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.dateEnteredInPrs.label"/></label>
       </td>
       <td>
        <s:select id="dateEnteredInPrs"   name="dateEnteredInPrs"
                      list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control"
                      />
                  
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label><fmt:message key="reportCoverSheet.finalRecordCleanup.results.accessRevoked.label"/></label>
       </td>
       <td>
         <s:select id="designeeAccessRevoked" name="designeeAccessRevoked" list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control"
                      />
            
          <s:textfield id="designeeAccessRevokedDate"  name="designeeAccessRevokedDate" maxlength="10" size="10" class="dateField" readonly="true" ></s:textfield>       
                   <a href="javascript:showCal('Cal3')" align="right"> <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a>           
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.results.changes.ctrpandctgov.label"/></label>
       </td>
       <td>
      <s:select id="changesInCtrpCtGov" name="changesInCtrpCtGov"  list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control"
                      />
         
        <s:textfield id="changesInCtrpCtGovDate"  name="changesInCtrpCtGovDate" maxlength="10" size="10" class="dateField" readonly="true" ></s:textfield>           
         <a href="javascript:showCal('Cal4')" align="right"> <img src="${pageContext.request.contextPath}/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                                    </a>                       
       </td>
     </tr>
     <tr>
        <td scope="row" class="label">
          <label ><fmt:message key="reportCoverSheet.finalRecordCleanup.results.trialinfo.ToCtGov.label"/></label>
       </td>
       <td>
       <s:select id="sendToCtGovUpdated" name="sendToCtGovUpdated"
                      list="#{'false':'No', 'true':'Yes'}" 
                      cssClass="form-control"
                      />
                    <span class="alert-danger">
                       <s:fielderror>
                       <s:param>studyProtocolDTO.sendToCtGovUpdated</s:param>
                      </s:fielderror>
                    </span>
       </td>
     </tr>
     </table>
     <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li><s:a href="javascript:void(0)" cssClass="btn" onclick="submitFinalChanges('resultsReportingCoverSheetsaveFinalChanges.action')" id="saveFinal"><span class="btn_img"><span class="save">Save</span></span></s:a>
                    
                    </li>                
                </ul>   
            </del>
        </div>          