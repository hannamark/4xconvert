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

   */

public  class HealthcareSiteInvestigator 	implements java.io.Serializable 
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
	
	
		
		
	private gov.nih.nci.ctom.domain.Investigator investigator;
	public gov.nih.nci.ctom.domain.Investigator getInvestigator()
	{
			
		if(investigator==null ||  investigator.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Investigator as child where child.id in (select parent.investigator.id from gov.nih.nci.ctom.domain.HealthcareSiteInvestigator as parent where parent.id="+idString+")";
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
				Logger log = Logger.getLogger(HealthcareSiteInvestigator.class.getName());
				log.error("HealthcareSiteInvestigator:getInvestigator throws exception ... ...",ex);
			}
		}
		return investigator;	
					
	}

	public void setInvestigator(gov.nih.nci.ctom.domain.Investigator investigator)
	{
		this.investigator = investigator;
	}
		
	
	
	
		
		
	private gov.nih.nci.ctom.domain.HealthcareSite healthcareSite;
	public gov.nih.nci.ctom.domain.HealthcareSite getHealthcareSite()
	{
			
		if(healthcareSite==null ||  healthcareSite.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.HealthcareSite as child where child.id in (select parent.healthcareSite.id from gov.nih.nci.ctom.domain.HealthcareSiteInvestigator as parent where parent.id="+idString+")";
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.HealthcareSite");				 
				if (resultList!=null && resultList.size()>0) 
					healthcareSite = (gov.nih.nci.ctom.domain.HealthcareSite)resultList.get(0);
				else
					healthcareSite = null;
			}
			catch(Exception ex) 
			{ 
				Logger log = Logger.getLogger(HealthcareSiteInvestigator.class.getName());
				log.error("HealthcareSiteInvestigator:getHealthcareSite throws exception ... ...",ex);
			}
		}
		return healthcareSite;	
					
	}

	public void setHealthcareSite(gov.nih.nci.ctom.domain.HealthcareSite healthcareSite)
	{
		this.healthcareSite = healthcareSite;
	}
		
	

	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof HealthcareSiteInvestigator) 
		{
			HealthcareSiteInvestigator c =(HealthcareSiteInvestigator)obj; 			 
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