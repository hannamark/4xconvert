package gov.nih.nci.coppa.services.accrual.subjectaccrualservice.service;

import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.accrual.grid.dto.accrual.StudySubjectTransformer;
import gov.nih.nci.coppa.services.accrual.grid.remote.InvokeSubjectAccrualEjb;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.iso21090.grid.dto.transform.iso.EDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.INTTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.TSTransformer;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Grid service methods for performing/updating/deleting and searching subject accruals.
 *
 * @created by Introduce Toolkit version 1.3
 */
public class SubjectAccrualServiceImpl extends SubjectAccrualServiceImplBase {
    private static final Logger logger = LogManager.getLogger(SubjectAccrualServiceImpl.class);
    private final InvokeSubjectAccrualEjb subjectAccrualService = new InvokeSubjectAccrualEjb();

    /**
     * Simple constructor.
     *
     * @throws RemoteException on error
     */
	public SubjectAccrualServiceImpl() throws RemoteException {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
  public gov.nih.nci.coppa.services.accrual.StudySubject[] manageSubjectAccruals(gov.nih.nci.coppa.services.accrual.StudySubject[] studySubjects) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.services.accrual.faults.IndexedInputValidationFault {
	    try {
	        List<SubjectAccrualDTO> input = StudySubjectTransformer.INSTANCE.convert(studySubjects);
	        List<SubjectAccrualDTO> results = subjectAccrualService.manageSubjectAccruals(input);
	        return StudySubjectTransformer.INSTANCE.convert(results);
	    } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
	}

  /**
   * {@inheritDoc}
   */
  public void deleteSubjectAccrual(gov.nih.nci.iso21090.extensions.Id subjectAccrualdenfier) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii ii = IITransformer.INSTANCE.toDto(subjectAccrualdenfier);
          subjectAccrualService.deleteSubjectAccrual(ii);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  /**
   * {@inheritDoc}
   */
  public void submitBatchData(gov.nih.nci.coppa.services.pa.Ed batchData) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ed data = EDTransformer.INSTANCE.toDto(batchData);
          subjectAccrualService.submitBatchData(data);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  /**
   * {@inheritDoc}
   */
  public gov.nih.nci.coppa.services.accrual.StudySubject[] search(gov.nih.nci.iso21090.extensions.Id studyProtocolIdentifer,gov.nih.nci.iso21090.extensions.Id participatingSiteIdentifier,gov.nih.nci.coppa.services.accrual.Ts startDate,gov.nih.nci.coppa.services.accrual.Ts endDate,gov.nih.nci.coppa.common.LimitOffset pagingParams) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          Ii spIi = IITransformer.INSTANCE.toDto(studyProtocolIdentifer);
          Ii participatingSiteIi = IITransformer.INSTANCE.toDto(participatingSiteIdentifier);
          Ts start = TSTransformer.INSTANCE.toDto(startDate);
          Ts end = TSTransformer.INSTANCE.toDto(endDate);
          LimitOffset lo = LimitOffsetTransformer.INSTANCE.toDto(pagingParams);
          List<SubjectAccrualDTO> results = subjectAccrualService.search(spIi, participatingSiteIi, start, end, lo);
          return StudySubjectTransformer.INSTANCE.convert(results);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  /**
   * {@inheritDoc}
   */
  public void updateSubjectAccrualCount(gov.nih.nci.iso21090.extensions.Id participatingSiteIdentifier,gov.nih.nci.coppa.services.accrual.Integer accrualCount) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii participatingSiteIi = IITransformer.INSTANCE.toDto(participatingSiteIdentifier);
          Int count = INTTransformer.INSTANCE.toDto(accrualCount);
          subjectAccrualService.updateSubjectAccrualCount(participatingSiteIi, count);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

