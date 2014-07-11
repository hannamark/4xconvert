package gov.nih.nci.services;


import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.ejb.AuthorizationInterceptor;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Entity;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.convert.CorrelationNodeDTOConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.CorrelationSortCriterion;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationSortCriterion;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.PersonSortCriterion;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoHibernateSessionInterceptor;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import org.jboss.ejb3.annotation.SecurityDomain;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private static final String DEFAULT_ROLE_ALLOWED_GRID_CLIENT = "gridClient";
    private static final String DEFAULT_ROLE_ALLOWED_CLIENT = "client";
    private OrganizationServiceLocal orgService;
    private PersonServiceLocal personService;
    private ResearchOrganizationServiceLocal researchOrgService;
    private static Map<String, GenericStructrualRoleServiceLocal<?>> rootToCorrelationMap = 
        new HashMap<String, GenericStructrualRoleServiceLocal<?>>();
    private static Map<String, GenericStructrualRoleServiceLocal<?>> roleToCorrelationMap = 
        new HashMap<String, GenericStructrualRoleServiceLocal<?>>();
    private static Map<String, String> classNameToRoleName = new HashMap<String, String>();
    
    static {
        classNameToRoleName.put(ClinicalResearchStaffDTO.class.getName(), RoleList.CLINICAL_RESEARCH_STAFF.name());
        classNameToRoleName.put(HealthCareProviderDTO.class.getName(), RoleList.HEALTH_CARE_PROVIDER.name());
        classNameToRoleName.put(IdentifiedPersonDTO.class.getName(), RoleList.IDENTIFIED_PERSON.name());
        classNameToRoleName.put(IdentifiedOrganizationDTO.class.getName(), RoleList.IDENTIFIED_ORGANIZATION.name());
        classNameToRoleName.put(HealthCareFacilityDTO.class.getName(), RoleList.HEALTH_CARE_FACILITY.name());
        classNameToRoleName.put(OversightCommitteeDTO.class.getName(), RoleList.OVERSIGHT_COMMITTEE.name());
        classNameToRoleName.put(ResearchOrganizationDTO.class.getName(), RoleList.RESEARCH_ORGANIZATION.name());
        classNameToRoleName.put(OrganizationalContactDTO.class.getName(), RoleList.ORGANIZATIONAL_CONTACT.name());
    }

    /**
     * @param crsService the crsService to set
     */
    @EJB
    public void setCrsService(ClinicalResearchStaffServiceLocal crsService) {
        rootToCorrelationMap.put(IdConverter.CLINICAL_RESEARCH_STAFF_ROOT, crsService);
        roleToCorrelationMap.put(RoleList.CLINICAL_RESEARCH_STAFF.name(), crsService);
    }



    /**
     * @param hcfService the hcfService to set
     */
    @EJB
    public void setHcfService(HealthCareFacilityServiceLocal hcfService) {
        rootToCorrelationMap.put(IdConverter.HEALTH_CARE_FACILITY_ROOT, hcfService);
        roleToCorrelationMap.put(RoleList.HEALTH_CARE_FACILITY.name(), hcfService);
    }

    /**
     * @param hcpService the hcpService to set
     */
    @EJB
    public void setHcpService(HealthCareProviderServiceLocal hcpService) {
        rootToCorrelationMap.put(IdConverter.HEALTH_CARE_PROVIDER_ROOT, hcpService);
        roleToCorrelationMap.put(RoleList.HEALTH_CARE_PROVIDER.name(), hcpService);
    }

    /**
     * @param idfOrgService the idfOrgService to set
     */
    @EJB
    public void setIdfOrgService(IdentifiedOrganizationServiceLocal idfOrgService) {
        rootToCorrelationMap.put(IdConverter.IDENTIFIED_ORG_ROOT, idfOrgService);
        roleToCorrelationMap.put(RoleList.IDENTIFIED_ORGANIZATION.name(), idfOrgService);
    }

    /**
     * @param idfPerService the idfPerService to set
     */
    @EJB
    public void setIdfPerService(IdentifiedPersonServiceLocal idfPerService) {
        rootToCorrelationMap.put(IdConverter.IDENTIFIED_PERSON_ROOT, idfPerService);
        roleToCorrelationMap.put(RoleList.IDENTIFIED_PERSON.name(), idfPerService);
    }


    /**
     * @param orgContService the orgContService to set
     */
    @EJB
    public void setOrgContService(OrganizationalContactServiceLocal orgContService) {
        rootToCorrelationMap.put(IdConverter.ORGANIZATIONAL_CONTACT_ROOT, orgContService);
        roleToCorrelationMap.put(RoleList.ORGANIZATIONAL_CONTACT.name(), orgContService);
    }

    /**
     * @param ovSightCommService the ovSightCommService to set
     */
    @EJB
    public void setOvSightCommService(OversightCommitteeServiceLocal ovSightCommService) {
        rootToCorrelationMap.put(IdConverter.OVERSIGHT_COMMITTEE_ROOT, ovSightCommService);
        roleToCorrelationMap.put(RoleList.OVERSIGHT_COMMITTEE.name(), ovSightCommService);
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
        rootToCorrelationMap.put(IdConverter.RESEARCH_ORG_ROOT, researchOrgService);
        roleToCorrelationMap.put(RoleList.RESEARCH_ORGANIZATION.name(), researchOrgService);
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
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
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
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, Bl player, Bl scoper) 
        throws NullifiedRoleException {
        Correlation correlation = getCorrelationService(id).getById(IiConverter.convertToLong(id));
        if (correlation == null) {
            return null;
        } else {
            return CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(correlation, player.getValue(), 
                    scoper.getValue());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<CorrelationNodeDTO> getCorrelationsByIdsWithEntities(Ii[] ids, Bl player, Bl scoper) 
        throws NullifiedRoleException {
        List<CorrelationNodeDTO> nodeList = new ArrayList<CorrelationNodeDTO>();
        if (ids.length > 0) {
            Set<Long> longIds = IiConverter.convertToLongs(ids);
            
            List<? extends Correlation> correlations = getCorrelationService(ids[0])
                .getByIds(longIds.toArray(new Long[longIds.size()]));
            for (Correlation corr : correlations) {       
                CorrelationNodeDTO node = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(corr, 
                        player.getValue(), scoper.getValue());
                if (node != null) {
                    nodeList.add(node);
                }
            }
        }
        return nodeList;
    }
   
    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
    public List<CorrelationNodeDTO> getCorrelationsByPlayerIdsWithEntities(Cd correlationType, 
            Ii[] playerIds, Bl player, Bl scoper) throws NullifiedRoleException {
        List<CorrelationNodeDTO> nodeList = new ArrayList<CorrelationNodeDTO>();
        Set<Long> longIds = IiConverter.convertToLongs(playerIds);
        List<? extends Correlation> correlations = 
            getCorrelationService(correlationType).getByPlayerIds(longIds.toArray(new Long[longIds.size()]));
        for (Correlation corr : correlations) {
            
            CorrelationNodeDTO node = CorrelationNodeDTOConverter.convertToCorrelationNodeDTO(corr, player.getValue(),
                    scoper.getValue());
            if (node != null) {
                nodeList.add(node);
            }
        }
        return nodeList;
    }
    
    
   

    private GenericStructrualRoleServiceLocal<?> getCorrelationService(Ii id)  {
        return rootToCorrelationMap.get(id.getRoot());
    }

    private GenericStructrualRoleServiceLocal<?> getCorrelationService(Cd corrType)  {
        return roleToCorrelationMap.get(corrType.getCode());
    }
    
    private GenericStructrualRoleServiceLocal<?> getCorrelationService(String corrType) {
        return roleToCorrelationMap.get(corrType);
    }

   /**
    * {@inheritDoc}
    */
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
   public List<CorrelationNodeDTO> searchCorrelationsWithEntities(CorrelationNodeDTO searchNode, Bl player, Bl scoper,
           LimitOffset limitOffset) throws TooManyResultsException {
       
       if (searchNode == null) {
           throw new IllegalArgumentException("searchNode may not be null");
       }
       if (searchNode.getCorrelation() == null) {
           throw new IllegalArgumentException("searchNode correlation may not be null");
       }
             
       SearchCriteria sc = createSearchCriteria(searchNode);
       
       String corrType = classNameToRoleName.get(searchNode.getCorrelation().getClass().getName());
      
       List<Correlation> correlations = null;
       if (limitOffset != null) {
           int maxLimit = Math.min(limitOffset.getLimit(), Utils.MAX_SEARCH_RESULTS + 1);
           PageSortParams params = 
               new PageSortParams<Correlation>(maxLimit, limitOffset.getOffset(),
                       CorrelationSortCriterion.ID, false);
           correlations = (List<Correlation>) getCorrelationService(corrType)
           .search(sc, params);
       } else {
           correlations = (List<Correlation>) getCorrelationService(corrType)
           .search(sc);
       }
       return  CorrelationNodeDTOConverter.convertToCorrelationNodeDTOList(
               correlations, player.getValue(), scoper.getValue());
   }
   
   /**
    * {@inheritDoc}
    */
   @TransactionAttribute(TransactionAttributeType.SUPPORTS)
   @RolesAllowed({DEFAULT_ROLE_ALLOWED_CLIENT, DEFAULT_ROLE_ALLOWED_GRID_CLIENT })
   public List<EntityNodeDto> searchEntitiesWithCorrelations(EntityNodeDto searchNode, Cd[] players, Cd[] scopers,
           LimitOffset limitOffset) throws TooManyResultsException {
       
       if (searchNode == null) {
           throw new IllegalArgumentException("searchNode may not be null");
       }
       if (searchNode.getEntityDto() == null) {
           throw new IllegalArgumentException("searchNode entity may not be null");
       }
       
       Entity entity = EntityNodeDtoConverter.convertToEntity(searchNode);
       
       List entities = null;
       if (limitOffset != null) {
           int maxLimit = Math.min(limitOffset.getLimit(), Utils.MAX_SEARCH_RESULTS + 1);
           if (entity instanceof Person) {
               SearchCriteria<Person> sc = new AnnotatedBeanSearchCriteria<Person>((Person) entity);
               PageSortParams<Person> params = new PageSortParams<Person>(maxLimit, limitOffset.getOffset(),
                       PersonSortCriterion.PERSON_ID, false);
               entities = personService.search(sc, params);
           } else {
               SearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>((Organization) entity);
               PageSortParams<Organization> params = new PageSortParams<Organization>(maxLimit, limitOffset.getOffset(),
                       OrganizationSortCriterion.ORGANIZATION_ID, false);
               entities = orgService.search(sc, params);
           }
       } else {
           if (entity instanceof Person) {
               SearchCriteria<Person> sc = new AnnotatedBeanSearchCriteria<Person>((Person) entity);
               entities = personService.search(sc);
           } else {
               SearchCriteria<Organization> sc = new AnnotatedBeanSearchCriteria<Organization>((Organization) entity);
               entities = orgService.search(sc);
           }
       }
              
       return  EntityNodeDtoConverter.convertToEntityNodeDtoList(
               entities, players, scopers);
   }

   private SearchCriteria createSearchCriteria(CorrelationNodeDTO cnDto) {
       Correlation cor = CorrelationNodeDTOConverter.convertFromCorrelationNodeDTO(cnDto);
       return new AnnotatedBeanSearchCriteria(cor);
   }

}
