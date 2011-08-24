package gov.nih.nci.coppa.services.accrual.subjectaccrualservice.client;

import gov.nih.nci.coppa.services.accrual.StudySubject;
import gov.nih.nci.coppa.services.accrual.faults.IndexedInputValidationFault;
import gov.nih.nci.coppa.services.accrual.subjectaccrualservice.common.SubjectAccrualServiceI;
import gov.nih.nci.coppa.services.client.ClientUtils;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.coppa.services.pa.Ed;
import gov.nih.nci.coppa.services.pa.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.ISOUtils;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.DSETCD;
import org.iso._21090.II;
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
public class SubjectAccrualServiceClient extends SubjectAccrualServiceClientBase implements SubjectAccrualServiceI {
    private static final ClientParameterHelper<SubjectAccrualServiceClient> HELPER =
        new ClientParameterHelper<SubjectAccrualServiceClient>(SubjectAccrualServiceClient.class);

    private static final String[] LOCAL_ARGS = new String[] { } ;

    public SubjectAccrualServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public SubjectAccrualServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url,proxy);
    }

    public SubjectAccrualServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr,null);
    }

    public SubjectAccrualServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(epr,proxy);
    }

    public static void usage(){
        System.out.println(SubjectAccrualServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");

        HELPER.setLocalArgs(LOCAL_ARGS);
        HELPER.setupParams(args);

        SubjectAccrualServiceClient client = null;
        try{
            client = new SubjectAccrualServiceClient(args[1]);
        } catch (Exception e) {
            System.out.println("Exception getting client");
            e.printStackTrace();
            System.exit(1);
        }

        for (Method method : HELPER.getRunMethods()) {
            System.out.println("Running " + method.getName());
            try {
                method.invoke(null, client);
            } catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GridTestMethod
    private static void manageSubjectAccruals(SubjectAccrualServiceClient client) throws RemoteException, PAFault, IndexedInputValidationFault {
        System.out.println("Managing Subject Accruals");
        TS birthDate = new TS();
        birthDate.setValue("20110101000000.0000-0500");

        StudySubject subject = new StudySubject();
        subject.setAssignedIdentifier(ISOUtils.buildST("Grid-Subject-1"));
        subject.setBirthDate(birthDate);
        subject.setRegistrationDate(birthDate);
        subject.setEthnicity(ISOUtils.buildCD(PatientEthnicityCode.NOT_HISPANIC.getCode()));
        subject.setGender(ISOUtils.buildCD(PatientGenderCode.MALE.getCode()));
        subject.setCountryCode(ISOUtils.buildCD("US"));
        subject.setZipCode(ISOUtils.buildST("11111"));

        CD race = ISOUtils.buildCD(PatientRaceCode.UNKNOWN.getCode());
        DSETCD races = new DSETCD();
        races.getItem().add(race);
        subject.setRace(races);

        II diseaseIi = new II();
        diseaseIi.setExtension("218");

        II participatingSiteIi = new II();
        participatingSiteIi.setExtension("27535");

        subject.setDiseaseIdentifier(diseaseIi);
        subject.setParticipatingSiteIdentifier(participatingSiteIi);

        StudySubject[] studySubjects = {subject};

        StudySubject[] results = client.manageSubjectAccruals(studySubjects);
        ClientUtils.print(results);

        subject = results[0];
        subject.setAssignedIdentifier(ISOUtils.buildST("Grid-Subject-1-Edit"));
        studySubjects = new StudySubject[] {subject};
        client.manageSubjectAccruals(studySubjects);
        ClientUtils.print(results);
    }

    @GridTestMethod
    private static void submitBataData(SubjectAccrualServiceClient client) throws RemoteException, IOException, PAFault {
        File batchFile = new File(SubjectAccrualServiceClient.class.getResource("/README.txt").getPath());
        byte[] fileData = new byte[(int) batchFile.length()];
        FileInputStream fin = new FileInputStream(batchFile);
        DataInputStream din = new DataInputStream(fin);
        din.readFully(fileData);

        Ed batchData = new Ed();
        batchData.setData(fileData);

        client.submitBatchData(batchData);
    }

    public gov.nih.nci.coppa.services.accrual.StudySubject[] manageSubjectAccruals(gov.nih.nci.coppa.services.accrual.StudySubject[] studySubjects) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.services.accrual.faults.IndexedInputValidationFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"manageSubjectAccruals");
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.ManageSubjectAccrualsRequest params = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.ManageSubjectAccrualsRequest();
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.ManageSubjectAccrualsRequestStudySubjects studySubjectsContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.ManageSubjectAccrualsRequestStudySubjects();
            studySubjectsContainer.setStudySubject(studySubjects);
            params.setStudySubjects(studySubjectsContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.ManageSubjectAccrualsResponse boxedResult = portType.manageSubjectAccruals(params);
            return boxedResult.getStudySubject();
        }
    }

    public void deleteSubjectAccrual(gov.nih.nci.iso21090.extensions.Id subjectAccrualdenfier) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"deleteSubjectAccrual");
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.DeleteSubjectAccrualRequest params = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.DeleteSubjectAccrualRequest();
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.DeleteSubjectAccrualRequestSubjectAccrualdenfier subjectAccrualdenfierContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.DeleteSubjectAccrualRequestSubjectAccrualdenfier();
            subjectAccrualdenfierContainer.setId(subjectAccrualdenfier);
            params.setSubjectAccrualdenfier(subjectAccrualdenfierContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.DeleteSubjectAccrualResponse boxedResult = portType.deleteSubjectAccrual(params);
        }
    }

    public void submitBatchData(gov.nih.nci.coppa.services.pa.Ed batchData) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"submitBatchData");
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SubmitBatchDataRequest params = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SubmitBatchDataRequest();
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SubmitBatchDataRequestBatchData batchDataContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SubmitBatchDataRequestBatchData();
            batchDataContainer.setEd(batchData);
            params.setBatchData(batchDataContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SubmitBatchDataResponse boxedResult = portType.submitBatchData(params);
        }
    }

    public gov.nih.nci.coppa.services.accrual.StudySubject[] search(gov.nih.nci.iso21090.extensions.Id studyProtocolIdentifer,gov.nih.nci.iso21090.extensions.Id participatingSiteIdentifier,gov.nih.nci.coppa.services.accrual.Ts startDate,gov.nih.nci.coppa.services.accrual.Ts endDate,gov.nih.nci.coppa.common.LimitOffset pagingParams) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"search");
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequest params = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequest();
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestStudyProtocolIdentifer studyProtocolIdentiferContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestStudyProtocolIdentifer();
            studyProtocolIdentiferContainer.setId(studyProtocolIdentifer);
            params.setStudyProtocolIdentifer(studyProtocolIdentiferContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestParticipatingSiteIdentifier participatingSiteIdentifierContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestParticipatingSiteIdentifier();
            participatingSiteIdentifierContainer.setId(participatingSiteIdentifier);
            params.setParticipatingSiteIdentifier(participatingSiteIdentifierContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestStartDate startDateContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestStartDate();
            startDateContainer.setTs(startDate);
            params.setStartDate(startDateContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestEndDate endDateContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestEndDate();
            endDateContainer.setTs(endDate);
            params.setEndDate(endDateContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestPagingParams pagingParamsContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchRequestPagingParams();
            pagingParamsContainer.setLimitOffset(pagingParams);
            params.setPagingParams(pagingParamsContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.SearchResponse boxedResult = portType.search(params);
            return boxedResult.getStudySubject();
        }
    }

    public void updateSubjectAccrualCount(gov.nih.nci.iso21090.extensions.Id participatingSiteIdentifier,gov.nih.nci.coppa.services.accrual.Integer accrualCount) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"updateSubjectAccrualCount");
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequest params = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequest();
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequestParticipatingSiteIdentifier participatingSiteIdentifierContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequestParticipatingSiteIdentifier();
            participatingSiteIdentifierContainer.setId(participatingSiteIdentifier);
            params.setParticipatingSiteIdentifier(participatingSiteIdentifierContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequestAccrualCount accrualCountContainer = new gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountRequestAccrualCount();
            accrualCountContainer.setInteger(accrualCount);
            params.setAccrualCount(accrualCountContainer);
            gov.nih.nci.coppa.services.accrual.subjectaccrualservice.stubs.UpdateSubjectAccrualCountResponse boxedResult = portType.updateSubjectAccrualCount(params);
        }
    }

}
