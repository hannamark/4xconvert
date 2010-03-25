package gov.nih.nci.coppa.services.outcomes.business.client;

import gov.nih.nci.coppa.services.outcomes.business.Cycle;
import gov.nih.nci.coppa.services.outcomes.business.DeathInformation;
import gov.nih.nci.coppa.services.outcomes.business.LesionAssessment;
import gov.nih.nci.coppa.services.outcomes.business.Pathology;
import gov.nih.nci.coppa.services.outcomes.business.Patient;
import gov.nih.nci.coppa.services.outcomes.business.PerformanceStatus;
import gov.nih.nci.coppa.services.outcomes.business.PriorTherapiesItem;
import gov.nih.nci.coppa.services.outcomes.business.PriorTherapy;
import gov.nih.nci.coppa.services.outcomes.business.TreatmentRegimen;
import gov.nih.nci.coppa.services.outcomes.business.common.BusinessI;
import gov.nih.nci.iso21090.extensions.Id;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.DSETCD;
import org.iso._21090.II;
import org.iso._21090.PQ;
import org.iso._21090.ST;
import org.iso._21090.TS;

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
public class BusinessClient extends BusinessClientBase implements BusinessI {	

	public BusinessClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public BusinessClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public BusinessClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public BusinessClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(BusinessClient.class.getName() + " -url <service url>");
	}
	
	private static String patientId = null;
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  BusinessClient client = new BusinessClient(args[1]);
			  
			  patientId = "5735733";
			  
			  testGetAllPatient(client);
			  testGetPatientById(client);
			  testGetPatient(client);
