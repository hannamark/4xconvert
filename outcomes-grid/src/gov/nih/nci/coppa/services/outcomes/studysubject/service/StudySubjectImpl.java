package gov.nih.nci.coppa.services.outcomes.studysubject.service;

import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.StudySubject;
import gov.nih.nci.coppa.services.outcomes.grid.GenericAccrualStudyGridServiceImpl;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.StudySubjectTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeStudySubjectEjb;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Study Subject Service. Dispatches to the remote EJBs and the Transformers.
 *
 */
public class StudySubjectImpl extends StudySubjectImplBase {
    private static final Logger logger = LogManager.getLogger(StudySubjectImpl.class);
    private final InvokeStudySubjectEjb studySubjectService = new InvokeStudySubjectEjb();
    private GenericAccrualStudyGridServiceImpl<StudySubjectDto, StudySubject> impl
        = new GenericAccrualStudyGridServiceImpl<StudySubjectDto, StudySubject>(StudySubject.class, StudySubjectDto.class);

	public StudySubjectImpl() throws RemoteException {
		super();
	}

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getByStudySite(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          List<StudySubjectDto> ssDtos = studySubjectService.getByStudySite(IITransformer.INSTANCE.toDto(id));
          return StudySubjectTransformer.INSTANCE.convert(ssDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getOutcomes(gov.nih.nci.coppa.services.outcomes.ST outcomesLoginName) throws RemoteException {
      try {
          List<StudySubjectDto> ssDtos = studySubjectService.getOutcomes(STTransformer.INSTANCE.toDto(outcomesLoginName));
          return StudySubjectTransformer.INSTANCE.convert(ssDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject createOutcomes(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
      try {
          StudySubjectDto ssDto = studySubjectService.createOutcomes(StudySubjectTransformer.INSTANCE.toDto(studySubject));
          return StudySubjectTransformer.INSTANCE.toXml(ssDto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getByStudyProtocol(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      return impl.getByStudyProtocol(id);
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    return impl.get(id);
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject create(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
    return impl.create(studySubject);
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject update(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
    return impl.update(studySubject);
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    impl.delete(id);
  }

}

