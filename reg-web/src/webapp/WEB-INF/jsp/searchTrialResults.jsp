<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<head>
<script type="text/javascript" language="javascript" src="<c:url value="/scripts/js/jquery-1.7.1.js"/>"></script>
<script type="text/javascript">
$(document).ready(function () {	
	
	jQuery('#nav li').hover(
		function () {
			//show its submenu
			jQuery('ul', this).slideDown(100);

		}, 
		function () {
			//hide its submenu
			jQuery('ul', this).slideUp(100);			
		}
	);
	
});
</script>
<style type="text/css">
	body {font-family:arial; font-size:11px;}
	
	/* remove the list style */
	#nav {
		margin:0; 
		padding:0; 
		list-style:none;
	}	
	
		/* make the LI display inline */
		/* it's position relative so that position absolute */
		/* can be used in submenu */
		#nav li {
			float:left; 
			display:block; 
			width:100px; 
			background:#ccc; 
			position:relative;
			z-index:2; 
			margin:0 1px;
		}
		
		/* this is the parent menu */
		#nav li a {
			display:block; 
			padding:8px 5px 0 5px; 
			font-weight:700;  
			height:23px; 
			text-decoration:none; 
			color:#fff; 
			text-align:center; 
			color:#333;
		}

		#nav li a:hover {
			color:#fff;
		}
	
		/* you can make a different style for default selected value */
		#nav a.selected {
			color:#f00;
		}
	
		/* submenu, it's hidden by default */
		#nav ul {
			position:absolute; 
			left:0; 
			display:none; 
			margin:0 0 0 -1px; 
			padding:0; 
			list-style:none;
		}
		
		#nav ul li {
			width:100px; 
			float:left; 
			border-top:1px solid #fff;
			z-index: 3;
		}
		
		/* display block will make the link fill the whole area of LI */
		#nav ul a {
			display:block;  
			height:15px;
			padding: 8px 5px; 
			color:#666;
		}
		
		#nav ul a:hover {
			text-decoration:underline;	
		}

		/* fix ie6 small issue */
		/* we should always avoid using hack like this */
		/* should put it into separate file : ) */
		*html #nav ul {
			margin:0 0 0 -2px;
		}

	</style>
</head>
<s:set name="records" value="records" scope="request"/>
<s:if test="records != null">
    <s:if test="records.size > 0">
        <c:set var="topic" scope="request" value="searchresults"/>
    </s:if>
    <c:choose>
        <c:when test="${requestScope.partialSubmission != null}">
            <h2 id="search_results">Saved Draft Search Results</h2>
            <display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialgetMyPartiallySavedTrial.action" export="true">
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsSavedDraftSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsSavedDraftSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="Temp Trial Identifier" sortable="true" headerScope="col" scope="row" media="html">
                    <a href="#" onclick="viewPartialProtocol('${row.studyProtocolId}');"><c:out value="${row.studyProtocolId}"/></a>
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
        </c:when>
        <c:otherwise>
            <h2 id="search_results">Submitted Clinical Trials Search Results</h2>
            <display:table class="data" summary="This table contains your trial search results. Please use column headers to sort results"
                           decorator="gov.nih.nci.registry.decorator.RegistryDisplayTagDecorator" sort="list" pagesize="10" id="row"
                           name="records" requestURI="searchTrialquery.action" export="true">
                <display:setProperty name="export.xml" value="false"/>
                <display:setProperty name="export.excel.filename" value="resultsTrialSearch.xls"/>
                <display:setProperty name="export.excel.include_header" value="true"/>
                <display:setProperty name="export.csv.filename" value="resultsTrialSearch.csv"/>
                <display:setProperty name="export.csv.include_header" value="true"/>
                <display:column class="title" title="NCI Trial Identifier" sortable="true" headerScope="col" scope="row" media="html">
                    <a href="#" onclick="viewProtocol('${row.studyProtocolId}');"><c:out value="${row.nciIdentifier}"/></a>
                </display:column>
                <display:column class="title" title="NCI Trial Identifier" headerScope="col" scope="row" media="excel csv xml">
                    <c:out value="${row.nciIdentifier}"/>
                </display:column>
                <display:column escapeXml="true" titleKey="search.trial.officialTitle" property="officialTitle" maxLength="200" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.studyStatusCode" property="studyStatusCode.code" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.leadOrganizationName" property="leadOrganizationName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.localStudyProtocolIdentifier" property="localStudyProtocolIdentifier" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column escapeXml="true" titleKey="search.trial.piFullName" property="piFullName" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.documentWorkflowStatus" property="documentWorkflowStatusCode.code" sortable="true" headerClass="sortable" headerScope="col"/>
                <display:column titleKey="search.trial.recordVerificationDate" property="recordVerificationDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.nctNumber" property="nctNumber" sortable="true" headerClass="sortable"/>
                <display:column titleKey="search.trial.submitter" property="lastCreated.userLastDisplayName" sortable="true" headerClass="sortable"/>
                
                <display:column title="Last update date" property="updatedDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
                <display:column title="Last amendment submitted" property="amendmentDate" format="{0,date,MM/dd/yyyy}" sortable="true" headerClass="sortable"/>
                <display:column title="Other Identifiers" >
                	<s:iterator value="%{#attr.row.otherIdentifiers}" >
				    	<s:property/> <br/>				
					</s:iterator>
                </display:column>               
                <display:column titleKey="search.trial.addMySite" sortable="false" headerClass="sortable" media="html">
                    <s:if test="%{#attr.row.currentUserCanAddSite}">
                        <s:url id="addMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <a href="javascript:void(0);" onclick="showPopup('${addMySiteUrl}', '', 'Add Participating Site');" 
                           onkeypress="showPopup('${addMySiteUrl}', '', 'Add Participating Site');">Add</a>
                    </s:if>
                </display:column>
                <display:column titleKey="search.trial.updateMySite" sortable="false" headerClass="sortable" media="html">
                    <s:if test="%{#attr.row.currentUserCanEditSite}">
                        <s:url id="updateMySiteUrl" action="addSitepopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
                        <a href="javascript:void(0);" onclick="showPopup('${updateMySiteUrl}', '', 'Update Participating Site');" 
                           onkeypress="showPopup('${updateMySiteUrl}', '', 'Update Participating Sit');">Update</a>
                    </s:if>
                </display:column>        
          	 	<display:column title="Action" sortable="false" headerClass="sortable" media="html">
                	<ul id="nav">
						<li>Action
							<ul>
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
					                        <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
					                        <a href="#" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');"><s:property value="%{#attr.row.statusChangeLinkText}" /></a>
					                    </s:if>
					                    <s:else>
					                        <s:url id="updateTrialStatusUrl" action="updateTrialStatuspopupview"><s:param name="studyProtocolId" value="%{#attr.row.studyProtocolId}" /></s:url>
					                        <a href="#" onclick="showPopup('${updateTrialStatusUrl}', '', 'Update Trial Status');"><s:property value="%{#attr.row.statusChangeLinkText}" /></a>
					                    </s:else>
				                    </s:if>
								</li>
								<li>
									<s:if test="%{#attr.row.showSendXml.booleanValue() == true}">
				                         <a href="#" onclick="sendXml('${row.studyProtocolId}');">Send XML/TSR</a>
				                    </s:if>
								</li>
							</ul>
						</li>
					</ul>                		                    	                    
                 </display:column>     
            </display:table>
        </c:otherwise>
    </c:choose>
</s:if>