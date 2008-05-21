package gov.nih.nci.ctom.domain;

import gov.nih.nci.ctom.domain.*;
import gov.nih.nci.system.applicationservice.*;
import gov.nih.nci.common.util.HQLCriteria;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * <!-- LICENSE_TEXT_START -->
 * <!-- LICENSE_TEXT_END -->
 */
 
  /**
   * Specifies the information describing an investigator associated with a study, i.e. role, association 
   * start and end date, etc. 
   * 
   */

public  class StudyInvestigator 	implements java.io.Serializable 
{
	private static final long serialVersionUID = 1234567890L;
	
	
		
	public java.lang.Long id;
	public java.lang.Long getId()
	{
		return id;
	}
	public void setId(java.lang.Long id)
	{
		this.id = id;
	}
	
		
	public java.lang.String responsibilityRoleCode;
	public java.lang.String getResponsibilityRoleCode()
	{
		return responsibilityRoleCode;
	}
	public void setResponsibilityRoleCode(java.lang.String responsibilityRoleCode)
	{
		this.responsibilityRoleCode = responsibilityRoleCode;
	}
	
		
	public java.lang.String signatureIndicator;
	public java.lang.String getSignatureIndicator()
	{
		return signatureIndicator;
	}
	public void setSignatureIndicator(java.lang.String signatureIndicator)
	{
		this.signatureIndicator = signatureIndicator;
	}
	
		
	public java.lang.String signatureText;
	public java.lang.String getSignatureText()
	{
		return signatureText;
	}
	public void setSignatureText(java.lang.String signatureText)
	{
		this.signatureText = signatureText;
	}
	
		
	public java.util.Date startDate;
	public java.util.Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(java.util.Date startDate)
	{
		this.startDate = startDate;
	}
	
		
	public java.lang.String statusCode;
	public java.lang.String getStatusCode()
	{
		return statusCode;
	}
	public void setStatusCode(java.lang.String statusCode)
	{
		this.statusCode = statusCode;
	}
	
		
	public java.util.Date stopDate;
	public java.util.Date getStopDate()
	{
		return stopDate;
	}
	public void setStopDate(java.util.Date stopDate)
	{
		this.stopDate = stopDate;
	}
	
	
		
		
	private gov.nih.nci.ctom.domain.Investigator investigator;
	public gov.nih.nci.ctom.domain.Investigator getInvestigator()
	{
			
		if(investigator==null ||  investigator.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Investigator as child where child.id in (select parent.investigator.id from gov.nih.nci.ctom.domain.StudyInvestigator as parent where parent.id="+idString+")";
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.Investigator");				 
				if (resultList!=null && resultList.size()>0) 
					investigator = (gov.nih.nci.ctom.domain.Investigator)resultList.get(0);
				else
					investigator = null;
			}
			catch(Exception ex) 
			{ 
				Logger log = Logger.getLogger(StudyInvestigator.class.getName());
				log.error("StudyInvestigator:getInvestigator throws exception ... ...",ex);
			}
		}
		return investigator;	
					
	}

	public void setInvestigator(gov.nih.nci.ctom.domain.Investigator investigator)
	{
		this.investigator = investigator;
	}
		
	
	
	
		
		
	private gov.nih.nci.ctom.domain.Protocol protocol;
	public gov.nih.nci.ctom.domain.Protocol getProtocol()
	{
			
		if(protocol==null ||  protocol.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Protocol as child where child.id in (select parent.protocol.id from gov.nih.nci.ctom.domain.StudyInvestigator as parent where parent.id="+idString+")";
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.Protocol");				 
				if (resultList!=null && resultList.size()>0) 
					protocol = (gov.nih.nci.ctom.domain.Protocol)resultList.get(0);
				else
					protocol = null;
			}
			catch(Exception ex) 
			{ 
				Logger log = Logger.getLogger(StudyInvestigator.class.getName());
				log.error("StudyInvestigator:getProtocol throws exception ... ...",ex);
			}
		}
		return protocol;	
					
	}

	public void setProtocol(gov.nih.nci.ctom.domain.Protocol protocol)
	{
		this.protocol = protocol;
	}
		
	

	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof StudyInvestigator) 
		{
			StudyInvestigator c =(StudyInvestigator)obj; 			 
			Long thisId = getId();		
			
			if(thisId != null && thisId.equals(c.getId()))
				eq = true;
			
			}
			return eq;
		}
		
	public int hashCode()
	{
		int h = 0;
		
		if(getId() != null)
			h += getId().hashCode();
		
		return h;
	}
}