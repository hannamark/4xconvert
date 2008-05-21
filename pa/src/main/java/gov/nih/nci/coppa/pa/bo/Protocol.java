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
   * A type of research activity that tests how well new medical treatments or other interventions work 
   * in subjects.#NOTES#Such plans test new methods of screening, prevention, diagnosis or treatment 
   * of a disease. The specific plans are fully defined in the protocol and may be carried out in a clinic 
   * or other medical facility. 
   * 
   */

public  class Protocol 	implements java.io.Serializable 
{
	private static final long serialVersionUID = 1234567890L;
	
	
		
	public java.util.Date amendmentDate;
	public java.util.Date getAmendmentDate()
	{
		return amendmentDate;
	}
	public void setAmendmentDate(java.util.Date amendmentDate)
	{
		this.amendmentDate = amendmentDate;
	}
	
		
	public java.lang.Long amendmentIdentifier;
	public java.lang.Long getAmendmentIdentifier()
	{
		return amendmentIdentifier;
	}
	public void setAmendmentIdentifier(java.lang.Long amendmentIdentifier)
	{
		this.amendmentIdentifier = amendmentIdentifier;
	}
	
		
	public java.lang.String blindedIndicator;
	public java.lang.String getBlindedIndicator()
	{
		return blindedIndicator;
	}
	public void setBlindedIndicator(java.lang.String blindedIndicator)
	{
		this.blindedIndicator = blindedIndicator;
	}
	
		
	public java.lang.String descriptionText;
	public java.lang.String getDescriptionText()
	{
		return descriptionText;
	}
	public void setDescriptionText(java.lang.String descriptionText)
	{
		this.descriptionText = descriptionText;
	}
	
		
	public java.lang.String diseaseCode;
	public java.lang.String getDiseaseCode()
	{
		return diseaseCode;
	}
	public void setDiseaseCode(java.lang.String diseaseCode)
	{
		this.diseaseCode = diseaseCode;
	}
	
		
	public java.lang.String documentUri;
	public java.lang.String getDocumentUri()
	{
		return documentUri;
	}
	public void setDocumentUri(java.lang.String documentUri)
	{
		this.documentUri = documentUri;
	}
	
		
	public java.lang.Long id;
	public java.lang.Long getId()
	{
		return id;
	}
	public void setId(java.lang.Long id)
	{
		this.id = id;
	}
	
		
	public java.lang.String intentCode;
	public java.lang.String getIntentCode()
	{
		return intentCode;
	}
	public void setIntentCode(java.lang.String intentCode)
	{
		this.intentCode = intentCode;
	}
	
		
	public java.lang.String longTitleText;
	public java.lang.String getLongTitleText()
	{
		return longTitleText;
	}
	public void setLongTitleText(java.lang.String longTitleText)
	{
		this.longTitleText = longTitleText;
	}
	
		
	public java.lang.String monitorCode;
	public java.lang.String getMonitorCode()
	{
		return monitorCode;
	}
	public void setMonitorCode(java.lang.String monitorCode)
	{
		this.monitorCode = monitorCode;
	}
	
		
	public java.lang.String multiInstitutionIndicator;
	public java.lang.String getMultiInstitutionIndicator()
	{
		return multiInstitutionIndicator;
	}
	public void setMultiInstitutionIndicator(java.lang.String multiInstitutionIndicator)
	{
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}
	
		
	public java.lang.String navyNCIIdentifier;
	public java.lang.String getNavyNCIIdentifier()
	{
		return navyNCIIdentifier;
	}
	public void setNavyNCIIdentifier(java.lang.String navyNCIIdentifier)
	{
		this.navyNCIIdentifier = navyNCIIdentifier;
	}
	
		
	public java.lang.String nciIdentifier;
	public java.lang.String getNciIdentifier()
	{
		return nciIdentifier;
	}
	public void setNciIdentifier(java.lang.String nciIdentifier)
	{
		this.nciIdentifier = nciIdentifier;
	}
	
		
	public java.lang.String phaseCode;
	public java.lang.String getPhaseCode()
	{
		return phaseCode;
	}
	public void setPhaseCode(java.lang.String phaseCode)
	{
		this.phaseCode = phaseCode;
	}
	
		
	public java.lang.String precisText;
	public java.lang.String getPrecisText()
	{
		return precisText;
	}
	public void setPrecisText(java.lang.String precisText)
	{
		this.precisText = precisText;
	}
	
		
	public java.lang.String randomizedIndicator;
	public java.lang.String getRandomizedIndicator()
	{
		return randomizedIndicator;
	}
	public void setRandomizedIndicator(java.lang.String randomizedIndicator)
	{
		this.randomizedIndicator = randomizedIndicator;
	}
	
		
	public java.lang.String shortTitleText;
	public java.lang.String getShortTitleText()
	{
		return shortTitleText;
	}
	public void setShortTitleText(java.lang.String shortTitleText)
	{
		this.shortTitleText = shortTitleText;
	}
	
		
	public java.lang.String sponsorCode;
	public java.lang.String getSponsorCode()
	{
		return sponsorCode;
	}
	public void setSponsorCode(java.lang.String sponsorCode)
	{
		this.sponsorCode = sponsorCode;
	}
	
		
	public java.lang.String sponsorMonitor;
	public java.lang.String getSponsorMonitor()
	{
		return sponsorMonitor;
	}
	public void setSponsorMonitor(java.lang.String sponsorMonitor)
	{
		this.sponsorMonitor = sponsorMonitor;
	}
	
		
	public java.lang.Long targetAccrualNumber;
	public java.lang.Long getTargetAccrualNumber()
	{
		return targetAccrualNumber;
	}
	public void setTargetAccrualNumber(java.lang.Long targetAccrualNumber)
	{
		this.targetAccrualNumber = targetAccrualNumber;
	}
	
