package gov.nih.nci.po.service.external.stubs;

import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.po.data.convert.util.AddressConverterUtil;
import gov.nih.nci.po.service.external.CtepPersonImporter;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.person.PersonDTO;

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
public class CTEPPerServiceStubBuilder {
    private static final String DEFAULT_EMAIL = "default@example.com";

    private CTEPPerServiceStubBuilder() {
    }

    public static final CTEPPerServiceStubBuilder INSTANCE = new CTEPPerServiceStubBuilder();

    public CTEPPersonServiceStub buildCreateBaseStub() throws URISyntaxException {
        return createGeneric();
    }
   
    public CTEPPersonServiceStub buildCreateHCPStub(Ii scoper) throws URISyntaxException {
        Cd statusPending = new Cd();
        statusPending.setCode("pending");

        CTEPPersonServiceStub common = createGeneric();
        HealthCareProviderDTO hcp = new HealthCareProviderDTO();
        hcp.setIdentifier(new DSet<Ii>());
        hcp.getIdentifier().setItem(new LinkedHashSet<Ii>());
        hcp.getIdentifier().getItem().add(getId1());
        hcp.setStatus(statusPending);
        hcp.setPostalAddress(getAds());
        hcp.setPlayerIdentifier(getId1());
        hcp.setScoperIdentifier(scoper);
        hcp.setTelecomAddress(new DSet<Tel>());
        hcp.getTelecomAddress().setItem(new LinkedHashSet<Tel>());
        Tel tel = new Tel();
        tel.setValue(new URI("tel:111-111-1111"));
        hcp.getTelecomAddress().getItem().add(tel);
        
        return new CTEPPersonServiceStub(common.getPer(), hcp, common.getCrs(), common.getIp());
    }
    
    private Ii getId1() {
        Ii id = new Ii();
        id.setExtension("AAA");
        id.setIdentifierName("CTEP ID");
        id.setRoot(CtepPersonImporter.CTEP_PERSON_ROOT);
        
        return id;
    }

    private DSet<Ad> getAds() {
        DSet<Ad> ads = new DSet<Ad>();
        ads.setItem(new LinkedHashSet<Ad>());
        ads.getItem().add(getAd());
        return ads;
    }
    
    private Ad getAd() {
        Ad ad = AddressConverterUtil.create("streetAddressLine", "deliveryAddressLine", "cityOrMunicipality", "VA",
                "20110", "USA", "United States");
        return ad;
    }
    
    private CTEPPersonServiceStub createGeneric() throws URISyntaxException {
        Cd status = new Cd();
        status.setCode("active");
       
        DSet<Tel> tels = new DSet<Tel>();
        tels.setItem(new HashSet<Tel>());

        TelEmail email = new TelEmail();
        email.setValue(new URI("mailto:" + DEFAULT_EMAIL));
        tels.getItem().add(email);

        PersonDTO p = new PersonDTO();
        p.setIdentifier(getId1());
        p.setStatusCode(status);
      
        EnPn name = new EnPn();
        Enxp oF = new Enxp(EntityNamePartType.GIV);
        oF.setValue("Jont");
        name.getPart().add(oF);
        Enxp oL = new Enxp(EntityNamePartType.FAM);
        oL.setValue("Dont");
        name.getPart().add(oL);
        
        p.setName(name);
        p.setPostalAddress(getAd());
        p.setTelecomAddress(tels);

        HealthCareProviderDTO hcp = null;
        ClinicalResearchStaffDTO crs = null;
        IdentifiedPersonDTO ip = null;
        
        return new CTEPPersonServiceStub(p, hcp, crs, ip);
    }

   
}
