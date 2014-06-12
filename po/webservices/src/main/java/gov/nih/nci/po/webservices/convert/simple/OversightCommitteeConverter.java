package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.webservices.types.OversightCommittee;

/**
 * This is Converter class for OversightCommittee.
 * 
 * @author Rohit Gupta
 */
public class OversightCommitteeConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.OversightCommittee, OversightCommittee> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param overComm
     *            -object to be mapped
     * @return mapped BO OversightCommittee
     */
    public gov.nih.nci.po.data.bo.OversightCommittee convertFromJaxbToBO(
            OversightCommittee overComm) {

        gov.nih.nci.po.data.bo.OversightCommittee overCommBo = null;
        if (overComm != null) {
            overCommBo = new gov.nih.nci.po.data.bo.OversightCommittee();

            // Set the basic attributes
            overCommBo.setId(overComm.getId());
            gov.nih.nci.po.data.bo.OversightCommitteeType ocType = PoRegistry
                    .getGenericCodeValueService().getByCode(
                            OversightCommitteeType.class,
                            overComm.getType().value());
            overCommBo.setTypeCode(ocType);
            overCommBo.setStatus(getBORoleStatus(overComm.getStatus().value()));

            // populate OversightCommittee specific attributes into BO
            // object(Overloaded method)
            populateOrganizationRoleFromJaxbToBO(overComm, overCommBo);
        }

        return overCommBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param overCommBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.OversightCommittee convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.OversightCommittee overCommBo) {

        gov.nih.nci.po.webservices.types.OversightCommittee overComm = null;
        if (overCommBo != null) {
            overComm = new OversightCommittee();

            // Set the basic attributes
            overComm.setId(overCommBo.getId());
            overComm.setType(gov.nih.nci.po.webservices.types.OversightCommitteeType
                    .fromValue(overCommBo.getTypeCode().getCode()));

            overComm.setStatus(getEntityStatus(overCommBo.getStatus().name()));

            // populate OversightCommittee specific attributes into Jaxb object
            // (Overloaded method)
            populateOrganizationRoleFromBOToJaxb(overCommBo, overComm);
        }

        return overComm;
    }

}