	public java.lang.String ctomInsertUser;
	public java.lang.String getCtomInsertUser()
    {
        return  ctomInsertUser ;
    }
	public void setCtomInsertUser(java.lang.String ctomInsertUser)
	{
		this.ctomInsertUser = ctomInsertUser;
	}

		
	private java.util.Collection studyAgentCollection = new java.util.HashSet();
	public java.util.Collection getStudyAgentCollection()
	{
		if (studyAgentCollection==null || studyAgentCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.StudyAgent as child, gov.nih.nci.ctom.domain.Protocol as parent  where child in elements(parent.studyAgentCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.StudyAgent");				 
				studyAgentCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(Protocol.class.getName());
				log.error("Protocol:getStudyAgentCollection throws exception ... ...",ex);
			}
		}	
		return studyAgentCollection;
	}
	
	public void setStudyAgentCollection(java.util.Collection studyAgentCollection)
	{
		this.studyAgentCollection = studyAgentCollection;
	}	
		
	
	
	
		
		
	private java.util.Collection studySiteCollection = new java.util.HashSet();
	public java.util.Collection getStudySiteCollection()
	{
		if (studySiteCollection==null || studySiteCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.StudySite as child, gov.nih.nci.ctom.domain.Protocol as parent  where child in elements(parent.studySiteCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.StudySite");				 
				studySiteCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(Protocol.class.getName());
				log.error("Protocol:getStudySiteCollection throws exception ... ...",ex);
			}
		}	
		return studySiteCollection;
	}
	
	public void setStudySiteCollection(java.util.Collection studySiteCollection)
	{
		this.studySiteCollection = studySiteCollection;
	}	
		
	
	
	
		
		
	private java.util.Collection protocolStatusCollection = new java.util.HashSet();
	public java.util.Collection getProtocolStatusCollection()
	{
		if (protocolStatusCollection==null || protocolStatusCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.ProtocolStatus as child, gov.nih.nci.ctom.domain.Protocol as parent  where child in elements(parent.protocolStatusCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.ProtocolStatus");				 
				protocolStatusCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(Protocol.class.getName());
				log.error("Protocol:getProtocolStatusCollection throws exception ... ...",ex);
			}
		}	
		return protocolStatusCollection;
	}
	
	public void setProtocolStatusCollection(java.util.Collection protocolStatusCollection)
	{
		this.protocolStatusCollection = protocolStatusCollection;
	}	
		
	
	
	
		
		
	private java.util.Collection studyInvestigatorCollection = new java.util.HashSet();
	public java.util.Collection getStudyInvestigatorCollection()
	{
		if (studyInvestigatorCollection==null || studyInvestigatorCollection.getClass().getName().indexOf("PersistentSet")>0)		
		{
	      try 
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.StudyInvestigator as child, gov.nih.nci.ctom.domain.Protocol as parent  where child in elements(parent.studyInvestigatorCollection) and parent.id="+idString;
				HQLCriteria hqlCriteria = new HQLCriteria(hql);
				ApplicationService applicationService = ApplicationServiceProvider.getApplicationService();
				java.util.List resultList = applicationService.query(hqlCriteria,"gov.nih.nci.ctom.domain.StudyInvestigator");				 
				studyInvestigatorCollection = resultList;	 
			}
			catch(Exception ex) 
			{
				Logger log = Logger.getLogger(Protocol.class.getName());
				log.error("Protocol:getStudyInvestigatorCollection throws exception ... ...",ex);
			}
		}	
		return studyInvestigatorCollection;
	}
	
	public void setStudyInvestigatorCollection(java.util.Collection studyInvestigatorCollection)
	{
		this.studyInvestigatorCollection = studyInvestigatorCollection;
	}	
		
	

	public boolean equals(Object obj)
	{
		boolean eq = false;
		if(obj instanceof Protocol) 
		{
			Protocol c =(Protocol)obj; 			 
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