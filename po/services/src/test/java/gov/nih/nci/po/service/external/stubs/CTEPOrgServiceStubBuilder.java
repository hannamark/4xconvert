package gov.nih.nci.po.service.external.stubs;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * A builder to simulate the data returned from {@link OrganizationService} as provided by CTEP.
 * 
 * @author smatyas
 * 
 */
public class CTEPOrgServiceStubBuilder {
    private static final String DEFAULT_EMAIL = "default@example.com";

    private CTEPOrgServiceStubBuilder() {
    }

    public static final CTEPOrgServiceStubBuilder INSTANCE = new CTEPOrgServiceStubBuilder();

    /**
     * 
     * @return the data that
     * @throws Exception
     */
    public CTEPOrganizationServiceStub buildCreateHcfStub() throws Exception {
        Ii id = new Ii();
        id.setExtension("AAA");
        id.setIdentifierName("CTEP ID");
        id.setRoot("Cancer Therapy Evaluation Program Organization Identifier");

        Cd status = new Cd();
        status.setCode("active");

        EnOn name = StringConverter.convertToEnOn("NAME");

        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "VA",
                "20110", "USA", "United States");
        DSet<Ad> ads = new DSet<Ad>();
        ads.setItem(new LinkedHashSet<Ad>());
        ads.getItem().add(ad);

        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        tels.getItem().add(email);

        OrganizationDTO o = new OrganizationDTO();
        o.setIdentifier(id);
        o.setStatusCode(status);
        o.setName(name);
        o.setPostalAddress(ad);
        o.setTelecomAddress(tels);

        HealthCareFacilityDTO hcf = new HealthCareFacilityDTO();
        hcf.setIdentifier(new DSet<Ii>());
        hcf.getIdentifier().setItem(new LinkedHashSet<Ii>());
        hcf.getIdentifier().getItem().add(id);
        hcf.setStatus(status);
        hcf.setName(name);
        hcf.setPostalAddress(ads);

        return new CTEPOrganizationServiceStub(o, hcf, null);
    }
    
    /**
     * 
     * @return the data that
     * @throws Exception
     */
    public CTEPOrganizationServiceStub buildCreateHcfWithAddedAddressStub(Ii hcfId, String street, String city, String state,
            String postalCode, Country country) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHcfStub(); 
        Address a = new Address(street, city, state, postalCode, country);
        stub.getHcf().getPostalAddress().getItem()
            .add(AddressConverter.SimpleConverter.convertToAd(a));
        stub.getHcf().getIdentifier().getItem().add(hcfId);
        return stub;
    }
    
    /**
     * 
     * @return the data that
     * @throws Exception
     */
    public CTEPOrganizationServiceStub buildCreateHcfWithNoUpdatesStub(Ii hcfId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHcfStub(); 
        stub.getHcf().getIdentifier().getItem().add(hcfId);
        return stub;
    }
    
    public CTEPOrganizationServiceStub buildCreateHcfWithNameUpdateStub(Ii hcfPOId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHcfStub();
        EnOn name = StringConverter.convertToEnOn("NAME2");
        stub.getOrg().setName(name);
        stub.getHcf().setName(name);
        stub.getHcf().getIdentifier().getItem().add(hcfPOId);
        return stub;
    }

}
