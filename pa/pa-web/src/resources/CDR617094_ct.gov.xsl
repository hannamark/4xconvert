<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:param name="page-type" select="'A4'"/>
	<xsl:template match="/">
	<body style="margin:2; padding:0; font-family:arial,helvetica,sans-serif; color:#000;font-size:13;background:#fff; min-width:920px">

	<b>Protocol Title</b>
	<p>	<xsl:value-of select="clinical_study/brief_title"/></p>

    <b>Original Title</b><br/>
	<p><xsl:value-of select="clinical_study/official_title"/></p>

	<b> General Protocol Information</b><br/><br/>
	<xsl:for-each select="clinical_study/id_info">

	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" cellpadding="1" cellspacing="6">
	<tbody>
		<tr><td><b>Provider Name:</b></td><td><xsl:value-of select="provider_name"/></td></tr>
		<tr><td><b>Provider study ID:</b></td><td><xsl:value-of select="provider_study_id"/></td></tr>
		<tr><td><b>Org Name:</b></td><td><xsl:value-of select="org_name"/></td></tr>
		<tr><td><b>Org Study ID:</b></td><td><xsl:value-of select="org_study_id"/></td></tr>
		<tr><td><b>Secondary ID:</b></td><td><xsl:value-of select="secondary_Id"/></td></tr>
		<tr><td><b>Ind Grantor:</b></td><td><xsl:value-of select="ind_grantor"/></td></tr>
		<tr><td><b>Ind Number:</b></td><td><xsl:value-of select="ind_number"/></td></tr>
		<tr><td><b>Has Expanded Access:</b></td><td><xsl:value-of select="has_expanded_access"/></td></tr>
		</tbody>
		</table>
		</xsl:for-each><br/>
		<b>Status</b><br/><br/>
		<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" cellpadding="1" cellspacing="5">
			   
		<tbody>
			<tr>
				<td><b>Overall Status</b></td><td><xsl:value-of select="clinical_study/overall_status"/></td>
			</tr>
			<tr>
				<td><b>Start Date</b></td><td><xsl:value-of select="clinical_study/start_date"/></td>
			</tr>
			<tr>
				<td><b>Primary Complete date</b></td><td><xsl:value-of select="clinical_study/primary_compl_date"/></td>
			</tr>
			<tr>
				<td><b>Primary Complete date Type</b></td><td><xsl:value-of select="clinical_study/primary_compl_date_type"/></td>
			</tr>
			<tr>
				<td><b>Phase</b></td><td><xsl:value-of select="clinical_study/phase"/></td>
			</tr>
		</tbody>
	</table>
	<br></br>
	
	<b>Required Regulatory Information</b><br/><br/>
	 <table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" cellpadding="1" cellspacing="5">
	 <tbody>
		<tr>
		<td>
		<b>Is FDA Regulated:</b></td><td><xsl:value-of select="clinical_study/is_fda_regulated"/></td>
		</tr>
		<tr><td><b>Is Section 801:</b></td><td><xsl:value-of select="clinical_study/is_section_801"/></td>
		</tr>
		<tr><td><b>Delayed Posting:</b></td><td><xsl:value-of select="clinical_study/delayed_posting"/></td>
		</tr>
		<tr><td><b>Is Study:</b></td><td><xsl:value-of select="clinical_study/is_ind_study"/></td>
		</tr>
		</tbody>
	</table>
	<br/>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   <xsl:for-each select="clinical_study/oversight_info">
			<tbody>
			<tr>
			 <td >
				<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;">
				<tr><td><b>Regulatory Authority:</b></td><td><xsl:value-of select="regulatory_authority"/></td></tr>
				<tr><td><b>Has DMC:</b></td><td><xsl:value-of select="has_dmc"/></td></tr>
				 <xsl:for-each select="irb_info">
				<tr><td> <b>Approval Status:</b></td><td><xsl:value-of select="approval_status"/></td></tr>
				<tr><td><b>Name:</b></td><td><xsl:value-of select="name"/></td></tr>
				<tr><td><b>Affiliation:</b></td><td><xsl:value-of select="affiliation"/></td></tr>
				<tr><td><b>Phone:</b></td><td><xsl:value-of select="phone"/></td></tr>
				<tr><td><b>Email:</b></td><td><xsl:value-of select="email"/></td></tr>
				<tr><td><b>Full Address:</b></td><td><xsl:value-of select="full_address"/></td></tr>
				</xsl:for-each>
				</table>
				</td>
			</tr>
			</tbody>
			</xsl:for-each>
	</table>
