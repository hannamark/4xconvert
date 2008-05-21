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
   * Information regarding a study participant assigned by and used in the context of a particular healthcare 
   * site. 
   * 
   */

public  class HealthcareSiteParticipant 	implements java.io.Serializable 
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
	
		
	public java.lang.String participantIdentifier;
	public java.lang.String getParticipantIdentifier()
	{
		return participantIdentifier;
	}
	public void setParticipantIdentifier(java.lang.String participantIdentifier)
	{
		this.participantIdentifier = participantIdentifier;
	}
	
	
		
		
	private gov.nih.nci.ctom.domain.Participant participant;
	public gov.nih.nci.ctom.domain.Participant getParticipant()
	{
			
		if(participant==null ||  participant.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Participant as child where child.id in (select parent.participant.id from gov.nih.nci.ctom.domain.HealthcareSiteParticipant as parent where parent.id="+idString+")";
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.Participant");				 
				if (resultList!=null && resultList.size()>0) 
					participant = (gov.nih.nci.ctom.domain.Participant)resultList.get(0);
				else
					participant = null;
			}
			catch(Exception ex) 
			{ 
				Logger log = Logger.getLogger(HealthcareSiteParticipant.class.getName());
				log.error("HealthcareSiteParticipant:getParticipant throws exception ... ...",ex);
			}
		}
		return participant;	
					
	}

	public void setParticipant(gov.nih.nci.ctom.domain.Participant participant)
	{
		this.participant = participant;
	}
		
	
	
	
		
		
	private java.util.Collection healthcareSiteRoleCollection = new java.util.HashSet();
	public java.util.Collection getHealthcareSiteRoleCollection()
	{
		if (healthcareSiteRoleCollection==null || healthcareSiteRoleCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.HealthcareSiteParticipantRole as child, gov.nih.nci.ctom.domain.HealthcareSiteParticipant as parent  where child in elements(parent.healthcareSiteRoleCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.HealthcareSiteParticipantRole");				 
				healthcareSiteRoleCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(HealthcareSiteParticipant.class.getName());
				log.error("HealthcareSiteParticipant:getHealthcareSiteRoleCollection throws exception ... ...",ex);
			}
		}	
		return healthcareSiteRoleCollection;
	}
	
	public void setHealthcareSiteRoleCollection(java.util.Collection healthcareSiteRoleCollection)
	{
		this.healthcareSiteRoleCollection = healthcareSiteRoleCollection;
	}	
		
	
	
	
		
	

	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof HealthcareSiteParticipant) 
		{
			HealthcareSiteParticipant c =(HealthcareSiteParticipant)obj; 			 
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