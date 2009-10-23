package gov.nih.nci.services;


import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Entity;
import gov.nih.nci.po.data.convert.CorrelationNodeDTOConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.jboss.annotation.security.SecurityDomain;

import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;

/**
 * Remote Service that contains methods which scope extends a simple entity. 
 *
 * @author ludetc
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({ AuthorizationInterceptor.class, PoHibernateSessionInterceptor.class,
    NullifiedEntityNodeInterceptor.class, NullifiedCorrelationNodeInterceptor.class })
@SecurityDomain("po")
public class BusinessServiceBean implements BusinessServiceRemote {

    private static final String DEFAULT_METHOD_ACCESS_ROLE = "client";
    
    private OrganizationServiceLocal orgService;
    private PersonServiceLocal personService;
    private ClinicalResearchStaffServiceLocal crsService;
    private HealthCareFacilityServiceLocal hcfService;
    private HealthCareProviderServiceLocal hcpService;
    private IdentifiedOrganizationServiceLocal idfOrgService;
    private IdentifiedPersonServiceLocal idfPerService;
    private OrganizationalContactServiceLocal orgContService;
    private OversightCommitteeServiceLocal ovSightCommService;
    private ResearchOrganizationServiceLocal researchOrgService;
    private static Map<String, GenericStructrualRoleServiceLocal<?>> rootToCorrelationMap = 
        new HashMap<String, GenericStructrualRoleServiceLocal<?>>();
    private static Map<String, GenericStructrualRoleServiceLocal<?>> roleToCorrelationMap = 
        new HashMap<String, GenericStructrualRoleServiceLocal<?>>();
    
    /**
     * @return the crsService
     */
    public ClinicalResearchStaffServiceLocal getCrsService() {
        return crsService;
    }

    /**
     * @param crsService the crsService to set
     */
    @EJB
    public void setCrsService(ClinicalResearchStaffServiceLocal crsService) {
        this.crsService = crsService;
        rootToCorrelationMap.put(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT, this.crsService);
        roleToCorrelationMap.put(RoleList.CLINICAL_RESEARCH_STAFF.name(), this.crsService);
    }

    /**
     * @return the hcfService
     */
    public HealthCareFacilityServiceLocal getHcfService() {
        return hcfService;
    }

    /**
     * @param hcfService the hcfService to set
     */
    @EJB
    public void setHcfService(HealthCareFacilityServiceLocal hcfService) {
        this.hcfService = hcfService;
        rootToCorrelationMap.put(IdConverter.HEALTH_CARE_FACILITY_ROOT, this.hcfService);
        roleToCorrelationMap.put(RoleList.HEALTH_CARE_FACILITY.name(), this.hcfService);
    }

    /**
     * @return the hcpService
     */
    public HealthCareProviderServiceLocal getHcpService() {
        return hcpService;
    }

    /**
     * @param hcpService the hcpService to set
     */
    @EJB
    public void setHcpService(HealthCareProviderServiceLocal hcpService) {
        this.hcpService = hcpService;
        rootToCorrelationMap.put(IdConverter.HEALTH_CARE_PROVIDER_ROOT, this.hcpService);
        roleToCorrelationMap.put(RoleList.HEALTH_CARE_PROVIDER.name(), this.hcpService);
    }

    /**
     * @return the idfOrgService
     */
    public IdentifiedOrganizationServiceLocal getIdfOrgService() {
        return idfOrgService;
    }

    /**
     * @param idfOrgService the idfOrgService to set
     */
    @EJB
    public void setIdfOrgService(IdentifiedOrganizationServiceLocal idfOrgService) {
        this.idfOrgService = idfOrgService;
        rootToCorrelationMap.put(IdConverter.IDENTIFIED_ORG_ROOT, this.idfOrgService);
        roleToCorrelationMap.put(RoleList.IDENTIFIED_ORGANIZATION.name(), this.idfOrgService);
    }

    /**
     * @return the idfPerService
     */
    public IdentifiedPersonServiceLocal getIdfPerService() {
        return idfPerService;
    }

    /**
     * @param idfPerService the idfPerService to set
     */
    @EJB
    public void setIdfPerService(IdentifiedPersonServiceLocal idfPerService) {
        this.idfPerService = idfPerService;
        rootToCorrelationMap.put(IdConverter.IDENTIFIED_PERSON_ROOT, this.idfPerService);
        roleToCorrelationMap.put(RoleList.IDENTIFIED_PERSON.name(), this.idfPerService);
    }

    /**
     * @return the orgContService
     */
    public OrganizationalContactServiceLocal getOrgContService() {
        return orgContService;
    }

    /**
     * @param orgContService the orgContService to set
     */
    @EJB
    public void setOrgContService(OrganizationalContactServiceLocal orgContService) {
        this.orgContService = orgContService;
        rootToCorrelationMap.put(IdConverter.OVERSIGHT_COMMITTEE_ROOT, this.ovSightCommService);
        roleToCorrelationMap.put(RoleList.OVERSIGHT_COMMITTEE.name(), this.ovSightCommService);
    }

    /**
     * @return the ovSightCommService
     */
    public OversightCommitteeServiceLocal getOvSightCommService() {
        return ovSightCommService;
    }

    /**
     * @param ovSightCommService the ovSightCommService to set
     */
    @EJB
    public void setOvSightCommService(OversightCommitteeServiceLocal ovSightCommService) {
        this.ovSightCommService = ovSightCommService;
        rootToCorrelationMap.put(IdConverter.OVERSIGHT_COMMITTEE_ROOT, this.ovSightCommService);
        roleToCorrelationMap.put(RoleList.OVERSIGHT_COMMITTEE.name(), this.ovSightCommService);
    }

    /**
     * @return the researchOrgService
     */
    public ResearchOrganizationServiceLocal getResearchOrgService() {
        return researchOrgService;
    }

    /**
     * @param researchOrgService the researchOrgService to set
     */
    @EJB
    public void setResearchOrgService(ResearchOrganizationServiceLocal researchOrgService) {
        this.researchOrgService = researchOrgService;
        rootToCorrelationMap.put(IdConverter.RESEARCH_ORG_ROOT, this.researchOrgService);
        roleToCorrelationMap.put(RoleList.RESEARCH_ORGANIZATION.name(), this.researchOrgService);
    }
    
    /**
     * @param svc service, injected
     */
    @EJB
    public void setOrganizationServiceBean(OrganizationServiceLocal svc) {
        this.orgService = svc;
    }

    /**
     * @return orgService that was injected by container.
     */
    public OrganizationServiceLocal getOrganizationServiceBean() {
        return this.orgService;
    }

    /**
     * @param svc service, injected
     */
    @EJB
    public void setPersonServiceBean(PersonServiceLocal svc) {
        this.personService = svc;
    }
    
    /**
     * @return orgService that was injected by container.
     */
    public PersonServiceLocal getPersonServiceBean() {
        return this.personService;
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public EntityNodeDto getEntityByIdWithCorrelations(Ii id, Cd[] players, Cd[] scopers) 
    throws NullifiedEntityException {       

        Entity entity = null;
        if (IdConverter.PERSON_ROOT.equals(id.getRoot())) {        
            entity = personService.getById(IiConverter.convertToLong(id));
        } else {
            entity = orgService.getById(IiConverter.convertToLong(id));      
        }
        if (entity == null) {
            return null;
        } else {
            return EntityNodeDtoConverter.convertToEntityNodeDto(entity, players, scopers);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, boolean player, boolean scoper) 
        throws NullifiedRoleException {
        Correlation correlation = getCorrelationService(id).getById(IiConverter.convertToLong(id));
        if (correlation == null) {
            return null;
        } else {
            return CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(correlation, player, scoper);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public CorrelationNodeDTO[] getCorrelationsByIdsWithEntities(Ii[] ids, boolean player, boolean scoper) 
        throws NullifiedRoleException {
        List<CorrelationNodeDTO> nodeList = new ArrayList<CorrelationNodeDTO>();
        if (ids.length < 1) {
            return new CorrelationNodeDTO[0];
        }
        
        Set<Long> longIds = IiConverter.convertToLongs(ids);
        
        List<? extends Correlation> correlations = getCorrelationService(ids[0])
            .getByIds(longIds.toArray(new Long[longIds.size()]));
        for (Correlation corr : correlations) {       
            CorrelationNodeDTO node = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(corr, player, scoper);
            if (node != null) {
                nodeList.add(node);
            }
        }
        return CorrelationNodeDTOConverter.listToArray(nodeList);
    }
   
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed(DEFAULT_METHOD_ACCESS_ROLE)
    public CorrelationNodeDTO[] getCorrelationsByPlayerIdsWithEntities(Cd correlationType, 
            Ii[] playerIds, boolean player, boolean scoper) throws NullifiedRoleException {
        List<CorrelationNodeDTO> nodeList = new ArrayList<CorrelationNodeDTO>();
        Set<Long> longIds = IiConverter.convertToLongs(playerIds);
        List<? extends Correlation> correlations = 
            getCorrelationService(correlationType).getByPlayerIds(longIds.toArray(new Long[longIds.size()]));
        for (Correlation corr : correlations) {
            
            CorrelationNodeDTO node = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(corr, player, scoper);
            if (node != null) {
                nodeList.add(node);
            }
        }
        return CorrelationNodeDTOConverter.listToArray(nodeList);
    }
    
    
   
    
    private GenericStructrualRoleServiceLocal<?> getCorrelationService(Ii id)  {
        
        return rootToCorrelationMap.get(id.getRoot());
    }
    
   private GenericStructrualRoleServiceLocal<?> getCorrelationService(Cd corrType)  {
        
        return roleToCorrelationMap.get(corrType.getCode());
    }
   
   


}