<br/><br/>	
	<xsl:for-each select="clinical_study/sponsors">
	<b>Responsible Party</b>
		<p><b>Lead Sponsor:</b><xsl:text> </xsl:text><xsl:value-of select="lead_sponsor/agency"/></p>
     	  <p><b>Collaborator(s)</b><br/>
 			<xsl:for-each select="collaborator/agency">
			 <b>Agency:</b><xsl:text> </xsl:text><xsl:apply-templates/><br/>
      </xsl:for-each></p>
     </xsl:for-each>
   <br/>
  <b>Protocol Personnel(s)</b><br/>
		<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   	<tbody>
		<xsl:for-each select="clinical_study/overall_official">
			<tr>
				<td><b>Name:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="middle_name"/><xsl:value-of select="last_name"/></td>
			</tr>
			<tr>
				<td><b>Degrees:</b></td><td><xsl:value-of select="degrees"/></td>
			</tr>
			<tr>
				<td><b>Role:</b></td><td><xsl:value-of select="role"/></td>
			</tr>
				<tr>
				<td><b>Affliation:</b></td><td><xsl:value-of select="affiliation"/></td>
			</tr>
		</xsl:for-each>
			</tbody>
	</table>
	<br></br>
	<b>Study Information</b><br/>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="10%">
	 <tbody>
		<xsl:for-each select="clinical_study/eligibility">
			<tr>
				<td><b>Criteria:</b></td><td><pre><xsl:value-of select="criteria/textblock"/></pre></td>
			</tr>
			<tr>
				<td><b>Healthy Volunteers:</b></td><td><xsl:value-of select="healthy_volunteers"/></td>
			</tr>
			<tr>
				<td><b>Expected Enrollment:</b></td><td><xsl:value-of select="expected_enrollment"/></td>
			</tr>
				<tr>
				<td><b>Gender:</b></td><td><xsl:value-of select="gender"/></td>
			</tr>
			<tr>
				<td><b>Minimum age:</b></td><td><xsl:value-of select="minimum_age"/></td>
			</tr>
			
			<tr>
				<td><b>Maximum age:</b></td><td><xsl:value-of select="maximum_age"/></td>
			</tr>
		</xsl:for-each>
			</tbody>
	</table>
