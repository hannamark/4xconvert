 <!DOCTYPE html PUBLIC
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title><fmt:message key="trialDocument.title"/></title>
	<s:head />
</head>
<SCRIPT LANGUAGE="JavaScript">
// this function is called from body onload in main.jsp (decorator)
function callOnloadFunctions(){
    // there are no onload functions to call for this jsp
    // leave this function to prevent 'error on page'
}

function handleMultiDeleteResultReporting(confirmationMessage, submitAction) {
	   
    var atLeastOne = false;
    var input_box = false;
    
  var isResultDeleted = false;
  var isComparisonPresent = false;
    
    $(document.forms[0]).getInputs().each(function(el) {
        if (el.name=='objectsToDelete' && el.checked) {
            atLeastOne = true;
            var elementIdString =el.id;
            var tokens = elementIdString.split("_");
            if(tokens.length==3) {
            	var type =tokens[2];
            	//check if one of the result documents is deleted
            	if(type=="Before results" || type== "After results") {
            		isResultDeleted = true;
            	}
            	
            	//check if comparison document is also delete
            	if(type=="Comparison") {
            		isComparisonPresent = true;
                }
            	
            }
           
        }
    });
    

    if (atLeastOne) {
        input_box = confirmationMessage!=''?confirm(confirmationMessage):true;
    }
    
    if (input_box || !atLeastOne) {
        if (document.forms[0].page!=undefined) {     
            document.forms[0].page.value = "Delete";
        }
        
        //display alert indicating comparison will be deleted if 
        //user is not already deleting comparison document
        if(isResultDeleted ) {
         
        	if(!isComparisonPresent) {
        		var isProceed =  confirm("If \"Before results\" or \"After results\" documents is removed the \"Comparison document\" will also be removed. Do you wish to proceed?");
        		if (!isProceed) {
        			return;
        		}
        		//select comparison document to delete 
                selectComparisonForDelete();
        	}
        }
        
        document.forms[0].action = submitAction;
        document.forms[0].submit();
    } else {
        $(document.forms[0]).getInputs().each(function(el) {
            if (el.name=='objectsToDelete') {
                el.checked = false;
            }
        });
    }
    
}

function selectComparisonForDelete() {
	
	  $(document.forms[0]).getInputs().each(function(el) {
	        if (el.name=='objectsToDelete') {
	            var elementIdString =el.id;
	            var tokens = elementIdString.split("_");
	            if(tokens.length==3) {
	                var type =tokens[2];
	                
	                //select comparion document for deletion
	                if(type=="Comparison") {
	                	el.checked = true;
	                }
	                
	            }
	           
	        }
	    });
}

function showreviewCtroDialog(id) {
	
	jQuery("#docId").val(id);
	jQuery("#reviewCtroUser").dialog({
        autoOpen: false,
        modal: true, 
        height :'auto',
        width :'auto',
        title :"CTRO Review Complete"
       
     }); 
    jQuery("#reviewCtroUser").dialog("open");
}

function showreviewCCCTDialog(id) {
	jQuery("#docId").val(id);
    jQuery("#reviewCcctUser").dialog({
        autoOpen: false,
        modal: true, 
        height :'auto',
        width :'auto',
        title :"CCCT Review Complete"
       
     }); 
    jQuery("#reviewCcctUser").dialog("open");
}

function closeDialog(ctroDialog) {
	if(ctroDialog) {
		 jQuery("#reviewCtroUser").dialog("close");
	}
	else {
		 jQuery("#reviewCcctUser").dialog("close");
	}
	
}

function saveCtroUserReview() {
	
	   var ctroUserId = jQuery("#ctroUserId").val();
	   document.forms[0].action="resultsReportingDocumentreviewCtro.action?ctroUserId="+ctroUserId;
	   document.forms[0].submit();
	    
}

