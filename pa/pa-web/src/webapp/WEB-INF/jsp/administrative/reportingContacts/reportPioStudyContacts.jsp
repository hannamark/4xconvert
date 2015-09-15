<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <h3><fmt:message key="reportStudyContacts.pio.assigned.title" /></h3>
      <s:set name="studyPioContactWebDtos" value="studyPioContactWebDtos" scope="request"/>
        
      <display:table name="${studyPioContactWebDtos}" id="pscWeb"
                    defaultsort="1" sort="list" pagesize="9999999"
                    requestURI="resultsReportingContactsquery.action" export="false"  >
                    <display:setProperty name="basic.msg.empty_list"
                        value="No PIO study contacts found." />
                    <display:setProperty name="basic.empty.showtable" value="false" />
                    <display:setProperty name="paging.banner.one_item_found" value="" />
                    <display:setProperty name="paging.banner.all_items_found" value="" />
                    <display:setProperty name="paging.banner.onepage" value="" />
                    <display:setProperty name="export.xml" value="false" />
                   
                    <display:column escapeXml="false" title="Name"  
                        property="editedPrsnNm" />
                     <display:column escapeXml="false" title="Email" 
                        property="email" />
                     <display:column escapeXml="false" title="Phone" 
                        property="phoneWithExt"/>
                    <display:column escapeXml="false" title="Edit" style="align:center" >
                        <div align="left">
                             <img style="cursor: pointer;" alt="Click here to edit"
                                src="${imagePath}/ico_edit.gif" onclick="editPioContact(${pscWeb.id});">
                        </div>
                    </display:column>
                    <display:column escapeXml="false" title="Delete" media="html">
                        <div align="left">
                            <s:checkbox name="objectsToDelete" id="objectsToDelete_%{#attr.pscWeb.id}" fieldValue="%{#attr.pscWeb.id}" value="%{#attr.pscWeb.id in objectsToDelete}"/>
                            <label style="display: none;" for="objectsToDelete_${pscWeb.id}">Check this box to mark row for deletion.</label>
                        </div>
                    </display:column>
                </display:table>
                
          
        