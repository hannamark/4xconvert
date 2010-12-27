package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.client;

import gov.nih.nci.coppa.services.grid.util.XMLUnmarshaller;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.HealthCareFacilityManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.PersonManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteAccrualStatusManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteContactManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.OrganizationalContact;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.PersonRole;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.PersonType;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudyProtocol;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.iso._21090.DSETII;
import org.iso._21090.DSETTEL;
import org.iso._21090.II;
import org.iso._21090.IVLTS;
import org.iso._21090.ST;
import org.iso._21090.TEL;

/**
 * Simple client to call the StudySiteParticipationService.
 * 
 * @author moweis
 * 
 */
public class StudySiteParticipationServiceSimpleClient {

    public static void usage() {
        System.out.println(StudySiteParticipationServiceSimpleClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    StudySiteParticipationServiceClient client = new StudySiteParticipationServiceClient(args[1]);
                    //createSiteWithPiAndPrimCont(client);
                    createSiteWithPiAndGenCont(client);
                    //updateSiteWithXml(client);
                    //getByStudyProtocol(client);
                    //changePITest(client);
                } else {
                    usage();
                    System.exit(1);
                }
            } else {
                usage();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void createTestSiteWithXml(StudySiteParticipationServiceClient client) throws DtoTransformException,
            PAFault, RemoteException, URISyntaxException, JAXBException {

        StudySite ssXml = XMLUnmarshaller.unmarshal(StudySite.class, "foo.xml");
        
        System.out.println("****:" + ssXml.getOrganizationRole().getIdentifier().getItem().get(0).getExtension());
        gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite result = client
            .createParticipatingSite(ssXml);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));   
    }
    
    private static StudySite getSiteHelper() throws DtoTransformException,
        PAFault, RemoteException, URISyntaxException, UnsupportedEncodingException {
     // create study protocol
        II studyProtocolIi = new II();
        studyProtocolIi.setExtension("CTEP-1234");
        studyProtocolIi.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setIdentifier(studyProtocolIi);
        
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime() + Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("ANY TEXT"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("ANY TEXT"));

        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime()
                - Long.valueOf("300000000"))));
        StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusManagementTransformer.INSTANCE.toXml(currentStatus);

        StudySite ssXml = StudySiteManagementTransformer.INSTANCE.toXml(studySiteDTO);
        ssXml.setAccrualStatus(ssasXml);

        Ii hcfIi = new Ii();
        hcfIi.setExtension("SUPERCOOL2");
        hcfIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        DSet<Ii> hcfDSetIi = new DSet<Ii>();
        hcfDSetIi.setItem(new HashSet<Ii>());
        hcfDSetIi.getItem().add(hcfIi);
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        hcfDTO.setIdentifier(hcfDSetIi);

        ssXml.setOrganizationRole(HealthCareFacilityManagementTransformer.INSTANCE.toXml(hcfDTO));
        
        ssXml.setStudyProtocol(studyProtocol);
        return ssXml;
    }

    private static void createSiteWithPiAndGenCont(StudySiteParticipationServiceClient client) throws DtoTransformException,
            PAFault, RemoteException, URISyntaxException, UnsupportedEncodingException {
        
        StudySite ssXml = getSiteHelper();
        ssXml.getStudySiteContacts().add(createPrinInvStudySiteContact());
        ssXml.getStudySiteContacts().add(createGenericContStudySiteContact()); 
        client.createParticipatingSite(ssXml);
    }
    
    private static void createSiteWithPiAndPrimCont(StudySiteParticipationServiceClient client) throws DtoTransformException,
    PAFault, RemoteException, URISyntaxException, UnsupportedEncodingException {

        StudySite ssXml = getSiteHelper();
        ssXml.getStudySiteContacts().add(createPrinInvStudySiteContact());
        ssXml.getStudySiteContacts().add(createPrimContStudySiteContact());
        client.createParticipatingSite(ssXml);
    }
     
    private static StudySiteContact createPrinInvStudySiteContact() throws DtoTransformException, UnsupportedEncodingException {
        // principal investigator
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        studySiteContactDTO.setRoleCode(CdConverter.convertStringToCd("Principal Investigator"));
        studySiteContactDTO.setPrimaryIndicator(BlConverter.convertToBl(false));
        
        studySiteContactDTO.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville",
                "MD", "20855", "USA"));
           
        StudySiteContact ssCont = StudySiteContactManagementTransformer.INSTANCE.toXml(studySiteContactDTO);
        DSETTEL dsetTel = new DSETTEL();
        TEL phone1 = new TEL();
        phone1.setValue("tel:" + URLEncoder.encode("111-111-1111", "UTF-8"));
        dsetTel.getItem().add(phone1);
        TEL mail1 = new TEL();
        mail1.setValue("mailto:" + URLEncoder.encode("aaa@example.com", "UTF-8"));
        dsetTel.getItem().add(mail1);
        ssCont.setTelecomAddresses(dsetTel);
        IVLTS ivlts = new IVLTS();
        ivlts.setLow(TSTransformer.INSTANCE.toXml(
                TsConverter.convertToTs(new Timestamp((new Date()).getTime()))));
        ssCont.setStatusDateRange(ivlts);
         
        PersonType pt = new PersonType();
        PersonRole pr = new ClinicalResearchStaff();
        DSet<Ii> prnDSetIi = new DSet<Ii>();
        prnDSetIi.setItem(new HashSet<Ii>());
        Ii personIi = new Ii();
        personIi.setExtension("MyCtepId1");
        personIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        prnDSetIi.getItem().add(personIi);
        pr.setIdentifier(DSETIITransformer.INSTANCE.toXml(prnDSetIi));
        pt.getContent().add(pr);
        ssCont.setPersonRole(pt);
        
        return ssCont;
    }
    
    private static StudySiteContact createPrimContStudySiteContact() throws DtoTransformException, UnsupportedEncodingException {
        // principal investigator
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        studySiteContactDTO.setRoleCode(CdConverter.convertStringToCd("Primary Contact"));
        studySiteContactDTO.setPrimaryIndicator(BlConverter.convertToBl(true));
        studySiteContactDTO.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville",
                "MD", "20855", "USA"));
        
        StudySiteContact ssCont = StudySiteContactManagementTransformer.INSTANCE.toXml(studySiteContactDTO);
        DSETTEL dsetTel = new DSETTEL();
        TEL phone1 = new TEL();
        phone1.setValue("tel:" + URLEncoder.encode("111-111-1111", "UTF-8"));
        dsetTel.getItem().add(phone1);
        TEL mail1 = new TEL();
        mail1.setValue("mailto:" + URLEncoder.encode("aaa@example.com", "UTF-8"));
        dsetTel.getItem().add(mail1);
        ssCont.setTelecomAddresses(dsetTel);
        IVLTS ivlts = new IVLTS();
        ivlts.setLow(TSTransformer.INSTANCE.toXml(
                TsConverter.convertToTs(new Timestamp((new Date()).getTime()))));
        ssCont.setStatusDateRange(ivlts);
        PersonType pt = new PersonType();
        PersonRole pr = new ClinicalResearchStaff();
        DSet<Ii> prnDSetIi = new DSet<Ii>();
        prnDSetIi.setItem(new HashSet<Ii>());
        Ii personIi = new Ii();
        personIi.setExtension("MyCtepId2");
        personIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        prnDSetIi.getItem().add(personIi);
        pr.setIdentifier(DSETIITransformer.INSTANCE.toXml(prnDSetIi));
        pt.getContent().add(pr);
        ssCont.setPersonRole(pt);
        
        return ssCont;
    }
    
    private static StudySiteContact createGenericContStudySiteContact() throws DtoTransformException, UnsupportedEncodingException {
        // principal investigator
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        studySiteContactDTO.setRoleCode(CdConverter.convertStringToCd("Primary Contact"));
        studySiteContactDTO.setPrimaryIndicator(BlConverter.convertToBl(true));
        studySiteContactDTO.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", "Rockville",
                "MD", "20855", "USA"));
        
        StudySiteContact ssCont = StudySiteContactManagementTransformer.INSTANCE.toXml(studySiteContactDTO);
        DSETTEL dsetTel = new DSETTEL();
        TEL phone1 = new TEL();
        phone1.setValue("tel:" + URLEncoder.encode("111-111-1111", "UTF-8"));
        dsetTel.getItem().add(phone1);
        TEL mail1 = new TEL();
        mail1.setValue("mailto:" + URLEncoder.encode("aaa@example.com", "UTF-8"));
        dsetTel.getItem().add(mail1);
        ssCont.setTelecomAddresses(dsetTel);
        IVLTS ivlts = new IVLTS();
        ivlts.setLow(TSTransformer.INSTANCE.toXml(
                TsConverter.convertToTs(new Timestamp((new Date()).getTime()))));
        ssCont.setStatusDateRange(ivlts);
        PersonType pt = new PersonType();
        // make sure that OC you are using has a type code Site.
        OrganizationalContact pr = new OrganizationalContact();
        pr.setTitle(STTransformer.INSTANCE.toXml(StConverter.convertToSt("newtesttitle2")));
        pr.setTypeCode(CDTransformer.INSTANCE.toXml(CdConverter.convertStringToCd("Site")));
        pr.setTelecomAddress(dsetTel);
        pt.getContent().add(pr);
        ssCont.setPersonRole(pt);
        
        return ssCont;
    }
    
    private static void getByStudyProtocol(StudySiteParticipationServiceClient client) throws DtoTransformException,
    PAFault, RemoteException, URISyntaxException {
        
        Id studyProtocolId = new Id();
        studyProtocolId.setExtension("NCI-2009-01504");
        studyProtocolId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite[] ssXmls 
            = client.getParticipatingSitesByStudyProtocol(studyProtocolId);
        for (gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite ssXml : ssXmls) {
            System.out.println(ToStringBuilder.reflectionToString(ssXml.getStudyProtocol().getIdentifier(), ToStringStyle.MULTI_LINE_STYLE)); 
        }
}

    private static void updateSiteWithXml(StudySiteParticipationServiceClient client) throws DtoTransformException,
            PAFault, RemoteException, URISyntaxException, JAXBException {

        StudySite ssXml = XMLUnmarshaller.unmarshal(StudySite.class, "foo.xml");
        Id ssId = 
            XMLUnmarshaller.unmarshal(Id.class, "bar.xml");
        System.out.println("****:bar:" + ssId.getExtension());
        System.out.println("****:foo:" + ssXml.getOrganizationRole().getIdentifier().getItem().get(0).getExtension());
        gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite ss 
        = client.updateParticipatingSite(ssId, ssXml);
        System.out.println("Update Result: " + ss.getIdentifier());

    }
    
    private static void updatePropSiteWithGenericContactAsPrimary(StudySiteParticipationServiceClient client) throws DtoTransformException,
    PAFault, RemoteException, URISyntaxException {

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime() - Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("CHANGED SP ID"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("PROGRAM CODE"));
        
        StudySite ssXml = StudySiteManagementTransformer.INSTANCE.toXml(studySiteDTO);
        
        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime()
                - Long.valueOf("300000000"))));
        
        StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusManagementTransformer.INSTANCE.toXml(currentStatus);
        ssXml.setAccrualStatus(ssasXml);
        
        StudySiteContact ssContact = new StudySiteContact();
        PersonType personType = new PersonType();
        gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.OrganizationalContact 
            orgC = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.OrganizationalContact();
        ST title = new ST();
        title.setValue("Doctors Assistant 4");
        orgC.setTitle(title);
        orgC.setTypeCode(CDTransformer.INSTANCE.toXml(CdConverter.convertStringToCd("Responsible Party")));
        
        personType.getContent().add(orgC);
        ssContact.setPersonRole(personType);
        ssContact.setPostalAddress(ADTransformer.INSTANCE.toXml(AddressConverterUtil.create("1000 Some St.",
                "1000 Some St.", "Rockville", "MD", "20855", "USA")));
        ssContact.setPrimaryIndicator(BLTransformer.INSTANCE.toXml(BlConverter.convertToBl(true)));
        
        TEL tel = new TEL();
        tel.setValue("tel:111-111-2222");
        TEL email = new TEL();
        email.setValue("mailto:test@example.com");
        
        DSETTEL dsetTel = new DSETTEL();
        dsetTel.getItem().add(tel);
        dsetTel.getItem().add(email);
        
        ssContact.setTelecomAddresses(dsetTel);
        ssXml.getStudySiteContacts().add(ssContact);
        
        Id studySiteIi = new Id();
        studySiteIi.setExtension("69525625");
        studySiteIi.setRoot(IiConverter.STUDY_SITE_ROOT);
        studySiteIi.setIdentifierName(IiConverter.STUDY_SITE_IDENTIFIER_NAME);
        client.updateParticipatingSite(studySiteIi, ssXml);
}
    
