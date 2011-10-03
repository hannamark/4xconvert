<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<c:set var="topic" scope="request" value="run_summ4_types"/> 
<head>
<title><fmt:message key="summ4Rep.header" /></title>
<s:head />
<script type="text/javascript" src="<c:url value="/scripts/js/cal2.js"/>"></script>
<script type="text/javascript">
addCalendar("Cal1", "Select Date", "criteria.intervalStartDate", "criteria");
addCalendar("Cal2", "Select Date", "criteria.intervalEndDate", "criteria");
setWidth(90, 1, 15, 1);
setFormat("mm/dd/yyyy");

function handleAction(){
    document.forms[0].action="resultsSumm4Rep.action";
    document.forms[0].submit();
}
function handleReset(){
    document.forms[0].action="criteriaSumm4Rep.action";
    document.forms[0].submit();
}
function loadOrgsDiv() { 
    var familyId = $('familyId').value;
    var div = $('organization_choices');
    if (familyId == "") {
        div.innerHTML = "";
        return false;
    }
    var url = '/viewer/ctro/ajax/refreshOrganizationsSumm4Rep.action';
    var params = { "criteria.familyId": familyId };
    div.innerHTML = '<div align="left"><img  src="/viewer/images/loading.gif"/>&nbsp;Loading...</div>';
    var aj = callAjaxPost(div, url, params);
    return false;
}
function selectAll(checkbox, selectBox) {
    var selectValue = false;
    if (checkbox.checked) {
        selectValue = true;
    }
    var selectBox = $(selectBox);
    for (var i = 0; i < selectBox.options.length; i++) {
        selectBox.options[i].selected = selectValue;
    }
}
function displaySearch() {
    var selected = $('criteria').getInputs('radio','orgSearchType').find(function(radio) { return radio.checked; });
    if (selected == undefined || selected.value == "Find by Org Name") {
        $('familyId').value = "";
        $('organization_choices').innerHTML = "";
        $('familyId').disabled = true;
        $('autocomplete').disabled = false;
    } else {
        $('familyId').disabled = false;
        $('autocomplete').value = "";
        $('autocomplete').disabled = true;
    }
}

