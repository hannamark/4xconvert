<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<script type="text/javascript" language="javascript" src="<c:url value='/scripts/js/coppa.js'/>"></script>
</head>
<s:set name="records" value="records" scope="request"/>
<s:if test="records != null">
    <s:if test="records.size > 0">
        <c:set var="topic" scope="request" value="searchresults"/>
    </s:if>
    <c:choose>
        <c:when test="${requestScope.partialSubmission != null}">
            <h2 id="search_results">Saved Draft Search Results</h2>
			<div class="table-wrapper">
            <div class="table-responsive">
            <display:table class="table table-striped table-bordered sortable" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="true">
                <display:setProperty name="paging.banner.item_name" value="trial"/>
                <display:setProperty name="paging.banner.items_name" value="trials"/>                           
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsSavedDraftSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsSavedDraftSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row" media="html">
                    <a href="javascript:void(0)" onclick="viewPartialProtocol('${row.studyProtocolId}');"><c:out value="${row.studyProtocolId}"/></a>
                </display:column>
                <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.studyProtocolId}"/>
                 </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength= "200" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName"    sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier"    sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.action" media="html">
                    <s:if test="%{#attr.row.proprietaryTrial}">
                        <s:url id="completeUrl" action="submitProprietaryTrialcomplete"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:if>
                    <s:else>
                        <s:url id="completeUrl" action="submitTrialcompletePartialSubmission"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                    </s:else>
                    <s:a href="%{completeUrl}">Complete</s:a>
                </display:column>
                <display:column titleKey="search.trial.action" media="html">
                    <s:url id="deleteUrl" action="submitTrialdeletePartialSubmission">
                        <s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" />
                        <s:param name="usercreated" value="%{#attr.row.userLastCreated}" />
                    </s:url>
                    <s:a href="%{deleteUrl}" onclick="return deletePartialProtocol();">Delete</s:a>
                </display:column>
            </display:table>
            </div>
            </div>
        </c:when>
        <c:otherwise>
            <h2 id="search_results">Submitted Clinical Trials Search Results</h2>
			<div class="table-wrapper">
            <div class="table-responsive">
            <display:table class="table table-striped table-bordered sortable" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialquery.action" export="true">
                <display:setProperty name="paging.banner.item_name" value="trial"/>
                <display:setProperty name="paging.banner.items_name" value="trials"/>
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsTrialSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsTrialSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column escapeXml="true" class="title" title="NCI Trial Identifier" sortable="true"
                    property="nciIdentifier" href="searchTrialview.action" paramId="studyProtocolId" paramProperty="studyProtocolId"
                    headerClass="sortable" headerScope="col" scope="row" media="html"/>
                <display:column class="title" title="NCI Trial Identifier" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength="200" sortable="true" headerClass="sortable" headerScope="col" media="excel csv"/>
                <display:column escapeXml="false" titleKey="search.trial.officialTitle" maxLength="200" sortable="true" headerClass="sortable" headerScope="col" media="html">
                    <!-- <c:out value="${row.officialTitle}"/> -->
                    <c:if test="${not empty row.studyAlternateTitles}">                    
                        <a href="javascript:void(0)" onclick="displayStudyAlternateTitles('${row.studyProtocolId}')">(*)</a>
                    </c:if>
                    <c:out value="${row.officialTitle}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.studyStatusCode" property="studyStatusCode.code" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.piFullName" property="piFullName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.nctNumber" property="nctIdentifier" sortable="true" headerClass="sortable"/>
                <display:column title="Other Identifiers"  property="otherIdentifiersAsString" />
                <display:column title="Participating Sites" media="html">
                	<s:url id="viewParticipatingSites" action="participatingSitespopup"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                   	<a href="javascript:void(0)" onclick="showPopup('${viewParticipatingSites}', '', 'View Participating Sites');">View</a>
               </display:column>                                              
          	 	<display:column title="Available Actions" sortable="false" headerClass="sortable" media="html">
          	 		<s:if test="%{#attr.row.actionVisible}">
          	 		<div class="btn-group">
                        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle btn-sm" type="button">Select Action <span class="caret"></span></button>
                        <ul role="menu" class="dropdown-menu">
                        		<li>
                        			<s:if test="%{!(#attr.row.update == null || #attr.row.update.equals(''))}">									
										<s:if test="%{#attr.row.proprietaryTrial}">
				                        <s:url id="url" action="updateProprietaryTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
					                        <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
					                    </s:if>
					                    <s:else>
					                        <s:url id="url" action="updateTrialview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
					                        <s:a href="%{url}"><s:property value="%{#attr.row.update}" /></s:a>
					                    </s:else>
									</s:if>																	
								</li>
								<li>
									<s:if test="%{!(#attr.row.amend == null || #attr.row.amend.equals(''))}">
										<s:url action="amendTrialview.action" var="urlTag" >
										    <s:param name="studyProtocolId"><s:property value="%{#attr.row.studyProtocolId}" /></s:param>
										</s:url>
										<s:a href="%{urlTag}">Amend</s:a>
									</s:if>
								</li>
								<li>
									<s:if test="%{!(#attr.row.statusChangeLinkText == null || #attr.row.statusChangeLinkText.equals(''))}">
										<s:if test="%{#attr.row.proprietaryTrial}">
					                    </s:if>
					                    <s:else>
					                        <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
					                        <a href="javascript:void(0)" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');">Change Status</a>
					                    </s:else>
				                    </s:if>
								</li>
								<li>
									<s:if test="%{#attr.row.showSendXml.booleanValue() == true}">
				                         <a href="javascript:void(0)" onclick="sendXml('${row.studyProtocolId}');">Send XML/TSR</a>
				                    </s:if>
								</li>
								<li>
									<s:if test="%{#attr.row.currentUserCanAddSite}">
				                        <s:url id="addMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
				                        <a href="javascript:void(0);" onclick="showPopup('${addMySiteUrl}', '', 'Add Participating Site');" 
				                           onkeypress="showPopup('${addMySiteUrl}', '', 'Add Participating Site');">Add My Site</a>
				                    </s:if>
								</li>
								<li>
									<s:if test="%{#attr.row.currentUserCanEditSite}">
				                        <s:url id="updateMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
				                        <a href="javascript:void(0);" onclick="showPopup('${updateMySiteUrl}', '', 'Update Participating Site');" 
				                           onkeypress="showPopup('${updateMySiteUrl}', '', 'Update Participating Sit');">Update My Site</a>
				                    </s:if>
								</li>
								<li>
                                    <s:if test="%{#attr.row.verifyData.booleanValue() == true}">
                                        <s:url id="editUrl" namespace="/protected" action="trialDataVerification" method="view">
                                            <s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}"/>
                                        </s:url>
                                        <s:a href="%{editUrl}">
                                          Verify Data
                                        </s:a>
                                    </s:if>
                                </li>
							</ul>
						</div>
					</s:if>              		                    	                    
                 </display:column>     
            </display:table>
            </div>
            </div>
        </c:otherwise>
    </c:choose>
</s:if>