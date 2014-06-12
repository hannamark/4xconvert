package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Organization;
import org.apache.commons.collections.CollectionUtils;

/**
 * This is Converter class for Organization.
 * 
 * @author Rohit Gupta
 */
public class OrganizationConverter
        extends
        AbstractConverter<gov.nih.nci.po.data.bo.Organization, gov.nih.nci.po.webservices.types.Organization> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param organization
     *            -object to be mapped
     * @return mapped BO Organization
     */
    public gov.nih.nci.po.data.bo.Organization convertFromJaxbToBO(
            Organization organization) {

        gov.nih.nci.po.data.bo.Organization organizationBo = null;
        if (organization != null) {
            organizationBo = new gov.nih.nci.po.data.bo.Organization();
            gov.nih.nci.po.data.bo.Address addressBo = null;

            // Set the basic attributes
            organizationBo.setId(organization.getId());
            organizationBo.setName(organization.getName());
            organizationBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus
                    .valueOf(organization.getStatus().value()));

            // Set the Address
            gov.nih.nci.po.webservices.types.Address address = organization
                    .getAddress();
            if (address != null) {
                AddressConverter addressConverter = new AddressConverter();
                addressBo = addressConverter.convertFromJaxbToBO(address);
                organizationBo.setPostalAddress(addressBo);
            }

            // logic to set CtepId is removed as per PO-7006.

            // Set the Contacts
            if (CollectionUtils.isNotEmpty(organization.getContact())) {
                populateJaxbContactListInBo(organization.getContact(),
                        organizationBo);
            }
        }

        return organizationBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param organizationBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.Organization convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.Organization organizationBo) {

        gov.nih.nci.po.webservices.types.Organization organization = null;
        gov.nih.nci.po.webservices.types.Address address = null;
        if (organizationBo != null) {
            organization = new gov.nih.nci.po.webservices.types.Organization();

            // Set the basic attributes
            organization.setId(organizationBo.getId());
            organization.setName(organizationBo.getName());
            organization.setStatus(EntityStatus.fromValue(organizationBo
                    .getStatusCode().name()));

            // Set the Address
            gov.nih.nci.po.data.bo.Address addressBo = organizationBo
                    .getPostalAddress();
            if (addressBo != null) {
                AddressConverter addressConverter = new AddressConverter();
                address = addressConverter.convertFromBOToJaxB(addressBo);
                organization.setAddress(address);
            }

            // logic to set CtepId is removed as per PO-7006.

            // Set the Contacts
            populateBoContactListInJaxb(organizationBo,
                    organization.getContact());
        }

        return organization;
    }
}
