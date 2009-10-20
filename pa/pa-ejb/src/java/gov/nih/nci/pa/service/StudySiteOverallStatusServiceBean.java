package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudySiteOverallStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
/**
 * 
 * @author Vrushali
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public  class StudySiteOverallStatusServiceBean  implements
        StudySiteOverallStatusServiceRemote, StudySiteOverallStatusServiceLocal {
    private static final Logger LOG  = Logger.getLogger(StudySiteOverallStatusServiceBean.class);
    /**
     * 
     * @param dto iso
     * @return bo
     * @throws PAException e
     */
    public StudySiteOverallStatusDTO create(
            StudySiteOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudySiteOverallStatusDTO resultDto = null;
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        validate(dto);

        StudySiteOverallStatus bo = Converters.get(StudySiteOverallStatusConverter.class).
        convertFromDtoToDomain(dto);

        // update
        session.saveOrUpdate(bo);
        resultDto = Converters.get(StudySiteOverallStatusConverter.class).convertFromDomainToDto(bo);
        return resultDto;
    }
    /**
     * @param dto dto to validate
     * @throws PAException e
     */
    public void validate(StudySiteOverallStatusDTO dto) throws PAException {
         StringBuffer sb = new StringBuffer();
         // enforce business rules
         StudySiteOverallStatusDTO oldStatus = getCurrentByStudySite(dto.getStudySiteIdentifier());
         StudyStatusCode oldCode = null;
         Timestamp oldDate = null;
        if (oldStatus != null) {
            oldCode = StudyStatusCode.getByCode(oldStatus.getStatusCode().getCode());
            oldDate = TsConverter.convertToTimestamp(oldStatus.getStatusDate());
        }
        StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
        Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
        if (newCode == null) {
            sb.append("Study status must be set.  ");
        }
        if (newDate == null) {
            sb.append("Study status date must be set.  ");
        }
        if ((oldCode != null) && isTrialStatusOrDateChanged(dto, dto.getStudySiteIdentifier()) 
                && !oldCode.canTransitionTo(newCode)) {
            sb.append("Illegal site status transition from " + oldCode.getCode()
                    + " to " + newCode.getCode() + ".  ");
        }
        if ((oldDate != null) && newDate.before(oldDate)) {
            sb.append("New current site status date should be bigger/same as old date.  ");
        }
        if (sb.length() > 1) {
            throw new PAException(sb.toString());
        }
    }
    /**
     * 
     * @param studySiteIdentifier ii
     * @return dto
     * @throws PAException e
     */
    public StudySiteOverallStatusDTO getCurrentByStudySite(
            Ii studySiteIdentifier) throws PAException {
        List<StudySiteOverallStatusDTO> dtoList = getByStudySite(studySiteIdentifier);
        StudySiteOverallStatusDTO result = null;
        if (!dtoList.isEmpty()) {
            result = dtoList.get(dtoList.size() - 1);
        }
        return result;
    }
    /**
     * @param studySiteIi id of Site
     * @return list StudySiteAccrualStatusDTO
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteOverallStatusDTO> getByStudySite(Ii studySiteIi)
    throws PAException {
        if (PAUtil.isIiNull(studySiteIi)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudySiteOverallStatusByStudySite");

        Session session = null;
        List<StudySiteOverallStatus> queryList = new ArrayList<StudySiteOverallStatus>();
        session = HibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select ssos "
            + " from StudySiteOverallStatus as ssos "
            + " join ssos.studySite as sp "
            + " where sp.id = :studySiteId "
            + " order by ssos.id ";
        LOG.info(" query StudySiteOverallStatus = " + hql);

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studySiteId", IiConverter.convertToLong(studySiteIi));

        // step 3: query the result
        queryList = query.list();
        ArrayList<StudySiteOverallStatusDTO> resultList = new ArrayList<StudySiteOverallStatusDTO>();
        for (StudySiteOverallStatus bo : queryList) {
            resultList.add(new StudySiteOverallStatusConverter().convertFromDomainToDto(bo));
        }

        LOG.info("Leaving getStudySiteOverallStatusByStudySite, returning "
                + resultList.size() + " object(s).");
        return resultList;
    }
    private boolean isTrialStatusOrDateChanged(StudySiteOverallStatusDTO newStatusDto,
            Ii studySiteIdentifier) throws PAException {

        boolean statusOrDateChanged = true;
        StudySiteOverallStatusDTO  currentDBdto = getCurrentByStudySite(studySiteIdentifier);
        StudyStatusCode currentStatusCode = StudyStatusCode.getByCode(currentDBdto.getStatusCode().getCode());
        Timestamp currentStatusDate = PAUtil.dateStringToTimestamp(currentDBdto.getStatusDate().toString());
        
        boolean codeChanged = (StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode()) == null)
                ? (currentStatusCode != null) 
                        : !StudyStatusCode.getByCode(newStatusDto.getStatusCode().getCode()).equals(currentStatusCode);
        boolean statusDateChanged = (currentStatusDate == null) 
                ? (PAUtil.dateStringToTimestamp(newStatusDto.getStatusDate().toString()) != null) 
                : !currentStatusDate.equals(PAUtil.dateStringToTimestamp(newStatusDto.getStatusDate().toString()));
        if (!codeChanged && !statusDateChanged) {
            statusOrDateChanged = false;
        }
        return statusOrDateChanged;
    }


}
