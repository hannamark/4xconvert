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
   * A health care site in which clinical trial activities are conducted.
   */

public  class StudySite 	implements java.io.Serializable 
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
	
		
	public java.lang.String roleCode;
	public java.lang.String getRoleCode()
	{
		return roleCode;
	}
	public void setRoleCode(java.lang.String roleCode)
	{
		this.roleCode = roleCode;
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
	
		
	public java.lang.String targetAccrualNumber;
	public java.lang.String getTargetAccrualNumber()
	{
		return targetAccrualNumber;
	}
	public void setTargetAccrualNumber(java.lang.String targetAccrualNumber)
	{
		this.targetAccrualNumber = targetAccrualNumber;
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
	
		
	public java.util.Date stopDate;
	public java.util.Date getStopDate()
	{
		return stopDate;
	}
	public void setStopDate(java.util.Date stopDate)
	{
		this.stopDate = stopDate;
	}
	
		
	public java.util.Date irbApprovalDate;
	public java.util.Date getIrbApprovalDate()
	{
		return irbApprovalDate;
	}
	public void setIrbApprovalDate(java.util.Date irbApprovalDate)
	{
		this.irbApprovalDate = irbApprovalDate;
	}
	
		
	public java.lang.String localProtocolIdentifier;
	public java.lang.String getLocalProtocolIdentifier()
	{
		return localProtocolIdentifier;
	}
	public void setLocalProtocolIdentifier(java.lang.String localProtocolIdentifier)
	{
		this.localProtocolIdentifier = localProtocolIdentifier;
	}
	
	
		
		
	private gov.nih.nci.ctom.domain.HealthcareSite healthCareSite;
	public gov.nih.nci.ctom.domain.HealthcareSite getHealthCareSite()
	{
			
		if(healthCareSite==null ||  healthCareSite.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.HealthcareSite as child where child.id in (select parent.healthCareSite.id from gov.nih.nci.ctom.domain.StudySite as parent where parent.id="+idString+")";
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.HealthcareSite");				 
				if (resultList!=null && resultList.size()>0) 
					healthCareSite = (gov.nih.nci.ctom.domain.HealthcareSite)resultList.get(0);
				else
					healthCareSite = null;
			}
			catch(Exception ex) 
			{ 
				Logger log = Logger.getLogger(StudySite.class.getName());
				log.error("StudySite:getHealthCareSite throws exception ... ...",ex);
			}
		}
		return healthCareSite;	
					
	}

	public void setHealthCareSite(gov.nih.nci.ctom.domain.HealthcareSite healthCareSite)
	{
		this.healthCareSite = healthCareSite;
	}
		
	
	
	
		
		
	private java.util.Collection studyParticipantCollection = new java.util.HashSet();
	public java.util.Collection getStudyParticipantCollection()
	{
		if (studyParticipantCollection==null || studyParticipantCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.StudyParticipantAssignment as child, gov.nih.nci.ctom.domain.StudySite as parent  where child in elements(parent.studyParticipantCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.StudyParticipantAssignment");				 
				studyParticipantCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(StudySite.class.getName());
				log.error("StudySite:getStudyParticipantCollection throws exception ... ...",ex);
			}
		}	
		return studyParticipantCollection;
	}
	
	public void setStudyParticipantCollection(java.util.Collection studyParticipantCollection)
	{
		this.studyParticipantCollection = studyParticipantCollection;
	}	
		
	
	
	
		
		
	private gov.nih.nci.ctom.domain.Protocol protocol;
	public gov.nih.nci.ctom.domain.Protocol getProtocol()
	{
			
		if(protocol==null ||  protocol.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Protocol as child where child.id in (select parent.protocol.id from gov.nih.nci.ctom.domain.StudySite as parent where parent.id="+idString+")";
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
				Logger log = Logger.getLogger(StudySite.class.getName());
				log.error("StudySite:getProtocol throws exception ... ...",ex);
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
		if(obj instanceof StudySite) 
		{
			StudySite c =(StudySite)obj; 			 
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