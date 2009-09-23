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
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
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

    public CTEPOrganizationServiceStub buildCreateHCFStub() throws Exception {
        CTEPOrganizationServiceStub common = createGeneric();
        return new CTEPOrganizationServiceStub(common.getOrg(), common.getHcf(), null);
    }

    public CTEPOrganizationServiceStub buildCreateROStub() throws URISyntaxException {
        CTEPOrganizationServiceStub common = createGeneric();
        return new CTEPOrganizationServiceStub(common.getOrg(), null, common.getRo());
    }

    private CTEPOrganizationServiceStub createGeneric() throws URISyntaxException {
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

        ResearchOrganizationDTO ro = new ResearchOrganizationDTO();
        ro.setIdentifier(new DSet<Ii>());
        ro.getIdentifier().setItem(new LinkedHashSet<Ii>());
        ro.getIdentifier().getItem().add(id);
        ro.setStatus(status);
        ro.setName(name);
        ro.setPostalAddress(ads);
        Cd funding = new Cd();
        funding.setCode("B09");
        ro.setFundingMechanism(funding);
        Cd type = new Cd();
        type.setCode("CCR");
        ro.setTypeCode(type);

        return new CTEPOrganizationServiceStub(o, hcf, ro);
    }

    /**
     * 
     * @return the data that
     * @throws Exception
     */
    public CTEPOrganizationServiceStub buildCreateHCFWithAddedAddressStub(Ii hcfId, String street, String city,
            String state, String postalCode, Country country) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHCFStub();
        buildCreateWithAddedAddressStub(stub.getHcf(), hcfId, street, city, state, postalCode, country);
        return stub;
    }

    /**
     * 
     * @return the data that
     * @throws Exception
     */
    public CTEPOrganizationServiceStub buildCreateROWithAddedAddressStub(Ii roId, String street, String city,
            String state, String postalCode, Country country) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateROStub();
        buildCreateWithAddedAddressStub(stub.getRo(), roId, street, city, state, postalCode, country);
        return stub; 
    }

    private void buildCreateWithAddedAddressStub(AbstractEnhancedOrganizationRoleDTO dto, Ii roId, String street,
            String city, String state, String postalCode, Country country) {
        addAdress(dto, street, city, state, postalCode, country);
        addIdentifier(dto, roId);
    }

    private void addIdentifier(AbstractEnhancedOrganizationRoleDTO ro, Ii roId) {
        ro.getIdentifier().getItem().add(roId);
    }

    private void addAdress(AbstractEnhancedOrganizationRoleDTO dto, String street, String city, String state,
            String postalCode, Country country) {
        Address a = new Address(street, city, state, postalCode, country);
        dto.getPostalAddress().getItem().add(AddressConverter.SimpleConverter.convertToAd(a));
    }

    public CTEPOrganizationServiceStub buildCreateHCFWithNoUpdatesStub(Ii hcfId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHCFStub();
        addIdentifier(stub.getHcf(), hcfId);
        return stub;
    }

    public CTEPOrganizationServiceStub buildCreateROWithNoUpdatesStub(Ii roId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateROStub();
        addIdentifier(stub.getRo(), roId);
        return stub;
    }

    public CTEPOrganizationServiceStub buildCreateHCFWithNameUpdateStub(Ii hcfPOId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateHCFStub();
        buildCreateWithNameUpdateStub(hcfPOId, stub.getHcf(), stub.getOrg());
        return stub;
    }

    public CTEPOrganizationServiceStub buildCreateROWithNameUpdateStub(Ii roPOId) throws Exception {
        CTEPOrganizationServiceStub stub = buildCreateROStub();
        buildCreateWithNameUpdateStub(roPOId, stub.getRo(), stub.getOrg());
        return stub;
    }

    private void buildCreateWithNameUpdateStub(Ii roPOId, AbstractEnhancedOrganizationRoleDTO dto,
            OrganizationDTO org) {
        EnOn name = StringConverter.convertToEnOn("NAME2");
        org.setName(name);
        dto.setName(name);
        addIdentifier(dto, roPOId);
    }
}