private static void changePITest(StudySiteParticipationServiceClient client) throws DtoTransformException,
    PAFault, RemoteException, URISyntaxException {
    System.out.println("Change PI Test");
    Id studyProtocolId = new Id();
    studyProtocolId.setExtension("VRUSH-001");
    studyProtocolId.setRoot(IiConverter.CTEP_STUDY_PROTOCOL_ROOT);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite[] results =
        client.getParticipatingSitesByStudyProtocol(studyProtocolId);
    for (gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite ss : results) {
        System.out.println(ss.getIdentifier().getExtension());
//        if ("444453".equals(ss.getIdentifier().getExtension()))
//        {
//            for (gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySiteContact ssc : ss.getStudySiteContacts()) {
//                if ("Principal Investigator".equals(ssc.getRoleCode().getCode())) {
//
//                }
//            }
            StudySiteDTO studySiteDTO = new StudySiteDTO();
            studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                                             .convertToIvl(new Timestamp(new Date().getTime() +
                                                                         Long.valueOf("300000000")), null));
            studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("CHANGED SP ID-c1"));
            studySiteDTO.setProgramCodeText(StConverter.convertToSt("UPD CODE"));
            StudySite ssXml = StudySiteManagementTransformer.INSTANCE.toXml(studySiteDTO);


            StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
            currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.SUSPENDED_RECRUITING.getCode()));
            currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() -
                                                                              Long.valueOf("300000000"))));
            StudySiteAccrualStatus ssasXml =
              StudySiteAccrualStatusManagementTransformer.INSTANCE.toXml(currentStatus);
            ssXml.setAccrualStatus(ssasXml);
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.HealthCareFacility
                hcfXml = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.HealthCareFacility();
            // following will reuse existing
            //                hcfXml.setIdentifier(ss.getOrganizationRole().getIdentifier());
            // Following will replace current ORG with "American College of Surgeons Oncology Trials Group"
            DSETII hcfDsetId = new DSETII();
            II hcfId = new II();
            hcfId.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
            hcfId.setExtension("ACOSOG");
            hcfDsetId.getItem().add(hcfId);
            hcfXml.setIdentifier(hcfDsetId);
            ssXml.setOrganizationRole(hcfXml);
            // Replace PIs with this one:
            StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
            studySiteContactDTO.setPrimaryIndicator(BlConverter.convertToBl(false));
            studySiteContactDTO.setPostalAddress(AddressConverterUtil.create("111", " Rockville Pike",
                                                                             "Rockville", "MD", "20850",
                                                                             "USA"));
            // set the person as the PI
            studySiteContactDTO.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));
            Tel email = new Tel();
            URI uri = new URI(TelEmail.SCHEME_MAILTO + ":howard@orion.com");
            email.setValue(uri);
            studySiteContactDTO.setTelecomAddresses(new DSet<Tel>());
            studySiteContactDTO.getTelecomAddresses().setItem(new HashSet<Tel>());
            studySiteContactDTO.getTelecomAddresses().getItem().add(email);
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact studySiteContact
                = StudySiteContactManagementTransformer.INSTANCE.toXml(studySiteContactDTO);


            // construct the person to link with that study site
            Ii personIi = new Ii();
