package gov.nih.nci.po.service.external.stubs;

import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.AddressPartType;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnOn;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.StringConverter;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.external.CtepOrganizationImporterTest;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
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

    public CTEPOrganizationServiceStub buildCreateROWithPlayerStub(Long id, Long roId, Long hcfId) throws URISyntaxException {
        CTEPOrganizationServiceStub common = createGeneric();
        Ii playerId = new Ii();
        playerId.setExtension(id.toString());
        playerId.setReliability(IdentifierReliability.ISS);
        playerId.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        playerId.setRoot(IdConverter.ORG_ROOT);
        common.getRo().setPlayerIdentifier(playerId);
        common.getHcf().setPlayerIdentifier(playerId);

        Ii roIi = new Ii();
        roIi.setExtension(roId.toString());
        roIi.setReliability(IdentifierReliability.ISS);
        roIi.setIdentifierName(IdConverter.RESEARCH_ORG_IDENTIFIER_NAME);
        roIi.setRoot(IdConverter.RESEARCH_ORG_ROOT);

        Ii hcfIi = new Ii();
        hcfIi.setExtension(hcfId.toString());
        hcfIi.setReliability(IdentifierReliability.ISS);
        hcfIi.setIdentifierName(IdConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        hcfIi.setRoot(IdConverter.HEALTH_CARE_FACILITY_ROOT);

        common.getRo().getIdentifier().getItem().add(roIi);

        common.getHcf().getIdentifier().getItem().add(hcfIi);

        return new CTEPOrganizationServiceStub(common.getOrg(), common.getHcf(), common.getRo());
    }

    private CTEPOrganizationServiceStub createGeneric() throws URISyntaxException {
        Ii id = new Ii();
        id.setExtension("AAA");
        id.setIdentifierName("CTEP ID");
        id.setRoot(CtepOrganizationImporterTest.CTEP_ORG_ROOT);

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

    @SuppressWarnings("unchecked")
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

    public CTEPOrganizationServiceStub buildCreateHCFWithBadOrgAddressStub() throws Exception {
        CTEPOrganizationServiceStub common = createGeneric();
        Ad postal = common.getOrg().getPostalAddress();
        for(Iterator<Adxp> itr = postal.getPart().iterator(); itr.hasNext();) {
            Adxp element = itr.next();  
            if (AddressPartType.CTY.equals(element.getType())) {
                itr.remove();  
            }  
        }  
        return new CTEPOrganizationServiceStub(common.getOrg(), common.getHcf(), null);
    }

    public CTEPOrganizationServiceStub buildCreateHCFWithBadRoleAddressStub() throws Exception {
        CTEPOrganizationServiceStub common = createGeneric();
        @SuppressWarnings("unchecked")
        DSet<Ad> dset = common.getHcf().getPostalAddress();
        for (Ad ad : dset.getItem()) {
            for(Iterator<Adxp> itr = ad.getPart().iterator(); itr.hasNext();) {
                Adxp element = itr.next();  
                if (AddressPartType.CTY.equals(element.getType())) {
                    itr.remove();  
                }  
            }  
        }
        return new CTEPOrganizationServiceStub(common.getOrg(), common.getHcf(), null);
    }

    public CTEPOrganizationServiceStub buildCreateROWithBadRoleAddressStub() throws Exception {
        CTEPOrganizationServiceStub common = createGeneric();
        @SuppressWarnings("unchecked")
        DSet<Ad> dset = common.getRo().getPostalAddress();
        for (Ad ad : dset.getItem()) {
            for(Iterator<Adxp> itr = ad.getPart().iterator(); itr.hasNext();) {
                Adxp element = itr.next();  
                if (AddressPartType.CTY.equals(element.getType())) {
                    itr.remove();  
                }  
            }  
        }
        return new CTEPOrganizationServiceStub(common.getOrg(), common.getHcf(), null);
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
