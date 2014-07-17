package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.FamilySortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.convert.simple.Converters;
import gov.nih.nci.po.webservices.convert.simple.FamilyConverter;
import gov.nih.nci.po.webservices.convert.simple.FamilyMemberConverter;
import gov.nih.nci.po.webservices.convert.simple.FamilyMemberRelationshipConverter;
import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import gov.nih.nci.services.Utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * This is the FamilyService implementation class.
 * 
 * @author Rohit Gupta
 * 
 */
@Service("simpleFamilyService")
public class FamilyServiceImpl implements FamilyService {

    private static final Logger LOG = Logger.getLogger(FamilyServiceImpl.class);
    private static final String DOESNT_EXIST = " doesn't exist.";

    @Override
    public List<Family> searchFamiliesByName(String name) {
        if (StringUtils.isBlank(name)) {
            LOG.error("Family search couldn't be performed as family name is null.");
            throw new ServiceException(
                    "Family search couldn't be performed as family name is null.");
        }

        try {
            gov.nih.nci.po.data.bo.Family familyBo = new gov.nih.nci.po.data.bo.Family();
            familyBo.setName(name);

            // call method to search families
            return searchFamilies(familyBo);
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while performing searchFamiliesByName() for name: "
                            + name + " .", e);
            throw new ServiceException(
                    "Exception occured while performing searchFamiliesByName() for name: "
                            + name + " .", e);
        }
    }

    @Override
    public List<Family> searchFamiliesByOrgId(long organizationId) {

        // get the OrganizationBO for given organizationId
        gov.nih.nci.po.data.bo.Organization orgBo = PoRegistry
                .getOrganizationService().getById(organizationId);
        if (orgBo == null) {
            LOG.error("Family search couldn't be performed as Organization for Id: "
                    + organizationId + DOESNT_EXIST);
            throw new EntityNotFoundException(
                    "Family search couldn't be performed as Organization for Id: "
                            + organizationId + DOESNT_EXIST);
        }

        try {
            FamilyOrganizationRelationship famOrgRel = new FamilyOrganizationRelationship();
            famOrgRel.setOrganization(orgBo);
            gov.nih.nci.po.data.bo.Family familyBo = new gov.nih.nci.po.data.bo.Family();
            familyBo.getFamilyOrganizationRelationships().add(famOrgRel);

            // call method to search families
            return searchFamilies(familyBo);
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while performing searchFamiliesByOrgId() for Id: "
                            + organizationId + " .", e);
            throw new ServiceException(
                    "Exception occured while performing searchFamiliesByOrgId() for Id: "
                            + organizationId + " .", e);
        }

    }
    
    @Override
    public Family getFamily(long familyId) {
        // get the FamilyBO for given familyId
        gov.nih.nci.po.data.bo.Family familyBo = PoRegistry.getFamilyService().getById(familyId);
        FamilyConverter fConverter = Converters.get(FamilyConverter.class);
        Family family = fConverter.convertFromBOToJaxB(familyBo);
        
        return family;
    }
    
    @Override
    public FamilyMember getFamilyMember(long familyMemberId) {
        
        FamilyOrganizationRelationship famOrgRelBo = PoRegistry.getFamilyOrganizationRelationshipService()
                .getById(familyMemberId);
        FamilyMemberConverter fmConverter = new FamilyMemberConverter();
        FamilyMember famMem = fmConverter.convertFromBOToJaxB(famOrgRelBo);
        
        return famMem;
    }

    @Override
    public List<FamilyMemberRelationship> getFamilyMemberRelationshipsByFamilyId(
            long familyId) {

        // get the FamilyBO for given familyId
        gov.nih.nci.po.data.bo.Family familyBo = PoRegistry.getFamilyService()
                .getById(familyId);
        if (familyBo == null) {
            LOG.error("FamilyMemberRelationship couldn't be fetched as Family for Id: "
                    + familyId + DOESNT_EXIST);
            throw new EntityNotFoundException(
                    "FamilyMemberRelationship couldn't be fetched as Family for Id: "
                            + familyId + DOESNT_EXIST);
        }

        try {
            // list to return
            List<FamilyMemberRelationship> fmrList = new ArrayList<FamilyMemberRelationship>();

            // populate search criteria
            OrganizationRelationship orgRel = new OrganizationRelationship();
            orgRel.setFamily(familyBo);
            AnnotatedBeanSearchCriteria<OrganizationRelationship> sc = 
                    new AnnotatedBeanSearchCriteria<OrganizationRelationship>(orgRel);

            // call EJB service method to search for FamilyMemberRelationships
            List<OrganizationRelationship> orgRelBoList = PoRegistry
                    .getOrganizationRelationshipService().search(sc);

            // Iterate the result & populate the List
            if (CollectionUtils.isNotEmpty(orgRelBoList)) {
                FamilyMemberRelationshipConverter fConverter = Converters
                        .get(FamilyMemberRelationshipConverter.class);
                for (gov.nih.nci.po.data.bo.OrganizationRelationship orgRelBo : orgRelBoList) {
                    FamilyMemberRelationship fmr = fConverter
                            .convertFromBOToJaxB(orgRelBo);
                    fmrList.add(fmr);
                }
            }
            return fmrList;
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while performing getFamilyMemberRelationshipsByFamilyId() for Id: "
                            + familyId + " .", e);
            throw new ServiceException(
                    "Exception occured while performing getFamilyMemberRelationshipsByFamilyId() for Id: "
                            + familyId + " .", e);
        }

    }

    /**
     * This method is used to search for the Families matching given Family
     * details(like name or orgId etc).
     * 
     * @param familyBo
     *            - family criteria to be searched
     * @return List<Family> matching the criteria
     */
    private List<Family> searchFamilies(gov.nih.nci.po.data.bo.Family familyBo) {

        List<Family> familyList = new ArrayList<Family>(); // list to return
        AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.Family> sc = 
                new AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.Family>(familyBo);
        PageSortParams<gov.nih.nci.po.data.bo.Family> params = new PageSortParams<gov.nih.nci.po.data.bo.Family>(
                Utils.MAX_SEARCH_RESULTS, 0, FamilySortCriterion.FAMILY_ID, false);

        // call the EJB service method to search for the Families
        List<gov.nih.nci.po.data.bo.Family> familyBoList = PoRegistry
                .getFamilyService().search(sc, params);

        // Iterate the result & populate the List
        if (CollectionUtils.isNotEmpty(familyBoList)) {
            FamilyConverter fConverter = Converters.get(FamilyConverter.class);
            for (gov.nih.nci.po.data.bo.Family famBo : familyBoList) {
                Family fam = fConverter.convertFromBOToJaxB(famBo);
                familyList.add(fam);
            }
        }

        return familyList;
    }

}
