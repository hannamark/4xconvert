package gov.nih.nci.po.webservices.convert.simple;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.webservices.types.Entity;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This is the base class for all type of Role converters in PO.
 * 
 * @author Rohit Gupta
 * 
 * @param <BO>
 *            business object
 * @param <JAXB>
 *            jaxb object
 */
public abstract class AbstractRoleConverter<BO extends PersistentObject, JAXB extends Entity>
        extends AbstractConverter<BO, JAXB> {

    /**
     * This method is used to populate values from JaxB PersonRole to BO
     * PersonRole object.
     * 
     * @param personRole
     *            JaxB PersonRole object to be read e.g. HealthCareProvider,
     *            OrganizationalContact, ClinicalResearchStaff
     * @param personRoleBo
     *            BO PersonRole which has to be populated
     */
    protected void populatePersonRoleFromJaxbToBO(
            gov.nih.nci.po.webservices.types.PersonRole personRole,
            gov.nih.nci.po.data.bo.AbstractPersonRole personRoleBo) {

        // Set player
        gov.nih.nci.po.data.bo.Person person = PoRegistry.getPersonService()
                .getById(personRole.getPersonId());
        personRoleBo.setPlayer(person);

        // Set scoper
        gov.nih.nci.po.data.bo.Organization organization = PoRegistry
                .getOrganizationService().getById(
                        personRole.getOrganizationId());
        personRoleBo.setScoper(organization);

        // Set the Address
        List<gov.nih.nci.po.webservices.types.Address> addressList = personRole
                .getAddress();
        if (CollectionUtils.isNotEmpty(addressList)) {
            for (gov.nih.nci.po.webservices.types.Address address : addressList) {
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.data.bo.Address addressBo = addressConverter
                        .convertFromJaxbToBO(address);
                personRoleBo.getPostalAddresses().add(addressBo);
            }
        }

        // Set the Contacts
        if (CollectionUtils.isNotEmpty(personRole.getContact())) {
            populateJaxbContactListInBo(personRole.getContact(), personRoleBo);
        }
    }

    /**
     * This method is used to populate values from BO PersonRole to JaxB
     * PersonRole object.
     * 
     * @param personRoleBo
     *            BO PersonRole object to be read e.g. HealthCareProvider,
     *            OrganizationalContact, ClinicalResearchStaff
     * @param personRole
     *            Jaxb PersonRole which has to be populated
     */
    protected void populatePersonRoleBOToJaxB(
            gov.nih.nci.po.data.bo.AbstractPersonRole personRoleBo,
            gov.nih.nci.po.webservices.types.PersonRole personRole) {

        // Set player (Person)
        personRole.setPersonId(personRoleBo.getPlayer().getId());

        // Set scoper (Organization)
        personRole.setOrganizationId(personRoleBo.getScoper().getId());

        // Set the Address
        Set<gov.nih.nci.po.data.bo.Address> addressBoSet = personRoleBo
                .getPostalAddresses();
        if (CollectionUtils.isNotEmpty(addressBoSet)) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = addressBoSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address addressBo = iterator.next();
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.webservices.types.Address address = addressConverter
                        .convertFromBOToJaxB(addressBo);
                personRole.getAddress().add(address);
            }
        }

        // Set the Contacts
        populateBoContactListInJaxb(personRoleBo, personRole.getContact());
    }

    /**
     * This method is used to populate values from JaxB OrganizationRole to BO
     * OrganizationRole object.
     * 
     * @param orgRole
     *            JaxB OrganizationRole object to be read - ResearchOrganization
     *            or HealthCareFacility
     * @param enOrgRoleBo
     *            BO OrganizationRole which has to be populated -
     *            ResearchOrganization or HealthCareFacility
     */
    protected void populateOrganizationRoleFromJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole,
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole enOrgRoleBo) {

        // Set Player(Organization)
        gov.nih.nci.po.data.bo.Organization organization = PoRegistry
                .getOrganizationService().getById(orgRole.getOrganizationId());
        enOrgRoleBo.setPlayer(organization);

        // Set the Address
        List<gov.nih.nci.po.webservices.types.Address> addressList = orgRole
                .getAddress();
        if (CollectionUtils.isNotEmpty(addressList)) {
            for (gov.nih.nci.po.webservices.types.Address address : addressList) {
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.data.bo.Address addressBo = addressConverter
                        .convertFromJaxbToBO(address);
                enOrgRoleBo.getPostalAddresses().add(addressBo);
            }
        }

        // Set the Contacts
        if (CollectionUtils.isNotEmpty(orgRole.getContact())) {
            populateJaxbContactListInBo(orgRole.getContact(), enOrgRoleBo);
        }
    }

    /**
     * This method is used to populate values from JaxB OversightCommittee to BO
     * OversightCommittee object.
     * 
     * @param orgRole
     *            JaxB OversightCommittee object to be read
     * @param overCommBo
     *            BO OversightCommittee which has to be populated
     */
    protected void populateOrganizationRoleFromJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole,
            gov.nih.nci.po.data.bo.AbstractOversightCommittee overCommBo) {

        // Set Player(Organization)
        gov.nih.nci.po.data.bo.Organization organization = PoRegistry
                .getOrganizationService().getById(orgRole.getOrganizationId());
        overCommBo.setPlayer(organization);

        // Set the Address
        List<gov.nih.nci.po.webservices.types.Address> addressList = orgRole
                .getAddress();
        if (CollectionUtils.isNotEmpty(addressList)) {
            for (gov.nih.nci.po.webservices.types.Address address : addressList) {
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.data.bo.Address addressBo = addressConverter
                        .convertFromJaxbToBO(address);
                overCommBo.getPostalAddresses().add(addressBo);
            }
        }

        // Set the Contacts
        if (CollectionUtils.isNotEmpty(orgRole.getContact())) {
            populateJaxbContactListInBo(orgRole.getContact(), overCommBo);
        }
    }

    /**
     * This method is used to populate values from BO OrganizationRole to JaxB
     * OrganizationRole object.
     * 
     * @param enOrgRoleBo
     *            BO OrganizationRole object to be read - ResearchOrganization
     *            or HealthCareFacility
     * @param orgRole
     *            JaxB OrganizationRole which has to be populated -
     *            ResearchOrganization or HealthCareFacility
     */
    protected void populateOrganizationRoleFromBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole enOrgRoleBo,
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole) {

        // Set Player (Organization)
        orgRole.setOrganizationId(enOrgRoleBo.getPlayer().getId());

        // Set the Address
        Set<gov.nih.nci.po.data.bo.Address> addressBoSet = enOrgRoleBo
                .getPostalAddresses();
        if (CollectionUtils.isNotEmpty(addressBoSet)) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = addressBoSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address addressBo = iterator.next();
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.webservices.types.Address address = addressConverter
                        .convertFromBOToJaxB(addressBo);
                orgRole.getAddress().add(address);
            }
        }

        // Set the Contacts
        populateBoContactListInJaxb(enOrgRoleBo, orgRole.getContact());
    }

    /**
     * This method is used to populate values from BO OversightCommittee to JaxB
     * OversightCommittee object.
     * 
     * @param overCommBo
     *            BO OversightCommittee object to be read
     * @param orgRole
     *            JaxB OversightCommittee which has to be populated
     */
    protected void populateOrganizationRoleFromBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractOversightCommittee overCommBo,
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole) {

        // Set Player (Organization)
        orgRole.setOrganizationId(overCommBo.getPlayer().getId());

        // Set the Address
        Set<gov.nih.nci.po.data.bo.Address> addressBoSet = overCommBo
                .getPostalAddresses();
        if (CollectionUtils.isNotEmpty(addressBoSet)) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = addressBoSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address addressBo = iterator.next();
                AddressConverter addressConverter = new AddressConverter();
                gov.nih.nci.po.webservices.types.Address address = addressConverter
                        .convertFromBOToJaxB(addressBo);
                orgRole.getAddress().add(address);
            }
        }

        // Set the Contacts
        populateBoContactListInJaxb(overCommBo, orgRole.getContact());
    }

    /**
     * This method is used to get BO RoleStatus for given JaxB status.
     * 
     * @param jaxbStatus
     *            JaxB status
     * @return Corresponding BO Role status
     */
    protected gov.nih.nci.po.data.bo.RoleStatus getBORoleStatus(
            String jaxbStatus) {

        return PoWSUtil.getBORoleStatus(jaxbStatus);
    }

    /**
     * This method is used to get JaxBStatus for given BO status.
     * 
     * @param boStatus
     *            BO role status
     * @return Corresponding JaxB status
     */
    protected gov.nih.nci.po.webservices.types.EntityStatus getEntityStatus(
            String boStatus) {

        return PoWSUtil.getEntityStatus(boStatus);
    }

    /**
     * This method is used to populate Jaxb CtepId into BO (OrgRole object).
     * 
     * @param <T>
     *            - JaxB- ResearchOrganization or HealthCareFacility
     * @param orgRole
     *            - JaxB- ResearchOrganization or HealthCareFacility
     * @param aeOrgBo
     *            - BO Object in which CtepId is to be set -
     *            ResearchOrganization or HealthCareFacility
     */
    protected <T extends OrganizationRole> void populateJaxbCtepIdInBoOrgRole(
            T orgRole,
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole aeOrgBo) {
        String ctepId = null;
        if (orgRole instanceof ResearchOrganization) {
            ResearchOrganization resOrg = (ResearchOrganization) orgRole;
            ctepId = resOrg.getCtepId();
        } else if (orgRole instanceof HealthCareFacility) {
            HealthCareFacility hcf = (HealthCareFacility) orgRole;
            ctepId = hcf.getCtepId();
        }
        if (StringUtils.isNotBlank(ctepId)) {
            gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
            assIden.setRoot(PoConstants.ORG_CTEP_ID_ROOT);
            assIden.setIdentifierName(PoConstants.ORG_CTEP_ID_IDENTIFIER_NAME);
            assIden.setExtension(ctepId);
            aeOrgBo.getOtherIdentifiers().add(assIden);
        }
    }

    /**
     * This method is used to populate BO CtepId into Jaxb (OrgRole object).
     * 
     * @param <T>
     *            - JaxB- ResearchOrganization or HealthCareFacility
     * @param aeOrgRoleBo
     *            - BO Object - ResearchOrganization or HealthCareFacility
     * @param orgRole
     *            - JaxB Object in which CtepId is to be set -
     *            ResearchOrganization or HealthCareFacility
     */

    protected <T extends OrganizationRole> void populateBoCtepIdInJaxbOrgRole(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole aeOrgRoleBo,
            T orgRole) {

        String ctepId = PoServiceUtil.getOrgRoleBoCtepId(aeOrgRoleBo);

        if (orgRole instanceof ResearchOrganization) {
            ResearchOrganization resOrg = (ResearchOrganization) orgRole;
            resOrg.setCtepId(ctepId);
        } else if (orgRole instanceof HealthCareFacility) {
            HealthCareFacility hcf = (HealthCareFacility) orgRole;
            hcf.setCtepId(ctepId);
        }
    }
}