</script>
</head>
<body>
<!-- main content begins-->
    <h1><fmt:message key="summ4Rep.header"/></h1>
    <s:form name="criteria" id="criteria">
        <table class="form">
            <s:if test="hasActionErrors()"><tr><td colspan="2"><div class="error_msg"><s:actionerror /></div></td></tr></s:if> 
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="report.dates"/></p>
            </td></tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalStartDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield id="intervalStartDate" name="criteria.intervalStartDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal1')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr> 
                <td class="label">
                    <label><fmt:message key="report.criteria.intervalEndDate"/></label>
                    <span class="required">*</span>
                </td>
                <td class="value">
                    <s:textfield id="intervalEndDate" name="criteria.intervalEndDate" maxlength="10" size="10" cssStyle="width:70px;float:left"/>
                    <a href="javascript:showCal('Cal2')">
                        <img src="<%=request.getContextPath()%>/images/ico_calendar.gif" alt="select date" class="calendaricon" />
                    </a>
                </td>
            </tr>
            <tr><td class="space"><br/></td></tr>
            <tr><td colspan="2">
                <p style="margin:0; padding:20"><fmt:message key="report.organizationName"/></p>
            </td></tr>
            <s:iterator value="orgSearchTypes" var="orgSearchType">
                <s:if test="#orgSearchType == 'Find by Org Name'">
                    <tr> 
                        <td class="label">
                            <label><s:radio name="orgSearchType" list="#orgSearchType" onchange="displaySearch()"/></label>
                        </td>
                        <td class="value">
                            <s:textfield 
                                id="autocomplete" name="criteria.orgName" maxlength="100" size="100" 
                                cssStyle="width:200px;float:left"/>
                                
                                <div id="autocomplete_choices" class="autocomplete"></div>            
                                <script type="text/javascript" language="javascript" charset="utf-8">
                                // <![CDATA[
                                  new Ajax.Autocompleter("autocomplete", 
                                		   "autocomplete_choices", 
                                           "/viewer/ctro/ajax/refreshAutocompleteResultsSumm4Rep.action",
                                           {minChars: 2});
                                // ]]>
                                </script>   
                        </td>
                    </tr>
                </s:if>
                <s:elseif test="#orgSearchType == 'Find by Family'">
                    <tr> 
                        <td class="label">
                            <label><s:radio name="orgSearchType" list="#orgSearchType" onchange="displaySearch()"/></label>
                        </td>
                        <td class="value">
                            <s:select id="familyId" name="criteria.familyId" list="families" onchange="loadOrgsDiv()" headerKey="" headerValue="--Select--"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="label">&nbsp;</td>
                        <td class="value">
                            <div id="organization_choices">
                                <jsp:include page="/WEB-INF/jsp/nodecorate/summ4RepOrganizationsResult.jsp"/>
                            </div>  
                        </td>
                    </tr>    
                </s:elseif>        
            </s:iterator>
        </table>
        <div class="actionsrow">
            <del class="btnwrapper">
                <ul class="btnrow">
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="search">Run report</span></span></s:a>  
                    </li>
                    <li>
                        <s:a href="#" cssClass="btn" onclick="handleReset()"><span class="btn_img"><span class="cancel">Reset</span></span></s:a>  
                    </li>
                </ul>   
            </del>
        </div>
        <script type="text/javascript">displaySearch();</script>
        <table width="100%">
            <tr><td colspan="2">
                <h2><fmt:message key="summ4Rep.header"/></h2>
            </td></tr>
            <tr>
                <td>
                    <fmt:message key="report.header.date1"/>
                    <s:label name="criteria.intervalStartDate"/>
                    <fmt:message key="report.header.date2"/>
                    <s:label name="criteria.intervalEndDate"/>
                </td>
                <td>
                    <fmt:message key="report.header.user"/>
                    <viewer:displayUser />
                </td>
            </tr>
            <tr><td colspan="2">
        <s:if test="%{resultList != null}">
             <table class="form">
     
               <tr><td colspan="2"><!--Tabs -->
                <ul id="maintabs" class="tabs">
                    <li><a href="#agent_device">Agent/Device</a></li>
                    <li><a href="#other_intervention">Other Intervention</a></li>
                    <li><a href="#epid_outcome">Epidemiologic/Other/Outcome</a></li>
                    <li><a href="#ancillary_correlative">Ancillary/Correlative</a></li>
                </ul>
            <!--/Tabs --> <!-- 
                    directions on http://livepipe.net/control/tabs 
                    - make sure you add control.tabs.js to your scripts directory! 
                    - Matt 
                --> <script type="text/javascript">             
                    //<![CDATA[
                    Event.observe(window,'load',function(){
                        $$('.tabs').each(function(tabs){
                            new Control.Tabs(tabs);
                        });
                    });
                    //]]>
                </script>
            <!-----------------------------------------------Begin Agent/Device Tab------------------->
            <div id="tabboxwrapper"><!--Facility-->
            <div id="agent_device" class="box">
            <s:form name="agent_device">
                <div id="loadAgentDeviceResults">
                    <%@ include file="/WEB-INF/jsp/nodecorate/summ4RepAgencyDeviceResult.jsp" %>
                </div>
            </s:form>
            </div>
            <!-----------------------------------------------End Agent/Device Tab------------------->
            <!-----------------------------------------------Begin Other Intervention Tab------------------->
            <div id="other_intervention" class="box" style="display:none;">
                 <s:form name="other_intervention">
                    <div id="loadOtherInterventionResults">
                        <%@ include file="/WEB-INF/jsp/nodecorate/summ4RepOtherInterventionResult.jsp" %>      
                    </div>
                 </s:form>
            </div>
          <!-----------------------------------------------End Other Intervention Tab------------------->
          <!-----------------------------------------------Begin Epid Other Outcome Tab------------------->
           <div id="epid_outcome" class="box" style="display:none;">                        
                <s:form name="epid_outcome">
                    <div id="loadEpidOutcomeResults">
                      <%@ include file="/WEB-INF/jsp/nodecorate/summ4RepEpidemOutcomeResult.jsp" %>
                    </div>
                </s:form>
            </div>
          <!-----------------------------------------------End Epid Other Outcome Tab------------------->
          <!-----------------------------------------------Begin Ancillary Correlative Tab------------------->
           <div id="ancillary_correlative" class="box" style="display:none;">                        
                <s:form name="ancillary_correlative">
                    <div id="loadAncillaryCorrelativeResults">
                        <%@ include file="/WEB-INF/jsp/nodecorate/summ4RepAncillaryCorrelativeResult.jsp" %>                        
                    </div>
                </s:form>
            </div>
          <!-----------------------------------------------End Ancillary Correlative Tab------------------->
            </div>
            </td></tr>
           
        </table>
        </s:if>
        </td></tr></table>
    </s:form>
</body>