function saveCcctUserReview() {
    
    var ccctUserId = jQuery("#ccctUserId").val();
    document.forms[0].action="resultsReportingDocumentreviewCcct.action?ccctUserId="+ccctUserId;
    document.forms[0].submit();
     
}


</SCRIPT>
 <body>

 <h1><fmt:message key="reportTrialDocument.title"/></h1>
 <jsp:include page="/WEB-INF/jsp/protocolDetailSummary.jsp"/>
 <div class="box">
   <pa:sucessMessage/>
   <pa:failureMessage/>
    <s:form>
        <s:actionerror/>
        <pa:studyUniqueToken/>
    <h2><fmt:message key="reportTrialDocument.subtitle" /></h2>
    
    <s:set name="hasDeletableDocs" value="%{false}" scope="request"/>
    <s:if test="trialDocumentList != null">
    <s:set name="trialDocumentList" value="trialDocumentList" scope="request"/>
    <display:table name="${trialDocumentList}" id="row" class="data" sort="list"  pagesize="10" requestURI="resultsReportingDocumentquery.action" export="false">
	    <display:column titleKey="trialDocument.fileName" sortable="true" headerClass="sortable"  style="width:30%">
	       <s:url id="url" action="resultsReportingDocumentsaveFile"><s:param name="id" value="%{#attr.row.id}" /></s:url>
	       <s:a href="%{url}"><s:property value="%{#attr.row.fileName}" /></s:a>
	    </display:column>
	    <display:column escapeXml="true" titleKey="trialDocument.type" property="typeCode" sortable="true" headerClass="sortable" style="width:10%"/>
	    <display:column escapeXml="true" titleKey="trialDocument.dateLastUpdated" property="dateLastUpdated" sortable="true" headerClass="sortable" style="width:10%" />
	     <display:column  titleKey="reportTrialDocument.ctroReviewComplete" class="action" style="width:15%" >
	        <s:if test="#attr.row.typeCode.equals('Comparison')">
	            <s:if test="#attr.row.ctroUserReviewDateTime!=null">
	              <c:out value="${attr.row.ctroUserName}"/>
	              <fmt:formatDate value="${attr.row.ctroUserReviewDateTime}" pattern="MM/dd/yyyy hh:mm aaa" />
	           </s:if>
	           <s:else>
                 <s:a href="#" cssClass="btn" onclick="javascript:showreviewCtroDialog('%{#attr.row.id}');"><span class="btn_img">Yes</span></s:a></li>
                 </div>
                </s:else> 
             
         </s:if>       
	    </display:column>
	    <display:column titleKey="reportTrialDocument.ccctoReviewComplete" style="width:15%">
	   
	      <s:if test="#attr.row.typeCode.equals('Comparison')">
	        <s:if test="#attr.row.ccctUserReviewDateTime!=null">
                   <c:out value="${attr.row.ccctUserName}"/>
                  <fmt:formatDate value="${attr.row.ccctUserReviewDateTime}" pattern="MM/dd/yyyy hh:mm aaa" />
               </s:if>
               <s:else>
	           <s:a href="#" cssClass="btn" onclick="javascript:showreviewCCCTDialog('%{#attr.row.id}');"><span class="btn_img">Yes</span></s:a></li>
	           </s:else>
	    </s:if>
	    </display:column>
	          
	   
	    <display:column escapeXml="true" titleKey="trialDocument.userLastUpdated" property="userLastUpdated" sortable="true" headerClass="sortable" style="width:10%" />
       <display:column title="Edit" class="action" style="width:5%" >
    		  <s:url id="url" action="resultsReportingDocumentedit"><s:param name="id" value="%{#attr.row.id}" /> <s:param name="page" value="%{'Edit'}"/>
    		  <s:param name="studyProtocolId" value="%{studyProtocolId}" />
    		  </s:url>
    		  <s:a href="%{url}"><img src="<c:url value='/images/ico_edit.gif'/>" alt="Edit" width="16" height="16"/></s:a>
    	   </display:column>
  	   <display:column title="Delete" class="action" style="width:5%">
			     
    		     <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.row.id}_%{#attr.row.typeCode}" fieldValue="%{#attr.row.id}" value="%{#attr.row.id in objectsToDelete}"/>
    		     <label style="display: none;" for="objectsToDelete_${row.id}">Check this box to mark row for deletion.</label>
    		      <s:set name="hasDeletableDocs" value="%{true}" scope="request"/>
	 </display:column>
     </display:table>
  </s:if>
		<div class="actionsrow">
			<del class="btnwrapper">
				<ul class="btnrow">
                    <li>
					    <s:url id="addurl" action="resultsReportingDocumentinput"><s:param name="studyProtocolId" value="%{studyProtocolId}" /> </s:url>
					   <s:a href="%{addurl}" cssClass="btn"><span class="btn_img"><span class="add">Add</span></span></s:a></li>
                        <s:if test="%{trialDocumentList != null && !trialDocumentList.isEmpty() && #request.hasDeletableDocs}">
                            <li><s:a href="javascript:void(0);" onclick="handleMultiDeleteResultReporting('Click OK to remove selected document(s) from the study. Cancel to abort.', 'resultsReportingDocumentdelete.action');" onkeypress="handleMultiDeleteResultReporting('Click OK to remove selected document(s) from the study. Cancel to abort.', 'resultsReportingDocumentdelete.action?studyProtocolId=%{studyProtocolId}');" cssClass="btn"><span class="btn_img"><span class="delete">Delete</span></span></s:a></li>
                            <li><pa:toggleDeleteBtn/></li>
                        </s:if>					   
                 </ul>
			</del>
		</div>
		<div id ="reviewCtroUser" style="display:none">
            <table class="form" height="100%" width="100%">
                <tr>
                    <td scope="row" class="label">Nci Trial Id</td>
                     <td class="value"><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></td>
                </tr>
                 <tr>
                    <td colspan="2">
                    </td>
                </tr>
                <tr>
                
                    <td scope="row" class="label">Ctro User</td>
                    <td class="value">  <s:select name="ctroUserId" id="ctroUserId"
                    list="usersMap"  />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                &nbsp;
                </td>
            </tr>
            <tr>
             <td colspan="2">
                 <s:a href="javascript:saveCtroUserReview()" cssClass="btn" onclick="#" id="ctroSave"><span  class="btn_img"><span class="save">Save</span></span></s:a>
                  <s:a href="javascript:closeDialog(true)" cssClass="btn" onclick="#"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
             </td>       
           </tr>
        </table>
        </div>
        <div id ="reviewCcctUser" style="display:none">
            <table class="form" height="100%" width="100%">
                <tr>
                    <td scope="row" class="label">Nci Trial Id</td>
                     <td class="value"><c:out value="${sessionScope.trialSummary.nciIdentifier }"/></td>
                </tr>
                 <tr>
                    <td colspan="2">
                    </td>
                </tr>
                <tr>
                
                    <td scope="row" class="label">CCCT User</td>
                    <td class="value">  <s:select  name="ccctUserId" id="ccctUserId"
                    list="usersMap"  />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                &nbsp;
                </td>
            </tr>
            <tr>
             <td colspan="2">
                 <s:a href="javascript:saveCcctUserReview()" cssClass="btn" onclick="#" id="ccctSave"><span class="btn_img"><span class="save">Save</span></span></s:a>
                  <s:a href="javascript:closeDialog(false)" cssClass="btn" onclick="#"><span class="btn_img"><span class="cancel">Cancel</span></span></s:a>
             </td>       
           </tr>
        </table>
        </div>
          <s:hidden name="studyProtocolId" id="studyProtocolId" />    
           <s:hidden name="id" id="docId" />    
	</s:form>
	</div>
	
 </body>
 </html>