//DEV            personIi.setExtension("1407731");
            personIi.setExtension("1479");
            personIi.setRoot(IiConverter.PERSON_ROOT);
            //personIi.setExtension("11630");
            //personIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
            // contruct the person DTO
            PersonDTO personDto = new PersonDTO();
            personDto.setIdentifier(personIi);
            // set the person as the CRS player
            ClinicalResearchStaff crs = new ClinicalResearchStaff();
            crs.setPlayer(PersonManagementTransformer.INSTANCE.toXml(personDto));
            // add person type to the study site
            PersonType personType = new PersonType();
            personType.getContent().add(crs);
            studySiteContact.setPersonRole(personType);
            ssXml.getStudySiteContacts().add(studySiteContact);
//            for (gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySiteContact ssc : ss.getStudySiteContacts()) {
//                if (!"Principal Investigator".equals(ssc.getRoleCode().getCode())) {
//                    StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
//                    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact studySiteContact =
//                        StudySiteContactManagementTransformer.INSTANCE.toXml(studySiteContactDTO);
//
//                    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff crs =
//                        new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff();
//
//
//                    studySiteContact.setPersonRole(crs);
//                }
//
//
//
//            }
            Id studySiteIi = new Id();
            studySiteIi.setExtension(ss.getIdentifier().getExtension());
            studySiteIi.setRoot(IiConverter.STUDY_SITE_ROOT);
            gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite ssResult =
                client.updateParticipatingSite(studySiteIi, ssXml);
            System.out.println("Update Result: " + ssResult.getIdentifier().getExtension());
        }


}

}
