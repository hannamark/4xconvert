package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.dto.OrganizationDTO;
//import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts2.ServletActionContext;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings("PMD")
public class ISOOrgDisplayConverter {
    private static List<Country> countryList = new ArrayList<Country>();

    /**
     * Converter form PO Org to PA Org.
     * 
     * @param poOrgDto OrganizationDTO
     * @return Organization
     */
    public static OrganizationDTO convertPoOrganizationDTO(gov.nih.nci.services.organization.OrganizationDTO poOrgDto) {
        setUpCountryList();
        OrganizationDTO retOrg = new OrganizationDTO();
        //retOrg.setId(Long.valueOf(poOrgDto.getIdentifier().getExtension().toString()));
        poOrgDto.getName().getPart().get(0).getValue();
        retOrg.setName(poOrgDto.getName().getPart().get(0).getValue());
        //
        int partSize = poOrgDto.getPostalAddress().getPart().size();
        AddressPartType type = null;
        for (int k = 0; k < partSize; k++) {
            type = poOrgDto.getPostalAddress().getPart().get(k).getType();
            if (type.name().equals("CNT")) {
                retOrg.setCountry(getCountryNameUsingCode(poOrgDto.getPostalAddress().getPart().get(k)
                        .getCode()));
            }
            if (type.name().equals("ZIP")) {
                retOrg.setZip(poOrgDto.getPostalAddress().getPart().get(k).getValue());
            }
            if (type.name().equals("CTY")) {
                retOrg.setCity(poOrgDto.getPostalAddress().getPart().get(k).getValue());
            }
//            if (type.name().equals("STA")) {
//                // retOrg.setState(poOrgDtos.get(i).getPostalAddress().getPart().get(k).getValue());
//            }
        }
        return retOrg;
    }

    private static String getCountryNameUsingCode(String code) {
        for (int i = 0; i < countryList.size(); i++) {
            gov.nih.nci.pa.domain.Country country = (gov.nih.nci.pa.domain.Country) countryList.get(i);
            if (country.getAlpha3().toString().equals(code)) {
                return country.getName();
            }
        }
        return null;
    }

    private static void setUpCountryList() {
        try {
            countryList = (List<Country>) ServletActionContext.getRequest().getSession().getAttribute("countrylist");
            if (countryList == null) {
                countryList = PaRegistry.getLookUpTableService().getCountries();
                ServletActionContext.getRequest().getSession().setAttribute("countrylist", countryList);
            }
        } catch (Exception e) {
            countryList = null;
        }
    }
}
