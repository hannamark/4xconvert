package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.webservices.convert.simple.Converters;
import gov.nih.nci.po.webservices.convert.simple.HealthCareFacilityConverter;
import gov.nih.nci.po.webservices.convert.simple.OrganizationConverter;
import gov.nih.nci.po.webservices.convert.simple.OrganizationSearchConverter;
import gov.nih.nci.po.webservices.convert.simple.OversightCommitteeConverter;
import gov.nih.nci.po.webservices.convert.simple.ResearchOrganizationConverter;
import gov.nih.nci.po.webservices.service.bo.HealthCareFacilityBoService;
import gov.nih.nci.po.webservices.service.bo.OrganizationBoService;
import gov.nih.nci.po.webservices.service.bo.OversightCommitteeBoService;
import gov.nih.nci.po.webservices.service.bo.ResearchOrganizationBoService;
import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.util.PoWSUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * This is the OrganizationService implementation class.
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
@Service("simpleOrganizationService")
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger LOG = Logger.getLogger(OrganizationServiceImpl.class);
    private static final String ORG_NOT_FOUND_IN_DB_MSG = " as Organization is not found in the DB.";
    private static final String ORG_ROLE_NOT_FOUND_IN_DB_MSG = " as OrganizationRole is not found in the DB.";
    private static final String CLASS_NULL_MSG = " as incoming Class is null.";
    private static final String PASS_IN_REQ = " is passed in the request.";
    private static final String RAW_TYPES = "rawtypes";
    private static final String UNCHECKED = "unchecked";

    @Autowired
    private OrganizationBoService organizationBoService;

    @Autowired
    private HealthCareFacilityBoService healthCareFacilityBoService;

    @Autowired
    private ResearchOrganizationBoService researchOrganizationBoService;

    @Autowired
    private OversightCommitteeBoService oversightCommitteeBoService;

    @Override
    public Organization createOrganization(Organization organization) {
        // null check for Organization
        if (organization == null) {
            LOG.error("The Organization couldn't be created as organization is null.");
            throw new ServiceException(
                    "The Organization couldn't be created as organization is null.");
        }

        gov.nih.nci.po.data.bo.Organization orgBo = null;
        long retOrgId = -1;
        String ctepId = organization.getCtepId();
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error("Organization couldn't be created as CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(
                    "Organization couldn't be created as CTEP ID " + ctepId + PASS_IN_REQ);
         }
        
        try {            
            // get the corresponding BO object
            OrganizationConverter oConverter = Converters.get(OrganizationConverter.class);
            orgBo = oConverter.convertFromJaxbToBO(organization);
            
            // call the BO service method
            retOrgId = organizationBoService.create(orgBo, null);
        } catch (EntityValidationException e) {
            LOG.error("Organization couldn't be created as data is invalid.", e);
            throw new ServiceException("Organization couldn't be created as data is invalid.", e);
        } catch (Exception e) {
            LOG.error("Exception occured while creating organization " + organization.getName() + ".", e);
            throw new ServiceException(
                    "Exception occured while creating organization " + organization.getName() + ".", e);
        }

        // get the Organization from the database & return it
        return getOrganization(retOrgId);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        // validate the request data
        validateUpdateOrgRequest(organization);

        gov.nih.nci.po.data.bo.Organization inOrgBo = null;       
        try {            
            // get the corresponding BO object
            OrganizationConverter oConverter = Converters.get(OrganizationConverter.class);
            inOrgBo = oConverter.convertFromJaxbToBO(organization);

            // call the BO service method
            organizationBoService.curate(inOrgBo, null);
        } catch (ServiceException e) {
            LOG.error(
                    "Exception occured while updating the organization having Id " + organization.getId() + ".", e);
            throw e;
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the organization having Id " + organization.getId() + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the organization having Id " + organization.getId() + ".", e);
        }

        // get the Organization from the database & return it
        return getOrganization(inOrgBo.getId());
    }

    @Override
    public Organization changeOrganizationStatus(long organizationID,
            EntityStatus status) {

        // get the OrganizationBO for given organizationID
        gov.nih.nci.po.data.bo.Organization orgBo = organizationBoService.getById(organizationID);
        if (orgBo == null) {
            LOG.error("Couldn't update the Organization Status for organizationID "
                    + organizationID + ORG_NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(
                    "Couldn't update the Organization Status for organizationID "
                            + organizationID + ORG_NOT_FOUND_IN_DB_MSG);
        }
        try {
            // set the Status
            orgBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.valueOf(status.value()));

            // call the EJB service method to update Organization status
            organizationBoService.curate(orgBo);
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the Status for organizationID " + organizationID + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the Status for organizationID " + organizationID + ".", e);
        }

        // get the Organization from the database & return it
        return getOrganization(organizationID);
    }

    @Override
    public Organization getOrganization(long organizationID) {
        // get the BO Organization for given organizationID
        gov.nih.nci.po.data.bo.Organization orgBo = organizationBoService.getById(organizationID);

        // Convert to get the corresponding JaxB object & then return it
        OrganizationConverter oConverter = Converters.get(OrganizationConverter.class);
        return oConverter.convertFromBOToJaxB(orgBo);
    }

    @Override
    public List<OrganizationSearchResult> searchOrganizations(
            OrganizationSearchCriteria osCriteria) {

        List<OrganizationSearchResult> osrList = new ArrayList<OrganizationSearchResult>();
        if (osCriteria == null) {
            // null check to avoid null pointer during isValid() check
            LOG.error("Organization search couldn't be performed as seach criteria is null.");
            throw new ServiceException("Organization search couldn't be performed as seach criteria is null.");
        }

        // get the corresponding BO object
        OrganizationSearchConverter osConverter = new OrganizationSearchConverter();
        gov.nih.nci.po.service.OrganizationSearchCriteria osCriteriaBo = osConverter
                .convertOSCFromJaxbToBO(osCriteria);

        try {
            // check that the search criteria is valid
            osCriteriaBo.isValid();
        } catch (OneCriterionRequiredException e) {
            LOG.error("Organization search couldn't be performed as search criteria is empty." + e);
            throw new ServiceException(
                    "Organization search couldn't be performed as search criteria is empty." + e);
        }

        // instantiate PageSortParams (in ascending order of ORGANIZATION_ID)
        PageSortParams<OrganizationSearchDTO> pageSortParams = new PageSortParams<OrganizationSearchDTO>(
                osCriteria.getLimit(), osCriteria.getOffset(), null, false, getDynamicSortCriteria());

        // call the EJB service method to search the Organizations
        List<OrganizationSearchDTO> osDtoList = organizationBoService.search(osCriteriaBo, pageSortParams);

        if (CollectionUtils.isNotEmpty(osDtoList)) {
            // Convert it to JaxB object & add in the list
            for (OrganizationSearchDTO osDto : osDtoList) {
                OrganizationSearchResult osResult = osConverter.convertOSRFromBOToJaxB(osDto);
                osrList.add(osResult);
            }
        }

        return osrList;
    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    @Override
    public OrganizationRole createOrganizationRole(OrganizationRole orgRole) {
        String msg = "The OrganizationRole couldn't be created as ";
        if (orgRole == null) {
            LOG.error(msg + " organizationRole is null.");
            throw new ServiceException(msg + "organizationRole is null.");
        }
        if (orgRole.getId() != null) {
            // If "Id" is present then curate will 'update' the DB
            LOG.error(msg + "organizationRoleId is present in the request.");
            throw new ServiceException(msg + "organizationRoleId is present in the request.");
        }

        String ctepId = getCtepId(orgRole);
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error(msg + "CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(msg + "CTEP ID " + ctepId + PASS_IN_REQ);                
         }
        
        Class<? extends OrganizationRole> clazz = orgRole.getClass();
        try {
            // get the corresponding BO (to be passed in 'curate' method)
            gov.nih.nci.po.data.bo.Correlation orgRoleBo = convertJaxbRoleToBO(orgRole);            

            // create OrganizationRole by calling EJB service method
            GenericStructrualRoleServiceLocal roleSerLocal = getGenericStructrualRoleServiceLocal(clazz);
            long id = roleSerLocal.create(orgRoleBo);

            // get OrganizationRole BO from DB after creation
            orgRoleBo = getOrganizationRoleBOByDBId(clazz, id);

            // convert from BO to JaxB & return it
            return convertFromBoRoleToJaxB(orgRoleBo);

        } catch (Exception e) {
            LOG.error(
                    "Exception occured while creating the OrganizationRole for organizationID "
                            + orgRole.getOrganizationId() + ".", e);
            throw new ServiceException(
                    "Exception occured while creating the OrganizationRole for organizationID "
                            + orgRole.getOrganizationId() + ".", e);
        }
    }

    @Override
    public OrganizationRole updateOrganizationRole(OrganizationRole orgRole) {

        // validate the request data and get the existing org role
        validateUpdateOrgRoleRequest(orgRole);
         
        gov.nih.nci.po.data.bo.Correlation inOrgRoleBo = null;
        try {
            // get the corresponding BO (to be passed in 'curate' method)
            inOrgRoleBo = convertJaxbRoleToBO(orgRole);

            // call the method to update the OrganizationRole
            updateOrgRole(inOrgRoleBo);

            // convert from BO to JaxB & return it
            return convertFromBoRoleToJaxB(inOrgRoleBo);
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the OrganizationRole for organizationID "
                            + orgRole.getOrganizationId() + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the OrganizationRole for organizationID "
                            + orgRole.getOrganizationId() + ".", e);
        }
    }

    @Override
    public List<OrganizationRole> getOrganizationRolesByOrgId(
            long organizationID) {
        // get the OrganizationBO for given organizationID
        gov.nih.nci.po.data.bo.Organization orgBo = organizationBoService.getById(organizationID);

        if (orgBo != null) {
            List<OrganizationRole> orgRoleList = new ArrayList<OrganizationRole>();

            // populate ResearchOrganization list
            populateROOrgRole(orgBo, orgRoleList);

            // populate OversightCommittee list
            populateOCOrgRole(orgBo, orgRoleList);

            // populate HealthCareFacility list
            populateHCFOrgRole(orgBo, orgRoleList);

            // return the orgRoleList
            return orgRoleList;
        }

        // If organization is not found in DB then throw ServiceException
        LOG.error("The OrganizationRole couldn't be fetched as organization with the given ID "
                + organizationID + " is not found.");
        throw new EntityNotFoundException(
                "The OrganizationRole couldn't be fetched as organization with the given ID "
                        + organizationID + " is not found.");
    }

    @Override
    public List<OrganizationRole> getOrganizationRolesByCtepId(String ctepId) {

        List<OrganizationRole> orgRoleList = new ArrayList<OrganizationRole>();

        if (StringUtils.isNotBlank(ctepId)) {
            gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
            assIden.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
            assIden.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
            assIden.setExtension(ctepId);

            // get HCF by CtepId and populate the list
            getAndPopulateHCFByCtepId(assIden, orgRoleList);

            // get RO by CtepId and populate the list
            getAndPopulateROByCtepId(assIden, orgRoleList);

            // Note-Don't search for OversightCommittee
        }

        return orgRoleList;
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public <T extends OrganizationRole> T getOrganizationRoleById(
            Class<T> clazz, long orgRoleID) {

        // get the OrganizationRoleBO from the DB
        gov.nih.nci.po.data.bo.Correlation orgRoleBo = getOrganizationRoleBOByDBId(
                clazz, orgRoleID);

        // check if OrganizationRole is found or not
        if (orgRoleBo != null) {
            // Convert from BO to JaxB
            return (T) convertFromBoRoleToJaxB(orgRoleBo);
        } else {
            // if OrganizationRole is not found
            return null;
        }
    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    @Override
    public <T extends OrganizationRole> T changeOrganizationRoleStatus(
            Class<T> clazz, long orgRoleID, EntityStatus status) {
        T retOrgRole = null;
        // get the OrganizationRole from the database
        gov.nih.nci.po.data.bo.Correlation orgRoleBo = getOrganizationRoleBOByDBId(
                clazz, orgRoleID);
        if (orgRoleBo == null) {
            LOG.error("Couldn't update the OrganizationRole Status for orgRoleID "
                    + orgRoleID + ORG_ROLE_NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(
                    "Couldn't update the OrganizationRole Status for orgRoleID "
                            + orgRoleID + ORG_ROLE_NOT_FOUND_IN_DB_MSG);
        }
        try {
            // set the status
            GenericStructrualRoleServiceLocal roleSerLocal = getGenericStructrualRoleServiceLocal(clazz);
            orgRoleBo.setStatus(PoWSUtil.getBORoleStatus(status.name()));

            // update/curate OrganizationRole by calling EJB service method
            roleSerLocal.curate(orgRoleBo);

            // convert from BO to JaxB
            retOrgRole = (T) convertFromBoRoleToJaxB(orgRoleBo);
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the OrganizationRole Status for orgRoleID "
                            + orgRoleID + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the OrganizationRole Status for orgRoleID "
                            + orgRoleID + ".", e);
        }

        return retOrgRole;
    }

    /**
     * This method is used to validate 'updateOrganization' request data.
     */
    private void validateUpdateOrgRequest(
            Organization organization) {
        // null check for Organization or OrganizationId
        if ((organization == null) || (organization.getId() == null)) {
            LOG.error("The Organization couldn't be updated as either organization or organizationId is null.");
            throw new ServiceException(
                    "The Organization couldn't be updated as either organization or organizationId is null.");
        }

        String ctepId = organization.getCtepId();
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error("Organization couldn't be updated as CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(
                    "Organization couldn't be updated as CTEP ID " + ctepId + PASS_IN_REQ);            
        }
        
        // get the OrganizationBO for given organizationID
        gov.nih.nci.po.data.bo.Organization existOrgBo = organizationBoService.getById(organization.getId());
        if (existOrgBo == null) {
            LOG.error("Couldn't update the Organization for organizationID "
                    + organization.getId() + ORG_NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(
                    "Couldn't update the Organization for organizationID "
                            + organization.getId() + ORG_NOT_FOUND_IN_DB_MSG);
        }
    }

    /**
     * This method is used to validate 'updateOrganizationRole' request data.
     */
    private void validateUpdateOrgRoleRequest(OrganizationRole orgRole) {
        String msg = "The OrganizationRole couldn't be updated ";

        // null check for OrganizationRole or OrganizationRoleId
        if ((orgRole == null) || (orgRole.getId() == null)) {
            LOG.error(msg + " as either organizationRole or organizationRoleId is null.");
            throw new ServiceException(msg + "as either organizationRole or organizationRoleId is null.");
        }
        
        String ctepId = getCtepId(orgRole);
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error(msg + "as CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(msg + "as CTEP ID " + ctepId + PASS_IN_REQ);                
         }

        Class<? extends OrganizationRole> clazz = orgRole.getClass();
        // get the OrganizationRole from the database
        gov.nih.nci.po.data.bo.Correlation existOrgRolBo = getOrganizationRoleBOByDBId(clazz, orgRole.getId());
        if (existOrgRolBo == null) {
            LOG.error(msg + "for ID " + orgRole.getId() + ORG_ROLE_NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(msg + "for ID " + orgRole.getId() + ORG_ROLE_NOT_FOUND_IN_DB_MSG);
        }
    }

    /**
     * This method is used to get OrganizationRoleBO by DB Id.
     */
    @SuppressWarnings(RAW_TYPES)
    private <T extends OrganizationRole> gov.nih.nci.po.data.bo.Correlation getOrganizationRoleBOByDBId(
            Class<T> clazz, long orgRoleID) {
        String msg = "Couldn't get OrganizationRoleBOByDBId for orgRoleID " + orgRoleID;
        if (clazz == null) {
            LOG.error(msg + orgRoleID + CLASS_NULL_MSG);
            throw new ServiceException(msg + orgRoleID + CLASS_NULL_MSG);
        }

        GenericStructrualRoleServiceLocal rolSerLocal = getGenericStructrualRoleServiceLocal(clazz);
        gov.nih.nci.po.data.bo.Correlation orgRoleBo = (gov.nih.nci.po.data.bo.Correlation) rolSerLocal
                .getById(orgRoleID);
        return orgRoleBo;
    }

    /**
     * This method is used to get the HCF by CtepId and then populate it in the
     * JaxB OrgRoleList.
     */
    private void getAndPopulateHCFByCtepId(gov.nih.nci.iso21090.Ii assIden,
            List<OrganizationRole> orgRoleList) {
        gov.nih.nci.po.data.bo.HealthCareFacility hcf = new gov.nih.nci.po.data.bo.HealthCareFacility();
        hcf.getOtherIdentifiers().add(assIden);
        // populate the SearchCriteria with the CtepId
        SearchCriteria<gov.nih.nci.po.data.bo.HealthCareFacility> hcfSearchCriteria = 
                new AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.HealthCareFacility>(hcf);
        // search for existing Records matching the CtepId
        List<gov.nih.nci.po.data.bo.HealthCareFacility> hcfList = healthCareFacilityBoService
                .search(hcfSearchCriteria);
        if (CollectionUtils.isNotEmpty(hcfList)) {
            HealthCareFacilityConverter hcfConverter = Converters.get(HealthCareFacilityConverter.class);
            for (gov.nih.nci.po.data.bo.HealthCareFacility hcfBo : hcfList) {
                gov.nih.nci.po.webservices.types.HealthCareFacility jaxbHCF = hcfConverter
                        .convertFromBOToJaxB(hcfBo);
                orgRoleList.add(jaxbHCF);
            }
        }
    }

    /**
     * This method is used to get the RO by CtepId and then populate it in the
     * JaxB OrgRoleList.
     */
    private void getAndPopulateROByCtepId(gov.nih.nci.iso21090.Ii assIden,
            List<OrganizationRole> orgRoleList) {

        gov.nih.nci.po.data.bo.ResearchOrganization ro = new gov.nih.nci.po.data.bo.ResearchOrganization();
        ro.getOtherIdentifiers().add(assIden);
        // populate the SearchCriteria with the CtepId
        SearchCriteria<gov.nih.nci.po.data.bo.ResearchOrganization> roSearchCriteria = 
                new AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.ResearchOrganization>(ro);
        // search for existing Records matching the CtepId
        List<gov.nih.nci.po.data.bo.ResearchOrganization> roList = researchOrganizationBoService
                .search(roSearchCriteria);
        if (CollectionUtils.isNotEmpty(roList)) {
            ResearchOrganizationConverter roConverter = Converters.get(ResearchOrganizationConverter.class);
            for (gov.nih.nci.po.data.bo.ResearchOrganization roBo : roList) {
                gov.nih.nci.po.webservices.types.ResearchOrganization jaxbRO = roConverter
                        .convertFromBOToJaxB(roBo);
                orgRoleList.add(jaxbRO);
            }
        }
    }

    /**
     * This method is used to update an Organizational Role(HCF, RO, OC).
     * 
     * @param inOrgRoleBo
     *            - incoming OrgRoleBO object
     * @throws JMSException
     * @throws Exception
     *             - incase any exception occurs
     */
    private void updateOrgRole(gov.nih.nci.po.data.bo.Correlation inOrgRoleBo) throws JMSException {
        if (inOrgRoleBo instanceof gov.nih.nci.po.data.bo.ResearchOrganization) {
            gov.nih.nci.po.data.bo.ResearchOrganization inROBo = 
                    (gov.nih.nci.po.data.bo.ResearchOrganization) inOrgRoleBo;
            researchOrganizationBoService.curate(inROBo, null); // curate
        } else if (inOrgRoleBo instanceof gov.nih.nci.po.data.bo.HealthCareFacility) {
            gov.nih.nci.po.data.bo.HealthCareFacility inHCFBo = 
                    (gov.nih.nci.po.data.bo.HealthCareFacility) inOrgRoleBo;
            healthCareFacilityBoService.curate(inHCFBo, null); // curate
        } else if (inOrgRoleBo instanceof gov.nih.nci.po.data.bo.OversightCommittee) {
            gov.nih.nci.po.data.bo.OversightCommittee ocBo = (gov.nih.nci.po.data.bo.OversightCommittee) inOrgRoleBo;
            oversightCommitteeBoService.curate(ocBo); // curate
        }
    }

    /**
     * This method is used to get the CtepId from the given OrganizationRole.
     */
    private String getCtepId(OrganizationRole orgRole) {
        String ctepId = "";
        if (orgRole instanceof ResearchOrganization) {
            ctepId = ((ResearchOrganization) orgRole).getCtepId();
        } else if (orgRole instanceof HealthCareFacility) {
            ctepId = ((HealthCareFacility) orgRole).getCtepId();
        }
        return ctepId;
    }

    /**
     * This method is used to convert JaxB into BO.
     */
    private gov.nih.nci.po.data.bo.Correlation convertJaxbRoleToBO(OrganizationRole jaxbOrgRole) {
        if (jaxbOrgRole instanceof ResearchOrganization) {
            ResearchOrganization ro = (ResearchOrganization) jaxbOrgRole;
            ResearchOrganizationConverter roConverter = Converters.get(ResearchOrganizationConverter.class);
            return roConverter.convertFromJaxbToBO(ro);
        } else if (jaxbOrgRole instanceof OversightCommittee) {
            OversightCommittee oc = (OversightCommittee) jaxbOrgRole;
            OversightCommitteeConverter ocConverter = Converters.get(OversightCommitteeConverter.class);
            return ocConverter.convertFromJaxbToBO(oc);
        } else if (jaxbOrgRole instanceof HealthCareFacility) {
            HealthCareFacility hcf = (HealthCareFacility) jaxbOrgRole;
            HealthCareFacilityConverter hcfConverter = Converters.get(HealthCareFacilityConverter.class);
            return hcfConverter.convertFromJaxbToBO(hcf);
        } else {
            throw new ServiceException("Can't convert JaxB-OrganizationRole to BO as type is wrong.");
        }
    }

    /**
     * This method is used to convert BO Role into JaxB.
     */
    private OrganizationRole convertFromBoRoleToJaxB(gov.nih.nci.po.data.bo.Correlation orgRoleBo) {
        if (orgRoleBo instanceof gov.nih.nci.po.data.bo.ResearchOrganization) {
            gov.nih.nci.po.data.bo.ResearchOrganization roBo = (gov.nih.nci.po.data.bo.ResearchOrganization) orgRoleBo;
            ResearchOrganizationConverter roConverter = Converters.get(ResearchOrganizationConverter.class);
            return roConverter.convertFromBOToJaxB(roBo);
        } else if (orgRoleBo instanceof gov.nih.nci.po.data.bo.OversightCommittee) {
            gov.nih.nci.po.data.bo.OversightCommittee ocBo = (gov.nih.nci.po.data.bo.OversightCommittee) orgRoleBo;
            OversightCommitteeConverter ocConverter = Converters.get(OversightCommitteeConverter.class);
            return ocConverter.convertFromBOToJaxB(ocBo);
        } else if (orgRoleBo instanceof gov.nih.nci.po.data.bo.HealthCareFacility) {
            gov.nih.nci.po.data.bo.HealthCareFacility hcfBo = (gov.nih.nci.po.data.bo.HealthCareFacility) orgRoleBo;
            HealthCareFacilityConverter hcfConverter = Converters.get(HealthCareFacilityConverter.class);
            return hcfConverter.convertFromBOToJaxB(hcfBo);
        } else {
            throw new ServiceException("Can't convert OrganizationRoleBO to JaxB as type is wrong.");
        }
    }

    @SuppressWarnings(RAW_TYPES)
    private <T extends OrganizationRole> GenericStructrualRoleServiceLocal getGenericStructrualRoleServiceLocal(
            Class<T> orgRoleClass) {
        GenericStructrualRoleServiceLocal gsRolSerLocal = null;
        if (ResearchOrganization.class.isAssignableFrom(orgRoleClass)) {
            gsRolSerLocal = researchOrganizationBoService;
        } else if (OversightCommittee.class.isAssignableFrom(orgRoleClass)) {
            gsRolSerLocal = oversightCommitteeBoService;
        } else if (HealthCareFacility.class.isAssignableFrom(orgRoleClass)) {
            gsRolSerLocal = healthCareFacilityBoService;
        }

        return gsRolSerLocal;
    }

    /**
     * This method is used to populate JaxB RO-RoleList from the OrganizationBO.
     */
    private void populateROOrgRole(gov.nih.nci.po.data.bo.Organization orgBo,
            Collection<OrganizationRole> orgRoleList) {
        if (CollectionUtils.isNotEmpty(orgBo.getResearchOrganizations())) {
            ResearchOrganizationConverter roConverter = Converters.get(ResearchOrganizationConverter.class);

            for (gov.nih.nci.po.data.bo.ResearchOrganization roBo : orgBo.getResearchOrganizations()) {
                ResearchOrganization hcp = roConverter.convertFromBOToJaxB(roBo);
                orgRoleList.add(hcp);
            }
        }
    }

    /**
     * This method is used to populate JaxB OC-RoleList from the OrganizationBO.
     */
    private void populateOCOrgRole(gov.nih.nci.po.data.bo.Organization orgBo,
            Collection<OrganizationRole> orgRoleList) {
        if (CollectionUtils.isNotEmpty(orgBo.getOversightCommittees())) {
            OversightCommitteeConverter roConverter = Converters.get(OversightCommitteeConverter.class);

            for (gov.nih.nci.po.data.bo.OversightCommittee roBo : orgBo.getOversightCommittees()) {
                OversightCommittee hcp = roConverter.convertFromBOToJaxB(roBo);
                orgRoleList.add(hcp);
            }
        }
    }

    /**
     * This method is used to populate JaxB HCF-RoleList from the
     * OrganizationBO.
     */
    private void populateHCFOrgRole(gov.nih.nci.po.data.bo.Organization orgBo,
            Collection<OrganizationRole> orgRoleList) {
        if (CollectionUtils.isNotEmpty(orgBo.getHealthCareFacilities())) {
            HealthCareFacilityConverter hcfConverter = Converters.get(HealthCareFacilityConverter.class);

            for (gov.nih.nci.po.data.bo.HealthCareFacility hcfBo : orgBo.getHealthCareFacilities()) {
                HealthCareFacility hcp = hcfConverter.convertFromBOToJaxB(hcfBo);
                orgRoleList.add(hcp);
            }
        }
    }

    private List<String> getDynamicSortCriteria() {
        List<String> dscList = new ArrayList<String>();
        dscList.add("ID");
        return dscList;
    }


    /*
     * GETTERS AND SETTERS
     */

    /**
     * @return The organization BO service.
     */
    public OrganizationBoService getOrganizationBoService() {
        return organizationBoService;
    }

    /**
     *
     * @param organizationBoService The organization BO service.
     */
    public void setOrganizationBoService(OrganizationBoService organizationBoService) {
        this.organizationBoService = organizationBoService;
    }

    /**
     * @return The HealthCareFacility BO service.
     */
    public HealthCareFacilityBoService getHealthCareFacilityBoService() {
        return healthCareFacilityBoService;
    }

    /**
     *
     * @param healthCareFacilityBoService The HealthCareFacility BO service.
     */
    public void setHealthCareFacilityBoService(HealthCareFacilityBoService healthCareFacilityBoService) {
        this.healthCareFacilityBoService = healthCareFacilityBoService;
    }

    /**
     * @return The ResearchOrganization BO service.
     */
    public ResearchOrganizationBoService getResearchOrganizationBoService() {
        return researchOrganizationBoService;
    }

    /**
     *
     * @param researchOrganizationBoService The ResearchOrganization BO service.
     */
    public void setResearchOrganizationBoService(ResearchOrganizationBoService researchOrganizationBoService) {
        this.researchOrganizationBoService = researchOrganizationBoService;
    }

    /**
     * @return The OversightCommittee BO service.
     */
    public OversightCommitteeBoService getOversightCommitteeBoService() {
        return oversightCommitteeBoService;
    }

    /**
     * @param oversightCommitteeBoService The OversightCommittee BO service.
     */
    public void setOversightCommitteeBoService(OversightCommitteeBoService oversightCommitteeBoService) {
        this.oversightCommitteeBoService = oversightCommitteeBoService;
    }

}
