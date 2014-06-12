package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.OrganizationalContact;

/**
 * This is Converter class for OrganizationalContact.
 * 
 * @author Rohit Gupta
 */
public class OrganizationalContactConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.OrganizationalContact, OrganizationalContact> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param orgContact
     *            -object to be mapped
     * @return mapped BO OrganizationalContact
     */
    public gov.nih.nci.po.data.bo.OrganizationalContact convertFromJaxbToBO(
            OrganizationalContact orgContact) {

        gov.nih.nci.po.data.bo.OrganizationalContact orgContactBo = null;
        if (orgContact != null) {
            orgContactBo = new gov.nih.nci.po.data.bo.OrganizationalContact();

            // Set the basic attributes
            orgContactBo.setId(orgContact.getId());
            orgContactBo.setTitle(orgContact.getTitle());
            OrganizationalContactType ocType = PoRegistry
                    .getGenericCodeValueService().getByCode(
                            OrganizationalContactType.class,
                            orgContact.getType().value());
            orgContactBo.setType(ocType);
            orgContactBo.setStatus(getBORoleStatus(orgContact.getStatus()
                    .value()));

            // populate PersonRole specific attributes into BO object
            populatePersonRoleFromJaxbToBO(orgContact, orgContactBo);
        }

        return orgContactBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param orgContactBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.OrganizationalContact convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.OrganizationalContact orgContactBo) {

        gov.nih.nci.po.webservices.types.OrganizationalContact orgContact = null;
        if (orgContactBo != null) {
            orgContact = new OrganizationalContact();

            // Set the basic attributes
            orgContact.setId(orgContactBo.getId());
            orgContact.setTitle(orgContactBo.getTitle());
            orgContact
                    .setType(gov.nih.nci.po.webservices.types.OrganizationalContactType
                            .fromValue(orgContactBo.getType().getCode()));
            orgContact.setStatus(getEntityStatus(orgContactBo.getStatus()
                    .name()));

            // populate PersonRole specific attributes into Jaxb object
            populatePersonRoleBOToJaxB(orgContactBo, orgContact);
        }

        return orgContact;
    }

}
