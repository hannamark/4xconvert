<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="stdnav"><div>NCI CTRP</div>
    <ul>
        <c:choose>
           <c:when test="${requestScope.topic == 'home'}">
              <li><a href="home.action" class="selected">Home</a></li>
           </c:when>
           <c:otherwise>
              <li><a href="home.action">Home</a></li>
           </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">            
                 <c:choose>
                    <c:when test="${requestScope.topic == 'trials_intro'}">
                       <li><a href="viewTrials.action" class="selected">Trial Search</a></li> 
                    </c:when>
                    <c:otherwise>
                       <li><a href="viewTrials.action" >Trial Search</a></li>
                    </c:otherwise>
                </c:choose>
             </c:when>
        </c:choose> 
        <c:choose>
              <c:when test="${(sessionScope.studyProtocolIi != null) && (sessionScope.accrualRole == 'Submitter')}">
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'accrual_submissions') || (requestScope.topic == 'accrual_submitting') || (requestScope.topic == 'accrual_reviewing')}">
                                <li><a href="accrualSubmissions.action" class="selected">Submissions</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="accrualSubmissions.action" >Submissions</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${(requestScope.topic == 'subjects_intro') || (requestScope.topic == 'subjects_adding') || (requestScope.topic == 'subjects_update')}">
                                <li><a href="patients.action" class="selected">Study Subject Search</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="patients.action" >Study Subject Search</a></li>
                            </c:otherwise>
                        </c:choose>
                 </c:when>
           </c:choose>                  
          
          <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">    
          			<c:choose>
                            <c:when test="${(requestScope.topic == 'subjects_intro') || (requestScope.topic == 'subjects_adding') || (requestScope.topic == 'subjects_update')}">
                                <li><a href="patients.action" class="selected">Study Subject Search</a></li> 
                            </c:when>
                            <c:otherwise>
                                <li><a href="patients.action" >Study Subject Search</a></li>
                            </c:otherwise>
                      </c:choose>
          </c:if>
          
          <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">         
          <li class="hassubmenu">Baseline Data
		         <ul id="part_sites">      	
		                     		<c:choose>      	                           
                            			<c:when test="${(requestScope.topic == 'diagnosis_detail')}">
		                                		<li><a href="diagnosis.action" class="selected">Diagnosis</a></li>     		                                
                            			</c:when>
		                            	<c:otherwise>
					                          <li><a href="diagnosis.action" >Diagnosis</a></li>
					                     </c:otherwise> 
					                   </c:choose>
					                   <c:choose>  
                            			<c:when test="${(requestScope.topic == 'staging_detail')}">
		                                	<li><a href="staging.action"  class="selected">Staging</a></li>
		                                </c:when>
		                            	<c:otherwise>
					                          <li><a href="staging.action" >Staging</a></li>
					                     </c:otherwise>
					                   </c:choose>
					                   <c:choose>   
		                                <c:when test="${(requestScope.topic == 'pathology_detail')}">	
		                                	<li><a href="pathology.action"  class="selected">Pathology</a></li>
		                                </c:when>
		                            	<c:otherwise>
					                          <li><a href="pathology.action" >Pathology</a></li>
					                     </c:otherwise> 
					                   </c:choose>
					                   <c:choose>  
		                                <c:when test="${(requestScope.topic == 'priorTherapies_detail')}">	
		                                	<li><a href="priorTherapies.action"  class="selected">Prior Therapies</a></li>
		                                </c:when>
		                            	<c:otherwise>
					                          <li><a href="priorTherapies.action" >Prior Therapies</a></li>
					                     </c:otherwise> 		                             
                        			</c:choose>
		            </ul>
		        </li>
              </c:if>
              
              <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">              
               <li class="hassubmenu">
               		<c:choose>                              	                           
		               		<c:when test="${(requestScope.topic == 'treatment_detail')}">
               					<a href="treatment.action"  class="selected">Treatment</a>
               				</c:when>
		                    <c:otherwise>
					               <a href="treatment.action">Treatment</a>     		              
					         </c:otherwise> 
		              </c:choose>
		             <ul id="part_sites">
		                       				<c:choose>                              	                           
		                            			<c:when test="${(requestScope.topic == 'drugBiologics_detail')}">
				                                		<li><a href="drugBiologics.action" class="selected">Drug/Biologics</a></li>     		                                
		                            			</c:when>
		                            			<c:otherwise>
					                                <li><a href="drugBiologics.action">Drug/Biologics</a></li>     		              
					                            </c:otherwise> 
						                   </c:choose>
						                   <c:choose>  
		                            			<c:when test="${(requestScope.topic == 'procedure_detail')}">
				                                	<li><a href="procedure.action"  class="selected">Procedure</a></li>
				                                </c:when>
		                            			<c:otherwise>
					                                <li><a href="procedure.action" >Procedure</a></li>
					                            </c:otherwise> 
						                   </c:choose>
						                   <c:choose>  
				                                <c:when test="${(requestScope.topic == 'radiation_detail')}">	
				                                	<li><a href="radiation.action"  class="selected">Radiation</a></li>
				                                </c:when>
		                            			<c:otherwise>
					                                <li><a href="radiation.action" >Radiation</a></li>
					                            </c:otherwise> 
						                   </c:choose>
						                   <c:choose>  
				                                <c:when test="${(requestScope.topic == 'offTreatment_detail')}">	
				                                	<li><a href="offTreatment.action"  class="selected">Off Treatment</a></li>
				                                </c:when>
		                            			<c:otherwise>
					                                <li><a href="offTreatment.action" >Off Treatment</a></li>
					                            </c:otherwise>
                        				</c:choose>
		                </ul>
		        </li>                         
                </c:if>
                
                <c:if test="${(pageContext.request.remoteUser != null) && (sessionScope.accrualRole == 'Outcomes')}">    
                <li class="hassubmenu">
               		<c:choose>                              	                           
		               		<c:when test="${(requestScope.topic == 'patientOutcomes_detail')}">
               					<a href="patientOutcomes.action"  class="selected">Patient Outcomes</a>
               				</c:when>
		                    <c:otherwise>
					               <a href="patientOutcomes.action">Patient Outcomes</a>     		              
					         </c:otherwise> 
		              </c:choose>
		              <ul id="part_sites"> 
		                           <c:choose>                                 	                           
                            			<c:when test="${(requestScope.topic == 'deathInformation_detail')}">
		                                		<li><a href="deathInformation.action" class="selected">Death Information</a></li>     		                                
                            			</c:when>
                            			<c:otherwise>
			                                <li><a href="deathInformation.action" >Death Information</a></li>
			                            </c:otherwise>
                        			</c:choose>
		               </ul>
		       </li>
		 </c:if>
          <c:choose>
            <c:when test="${pageContext.request.remoteUser != null}">      
                <li><a href="/accrual/logout.action" >Log Out</a></li>
            </c:when>
            <c:otherwise>
               <c:choose>
                   <c:when test="${requestScope.topic == 'login'}">
                      <li><a href="/accrual/common/welcome.action" class="selected">Log In</a></li>
                   </c:when>
                   <c:otherwise>
                      <li><a href="/accrual/common/welcome.action" >Log In</a></li>
                   </c:otherwise>
               </c:choose>
            </c:otherwise>
        </c:choose>
    </ul>
</li>


