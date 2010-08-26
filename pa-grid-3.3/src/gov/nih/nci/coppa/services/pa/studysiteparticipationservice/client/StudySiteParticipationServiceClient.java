package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.client;

import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.services.pa.StudySite;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.studysiteparticipationservice.common.StudySiteParticipationServiceI;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class StudySiteParticipationServiceClient extends StudySiteParticipationServiceClientBase implements StudySiteParticipationServiceI {	

	public StudySiteParticipationServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudySiteParticipationServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudySiteParticipationServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudySiteParticipationServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudySiteParticipationServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudySiteParticipationServiceClient client = new StudySiteParticipationServiceClient(args[1]);
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
	
	private static void createPropSite(StudySiteParticipationServiceClient client) 
    throws DtoTransformException, PAFault, RemoteException, URISyntaxException {
   
        Id studyProtocolIi = new Id();
        studyProtocolIi.setExtension("NCI-2009-00933");
        studyProtocolIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        
        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("my org"));
        org.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        org.setPostalAddress(
                AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                        "Rockville", "MD", "20855", "USA"));
        
        org.setTelecomAddress(new DSet<Tel>());
        org.getTelecomAddress().setItem(new HashSet<Tel>());
        Tel email = new Tel();
       
        URI uri = new URI(TelEmail.SCHEME_MAILTO + ":aaa@bbb.com");
        email.setValue(uri);
        org.getTelecomAddress().getItem().add(email);
        
        Organization orgXml = OrganizationTransformer.INSTANCE.toXml(org);
        
        PersonDTO person = new PersonDTO();
        person.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person.setName(EnPnConverter.convertToEnPn("first", "middle", "lastName", "prefix", "suffix"));
        person.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                "Rockville", "MD", "20855", "USA"));
        person.setSexCode(CdConverter.convertStringToCd("MALE"));
        person.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person.setTelecomAddress(new DSet<Tel>());
        person.getTelecomAddress().setItem(new HashSet<Tel>());
        person.getTelecomAddress().getItem().add(email);
        Person personXml = PersonTransformer.INSTANCE.toXml(person);
        
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime() + Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LOCAL SP ID"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("PROGRAM CODE"));
        
        StudySite ssXml = StudySiteTransformer.INSTANCE.toXml(studySiteDTO);
        
        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() + Long.valueOf("300000000"))));
    
        StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusTransformer.INSTANCE.toXml(currentStatus);
        
        client.createParticipatingSiteForPropTrial(studyProtocolIi, orgXml, ssXml, ssasXml, personXml);

  }
	
	private static void updatePropSite(StudySiteParticipationServiceClient client) 
	  throws DtoTransformException, PAFault, RemoteException, URISyntaxException {
	      
	      Id studyProtocolIi = new Id();
	      studyProtocolIi.setExtension("NCI-2009-00933");
	      studyProtocolIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
	      
	      
	      Id organizationIi = new Id(); 
	      organizationIi.setExtension("SUPERCOOL");
	      organizationIi.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
	      
	      System.out.println("true = " + client.isParticipatingSite(studyProtocolIi, organizationIi).isValue());
	      
	      PersonDTO person = new PersonDTO();
	      person.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
	      person.setName(EnPnConverter.convertToEnPn("changed", "middle", "second", "prefix", "suffix"));
	      person.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
	              "Rockville", "MD", "20855", "USA"));
	      person.setSexCode(CdConverter.convertStringToCd("MALE"));
	      person.setStatusCode(CdConverter.convertStringToCd("PENDING"));
	      person.setTelecomAddress(new DSet<Tel>());
	      person.getTelecomAddress().setItem(new HashSet<Tel>());
	      Tel email = new Tel();
	      URI uri = new URI(TelEmail.SCHEME_MAILTO + ":aaa@bbb.com");
	      email.setValue(uri);
	      person.getTelecomAddress().getItem().add(email);
	      Person personXml = PersonTransformer.INSTANCE.toXml(person);
	      
	      StudySiteDTO studySiteDTO = new StudySiteDTO();
	      studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime() + Long.valueOf("300000000")), null));
	      studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("CHANGED SP ID"));
	      studySiteDTO.setProgramCodeText(StConverter.convertToSt("PROGRAM CODE"));
	      
	      StudySite ssXml = StudySiteTransformer.INSTANCE.toXml(studySiteDTO);
	      
	      StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
	      currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
	      currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() + Long.valueOf("300000000"))));
	    
	      StudySiteAccrualStatus ssasXml = StudySiteAccrualStatusTransformer.INSTANCE.toXml(currentStatus);
	      
	      client.updateParticipatingSiteForPropTrial(studyProtocolIi, organizationIi, ssXml, ssasXml, personXml);

	}

  public gov.nih.nci.iso21090.extensions.Id createParticipatingSiteForPropTrial(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.coppa.po.Organization organization,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus status,gov.nih.nci.coppa.po.Person investigator) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createParticipatingSiteForPropTrial");
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequest params = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequest();
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStudySite studySiteContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStudySite();
    studySiteContainer.setStudySite(studySite);
    params.setStudySite(studySiteContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStatus statusContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestStatus();
    statusContainer.setStudySiteAccrualStatus(status);
    params.setStatus(statusContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestInvestigator investigatorContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialRequestInvestigator();
    investigatorContainer.setPerson(investigator);
    params.setInvestigator(investigatorContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.CreateParticipatingSiteForPropTrialResponse boxedResult = portType.createParticipatingSiteForPropTrial(params);
    return boxedResult.getId();
    }
  }

  public void updateParticipatingSiteForPropTrial(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.iso21090.extensions.Id organizationId,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus status,gov.nih.nci.coppa.po.Person investigator) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateParticipatingSiteForPropTrial");
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequest params = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequest();
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestOrganizationId organizationIdContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestOrganizationId();
    organizationIdContainer.setId(organizationId);
    params.setOrganizationId(organizationIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStudySite studySiteContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStudySite();
    studySiteContainer.setStudySite(studySite);
    params.setStudySite(studySiteContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStatus statusContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestStatus();
    statusContainer.setStudySiteAccrualStatus(status);
    params.setStatus(statusContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestInvestigator investigatorContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialRequestInvestigator();
    investigatorContainer.setPerson(investigator);
    params.setInvestigator(investigatorContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.UpdateParticipatingSiteForPropTrialResponse boxedResult = portType.updateParticipatingSiteForPropTrial(params);
    }
  }

  public gov.nih.nci.iso21090.extensions.Bl isParticipatingSite(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.iso21090.extensions.Id organizationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"isParticipatingSite");
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequest params = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequest();
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequestOrganizationId organizationIdContainer = new gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteRequestOrganizationId();
    organizationIdContainer.setId(organizationId);
    params.setOrganizationId(organizationIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteparticipationservice.stubs.IsParticipatingSiteResponse boxedResult = portType.isParticipatingSite(params);
    return boxedResult.getBl();
    }
  }

}
