package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.client;

import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.HealthCareFacilityManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.OrganizationManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.PersonManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteAccrualStatusManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.transformers.management.StudySiteManagementTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.ClinicalResearchStaff;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.PersonType;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudyProtocol;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySiteContact;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import org.iso._21090.II;

/**
 * Simple client to call the StudySiteParticipationService.
 * @author moweis
 *
 */
public class StudySiteParticipationServiceSimpleClient{

    public static void usage() {
        System.out.println(StudySiteParticipationServiceSimpleClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    StudySiteParticipationServiceClient client = new StudySiteParticipationServiceClient(
                            args[1]);
                    createPropSite(client);
                    updatePropSite(client);
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

    private static void createPropSite(StudySiteParticipationServiceClient client) throws DtoTransformException,
            PAFault, RemoteException, URISyntaxException {

        II studyProtocolIi = new II();
        studyProtocolIi.setExtension("NCI-2010-00003");
        studyProtocolIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setIdentifier(studyProtocolIi);

        Ii personIi = new Ii();
        personIi.setExtension("597");
        personIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        PersonDTO person = new PersonDTO();
        person.setIdentifier(personIi);

        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(
                new Timestamp(new Date().getTime() + Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("ANY TEXT"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("ANY TEXT"));

        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime()
                - Long.valueOf("300000000"))));
        StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusManagementTransformer.INSTANCE
                .toXml(currentStatus);
        
        StudySite ssXml = StudySiteManagementTransformer.INSTANCE.toXml(studySiteDTO);
        ssXml.setAccrualStatus(ssasXml);

        Ii hcfIi = new Ii();
        hcfIi.setExtension("771");
        hcfIi.setRoot(IiConverter.HEALTH_CARE_FACILITY_ROOT);
        hcfIi.setIdentifierName(IiConverter.HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        DSet<Ii> hcfDSetIi = new DSet<Ii>();
        hcfDSetIi.setItem(new HashSet<Ii>());
        hcfDSetIi.getItem().add(hcfIi);
        HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
        hcfDTO.setIdentifier(hcfDSetIi);

        Ii orgIi = new Ii();
        orgIi.setExtension("753");
        orgIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setIdentifier(orgIi);

        hcfDTO.setPlayerIdentifier(orgIi);
        ssXml.setOrganizationRole(HealthCareFacilityManagementTransformer.INSTANCE.toXml(hcfDTO));
        ssXml.getOrganizationRole().setPlayer(
                OrganizationManagementTransformer.INSTANCE.toXml(organizationDTO));
        
        ssXml.setStudyProtocol(studyProtocol);

        client.createParticipatingSite(ssXml);

    }

    private static void updatePropSite(StudySiteParticipationServiceClient client) throws DtoTransformException,
            PAFault, RemoteException, URISyntaxException {

        Ii personIi = new Ii();
        personIi.setExtension("597");
        personIi.setRoot(IiConverter.CTEP_PERSON_IDENTIFIER_ROOT);
        PersonDTO person = new PersonDTO();
        person.setIdentifier(personIi);

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

        StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusManagementTransformer.INSTANCE
                .toXml(currentStatus);
        ssXml.setAccrualStatus(ssasXml);
        
        StudySiteContact ssContact = new StudySiteContact();
        PersonType personType = new PersonType();
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setPlayer(PersonManagementTransformer.INSTANCE.toXml(person));
        personType.getContent().add(crs);
        ssContact.setPersonRole(personType);
        ssContact.setPostalAddress(ADTransformer.INSTANCE.toXml(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                "Rockville", "MD", "20855", "USA")));
        ssContact.setPrimaryIndicator(BLTransformer.INSTANCE.toXml(BlConverter.convertToBl(true)));
        ssContact.setRoleCode(CDTransformer.INSTANCE.toXml(CdConverter.convertToCd(StudySiteContactRoleCode.PRIMARY_CONTACT)));
        ssXml.getStudySiteContacts().add(ssContact);

        Id studySiteIi = new Id();
        studySiteIi.setExtension("27454");
        studySiteIi.setRoot(IiConverter.STUDY_SITE_ROOT);
        studySiteIi.setIdentifierName(IiConverter.STUDY_SITE_IDENTIFIER_NAME);
        client.updateParticipatingSite(studySiteIi, ssXml);

    }

}