<br></br>
	<xsl:for-each select="clinical_study/brief_summary">
		<p><b>Brief Summary</b><br></br>
			 <xsl:value-of select="textblock"/></p>
	</xsl:for-each>
	
	<xsl:for-each select="clinical_study/detailed_description">
	 <b>Detailed Description</b><br/>
		<pre> <xsl:value-of select="textblock"/></pre>
	</xsl:for-each>
	
	<b>Study Design</b><br/>
	<table  style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	  <xsl:for-each select="clinical_study/study_design">
		<tbody>
			<tr>
				<td >
				<table  style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;">
				<tr><td><b>Name:</b></td><td><xsl:value-of select="study_type"/></td></tr>
				 <xsl:for-each select="interventional_design">
				 <tr><td><b>Interventional sub Type:</b></td><td><xsl:value-of select="interventional_subtype"/></td></tr>
				<tr><td><b>Interventional Phase:</b></td><td><xsl:value-of select="phase"/></td></tr>
				<tr><td><b>Interventional Allocation:</b></td><td><xsl:value-of select="allocation"/></td></tr>
				<tr><td><b>Interventional Masking:</b></td><td><xsl:value-of select="masking"/></td></tr>
				<tr><td><b>Interventional Masked Caregiver:</b></td><td><xsl:value-of select="masked_caregiver"/></td></tr>
				<tr><td><b>InterventionalMasked Subject:</b></td><td><xsl:value-of select="masked_subject"/></td></tr>
				<tr><td><b>Interventional Assignment:</b></td><td><xsl:value-of select="assignment"/></td></tr>
				<tr><td><b>Interventional Endpoint:</b></td><td><xsl:value-of select="endpoint"/></td></tr>
				<tr><td><b>Number of arms:</b></td><td><xsl:value-of select="number_of_arms"/></td></tr>
				</xsl:for-each>
				</table>
				</td>
			</tr>
			</tbody>
			</xsl:for-each>
	</table>
		<br/>
	<b>Protocol OutComes</b><br/><br/>
	<table  style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   <thead >
			<tr align="left">
				<th>Primary Out Comes</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td >
				<xsl:for-each select="clinical_study/primary_outcome">
				<ul>
				<li><xsl:value-of select="outcome_measure"/><xsl:text> </xsl:text>[<i>Designated as safety issue:<xsl:text> </xsl:text><xsl:value-of select="outcome_safety_issue"/></i>][<i>Designated Time frame:<xsl:text> </xsl:text><xsl:value-of select="outcome_time_frame"/></i>]
			    </li>
				</ul>
				</xsl:for-each>
				</td>
			</tr>
			</tbody>
			
	</table>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   <thead >
		<tr align="left">
			<th >Secondry Out Comes</th>
		</tr>
		</thead>
		<tbody>
			<tr>
				<td>
				<xsl:for-each select="clinical_study/secondary_outcome">
				<ul>
				<li><xsl:value-of select="outcome_measure"/><xsl:text> </xsl:text>[<i>Designated as safety issue:<xsl:text> </xsl:text><xsl:value-of select="outcome_safety_issue"/></i>][<i>Designated Time frame:<xsl:text> </xsl:text><xsl:value-of select="outcome_time_frame"/></i>]
			    </li>
				</ul>
				</xsl:for-each>
			</td>
			</tr>
		</tbody>
	</table>
	<xsl:for-each select="clinical_study/enrollment">
		<p><b>Enrollment</b><br></br>
			<xsl:apply-templates/></p>
		</xsl:for-each>

  <b>Arm Group</b><br/><br/>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	  <tbody>
		<xsl:for-each select="clinical_study/arm_group">
			<tr>
				<td><b>Arm Group Label:</b></td><td><xsl:value-of select="arm_group_label"/></td>
			</tr>
			<tr>
				<td><b>Arm Type:</b></td><td><xsl:value-of select="arm_type"/></td>
			</tr>
			<tr>
				<td><b>Arm Group Description:</b></td><td><xsl:value-of select="arm_group_description/textblock"/></td>
			</tr>
			</xsl:for-each>
			</tbody>
	</table>
<br></br>
<b>Intervention</b><br/><br/>
			<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	  		<tbody>
		<xsl:for-each select="clinical_study/intervention">
			<tr>
				<td><b>Type:</b></td><td><xsl:value-of select="intervention_type"/></td>
			</tr>
			<tr>
				<td><b>Name:</b></td><td><xsl:value-of select="intervention_name"/></td>
			</tr>
			<tr>
				<td ><b>Description:</b></td><td ><xsl:value-of select="intervention_description/textblock"/></td>
			</tr>
			<tr>
				<td><b>Other Name:</b></td><td><xsl:value-of select="intervention_other_name"/></td>
			</tr>
			<xsl:for-each select="arm_group_label">
				<tr>
				<td ><b>Arm Group Label:</b></td><td><xsl:apply-templates/></td>
			</tr>
			</xsl:for-each>
		</xsl:for-each>
			</tbody>
	</table>
<br></br>

<xsl:for-each select="clinical_study/criteria">
<p><b>Criteria</b><br></br>
	<xsl:value-of select="textblock"/></p>
