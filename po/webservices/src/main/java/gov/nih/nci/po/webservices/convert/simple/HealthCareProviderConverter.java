package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.webservices.types.HealthCareProvider;

/**
 * This is Converter class for HealthCareProvider.
 * 
 * @author Rohit Gupta
 */
public class HealthCareProviderConverter
        extends
        AbstractRoleConverter<gov.nih.nci.po.data.bo.HealthCareProvider, HealthCareProvider> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param hcp
     *            -object to be mapped
     * @return mapped BO HCP
     */
    public gov.nih.nci.po.data.bo.HealthCareProvider convertFromJaxbToBO(
            HealthCareProvider hcp) {

        gov.nih.nci.po.data.bo.HealthCareProvider hcpBo = null;
        if (hcp != null) {
            hcpBo = new gov.nih.nci.po.data.bo.HealthCareProvider();

            // Set the basic attributes
            hcpBo.setId(hcp.getId());
            hcpBo.setCertificateLicenseText(hcp.getLicense());
            hcpBo.setStatus(getBORoleStatus(hcp.getStatus().value()));

            // populate PersonRole specific attributes into BO object
            populatePersonRoleFromJaxbToBO(hcp, hcpBo);
        }

        return hcpBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param hcpBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.HealthCareProvider convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.HealthCareProvider hcpBo) {

        gov.nih.nci.po.webservices.types.HealthCareProvider hcp = null;
        if (hcpBo != null) {
            hcp = new HealthCareProvider();

            // Set the basic attributes
            hcp.setId(hcpBo.getId());
            hcp.setLicense(hcpBo.getCertificateLicenseText());
            hcp.setStatus(getEntityStatus(hcpBo.getStatus().name()));

            // populate PersonRole specific attributes into Jaxb object
            populatePersonRoleBOToJaxB(hcpBo, hcp);
        }

        return hcp;
    }
}
