<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
  <h2><fmt:message key="reportCoverSheet.recordChanges.title" /></h2>
      <s:set name="studyRecordChagesList" value="studyRecordChagesList" scope="request"/>  
       <div id="msg" class="confirm_msg" style="display: none;">
                    <strong>Changes saved!</strong>
                </div>     
        <display:table name="${studyRecordChangeList}" id="recordChanges"
                    defaultsort="1" sort="list" pagesize="9999999"
                    requestURI="resultsReportingCoverSheetquery.action" export="false"  >
                    <display:setProperty name="basic.msg.empty_list"
                        value="No study record changes found." />
                    <display:setProperty name="basic.empty.showtable" value="false" />
                    <display:setProperty name="paging.banner.one_item_found" value="" />
                    <display:setProperty name="paging.banner.all_items_found" value="" />
                    <display:setProperty name="paging.banner.onepage" value="" />
                    <display:setProperty name="export.xml" value="false" />
                   
                    <display:column escapeXml="false" title="Change Type"  
                        property="changeType" />
                    <display:column escapeXml="false" title="Action Taken" 
                        property="actionTaken" />
                    <display:column escapeXml="false" title="Action Completion Date" 
                        property="actionCompletionDate" format="{0,date,MM/dd/yyyy}"/>
                   
                    <display:column escapeXml="false" title="Edit" style="align:center" >
                        <div align="left">
                             <img style="cursor: pointer;" alt="Click here to edit"
                                src="${imagePath}/ico_edit.gif" >
                        </div>
                    </display:column>
                    <display:column escapeXml="false" title="Delete" media="html">
                        <div align="left">
                            <s:checkbox name="objectsToDelete"
                                id="objectsToDelete_%{#attr.recordChanges.id}"
                                fieldValue="%{#attr.recordChanges.id}"
                                value="%{#attr.recordChanges.id in objectsToDelete}" />
                            <label style="display: none;"
                                for="objectsToDelete_${dataDisc.id}">Check this box
                                to mark row for deletion.</label>
                        </div>
                    </display:column>
                  <display:column escapeXml="false" media="html" property="id" 
                        class="id_holder" />
                </display:table>        
          
                 <div class="actionsrow">
                <del class="btnwrapper">
                    <ul class="btnrow">
                        <li><s:a href="javascript:void(0);" onclick="addStudyRecord();"
                                cssClass="btn" id="addStudyRecord">
                                <span class="btn_img"><span class="add">Add </span></span>
                            </s:a></li>
                        <c:if test="${not empty studyRecordChangeList}">
                            <li><s:a href="javascript:void(0);" id="deleteBtnChangeType"
                                    cssClass="btn">
                                    <span class="btn_img"><span class="delete">Delete</span></span>
                                </s:a></li>
                        </c:if>
                    </ul>
                </del>
            </div>
               <s:hidden name="studyProtocolId" id="studyProtocolId" /> 
               <s:hidden name="deleteType" id="deleteType" />