</xsl:for-each>
<br/>
<b>Location(s)</b><br/><br/>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%" cellpadding="2" cellspacing="4">
	  <tbody>
		<xsl:for-each select="clinical_study/location">
		 	<tr>
				<td>
				 <table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" cellpadding="2" cellspacing="4">
				 <tr><td><b>Facility:</b></td><td><xsl:value-of select="facility/name"/></td></tr>
				<tr><td><b>Address:</b></td><td><xsl:value-of select="facility/address"/></td></tr>
				<tr><td><b>Status:</b></td><td><xsl:value-of select="status"/></td></tr>
           <xsl:for-each select="contact">
				 <tr><td><b>Contact:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="last_name"/></td></tr>
				 <tr><td><b>Phone:</b></td><td><xsl:value-of select="phone"/></td></tr>
				 <tr><td><b>Email:</b></td><td><xsl:value-of select="email"/></td></tr>				
		   </xsl:for-each>
			<xsl:for-each select="investigator">
				 <tr><td><b>Investigator:</b></td><td><xsl:value-of select="first_name"/><xsl:text> </xsl:text><xsl:value-of select="last_name"/></td></tr>
				 <tr><td><b>Role:</b></td><td><xsl:value-of select="role"/></td></tr>
           </xsl:for-each>
		   </table>
				</td>
			</tr>
		</xsl:for-each>
			</tbody>
	</table>
	<br/>
	<table  style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   <thead >
		<tr align="left">
			<th>Diagnosis/Condition</th>
		</tr>
		</thead>
 		<tbody>
		<xsl:for-each select="clinical_study/keyword">
		<tr>
			<td><xsl:apply-templates/></td>
		</tr>
		</xsl:for-each>
		</tbody>
	</table>
	<br/>
	<table style="border: 1px; font-family:arial,helvetica,sans-serif;font-size:12;" width="40%">
	   <thead >
		<tr>
			<th align="left">Link(s)</th>
		</tr>
		</thead>
		<tbody>
		  <xsl:for-each select="clinical_study/link">
			<tr>
				<td><xsl:value-of select="url"/></td>
			</tr>
			<tr>
				<td style="padding:5px; border-top:1px solid #999; border-bottom:1px solid #a8b8ce; border-right:1px solid #a8b8ce; background:#d6dce6; color:#333"><b>Description:</b></td><td style="border-bottom:1px solid #386ebf; border-top:1px solid #fff; border-right:1px solid #eee; padding:4px 6px 3px 6px;background:#f7f9ea"><xsl:value-of select="description"/></td>
			</tr>
		  </xsl:for-each>
		</tbody>
	</table>
</body>
</xsl:template>
</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c) 2004-2008. Progress Software Corporation. All rights reserved.

<metaInformation>
	<scenarios>
		<scenario default="yes" name="Scenario1" userelativepaths="yes" externalpreview="no" url="" htmlbaseurl="" outputurl="" processortype="saxon8" useresolver="yes" profilemode="0" profiledepth="" profilelength="" urlprofilexml="" commandline=""
		          additionalpath="" additionalclasspath="" postprocessortype="none" postprocesscommandline="" postprocessadditionalpath="" postprocessgeneratedext="" validateoutput="no" validator="internal" customvalidator="">
			<advancedProp name="sInitialMode" value=""/>
			<advancedProp name="bXsltOneIsOkay" value="true"/>
			<advancedProp name="bSchemaAware" value="true"/>
			<advancedProp name="bXml11" value="false"/>
			<advancedProp name="iValidation" value="0"/>
			<advancedProp name="bExtensions" value="true"/>
			<advancedProp name="iWhitespace" value="0"/>
			<advancedProp name="sInitialTemplate" value=""/>
			<advancedProp name="bTinyTree" value="true"/>
			<advancedProp name="bWarnings" value="true"/>
			<advancedProp name="bUseDTD" value="false"/>
			<advancedProp name="iErrorHandling" value="fatal"/>
		</scenario>
	</scenarios>
	<MapperMetaTag>
		<MapperInfo srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" destSchemaPath="" destSchemaRoot="" destSchemaPathIsRelative="yes" destSchemaInterpretAsXML="no"/>
		<MapperBlockPosition></MapperBlockPosition>
		<TemplateContext></TemplateContext>
		<MapperFilter side="source"></MapperFilter>
	</MapperMetaTag>
</metaInformation>
-->