<!DOCTYPE html PUBLIC 
    "-//W3C//DTD XHTML 1.1 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %> 
	   	<tr>
  			<th colspan="2"><fmt:message key="trial.detail.summary.title"/></th>
		</tr>
		<tr>
			<td scope="row" class="label" nowrap>
				<label for="nciAccessionNumber"> 
					<fmt:message key="trial.detail.summary.nci"/>
				</label>
			</td>
			<td >
				 Place Holder for session var
			</td>
        </tr>
		<tr>
			<td scope="row" class="label" nowrap>
				<label for="trialTitle"> 
					<fmt:message key="trial.detail.summary.trial.title"/>
				</label>
			</td>
			<td >
				 Place Holder for session var
			</td>
        </tr>       
        <tr>
			<td scope="row" class="label" nowrap>
				<label for="abstractionStatus"> 
					<fmt:message key="trial.detail.summary.abstraction.status"/>
				</label>
			</td>
			<td >
				 Place Holder for session var
			</td>
        </tr>       
        <tr>
			<td scope="row" class="label" nowrap>
				<label for="trialSubmitter"> 
					<fmt:message key="trial.detail.summary.trial.submitter"/>
				</label>
			</td>
			<td >
				 Place Holder for session var
			</td>
        </tr>     
		<tr>
			<td scope="row" class="label" nowrap>
				<label for="principalInvestigator"> 
					<fmt:message key="trial.detail.summary.principal.investigator"/>
				</label>
			</td>			
			<td >&nbsp;&nbsp;
				<a href="PrincipalInv.html" target="popup" onClick="wopen('PrincipalInv.html', 'popup', 640, 200); return false;">Place holder for session.principal Name </a>
			</td>
		</tr>
		<tr>
			 <td scope="row" class="label" nowrap>
				<label for="principalInvestigator"> 
					<fmt:message key="trial.detail.summary.supporting.documents"/>
				</label>
			 </td>					 
			  <td class="value">&nbsp;&nbsp;
					<a href="SupportingDoc.html" target="popup" onClick="wopen('SupportingDoc.html', 'popup', 640, 200); return false;"> Click Here</a>
			  </td>
		</tr>