//			  testAddCauseOfDeath(client);
//			  testDeleteDeathInfo(client);
//			  testAddTreatmentRegimentToPatient(client);
//			  testAddCycle(client);
//			  testAddPriorTherapies(client);
//			  testDeleteTreatmentRegimen(client);
//			  testLesionAssessment(client);
//			  testUpdateLesionAssessment(client);	
			  testCreatePatient(client);
			  
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
	
    private static void printResults(Patient... patient) {
        if (patient == null || (patient.length == 1 && patient[0] == null)) {
            System.out.println("search found no results");
        } else {
            System.out.println("search found " + patient.length + " results");
            for (int i = 0; i < patient.length; i++) {
                System.out.println(ToStringBuilder.reflectionToString(patient[i], ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }
        
    private static void testAddPriorTherapies(BusinessClient client) throws RemoteException {
        System.out.println("test add prior therapy");
        
        Patient patient = getCreatedPatient(client);

        PriorTherapy pt = new PriorTherapy();
        PriorTherapiesItem pti = new PriorTherapiesItem();
                
        PQ pq = new PQ();
        pq.setValue(1d);
        pt.setTotalRegimenNum(pq);
        
        PQ pq2 = new PQ();
        pq2.setValue(0d);
        pt.setChemoRegimenNum(pq2);
        
        
        pt.setHasPrior(true);
        
        CD type = new CD();
        type.setCode("Antisense");
        pti.setType(type);
        
        ST desc = new ST();
        desc.setValue("my pti desc");
        pti.setDescription(desc);
        
 //       pti.setAction(createCD());
        
        pt.getItemList().add(pti);
        
        pt.setAction(createCD());
               
        patient.setPriorTherapy(pt);
        client.write(patient);
        
    }
    
    private static void testAddCycle(BusinessClient client) throws RemoteException {
        System.out.println("testAddCycle");

        Cycle c = new Cycle();

        ST name = new ST();
        name.setValue("cycle name");
        c.setName(name);

        TS ts = new TS();
        ts.setValue("20090922090000.0000-0500");
        c.setCreateDate(ts);
        
        c.setAction(createCD());
        
        Patient patient = getCreatedPatient(client);
        TreatmentRegimen tr = patient.getTreatmentRegimens().get(0);        
        tr.getCycles().add(c);
        
        tr.setAction(updateCD());
        
        client.write(patient);
        
    }
    
	private static void testDeleteDeathInfo(BusinessClient client) throws RemoteException {
	    System.out.println("testDeleteDeathInfo");

        Patient patient = getCreatedPatient(client);
        DeathInformation deathInfo = patient.getDeathInformation();
        deathInfo.setAction(deleteCD());
        
        client.write(patient);

	}

	private static void testDeleteTreatmentRegimen(BusinessClient client) throws RemoteException {
	    System.out.println("testDeleteTreatmentRegimen");
	    
	    Patient patient = getCreatedPatient(client);
	    TreatmentRegimen tr = patient.getTreatmentRegimens().get(0);
	    tr.setAction(deleteCD());
	    
	    client.write(patient);
	    
	}
	
	private static void testDeletePatient(BusinessClient client) throws RemoteException {
        System.out.println("testDelete");

        Patient patient = getCreatedPatient(client);
	    patient.setAction(deleteCD());
	    
	    client.write(patient);
	}
	
	private static void testNoAction(BusinessClient client) throws RemoteException {
	    System.out.println("tesNoAction");
	    
	    Patient patient = new Patient();         
        
	    patient = client.write(patient);
        
	}
	
	private static void testCreatePatient(BusinessClient client) throws RemoteException {
	    System.out.println("testCreatePatient");
	    
	    Patient patient = new Patient(); 
	    
	    II id = new II();
	    id.setExtension("14");
	    ST aId = new ST();
	    aId.setValue("14");
	    patient.setAssignedIdentifier(aId);
	    
	    TS dob = new TS();
        dob.setValue("20090922090000.0000-0500");
	    patient.setBirthDate(dob);
	    
	    CD gender = new CD();
	    gender.setCode("Male");
	    patient.setGenderCode(gender);
	    
	    CD race = new CD();
	    race.setCode("Asian");
	    DSETCD raceset = new DSETCD();
	    raceset.getItem().add(race);
	    patient.setRaceCode(raceset);
	    
	    CD ethn = new CD();
	    ethn.setCode("Not_Hispanic_or_Latino");
	    patient.setEthnicCode(ethn);
	    
	    ST country = new ST();
	    country.setValue("USA");
	    patient.setCountryAlpha3(country);
	    
	    patient.setAction(createCD());
	    
	    Patient written = client.write(patient);
	    printResults(written);
	    
	}
	
	private static void testUpdateLesionAssessment(BusinessClient client) throws RemoteException {
	   System.out.println("testUpdateLesionAssessment");

       Patient patient = getCreatedPatient(client);
       LesionAssessment la = patient.getTreatmentRegimens().get(0).getLesionAssessments().get(0);

       CD type = new CD();
       type.setCode("Evaluable");
       la.setMeasurableEvaluableDiseaseType(type);
	   la.setAction(updateCD());
	   
	   client.write(patient);
       
	}
	
	private static void testLesionAssessment(BusinessClient client) throws RemoteException {
	    System.out.println("testLesionAssessment");
	    
	    
	    LesionAssessment la = new LesionAssessment();
	    II num = new II();
	    num.setExtension("1");
	    la.setLesionNum(num);
	    
	    CD site = new CD();
	    site.setCode("Cecum");
	    la.setLesionSite(site);
	    
	    CD meth = new CD();
	    meth.setCode("Biopsy");
	    la.setLesionMeasurementMethod(meth);
	    
	    CD type = new CD();
	    type.setCode("Measurable");
	    la.setMeasurableEvaluableDiseaseType(type);
	    
	    TS ts = new TS();
	    ts.setValue("20090922090000.0000-0500");
	    la.setClinicalAssessmentDate(ts);
	    
	    la.setImageIdentifier(new II());
	    la.setImageSeriesIdentifier(new II());
	    
	    la.setAction(createCD());
	    
	    
        Patient patient = getCreatedPatient(client);
        TreatmentRegimen tr = patient.getTreatmentRegimens().get(0);        
        tr.getLesionAssessments().add(la);
        
        tr.setAction(updateCD());
        
        client.write(patient);
                
	}
	
	private static void testGetPatientById(BusinessClient client) throws RemoteException {
	    System.out.println("testGetPatientById");

	    Patient patient = getCreatedPatient(client);
	    
	    printResults(patient);
	    System.out.println(patient.getEthnicCode().getCode());
	}
	
	private static void testAddTreatmentRegimentToPatient(BusinessClient client) throws RemoteException {
	    System.out.println("testAddTreatmentRegimentToPatient");
	    
	    TreatmentRegimen tr = new TreatmentRegimen();
	    ST desc = new ST();
	    desc.setValue("my treatment regimen");
	    tr.setDescription(desc);
	    ST name = new ST();
	    name.setValue("my tr name");
	    tr.setName(name);
	    
	    tr.setAction(createCD());
	    
        Patient patient = getCreatedPatient(client);
        patient.getTreatmentRegimens().add(tr);
        patient.setAction(updateCD());
        client.write(patient);
	}
	
	private static void testAddCauseOfDeath(BusinessClient client) throws RemoteException {
	    System.out.println("testAddCauseOfDeath");

	    Patient patient = getCreatedPatient(client);

	    CD causeOfDeath = new CD();
	    causeOfDeath.setCode("Drug Related");
	    
	    TS dd = new TS();
	    dd.setValue("20090922090000.0000-0500");
	    
	    CD noCD  = new CD();
	    noCD.setCode("No");
	    
        DeathInformation deathInfo = new DeathInformation();
        deathInfo.setIdentifier(new II());
        deathInfo.setAction(createCD());
        deathInfo.setCause(causeOfDeath);
        deathInfo.setEventDate(dd);
        deathInfo.setAutopsyInd(noCD);
        
        patient.setDeathInformation(deathInfo);
//        patient.setAction(updateCD());
        
        client.write(patient);       
    }
	
	private static void testUpdatePatient(BusinessClient client) throws RemoteException {
	    System.out.println("testCreatePatientFull");
	    
	    Patient patient = new Patient();
	    patient.setAction(createCD());
	    
	    Pathology path = new Pathology();
	    path.setAction(createCD());
	    patient.setPathology(path);
	    
	    PerformanceStatus perfStat = new PerformanceStatus();
	    perfStat.setAction(createCD());
	    patient.setPerformanceStatus(perfStat);
	    
	    patient = client.write(patient);
	    
	    II id = new II();
	    id.setExtension(patient.getIdentifier().getExtension());
	    
	    patient = new Patient();
	    patient.setIdentifier(id);
	    
	    patient.setAction(updateCD());
	    
	    client.write(patient);
	    
	}
	
	private static void testGetPatient(BusinessClient client)  throws RemoteException {
	    System.out.println("testGetPatient");
	    Id id = new Id();
        id.setExtension(patientId);
        Patient patient = new Patient();
        patient.setIdentifier(id);
        
        patient = client.get(patient)[0];
        printResults(patient);
        
	}
	
	private static void testGetAllPatient(BusinessClient client)  throws RemoteException {
	    System.out.println("testGetAllPatients");
	    Patient[] result = client.get(new Patient());
	    
	    printResults(result);
	    
	}
	
	private static Patient getCreatedPatient(BusinessClient client) throws RemoteException {
	    Id id = new Id();
        id.setExtension(patientId);

        return client.getById(id);
	}
	
	private static CD createCD() {
	    CD createCD = new CD();
        createCD.setCode("create");
        ST st = new ST();
        st.setValue("Create");
        createCD.setDisplayName(st);
        return createCD;
	}

	private static CD updateCD() {
	    CD cd = new CD();
	    cd.setCode("update");
	    ST st = new ST();
	    st.setValue("Update");
	    cd.setDisplayName(st);
	    return cd;
	}

	private static CD deleteCD() {
	    CD cd = new CD();
	    cd.setCode("delete");
	    return cd;
	}

  public gov.nih.nci.coppa.services.outcomes.business.Patient[] get(gov.nih.nci.coppa.services.outcomes.business.Patient patient) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetRequest params = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetRequest();
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetRequestPatient patientContainer = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetRequestPatient();
    patientContainer.setPatient(patient);
    params.setPatient(patientContainer);
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getPatient();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.business.Patient getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getPatient();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.business.Patient write(gov.nih.nci.coppa.services.outcomes.business.Patient patient) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"write");
    gov.nih.nci.coppa.services.outcomes.business.stubs.WriteRequest params = new gov.nih.nci.coppa.services.outcomes.business.stubs.WriteRequest();
    gov.nih.nci.coppa.services.outcomes.business.stubs.WriteRequestPatient patientContainer = new gov.nih.nci.coppa.services.outcomes.business.stubs.WriteRequestPatient();
    patientContainer.setPatient(patient);
    params.setPatient(patientContainer);
    gov.nih.nci.coppa.services.outcomes.business.stubs.WriteResponse boxedResult = portType.write(params);
    return boxedResult.getPatient();
    }
  }

}
