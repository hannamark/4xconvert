<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
<c:url value="/protected/popupCadsr.action" var="lookupUrl" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
    <head>
        <title>
            <c:choose>
                <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
                    <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
                </c:when>
                <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise>
                </c:choose>
        </title>
        <s:head />
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/tooltip.js"/>"></script>
        <script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/ajaxHelper.js'/>"></script>
    
        <c:url value="/protected/popupTypeInterventiontype.action?className=UnitOfMeasurement&divName=loadUOMDetails" var="lookupUOMUrl" />
        <script language="javascript" type="text/javascript">
            // this function is called from body onload in main.jsp (decorator)
            function callOnloadFunctions() {
                setFocusToFirstControl();
                var len = document.eligibilityCriteraiAdd.group3.length;
                var selElement;
                for (i = 0; i < len; i++)
                 {
                 if(document.eligibilityCriteraiAdd.group3[i].checked){
                   selElement = document.eligibilityCriteraiAdd.group3[i];
                   }
                 }  
                activate(selElement);
                activateMax();    
            }

            function lookup() {
                showPopWin('${lookupUrl}', 900, 400, '', 'caDSR Lookup');
            } 
            
            function handleAction() {
                var page = document.forms[0].page.value;
                var form = document.forms["eligibilityCriteraiAdd"];
                var UOM = form.elements["webDTO.unit"].value;
                if (UOM == '' || UOM == 'undefined') {
                    input_uom_box = confirm("Criterion does not include UOM.  Do you want to save criterion anyway?");
                    if (input_uom_box == true){
                        if (page == "Edit"){
                            form.action="eligibilityCriteriaupdate.action";
                        } else {
                            form.action="eligibilityCriteriacreate.action";
                        } 
                        form.submit();
                     }
                } else {
                    if (page == "Edit") {
                        form.action="eligibilityCriteriaupdate.action";
                    } else {
                        form.action="eligibilityCriteriacreate.action";
                    } 
                    form.submit();
                }
            } 

            function activate(selected) {
                var input = "webDTO.textDescription";
                var form = document.forms["eligibilityCriteraiAdd"];
                var inputElement = form.elements[input];
                var criterionName = "webDTO.criterionName";
                var cnElement = document.forms["eligibilityCriteraiAdd"].elements[criterionName];
                var operator = "webDTO.operator";
                var opElement = document.forms["eligibilityCriteraiAdd"].elements[operator];
                var min = "webDTO.valueIntegerMin";
                var minElement = "";
                var max = "webDTO.valueIntegerMax";
                var maxElement = "";
                var txt = "webDTO.valueText";
                var txtElement = "";

                if (form.elements[min] != 'undefined' && form.elements[min] != null) {
                    minElement = form.elements[min];
                }
                if (form.elements[max] != 'undefined' && form.elements[max] != null) {
                    maxElement = form.elements[max];
                }
                if (form.elements[txt] !='undefined' &&  form.elements[txt] != null) {
                    txtElement = form.elements[txt];
                }
                var unit="webDTO.unit";
                var uElement = form.elements[unit];
                if (selected.value == 'Unstructured')
                    {
                        cnElement.disabled = true;
                        opElement.disabled = true;
                        if (minElement != "") {
                         minElement.disabled = true;
                        } 
                        if (maxElement != "") {
                         maxElement.disabled = true;
                        } 
                        if (txtElement != "") {
                         txtElement.disabled = true;
                        } 
                        if (uElement != "") {
                         uElement.disabled = true;
                        } 
                        inputElement.disabled=false;
                        $("loadUOMDetails").disabled=true;
                        $("criteriaNameLookup").disabled=true;
                        $("generateTextButton").disabled=true;
                        
                    } else {
                        cnElement.disabled = false;
                        opElement.disabled = false;
                        if (minElement != 'undefined'|| minElement != null) {
                            minElement.disabled = false;
                        } 
                        if (maxElement != 'undefined'|| maxElement != null) {
                           maxElement.disabled = false;
                        } 
                        if (txtElement != 'undefined'|| txtElement != null) {
                            txtElement.disabled = false;
                        } 
                        if (uElement != 'undefined'|| uElement != null) {
                           uElement.disabled = false;
                        } 
                        inputElement.disabled=false;
                        $("loadUOMDetails").disabled=false;
                        $("criteriaNameLookup").disabled=false;
                        $("generateTextButton").disabled=false;
                    }   
                }

            function tooltip() {
                BubbleTips.activateTipOn("acronym");
                BubbleTips.activateTipOn("dfn"); 
            }

            function lookupUOM() {
                showPopWin('${lookupUOMUrl}', 900, 400, '', 'Unit Of Measure');
            }

            function loadDetails(id, divName,className) {
                var url = '/pa/protected/ajaxEligibilityCriteriadisplaySelectedType.action';
                var params = {
                    className: className,
                    divName: divName,
                    id: id
                };
                var div = $(divName);   
                div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';
                var aj = callAjaxPost(div, url, params);
            }
               
            function handleGenerateCriteriaText() {
                document.forms[0].action="eligibilityCriteriagenerate.action";
                document.forms[0].submit();
            }   
            
            function loadDiv(deid) {
                 window.top.hidePopWin(true);
                 var url = '/pa/protected/ajaxEligibilityCriteriadisplaycde.action';
                 var params = { cdeid: deid };
                 var div = $('eligibility.build.criterion');   
                 div.innerHTML = '<div align="left"><img  src="../images/loading.gif"/>&nbsp;Loading...</div>';    
                 var aj = callAjaxPost(div, url, params);
            }
             
            function activateMax() {
                var operate = "webDTO.operator";
                var operateElement = document.forms["eligibilityCriteraiAdd"].elements[operate];
                var intMaxName = "webDTO.valueIntegerMax";
                var intMaxElement = document.forms["eligibilityCriteraiAdd"].elements[intMaxName];
                intMaxElement.disabled = operateElement.value != 'in';
            }  
            
        </script>
    </head>
    <body>
        <c:set var="topic" scope="request" value="abstracteligibility"/>
        <h1>
            <c:choose>
                <c:when test="${sessionScope.trialSummary.studyProtocolType  == 'ObservationalStudyProtocol'}">
                    <fmt:message key="osdesign.eligibilitycriteria.webtitle"/>
                </c:when>
                <c:otherwise><fmt:message key="isdesign.eligibilitycriteria.webtitle"/></c:otherwise>
            </c:choose>
        </h1>
        <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
        <div class="box">
            <pa:sucessMessage/>
            <pa:failureMessage/>
            <s:form id="eligibilityCriteraiAdd" name="eligibilityCriteraiAdd">
                <s:token/>
                <s:actionerror/>
                <pa:studyUniqueToken/>
                <h2><fmt:message key="isdesign.eligibilitycriteria.subtitle"/></h2>
                <s:hidden name="page"/>
                <s:hidden name="id"/>
                <table class="form">
                    <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriatype"/></label>
                        </td>
                        <td class="value">
                            <s:select name="webDTO.inclusionIndicator" list="#{' ':' ', 'Exclusion':'Exclusion', 'Inclusion':'Inclusion'}" cssStyle="width:106px"/>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>webDTO.inclusionIndicator</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="isdesign.eligibilitycriteria.structuredOrUnstructured"/><span class="required">*</span>:</label>
                        </td>
                        <td class="value">
                            <s:if test="%{webDTO.structuredType == 'Structured'}">
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  checked="checked" onclick='activate(this)'/>Structured<br/>
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" onclick='activate(this)'/>Un-structured
                            </s:if>
                            <s:elseif test="%{webDTO.structuredType == 'Unstructured'}">
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Structured"  onclick='activate(this)'/>Structured<br/>
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" checked="checked" onclick='activate(this)'/>Un-structured
                            </s:elseif>
                            <s:else>
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Structured" onclick='activate(this)' />Structured<br/>
                                <input type="radio" id="group3" name="webDTO.structuredType" value="Unstructured" onclick='activate(this)'/>Un-structured
                            </s:else>
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>webDTO.structuredType</s:param>
                               </s:fielderror>
                            </span>
                        </td>
                    </tr> 
                    <tr>
                        <td scope="row"  class="label"><label>
                            <fmt:message key="isdesign.eligibilitycriteria.eligibilitycriteriaDisplayOrder"/><span class="required">*</span></label>
                        </td>
                        <td class="value">
                            <s:textfield name="webDTO.displayOrder" maxlength="3" cssStyle="width:150px" />
                            <span class="formErrorMsg"> 
                                <s:fielderror>
                                    <s:param>webDTO.displayOrder</s:param>
                                </s:fielderror>
                            </span>
                        </td>
                    </tr>           
                </table>
                <div id="eligibility.build.criterion">
                        <jsp:include page="/WEB-INF/jsp/nodecorate/eligibilityBuildCriterion.jsp" />
                </div>
                <div class="actionsrow">
                    <del class="btnwrapper">
                        <ul class="btnrow">
                            <li><s:a href="javascript:void(0)" cssClass="btn" onclick="handleAction()"><span class="btn_img"><span class="save">Save</span></span></s:a></li>                
                        </ul>   
                    </del>
                </div> 
            </s:form>
        </div>
    </body>
</html>
