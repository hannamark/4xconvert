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
   * The status and date associated with a status change of a protocol.
   */

public  class ProtocolStatus 	implements java.io.Serializable 
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
	
		
	public java.lang.String statusCode;
	public java.lang.String getStatusCode()
	{
		return statusCode;
	}
	public void setStatusCode(java.lang.String statusCode)
	{
		this.statusCode = statusCode;
	}
	
		
	public java.util.Date statusDate;
	public java.util.Date getStatusDate()
	{
		return statusDate;
	}
	public void setStatusDate(java.util.Date statusDate)
	{
		this.statusDate = statusDate;
	}
	
	
		
		
	private gov.nih.nci.ctom.domain.Protocol protocol;
	public gov.nih.nci.ctom.domain.Protocol getProtocol()
	{
			
		if(protocol==null ||  protocol.getClass().getName().indexOf('$')>0)
		{			
			try
			{
				String idString = (Class.forName("java.lang.String").isInstance(getId()))? "'"+ getId() + "'" : ""+getId(); 
				String hql = "select child from gov.nih.nci.ctom.domain.Protocol as child where child.id in (select parent.protocol.id from gov.nih.nci.ctom.domain.ProtocolStatus as parent where parent.id="+idString+")";
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
				Logger log = Logger.getLogger(ProtocolStatus.class.getName());
				log.error("ProtocolStatus:getProtocol throws exception ... ...",ex);
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
		if(obj instanceof ProtocolStatus) 
		{
			ProtocolStatus c =(ProtocolStatus)obj; 			 
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